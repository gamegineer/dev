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
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMessage
    extends Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The maximum message tag value. */
    public final int MAX_TAG = 0x3FF;

    /** The minimum message tag value. */
    public final int MIN_TAG = 0x001;

    /** The message tag used to indicate no tag is present. */
    public final int NO_TAG = 0;


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
     *         If {@code tag} is less than {@link #MIN_TAG} or greater than
     *         {@link #MAX_TAG} or not equal to {@link #NO_TAG}.
     */
    public void setTag(
        int tag );
}
