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

package org.gamegineer.table.internal.net.transport.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.IMessage;

/**
 * Superclass for all implementations of {@link IMessage}.
 */
@NotThreadSafe
public abstract class AbstractMessage
    implements IMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5994031588875266119L;

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

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#getId()
     */
    public final int getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#getTag()
     */
    public final int getTag()
    {
        return tag_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#setTag(int)
     */
    public final void setTag(
        final int tag )
    {
        assertArgumentLegal( (tag == NO_TAG) || (tag >= MIN_TAG) && (tag <= MAX_TAG), "tag" ); //$NON-NLS-1$

        tag_ = tag;
    }
}
