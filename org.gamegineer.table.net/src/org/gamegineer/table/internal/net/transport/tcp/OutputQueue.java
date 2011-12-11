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

package org.gamegineer.table.internal.net.transport.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Deque;
import java.util.LinkedList;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

/**
 * A message output queue.
 */
@NotThreadSafe
final class OutputQueue
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The buffer pool associated with the queue. */
    private final ByteBufferPool bufferPool_;

    /** The queue of buffers associated with the queue waiting to be processed. */
    private final Deque<ByteBuffer> bufferQueue_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code OuputQueue} class.
     * 
     * @param bufferPool
     *        The buffer pool associated with the queue; must not be {@code
     *        null}.
     */
    OutputQueue(
        /* @NonNull */
        final ByteBufferPool bufferPool )
    {
        assert bufferPool != null;

        bufferPool_ = bufferPool;
        bufferQueue_ = new LinkedList<ByteBuffer>();
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
        final WritableByteChannel channel )
        throws IOException
    {
        assert channel != null;

        ByteBuffer buffer = null;
        int bytesWritten = 0;

        while( true )
        {
            if( buffer == null )
            {
                if( bufferQueue_.isEmpty() )
                {
                    break;
                }

                buffer = bufferQueue_.removeFirst();
                buffer.flip();
            }

            final int localBytesWritten = channel.write( buffer );
            bytesWritten += localBytesWritten;

            if( !buffer.hasRemaining() )
            {
                bufferPool_.returnByteBuffer( buffer );
                buffer = null;
            }

            if( localBytesWritten == 0 )
            {
                break;
            }
        }

        return bytesWritten;
    }

    /**
     * Adds the specified message envelope to the queue.
     * 
     * @param messageEnvelope
     *        The message envelope to be added to the queue; must not be {@code
     *        null}.
     */
    void enqueueMessageEnvelope(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        assert messageEnvelope != null;

        final ByteBuffer incomingBuffer = ByteBuffer.wrap( messageEnvelope.toByteArray() );
        final ByteBuffer lastBuffer = bufferQueue_.peekLast();
        if( (lastBuffer != null) && lastBuffer.hasRemaining() )
        {
            fillBuffer( lastBuffer, incomingBuffer );
        }

        while( incomingBuffer.hasRemaining() )
        {
            final ByteBuffer newBuffer = bufferPool_.takeByteBuffer();
            fillBuffer( newBuffer, incomingBuffer );
            bufferQueue_.addLast( newBuffer );
        }
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
        return bufferQueue_.isEmpty();
    }
}
