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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.net.NetworkTableException;

/**
 * An event dispatcher in the TCP network interface Acceptor-Connector pattern
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
    @GuardedBy( "lock_" )
    private Selector selector_;

    /** The dispatcher state. */
    @GuardedBy( "lock_" )
    private State state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dispatcher} class.
     */
    Dispatcher()
    {
        eventDispatchTask_ = null;
        eventHandlers_ = new ArrayList<AbstractEventHandler>();
        lock_ = new Object();
        selector_ = null;
        state_ = State.PRISTINE;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the dispatcher.
     */
    void close()
    {
        synchronized( lock_ )
        {
            if( state_ == State.OPENED )
            {
                // TODO: unregister handlers first if we move unregister() calls outside of close() methods
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
                catch( final Exception e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_close_error, e );
                }

                try
                {
                    selector_.close();
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Dispatcher_close_error, e );
                }
            }

            state_ = State.CLOSED;
            selector_ = null;
            eventDispatchTask_ = null;
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
                if( selector.select( 100 ) > 0 )
                {
                    for( final SelectionKey selectionKey : selector.selectedKeys() )
                    {
                        if( selectionKey.isValid() )
                        {
                            final AbstractEventHandler eventHandler = (AbstractEventHandler)selectionKey.attachment();
                            eventHandler.operationReady();
                        }
                    }
                }
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
     * Opens the dispatcher.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher has already been opened or is closed.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    void open()
        throws NetworkTableException
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
                throw new NetworkTableException( Messages.Dispatcher_open_ioError, e );
            }

            state_ = State.OPENED;
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
    }

    /**
     * Registers the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the event handler is already registered with the dispatcher.
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is not open.
     * @throws java.nio.channels.ClosedChannelException
     *         If the event handler channel is closed.
     */
    void registerEventHandler(
        /* @NonNull */
        final AbstractEventHandler eventHandler )
        throws ClosedChannelException
    {
        assert eventHandler != null;

        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.OPENED, Messages.Dispatcher_state_notOpen );
            assertArgumentLegal( !eventHandlers_.contains( eventHandler ), "eventHandler", Messages.Dispatcher_registerEventHandler_eventHandlerRegistered ); //$NON-NLS-1$

            eventHandler.setSelectionKey( eventHandler.getChannel().register( selector_, eventHandler.getInterestOperations(), eventHandler ) );
            eventHandlers_.add( eventHandler );
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered event handler '%s'", eventHandler ) ); //$NON-NLS-1$
        }
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
            assertStateLegal( state_ == State.OPENED, Messages.Dispatcher_state_notOpen );
            assertArgumentLegal( eventHandlers_.remove( eventHandler ), "eventHandler", Messages.Dispatcher_unregisterEventHandler_eventHandlerUnregistered ); //$NON-NLS-1$

            final SelectionKey selectionKey = eventHandler.getSelectionKey();
            if( selectionKey != null )
            {
                selectionKey.cancel();
                eventHandler.setSelectionKey( null );
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
        OPENED,

        /** The dispatcher is closed. */
        CLOSED;
    }
}
