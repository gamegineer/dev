/*
 * NetworkTableDisconnectedEvent.java
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
 * Created on Apr 30, 2011 at 10:10:34 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.ThreadSafe;

/**
 * An event used to notify listeners that the network table has been
 * disconnected from the network.
 */
@ThreadSafe
public final class NetworkTableDisconnectedEvent
    extends NetworkTableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -526834421408764189L;

    /**
     * The error that caused the network table to be disconnected or {@code
     * null} if the network table was disconnected normally.
     */
    private final NetworkTableError error_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableDisconnectedEvent}
     * class.
     * 
     * @param source
     *        The network table that fired the event; must not be {@code null}.
     * @param error
     *        The error that caused the network table to be disconnected or
     *        {@code null} if the network table was disconnected normally.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    public NetworkTableDisconnectedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final INetworkTable source,
        /* @Nullable */
        final NetworkTableError error )
    {
        super( source );

        error_ = error;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the error that caused the network table to be disconnected.
     * 
     * @return The error that caused the network table to be disconnected or
     *         {@code null} if the network table was disconnected normally.
     */
    /* @Nullable */
    public NetworkTableError getError()
    {
        return error_;
    }
}
