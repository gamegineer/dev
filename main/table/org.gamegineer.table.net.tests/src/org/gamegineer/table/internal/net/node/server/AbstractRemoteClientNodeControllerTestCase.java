/*
 * AbstractRemoteClientNodeControllerTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Apr 23, 2011 at 9:23:06 PM.
 */

package org.gamegineer.table.internal.net.node.server;

import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.AbstractRemoteNodeControllerTestCase;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IRemoteClientNodeController} interface.
 * 
 * @param <T>
 *        The type of the remote client node controller.
 */
public abstract class AbstractRemoteClientNodeControllerTestCase<T extends IRemoteClientNodeController>
    extends AbstractRemoteNodeControllerTestCase<T, IServerNode, IRemoteClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractRemoteClientNodeControllerTestCase} class.
     */
    protected AbstractRemoteClientNodeControllerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNodeControllerTestCase#createMockLocalNode(org.easymock.IMocksControl)
     */
    @Override
    protected final IServerNode createMockLocalNode(
        final IMocksControl mocksControl )
    {
        return mocksControl.createMock( IServerNode.class );
    }
}