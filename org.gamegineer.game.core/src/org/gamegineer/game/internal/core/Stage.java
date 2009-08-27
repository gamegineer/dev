/*
 * Stage.java
 * Copyright 2008-2009 Gamegineer.org
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Jul 22, 2008 at 9:46:25 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.game.core.GameException;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;
import org.gamegineer.game.internal.core.commands.ActivateStageCommand;
import org.gamegineer.game.internal.core.commands.DeactivateStageCommand;

/**
 * Implementation of {@link org.gamegineer.game.core.system.IStage}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class Stage
    implements IStage, ICommandListener, IStateListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage cardinality. */
    private final int m_cardinality;

    /** The stage identifier. */
    private final String m_id;

    /** The child stage list. */
    private final List<Stage> m_stages;

    /** The stage strategy. */
    private final IStageStrategy m_strategy;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Stage} class.
     * 
     * @param stage
     *        The stage prototype; must not be {@code null}. It is assumed the
     *        stage prototype has already been validated and is legal.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stage} is {@code null}.
     */
    public Stage(
        /* @NonNull */
        final IStage stage )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$

        m_cardinality = stage.getCardinality();
        m_id = stage.getId();
        m_strategy = stage.getStrategy();

        final List<Stage> childStages = new ArrayList<Stage>();
        for( final IStage childStage : stage.getStages() )
        {
            childStages.add( new Stage( childStage ) );
        }
        m_stages = Collections.unmodifiableList( childStages );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates the stage or one of its descendant stages.
     * 
     * <p>
     * If the stage is inactive, it will be activated.
     * </p>
     * 
     * <p>
     * If the stage is already active and has no active child stage (i.e. it is
     * the executing stage), the next appropriate child stage will be activated
     * and made the executing stage. If the stage has an active child stage
     * (i.e. it is not the executing stage), the activation request will be
     * forwarded to the child stage (the executing stage).
     * </p>
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the stage is already active but has no child stages.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.game.core.GameException
     *         If an error occurs while activating the stage.
     */
    public void activate(
        /* @NonNull */
        final IEngineContext context )
        throws GameException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IState state = context.getState();
        if( isStageStatePresent( state ) )
        {
            if( getStageState( state ).getActiveChildStageIndex() == -1 )
            {
                assertStateLegal( !m_stages.isEmpty(), Messages.Stage_activate_noChildStages );
                final int newChildStageIndex = (getStageState( state ).getPreviousChildStageIndex() + 1) % m_stages.size();
                setStageState( state, getStageState( state ).activateChildStage( newChildStageIndex ) );
            }

            m_stages.get( getStageState( state ).getActiveChildStageIndex() ).activate( context );
        }
        else
        {
            if( Debug.DEFAULT )
            {
                Debug.trace( String.format( "Activating stage '%1$s'.", m_id ) ); //$NON-NLS-1$
            }
            GameAttributes.stageState( m_id ).add( state, new StageState( this ) );
            m_strategy.activate( this, context );
        }
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuted(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent)
     */
    public void commandExecuted(
        final CommandExecutedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final IEngineContext context = event.getEngineContext();
        final StageState stageState = getStageState( context.getState() );
        final int activeChildStageIndex = stageState.getActiveChildStageIndex();
        if( activeChildStageIndex == -1 )
        {
            m_strategy.commandExecuted( event );

            if( m_strategy.isComplete( this, context ) )
            {
                submitCommand( context, new DeactivateStageCommand( m_id, stageState.getVersion() ) );
            }
            else if( !m_stages.isEmpty() )
            {
                submitCommand( context, new ActivateStageCommand( m_id, stageState.getVersion() ) );
            }
        }
        else
        {
            m_stages.get( activeChildStageIndex ).commandExecuted( event );
        }
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuting(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent)
     */
    public void commandExecuting(
        final CommandExecutingEvent event )
        throws EngineException
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final IEngineContext context = event.getEngineContext();
        final int activeChildStageIndex = getStageState( context.getState() ).getActiveChildStageIndex();
        if( activeChildStageIndex == -1 )
        {
            m_strategy.commandExecuting( event );
        }
        else
        {
            m_stages.get( activeChildStageIndex ).commandExecuting( event );
        }
    }

    /**
     * Deactivates the stage or one of its descendant stages.
     * 
     * <p>
     * If the stage has no active child stage (i.e. it is the executing stage),
     * it will be deactivated. If the stage has an active child stage (i.e. it
     * is not the executing stage), the deactivation request will be forwarded
     * to the child stage (the executing stage).
     * </p>
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return {@code true} if this stage was deactivated; otherwise
     *         {@code false} if a descendant stage of this stage was
     *         deactivated.
     * 
     * @throws java.lang.IllegalStateException
     *         If this stage is not active.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.game.core.GameException
     *         If an error occurs while deactivating the stage.
     */
    public boolean deactivate(
        /* @NonNull */
        final IEngineContext context )
        throws GameException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertStateLegal( isStageStatePresent( context.getState() ), Messages.Stage_deactivate_notActive );

        final IState state = context.getState();
        final int activeChildStageIndex = getStageState( state ).getActiveChildStageIndex();
        if( activeChildStageIndex != -1 )
        {
            if( m_stages.get( activeChildStageIndex ).deactivate( context ) )
            {
                setStageState( state, getStageState( state ).deactivateChildStage() );
            }
            return false;
        }

        if( Debug.DEFAULT )
        {
            Debug.trace( String.format( "Deactivating stage '%1$s'.", m_id ) ); //$NON-NLS-1$
        }
        m_strategy.deactivate( this, context );
        GameAttributes.stageState( m_id ).remove( state );
        return true;
    }

    /**
     * Gets the active stage with the specified stage identifier.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * @param stageId
     *        The stage identifier; must not be {@code null}.
     * 
     * @return The active stage with the specified stage identifier or
     *         {@code null} if the specified stage is not active.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code state} or {@code stageId} is {@code null}.
     */
    /* @Nullable */
    public static Stage getActiveStage(
        /* @NonNull */
        final IState state,
        /* @NonNull */
        final String stageId )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$
        assertArgumentNotNull( stageId, "stageId" ); //$NON-NLS-1$

        if( !GameAttributes.stageState( stageId ).isPresent( state ) )
        {
            return null;
        }

        return GameAttributes.stageState( stageId ).getValue( state ).getStage();
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getCardinality()
     */
    public int getCardinality()
    {
        return m_cardinality;
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getId()
     */
    public String getId()
    {
        return m_id;
    }

    /**
     * Gets the stage state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return The stage state; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the stage state does not exist in the engine state.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    /* @NonNull */
    private StageState getStageState(
        /* @NonNull */
        final IState state )
    {
        return GameAttributes.stageState( m_id ).getValue( state );
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getStages()
     */
    public List<IStage> getStages()
    {
        return new ArrayList<IStage>( m_stages );
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getStrategy()
     */
    public IStageStrategy getStrategy()
    {
        return m_strategy;
    }

    /**
     * Gets the stage version.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return The stage version; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the stage is not active.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    /* @NonNull */
    public StageVersion getVersion(
        /* @NonNull */
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$
        assertStateLegal( isActive( state ), Messages.Stage_notActive( m_id ) );

        return getStageState( state ).getVersion();
    }

    /**
     * Indicates this stage is active.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return {@code true} if this stage is active; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    public boolean isActive(
        /* @NonNull */
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$

        return isStageStatePresent( state );
    }

    /**
     * Indicates this stage is currently executing.
     * 
     * <p>
     * By definition, the currently executing stage is an active stage. Thus, if
     * this method returns {@code true}, {@code isActive} must necessarily also
     * return {@code true}.
     * </p>
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return {@code true} if this stage is currently executing; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    public boolean isExecuting(
        /* @NonNull */
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$

        if( !isActive( state ) )
        {
            return false;
        }

        return getStageState( state ).getActiveChildStageIndex() == -1;
    }

    /**
     * Indicates the stage state is present in the specified engine state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return {@code true} if the stage state is present in the specified
     *         engine state; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    private boolean isStageStatePresent(
        /* @NonNull */
        final IState state )
    {
        return GameAttributes.stageState( m_id ).isPresent( state );
    }

    /**
     * Sets the stage state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * @param stageState
     *        The stage state; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the stage state does not exist in the engine state.
     * @throws java.lang.NullPointerException
     *         If {@code state} or {@code stageState} is {@code null}.
     */
    private void setStageState(
        /* @NonNull */
        final IState state,
        /* @NonNull */
        final StageState stageState )
    {
        GameAttributes.stageState( m_id ).setValue( state, stageState );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanged(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    public void stateChanged(
        final StateChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final IEngineContext context = event.getEngineContext();
        final int activeChildStageIndex = getStageState( context.getState() ).getActiveChildStageIndex();
        if( activeChildStageIndex == -1 )
        {
            m_strategy.stateChanged( event );
        }
        else
        {
            m_stages.get( activeChildStageIndex ).stateChanged( event );
        }
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanging(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    public void stateChanging(
        final StateChangeEvent event )
        throws EngineException
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final IEngineContext context = event.getEngineContext();
        final int activeChildStageIndex = getStageState( context.getState() ).getActiveChildStageIndex();
        if( activeChildStageIndex == -1 )
        {
            m_strategy.stateChanging( event );
        }
        else
        {
            m_stages.get( activeChildStageIndex ).stateChanging( event );
        }
    }

    /**
     * Submits the specified command to the specified engine.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The command; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the engine does not support the command queue extension.
     */
    private static void submitCommand(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command )
    {
        assert context != null;
        assert command != null;

        final ICommandQueue commandQueue = context.getExtension( ICommandQueue.class );
        if( commandQueue == null )
        {
            throw new IllegalArgumentException( Messages.Stage_commandQueueExtension_unavailable );
        }

        try
        {
            commandQueue.submitCommand( context, command );
        }
        catch( final IllegalStateException e )
        {
            if( Debug.DEFAULT )
            {
                Debug.trace( "A stage command could not be submitted because the engine has been shut down." ); //$NON-NLS-1$
            }
        }
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format( "Stage[m_id='%1$s']", m_id ); //$NON-NLS-1$
    }
}
