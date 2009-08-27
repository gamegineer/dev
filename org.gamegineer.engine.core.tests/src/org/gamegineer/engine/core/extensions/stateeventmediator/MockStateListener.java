/*
 * MockStateListener.java
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
 * Created on May 31, 2008 at 9:30:25 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.EngineException;

/**
 * Mock implementation of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public class MockStateListener
    implements IStateListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The most recent state changed event received from the engine. */
    private StateChangeEvent m_stateChangedEvent;

    /** The count of state changed events received from the engine. */
    private int m_stateChangedEventCount;

    /** The most recent state changing event received from the engine. */
    private StateChangeEvent m_stateChangingEvent;

    /** The count of state changing events received from the engine. */
    private int m_stateChangingEventCount;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockStateListener} class.
     */
    public MockStateListener()
    {
        m_stateChangedEvent = null;
        m_stateChangedEventCount = 0;
        m_stateChangingEvent = null;
        m_stateChangingEventCount = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clears all information about events received from the engine.
     */
    public void clearEvents()
    {
        m_stateChangedEvent = null;
        m_stateChangedEventCount = 0;
        m_stateChangingEvent = null;
        m_stateChangingEventCount = 0;
    }

    /**
     * Gets the most recent state changed event received from the engine.
     * 
     * @return The most recent state changed event received from the engine; may
     *         be {@code null}.
     */
    /* @Nullable */
    public StateChangeEvent getStateChangedEvent()
    {
        return m_stateChangedEvent;
    }

    /**
     * Gets the count of state changed events received from the engine.
     * 
     * @return The count of state changed events received from the engine.
     */
    public int getStateChangedEventCount()
    {
        return m_stateChangedEventCount;
    }

    /**
     * Gets the most recent state changing event received from the engine.
     * 
     * @return The most recent state changing event received from the engine;
     *         may be {@code null}.
     */
    /* @Nullable */
    public StateChangeEvent getStateChangingEvent()
    {
        return m_stateChangingEvent;
    }

    /**
     * Gets the count of state changing events received from the engine.
     * 
     * @return The count of state changing events received from the engine.
     */
    public int getStateChangingEventCount()
    {
        return m_stateChangingEventCount;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanged(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    public void stateChanged(
        final StateChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        m_stateChangedEvent = event;
        ++m_stateChangedEventCount;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanging(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    @SuppressWarnings( "unused" )
    public void stateChanging(
        final StateChangeEvent event )
        throws EngineException
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        m_stateChangingEvent = event;
        ++m_stateChangingEventCount;
    }
}
