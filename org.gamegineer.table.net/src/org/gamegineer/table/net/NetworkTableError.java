/*
 * NetworkTableError.java
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
 * Created on Apr 28, 2011 at 10:22:27 PM.
 */

package org.gamegineer.table.net;

/**
 * Enumerates all possible network table errors.
 */
public enum NetworkTableError
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /** Authentication failed. */
    AUTHENTICATION_FAILED,

    /** The player name is already registered. */
    DUPLICATE_PLAYER_NAME,

    /**
     * The network connection state is illegal for the requested operation.
     * 
     * <p>
     * For example, a connection request was made, but the network is already
     * connected.
     * </p>
     */
    ILLEGAL_CONNECTION_STATE,

    /** An operation was interrupted waiting for completion. */
    INTERRUPTED,

    /** An operation timed out waiting for completion. */
    TIME_OUT,

    /** An error occurred in the transport layer. */
    TRANSPORT_ERROR,

    /** The received message is not expected at this time. */
    UNEXPECTED_MESSAGE,

    /** The peer unexpectedly terminated the connection. */
    UNEXPECTED_PEER_TERMINATION,

    /** The received message has no registered handler. */
    UNHANDLED_MESSAGE,

    /** An unspecified error occurred. */
    UNSPECIFIED_ERROR,

    /** The received message is unknown. */
    UNKNOWN_MESSAGE,

    /** The server does not support the client protocol version. */
    UNSUPPORTED_PROTOCOL_VERSION;
}
