/*
 * TableNetworkTest.java
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
 * Created on Nov 11, 2010 at 10:47:20 PM.
 */

package org.gamegineer.table.internal.net;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.TableNetwork} class.
 */
public final class TableNetworkTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkTest} class.
     */
    public TableNetworkTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code TableNetwork(INodeFactory, ITransportLayerFactory)}
     * constructor throws an exception when passed a {@code null} node factory.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromNodeFactoryAndTransportLayerFactory_NodeFactory_Null()
    {
        new TableNetwork( null, EasyMock.createMock( ITransportLayerFactory.class ) );
    }

    /**
     * Ensures the {@code TableNetwork(INodeFactory, ITransportLayerFactory)}
     * constructor throws an exception when passed a {@code null} transport
     * layer factory.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromNodeFactoryAndTransportLayerFactory_TransportLayerFactory_Null()
    {
        new TableNetwork( EasyMock.createMock( INodeFactory.class ), null );
    }
}
