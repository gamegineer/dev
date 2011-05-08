/*
 * DefaultNetworkTableStrategyFactory.java
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
import org.gamegineer.table.internal.net.client.ClientNetworkTableStrategy;
import org.gamegineer.table.internal.net.server.ServerNetworkTableStrategy;

/**
 * Default implementation of
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategyFactory}.
 */
@Immutable
public class DefaultNetworkTableStrategyFactory
    implements INetworkTableStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * DefaultNetworkTableStrategyFactory} class.
     */
    public DefaultNetworkTableStrategyFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation creates an instance of
     * {@link ClientNetworkTableStrategy}.
     * 
     * @see org.gamegineer.table.internal.net.INetworkTableStrategyFactory#createClientNetworkTableStrategy(org.gamegineer.table.internal.net.INetworkTableStrategyContext)
     */
    @Override
    public INetworkTableStrategy createClientNetworkTableStrategy(
        final INetworkTableStrategyContext context )
    {
        return new ClientNetworkTableStrategy( context );
    }

    /**
     * This implementation creates an instance of
     * {@link ServerNetworkTableStrategy}.
     * 
     * @see org.gamegineer.table.internal.net.INetworkTableStrategyFactory#createServerNetworkTableStrategy(org.gamegineer.table.internal.net.INetworkTableStrategyContext)
     */
    @Override
    public INetworkTableStrategy createServerNetworkTableStrategy(
        final INetworkTableStrategyContext context )
    {
        return new ServerNetworkTableStrategy( context );
    }
}
