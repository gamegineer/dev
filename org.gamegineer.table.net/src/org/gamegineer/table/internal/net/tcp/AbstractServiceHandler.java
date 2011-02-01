/*
 * AbstractServiceHandler.java
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
 * Created on Jan 6, 2011 at 10:59:45 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Loggers;

/**
 * A service handler in the TCP network interface Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * A service handler implements one half of an end-to-end protocol in a
 * networked application.
 * </p>
 */
@ThreadSafe
abstract class AbstractServiceHandler
    extends AbstractEventHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The channel associated with the service handler. */
    @GuardedBy( "getLock()" )
    private SelectableChannel channel_;

    /** The dispatcher associated with the service handler. */
    private final Dispatcher dispatcher_;

    /** Indicates the service handler has been registered with the dispatcher. */
    @GuardedBy( "getLock()" )
    private boolean isRegistered_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractServiceHandler} class.
     * 
     * @param dispatcher
     *        The dispatcher associated with the service handler; must not be
     *        {@code null}.
     */
    AbstractServiceHandler(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        assert dispatcher != null;

        channel_ = null;
        dispatcher_ = dispatcher;
        isRegistered_ = false;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#close()
     */
    @Override
    final void close()
    {
        synchronized( getLock() )
        {
            if( getState() == State.OPENED )
            {
                if( isRegistered_ )
                {
                    isRegistered_ = false;
                    dispatcher_.unregisterEventHandler( this );
                }

                try
                {
                    channel_.close();
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractServiceHandler_close_ioError, e );
                }
                finally
                {
                    channel_ = null;
                }
            }

            setState( State.CLOSED );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    final SelectableChannel getChannel()
    {
        synchronized( getLock() )
        {
            return channel_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    final int getInterestOperations()
    {
        return SelectionKey.OP_READ | SelectionKey.OP_WRITE;
    }

    /**
     * Opens the service handler.
     * 
     * @param channel
     *        The channel associated with the service handler; must not be
     *        {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs
     */
    final void open(
        /* @NonNull */
        final SelectableChannel channel )
        throws IOException
    {
        assert channel != null;

        synchronized( getLock() )
        {
            assertStateLegal( getState() == State.PRISTINE, Messages.AbstractServiceHandler_state_notPristine );

            channel_ = channel;
            setState( State.OPENED );

            try
            {
                dispatcher_.registerEventHandler( this );
                isRegistered_ = true;
            }
            catch( final IOException e )
            {
                close();
                throw e;
            }
        }
    }
}
