/*
 * MockStateListener.java
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
 * Created on May 31, 2008 at 9:30:25 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.EngineException;

/**
 * Mock implementation of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener}
 * .
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
    private StateChangeEvent stateChangedEvent_;

    /** The count of state changed events received from the engine. */
    private int stateChangedEventCount_;

    /** The most recent state changing event received from the engine. */
    private StateChangeEvent stateChangingEvent_;

    /** The count of state changing events received from the engine. */
    private int stateChangingEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockStateListener} class.
     */
    public MockStateListener()
    {
        stateChangedEvent_ = null;
        stateChangedEventCount_ = 0;
        stateChangingEvent_ = null;
        stateChangingEventCount_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clears all information about events received from the engine.
     */
    public void clearEvents()
    {
        stateChangedEvent_ = null;
        stateChangedEventCount_ = 0;
        stateChangingEvent_ = null;
        stateChangingEventCount_ = 0;
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
        return stateChangedEvent_;
    }

    /**
     * Gets the count of state changed events received from the engine.
     * 
     * @return The count of state changed events received from the engine.
     */
    public int getStateChangedEventCount()
    {
        return stateChangedEventCount_;
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
        return stateChangingEvent_;
    }

    /**
     * Gets the count of state changing events received from the engine.
     * 
     * @return The count of state changing events received from the engine.
     */
    public int getStateChangingEventCount()
    {
        return stateChangingEventCount_;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanged(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    public void stateChanged(
        final StateChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        stateChangedEvent_ = event;
        ++stateChangedEventCount_;
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

        stateChangingEvent_ = event;
        ++stateChangingEventCount_;
    }
}
