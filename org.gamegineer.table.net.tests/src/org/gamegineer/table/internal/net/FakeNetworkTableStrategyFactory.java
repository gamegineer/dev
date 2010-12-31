/*
 * FakeNetworkTableStrategyFactory.java
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
 * Created on Dec 30, 2010 at 12:13:00 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;

/**
 * A fake network table strategy factory.
 */
@Immutable
final class FakeNetworkTableStrategyFactory
    extends AbstractNetworkTableStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeNetworkTableStrategyFactory}
     * class.
     */
    FakeNetworkTableStrategyFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategyFactory#createClientNetworkTableStrategy(org.gamegineer.table.internal.net.NetworkTable)
     */
    @Override
    AbstractNetworkTableStrategy createClientNetworkTableStrategy(
        final NetworkTable networkTable )
    {
        assert networkTable != null;

        return new FakeClientNetworkTableStrategy( networkTable );
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategyFactory#createServerNetworkTableStrategy(org.gamegineer.table.internal.net.NetworkTable)
     */
    @Override
    AbstractNetworkTableStrategy createServerNetworkTableStrategy(
        final NetworkTable networkTable )
    {
        assert networkTable != null;

        return new FakeServerNetworkTableStrategy( networkTable );
    }
}
