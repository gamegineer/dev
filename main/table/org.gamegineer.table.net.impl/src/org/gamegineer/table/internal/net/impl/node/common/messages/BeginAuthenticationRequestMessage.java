/*
 * BeginAuthenticationRequestMessage.java
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
 * Created on Mar 12, 2011 at 9:09:06 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;

/**
 * A message sent by a server to a client to request an authentication
 * handshake.
 */
@NotThreadSafe
public final class BeginAuthenticationRequestMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3655289502300758295L;

    /**
     * The challenge.
     * 
     * @serial The challenge.
     */
    private byte[] challenge_;

    /**
     * The salt used to generate the shared secret key.
     * 
     * @serial The salt used to generate the shared secret key.
     */
    private byte[] salt_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code BeginAuthenticationRequestMessage} class.
     */
    public BeginAuthenticationRequestMessage()
    {
        challenge_ = new byte[ 1 ];
        salt_ = new byte[ 1 ];
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the challenge.
     * 
     * @return The challenge. The returned value is a direct reference to the
     *         field and must not be modified.
     */
    public byte[] getChallenge()
    {
        return challenge_;
    }

    /**
     * Gets the salt used to generate the shared secret key.
     * 
     * @return The salt used to generate the shared secret key. The returned
     *         value is a direct reference to the field and must not be
     *         modified.
     */
    public byte[] getSalt()
    {
        return salt_;
    }

    /**
     * Sets the challenge.
     * 
     * @param challenge
     *        The challenge. No copy is made of this value and it must not be
     *        modified at a later time.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code challenge} is empty.
     */
    public void setChallenge(
        final byte[] challenge )
    {
        assertArgumentLegal( challenge.length != 0, "challenge", NonNlsMessages.BeginAuthenticationRequestMessage_setChallenge_empty ); //$NON-NLS-1$

        challenge_ = challenge;
    }

    /**
     * Sets the salt used to generate the shared secret key.
     * 
     * @param salt
     *        The salt used to generate the shared secret key. No copy is made
     *        of this value and it must not be modified at a later time.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code salt} is empty.
     */
    public void setSalt(
        final byte[] salt )
    {
        assertArgumentLegal( salt.length != 0, "salt", NonNlsMessages.BeginAuthenticationRequestMessage_setSalt_empty ); //$NON-NLS-1$

        salt_ = salt;
    }
}
