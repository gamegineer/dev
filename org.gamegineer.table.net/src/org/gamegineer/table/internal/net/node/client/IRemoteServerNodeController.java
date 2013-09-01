/*
 * IRemoteServerNodeController.java
 * Copyright 2008-2011 Gamegineer contributors and others.
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
 * Created on Apr 23, 2011 at 8:44:12 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import org.gamegineer.table.internal.net.node.IRemoteNodeController;

/**
 * The control interface for a remote server node.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteServerNodeController
    extends IRemoteNodeController<IClientNode>
{
    // ======================================================================
    // Methods
    // ======================================================================
}
