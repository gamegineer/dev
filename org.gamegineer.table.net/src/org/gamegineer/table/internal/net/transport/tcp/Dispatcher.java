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
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
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
 */
@ThreadSafe
final class Dispatcher
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The byte buffer pool associated with the dispatcher. */
    private final ByteBufferPool bufferPool_;

    /**
     * The task executing the event dispatch thread or {@code null} if the event
     * dispatch thread is not running.
     */
    @GuardedBy( "lock_" )
    private Future<?> eventDispatchTask_;

    /** The collection of registered event handlers. */
    @GuardedBy( "lock_" )
    private final Collection<AbstractEventHandler> eventHandlers_;

    /** The instance lock. */
    private final Object lock_;

    /**
     * The dispatcher channel multiplexor executing on the event dispatch thread
     * or {@code null} if the event dispatch thread is not running.
     */
    @GuardedBy( "selectorGuard_" )
    private Selector selector_;

    /** The selector lock. */
    private final ReadWriteLock selectorGuard_;

    /** The dispatcher state. */
    @GuardedBy( "lock_" )
    private State state_;

    /** The event handler status change queue. */
    private final BlockingQueue<AbstractEventHandler> statusChangeQueue_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dispatcher} class.
     */
    Dispatcher()
    {
        // FIXME: Under current implementation, buffer capacity must be as
        // large as the largest incoming message.  Need to fix this requirement.

        bufferPool_ = new ByteBufferPool( 8196 );
        eventDispatchTask_ = null;
        eventHandlers_ = new ArrayList<AbstractEventHandler>();
        lock_ = new Object();
        selector_ = null;
        selectorGuard_ = new ReentrantReadWriteLock();
        state_ = State.PRISTINE;
        statusChangeQueue_ = new ArrayBlockingQueue<AbstractEventHandler>( 100 );
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
     * Closes the dispatcher.
     */
    void close()
    {
        synchronized( lock_ )
        {
            if( state_ == State.OPEN )
            {
                final Collection<AbstractEventHandler> eventHandlers = new ArrayList<AbstractEventHandler>( eventHandlers_ );
                for( final AbstractEventHandler eventHandler : eventHandlers )
                {
                    Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Closing orphaned event handler '%s'", eventHandler ) ); //$NON-NLS-1$
                    eventHandler.close();
                }

                eventDispatchTask_.cancel( true );
                try
                {
                    eventDispatchTask_.get( 10, TimeUnit.SECONDS );
                }
                catch( final CancellationException e )
                {
                    // do nothing
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }
                catch( final Exception e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_close_error, e );
                }
                finally
                {
                    eventDispatchTask_ = null;
                }

                acquireSelectorGuard();
                try
                {
                    selector_.close();
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_close_error, e );
                }
                finally
                {
                    selector_ = null;
                    releaseSelectorGuard();
                }
            }

            state_ = State.CLOSED;
        }
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

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Event dispatch thread started" ); //$NON-NLS-1$

        try
        {
            while( !Thread.interrupted() )
            {
                selectorGuardBarrier();

                selector.select();

                checkStatusChangeQueue();

                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for( final SelectionKey selectionKey : selectionKeys )
                {
                    processEvents( selectionKey );
                }
                selectionKeys.clear();
            }
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_dispatchEvents_error, e );
        }
        finally
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Event dispatch thread stopped" ); //$NON-NLS-1$
        }
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

        boolean interrupted = false;
        try
        {
            while( true )
            {
                try
                {
                    statusChangeQueue_.put( eventHandler );
                    acquireSelectorGuard(); // forces thread-safe wake up of selector
                    releaseSelectorGuard();
                    return;
                }
                catch( final InterruptedException e )
                {
                    interrupted = true;
                }
            }
        }
        finally
        {
            if( interrupted )
            {
                Thread.currentThread().interrupt();
            }
        }
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
        return bufferPool_;
    }

    /**
     * Opens the dispatcher.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher has already been opened or is closed.
     * @throws org.gamegineer.table.internal.net.transport.TransportException
     *         If an error occurs.
     */
    void open()
        throws TransportException
    {
        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.PRISTINE, Messages.Dispatcher_state_notPristine );

            final Selector selector;
            try
            {
                selector = Selector.open();
            }
            catch( final IOException e )
            {
                state_ = State.CLOSED;
                throw new TransportException( Messages.Dispatcher_open_ioError, e );
            }

            state_ = State.OPEN;

            acquireSelectorGuard();
            try
            {
                selector_ = selector;
                eventDispatchTask_ = Activator.getDefault().getExecutorService().submit( new Runnable()
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

        // TODO: Eventually submit to executor to run the handler asynchronously.
        try
        {
            eventHandler.run();
        }
        catch( final RuntimeException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_processEvents_unexpectedError, e );
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

        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.OPEN, Messages.Dispatcher_state_notOpen );
            assertArgumentLegal( !eventHandlers_.contains( eventHandler ), "eventHandler", Messages.Dispatcher_registerEventHandler_eventHandlerRegistered ); //$NON-NLS-1$

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

        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.OPEN, Messages.Dispatcher_state_notOpen );
            assertArgumentLegal( eventHandlers_.remove( eventHandler ), "eventHandler", Messages.Dispatcher_unregisterEventHandler_eventHandlerUnregistered ); //$NON-NLS-1$

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
