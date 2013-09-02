/*
 * IMessage.java
 * Copyright 2008-2011 Gamegineer contributors and others.
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
 * Created on Apr 8, 2011 at 9:57:53 PM.
 */

package org.gamegineer.table.internal.net.transport;

import java.io.Serializable;

/**
 * A network protocol message.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMessage
    extends Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The maximum message identifier. */
    public final int MAXIMUM_ID = 0xFF;

    /** The minimum message identifier value. */
    public final int MINIMUM_ID = 0x01;

    /**
     * The null message correlation identifier used to indicate a message was
     * not sent in reply to any other message.
     */
    public final int NULL_CORRELATION_ID = 0x00;


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message correlation identifier.
     * 
     * @return The message correlation identifier or
     *         {@link #NULL_CORRELATION_ID} if this message was not sent in
     *         reply to another message.
     */
    public int getCorrelationId();

    /**
     * Gets the message identifier.
     * 
     * @return The message identifier.
     */
    public int getId();

    /**
     * Sets the message correlation identifier.
     * 
     * @param correlationId
     *        The message correlation identifier.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code correlationId} is less than {@link #MINIMUM_ID} or
     *         greater than {@link #MAXIMUM_ID} or not equal to
     *         {@link #NULL_CORRELATION_ID} .
     */
    public void setCorrelationId(
        int correlationId );

    /**
     * Sets the message identifier.
     * 
     * @param id
     *        The message identifier.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code id} is less than {@link #MINIMUM_ID} or greater than
     *         {@link #MAXIMUM_ID}.
     */
    public void setId(
        int id );
}
