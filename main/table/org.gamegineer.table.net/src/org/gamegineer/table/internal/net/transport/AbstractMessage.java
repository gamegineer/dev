/*
 * AbstractMessage.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.transport;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.NotThreadSafe;

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

    /** The message correlation identifier. */
    private int correlationId_;

    /** The message identifier. */
    private int id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessage} class.
     */
    protected AbstractMessage()
    {
        correlationId_ = NULL_CORRELATION_ID;
        id_ = MINIMUM_ID;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#getCorrelationId()
     */
    @Override
    public final int getCorrelationId()
    {
        return correlationId_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#getId()
     */
    @Override
    public final int getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#setCorrelationId(int)
     */
    @Override
    public final void setCorrelationId(
        final int correlationId )
    {
        assertArgumentLegal( (correlationId == NULL_CORRELATION_ID) || ((correlationId >= MINIMUM_ID) && (correlationId <= MAXIMUM_ID)), "correlationId" ); //$NON-NLS-1$

        correlationId_ = correlationId;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IMessage#setId(int)
     */
    @Override
    public final void setId(
        final int id )
    {
        assertArgumentLegal( (id >= MINIMUM_ID) && (id <= MAXIMUM_ID), "id" ); //$NON-NLS-1$

        id_ = id;
    }
}
