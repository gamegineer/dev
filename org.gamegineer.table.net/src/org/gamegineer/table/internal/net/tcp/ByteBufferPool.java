/*
 * ByteBufferPool.java
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
 * Created on Feb 4, 2011 at 9:52:26 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.nio.ByteBuffer;
import net.jcip.annotations.Immutable;

/**
 * A pool for sharing instances of the {@link java.nio.ByteBuffer} class.
 */
@Immutable
final class ByteBufferPool
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default capacity of all byte buffers created by this factory. */
    private final int capacity_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ByteBufferPool} class.
     * 
     * @param capacity
     *        The capacity of all byte buffers created by this pool; must not be
     *        negative.
     */
    ByteBufferPool(
        final int capacity )
    {
        assert capacity >= 0;

        capacity_ = capacity;
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

        // do nothing
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
        return ByteBuffer.allocate( capacity_ );
    }
}
