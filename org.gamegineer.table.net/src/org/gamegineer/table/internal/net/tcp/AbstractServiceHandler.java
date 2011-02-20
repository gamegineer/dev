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
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
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
    private SocketChannel channel_;

    /** The input queue associated with the service handler. */
    private final InputQueue inputQueue_;

    /** The channel operations in which the handler is interested. */
    @GuardedBy( "getLock()" )
    private int interestOperations_;

    /** Indicates the input stream is shut down. */
    @GuardedBy( "getLock()" )
    private boolean isInputShutDown_;

    /** Indicates the service handler has been registered with the dispatcher. */
    @GuardedBy( "getLock()" )
    private boolean isRegistered_;

    /** Indicates the handler is running. */
    @GuardedBy( "getLock()" )
    private boolean isRunning_;

    /** The network interface associated with the service handler. */
    private final AbstractNetworkInterface networkInterface_;

    /** The output handler associated with the service handler. */
    private final OutputQueue outputQueue_;

    /**
     * A snapshot of the channel operations that are ready immediately before
     * the handler is run.
     */
    @GuardedBy( "getLock()" )
    private int readyOperations_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractServiceHandler} class.
     * 
     * @param networkInterface
     *        The network interface associated with the service handler; must
     *        not be {@code null}.
     */
    AbstractServiceHandler(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface )
    {
        assert networkInterface != null;

        channel_ = null;
        inputQueue_ = new InputQueue( networkInterface.getDispatcher().getByteBufferPool() );
        interestOperations_ = SelectionKey.OP_READ;
        isInputShutDown_ = false;
        isRegistered_ = false;
        isRunning_ = false;
        networkInterface_ = networkInterface;
        outputQueue_ = new OutputQueue( networkInterface.getDispatcher().getByteBufferPool(), this );
        readyOperations_ = 0;
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
        final State state;

        synchronized( getLock() )
        {
            if( (state = getState()) == State.OPEN )
            {
                if( isRegistered_ )
                {
                    isRegistered_ = false;
                    networkInterface_.getDispatcher().unregisterEventHandler( this );
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

        if( state == State.OPEN )
        {
            serviceHandlerClosed();
        }
    }

    /**
     * Invoked when the service handler has been opened.
     * 
     * TODO: Remove
     */
    void doOpen()
    {
        // do nothing
    }

    /**
     * Drains the output queue to the channel.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    @GuardedBy( "getLock()" )
    private void drainOutput()
        throws IOException
    {
        assert Thread.holdsLock( getLock() );

        if( ((readyOperations_ & SelectionKey.OP_WRITE) != 0) && !outputQueue_.isEmpty() )
        {
            outputQueue_.drainTo( channel_ );
        }

        if( outputQueue_.isEmpty() )
        {
            modifyInterestOperations( 0, SelectionKey.OP_WRITE );
        }
    }

    /**
     * Fills the input queue from the channel.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    @GuardedBy( "getLock()" )
    private void fillInput()
        throws IOException
    {
        assert Thread.holdsLock( getLock() );

        if( isInputShutDown_ )
        {
            return;
        }

        final int bytesRead = inputQueue_.fillFrom( channel_ );
        if( bytesRead == -1 )
        {
            modifyInterestOperations( 0, SelectionKey.OP_READ );

            if( channel_.socket().isConnected() )
            {
                try
                {
                    channel_.socket().shutdownInput();
                }
                catch( final SocketException e )
                {
                    // ignore
                }
            }

            isInputShutDown_ = true;
            modifyInterestOperations( SelectionKey.OP_WRITE, 0 );
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

    /**
     * Gets the input queue associated with the service handler.
     * 
     * @return The input queue associated with the service handler; never
     *         {@code null}.
     */
    /* @NonNull */
    final InputQueue getInputQueue()
    {
        return inputQueue_;
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    final int getInterestOperations()
    {
        synchronized( getLock() )
        {
            return interestOperations_;
        }
    }

    /**
     * Gets the network interface associated with the service handler.
     * 
     * @return The network interface associated with the service handler; never
     *         {@code null}.
     */
    /* @NonNull */
    final AbstractNetworkInterface getNetworkInterface()
    {
        return networkInterface_;
    }

    /**
     * Gets the next message available from the input queue.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * @return The next message available from the input queue or {@code null}
     *         if no message is available.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    abstract ByteBuffer getNextMessage();

    /**
     * Gets the output queue associated with the service handler.
     * 
     * @return The output queue associated with the service handler; never
     *         {@code null}.
     */
    /* @NonNull */
    final OutputQueue getOutputQueue()
    {
        return outputQueue_;
    }

    /**
     * Invoked when the input stream is shut down.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     */
    @GuardedBy( "getLock()" )
    abstract void handleInputShutDown();

    /**
     * Invoked to handle the specified message.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    abstract void handleMessage(
        /* @NonNull */
        ByteBuffer message );

    /**
     * Modifies the channel operations in which the handler is interested.
     * 
     * @param operationsToSet
     *        A bit mask of channel operations to add to the handler interest
     *        set.
     * @param operationsToReset
     *        A bit mask of channel operations to remove from the handler
     *        interest set.
     */
    final void modifyInterestOperations(
        final int operationsToSet,
        final int operationsToReset )
    {
        synchronized( getLock() )
        {
            interestOperations_ = (interestOperations_ | operationsToSet) & ~operationsToReset;

            if( !isRunning_ )
            {
                networkInterface_.getDispatcher().enqueueStatusChange( this );
            }
        }
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
        final SocketChannel channel )
        throws IOException
    {
        assert channel != null;

        synchronized( getLock() )
        {
            assertStateLegal( getState() == State.PRISTINE, Messages.AbstractServiceHandler_state_notPristine );

            channel_ = channel;
            setState( State.OPEN );

            try
            {
                networkInterface_.getDispatcher().registerEventHandler( this );
                isRegistered_ = true;
            }
            catch( final IOException e )
            {
                close();
                throw e;
            }

            // TODO: Remove
            doOpen();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#prepareToRun()
     */
    @Override
    final void prepareToRun()
    {
        synchronized( getLock() )
        {
            interestOperations_ = getSelectionKey().interestOps();
            readyOperations_ = getSelectionKey().readyOps();
            isRunning_ = true;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#run()
     */
    @Override
    final void run()
    {
        synchronized( getLock() )
        {
            try
            {
                drainOutput();
                fillInput();

                ByteBuffer message = null;
                while( (message = getNextMessage()) != null )
                {
                    handleMessage( message );
                }

                if( isInputShutDown_ )
                {
                    handleInputShutDown();
                }
            }
            catch( final Exception e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractServiceHandler_run_error, e );
                close();
            }
            finally
            {
                isRunning_ = false;
                readyOperations_ = 0;
            }
        }
    }

    /**
     * Template method invoked after the service handler has been closed.
     * 
     * <p>
     * This method is NOT invoked while the instance lock is held. Subclasses
     * may override to notify the network interface in an appropriate manner.
     * </p>
     */
    abstract void serviceHandlerClosed();
}
