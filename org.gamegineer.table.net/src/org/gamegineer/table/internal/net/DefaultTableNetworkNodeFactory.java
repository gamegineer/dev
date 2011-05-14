/*
 * DefaultTableNetworkNodeFactory.java
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
import org.gamegineer.table.internal.net.client.ClientTableNetworkNode;
import org.gamegineer.table.internal.net.server.ServerTableNetworkNode;

/**
 * Default implementation of
 * {@link org.gamegineer.table.internal.net.ITableNetworkNodeFactory}.
 */
@Immutable
public class DefaultTableNetworkNodeFactory
    implements ITableNetworkNodeFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultTableNetworkNodeFactory}
     * class.
     */
    public DefaultTableNetworkNodeFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation creates an instance of {@link ClientTableNetworkNode}
     * .
     * 
     * @see org.gamegineer.table.internal.net.ITableNetworkNodeFactory#createClientTableNetworkNode(org.gamegineer.table.internal.net.ITableNetworkController)
     */
    @Override
    public ITableNetworkNodeController createClientTableNetworkNode(
        final ITableNetworkController tableNetworkController )
    {
        return new ClientTableNetworkNode( tableNetworkController );
    }

    /**
     * This implementation creates an instance of {@link ServerTableNetworkNode}
     * .
     * 
     * @see org.gamegineer.table.internal.net.ITableNetworkNodeFactory#createServerTableNetworkNode(org.gamegineer.table.internal.net.ITableNetworkController)
     */
    @Override
    public ITableNetworkNodeController createServerTableNetworkNode(
        final ITableNetworkController tableNetworkController )
    {
        return new ServerTableNetworkNode( tableNetworkController );
    }
}
