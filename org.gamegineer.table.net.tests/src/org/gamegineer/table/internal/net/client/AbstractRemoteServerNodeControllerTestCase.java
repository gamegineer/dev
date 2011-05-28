/*
 * AbstractRemoteServerNodeControllerTestCase.java
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
 * Created on Apr 23, 2011 at 9:18:27 PM.
 */

package org.gamegineer.table.internal.net.client;

import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.common.AbstractRemoteNodeControllerTestCase;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.client.IRemoteServerNodeController}
 * interface.
 * 
 * @param <T>
 *        The type of the remote server node controller.
 */
public abstract class AbstractRemoteServerNodeControllerTestCase<T extends IRemoteServerNodeController>
    extends AbstractRemoteNodeControllerTestCase<T, IClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractRemoteServerNodeControllerTestCase} class.
     */
    protected AbstractRemoteServerNodeControllerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteNodeControllerTestCase#createLocalNode(org.easymock.IMocksControl)
     */
    @Override
    protected final IClientNode createLocalNode(
        final IMocksControl mocksControl )
    {
        return mocksControl.createMock( IClientNode.class );
    }
}
