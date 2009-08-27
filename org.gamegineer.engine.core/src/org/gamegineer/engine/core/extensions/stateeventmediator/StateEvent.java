/*
 * StateEvent.java
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
 * Created on May 30, 2008 at 11:08:57 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import java.util.EventObject;
import org.gamegineer.engine.core.IEngineContext;

/**
 * Superclass for all event objects fired by an engine that are associated with
 * the engine state.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public abstract class StateEvent
    extends EventObject
    implements IStateEvent
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEvent} class.
     * 
     * @param source
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    protected StateEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final IEngineContext source )
    {
        super( source );
    }
}