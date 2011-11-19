/*
 * NodeControllerProxy.java
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
 * Created on Nov 8, 2011 at 8:20:46 PM.
 */

package org.gamegineer.table.internal.net.node;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A proxy for the {@link INodeController} interface of an instance of
 * {@link AbstractNode} that ensures all methods are called on the associated
 * node layer thread.
 */
@Immutable
final class NodeControllerProxy
    implements INodeController
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The actual node controller. */
    private final AbstractNode<?> actualNodeController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NodeControllerProxy} class.
     * 
     * @param actualNodeController
     *        The actual node controller; must not be {@code null}.
     */
    NodeControllerProxy(
        /* @NonNull */
        final AbstractNode<?> actualNodeController )
    {
        assert actualNodeController != null;

        actualNodeController_ = actualNodeController;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginConnect(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public Future<Void> beginConnect(
        final ITableNetworkConfiguration configuration )
    {
        try
        {
            return actualNodeController_.syncExec( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return actualNodeController_.beginConnect( configuration );
                }
            } );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            // TODO: avoid InterruptedException by not calling syncExec, but rather
            // using a pool thread and invoking endConnect() within the pool task
            Thread.currentThread().interrupt();
            throw new RuntimeException( e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginDisconnect()
     */
    @Override
    public Future<Void> beginDisconnect()
    {
        try
        {
            return actualNodeController_.syncExec( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return actualNodeController_.beginDisconnect();
                }
            } );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            // TODO: avoid InterruptedException by not calling syncExec, but rather
            // using a pool thread and invoking endDisconnect() within the pool task
            Thread.currentThread().interrupt();
            throw new RuntimeException( e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        try
        {
            actualNodeController_.syncExec( new Callable<Void>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Void call()
                {
                    actualNodeController_.cancelControlRequest();

                    return null;
                }
            } );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NodeControllerProxy_interrupted, e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#endConnect(java.util.concurrent.Future)
     */
    @Override
    public void endConnect(
        final Future<Void> future )
        throws TableNetworkException, InterruptedException
    {
        actualNodeController_.endConnect( future );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public void endDisconnect(
        final Future<Void> future )
        throws InterruptedException
    {
        actualNodeController_.endDisconnect( future );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayer()
     */
    @Override
    public IPlayer getPlayer()
    {
        try
        {
            return actualNodeController_.syncExec( new Callable<IPlayer>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public IPlayer call()
                {
                    return actualNodeController_.getPlayer();
                }
            } );

        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NodeControllerProxy_interrupted, e );
            return null;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        try
        {
            return actualNodeController_.syncExec( new Callable<Collection<IPlayer>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Collection<IPlayer> call()
                {
                    return actualNodeController_.getPlayers();
                }
            } );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NodeControllerProxy_interrupted, e );
            return Collections.emptyList();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        final String playerName )
    {
        try
        {
            actualNodeController_.syncExec( new Callable<Void>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Void call()
                {
                    actualNodeController_.giveControl( playerName );

                    return null;
                }
            } );

        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NodeControllerProxy_interrupted, e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#requestControl()
     */
    @Override
    public void requestControl()
    {
        try
        {
            actualNodeController_.syncExec( new Callable<Void>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Void call()
                {
                    actualNodeController_.requestControl();

                    return null;
                }
            } );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NodeControllerProxy_interrupted, e );
        }
    }
}
