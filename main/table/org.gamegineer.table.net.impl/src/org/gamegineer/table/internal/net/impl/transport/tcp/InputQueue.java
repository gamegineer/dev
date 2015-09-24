/*
 * InputQueue.java
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
 * Created on Feb 4, 2011 at 10:00:56 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Deque;
import java.util.LinkedList;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.transport.MessageEnvelope;

/**
 * A message input queue.
 */
@NotThreadSafe
final class InputQueue
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
     * reading (i.e. dequeuing a message envelope). Before writing to a buffer,
     * it must be compacted. Similarly, before returning a buffer to the queue
     * that has been partially filled, it must be flipped.
     * </p>
     */
    private final Deque<ByteBuffer> bufferQueue_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InputQueue} class.
     * 
     * @param bufferPool
     *        The buffer pool associated with the queue.
     */
    InputQueue(
        final ByteBufferPool bufferPool )
    {
        bufferPool_ = bufferPool;
        bufferQueue_ = new LinkedList<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Removes the next available message envelope from the queue.
     * 
     * @return The message envelope removed from the queue or {@code null} if no
     *         message envelope is available.
     */
    @Nullable MessageEnvelope dequeueMessageEnvelope()
    {
        final ByteBuffer lastBuffer = bufferQueue_.peekLast();
        if( lastBuffer == null )
        {
            return null;
        }

        final byte[] headerBytes = ByteBufferUtils.get( ByteBufferUtils.duplicate( bufferQueue_ ), MessageEnvelope.Header.LENGTH );
        if( headerBytes == null )
        {
            return null;
        }

        final MessageEnvelope.Header header = MessageEnvelope.Header.fromByteArray( headerBytes );
        final int length = MessageEnvelope.Header.LENGTH + header.getBodyLength();
        final byte[] bytes = ByteBufferUtils.get( bufferQueue_, length );
        if( bytes == null )
        {
            return null;
        }

        while( !bufferQueue_.isEmpty() )
        {
            final ByteBuffer buffer = bufferQueue_.peekFirst();
            if( buffer.hasRemaining() )
            {
                break;
            }

            bufferPool_.returnByteBuffer( buffer );
            bufferQueue_.removeFirst();
        }

        return MessageEnvelope.fromByteArray( bytes );
    }

    /**
     * Attempts to fill the queue from the specified channel.
     * 
     * @param channel
     *        The channel used to fill the queue.
     * 
     * @return The number of bytes read from the channel or -1 if channel has
     *         reached the end-of-stream.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    int fillFrom(
        final ReadableByteChannel channel )
        throws IOException
    {
        // WRITE ORIENTATION                        READ ORIENTATION (default)
        //
        // |*|*|*|*|-|-|-|-|     ==== flip ===>     |*|*|*|*|-|-|-|-|
        //         ^       ^     <== compact ==     ^       ^       ^
        //        pos   lim/cap                    pos     lim     cap
        //

        int bytesRead = 0;

        ByteBuffer buffer = bufferQueue_.peekLast();
        if( buffer != null )
        {
            if( buffer.limit() == buffer.capacity() ) // buffer is full
            {
                buffer = null;
            }
            else
            {
                buffer.compact(); // prepare buffer for writing
            }
        }

        do
        {
            if( buffer == null )
            {
                buffer = bufferPool_.takeByteBuffer();
                bufferQueue_.addLast( buffer );
            }

            bytesRead += channel.read( buffer );
            buffer.flip(); // prepare buffer for reading

            if( buffer.limit() == buffer.capacity() ) // buffer is full
            {
                buffer = null;
            }
            else if( buffer.limit() == 0 ) // buffer is empty
            {
                bufferQueue_.removeLast();
            }

        } while( buffer == null );

        return bytesRead;
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
