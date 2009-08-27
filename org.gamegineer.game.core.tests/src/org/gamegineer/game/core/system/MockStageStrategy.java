/*
 * MockStageStrategy.java
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
 * Created on Aug 7, 2008 at 10:59:49 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.game.core.GameException;

/**
 * Mock implementation of {@link org.gamegineer.game.core.system.IStageStrategy}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public class MockStageStrategy
    extends AbstractStageStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The instance synchronization lock. */
    private final Object m_lock;

    /** The call count for the {@code activate} method. */
    @GuardedBy( "m_lock" )
    private int m_activateCallCount;

    /** The call count for the {@code commandExecuted} method. */
    @GuardedBy( "m_lock" )
    private int m_commandExecutedCallCount;

    /** The call count for the {@code commandExecuting} method. */
    @GuardedBy( "m_lock" )
    private int m_commandExecutingCallCount;

    /** The call count for the {@code deactivate} method. */
    @GuardedBy( "m_lock" )
    private int m_deactivateCallCount;

    /** The call count for the {@code stateChanged} method. */
    @GuardedBy( "m_lock" )
    private int m_stateChangedCallCount;

    /** The call count for the {@code stateChanging} method. */
    @GuardedBy( "m_lock" )
    private int m_stateChangingCallCount;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockStageStrategy} class.
     */
    public MockStageStrategy()
    {
        m_lock = new Object();
        m_activateCallCount = 0;
        m_commandExecutedCallCount = 0;
        m_commandExecutingCallCount = 0;
        m_deactivateCallCount = 0;
        m_stateChangedCallCount = 0;
        m_stateChangingCallCount = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.IStageStrategy#activate(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void activate(
        final IStage stage,
        final IEngineContext context )
        throws GameException
    {
        synchronized( m_lock )
        {
            ++m_activateCallCount;
        }

        super.activate( stage, context );
    }

    /**
     * Clears all information about method calls invoked on this object.
     */
    public void clearCalls()
    {
        synchronized( m_lock )
        {
            m_activateCallCount = 0;
            m_commandExecutedCallCount = 0;
            m_commandExecutingCallCount = 0;
            m_deactivateCallCount = 0;
            m_stateChangedCallCount = 0;
            m_stateChangingCallCount = 0;
        }
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuted(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent)
     */
    @Override
    public void commandExecuted(
        final CommandExecutedEvent event )
    {
        synchronized( m_lock )
        {
            ++m_commandExecutedCallCount;
        }

        super.commandExecuted( event );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuting(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent)
     */
    @Override
    public void commandExecuting(
        final CommandExecutingEvent event )
        throws EngineException
    {
        synchronized( m_lock )
        {
            ++m_commandExecutingCallCount;
        }

        super.commandExecuting( event );
    }

    /*
     * @see org.gamegineer.game.core.system.IStageStrategy#deactivate(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void deactivate(
        final IStage stage,
        final IEngineContext context )
        throws GameException
    {
        synchronized( m_lock )
        {
            ++m_deactivateCallCount;
        }

        super.deactivate( stage, context );
    }

    /**
     * Gets the call count for the {@code activate} method.
     * 
     * @return The call count for the {@code activate} method.
     */
    public int getActivateCallCount()
    {
        synchronized( m_lock )
        {
            return m_activateCallCount;
        }
    }

    /**
     * Gets the call count for the {@code commandExecuted} method.
     * 
     * @return The call count for the {@code commandExecuted} method.
     */
    public int getCommandExecutedCallCount()
    {
        synchronized( m_lock )
        {
            return m_commandExecutedCallCount;
        }
    }

    /**
     * Gets the call count for the {@code commandExecuting} method.
     * 
     * @return The call count for the {@code commandExecuting} method.
     */
    public int getCommandExecutingCallCount()
    {
        synchronized( m_lock )
        {
            return m_commandExecutingCallCount;
        }
    }

    /**
     * Gets the call count for the {@code deactivate} method.
     * 
     * @return The call count for the {@code deactivate} method.
     */
    public int getDeactivateCallCount()
    {
        synchronized( m_lock )
        {
            return m_deactivateCallCount;
        }
    }

    /**
     * Gets the call count for the {@code stateChanged} method.
     * 
     * @return The call count for the {@code stateChanged} method.
     */
    public int getStateChangedCallCount()
    {
        synchronized( m_lock )
        {
            return m_stateChangedCallCount;
        }
    }

    /**
     * Gets the call count for the {@code stateChanging} method.
     * 
     * @return The call count for the {@code stateChanging} method.
     */
    public int getStateChangingCallCount()
    {
        synchronized( m_lock )
        {
            return m_stateChangingCallCount;
        }
    }

    /**
     * The default implementation always indicates the stage is complete.
     * 
     * @see org.gamegineer.game.core.system.IStageStrategy#isComplete(org.gamegineer.game.core.system.IStage,
     *      org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public boolean isComplete(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return true;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanged(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    @Override
    public void stateChanged(
        final StateChangeEvent event )
    {
        synchronized( m_lock )
        {
            ++m_stateChangedCallCount;
        }

        super.stateChanged( event );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanging(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    @Override
    public void stateChanging(
        final StateChangeEvent event )
        throws EngineException
    {
        synchronized( m_lock )
        {
            ++m_stateChangingCallCount;
        }

        super.stateChanging( event );
    }
}
