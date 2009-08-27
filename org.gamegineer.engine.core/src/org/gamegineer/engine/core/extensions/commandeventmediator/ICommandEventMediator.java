/*
 * ICommandEventMediator.java
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
 * Created on May 30, 2008 at 8:26:05 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.IEngineContext;

/**
 * An engine extension that publishes engine command events to interested
 * clients.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ICommandEventMediator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified command listener to the engine.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param listener
     *        The command listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered command listener.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code listener} is {@code null}.
     */
    public void addCommandListener(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        ICommandListener listener );

    /**
     * Removes the specified command listener from the engine.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param listener
     *        The command listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered command listener.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code listener} is {@code null}.
     */
    public void removeCommandListener(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        ICommandListener listener );
}
