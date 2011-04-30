/*
 * HelloResponseMessage.java
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
 * Created on Mar 11, 2011 at 10:16:05 PM.
 */

package org.gamegineer.table.internal.net.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a server to a client in response to a client's request to
 * begin the handshake necessary to connect to the server.
 */
@NotThreadSafe
public final class HelloResponseMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -2723602157514908503L;

    /**
     * The protocol version chosen by the server.
     * 
     * @serial The protocol version chosen by the server.
     */
    private int chosenProtocolVersion_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloResponseMessage} class.
     */
    public HelloResponseMessage()
    {
        chosenProtocolVersion_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the protocol version chosen by the server.
     * 
     * @return The protocol version chosen by the server.
     */
    public int getChosenProtocolVersion()
    {
        return chosenProtocolVersion_;
    }

    /**
     * Sets the protocol version chosen by the server.
     * 
     * @param chosenProtocolVersion
     *        The protocol version chosen by the server.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code chosenProtocolVersion} is negative.
     */
    public void setChosenProtocolVersion(
        final int chosenProtocolVersion )
    {
        assertArgumentLegal( chosenProtocolVersion >= 0, "chosenProtocolVersion" ); //$NON-NLS-1$

        chosenProtocolVersion_ = chosenProtocolVersion;
    }
}
