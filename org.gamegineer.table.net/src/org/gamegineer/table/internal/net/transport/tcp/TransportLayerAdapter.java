/*
 * TransportLayerAdapter.java
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
 * Created on Oct 10, 2011 at 8:31:15 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.TransportException;

/**
 * Adapts an instance of {@link AbstractTransportLayer} to
 * {@link ITransportLayer} ensuring that all operations of
 * {@link AbstractTransportLayer} are executed on the transport layer thread.
 */
@Immutable
final class TransportLayerAdapter
    implements ITransportLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The TCP transport layer to be adapted. */
    private final AbstractTransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TransportLayerAdapter} class.
     * 
     * @param transportLayer
     *        The TCP transport layer to be adapted; must not be {@code null}.
     */
    TransportLayerAdapter(
        /* @NonNull */
        final AbstractTransportLayer transportLayer )
    {
        assert transportLayer != null;

        transportLayer_ = transportLayer;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#beginClose()
     */
    @Override
    public Future<Void> beginClose()
    {
        // Invoke beginClose() on transport layer thread
        final Future<Future<Void>> beginCloseTaskFuture;
        try
        {
            beginCloseTaskFuture = transportLayer_.getExecutorService().submit( new Callable<Future<Void>>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Future<Void> call()
                {
                    return transportLayer_.beginClose();
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            // Transport layer is closed
            return new SynchronousFuture<Void>();
        }

        // Wait for beginClose() to complete on a thread other than the transport layer
        // thread and then invoke endClose() on the transport layer thread
        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                final Future<Void> beginCloseFuture = beginCloseTaskFuture.get();
                transportLayer_.getExecutorService().submit( new Callable<Void>()
                {
                    @Override
                    public Void call()
                    {
                        transportLayer_.endClose( beginCloseFuture );
                        return null;
                    }
                } );
                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#beginOpen(java.lang.String, int)
     */
    @Override
    public Future<Void> beginOpen(
        final String hostName,
        final int port )
    {
        assertArgumentNotNull( hostName, "hostName" ); //$NON-NLS-1$

        try
        {
            return transportLayer_.getExecutorService().submit( new Callable<Void>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public Void call()
                    throws Exception
                {
                    transportLayer_.open( hostName, port );
                    return null;
                }
            } );
        }
        catch( final RejectedExecutionException e )
        {
            // Transport layer is closed
            throw new IllegalStateException( NonNlsMessages.TransportLayerAdapter_beginOpen_closed, e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#close()
     */
    @Override
    public void close()
        throws InterruptedException
    {
        endClose( beginClose() );
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#endClose(java.util.concurrent.Future)
     */
    @Override
    public void endClose(
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
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#endOpen(java.util.concurrent.Future)
     */
    @Override
    public void endOpen(
        final Future<Void> future )
        throws TransportException, InterruptedException
    {
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$

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

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#open(java.lang.String, int)
     */
    @Override
    public void open(
        final String hostName,
        final int port )
        throws TransportException, InterruptedException
    {
        endOpen( beginOpen( hostName, port ) );
    }
}