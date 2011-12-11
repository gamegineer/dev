/*
 * ByteBufferUtils.java
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
 * Created on Dec 8, 2011 at 8:26:55 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with byte buffers.
 */
@ThreadSafe
final class ByteBufferUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ByteBufferUtils} class.
     */
    private ByteBufferUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new collection that contains duplicates of each byte buffer in
     * the specified collection.
     * 
     * @param buffers
     *        The byte buffer collection; must not be {@code null}.
     * 
     * @return A new collection of duplicate byte buffers; never {@code null}.
     */
    /* @NonNull */
    static Collection<ByteBuffer> duplicate(
        /* @NonNull */
        final Collection<ByteBuffer> buffers )
    {
        assert buffers != null;

        final Collection<ByteBuffer> duplicateBuffers = new ArrayList<ByteBuffer>( buffers.size() );
        for( final ByteBuffer buffer : buffers )
        {
            duplicateBuffers.add( buffer.duplicate() );
        }

        return duplicateBuffers;
    }

    /**
     * Performs a relative bulk get on the specified byte buffer collection.
     * 
     * @param buffers
     *        The collection of byte buffers; must not be {@code null}.
     * @param length
     *        The count of bytes to get; must not be negative.
     * 
     * @return The bytes read from the specified byte buffer collection or
     *         {@code null} if the byte buffer collection does not have enough
     *         remaining bytes to satisfy the request.
     */
    /* @Nullable */
    static byte[] get(
        /* @NonNull */
        final Collection<ByteBuffer> buffers,
        final int length )
    {
        assert buffers != null;
        assert length >= 0;

        if( !hasRemaining( buffers, length ) )
        {
            return null;
        }

        final byte[] bytes = new byte[ length ];
        int bytesRemaining = length;
        int offset = 0;
        for( final ByteBuffer buffer : buffers )
        {
            final int bytesToGet = (bytesRemaining > buffer.remaining()) ? buffer.remaining() : bytesRemaining;
            buffer.get( bytes, offset, bytesToGet );
            offset += bytesToGet;
            bytesRemaining -= bytesToGet;
            if( bytesRemaining == 0 )
            {
                break;
            }
        }

        return bytes;
    }

    /**
     * Indicates the specified byte buffer collection has at least the specified
     * count of bytes remaining.
     * 
     * @param buffers
     *        The collection of byte buffers; must not be {@code null}.
     * @param length
     *        The count of bytes remaining; must not be negative.
     * 
     * @return {@code true} if the specified byte buffer collection has at least
     *         the specified count of bytes remaining; otherwise {@code false}.
     */
    static boolean hasRemaining(
        /* @NonNull */
        final Collection<ByteBuffer> buffers,
        final int length )
    {
        assert buffers != null;
        assert length >= 0;

        int remaining = 0;
        for( final ByteBuffer buffer : buffers )
        {
            remaining += buffer.remaining();
            if( remaining >= length )
            {
                return true;
            }
        }

        return length == 0;
    }
}
