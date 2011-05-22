/*
 * ServerTableNetworkNode.java
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
import java.util.ArrayList;
import java.util.Collection;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.ITableProxy;
import org.gamegineer.table.internal.net.common.AbstractTableNetworkNode;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;

/**
 * A server node in a table network.
 */
@ThreadSafe
public final class ServerTableNetworkNode
    extends AbstractTableNetworkNode
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerTableNetworkNode} class.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    public ServerTableNetworkNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        super( tableNetworkController );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
    {
        assert Thread.holdsLock( getLock() );

        return getTableNetworkController().getTransportLayerFactory().createPassiveTransportLayer( new AbstractTransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new RemoteClientTableProxy( ServerTableNetworkNode.this );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#disconnecting()
     */
    @Override
    protected void disconnecting()
    {
        assert Thread.holdsLock( getLock() );

        super.disconnecting();

        // TODO: need to gracefully shut down all remote clients : GoodbyeMessage
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#getPlayers()
     * @see org.gamegineer.table.internal.net.ITableNetworkNodeController#getPlayers()
     */
    @Override
    public Collection<String> getPlayers()
    {
        final Collection<ITableProxy> tableProxies = getTableProxies();
        final Collection<String> players = new ArrayList<String>( tableProxies.size() );
        for( final ITableProxy tableProxy : tableProxies )
        {
            players.add( tableProxy.getPlayerName() );
        }
        return players;
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<String> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        throw new AssertionError( "should never be called on a server node" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#tableProxyAdded(org.gamegineer.table.internal.net.ITableProxy)
     */
    @Override
    protected void tableProxyAdded(
        final ITableProxy tableProxy )
    {
        assertArgumentNotNull( tableProxy, "tableProxy" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.tableProxyAdded( tableProxy );

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "player '%s' has connected", tableProxy.getPlayerName() ) ); //$NON-NLS-1$

        final Collection<String> players = getPlayers();
        for( final ITableProxy otherTableProxy : getTableProxies() )
        {
            otherTableProxy.setPlayers( players );
        }

        getTableNetworkController().playersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#tableProxyRemoved(org.gamegineer.table.internal.net.ITableProxy)
     */
    @Override
    protected void tableProxyRemoved(
        final ITableProxy tableProxy )
    {
        assertArgumentNotNull( tableProxy, "tableProxy" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.tableProxyRemoved( tableProxy );

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "player '%s' has disconnected", tableProxy.getPlayerName() ) ); //$NON-NLS-1$

        final Collection<String> players = getPlayers();
        for( final ITableProxy otherTableProxy : getTableProxies() )
        {
            otherTableProxy.setPlayers( players );
        }

        getTableNetworkController().playersUpdated();
    }
}
