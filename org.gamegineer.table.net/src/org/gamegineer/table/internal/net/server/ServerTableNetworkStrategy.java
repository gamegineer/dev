/*
 * ServerTableNetworkStrategy.java
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
 * Created on Apr 9, 2011 at 10:45:14 PM.
 */

package org.gamegineer.table.internal.net.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.ITableNetworkStrategyContext;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.AbstractTableNetworkStrategy;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.ITableNetworkStrategy} for server
 * table networks.
 */
@ThreadSafe
public final class ServerTableNetworkStrategy
    extends AbstractTableNetworkStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerTableNetworkStrategy}
     * class.
     * 
     * @param context
     *        The table network strategy context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public ServerTableNetworkStrategy(
        /* @NonNull */
        final ITableNetworkStrategyContext context )
    {
        super( context );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkStrategy#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
    {
        assert Thread.holdsLock( getLock() );

        return getContext().getTransportLayerFactory().createPassiveTransportLayer( new AbstractTransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new RemoteClientTableGateway( ServerTableNetworkStrategy.this );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkStrategy#disconnecting()
     */
    @Override
    protected void disconnecting()
    {
        assert Thread.holdsLock( getLock() );

        super.disconnecting();

        // TODO: need to gracefully shut down all remote clients : GoodbyeMessage
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkStrategy#tableGatewayAdded(org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    protected void tableGatewayAdded(
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.tableGatewayAdded( tableGateway );

        Loggers.getDefaultLogger().info( Messages.ServerTableNetworkStrategy_playerConnected_playerConnected( tableGateway.getPlayerName() ) );

        // TODO
        //
        // - send PlayerConnected message to all other gateways other than "tableGateway"
        // - send PlayerList message to "tableGateway"
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkStrategy#tableGatewayRemoved(org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    protected void tableGatewayRemoved(
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.tableGatewayRemoved( tableGateway );

        Loggers.getDefaultLogger().info( Messages.ServerTableNetworkStrategy_playerDisconnected_playerDisconnected( tableGateway.getPlayerName() ) );
    }
}
