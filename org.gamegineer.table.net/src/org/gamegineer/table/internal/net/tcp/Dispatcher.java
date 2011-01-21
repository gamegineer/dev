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

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.Loggers;

/**
 * An event dispatcher in the TCP network interface Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * An event dispatcher is responsible for managing event handlers and
 * appropriately dispatching events that occur on the event handler transport
 * handles.
 * </p>
 */
@ThreadSafe
final class Dispatcher
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of event handler registration requests. */
    private final Queue<AbstractEventHandler> eventHandlerRegistrationRequests_;

    /** The collection of event handler unregistration requests. */
    private final Queue<AbstractEventHandler> eventHandlerUnregistrationRequests_;

    /**
     * The instance lock used to synchronize access to public methods that are
     * invoked on a client thread (i.e. any thread other than the event dispatch
     * thread).
     */
    private final Object externalLock_;

    /**
     * A reference to the dispatcher channel multiplexor executing on the event
     * dispatch thread. The referee is {@code null} if the event dispatch thread
     * is not running.
     */
    private final AtomicReference<Selector> selectorRef_;

    /** A reference to the dispatcher state. */
    private final AtomicReference<State> stateRef_;

    /**
     * A reference to the task executing the event dispatch thread or {@code
     * null} if the event dispatch thread is not running.
     */
    private final AtomicReference<Future<?>> taskRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dispatcher} class.
     */
    Dispatcher()
    {
        eventHandlerRegistrationRequests_ = new ConcurrentLinkedQueue<AbstractEventHandler>();
        eventHandlerUnregistrationRequests_ = new ConcurrentLinkedQueue<AbstractEventHandler>();
        externalLock_ = new Object();
        selectorRef_ = new AtomicReference<Selector>( null );
        stateRef_ = new AtomicReference<State>( State.PRISTINE );
        taskRef_ = new AtomicReference<Future<?>>( null );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the dispatcher.
     */
    void close()
    {
        // XXX: WHO IS GOING TO BE RESPONSIBLE for closing any service handlers
        // that are registered with the dispatcher before closing the dispatcher?
        // Could do it here but that means the registered event handler collection
        // must be a thread-safe field.

        synchronized( externalLock_ )
        {
            final Future<?> task = taskRef_.getAndSet( null );
            stateRef_.set( State.CLOSED );

            if( task != null )
            {
                task.cancel( true );
                try
                {
                    task.get( 10, TimeUnit.SECONDS );
                }
                catch( final CancellationException e )
                {
                    // do nothing
                }
                catch( final Exception e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_close_error, e );
                }
            }
        }
    }

    /**
     * Dispatches events on all channels registered with this object until this
     * thread is interrupted.
     */
    private void dispatchEvents()
    {
        System.out.println( "starting dispatch event handler" ); //$NON-NLS-1$ // XXX

        final Map<SelectionKey, AbstractEventHandler> eventHandlers = new IdentityHashMap<SelectionKey, AbstractEventHandler>();
        final Map<AbstractEventHandler, SelectionKey> selectionKeys = new IdentityHashMap<AbstractEventHandler, SelectionKey>();

        try
        {
            // TODO: May consider not interrupting thread to terminate it because a race condition
            // may occur where the interrupt happens while the following method is executing.  This
            // leads to a harmless but annoying IOException which will ultimately be logged during
            // testing.  Alternatively, use a separate flag (or a CLOSING state) to indicate the
            // EDT should shut down and wake up the selector.
            final Selector selector = Selector.open();
            try
            {
                selectorRef_.set( selector );

                while( true )
                {
                    final int n = selector.select();
                    if( (n == 0) && Thread.interrupted() )
                    {
                        // TODO: Need to clean up as if we've been closed.
                        //
                        // Note that we cannot call close() on the remaining event handlers
                        // because close() will most likely call unregisterEventHandler
                        // thus creating a circularity.

                        return;
                    }

                    // TODO: Need to avoid registering handlers twice; log warning in that case.
                    {
                        AbstractEventHandler eventHandler = null;
                        while( (eventHandler = eventHandlerRegistrationRequests_.poll()) != null )
                        {
                            final SelectableChannel channel = eventHandler.getTransportHandle();
                            final SelectionKey selectionKey = channel.register( selector, eventHandler.getEvents() );
                            eventHandlers.put( selectionKey, eventHandler );
                            selectionKeys.put( eventHandler, selectionKey );
                        }
                    }

                    {
                        AbstractEventHandler eventHandler = null;
                        while( (eventHandler = eventHandlerUnregistrationRequests_.poll()) != null )
                        {
                            // TODO: Log warning if handler not registered.
                            final SelectionKey selectionKey = selectionKeys.remove( eventHandler );
                            if( selectionKey != null )
                            {
                                eventHandlers.remove( selectionKey );
                                selectionKey.cancel();
                            }
                        }
                    }

                    for( final SelectionKey selectionKey : selector.selectedKeys() )
                    {
                        // Check key validity as associated channel may have been closed by another thread
                        if( selectionKey.isValid() )
                        {
                            final AbstractEventHandler eventHandler = eventHandlers.get( selectionKey );
                            assert eventHandler != null; // XXX: may have to check for null
                            eventHandler.handleEvent( selectionKey );
                        }
                    }
                }
            }
            finally
            {
                selectorRef_.set( null );
                selector.close();
            }
        }
        catch( final Exception e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_dispatchEvents_error, e );
        }
        finally
        {
            System.out.println( "stopping dispatch event handler" ); //$NON-NLS-1$ // XXX
        }
    }

    /**
     * Opens the dispatcher.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher has already been opened or is closed.
     */
    void open()
    {
        synchronized( externalLock_ )
        {
            assertStateLegal( stateRef_.compareAndSet( State.PRISTINE, State.OPENED ), Messages.Dispatcher_state_notPristine );

            taskRef_.set( Activator.getDefault().getExecutorService().submit( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    dispatchEvents();
                }
            } ) );
        }
    }

    /**
     * Registers the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is closed.
     */
    void registerEventHandler(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
    {
        assert eventHandler != null;
        assertStateLegal( stateRef_.get() != State.CLOSED, Messages.Dispatcher_state_closed );

        eventHandlerRegistrationRequests_.offer( eventHandler );
        wakeupEventDispatchThread();
    }

    /**
     * Unregisters the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is closed.
     */
    void unregisterEventHandler(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
    {
        assert eventHandler != null;
        assertStateLegal( stateRef_.get() != State.CLOSED, Messages.Dispatcher_state_closed );

        eventHandlerUnregistrationRequests_.offer( eventHandler );
        wakeupEventDispatchThread();
    }

    /**
     * Wakes up the event dispatch thread if it is running.
     */
    private void wakeupEventDispatchThread()
    {
        final Selector selector = selectorRef_.get();
        if( selector != null )
        {
            selector.wakeup();
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
        OPENED,

        /** The dispatcher is closed. */
        CLOSED;
    }
}
