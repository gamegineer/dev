/*
 * NetworkTableEvent.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Superclass for all event objects fired by a network table.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class NetworkTableEvent
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
     * Initializes a new instance of the {@code NetworkTableEvent} class.
     * 
     * @param source
     *        The network table that fired the event; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    public NetworkTableEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final INetworkTable source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the network table that fired the event.
     * 
     * @return The network table that fired the event; never {@code null}.
     */
    /* @NonNull */
    public final INetworkTable getNetworkTable()
    {
        return (INetworkTable)getSource();
    }
}
