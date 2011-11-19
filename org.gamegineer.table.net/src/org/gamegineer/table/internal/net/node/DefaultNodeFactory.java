/*
 * DefaultNodeFactory.java
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

package org.gamegineer.table.internal.net.node;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.node.client.ClientNode;
import org.gamegineer.table.internal.net.node.server.ServerNode;

/**
 * Default implementation of
 * {@link org.gamegineer.table.internal.net.node.INodeFactory}.
 */
@Immutable
public final class DefaultNodeFactory
    implements INodeFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultNodeFactory} class.
     */
    public DefaultNodeFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.INodeFactory#createClientNode(org.gamegineer.table.internal.net.ITableNetworkController)
     */
    @Override
    public INodeController createClientNode(
        final ITableNetworkController tableNetworkController )
    {
        return new NodeControllerProxy( new ClientNode.Factory().createNode( tableNetworkController ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeFactory#createServerNode(org.gamegineer.table.internal.net.ITableNetworkController)
     */
    @Override
    public INodeController createServerNode(
        final ITableNetworkController tableNetworkController )
    {
        return new NodeControllerProxy( new ServerNode.Factory().createNode( tableNetworkController ) );
    }
}
