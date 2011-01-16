/*
 * ClientServiceHandler.java
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
 * Created on Jan 7, 2011 at 10:31:56 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.connection.IServiceHandler;

/**
 * A service handler that represents the client half of a network table
 * connection.
 */
@ThreadSafe
final class ClientServiceHandler
    implements IServiceHandler<SelectableChannel, SelectionKey>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The channel associated with the peer server. */
    @GuardedBy( "lock_" )
    private SelectableChannel channel_;

    /** The dispatcher associated with the service handler. */
    private final Dispatcher dispatcher_;

    /** The instance lock. */
    private final Object lock_;

    /** The client service handler state. */
    @GuardedBy( "lock_" )
    private State state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientServiceHandler} class.
     * 
     * @param dispatcher
     *        The dispatcher associated with the service handler; must not be
     *        {@code null}.
     */
    ClientServiceHandler(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        assert dispatcher != null;

        channel_ = null;
        dispatcher_ = dispatcher;
        lock_ = new Object();
        state_ = State.PRISTINE;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#close()
     */
    @Override
    public void close()
    {
        synchronized( lock_ )
        {
            if( state_ == State.OPENED )
            {
                dispatcher_.unregisterEventHandler( this );

                try
                {
                    channel_.close();
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ClientServiceHandler_close_ioError, e );
                }
                finally
                {
                    channel_ = null;
                }
            }

            state_ = State.CLOSED;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#getEvents()
     */
    @Override
    public int getEvents()
    {
        return SelectionKey.OP_READ | SelectionKey.OP_WRITE;
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#getTransportHandle()
     */
    @Override
    public SelectableChannel getTransportHandle()
    {
        synchronized( lock_ )
        {
            return channel_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#handleEvent(java.lang.Object)
     */
    @Override
    public void handleEvent(
        final SelectionKey event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // TODO: process events as needed
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IServiceHandler#open(java.lang.Object)
     */
    @Override
    public void open(
        final SelectableChannel handle )
    {
        assertArgumentNotNull( handle, "handle" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.PRISTINE, Messages.ClientServiceHandler_state_notPristine );

            channel_ = handle;
            state_ = State.OPENED;

            dispatcher_.registerEventHandler( this );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible client service handler states.
     */
    private enum State
    {
        /** The client service handler has never been used. */
        PRISTINE,

        /** The client service handler is open. */
        OPENED,

        /** The client service handler is closed. */
        CLOSED;
    }
}
