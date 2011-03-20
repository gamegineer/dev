/*
 * EndAuthenticationMessage.java
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
 * Created on Mar 12, 2011 at 9:09:22 PM.
 */

package org.gamegineer.table.internal.net.messages;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message sent by a server to a client to complete an authentication
 * handshake.
 */
@NotThreadSafe
public final class EndAuthenticationMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3711359984148117329L;

    /** The message identifier. */
    public static final int ID = 0x0000000A;

    /**
     * The exception that occurred during authentication or {@code null} if no
     * error occurred.
     * 
     * @serial The exception that occurred during authentication.
     */
    private NetworkTableException exception_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EndAuthenticationMessage} class.
     */
    public EndAuthenticationMessage()
    {
        super( ID );

        exception_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the exception that occurred during authentication.
     * 
     * @return The exception that occurred during authentication or {@code null}
     *         if no error occurred.
     */
    /* @Nullable */
    public NetworkTableException getException()
    {
        return exception_;
    }

    /**
     * Sets the exception that occurred during authentication.
     * 
     * @param exception
     *        The exception that occurred during authentication or {@code null}
     *        if no error occurred.
     */
    public void setException(
        /* @Nullable */
        final NetworkTableException exception )
    {
        exception_ = exception;
    }
}
