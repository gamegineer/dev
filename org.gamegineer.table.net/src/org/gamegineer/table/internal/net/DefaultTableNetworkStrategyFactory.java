/*
 * DefaultTableNetworkStrategyFactory.java
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
 * Created on May 6, 2011 at 11:55:41 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.client.ClientTableNetworkStrategy;
import org.gamegineer.table.internal.net.server.ServerTableNetworkStrategy;

/**
 * Default implementation of
 * {@link org.gamegineer.table.internal.net.ITableNetworkStrategyFactory}.
 */
@Immutable
public class DefaultTableNetworkStrategyFactory
    implements ITableNetworkStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * DefaultTableNetworkStrategyFactory} class.
     */
    public DefaultTableNetworkStrategyFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation creates an instance of
     * {@link ClientTableNetworkStrategy}.
     * 
     * @see org.gamegineer.table.internal.net.ITableNetworkStrategyFactory#createClientTableNetworkStrategy(org.gamegineer.table.internal.net.ITableNetworkStrategyContext)
     */
    @Override
    public ITableNetworkStrategy createClientTableNetworkStrategy(
        final ITableNetworkStrategyContext context )
    {
        return new ClientTableNetworkStrategy( context );
    }

    /**
     * This implementation creates an instance of
     * {@link ServerTableNetworkStrategy}.
     * 
     * @see org.gamegineer.table.internal.net.ITableNetworkStrategyFactory#createServerTableNetworkStrategy(org.gamegineer.table.internal.net.ITableNetworkStrategyContext)
     */
    @Override
    public ITableNetworkStrategy createServerTableNetworkStrategy(
        final ITableNetworkStrategyContext context )
    {
        return new ServerTableNetworkStrategy( context );
    }
}
