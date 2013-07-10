/*
 * ByteBufferUtilsTest.java
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
 * Created on Dec 9, 2011 at 7:56:29 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.tcp.ByteBufferUtils}
 * class.
 */
public final class ByteBufferUtilsTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of byte buffers for use in the fixture. */
    private List<ByteBuffer> buffers_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ByteBufferUtilsTest} class.
     */
    public ByteBufferUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts the specified byte buffer has the specified position and
     * remaining.
     * 
     * @param buffer
     *        The byte buffer; must not be {@code null}.
     * @param position
     *        The expected byte buffer position.
     * @param remaining
     *        The expected byte buffer remaining.
     */
    private static void assertByteBuffer(
        /* @NonNull */
        final ByteBuffer buffer,
        final int position,
        final int remaining )
    {
        assert buffer != null;

        assertEquals( position, buffer.position() );
        assertEquals( remaining, buffer.remaining() );
    }

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
        buffers_ = new ArrayList<>();
        buffers_.add( ByteBuffer.wrap( new byte[] {
            0x00, 0x01, 0x02, 0x03
        } ) );
        buffers_.add( ByteBuffer.wrap( new byte[] {
            0x04, 0x05, 0x06, 0x07
        } ) );
        buffers_.add( ByteBuffer.wrap( new byte[] {
            0x08, 0x09, 0x0A, 0x0B
        } ) );
        buffers_.add( ByteBuffer.wrap( new byte[] {
            0x0C, 0x0D, 0x0E, 0x0F
        } ) );
    }

    /**
     * Ensures the {@link ByteBufferUtils#duplicate} method returns a collection
     * of duplicate buffers.
     */
    @Test
    public void testDuplicate()
    {
        final Collection<ByteBuffer> duplicateBuffers = ByteBufferUtils.duplicate( buffers_ );
        for( final ByteBuffer duplicateBuffer : duplicateBuffers )
        {
            duplicateBuffer.get();
        }

        for( final ByteBuffer buffer : buffers_ )
        {
            assertByteBuffer( buffer, 0, 4 );
        }
    }

    /**
     * Ensures the {@link ByteBufferUtils#fill} method correctly fills the
     * destination buffer when the source buffer has more elements remaining
     * than the destination buffer.
     */
    @Test
    public void testFill_SourceBuffer_RemainingGreaterThanDestinationBuffer()
    {
        final byte[] inputValue = new byte[] {
            0x00, 0x01, 0x02, 0x03
        };
        final byte[] expectedValue = new byte[] {
            0x00, 0x01
        };
        final ByteBuffer sourceBuffer = ByteBuffer.wrap( inputValue );
        final ByteBuffer destinationBuffer = ByteBuffer.allocate( expectedValue.length );

        ByteBufferUtils.fill( destinationBuffer, sourceBuffer );
        destinationBuffer.flip();
        final byte[] actualValue = new byte[ destinationBuffer.remaining() ];
        destinationBuffer.get( actualValue );

        assertArrayEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link ByteBufferUtils#fill} method correctly fills the
     * destination buffer when the source buffer has fewer elements remaining
     * than the destination buffer.
     */
    @Test
    public void testFill_SourceBuffer_RemainingLessThanDestinationBuffer()
    {
        final byte[] inputValue = new byte[] {
            0x00, 0x01
        };
        final byte[] expectedValue = new byte[] {
            0x00, 0x01
        };
        final ByteBuffer sourceBuffer = ByteBuffer.wrap( inputValue );
        final ByteBuffer destinationBuffer = ByteBuffer.allocate( expectedValue.length );

        ByteBufferUtils.fill( destinationBuffer, sourceBuffer );
        destinationBuffer.flip();
        final byte[] actualValue = new byte[ destinationBuffer.remaining() ];
        destinationBuffer.get( actualValue );

        assertArrayEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link ByteBufferUtils#get} method returns the correct value
     * when passed a length that is equal to the remaining length of the byte
     * buffer collection.
     */
    @Test
    public void testGet_Length_EqualToRemainingLength_SpansOneBuffer()
    {
        final byte[] expectedValue = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, //
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
        };

        final byte[] actualValue = ByteBufferUtils.get( buffers_, expectedValue.length );

        assertArrayEquals( expectedValue, actualValue );
        for( final ByteBuffer buffer : buffers_ )
        {
            assertByteBuffer( buffer, 4, 0 );
        }
    }

    /**
     * Ensures the {@link ByteBufferUtils#get} method returns {@code null} when
     * passed a length that is greater than the remaining length of the byte
     * buffer collection.
     */
    @Test
    public void testGet_Length_GreaterThanRemainingLength()
    {
        assertNull( ByteBufferUtils.get( buffers_, Integer.MAX_VALUE ) );
    }

    /**
     * Ensures the {@link ByteBufferUtils#get} method returns the correct value
     * when passed a length that is less than the remaining length of the byte
     * buffer collection and the required bytes span one buffer.
     */
    @Test
    public void testGet_Length_LessThanRemainingLength_SpansOneBuffer()
    {
        final byte[] expectedValue = new byte[] {
            0x00, 0x01, 0x02
        };

        final byte[] actualValue = ByteBufferUtils.get( buffers_, expectedValue.length );

        assertArrayEquals( expectedValue, actualValue );
        assertByteBuffer( buffers_.get( 0 ), 3, 1 );
        assertByteBuffer( buffers_.get( 1 ), 0, 4 );
        assertByteBuffer( buffers_.get( 2 ), 0, 4 );
        assertByteBuffer( buffers_.get( 3 ), 0, 4 );
    }

    /**
     * Ensures the {@link ByteBufferUtils#get} method returns the correct value
     * when passed a length that is less than the remaining length of the byte
     * buffer collection and the required bytes span two buffers.
     */
    @Test
    public void testGet_Length_LessThanRemainingLength_SpansTwoBuffers()
    {
        final byte[] expectedValue = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05
        };

        final byte[] actualValue = ByteBufferUtils.get( buffers_, expectedValue.length );

        assertArrayEquals( expectedValue, actualValue );
        assertByteBuffer( buffers_.get( 0 ), 4, 0 );
        assertByteBuffer( buffers_.get( 1 ), 2, 2 );
        assertByteBuffer( buffers_.get( 2 ), 0, 4 );
        assertByteBuffer( buffers_.get( 3 ), 0, 4 );
    }

    /**
     * Ensures the {@link ByteBufferUtils#get} method returns the correct value
     * when passed a length of zero.
     */
    @Test
    public void testGet_Length_Zero()
    {
        final byte[] expectedValue = new byte[ 0 ];

        final byte[] actualValue = ByteBufferUtils.get( buffers_, expectedValue.length );

        assertArrayEquals( expectedValue, actualValue );
        for( final ByteBuffer buffer : buffers_ )
        {
            assertByteBuffer( buffer, 0, 4 );
        }
    }

    /**
     * Ensures the {@link ByteBufferUtils#hasRemaining} method returns
     * {@code false} when passed a length that is greater than the remaining
     * length of the byte buffer collection.
     */
    @Test
    public void testHasRemaining_Length_GreaterThanRemainingLength()
    {
        assertFalse( ByteBufferUtils.hasRemaining( buffers_, 17 ) );
    }

    /**
     * Ensures the {@link ByteBufferUtils#hasRemaining} method returns
     * {@code true} when passed a length that is less than the remaining length
     * of the byte buffer collection.
     */
    @Test
    public void testHasRemaining_Length_LessThanRemainingLength()
    {
        assertTrue( ByteBufferUtils.hasRemaining( buffers_, 16 ) );
    }

    /**
     * Ensures the {@link ByteBufferUtils#hasRemaining} method returns
     * {@code true} when passed an empty byte buffer collection and a length of
     * zero.
     */
    @Test
    public void testHasRemaining_Buffers_Empty_Length_Zero()
    {
        assertTrue( ByteBufferUtils.hasRemaining( Collections.<ByteBuffer>emptyList(), 0 ) );
    }
}
