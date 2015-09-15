/*
 * NodeControllerProxy.java
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
 * Created on Nov 8, 2011 at 8:20:46 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.impl.Activator;
import org.gamegineer.table.internal.net.impl.Loggers;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.TableNetworkConfiguration;
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
    private final AbstractNode<@NonNull ?> actualNodeController_;


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
        final AbstractNode<@NonNull ?> actualNodeController )
    {
        actualNodeController_ = actualNodeController;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#beginConnect(org.gamegineer.table.net.TableNetworkConfiguration)
     */
    @Override
    public Future<@Nullable Void> beginConnect(
        final TableNetworkConfiguration configuration )
    {
        final Future<Future<@Nullable Void>> beginConnectTaskFuture;
        try
        {
            beginConnectTaskFuture = actualNodeController_.getNodeLayer().asyncExec( new Callable<Future<@Nullable Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<@Nullable Void> call()
                {
                    return actualNodeController_.beginConnect( configuration );
                }
            } );
        }
        catch( @SuppressWarnings( "unused" ) final RejectedExecutionException e )
        {
            return new SynchronousFuture<>( new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE ) );
        }

        return nonNull( Activator.getDefault().getExecutorService().submit( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
                throws TableNetworkException
            {
                try
                {
                    final Future<@Nullable Void> connectTaskFuture;
                    try
                    {
                        connectTaskFuture = beginConnectTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    assert connectTaskFuture != null;
                    actualNodeController_.endConnect( connectTaskFuture );
                }
                catch( @SuppressWarnings( "unused" ) final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#beginDisconnect()
     */
    @Override
    public Future<@Nullable Void> beginDisconnect()
    {
        final Future<Future<@Nullable Void>> beginDisconnectTaskFuture;
        try
        {
            beginDisconnectTaskFuture = actualNodeController_.getNodeLayer().asyncExec( new Callable<Future<@Nullable Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<@Nullable Void> call()
                {
                    return actualNodeController_.beginDisconnect();
                }
            } );
        }
        catch( @SuppressWarnings( "unused" ) final RejectedExecutionException e )
        {
            // Silently ignore request when node layer is disconnected
            return new SynchronousFuture<>();
        }

        return nonNull( Activator.getDefault().getExecutorService().submit( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
            {
                try
                {
                    final Future<@Nullable Void> disconnectTaskFuture;
                    try
                    {
                        disconnectTaskFuture = beginDisconnectTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    assert disconnectTaskFuture != null;
                    actualNodeController_.endDisconnect( disconnectTaskFuture );
                }
                catch( @SuppressWarnings( "unused" ) final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#cancelControlRequest()
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
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#endConnect(java.util.concurrent.Future)
     */
    @Override
    public void endConnect(
        final Future<@Nullable Void> future )
        throws TableNetworkException, InterruptedException
    {
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
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public void endDisconnect(
        final Future<@Nullable Void> future )
        throws InterruptedException
    {
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
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayer()
     */
    @Nullable
    @Override
    public IPlayer getPlayer()
    {
        try
        {
            return actualNodeController_.getNodeLayer().syncExec( new Callable<@Nullable IPlayer>()
            {
                @Nullable
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
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        try
        {
            final Collection<IPlayer> players = actualNodeController_.getNodeLayer().syncExec( new Callable<Collection<IPlayer>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Collection<IPlayer> call()
                {
                    return actualNodeController_.getPlayers();
                }
            } );
            assert players != null;
            return players;
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NodeControllerProxy_interrupted, e );
            return nonNull( Collections.<@NonNull IPlayer>emptyList() );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#giveControl(java.lang.String)
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
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#requestControl()
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
