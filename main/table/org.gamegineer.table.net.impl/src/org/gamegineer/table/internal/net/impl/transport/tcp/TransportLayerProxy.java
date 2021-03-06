/*
 * TransportLayerProxy.java
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
 * Created on Oct 10, 2011 at 8:31:15 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.impl.Activator;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.TransportException;

/**
 * A proxy for the {@link ITransportLayer} interface of an instance of
 * {@link AbstractTransportLayer} that ensures all methods are called on the
 * associated transport layer thread.
 */
@Immutable
final class TransportLayerProxy
    implements ITransportLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The actual transport layer. */
    private final AbstractTransportLayer actualTransportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TransportLayerProxy} class.
     * 
     * @param transportLayer
     *        The actual transport layer.
     */
    TransportLayerProxy(
        final AbstractTransportLayer transportLayer )
    {
        actualTransportLayer_ = transportLayer;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginClose()
     */
    @Override
    public Future<@Nullable Void> beginClose()
    {
        final Future<Future<@Nullable Void>> beginCloseTaskFuture;
        try
        {
            beginCloseTaskFuture = actualTransportLayer_.asyncExec( new Callable<Future<@Nullable Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<@Nullable Void> call()
                {
                    return actualTransportLayer_.beginClose();
                }
            } );
        }
        catch( @SuppressWarnings( "unused" ) final RejectedExecutionException e )
        {
            // Silently ignore request when transport layer is closed
            return new SynchronousFuture<>();
        }

        return Activator.getDefault().getExecutorService().submit( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
            {
                try
                {
                    final Future<@Nullable Void> closeTaskFuture;
                    try
                    {
                        closeTaskFuture = beginCloseTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    actualTransportLayer_.endClose( closeTaskFuture );
                }
                catch( @SuppressWarnings( "unused" ) final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginOpen(java.lang.String, int)
     */
    @Override
    public Future<@Nullable Void> beginOpen(
        final String hostName,
        final int port )
    {
        final Future<Future<@Nullable Void>> beginOpenTaskFuture;
        try
        {
            beginOpenTaskFuture = actualTransportLayer_.asyncExec( new Callable<Future<@Nullable Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<@Nullable Void> call()
                {
                    return actualTransportLayer_.beginOpen( hostName, port );
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            return new SynchronousFuture<>( new IllegalStateException( NonNlsMessages.TransportLayerProxy_beginOpen_transportLayerClosed, e ) );
        }

        return Activator.getDefault().getExecutorService().submit( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
                throws TransportException
            {
                try
                {
                    final Future<@Nullable Void> openTaskFuture;
                    try
                    {
                        openTaskFuture = beginOpenTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    actualTransportLayer_.endOpen( openTaskFuture );
                }
                catch( @SuppressWarnings( "unused" ) final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endClose(java.util.concurrent.Future)
     */
    @Override
    public void endClose(
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
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endOpen(java.util.concurrent.Future)
     */
    @Override
    public void endOpen(
        final Future<@Nullable Void> future )
        throws TransportException, InterruptedException
    {
        try
        {
            future.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof TransportException )
            {
                throw (TransportException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }
}
