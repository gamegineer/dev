/*
 * IStateEvent.java
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
 * Created on Jun 2, 2008 at 8:44:03 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import org.gamegineer.engine.core.IEngineContext;

/**
 * The interface that defines the behavior for all event objects fired by an
 * engine that are associated with the engine state.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStateEvent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the context which represents the engine that fired the event.
     * 
     * <p>
     * Note that this context does not allow direct or indirect mutating
     * operations on the engine state. For example, using an extension in a way
     * that modifies the engine state is not supported and will result in a
     * runtime error.
     * </p>
     * 
     * @return The context which represents the engine that fired the event;
     *         never {@code null}.
     */
    /* @NonNull */
    public IEngineContext getEngineContext();
}
