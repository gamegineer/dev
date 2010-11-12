/*
 * NetworkTableEventDelegate.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 9, 2010 at 10:29:48 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.net.INetworkTable;
import org.gamegineer.table.net.INetworkTableEvent;

/**
 * An implementation of {@link org.gamegineer.table.net.INetworkTableEvent} to
 * which implementations of {@link org.gamegineer.table.net.NetworkTableEvent}
 * can delegate their behavior.
 */
@Immutable
final class NetworkTableEventDelegate
    implements INetworkTableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table that fired the event. */
    private final INetworkTable networkTable_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableEventDelegate}
     * class.
     * 
     * @param networkTable
     *        The network table that fired the event; must not be {@code null}.
     */
    NetworkTableEventDelegate(
        /* @NonNull */
        final INetworkTable networkTable )
    {
        assert networkTable != null;

        networkTable_ = networkTable;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.INetworkTableEvent#getNetworkTable()
     */
    @Override
    public INetworkTable getNetworkTable()
    {
        return networkTable_;
    }
}
