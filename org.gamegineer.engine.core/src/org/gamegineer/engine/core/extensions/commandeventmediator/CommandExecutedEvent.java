/*
 * CommandExecutedEvent.java
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
 * Created on May 8, 2008 at 9:39:22 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.IEngineContext;

/**
 * Superclass for all event objects used to notify listeners that an engine
 * command has been executed.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public abstract class CommandExecutedEvent
    extends CommandEvent
    implements ICommandExecutedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3477502806379860236L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandExecutedEvent} class.
     * 
     * @param source
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    protected CommandExecutedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final IEngineContext source )
    {
        super( source );
    }
}
