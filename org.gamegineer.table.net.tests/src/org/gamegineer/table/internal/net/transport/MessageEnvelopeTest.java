/*
 * MessageEnvelopeTest.java
 * Copyright 2008-2012 Gamegineer.org
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
import org.gamegineer.table.internal.net.transport.MessageEnvelope.Header;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.MessageEnvelope} class.
 */
public final class MessageEnvelopeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MessageEnvelopeTest} class.
     */
    public MessageEnvelopeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link MessageEnvelope#fromByteArray} method returns the
     * correct message envelope.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testFromByteArray()
        throws Exception
    {
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MAXIMUM_ID );
        message.setCorrelationId( IMessage.MAXIMUM_ID );
        message.setContent( new byte[] {
            (byte)0x00, (byte)0x11, (byte)0x22, (byte)0x33, //
            (byte)0x44, (byte)0x55, (byte)0x66, (byte)0x77, //
            (byte)0x88, (byte)0x99, (byte)0xAA, (byte)0xBB, //
            (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF
        } );
        final MessageEnvelope expectedValue = MessageEnvelope.fromMessage( message );

        final MessageEnvelope actualValue = MessageEnvelope.fromByteArray( expectedValue.toByteArray() );

        assertArrayEquals( expectedValue.toByteArray(), actualValue.toByteArray() );
    }

    /**
     * Ensures the {@link MessageEnvelope#fromByteArray} method throws an
     * exception when passed an illegal byte array that has a length longer than
     * the total message envelope length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromByteArray_Bytes_Illegal_LengthLongerThanTotalLength()
    {
        MessageEnvelope.fromByteArray( new byte[] {
            (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0xFF
        } );
    }

    /**
     * Ensures the {@link MessageEnvelope#fromByteArray} method throws an
     * exception when passed an illegal byte array that has a length shorter
     * than the header length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromByteArray_Bytes_Illegal_LengthShorterThanHeaderLength()
    {
        MessageEnvelope.fromByteArray( new byte[ Header.LENGTH - 1 ] );
    }

    /**
     * Ensures the {@link MessageEnvelope#fromByteArray} method throws an
     * exception when passed an illegal byte array that has a length shorter
     * than the total message envelope length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromByteArray_Bytes_Illegal_LengthShorterThanTotalLength()
    {
        MessageEnvelope.fromByteArray( new byte[] {
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF
        } );
    }

    /**
     * Ensures the {@link MessageEnvelope#fromByteArray} method throws an
     * exception when passed a {@code null} byte array.
     */
    @Test( expected = NullPointerException.class )
    public void testFromByteArray_Bytes_Null()
    {
        MessageEnvelope.fromByteArray( null );
    }

    /**
     * Ensures the {@link MessageEnvelope#fromMessage} method returns the
     * correct message envelope.
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
        final IMessage actualValue = messageEnvelope.getMessage();

        assertEquals( expectedValue.getId(), messageEnvelope.getHeader().getId() );
        assertEquals( expectedValue.getCorrelationId(), messageEnvelope.getHeader().getCorrelationId() );
        assertEquals( expectedValue.getId(), actualValue.getId() );
        assertEquals( expectedValue.getCorrelationId(), actualValue.getCorrelationId() );
    }

    /**
     * Ensures the {@link MessageEnvelope#fromMessage} method throws an
     * exception when passed a {@code null} message.
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
}
