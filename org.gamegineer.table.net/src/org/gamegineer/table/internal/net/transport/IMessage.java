/*
 * IMessage.java
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
 * Created on Apr 8, 2011 at 9:57:53 PM.
 */

package org.gamegineer.table.internal.net.transport;

import java.io.Serializable;

/**
 * A network protocol message.
 * 
 * <p>
 * Messages are divided into three classes:
 * </p>
 * 
 * <ol>
 * <li>
 * Events
 * <p>
 * An event is an unsolicited message sent without any expectation of a
 * response. The most-significant bit of their identifier is clear, indicating
 * they are unsolicited. Their tag is set to {@link #NO_TAG}.
 * </p>
 * </li>
 * <li>
 * Requests
 * <p>
 * A request is an unsolicited message sent with the expectation of a response.
 * The most-significant bit of their identifier is clear, indicating they are
 * unsolicited. Their tag is set to any value from {@link #MINIMUM_TAG} to
 * {@link #MAXIMUM_TAG}, inclusive.
 * </p>
 * </li>
 * <li>
 * Responses
 * <p>
 * A response is a solicited message sent in reply to a request. The
 * most-significant bit of their identifier is set, indicating they are
 * solicited. Their tag is set to the tag of their corresponding request.
 * </p>
 * </li>
 * </ol>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMessage
    extends Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The maximum message tag value. */
    public final int MAXIMUM_TAG = 0x3FF;

    /** The minimum message tag value. */
    public final int MINIMUM_TAG = 0x001;

    /** The message tag used to indicate no tag is present. */
    public final int NO_TAG = 0;

    /** The message identifier mask used to indicate the message is solicited. */
    public final int SOLICITED_MASK = 0x80000000;


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message identifier.
     * 
     * @return The message identifier.
     */
    public int getId();

    /**
     * Gets the message tag.
     * 
     * @return The message tag.
     */
    public int getTag();

    /**
     * Sets the message tag.
     * 
     * @param tag
     *        The message tag.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code tag} is less than {@link #MINIMUM_TAG} or greater than
     *         {@link #MAXIMUM_TAG} or not equal to {@link #NO_TAG}.
     */
    public void setTag(
        int tag );
}
