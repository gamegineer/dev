/*
 * InternalNetworkTableEvent.java
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
 * Created on Nov 9, 2010 at 10:29:22 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.net.INetworkTable;
import org.gamegineer.table.net.INetworkTableEvent;
import org.gamegineer.table.net.NetworkTableEvent;

/**
 * Implementation of {@link org.gamegineer.table.net.NetworkTableEvent}.
 */
@ThreadSafe
final class InternalNetworkTableEvent
    extends NetworkTableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 6657768983068461538L;

    /**
     * The network table event implementation to which all behavior is
     * delegated.
     */
    private final INetworkTableEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalNetworkTableEvent}
     * class.
     * 
     * @param delegate
     *        The network table event implementation to which all behavior is
     *        delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalNetworkTableEvent(
        /* @NonNull */
        final INetworkTableEvent delegate )
    {
        super( delegate.getNetworkTable() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalNetworkTableEvent} class.
     * 
     * @param networkTable
     *        The network table that fired the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalNetworkTableEvent} class;
     *         never {@code null}.
     */
    /* @NonNull */
    static InternalNetworkTableEvent createNetworkTableEvent(
        /* @NonNull */
        final INetworkTable networkTable )
    {
        assert networkTable != null;

        return new InternalNetworkTableEvent( new NetworkTableEventDelegate( networkTable ) );
    }

    /*
     * @see org.gamegineer.table.net.INetworkTableEvent#getNetworkTable()
     */
    @Override
    public INetworkTable getNetworkTable()
    {
        return delegate_.getNetworkTable();
    }
}
