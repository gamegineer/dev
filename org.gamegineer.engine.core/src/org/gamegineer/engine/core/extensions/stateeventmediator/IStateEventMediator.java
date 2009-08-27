/*
 * IStateEventMediator.java
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
 * Created on May 30, 2008 at 10:57:34 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import org.gamegineer.engine.core.IEngineContext;

/**
 * An engine extension that publishes engine state events to interested clients.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStateEventMediator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified state listener to the engine.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param listener
     *        The state listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered state listener.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code listener} is {@code null}.
     */
    public void addStateListener(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        IStateListener listener );

    /**
     * Removes the specified state listener from the engine.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param listener
     *        The state listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered state listener.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code listener} is {@code null}.
     */
    public void removeStateListener(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        IStateListener listener );
}
