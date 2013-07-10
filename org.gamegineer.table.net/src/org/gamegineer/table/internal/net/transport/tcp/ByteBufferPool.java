/*
 * ByteBufferPool.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Feb 4, 2011 at 9:52:26 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;
import net.jcip.annotations.NotThreadSafe;

/**
 * A pool for sharing instances of the {@link java.nio.ByteBuffer} class.
 */
@NotThreadSafe
final class ByteBufferPool
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The maximum amount of memory the pool may use in bytes. */
    private static final int MAX_MEMORY_USAGE = 1024 * 1024;

    /** The capacity of all byte buffers created by this pool. */
    private final int byteBufferCapacity_;

    /** The collection of byte buffers in the pool. */
    private final Queue<ByteBuffer> byteBuffers_;

    /** The capacity of the pool. */
    private final int capacity_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ByteBufferPool} class.
     * 
     * @param byteBufferCapacity
     *        The capacity of all byte buffers created by this pool; must be
     *        positive.
     */
    ByteBufferPool(
        final int byteBufferCapacity )
    {
        assert byteBufferCapacity > 0;

        byteBufferCapacity_ = byteBufferCapacity;
        byteBuffers_ = new ArrayDeque<>();
        capacity_ = Math.max( 1, MAX_MEMORY_USAGE / byteBufferCapacity );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Returns the specified byte buffer to the pool.
     * 
     * @param byteBuffer
     *        The byte buffer to return; must not be {@code null}.
     */
    void returnByteBuffer(
        /* @NonNull */
        final ByteBuffer byteBuffer )
    {
        assert byteBuffer != null;

        if( byteBuffers_.size() < capacity_ )
        {
            byteBuffers_.offer( byteBuffer );
        }
    }

    /**
     * Takes a byte buffer from the pool.
     * 
     * <p>
     * When the returned byte buffer is no longer needed, it must be returned to
     * the pool using the {@link #returnByteBuffer(ByteBuffer)} method.
     * </p>
     * 
     * @return A byte buffer; never {@code null}.
     */
    /* @NonNull */
    ByteBuffer takeByteBuffer()
    {
        final ByteBuffer byteBuffer = byteBuffers_.poll();
        if( byteBuffer != null )
        {
            byteBuffer.clear();
            return byteBuffer;
        }

        return ByteBuffer.allocate( byteBufferCapacity_ );
    }
}
