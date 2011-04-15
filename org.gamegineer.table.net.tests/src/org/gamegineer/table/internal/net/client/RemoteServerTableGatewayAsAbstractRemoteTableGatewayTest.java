/*
 * RemoteServerTableGatewayAsAbstractRemoteTableGatewayTest.java
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
 * Created on Apr 15, 2011 at 12:02:09 AM.
 */

package org.gamegineer.table.internal.net.client;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.AbstractAbstractRemoteTableGatewayTestCase;
import org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.client.RemoteServerTableGateway}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public final class RemoteServerTableGatewayAsAbstractRemoteTableGatewayTest
    extends AbstractAbstractRemoteTableGatewayTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * RemoteServerTableGatewayAsAbstractRemoteTableGatewayTest} class.
     */
    public RemoteServerTableGatewayAsAbstractRemoteTableGatewayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractAbstractRemoteTableGatewayTestCase#createRemoteTableGateway()
     */
    @Override
    protected AbstractRemoteTableGateway createRemoteTableGateway()
    {
        return new RemoteServerTableGateway( EasyMock.createMock( ITableGatewayContext.class ) );
    }
}
