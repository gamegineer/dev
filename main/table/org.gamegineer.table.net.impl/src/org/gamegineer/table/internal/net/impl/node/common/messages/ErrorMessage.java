/*
 * ErrorMessage.java
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
 * Created on Apr 28, 2011 at 10:23:26 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message sent by one peer to another to indicate an error occurred.
 */
@NotThreadSafe
public final class ErrorMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8612976594830899887L;

    /**
     * The error that occurred.
     * 
     * @serial The error that occurred.
     */
    private TableNetworkError error_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ErrorMessage} class.
     */
    public ErrorMessage()
    {
        error_ = TableNetworkError.UNSPECIFIED_ERROR;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the error that occurred.
     * 
     * @return The error that occurred.
     */
    public TableNetworkError getError()
    {
        return error_;
    }

    /**
     * Sets the error that occurred.
     * 
     * @param error
     *        The error that occurred.
     */
    public void setError(
        final TableNetworkError error )
    {
        error_ = error;
    }
}
