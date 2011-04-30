/*
 * AbstractRemoteClientTableGatewayTestCase.java
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
 * Created on Apr 23, 2011 at 9:23:06 PM.
 */

package org.gamegineer.table.internal.net.server;

import org.gamegineer.table.internal.net.common.AbstractRemoteTableGatewayTestCase;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.server.IRemoteClientTableGateway}
 * interface.
 * 
 * @param <T>
 *        The type of the remote client table gateway.
 */
public abstract class AbstractRemoteClientTableGatewayTestCase<T extends IRemoteClientTableGateway>
    extends AbstractRemoteTableGatewayTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractRemoteClientTableGatewayTestCase} class.
     */
    protected AbstractRemoteClientTableGatewayTestCase()
    {
        super();
    }
}