/*
 * OutputQueue.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Deque;
import java.util.LinkedList;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.transport.MessageEnvelope;

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

    /**
     * The queue of buffers associated with the queue waiting to be processed.
     * 
     * <p>
     * The buffers in the queue are oriented such that they are prepared for
     * writing (i.e. enqueuing a message envelope). Before reading from a
     * buffer, it must be flipped. Similarly, before returning a buffer to the
     * queue that has been partially drained, it must be compacted.
     * </p>
     */
    private final Deque<ByteBuffer> bufferQueue_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code OuputQueue} class.
     * 
     * @param bufferPool
     *        The buffer pool associated with the queue.
     */
    OutputQueue(
        final ByteBufferPool bufferPool )
    {
        bufferPool_ = bufferPool;
        bufferQueue_ = new LinkedList<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Attempts to drain the queue to the specified channel.
     * 
     * @param channel
     *        The channel to which the queue will be drained.
     * 
     * @return The number of bytes written to the channel.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    int drainTo(
        final WritableByteChannel channel )
        throws IOException
    {
        // WRITE ORIENTATION (default)              READ ORIENTATION
        //
        // |*|*|*|*|-|-|-|-|     ==== flip ===>     |*|*|*|*|-|-|-|-|
        //         ^       ^     <== compact ==     ^       ^       ^
        //        pos   lim/cap                    pos     lim     cap
        //

        int bytesWritten = 0;

        while( !bufferQueue_.isEmpty() )
        {
            final ByteBuffer buffer = bufferQueue_.removeFirst();

            buffer.flip(); // prepare buffer for reading
            bytesWritten += channel.write( buffer );

            if( buffer.hasRemaining() )
            {
                buffer.compact(); // prepare buffer for writing
                bufferQueue_.addFirst( buffer );
                break;
            }

            bufferPool_.returnByteBuffer( buffer );
        }

        return bytesWritten;
    }

    /**
     * Adds the specified message envelope to the queue.
     * 
     * @param messageEnvelope
     *        The message envelope to be added to the queue.
     */
    void enqueueMessageEnvelope(
        final MessageEnvelope messageEnvelope )
    {
        final ByteBuffer incomingBuffer = ByteBuffer.wrap( messageEnvelope.toByteArray() );
        final ByteBuffer lastBuffer = bufferQueue_.peekLast();
        if( (lastBuffer != null) && lastBuffer.hasRemaining() )
        {
            ByteBufferUtils.fill( lastBuffer, incomingBuffer );
        }

        while( incomingBuffer.hasRemaining() )
        {
            final ByteBuffer newBuffer = bufferPool_.takeByteBuffer();
            ByteBufferUtils.fill( newBuffer, incomingBuffer );
            bufferQueue_.addLast( newBuffer );
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
