/*
 * InputQueue.java
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
 * Created on Feb 4, 2011 at 10:00:56 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

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

    /** The queue of buffers associated with the queue waiting to be processed. */
    private final Deque<ByteBuffer> bufferQueue_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InputQueue} class.
     * 
     * @param bufferPool
     *        The buffer pool associated with the queue; must not be {@code
     *        null}.
     */
    InputQueue(
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
     * Removes the next available message envelope from the queue.
     * 
     * @return The message envelope removed from the queue or {@code null} if no
     *         message envelope is available.
     */
    /* @Nullable */
    MessageEnvelope dequeueMessageEnvelope()
    {
        if( isEmpty() )
        {
            return null;
        }

        final List<ByteBuffer> buffers = Arrays.asList( bufferQueue_.toArray( new ByteBuffer[ bufferQueue_.size() ] ) );
        final ByteBuffer lastBuffer = buffers.get( buffers.size() - 1 );
        final int lastBufferPosition = lastBuffer.position();
        final int lastBufferLimit = lastBuffer.limit();
        lastBuffer.flip();

        final MessageEnvelope messageEnvelope = MessageEnvelope.fromByteBuffers( buffers );
        if( messageEnvelope == null )
        {
            lastBuffer.position( lastBufferPosition ).limit( lastBufferLimit );
            return null;
        }

        while( true )
        {
            final ByteBuffer buffer = bufferQueue_.peekFirst();
            if( buffer == null )
            {
                break;
            }
            else if( buffer.hasRemaining() )
            {
                buffer.compact();
                break;
            }
            else
            {
                bufferPool_.returnByteBuffer( buffer );
                bufferQueue_.removeFirst();
            }
        }

        return messageEnvelope;
    }

    /**
     * Attempts to fill the queue from the specified channel.
     * 
     * @param channel
     *        The channel used to fill the queue; must not be {@code null}.
     * 
     * @return The number of bytes read from the channel or -1 if channel has
     *         reached the end-of-stream.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    int fillFrom(
        /* @NonNull */
        final ReadableByteChannel channel )
        throws IOException
    {
        assert channel != null;

        ByteBuffer buffer = bufferQueue_.peekLast();
        if( buffer == null )
        {
            buffer = bufferPool_.takeByteBuffer();
            bufferQueue_.addLast( buffer );
        }

        int bytesRead = 0;
        while( true )
        {
            final int localBytesRead = channel.read( buffer );
            bytesRead += localBytesRead;
            if( localBytesRead <= 0 )
            {
                break;
            }

            if( buffer.hasRemaining() )
            {
                break;
            }

            buffer.flip();
            buffer = bufferPool_.takeByteBuffer();
            bufferQueue_.addLast( buffer );
        }

        return bytesRead;
    }

    /**
     * Indicates the queue is empty.
     * 
     * @return {@code true} if the queue is empty; otherwise {@code false}.
     */
    boolean isEmpty()
    {
        final int bufferCount = bufferQueue_.size();
        if( bufferCount == 0 )
        {
            return true;
        }
        else if( bufferCount == 1 )
        {
            return bufferQueue_.peekFirst().position() == 0;
        }

        return false;
    }
}
