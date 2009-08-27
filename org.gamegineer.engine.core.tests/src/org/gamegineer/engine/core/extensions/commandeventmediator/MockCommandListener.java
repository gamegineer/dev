/*
 * MockCommandListener.java
 * Copyright 2008 Gamegineer.org
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
 * Created on May 9, 2008 at 10:47:13 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.EngineException;

/**
 * Mock implementation of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public class MockCommandListener
    implements ICommandListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The most recent command executed event received from the engine. */
    private CommandExecutedEvent m_commandExecutedEvent;

    /** The count of command executed events received from the engine. */
    private int m_commandExecutedEventCount;

    /** The most recent command executing event received from the engine. */
    private CommandExecutingEvent m_commandExecutingEvent;

    /** The count of command executing events received from the engine. */
    private int m_commandExecutingEventCount;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCommandListener} class.
     */
    public MockCommandListener()
    {
        m_commandExecutedEvent = null;
        m_commandExecutedEventCount = 0;
        m_commandExecutingEvent = null;
        m_commandExecutingEventCount = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clears all information about events received from the engine.
     */
    public void clearEvents()
    {
        m_commandExecutedEvent = null;
        m_commandExecutedEventCount = 0;
        m_commandExecutingEvent = null;
        m_commandExecutingEventCount = 0;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuted(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent)
     */
    public void commandExecuted(
        final CommandExecutedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        m_commandExecutedEvent = event;
        ++m_commandExecutedEventCount;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuting(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent)
     */
    @SuppressWarnings( "unused" )
    public void commandExecuting(
        final CommandExecutingEvent event )
        throws EngineException
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        m_commandExecutingEvent = event;
        ++m_commandExecutingEventCount;
    }

    /**
     * Gets the most recent command executed event received from the engine.
     * 
     * @return The most recent command executed event received from the engine;
     *         may be {@code null}.
     */
    /* @Nullable */
    public CommandExecutedEvent getCommandExecutedEvent()
    {
        return m_commandExecutedEvent;
    }

    /**
     * Gets the count of command executed events received from the engine.
     * 
     * @return The count of command executed events received from the engine.
     */
    public int getCommandExecutedEventCount()
    {
        return m_commandExecutedEventCount;
    }

    /**
     * Gets the most recent command executing event received from the engine.
     * 
     * @return The most recent command executing event received from the engine;
     *         may be {@code null}.
     */
    /* @Nullable */
    public CommandExecutingEvent getCommandExecutingEvent()
    {
        return m_commandExecutingEvent;
    }

    /**
     * Gets the count of command executing events received from the engine.
     * 
     * @return The count of command executing events received from the engine.
     */
    public int getCommandExecutingEventCount()
    {
        return m_commandExecutingEventCount;
    }
}
