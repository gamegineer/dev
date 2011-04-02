/*
 * AbstractNetworkServiceHandler.java
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
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.INetworkServiceContext;
import org.gamegineer.table.internal.net.INetworkServiceHandler;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.MessageEnvelope;

/**
 * Adapts instances of {@link INetworkServiceHandler} as a service handler in
 * the TCP network interface Acceptor-Connector pattern implementation.
 */
@ThreadSafe
final class ServiceHandlerAdapter
    extends AbstractEventHandler
    implements INetworkServiceContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The channel associated with the service handler. */
    @GuardedBy( "getLock()" )
    private SocketChannel channel_;

    /** The input queue associated with the service handler. */
    private final InputQueue inputQueue_;

    /** The state of the input queue. */
    @GuardedBy( "getLock()" )
    private QueueState inputQueueState_;

    /** The channel operations in which the handler is interested. */
    @GuardedBy( "getLock()" )
    private int interestOperations_;

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

    /** The state of the output queue. */
    @GuardedBy( "getLock()" )
    private QueueState outputQueueState_;

    /**
     * A snapshot of the channel operations that are ready immediately before
     * the handler is run.
     */
    @GuardedBy( "getLock()" )
    private int readyOperations_;

    /** The service handler to adapt. */
    private final INetworkServiceHandler serviceHandler_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkServiceHandler}
     * class.
     * 
     * @param networkInterface
     *        The network interface associated with the service handler; must
     *        not be {@code null}.
     * @param serviceHandler
     *        The service handler to adapt; must not be {@code null}.
     */
    ServiceHandlerAdapter(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface,
        /* @NonNull */
        final INetworkServiceHandler serviceHandler )
    {
        assert networkInterface != null;
        assert serviceHandler != null;

        channel_ = null;
        inputQueue_ = new InputQueue( networkInterface.getDispatcher().getByteBufferPool() );
        inputQueueState_ = QueueState.OPEN;
        interestOperations_ = SelectionKey.OP_READ;
        isRegistered_ = false;
        isRunning_ = false;
        networkInterface_ = networkInterface;
        outputQueue_ = new OutputQueue( networkInterface.getDispatcher().getByteBufferPool(), this );
        outputQueueState_ = QueueState.OPEN;
        readyOperations_ = 0;
        serviceHandler_ = serviceHandler;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#close()
     */
    @Override
    void close()
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
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServiceHandlerAdapter_close_ioError, e );
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
            serviceHandler_.stopped( this );
        }
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

        if( outputQueueState_ == QueueState.SHUT_DOWN )
        {
            return;
        }

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

        if( inputQueueState_ != QueueState.OPEN )
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

            inputQueueState_ = QueueState.SHUTTING_DOWN;
            modifyInterestOperations( SelectionKey.OP_WRITE, 0 );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    SelectableChannel getChannel()
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
    int getInterestOperations()
    {
        synchronized( getLock() )
        {
            return interestOperations_;
        }
    }

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
    void modifyInterestOperations(
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
    void open(
        /* @NonNull */
        final SocketChannel channel )
        throws IOException
    {
        assert channel != null;

        synchronized( getLock() )
        {
            assertStateLegal( getState() == State.PRISTINE, Messages.ServiceHandlerAdapter_state_notPristine );

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

            serviceHandler_.started( this );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#prepareToRun()
     */
    @Override
    void prepareToRun()
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
    void run()
    {
        synchronized( getLock() )
        {
            try
            {
                drainOutput();
                fillInput();

                if( inputQueueState_ != QueueState.SHUT_DOWN )
                {
                    MessageEnvelope messageEnvelope = null;
                    while( (messageEnvelope = inputQueue_.dequeueMessageEnvelope()) != null )
                    {
                        serviceHandler_.messageReceived( this, messageEnvelope );
                    }

                    if( inputQueueState_ == QueueState.SHUTTING_DOWN )
                    {
                        inputQueueState_ = QueueState.SHUT_DOWN;
                        serviceHandler_.peerStopped( this );
                    }

                    if( (outputQueueState_ == QueueState.SHUTTING_DOWN) && outputQueue_.isEmpty() )
                    {
                        outputQueueState_ = QueueState.SHUT_DOWN;
                        close();
                    }
                }
            }
            catch( final Exception e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServiceHandlerAdapter_run_error, e );
                close();
            }
            finally
            {
                isRunning_ = false;
                readyOperations_ = 0;
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceContext#sendMessage(org.gamegineer.table.internal.net.AbstractMessage)
     */
    public boolean sendMessage(
        /* @NonNull */
        final AbstractMessage message )
    {
        assert message != null;

        synchronized( getLock() )
        {
            try
            {
                outputQueue_.enqueueMessageEnvelope( MessageEnvelope.fromMessage( message ) );
                return true;
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServiceHandlerAdapter_sendMessage_ioError( message ), e );
                return false;
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceContext#stopService()
     */
    @Override
    public void stopService()
    {
        synchronized( getLock() )
        {
            if( outputQueueState_ == QueueState.OPEN )
            {
                outputQueueState_ = QueueState.SHUTTING_DOWN;
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible states of a service handler queue.
     */
    private enum QueueState
    {
        /** The queue is open. */
        OPEN,

        /**
         * The queue is shutting down.
         * 
         * <p>
         * There may be unprocessed data in the queue. For input queues, the
         * underlying stream has been closed. For output queues, the underlying
         * stream will not be closed until the queue has been emptied.
         * </p>
         */
        SHUTTING_DOWN,

        /**
         * The queue is shut down.
         * 
         * <p>
         * No more data remains in the queue to be processed.
         * </p>
         */
        SHUT_DOWN;
    }
}
