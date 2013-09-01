/*
 * InputQueueTest.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Dec 1, 2011 at 11:15:12 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.ByteArrayInputStream;
import java.nio.channels.Channels;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.tcp.InputQueue} class.
 */
public final class InputQueueTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The capacity of byte buffers created by the fixture byte buffer pool. */
    private static final int BYTE_BUFFER_POOL_CAPACITY = 4096;

    /** The input queue under test in the fixture. */
    private InputQueue inputQueue_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InputQueueTest} class.
     */
    public InputQueueTest()
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
        inputQueue_ = new InputQueue( new ByteBufferPool( BYTE_BUFFER_POOL_CAPACITY ) );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method correctly
     * dequeues a message envelope when the input queue contains exactly one
     * message envelope.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_ContainsExactlyOneMessageEnvelope()
        throws Exception
    {
        final MessageEnvelope expectedMessageEnvelope = MessageEnvelope.fromMessage( new FakeMessage() );
        final byte[] expectedBytes = expectedMessageEnvelope.toByteArray();
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( expectedBytes ) ) );

        final MessageEnvelope actualMessageEnvelope = inputQueue_.dequeueMessageEnvelope();

        assertNotNull( actualMessageEnvelope );
        final byte[] actualBytes = actualMessageEnvelope.toByteArray();
        assertArrayEquals( expectedBytes, actualBytes );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method correctly
     * dequeues a message envelope when the input queue contains exactly one
     * message envelope that exceeds the capacity of a pool buffer.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_ContainsExactlyOneMessageEnvelope_MessageEnvelopeExceedsPoolBufferCapacity()
        throws Exception
    {
        final FakeMessage message = new FakeMessage();
        message.setContent( new byte[ BYTE_BUFFER_POOL_CAPACITY + 1 ] );
        final MessageEnvelope expectedMessageEnvelope = MessageEnvelope.fromMessage( message );
        final byte[] expectedBytes = expectedMessageEnvelope.toByteArray();
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( expectedBytes ) ) );

        final MessageEnvelope actualMessageEnvelope = inputQueue_.dequeueMessageEnvelope();
        assertNotNull( actualMessageEnvelope );
        final byte[] actualBytes = actualMessageEnvelope.toByteArray();
        assertArrayEquals( expectedBytes, actualBytes );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method correctly
     * dequeues a message envelope when the input queue contains exactly two
     * message envelopes.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_ContainsExactlyTwoMessageEnvelope2()
        throws Exception
    {
        final FakeMessage expectedMessage1 = new FakeMessage();
        expectedMessage1.setContent( new byte[] {
            0x11
        } );
        final MessageEnvelope expectedMessageEnvelope1 = MessageEnvelope.fromMessage( expectedMessage1 );
        final byte[] expectedBytes1 = expectedMessageEnvelope1.toByteArray();
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( expectedBytes1 ) ) );
        final FakeMessage expectedMessage2 = new FakeMessage();
        expectedMessage2.setContent( new byte[] {
            0x77
        } );
        final MessageEnvelope expectedMessageEnvelope2 = MessageEnvelope.fromMessage( expectedMessage2 );
        final byte[] expectedBytes2 = expectedMessageEnvelope2.toByteArray();
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( expectedBytes2 ) ) );

        final MessageEnvelope actualMessageEnvelope1 = inputQueue_.dequeueMessageEnvelope();
        final MessageEnvelope actualMessageEnvelope2 = inputQueue_.dequeueMessageEnvelope();

        assertNotNull( actualMessageEnvelope1 );
        final byte[] actualBytes1 = actualMessageEnvelope1.toByteArray();
        assertArrayEquals( expectedBytes1, actualBytes1 );
        assertNotNull( actualMessageEnvelope2 );
        final byte[] actualBytes2 = actualMessageEnvelope2.toByteArray();
        assertArrayEquals( expectedBytes2, actualBytes2 );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method returns
     * {@code null} when the input queue contains an incomplete message
     * envelope.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_ContainsIncompleteMessageEnvelope()
        throws Exception
    {
        final byte[] inputBytes = new byte[ 1 ];
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( inputBytes ) ) );

        final MessageEnvelope actualMessageEnvelope = inputQueue_.dequeueMessageEnvelope();

        assertNull( actualMessageEnvelope );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method correctly
     * dequeues a message envelope when the input queue contains one message
     * envelope and one additional byte.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_ContainsOneMessageEnvelopeAndOneAdditionalByte()
        throws Exception
    {
        final MessageEnvelope expectedMessageEnvelope = MessageEnvelope.fromMessage( new FakeMessage() );
        final byte[] expectedBytes = expectedMessageEnvelope.toByteArray();
        final byte[] inputBytes = new byte[ expectedBytes.length + 1 ];
        System.arraycopy( expectedBytes, 0, inputBytes, 0, expectedBytes.length );
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( inputBytes ) ) );

        final MessageEnvelope actualMessageEnvelope = inputQueue_.dequeueMessageEnvelope();
        assertNotNull( actualMessageEnvelope );
        final byte[] actualBytes = actualMessageEnvelope.toByteArray();
        assertArrayEquals( expectedBytes, actualBytes );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method returns
     * {@code null} when the input queue is empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_Empty()
        throws Exception
    {
        final MessageEnvelope actualMessageEnvelope = inputQueue_.dequeueMessageEnvelope();

        assertNull( actualMessageEnvelope );
    }

    /**
     * Ensures the {@link InputQueue#dequeueMessageEnvelope} method correctly
     * dequeues a message envelope when the input queue contains a partial
     * message envelope during the first call and a complete message envelope
     * during the second call.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDequeueMessageEnvelope_FirstCallContainsIncompleteMessageEnvelope_SecondCallContainsCompleteMessageEnvelope()
        throws Exception
    {
        final MessageEnvelope expectedMessageEnvelope = MessageEnvelope.fromMessage( new FakeMessage() );
        final byte[] expectedBytes = expectedMessageEnvelope.toByteArray();

        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( expectedBytes, 0, expectedBytes.length - 1 ) ) );
        final MessageEnvelope firstMessageEnvelope = inputQueue_.dequeueMessageEnvelope();
        inputQueue_.fillFrom( Channels.newChannel( new ByteArrayInputStream( expectedBytes, expectedBytes.length - 1, 1 ) ) );
        final MessageEnvelope actualMessageEnvelope = inputQueue_.dequeueMessageEnvelope();

        assertNull( firstMessageEnvelope );
        assertNotNull( actualMessageEnvelope );
        final byte[] actualBytes = actualMessageEnvelope.toByteArray();
        assertArrayEquals( expectedBytes, actualBytes );
    }
}
