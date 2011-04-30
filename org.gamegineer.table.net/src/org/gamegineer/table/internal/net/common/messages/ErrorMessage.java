/*
 * ErrorMessage.java
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
 * Created on Apr 28, 2011 at 10:23:26 PM.
 */

package org.gamegineer.table.internal.net.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.AbstractMessage;
import org.gamegineer.table.net.NetworkTableError;

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
    private NetworkTableError error_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ErrorMessage} class.
     */
    public ErrorMessage()
    {
        error_ = NetworkTableError.UNSPECIFIED_ERROR;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the error that occurred.
     * 
     * @return The error that occurred; never {@code null}.
     */
    /* @NonNull */
    public NetworkTableError getError()
    {
        return error_;
    }

    /**
     * Sets the error that occurred.
     * 
     * @param error
     *        The error that occurred; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code error} is {@code null}.
     */
    public void setError(
        /* @NonNull */
        final NetworkTableError error )
    {
        assertArgumentNotNull( error, "error" ); //$NON-NLS-1$

        error_ = error;
    }
}
