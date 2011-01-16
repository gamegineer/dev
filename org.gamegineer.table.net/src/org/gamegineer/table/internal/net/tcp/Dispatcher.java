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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.connection.IDispatcher;
import org.gamegineer.table.internal.net.connection.IEventHandler;

// TODO: This class should implement the Active Object pattern such that open() no
// longer blocks but simply starts the task.

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.connection.IDispatcher} that uses
 * TCP.
 */
@ThreadSafe
final class Dispatcher
    implements IDispatcher<SelectableChannel, SelectionKey>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of event handler registration requests. */
    private final Queue<IEventHandler<SelectableChannel, SelectionKey>> eventHandlerRegistrationRequests_;

    /** The collection of event handler unregistration requests. */
    private final Queue<IEventHandler<SelectableChannel, SelectionKey>> eventHandlerUnregistrationRequests_;

    /**
     * A reference to the dispatcher channel multiplexor executing on the event
     * dispatch thread. The referee is {@code null} if the event dispatch thread
     * is not running.
     */
    private final AtomicReference<Selector> selectorRef_;

    /** A reference to the dispatcher state. */
    private final AtomicReference<State> stateRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dispatcher} class.
     */
    Dispatcher()
    {
        eventHandlerRegistrationRequests_ = new ConcurrentLinkedQueue<IEventHandler<SelectableChannel, SelectionKey>>();
        eventHandlerUnregistrationRequests_ = new ConcurrentLinkedQueue<IEventHandler<SelectableChannel, SelectionKey>>();
        selectorRef_ = new AtomicReference<Selector>( null );
        stateRef_ = new AtomicReference<State>( State.PRISTINE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#close()
     */
    @Override
    public void close()
    {
        // XXX: WHO IS GOING TO BE RESPONSIBLE for closing any service handlers
        // that are registered with the dispatcher before closing the dispatcher?
        // Could do it here but that means the registered event handler collection
        // must be a thread-safe field.

        // TODO: Interrupt the EDT instead; that's our standard mechanism for indicating
        // the EDT should shutdown.
        stateRef_.set( State.CLOSED );
        wakeupEventDispatchThread();
    }

    /**
     * Dispatches events on all channels registered with this object until this
     * thread is interrupted.
     */
    private void dispatchEvents()
    {
        System.out.println( "starting dispatch event handler" ); //$NON-NLS-1$ // XXX

        final Map<SelectionKey, IEventHandler<SelectableChannel, SelectionKey>> eventHandlers = new IdentityHashMap<SelectionKey, IEventHandler<SelectableChannel, SelectionKey>>();
        final Map<IEventHandler<SelectableChannel, SelectionKey>, SelectionKey> selectionKeys = new IdentityHashMap<IEventHandler<SelectableChannel, SelectionKey>, SelectionKey>();

        try
        {
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
                        IEventHandler<SelectableChannel, SelectionKey> eventHandler = null;
                        while( (eventHandler = eventHandlerRegistrationRequests_.poll()) != null )
                        {
                            final SelectableChannel channel = eventHandler.getTransportHandle();
                            final SelectionKey selectionKey = channel.register( selector, eventHandler.getEvents() );
                            eventHandlers.put( selectionKey, eventHandler );
                            selectionKeys.put( eventHandler, selectionKey );
                        }
                    }

                    {
                        IEventHandler<SelectableChannel, SelectionKey> eventHandler = null;
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
                            final IEventHandler<SelectableChannel, SelectionKey> eventHandler = eventHandlers.get( selectionKey );
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
            // TODO
            e.printStackTrace();
        }
        finally
        {
            System.out.println( "stopping dispatch event handler" ); //$NON-NLS-1$ // XXX
            stateRef_.set( State.CLOSED );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#open()
     */
    @Override
    public void open()
    {
        // TODO: Spawn new task.
        dispatchEvents();
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#registerEventHandler(org.gamegineer.table.internal.net.connection.IEventHandler)
     */
    @Override
    public void registerEventHandler(
        final IEventHandler<SelectableChannel, SelectionKey> eventHandler )
    {
        assertArgumentNotNull( eventHandler, "eventHandler" ); //$NON-NLS-1$
        assertStateLegal( stateRef_.get() != State.CLOSED, Messages.Dispatcher_state_notPristine );

        eventHandlerRegistrationRequests_.offer( eventHandler );
        wakeupEventDispatchThread();
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#unregisterEventHandler(org.gamegineer.table.internal.net.connection.IEventHandler)
     */
    @Override
    public void unregisterEventHandler(
        final IEventHandler<SelectableChannel, SelectionKey> eventHandler )
    {
        assertArgumentNotNull( eventHandler, "eventHandler" ); //$NON-NLS-1$
        assertStateLegal( stateRef_.get() != State.CLOSED, Messages.Dispatcher_state_notPristine );

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
