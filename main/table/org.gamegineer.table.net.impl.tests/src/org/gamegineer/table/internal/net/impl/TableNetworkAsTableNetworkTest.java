/*
 * TableNetworkAsTableNetworkTest.java
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
 * Created on Nov 9, 2010 at 10:58:36 PM.
 */

package org.gamegineer.table.internal.net.impl;

import java.lang.reflect.Method;
import org.gamegineer.table.internal.net.impl.node.FakeNodeController;
import org.gamegineer.table.internal.net.impl.node.INodeController;
import org.gamegineer.table.internal.net.impl.node.INodeFactory;
import org.gamegineer.table.internal.net.impl.transport.fake.FakeTransportLayerFactory;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.test.AbstractTableNetworkTestCase;

/**
 * A fixture for testing the {@link TableNetwork} class to ensure it does not
 * violate the contract of the {@link ITableNetwork} interface.
 */
public final class TableNetworkAsTableNetworkTest
    extends AbstractTableNetworkTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkAsTableNetworkTest}
     * class.
     */
    public TableNetworkAsTableNetworkTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.test.AbstractTableNetworkTestCase#createTableNetwork()
     */
    @Override
    protected ITableNetwork createTableNetwork()
    {
        return new TableNetwork( //
            new INodeFactory()
            {
                @Override
                public INodeController createClientNode(
                    final ITableNetworkController tableNetworkController )
                {
                    return new FakeNodeController();
                }

                @Override
                public INodeController createServerNode(
                    final ITableNetworkController tableNetworkController )
                {
                    return new FakeNodeController();
                }
            }, //
            new FakeTransportLayerFactory() );
    }

    /*
     * @see org.gamegineer.table.net.test.AbstractTableNetworkTestCase#fireTableNetworkPlayersUpdatedEvent(org.gamegineer.table.net.ITableNetwork)
     */
    @Override
    protected void fireTableNetworkPlayersUpdatedEvent(
        final ITableNetwork tableNetwork )
    {
        try
        {
            final Method method = TableNetwork.class.getDeclaredMethod( "fireTableNetworkPlayersUpdated" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( tableNetwork );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
