/*
 * ServerNode.java
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

package org.gamegineer.table.internal.net.node.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.AbstractNode;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A server node in a table network.
 */
@ThreadSafe
public final class ServerNode
    extends AbstractNode<IRemoteClientNode>
    implements IServerNode
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The master table for the table network. */
    @GuardedBy( "getLock()" )
    private ITable masterTable_;

    /** The collection of players connected to the table network. */
    @GuardedBy( "getLock()" )
    private final Collection<String> players_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerNode} class.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    public ServerNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        super( tableNetworkController );

        masterTable_ = null;
        players_ = new ArrayList<String>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified player to the table network.
     * 
     * @param playerName
     *        The name of the player to bind to the table network; must not be
     *        {@code null}.
     */
    @GuardedBy( "getLock()" )
    private void bindPlayer(
        /* @NonNull */
        final String playerName )
    {
        assert playerName != null;
        assert Thread.holdsLock( getLock() );

        assert !players_.contains( playerName );
        players_.add( playerName );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has connected", playerName ) ); //$NON-NLS-1$
        notifyPlayersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#connecting()
     */
    @Override
    protected void connecting()
        throws TableNetworkException
    {
        assert Thread.holdsLock( getLock() );

        super.connecting();

        initializeMasterTable();
        bindPlayer( getPlayerName() );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#createTransportLayer()
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
                return new RemoteClientNode( ServerNode.this );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#disconnected()
     */
    @Override
    protected void disconnected()
    {
        assert Thread.holdsLock( getLock() );

        super.disconnected();

        unbindPlayer( getPlayerName() );
        masterTable_ = null;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#dispose()
     */
    @Override
    protected void dispose()
    {
        assert Thread.holdsLock( getLock() );

        players_.clear();

        super.dispose();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
     */
    @Override
    public Collection<String> getPlayers()
    {
        synchronized( getLock() )
        {
            return new ArrayList<String>( players_ );
        }
    }

    /**
     * Initializes the master table for the table network.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If an error occurs.
     */
    private void initializeMasterTable()
        throws TableNetworkException
    {
        final ITable masterTable;
        try
        {
            masterTable = TableFactory.createTable();
            masterTable.setMemento( getLocalTable().createMemento() );
        }
        catch( final MementoException e )
        {
            throw new TableNetworkException( TableNetworkError.UNSPECIFIED_ERROR, e );
        }

        masterTable_ = masterTable;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IServerNode#isPlayerConnected(java.lang.String)
     */
    @Override
    public boolean isPlayerConnected(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertConnected();
        return players_.contains( playerName );
    }

    /**
     * Notifies all remote nodes and the local table network controller that the
     * collection of players connected to the table network has been updated.
     */
    private void notifyPlayersUpdated()
    {
        final Collection<String> players = getPlayers();
        for( final IRemoteClientNode remoteNode : getRemoteNodes() )
        {
            remoteNode.setPlayers( players );
        }

        getTableNetworkController().playersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#remoteNodeBound(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeBound(
        final IRemoteClientNode remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.remoteNodeBound( remoteNode );

        bindPlayer( remoteNode.getPlayerName() );
        updateRemoteTable( remoteNode );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#remoteNodeUnbound(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeUnbound(
        final IRemoteClientNode remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.remoteNodeUnbound( remoteNode );

        unbindPlayer( remoteNode.getPlayerName() );
    }

    /**
     * Unbinds the specified player from the table network.
     * 
     * @param playerName
     *        The name of the player to unbind from the table network; must not
     *        be {@code null}.
     */
    @GuardedBy( "getLock()" )
    private void unbindPlayer(
        /* @NonNull */
        final String playerName )
    {
        assert playerName != null;
        assert Thread.holdsLock( getLock() );

        assert players_.contains( playerName );
        players_.remove( playerName );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has disconnected", playerName ) ); //$NON-NLS-1$
        notifyPlayersUpdated();
    }

    /**
     * Updates the state of the table at the specified remote node with the
     * master table state.
     * 
     * @param remoteNode
     *        The remote node; must not be {@code null}.
     */
    private void updateRemoteTable(
        /* @NonNull */
        final IRemoteClientNode remoteNode )
    {
        assert remoteNode != null;

        try
        {
            remoteNode.getTableProxy().setMemento( masterTable_.createMemento() );
        }
        catch( final MementoException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServerNode_updateRemoteTable_error( remoteNode.getPlayerName() ), e );
        }
    }
}
