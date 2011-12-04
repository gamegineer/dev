/*
 * MessageEnvelopeTest.java
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
 * Created on Feb 26, 2011 at 8:47:39 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.MessageEnvelope} class.
 */
public final class MessageEnvelopeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default message body for use in the fixture. */
    private static final byte[] DEFAULT_BODY = new byte[] {
        0x09, 0x01, 0x25
    };

    /** The default message correlation identifier for use in the fixture. */
    private static final int DEFAULT_CORRELATION_ID = 42;

    /** The default message identifier for use in the fixture. */
    private static final int DEFAULT_ID = 212;

    /** The message envelope under test in the fixture. */
    private MessageEnvelope messageEnvelope_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MessageEnvelopeTest} class.
     */
    public MessageEnvelopeTest()
    {
        super();
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
        messageEnvelope_ = new MessageEnvelope( DEFAULT_ID, DEFAULT_CORRELATION_ID, DEFAULT_BODY );
    }

    /**
     * Ensures the constructor throws an exception when passed a body whose
     * length is greater than the maximum body length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Body_Illegal_LengthGreaterThanMaxLength()
    {
        new MessageEnvelope( DEFAULT_ID, DEFAULT_CORRELATION_ID, new byte[ MessageEnvelope.MAXIMUM_BODY_LENGTH + 1 ] );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * body.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Body_Null()
    {
        new MessageEnvelope( DEFAULT_ID, DEFAULT_CORRELATION_ID, null );
    }

    /**
     * Ensures the constructor throws an exception when passed a correlation
     * identifier that is greater than the maximum correlation identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_CorrelationId_Illegal_GreaterThanMaxCorrelationId()
    {
        new MessageEnvelope( DEFAULT_ID, IMessage.MAXIMUM_ID + 1, DEFAULT_BODY );
    }

    /**
     * Ensures the constructor throws an exception when passed a correlation
     * identifier that is less than the minimum correlation identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_CorrelationId_Illegal_LessThanMinCorrelationId()
    {
        new MessageEnvelope( DEFAULT_ID, IMessage.NULL_CORRELATION_ID - 1, DEFAULT_BODY );
    }

    /**
     * Ensures the constructor throws an exception when passed an identifier
     * that is greater than the maximum identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Id_Illegal_GreaterThanMaxId()
    {
        new MessageEnvelope( IMessage.MAXIMUM_ID + 1, DEFAULT_CORRELATION_ID, DEFAULT_BODY );
    }

    /**
     * Ensures the constructor throws an exception when passed an identifier
     * that is less than the minimum identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Id_Illegal_LessThanMinId()
    {
        new MessageEnvelope( IMessage.MINIMUM_ID - 1, DEFAULT_CORRELATION_ID, DEFAULT_BODY );
    }

    /**
     * Ensures the {@code fromByteBuffer} method returns the correct value when
     * the buffer contains a complete message envelope.
     */
    @Test
    public void testFromByteBuffer_Buffer_Complete()
    {
        final ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.putInt( ((DEFAULT_ID << 24) & 0xFF000000) | ((DEFAULT_CORRELATION_ID << 16) & 0x00FF0000) | (DEFAULT_BODY.length & 0x0000FFFF) );
        buffer.put( DEFAULT_BODY );
        buffer.flip();

        final MessageEnvelope messageEnvelope = MessageEnvelope.fromByteBuffer( buffer );

        assertEquals( DEFAULT_ID, messageEnvelope.getId() );
        assertEquals( DEFAULT_CORRELATION_ID, messageEnvelope.getCorrelationId() );
        final ByteBuffer bodyBuffer = messageEnvelope_.getBody();
        final byte[] body = new byte[ bodyBuffer.remaining() ];
        bodyBuffer.get( body );
        assertArrayEquals( DEFAULT_BODY, body );
    }

    /**
     * Ensures the {@code fromByteBuffer} method returns {@code null} when the
     * buffer contains a message envelope with an incomplete body.
     */
    @Test
    public void testFromByteBuffer_Buffer_IncompleteBody()
    {
        final ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.putInt( ((DEFAULT_ID << 24) & 0xFF000000) | ((DEFAULT_CORRELATION_ID << 16) & 0x00FF0000) | (DEFAULT_BODY.length & 0x0000FFFF) );
        buffer.put( DEFAULT_BODY, 0, 1 );
        buffer.flip();

        assertNull( MessageEnvelope.fromByteBuffer( buffer ) );
    }

    /**
     * Ensures the {@code fromByteBuffer} method returns {@code null} when the
     * buffer contains a message envelope with an incomplete header.
     */
    @Test
    public void testFromByteBuffer_Buffer_IncompleteHeader()
    {
        final ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.putInt( DEFAULT_ID );
        buffer.flip();

        assertNull( MessageEnvelope.fromByteBuffer( buffer ) );
    }

    /**
     * Ensures the {@code fromByteBuffer} method throws an exception when passed
     * a {@code null} buffer.
     */
    @Test( expected = NullPointerException.class )
    public void testFromByteBuffer_Buffer_Null()
    {
        MessageEnvelope.fromByteBuffer( null );
    }

    /**
     * Ensures the {@code fromByteBuffers} method returns the correct value when
     * the buffer collection contains a complete message envelope.
     */
    @Test
    public void testFromByteBuffers_Buffers_Complete()
    {
        final ByteBuffer buffer1 = ByteBuffer.allocate( 128 );
        buffer1.putInt( ((DEFAULT_ID << 24) & 0xFF000000) | ((DEFAULT_CORRELATION_ID << 16) & 0x00FF0000) | (DEFAULT_BODY.length & 0x0000FFFF) );
        buffer1.flip();
        final ByteBuffer buffer2 = ByteBuffer.allocate( 128 );
        buffer2.put( DEFAULT_BODY, 0, 1 );
        buffer2.flip();
        final ByteBuffer buffer3 = ByteBuffer.allocate( 128 );
        buffer3.put( DEFAULT_BODY, 1, DEFAULT_BODY.length - 1 );
        buffer3.flip();

        final MessageEnvelope messageEnvelope = MessageEnvelope.fromByteBuffers( Arrays.asList( buffer1, buffer2, buffer3 ) );

        assertEquals( DEFAULT_ID, messageEnvelope.getId() );
        assertEquals( DEFAULT_CORRELATION_ID, messageEnvelope.getCorrelationId() );
        final ByteBuffer bodyBuffer = messageEnvelope_.getBody();
        final byte[] body = new byte[ bodyBuffer.remaining() ];
        bodyBuffer.get( body );
        assertArrayEquals( DEFAULT_BODY, body );
    }

    /**
     * Ensures the {@code fromByteBuffers} method throws an exception when
     * passed an illegal buffer collection that contains a {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromByteBuffers_Buffers_Illegal_ContainsNullElement()
    {
        MessageEnvelope.fromByteBuffers( Collections.<ByteBuffer>singletonList( null ) );
    }

    /**
     * Ensures the {@code fromByteBuffers} method returns {@code null} when the
     * buffer collection contains a message envelope with an incomplete body.
     */
    @Test
    public void testFromByteBuffers_Buffers_IncompleteBody()
    {
        final ByteBuffer buffer1 = ByteBuffer.allocate( 128 );
        buffer1.putInt( ((DEFAULT_ID << 24) & 0xFF000000) | ((DEFAULT_CORRELATION_ID << 16) & 0x00FF0000) | (DEFAULT_BODY.length & 0x0000FFFF) );
        buffer1.flip();
        final ByteBuffer buffer2 = ByteBuffer.allocate( 128 );
        buffer2.put( DEFAULT_BODY, 0, 1 );
        buffer2.flip();

        assertNull( MessageEnvelope.fromByteBuffers( Arrays.asList( buffer1, buffer2 ) ) );
    }

    /**
     * Ensures the {@code fromByteBuffers} method returns {@code null} when the
     * buffer collection contains a message envelope with an incomplete header.
     */
    @Test
    public void testFromByteBuffers_Buffers_IncompleteHeader()
    {
        final ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.putInt( DEFAULT_ID );
        buffer.flip();

        assertNull( MessageEnvelope.fromByteBuffers( Arrays.asList( buffer ) ) );
    }

    /**
     * Ensures the {@code fromByteBuffers} method throws an exception when
     * passed a {@code null} buffer collection.
     */
    @Test( expected = NullPointerException.class )
    public void testFromByteBuffers_Buffers_Null()
    {
        MessageEnvelope.fromByteBuffers( null );
    }

    /**
     * Ensures the {@code fromMessage} method returns the correct message
     * envelope.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testFromMessage()
        throws Exception
    {
        final IMessage expectedValue = new FakeMessage();
        expectedValue.setId( IMessage.MAXIMUM_ID );
        expectedValue.setCorrelationId( IMessage.MAXIMUM_ID );

        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( expectedValue );
        final IMessage actualValue = messageEnvelope.getBodyAsMessage();

        assertEquals( expectedValue.getId(), messageEnvelope.getId() );
        assertEquals( expectedValue.getCorrelationId(), messageEnvelope.getCorrelationId() );
        assertEquals( expectedValue.getId(), actualValue.getId() );
        assertEquals( expectedValue.getCorrelationId(), actualValue.getCorrelationId() );
    }

    /**
     * Ensures the {@code fromMessage} method throws an exception when passed a
     * {@code null} message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testFromMessage_Message_Null()
        throws Exception
    {
        MessageEnvelope.fromMessage( null );
    }

    /**
     * Ensures the {@code getBody} method returns the message body.
     */
    @Test
    public void testGetBody()
    {
        final ByteBuffer bodyBuffer = messageEnvelope_.getBody();
        final byte[] body = new byte[ bodyBuffer.remaining() ];
        bodyBuffer.get( body );

        assertArrayEquals( DEFAULT_BODY, body );
    }

    /**
     * Ensures the {@code getCorrelationId} method returns the message
     * correlation identifier.
     */
    @Test
    public void testGetCorrelationId()
    {
        assertEquals( DEFAULT_CORRELATION_ID, messageEnvelope_.getCorrelationId() );
    }

    /**
     * Ensures the {@code getId} method returns the message identifier.
     */
    @Test
    public void testGetId()
    {
        assertEquals( DEFAULT_ID, messageEnvelope_.getId() );
    }
}
