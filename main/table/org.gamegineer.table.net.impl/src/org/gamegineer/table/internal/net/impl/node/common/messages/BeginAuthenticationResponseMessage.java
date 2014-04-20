/*
 * BeginAuthenticationResponseMessage.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Mar 12, 2011 at 9:09:16 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;

/**
 * A message sent by a client to a server in response to a server's request to
 * begin the authentication handshake.
 */
@NotThreadSafe
public final class BeginAuthenticationResponseMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8921398762357752322L;

    /**
     * The player name.
     * 
     * @serial The player name.
     */
    private String playerName_;

    /**
     * The challenge response.
     * 
     * @serial The challenge response.
     */
    private byte[] response_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code BeginAuthenticationResponseMessage} class.
     */
    public BeginAuthenticationResponseMessage()
    {
        playerName_ = ""; //$NON-NLS-1$
        response_ = new byte[ 1 ];
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the player name.
     * 
     * @return The player name; never {@code null}.
     */
    public String getPlayerName()
    {
        return playerName_;
    }

    /**
     * Gets the challenge response.
     * 
     * @return The challenge response; never {@code null}. The returned value is
     *         a direct reference to the field and must not be modified.
     */
    public byte[] getResponse()
    {
        return response_;
    }

    /**
     * Sets the player name.
     * 
     * @param playerName
     *        The player name; must not be {@code null}.
     */
    public void setPlayerName(
        final String playerName )
    {
        playerName_ = playerName;
    }

    /**
     * Sets the challenge response.
     * 
     * @param response
     *        The challenge response; must not be {@code null}. No copy is made
     *        of this value and it must not be modified at a later time.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code response} is empty.
     */
    public void setResponse(
        final byte[] response )
    {
        assertArgumentLegal( response.length != 0, "response", NonNlsMessages.BeginAuthenticationResponseMessage_setResponse_empty ); //$NON-NLS-1$

        response_ = response;
    }
}
