/*
 * IStateListener.java
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
 * Created on May 31, 2008 at 9:20:44 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import java.util.EventListener;
import org.gamegineer.engine.core.EngineException;

/**
 * The listener interface for use by clients to be notified of events pertaining
 * to changes in the engine state.
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface IStateListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the state has changed.
     * 
     * @param event
     *        The event describing the changed state; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void stateChanged(
        /* @NonNull */
        StateChangeEvent event );

    /**
     * Invoked before the state is about to be changed.
     * 
     * <p>
     * Clients may abort the state change by throwing an {@code EngineException}
     * from this method.
     * </p>
     * 
     * @param event
     *        The event describing the state change; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If the state should not be changed.
     */
    public void stateChanging(
        /* @NonNull */
        StateChangeEvent event )
        throws EngineException;
}
