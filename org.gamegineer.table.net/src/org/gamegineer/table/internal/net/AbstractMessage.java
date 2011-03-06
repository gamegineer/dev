/*
 * AbstractMessage.java
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
 * Created on Feb 24, 2011 at 8:09:45 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;

/**
 * A network table protocol message.
 */
@NotThreadSafe
public abstract class AbstractMessage
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5994031588875266119L;

    /** The maximum message tag value. */
    public static final int MAX_TAG = 0x3FF;

    /** The minimum message tag value. */
    public static final int MIN_TAG = 0x001;

    /** The message tag used to indicate no tag is present. */
    public static final int NO_TAG = 0;

    /** The message identifier. */
    private int id_;

    /** The message tag. */
    private int tag_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessage} class.
     * 
     * @param id
     *        The message identifier.
     */
    protected AbstractMessage(
        final int id )
    {
        id_ = id;
        tag_ = NO_TAG;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message identifier.
     * 
     * @return The message identifier.
     */
    public final int getId()
    {
        return id_;
    }

    /**
     * Gets the message tag.
     * 
     * @return The message tag.
     */
    public final int getTag()
    {
        return tag_;
    }

    /**
     * Sets the message tag.
     * 
     * @param tag
     *        The message tag.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code tag} is less than zero or greater than {@link #MAX_TAG}
     *         .
     */
    public final void setTag(
        final int tag )
    {
        assertArgumentLegal( (tag >= 0) && (tag <= MAX_TAG), "tag" ); //$NON-NLS-1$

        tag_ = tag;
    }
}
