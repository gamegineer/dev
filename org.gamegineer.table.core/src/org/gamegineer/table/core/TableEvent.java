/*
 * TableEvent.java
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
 * Created on Oct 16, 2009 at 9:59:25 PM.
 */

package org.gamegineer.table.core;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * Superclass for all event objects fired by a table.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public abstract class TableEvent
    extends EventObject
    implements ITableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 7958407383161810710L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEvent} class.
     * 
     * @param source
     *        The table that fired the event; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    protected TableEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final ITable source )
    {
        super( source );
    }
}
