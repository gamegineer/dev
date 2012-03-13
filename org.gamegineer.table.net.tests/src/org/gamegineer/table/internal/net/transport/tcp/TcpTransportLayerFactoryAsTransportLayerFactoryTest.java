/*
 * TcpTransportLayerFactoryAsTransportLayerFactoryTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 15, 2011 at 11:56:00 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import org.gamegineer.table.internal.net.transport.AbstractTransportLayerFactoryTestCase;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.tcp.TcpTransportLayerFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.transport.ITransportLayerFactory}
 * interface.
 */
public final class TcpTransportLayerFactoryAsTransportLayerFactoryTest
    extends AbstractTransportLayerFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TcpTransportLayerFactoryAsTransportLayerFactoryTest} class.
     */
    public TcpTransportLayerFactoryAsTransportLayerFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.AbstractTransportLayerFactoryTestCase#createTransportLayerFactory()
     */
    @Override
    protected ITransportLayerFactory createTransportLayerFactory()
    {
        return new TcpTransportLayerFactory();
    }
}
