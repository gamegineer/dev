/*
 * TransportLayerProxy.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
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
     *        The actual transport layer; must not be {@code null}.
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
    public Future<Void> beginClose()
    {
        final Future<Future<Void>> beginCloseTaskFuture;
        try
        {
            beginCloseTaskFuture = actualTransportLayer_.asyncExec( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return actualTransportLayer_.beginClose();
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            // Silently ignore request when transport layer is closed
            return new SynchronousFuture<>();
        }

        return nonNull( Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Nullable
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
            {
                try
                {
                    final Future<Void> closeTaskFuture;
                    try
                    {
                        closeTaskFuture = beginCloseTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    assert closeTaskFuture != null;
                    actualTransportLayer_.endClose( closeTaskFuture );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginOpen(java.lang.String, int)
     */
    @Override
    public Future<Void> beginOpen(
        final String hostName,
        final int port )
    {
        final Future<Future<Void>> beginOpenTaskFuture;
        try
        {
            beginOpenTaskFuture = actualTransportLayer_.asyncExec( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return actualTransportLayer_.beginOpen( hostName, port );
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            return new SynchronousFuture<>( new IllegalStateException( NonNlsMessages.TransportLayerProxy_beginOpen_transportLayerClosed, e ) );
        }

        return nonNull( Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Nullable
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws TransportException
            {
                try
                {
                    final Future<Void> openTaskFuture;
                    try
                    {
                        openTaskFuture = beginOpenTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }

                    assert openTaskFuture != null;
                    actualTransportLayer_.endOpen( openTaskFuture );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endClose(java.util.concurrent.Future)
     */
    @Override
    public void endClose(
        final Future<Void> future )
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
        final Future<Void> future )
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
