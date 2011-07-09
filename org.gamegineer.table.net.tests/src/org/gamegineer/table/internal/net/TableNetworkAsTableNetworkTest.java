/*
 * TableNetworkAsTableNetworkTest.java
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
 * Created on Nov 9, 2010 at 10:58:36 PM.
 */

package org.gamegineer.table.internal.net;

import java.lang.reflect.Method;
import org.gamegineer.table.internal.net.node.DefaultNodeFactory;
import org.gamegineer.table.internal.net.node.INodeController;
import org.gamegineer.table.internal.net.node.client.ClientNode;
import org.gamegineer.table.internal.net.transport.fake.FakeTransportLayerFactory;
import org.gamegineer.table.net.AbstractTableNetworkTestCase;
import org.gamegineer.table.net.ITableNetwork;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.TableNetwork} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.net.ITableNetwork} interface.
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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.AbstractTableNetworkTestCase#createTableNetwork()
     */
    @Override
    protected ITableNetwork createTableNetwork()
    {
        return new TableNetwork( //
            new DefaultNodeFactory()
            {
                @Override
                public INodeController createClientNode(
                    final ITableNetworkController tableNetworkController )
                {
                    return new ClientNode( tableNetworkController, false );
                }
            }, //
            new FakeTransportLayerFactory() );
    }

    /*
     * @see org.gamegineer.table.net.AbstractTableNetworkTestCase#fireTableNetworkPlayersUpdatedEvent(org.gamegineer.table.net.ITableNetwork)
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
