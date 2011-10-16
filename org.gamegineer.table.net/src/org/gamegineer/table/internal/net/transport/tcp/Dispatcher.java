/*
 * Dispatcher.java
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
 * Created on Jan 7, 2011 at 10:31:01 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.transport.TransportException;

/**
 * An event dispatcher in the TCP transport layer Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * An event dispatcher is responsible for managing event handlers and
 * appropriately dispatching events that occur on the event handler channels.
 * </p>
 * 
 * <p>
 * All client methods of this class are expected to be invoked on the associated
 * transport layer thread except where explicitly noted.
 * </p>
 */
@NotThreadSafe
final class Dispatcher
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The byte buffer pool associated with the dispatcher. */
    private final ByteBufferPool bufferPool_;

    /**
     * The asynchronous completion token for the task executing the event
     * dispatch thread or {@code null} if the event dispatch thread is not
     * running.
     */
    private Future<?> eventDispatchFuture_;

    /** The collection of registered event handlers. */
    private final Collection<AbstractEventHandler> eventHandlers_;

    /**
     * The dispatcher channel multiplexor executing on the event dispatch thread
     * or {@code null} if the event dispatch thread is not running.
     */
    @GuardedBy( "selectorGuard_" )
    private Selector selector_;

    /** The selector lock. */
    private final ReadWriteLock selectorGuard_;

    /** The dispatcher state. */
    private State state_;

    /** The event handler status change queue. */
    private final Queue<AbstractEventHandler> statusChangeQueue_;

    /** The transport layer associated with the dispatcher. */
    private final AbstractTransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dispatcher} class.
     * 
     * @param transportLayer
     *        The transport layer associated with the dispatcher; must not be
     *        {@code null}.
     */
    Dispatcher(
        /* @NonNull */
        final AbstractTransportLayer transportLayer )
    {
        assert transportLayer != null;

        // FIXME: Under current implementation, buffer capacity must be as
        // large as the largest incoming message.  Need to fix this requirement.
        //
        // The fix is to break each MessageEnvelope into fixed size fragments
        // and reassemble them as needed.

        bufferPool_ = new ByteBufferPool( 16384 );
        eventDispatchFuture_ = null;
        eventHandlers_ = new ArrayList<AbstractEventHandler>();
        selector_ = null;
        selectorGuard_ = new ReentrantReadWriteLock();
        state_ = State.PRISTINE;
        statusChangeQueue_ = new LinkedList<AbstractEventHandler>();
        transportLayer_ = transportLayer;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Acquires the selector guard.
     */
    private void acquireSelectorGuard()
    {
        selectorGuard_.readLock().lock();

        if( selector_ != null )
        {
            selector_.wakeup();
        }
    }

    /**
     * Begins an asynchronous operation to close the dispatcher.
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     */
    /* @NonNull */
    Future<Void> beginClose()
    {
        assert isTransportLayerThread();

        if( state_ == State.OPEN )
        {
            closeOrphanedEventHandlers();

            // Wait for the event dispatch task to shut down on a thread other than
            // the transport layer thread or the event dispatch thread because the
            // event dispatch thread may wait on the transport layer thread
            eventDispatchFuture_.cancel( true );
            final Future<?> eventDispatchFuture = eventDispatchFuture_;
            return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
            {
                @Override
                public Void call()
                    throws Exception
                {
                    try
                    {
                        eventDispatchFuture.get( 10, TimeUnit.SECONDS );
                    }
                    catch( final CancellationException e )
                    {
                        // do nothing
                    }

                    return null;
                }
            } );
        }
        return new SynchronousFuture<Void>();
    }

    /**
     * Processes event handlers in the status change queue.
     */
    private void checkStatusChangeQueue()
    {
        AbstractEventHandler eventHandler = null;
        while( (eventHandler = statusChangeQueue_.poll()) != null )
        {
            if( eventHandler.getState() != AbstractEventHandler.State.CLOSED )
            {
                resumeSelection( eventHandler );
            }
        }
    }

    /**
     * Closes any orphaned event handlers before the dispatcher is closed.
     */
    private void closeOrphanedEventHandlers()
    {
        final Collection<AbstractEventHandler> eventHandlers = new ArrayList<AbstractEventHandler>( eventHandlers_ );
        for( final AbstractEventHandler eventHandler : eventHandlers )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Closing orphaned event handler '%s'", eventHandler ) ); //$NON-NLS-1$
            eventHandler.close();
        }
    }

    /**
     * Dispatches events on all channels registered with the specified selector
     * until this thread is interrupted.
     * 
     * @param selector
     *        The event selector; must not be {@code null}.
     */
    private void dispatchEvents(
        /* @NonNull */
        final Selector selector )
    {
        assert selector != null;

        Thread.currentThread().setName( NonNlsMessages.Dispatcher_eventDispatchThread_name );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Event dispatch thread started" ); //$NON-NLS-1$

        try
        {
            while( !Thread.interrupted() )
            {
                selectorGuardBarrier();

                selector.select();

                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                try
                {
                    final Future<?> future = transportLayer_.getExecutorService().submit( new Runnable()
                    {
                        @Override
                        @SuppressWarnings( "synthetic-access" )
                        public void run()
                        {
                            checkStatusChangeQueue();

                            for( final SelectionKey selectionKey : selectionKeys )
                            {
                                processEvents( selectionKey );
                            }
                        }
                    } );
                    future.get();
                }
                catch( final RejectedExecutionException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Dispatcher_transportLayer_shutdown, e );
                }
                catch( final ExecutionException e )
                {
                    throw TaskUtils.launderThrowable( e.getCause() );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }
                finally
                {
                    selectionKeys.clear();
                }
            }
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Dispatcher_dispatchEvents_error, e );
        }
        finally
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Event dispatch thread stopped" ); //$NON-NLS-1$
        }
    }

    /**
     * Ends an asynchronous operation to close the dispatcher.
     * 
     * <p>
     * This method does nothing if the dispatcher is already closed.
     * </p>
     * 
     * @param future
     *        The asynchronous completion token associated with the operation;
     *        must not be {@code null} and must be done.
     */
    void endClose(
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
                eventDispatchFuture_ = null;
            }

            acquireSelectorGuard();
            try
            {
                selector_.close();
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Dispatcher_close_error, e );
            }
            finally
            {
                selector_ = null;
                releaseSelectorGuard();
            }
        }

        state_ = State.CLOSED;
    }

    /**
     * Adds the specified event handler to the status change queue.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     */
    void enqueueStatusChange(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
    {
        assert eventHandler != null;
        assert isTransportLayerThread();

        statusChangeQueue_.add( eventHandler );

        acquireSelectorGuard(); // forces thread-safe wake up of selector
        releaseSelectorGuard();
    }

    /**
     * Gets the byte buffer pool associated with the dispatcher.
     * 
     * @return The byte buffer pool associated with the dispatcher; never
     *         {@code null}.
     */
    /* @NonNull */
    ByteBufferPool getByteBufferPool()
    {
        assert isTransportLayerThread();

        return bufferPool_;
    }

    /**
     * Indicates the current thread is the transport layer thread for the
     * transport layer associated with the dispatcher.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return {@code true} if the current thread is the transport layer thread
     *         for the transport layer associated with the dispatcher; otherwise
     *         {@code false}.
     */
    private boolean isTransportLayerThread()
    {
        return transportLayer_.isTransportLayerThread();
    }

    /**
     * Opens the dispatcher.
     * 
     * @throws org.gamegineer.table.internal.net.transport.TransportException
     *         If an error occurs.
     */
    void open()
        throws TransportException
    {
        assertStateLegal( state_ == State.PRISTINE, NonNlsMessages.Dispatcher_state_notPristine );

        final Selector selector;
        try
        {
            selector = Selector.open();
        }
        catch( final IOException e )
        {
            state_ = State.CLOSED;
            throw new TransportException( NonNlsMessages.Dispatcher_open_ioError, e );
        }

        state_ = State.OPEN;

        acquireSelectorGuard();
        try
        {
            selector_ = selector;
            eventDispatchFuture_ = Activator.getDefault().getExecutorService().submit( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    dispatchEvents( selector );
                }
            } );
        }
        finally
        {
            releaseSelectorGuard();
        }
    }

    /**
     * Processes the events associated with the specified selection key.
     * 
     * @param selectionKey
     *        The selection key; must not be {@code null}.
     */
    private void processEvents(
        /* @NonNull */
        final SelectionKey selectionKey )
    {
        assert selectionKey != null;

        final AbstractEventHandler eventHandler = (AbstractEventHandler)selectionKey.attachment();
        eventHandler.prepareToRun();
        selectionKey.interestOps( 0 );

        try
        {
            eventHandler.run();
        }
        catch( final RuntimeException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Dispatcher_processEvents_unexpectedError, e );
        }
        finally
        {
            enqueueStatusChange( eventHandler );
        }
    }

    /**
     * Releases the selector guard.
     */
    private void releaseSelectorGuard()
    {
        selectorGuard_.readLock().unlock();
    }

    /**
     * Registers the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     * @throws java.lang.IllegalArgumentException
     *         If the event handler is already registered with the dispatcher.
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is not open.
     */
    void registerEventHandler(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
        throws IOException
    {
        assert eventHandler != null;

        assertStateLegal( state_ == State.OPEN, NonNlsMessages.Dispatcher_state_notOpen );
        assertArgumentLegal( !eventHandlers_.contains( eventHandler ), "eventHandler", NonNlsMessages.Dispatcher_registerEventHandler_eventHandlerRegistered ); //$NON-NLS-1$
        assert isTransportLayerThread();

        acquireSelectorGuard();
        try
        {
            eventHandler.setSelectionKey( eventHandler.getChannel().register( selector_, eventHandler.getInterestOperations(), eventHandler ) );
        }
        finally
        {
            releaseSelectorGuard();
        }

        eventHandlers_.add( eventHandler );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered event handler '%s'", eventHandler ) ); //$NON-NLS-1$
    }

    /**
     * Resumes event selection of the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     */
    private void resumeSelection(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
    {
        assert eventHandler != null;

        final SelectionKey selectionKey = eventHandler.getSelectionKey();
        if( selectionKey.isValid() )
        {
            selectionKey.interestOps( eventHandler.getInterestOperations() );
        }
    }

    /**
     * Blocks the event dispatch thread until no client threads hold the
     * selector guard.
     */
    private void selectorGuardBarrier()
    {
        selectorGuard_.writeLock().lock();
        selectorGuard_.writeLock().unlock();
    }

    /**
     * Unregisters the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the event handler is not registered with the dispatcher.
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is not open.
     */
    void unregisterEventHandler(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
    {
        assert eventHandler != null;

        assertStateLegal( state_ == State.OPEN, NonNlsMessages.Dispatcher_state_notOpen );
        assertArgumentLegal( eventHandlers_.remove( eventHandler ), "eventHandler", NonNlsMessages.Dispatcher_unregisterEventHandler_eventHandlerUnregistered ); //$NON-NLS-1$
        assert isTransportLayerThread();

        acquireSelectorGuard();
        try
        {
            final SelectionKey selectionKey = eventHandler.getSelectionKey();
            if( selectionKey != null )
            {
                selectionKey.cancel();
                eventHandler.setSelectionKey( null );
            }
        }
        finally
        {
            releaseSelectorGuard();
        }

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered event handler '%s'", eventHandler ) ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible dispatcher states.
     */
    private enum State
    {
        /** The dispatcher has never been used. */
        PRISTINE,

        /** The dispatcher is open. */
        OPEN,

        /** The dispatcher is closed. */
        CLOSED;
    }
}
