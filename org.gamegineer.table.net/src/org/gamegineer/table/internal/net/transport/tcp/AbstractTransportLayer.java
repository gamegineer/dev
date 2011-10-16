/*
 * AbstractTransportLayer.java
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
 * Created on Feb 12, 2011 at 10:54:54 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.TransportException;

/**
 * Superclass for all transport layer implementations that operate over a TCP
 * connection.
 * 
 * <p>
 * All client methods of this class are expected to be invoked on the associated
 * transport layer thread except where explicitly noted.
 * </p>
 */
@NotThreadSafe
abstract class AbstractTransportLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer context. */
    private final ITransportLayerContext context_;

    /**
     * The dispatcher associated with the transport layer or {@code null} if the
     * transport layer is not open.
     */
    private Dispatcher dispatcher_;

    /** The executor service associated with the transport layer. */
    private final ExecutorService executorService_;

    /** The transport layer state. */
    private State state_;

    /**
     * A reference to the transport layer thread or {@code null} if the
     * transport layer is not open.
     */
    private final AtomicReference<Thread> transportLayerThreadRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTransportLayer} class.
     * 
     * @param context
     *        The transport layer context; must not be {@code null}.
     */
    AbstractTransportLayer(
        /* @NonNull */
        final ITransportLayerContext context )
    {
        assert context != null;

        context_ = context;
        dispatcher_ = null;
        executorService_ = createExecutorService();
        state_ = State.PRISTINE;
        transportLayerThreadRef_ = new AtomicReference<Thread>( null );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Begins an asynchronous operation to close the transport layer.
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     */
    /* @NonNull */
    final Future<Void> beginClose()
    {
        assert isTransportLayerThread();

        if( state_ == State.OPEN )
        {
            return dispatcher_.beginClose();
        }

        return new SynchronousFuture<Void>();
    }

    /**
     * Creates the transport layer executor service.
     * 
     * @return The transport layer executor service; never {@code null}.
     */
    /* @NonNull */
    private ExecutorService createExecutorService()
    {
        return Executors.newSingleThreadExecutor( new ThreadFactory()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Thread newThread(
                final Runnable r )
            {
                final Thread transportLayerThread = new Thread( r, NonNlsMessages.AbstractTransportLayer_transportLayerThread_name )
                {
                    @Override
                    public void run()
                    {
                        transportLayerThreadRef_.set( this );
                        try
                        {
                            super.run();
                        }
                        finally
                        {
                            transportLayerThreadRef_.set( null );
                        }
                    }
                };

                return transportLayerThread;
            }
        } );
    }

    /**
     * Creates a new network service that can be associated with the transport
     * layer.
     * 
     * @return A new network service that can be associated with the transport
     *         layer; never {@code null}.
     */
    /* @NonNull */
    final IService createService()
    {
        assert isTransportLayerThread();

        return context_.createService();
    }

    /**
     * Invoked when the transport layer has been disconnected.
     * 
     * @param exception
     *        The exception that caused the transport layer to be disconnected
     *        or {@code null} if the transport layer was disconnected normally.
     */
    final void disconnected(
        /* @Nullable */
        final Exception exception )
    {
        assert isTransportLayerThread();

        context_.transportLayerDisconnected( exception );
    }

    /**
     * Ends an asynchronous operation to close the transport layer.
     * 
     * <p>
     * This method does nothing if the transport layer is already closed.
     * </p>
     * 
     * @param future
     *        The asynchronous completion token associated with the operation;
     *        must not be {@code null} and must be done.
     */
    final void endClose(
        /* @NonNull */
        final Future<Void> future )
    {
        assert future != null;
        assert future.isDone();
        assert isTransportLayerThread();

        if( state_ == State.OPEN )
        {
            try
            {
                future.get();
            }
            catch( final InterruptedException e )
            {
                throw new AssertionError( "InterruptedException should not happen if future.isDone()" ); //$NON-NLS-1$
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
            finally
            {
                dispatcher_ = null;
            }
        }

        executorService_.shutdown();
        state_ = State.CLOSED;
    }

    /**
     * Gets the dispatcher associated with the transport layer.
     * 
     * @return The dispatcher associated with the transport layer; never {@code
     *         null}.
     */
    /* @NonNull */
    final Dispatcher getDispatcher()
    {
        assert isTransportLayerThread();
        assert dispatcher_ != null;

        return dispatcher_;
    }

    /**
     * Gets the executor service associated with the transport layer.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return The executor service associated with the transport layer; never
     *         {@code null}.
     */
    /* @NonNull */
    final ExecutorService getExecutorService()
    {
        return executorService_;
    }

    /**
     * Indicates the current thread is the transport layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return {@code true} if the current thread is the transport layer thread;
     *         otherwise {@code false}.
     */
    final boolean isTransportLayerThread()
    {
        return Thread.currentThread() == transportLayerThreadRef_.get();
    }

    /**
     * Opens the transport layer.
     * 
     * <p>
     * This method blocks until the transport layer is connected or an error
     * occurs.
     * </p>
     * 
     * @param hostName
     *        The host name; must not be {@code null}. For a passive transport
     *        layer, this value is the host name to which all services will be
     *        bound. For an active transport layer, this value is the host name
     *        of the remote service.
     * @param port
     *        The port. For a passive transport layer, this value is the port to
     *        which all services will be bound. For an active transport layer,
     *        this value is the port of the remote service.
     * 
     * @throws java.lang.IllegalStateException
     *         If the transport layer has already been opened or is closed.
     * @throws org.gamegineer.table.internal.net.transport.TransportException
     *         If an error occurs.
     */
    final void open(
        /* @NonNull */
        final String hostName,
        final int port )
        throws TransportException
    {
        assert hostName != null;
        assert isTransportLayerThread();
        assertStateLegal( state_ == State.PRISTINE, NonNlsMessages.AbstractTransportLayer_state_notPristine );

        state_ = State.OPEN;
        dispatcher_ = new Dispatcher( this );

        try
        {
            dispatcher_.open();
            openInternal( hostName, port );
        }
        catch( final TransportException e )
        {
            // FIXME: Can't wait for full closure on this thread.  Need to transform
            // open() into beginOpen() and endOpen() to accomplish this correctly.
            beginClose();
            throw e;
        }
    }

    /**
     * Template method invoked to open the transport layer.
     * 
     * @param hostName
     *        The host name; must not be {@code null}.
     * @param port
     *        The port.
     * 
     * @throws org.gamegineer.table.internal.net.transport.TransportException
     *         If an error occurs.
     */
    abstract void openInternal(
        /* @NonNull */
        String hostName,
        int port )
        throws TransportException;


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible transport layer states.
     */
    private enum State
    {
        /** The transport layer has never been used. */
        PRISTINE,

        /** The transport layer is open. */
        OPEN,

        /** The transport layer is closed. */
        CLOSED;
    }
}
