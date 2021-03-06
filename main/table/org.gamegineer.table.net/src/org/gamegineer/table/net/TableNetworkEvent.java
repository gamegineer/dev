/*
 * TableNetworkEvent.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Nov 9, 2010 at 10:20:54 PM.
 */

package org.gamegineer.table.net;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * Superclass for all event objects fired by a table network.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class TableNetworkEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -6505624556869162617L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkEvent} class.
     * 
     * @param source
     *        The table network that fired the event.
     */
    public TableNetworkEvent(
        final ITableNetwork source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table network that fired the event.
     * 
     * @return The table network that fired the event.
     */
    public final ITableNetwork getTableNetwork()
    {
        return (ITableNetwork)getSource();
    }
}
