/*
 * AbstractService.java
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

package org.gamegineer.table.internal.net.transport.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

/**
 * A service handler in the TCP transport layer Acceptor-Connector pattern
 * implementation.
 */
@ThreadSafe
final class ServiceHandler
    extends AbstractEventHandler
    implements IServiceContext
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

    /** The service. */
    private final IService service_;

    /** The transport layer associated with the service handler. */
    private final AbstractTransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractService} class.
     * 
     * @param transportLayer
     *        The transport layer associated with the service handler; must not
     *        be {@code null}.
     * @param service
     *        The service; must not be {@code null}.
     */
    ServiceHandler(
        /* @NonNull */
        final AbstractTransportLayer transportLayer,
        /* @NonNull */
        final IService service )
    {
        assert transportLayer != null;
        assert service != null;

        channel_ = null;
        inputQueue_ = new InputQueue( transportLayer.getDispatcher().getByteBufferPool() );
        inputQueueState_ = QueueState.OPEN;
        interestOperations_ = SelectionKey.OP_READ;
        isRegistered_ = false;
        isRunning_ = false;
        outputQueue_ = new OutputQueue( transportLayer.getDispatcher().getByteBufferPool(), this );
        outputQueueState_ = QueueState.OPEN;
        readyOperations_ = 0;
        service_ = service;
        transportLayer_ = transportLayer;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#close(java.lang.Exception)
     */
    @Override
    void close(
        final Exception exception )
    {
        final State state;

        synchronized( getLock() )
        {
            if( (state = getState()) == State.OPEN )
            {
                if( isRegistered_ )
                {
                    isRegistered_ = false;
                    transportLayer_.getDispatcher().unregisterEventHandler( this );
                }

                try
                {
                    channel_.close();
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ServiceHandler_close_ioError, e );
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
            service_.stopped( exception );
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
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getChannel()
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
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getInterestOperations()
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
                transportLayer_.getDispatcher().enqueueStatusChange( this );
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
            assertStateLegal( getState() == State.PRISTINE, NonNlsMessages.ServiceHandler_state_notPristine );

            channel_ = channel;
            setState( State.OPEN );

            try
            {
                transportLayer_.getDispatcher().registerEventHandler( this );
                isRegistered_ = true;
            }
            catch( final IOException e )
            {
                close( e );
                throw e;
            }

            service_.started( this );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#prepareToRun()
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
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#run()
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
                        service_.messageReceived( messageEnvelope );
                    }

                    if( inputQueueState_ == QueueState.SHUTTING_DOWN )
                    {
                        inputQueueState_ = QueueState.SHUT_DOWN;
                        service_.peerStopped();
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ServiceHandler_run_error, e );
                close( e );
            }
            finally
            {
                isRunning_ = false;
                readyOperations_ = 0;
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IServiceContext#sendMessage(org.gamegineer.table.internal.net.transport.IMessage)
     */
    public void sendMessage(
        /* @NonNull */
        final IMessage message )
    {
        assert message != null;

        synchronized( getLock() )
        {
            try
            {
                outputQueue_.enqueueMessageEnvelope( MessageEnvelope.fromMessage( message ) );
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ServiceHandler_sendMessage_ioError( message ), e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IServiceContext#stopService()
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
