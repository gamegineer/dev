/*
 * Game.java
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
 * Created on Jul 16, 2008 at 10:33:18 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Future;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineConfigurationException;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.EngineFactory;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandEventMediatorFacade;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateEventMediatorFacade;
import org.gamegineer.game.core.GameCommands;
import org.gamegineer.game.core.GameException;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.internal.core.commands.InitializeEngineCommand;
import org.gamegineer.game.internal.core.config.ConfigurationUtils;

/**
 * Implementation of {@link org.gamegineer.game.core.IGame}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
final class Game
    implements IGame, ICommandListener, IStateListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game engine. */
    private final IEngine m_engine;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Game} class.
     * 
     * @param engine
     *        The game engine; must not be {@code null}.
     */
    private Game(
        /* @NonNull */
        final IEngine engine )
    {
        assert engine != null;

        m_engine = engine;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuted(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent)
     */
    public void commandExecuted(
        final CommandExecutedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final IState state = event.getEngineContext().getState();
        final Stage rootStage = GameAttributes.ROOT_STAGE.getValue( state );
        if( rootStage.isActive( state ) )
        {
            rootStage.commandExecuted( event );
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

        final IState state = event.getEngineContext().getState();
        final Stage rootStage = GameAttributes.ROOT_STAGE.getValue( state );
        if( rootStage.isActive( state ) )
        {
            rootStage.commandExecuting( event );
        }
    }

    /**
     * Creates a new instance of the {@code Game} class.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}.
     * 
     * @return A new instance of the {@code Game} class; never {@code null}.
     * 
     * @throws org.gamegineer.game.core.GameException
     *         If an error occurs while creating the game.
     */
    /* @NonNull */
    static Game createGame(
        /* @NonNull */
        final IGameConfiguration gameConfig )
        throws GameException
    {
        assert gameConfig != null;

        // Verify the game configuration is legal
        try
        {
            ConfigurationUtils.assertGameConfigurationLegal( gameConfig );
        }
        catch( final IllegalArgumentException e )
        {
            throw new GameException( Messages.Game_gameConfig_illegal, e );
        }

        // Create the engine and initialize its state using the game configuration
        final IEngine engine;
        try
        {
            engine = EngineFactory.createEngine( new InitializeEngineCommand( gameConfig ) );
        }
        catch( final EngineConfigurationException e )
        {
            throw new GameException( Messages.Game_engine_configError, e );
        }

        // Create the game and subscribe to all engine events
        final Game game = new Game( engine );
        CommandEventMediatorFacade.addCommandListener( engine, game );
        StateEventMediatorFacade.addStateListener( engine, game );
        return game;
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#executeCommand(org.gamegineer.engine.core.ICommand)
     */
    public <T> T executeCommand(
        final ICommand<T> command )
        throws EngineException
    {
        return m_engine.executeCommand( command );
    }

    /*
     * @see org.gamegineer.game.core.IGame#getId()
     */
    public String getId()
    {
        try
        {
            return m_engine.executeCommand( GameCommands.createGetGameIdCommand() );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /*
     * @see org.gamegineer.game.core.IGame#getName()
     */
    public String getName()
    {
        try
        {
            return m_engine.executeCommand( GameCommands.createGetGameNameCommand() );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /*
     * @see org.gamegineer.game.core.IGame#getSystemId()
     */
    public String getSystemId()
    {
        try
        {
            return m_engine.executeCommand( GameCommands.createGetGameSystemIdCommand() );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /*
     * @see org.gamegineer.game.core.IGame#isComplete()
     */
    @SuppressWarnings( "boxing" )
    public boolean isComplete()
    {
        try
        {
            return m_engine.executeCommand( GameCommands.createIsGameCompleteCommand() );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#isShutdown()
     */
    public boolean isShutdown()
    {
        return m_engine.isShutdown();
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#shutdown()
     */
    public void shutdown()
        throws InterruptedException
    {
        m_engine.shutdown();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanged(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    public void stateChanged(
        final StateChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final IState state = event.getEngineContext().getState();
        final Stage rootStage = GameAttributes.ROOT_STAGE.getValue( state );
        if( rootStage.isActive( state ) )
        {
            rootStage.stateChanged( event );
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

        final IState state = event.getEngineContext().getState();
        final Stage rootStage = GameAttributes.ROOT_STAGE.getValue( state );
        if( rootStage.isActive( state ) )
        {
            rootStage.stateChanging( event );
        }
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#submitCommand(org.gamegineer.engine.core.ICommand)
     */
    public <T> Future<T> submitCommand(
        final ICommand<T> command )
    {
        return m_engine.submitCommand( command );
    }
}
