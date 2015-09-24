/*
 * INodeFactory.java
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
 * Created on May 6, 2011 at 11:38:45 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import org.gamegineer.table.internal.net.impl.ITableNetworkController;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A factory for creating local table network nodes.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INodeFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new local client table network node.
     * 
     * @param tableNetworkController
     *        The table network controller.
     * 
     * @return The control interface for the new local client table network
     *         node.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the table network node cannot be created.
     */
    public INodeController createClientNode(
        ITableNetworkController tableNetworkController )
        throws TableNetworkException;

    /**
     * Creates a new local server table network node.
     * 
     * @param tableNetworkController
     *        The table network controller.
     * 
     * @return The control interface for the new local server table network
     *         node.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the table network node cannot be created.
     */
    public INodeController createServerNode(
        ITableNetworkController tableNetworkController )
        throws TableNetworkException;
}
