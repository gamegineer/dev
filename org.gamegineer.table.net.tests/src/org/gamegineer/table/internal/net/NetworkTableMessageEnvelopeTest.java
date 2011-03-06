/*
 * NetworkTableMessageEnvelopeTest.java
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

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.nio.ByteBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.NetworkTableMessageEnvelope} class.
 */
public final class NetworkTableMessageEnvelopeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default message body for use in the fixture. */
    private static final byte[] DEFAULT_BODY = new byte[] {
        0x09, 0x01, 0x25
    };

    /** The default message identifier for use in the fixture. */
    private static final int DEFAULT_ID = 2112;

    /** The default message tag for use in the fixture. */
    private static final int DEFAULT_TAG = 42;

    /** The message envelope under test in the fixture. */
    private NetworkTableMessageEnvelope messageEnvelope_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableMessageEnvelopeTest}
     * class.
     */
    public NetworkTableMessageEnvelopeTest()
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
        messageEnvelope_ = new NetworkTableMessageEnvelope( DEFAULT_ID, DEFAULT_TAG, DEFAULT_BODY );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        messageEnvelope_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a body whose
     * length is greater than the maximum body length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Body_Illegal_LengthGreaterThanMaxLength()
    {
        new NetworkTableMessageEnvelope( DEFAULT_ID, DEFAULT_TAG, new byte[ NetworkTableMessageEnvelope.MAX_BODY_LENGTH + 1 ] );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * body.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Body_Null()
    {
        new NetworkTableMessageEnvelope( DEFAULT_ID, DEFAULT_TAG, null );
    }

    /**
     * Ensures the constructor throws an exception when passed a tag that is
     * greater than the maximum tag.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Tag_Illegal_GreaterThanMaxTag()
    {
        new NetworkTableMessageEnvelope( DEFAULT_ID, AbstractNetworkTableMessage.MAX_TAG + 1, DEFAULT_BODY );
    }

    /**
     * Ensures the constructor throws an exception when passed a tag that is
     * less than zero.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Tag_Illegal_LessThanZero()
    {
        new NetworkTableMessageEnvelope( DEFAULT_ID, -1, DEFAULT_BODY );
    }

    /**
     * Ensures the {@code fromByteBuffer} method returns the correct value when
     * the buffer contains a complete message envelope.
     */
    @Test
    public void testFromByteBuffer_Buffer_Complete()
    {
        final ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.putInt( DEFAULT_ID );
        buffer.putInt( ((DEFAULT_TAG << 22) & 0xFFC00000) | (DEFAULT_BODY.length & 0x003FFFFF) );
        buffer.put( DEFAULT_BODY );
        buffer.flip();

        final NetworkTableMessageEnvelope messageEnvelope = NetworkTableMessageEnvelope.fromByteBuffer( buffer );

        assertEquals( DEFAULT_ID, messageEnvelope.getId() );
        assertEquals( DEFAULT_TAG, messageEnvelope.getTag() );
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
        buffer.putInt( DEFAULT_ID );
        buffer.putInt( ((DEFAULT_TAG << 22) & 0xFFC00000) | (DEFAULT_BODY.length & 0x003FFFFF) );
        buffer.put( DEFAULT_BODY, 0, 1 );
        buffer.flip();

        assertNull( NetworkTableMessageEnvelope.fromByteBuffer( buffer ) );
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

        assertNull( NetworkTableMessageEnvelope.fromByteBuffer( buffer ) );
    }

    /**
     * Ensures the {@code fromByteBuffer} method throws an exception when passed
     * a {@code null} buffer.
     */
    @Test( expected = NullPointerException.class )
    public void testFromByteBuffer_Buffer_Null()
    {
        NetworkTableMessageEnvelope.fromByteBuffer( null );
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
        final AbstractNetworkTableMessage expectedValue = new FakeMessage();
        expectedValue.setTag( FakeMessage.MAX_TAG );

        final NetworkTableMessageEnvelope messageEnvelope = NetworkTableMessageEnvelope.fromMessage( expectedValue );
        final AbstractNetworkTableMessage actualValue = messageEnvelope.getBodyAsMessage();

        assertEquals( expectedValue.getId(), messageEnvelope.getId() );
        assertEquals( expectedValue.getTag(), messageEnvelope.getTag() );
        assertEquals( expectedValue.getId(), actualValue.getId() );
        assertEquals( expectedValue.getTag(), actualValue.getTag() );
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
        NetworkTableMessageEnvelope.fromMessage( null );
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
     * Ensures the {@code getId} method returns the message identifier.
     */
    @Test
    public void testGetId()
    {
        assertEquals( DEFAULT_ID, messageEnvelope_.getId() );
    }

    /**
     * Ensures the {@code getId} method returns the message tag.
     */
    @Test
    public void testGetTag()
    {
        assertEquals( DEFAULT_TAG, messageEnvelope_.getTag() );
    }
}
