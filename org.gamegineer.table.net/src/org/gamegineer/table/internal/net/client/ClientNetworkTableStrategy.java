/*
 * ClientNetworkTableStrategy.java
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
 * Created on Apr 9, 2011 at 10:45:05 PM.
 */

package org.gamegineer.table.internal.net.client;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.NetworkTable;
import org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategy} for client
 * network tables.
 */
@ThreadSafe
public final class ClientNetworkTableStrategy
    extends AbstractNetworkTableStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNetworkTableStrategy}
     * class.
     * 
     * @param networkTable
     *        The network table that hosts the strategy; must not be {@code
     *        null}.
     * @param transportLayerFactory
     *        The transport layer factory used by the strategy; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code networkTable} or {@code transportLayerFactory} is
     *         {@code null}.
     */
    public ClientNetworkTableStrategy(
        /* @NonNull */
        final NetworkTable networkTable,
        /* @NonNull */
        final ITransportLayerFactory transportLayerFactory )
    {
        super( networkTable, transportLayerFactory );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy#createTransportLayer(org.gamegineer.table.internal.net.transport.ITransportLayerFactory)
     */
    @Override
    protected ITransportLayer createTransportLayer(
        final ITransportLayerFactory transportLayerFactory )
    {
        assertArgumentNotNull( transportLayerFactory, "transportLayerFactory" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        return transportLayerFactory.createActiveTransportLayer( new ITransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new RemoteServerTableGateway( ClientNetworkTableStrategy.this );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void transportLayerDisconnected()
            {
                getNetworkTable().disconnect();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#playerConnected(java.lang.String, org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    public void playerConnected(
        final String playerName,
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$

        throw new AssertionError( "not implemented" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#playerDisconnected(java.lang.String)
     */
    @Override
    public void playerDisconnected(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$

        throw new AssertionError( "not implemented" ); //$NON-NLS-1$
    }
}
