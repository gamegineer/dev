/*
 * FakeServerNetworkTableStrategy.java
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
 * Created on Dec 30, 2010 at 12:12:48 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.net.INetworkTableConfiguration;

/**
 * A fake server network table strategy.
 */
@ThreadSafe
final class FakeServerNetworkTableStrategy
    extends AbstractNetworkTableStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeServerNetworkTableStrategy}
     * class.
     * 
     * @param networkTable
     *        The network table that hosts the strategy; must not be {@code
     *        null}.
     */
    FakeServerNetworkTableStrategy(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        super( networkTable );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategy#connect(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    void connect(
        final INetworkTableConfiguration configuration )
    {
        assert configuration != null;
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategy#disconnect()
     */
    @Override
    void disconnect()
    {
        // do nothing
    }
}
