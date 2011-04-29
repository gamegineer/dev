/*
 * HelloRequestMessage.java
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
 * Created on Mar 11, 2011 at 10:15:55 PM.
 */

package org.gamegineer.table.internal.net.transport.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a client to a server to request begin the handshake
 * necessary to connect to the server.
 */
@NotThreadSafe
public final class HelloRequestMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5756672104592548380L;

    /**
     * The highest protocol version supported by the client.
     * 
     * @serial The highest protocol version supported by the client.
     */
    private int supportedProtocolVersion_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessage} class.
     */
    public HelloRequestMessage()
    {
        supportedProtocolVersion_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the highest protocol version supported by the client.
     * 
     * @return The highest protocol version supported by the client.
     */
    public int getSupportedProtocolVersion()
    {
        return supportedProtocolVersion_;
    }

    /**
     * Sets the highest protocol version supported by the client.
     * 
     * @param supportedProtocolVersion
     *        The highest protocol version supported by the client.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code supportedProtocolVersion} is negative.
     */
    public void setSupportedProtocolVersion(
        final int supportedProtocolVersion )
    {
        assertArgumentLegal( supportedProtocolVersion >= 0, "supportedProtocolVersion" ); //$NON-NLS-1$

        supportedProtocolVersion_ = supportedProtocolVersion;
    }
}
