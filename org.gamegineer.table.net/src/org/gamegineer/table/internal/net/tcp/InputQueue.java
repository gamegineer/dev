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

package org.gamegineer.table.internal.net.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * A message input queue.
 */
@ThreadSafe
final class InputQueue
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** An immutable empty buffer. */
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate( 0 ).asReadOnlyBuffer();

    /** The buffer associated with the queue. */
    @GuardedBy( "lock_" )
    private ByteBuffer buffer_;

    /** The buffer pool associated with the queue. */
    private final ByteBufferPool bufferPool_;

    /** The instance lock. */
    private final Object lock_;


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

        buffer_ = null;
        bufferPool_ = bufferPool;
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Removes up to the specified count of bytes from the queue.
     * 
     * @param count
     *        The maximum count of bytes to remove from the queue; must not be
     *        negative.
     * 
     * @return A buffer containing the bytes removed from the queue; never
     *         {@code null}.
     */
    /* @NonNull */
    ByteBuffer dequeueBytes(
        final int count )
    {
        assert count >= 0;

        synchronized( lock_ )
        {
            if( (count == 0) || isEmpty() )
            {
                return EMPTY_BUFFER;
            }

            final int outgoingCapacity = Math.min( count, buffer_.position() );
            final ByteBuffer outgoingBuffer = ByteBuffer.allocate( outgoingCapacity );

            buffer_.flip();

            if( buffer_.remaining() <= outgoingBuffer.remaining() )
            {
                outgoingBuffer.put( buffer_ );
            }
            else
            {
                while( outgoingBuffer.hasRemaining() )
                {
                    outgoingBuffer.put( buffer_.get() );
                }
            }

            if( buffer_.hasRemaining() )
            {
                buffer_.compact();
            }
            else
            {
                bufferPool_.returnByteBuffer( buffer_ );
                buffer_ = null;
            }

            outgoingBuffer.flip();

            return outgoingBuffer;
        }
    }

    /**
     * Discards up to the specified count of bytes from the queue.
     * 
     * @param count
     *        The maximum count of bytes to remove from the queue; must not be
     *        negative.
     */
    void discardBytes(
        final int count )
    {
        dequeueBytes( count );
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
        final ByteChannel channel )
        throws IOException
    {
        assert channel != null;

        synchronized( lock_ )
        {
            if( buffer_ == null )
            {
                buffer_ = bufferPool_.takeByteBuffer();
            }

            return channel.read( buffer_ );
        }
    }

    /**
     * Gets the index of the specified byte in the queue.
     * 
     * @param b
     *        The byte for which to search.
     * 
     * @return The index of the specified byte in the queue or -1 if the byte is
     *         not found.
     */
    int indexOf(
        final byte b )
    {
        synchronized( lock_ )
        {
            if( buffer_ == null )
            {
                return -1;
            }

            for( int index = 0, position = buffer_.position(); index < position; ++index )
            {
                if( b == buffer_.get( index ) )
                {
                    return index;
                }
            }
        }

        return -1;
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
            return (buffer_ == null) || (buffer_.position() == 0);
        }
    }
}
