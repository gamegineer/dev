/*
 * NodeControllerProxy.java
 * Copyright 2008-2013 Gamegineer.org
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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkError;
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
        final Future<Future<Void>> beginConnectTaskFuture;
        try
        {
            beginConnectTaskFuture = actualNodeController_.getNodeLayer().asyncExec( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return actualNodeController_.beginConnect( configuration );
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            return new SynchronousFuture<>( new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE ) );
        }

        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws TableNetworkException
            {
                try
                {
                    final Future<Void> connectTaskFuture;
                    try
                    {
                        connectTaskFuture = beginConnectTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    actualNodeController_.endConnect( connectTaskFuture );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginDisconnect()
     */
    @Override
    public Future<Void> beginDisconnect()
    {
        final Future<Future<Void>> beginDisconnectTaskFuture;
        try
        {
            beginDisconnectTaskFuture = actualNodeController_.getNodeLayer().asyncExec( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return actualNodeController_.beginDisconnect();
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            // Silently ignore request when node layer is disconnected
            return new SynchronousFuture<>();
        }

        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
            {
                try
                {
                    final Future<Void> disconnectTaskFuture;
                    try
                    {
                        disconnectTaskFuture = beginDisconnectTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    actualNodeController_.endDisconnect( disconnectTaskFuture );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        try
        {
            actualNodeController_.getNodeLayer().syncExec( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    actualNodeController_.cancelControlRequest();
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
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$

        try
        {
            future.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof TableNetworkException )
            {
                throw (TableNetworkException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public void endDisconnect(
        final Future<Void> future )
        throws InterruptedException
    {
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$

        try
        {
            future.get();
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayer()
     */
    @Override
    public IPlayer getPlayer()
    {
        try
        {
            return actualNodeController_.getNodeLayer().syncExec( new Callable<IPlayer>()
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
            return actualNodeController_.getNodeLayer().syncExec( new Callable<Collection<IPlayer>>()
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
            actualNodeController_.getNodeLayer().syncExec( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    actualNodeController_.giveControl( playerName );
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
            actualNodeController_.getNodeLayer().syncExec( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    actualNodeController_.requestControl();
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
