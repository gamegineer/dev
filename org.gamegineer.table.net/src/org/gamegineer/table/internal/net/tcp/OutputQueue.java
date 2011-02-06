/*
 * OutputQueue.java
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
 * Created on Feb 4, 2011 at 10:42:47 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.Deque;
import java.util.LinkedList;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * A message output queue.
 */
@ThreadSafe
final class OutputQueue
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The active buffer associated with the queue. */
    @GuardedBy( "lock_" )
    private ByteBuffer buffer_;

    /** The buffer pool associated with the queue. */
    private final ByteBufferPool bufferPool_;

    /** The queue of buffers associated with the queue waiting to be processed. */
    @GuardedBy( "lock_" )
    private final Deque<ByteBuffer> bufferQueue_;

    /** The instance lock. */
    private final Object lock_;

    /** The service handler that owns the queue. */
    private final AbstractServiceHandler serviceHandler_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code OuputQueue} class.
     * 
     * @param bufferPool
     *        The buffer pool associated with the queue; must not be {@code
     *        null}.
     * @param serviceHandler
     *        The service handler that owns the queue; must not be {@code null}.
     */
    OutputQueue(
        /* @NonNull */
        final ByteBufferPool bufferPool,
        /* @NonNull */
        final AbstractServiceHandler serviceHandler )
    {
        assert bufferPool != null;
        assert serviceHandler != null;

        buffer_ = null;
        bufferPool_ = bufferPool;
        bufferQueue_ = new LinkedList<ByteBuffer>();
        lock_ = new Object();
        serviceHandler_ = serviceHandler;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Attempts to drain the queue to the specified channel.
     * 
     * @param channel
     *        The channel to which the queue will be drained; must not be
     *        {@code null}.
     * 
     * @return The number of bytes written to the channel.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    int drainTo(
        /* @NonNull */
        final ByteChannel channel )
        throws IOException
    {
        assert channel != null;

        int bytesWritten = 0;

        synchronized( lock_ )
        {
            while( true )
            {
                if( buffer_ == null )
                {
                    if( bufferQueue_.isEmpty() )
                    {
                        break;
                    }

                    buffer_ = bufferQueue_.removeFirst();
                    buffer_.flip();
                }

                final int localBytesWritten = channel.write( buffer_ );
                bytesWritten += localBytesWritten;

                if( !buffer_.hasRemaining() )
                {
                    bufferPool_.returnByteBuffer( buffer_ );
                    buffer_ = null;
                }

                if( localBytesWritten == 0 )
                {
                    break;
                }
            }
        }

        return bytesWritten;
    }

    /**
     * Adds the bytes in the specified buffer to the queue.
     * 
     * @param incomingBuffer
     *        The buffer whose contents are to be added to the queue; must not
     *        be {@code null}.
     * 
     * @return {@code true} if the bytes in the specified buffer were added to
     *         the queue; otherwise {@code false}.
     */
    boolean enqueueBytes(
        /* @NonNull */
        final ByteBuffer incomingBuffer )
    {
        assert incomingBuffer != null;

        if( !incomingBuffer.hasRemaining() )
        {
            return false;
        }

        synchronized( lock_ )
        {
            if( !bufferQueue_.isEmpty() )
            {
                final ByteBuffer lastBuffer = bufferQueue_.getLast();
                if( lastBuffer.hasRemaining() )
                {
                    fillBuffer( lastBuffer, incomingBuffer );
                }
            }

            while( incomingBuffer.hasRemaining() )
            {
                final ByteBuffer newBuffer = bufferPool_.takeByteBuffer();
                fillBuffer( newBuffer, incomingBuffer );
                bufferQueue_.addLast( newBuffer );
            }
        }

        serviceHandler_.modifyInterestOperations( SelectionKey.OP_WRITE, 0 );

        return true;
    }

    /**
     * Fills the destination buffer with the contents of the source buffer.
     * 
     * @param destinationBuffer
     *        The destination buffer; must not be {@code null}.
     * @param sourceBuffer
     *        The source buffer; must not be {@code null}.
     */
    private static void fillBuffer(
        /* @NonNull */
        final ByteBuffer destinationBuffer,
        /* @NonNull */
        final ByteBuffer sourceBuffer )
    {
        assert destinationBuffer != null;
        assert sourceBuffer != null;

        if( sourceBuffer.remaining() <= destinationBuffer.remaining() )
        {
            destinationBuffer.put( sourceBuffer );
        }
        else
        {
            while( destinationBuffer.hasRemaining() )
            {
                destinationBuffer.put( sourceBuffer.get() );
            }
        }
    }

    /**
     * Indicates the queue is empty.
     * 
     * @return {@code true} if the queue is empty; otherwise {@code false}.
     */
    boolean isEmpty()
    {
        synchronized( lock_ )
        {
            return (buffer_ == null) && bufferQueue_.isEmpty();
        }
    }
}
