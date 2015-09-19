/*
 * TableNetworkDisconnectedEvent.java
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
 * Created on Apr 30, 2011 at 10:10:34 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An event used to notify listeners that the table network has been
 * disconnected.
 */
@ThreadSafe
public final class TableNetworkDisconnectedEvent
    extends TableNetworkEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -526834421408764189L;

    /**
     * The error that caused the table network to be disconnected or
     * {@code null} if the table network was disconnected normally.
     */
    private final @Nullable TableNetworkError error_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkDisconnectedEvent}
     * class.
     * 
     * @param source
     *        The table network that fired the event; must not be {@code null}.
     * @param error
     *        The error that caused the table network to be disconnected or
     *        {@code null} if the table network was disconnected normally.
     */
    public TableNetworkDisconnectedEvent(
        final ITableNetwork source,
        final @Nullable TableNetworkError error )
    {
        super( source );

        error_ = error;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the error that caused the table network to be disconnected.
     * 
     * @return The error that caused the table network to be disconnected or
     *         {@code null} if the table network was disconnected normally.
     */
    public @Nullable TableNetworkError getError()
    {
        return error_;
    }
}
