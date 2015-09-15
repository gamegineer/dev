/*
 * ByteBufferPoolTest.java
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
 * Created on Dec 21, 2011 at 9:04:37 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.nio.ByteBuffer;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ByteBufferPool} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class ByteBufferPoolTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The capacity of byte buffers created by the pool in bytes. */
    private static final int BYTE_BUFFER_CAPACITY = 16;

    /** The byte buffer pool under test in the fixture. */
    private ByteBufferPool byteBufferPool_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ByteBufferPoolTest} class.
     */
    public ByteBufferPoolTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        byteBufferPool_ = new ByteBufferPool( BYTE_BUFFER_CAPACITY );
    }

    /**
     * Ensures the {@link ByteBufferPool#takeByteBuffer} method clears an
     * existing byte buffer before returning it.
     */
    @Test
    public void testTakeByteBuffer_ClearsExistingByteBuffer()
    {
        final ByteBuffer byteBuffer1 = byteBufferPool_.takeByteBuffer();
        byteBuffer1.position( byteBuffer1.limit() );
        byteBufferPool_.returnByteBuffer( byteBuffer1 );

        final ByteBuffer byteBuffer2 = byteBufferPool_.takeByteBuffer();

        assertEquals( 0, byteBuffer2.position() );
        assertEquals( byteBuffer2.capacity(), byteBuffer2.limit() );
    }

    /**
     * Ensures the {@link ByteBufferPool#takeByteBuffer} method returns an
     * existing byte buffer when one exists in the pool.
     */
    @Test
    public void testTakeByteBuffer_ReturnsExistingByteBuffer()
    {
        final ByteBuffer byteBuffer1 = byteBufferPool_.takeByteBuffer();
        byteBufferPool_.returnByteBuffer( byteBuffer1 );

        final ByteBuffer byteBuffer2 = byteBufferPool_.takeByteBuffer();

        assertSame( byteBuffer1, byteBuffer2 );
    }
}
