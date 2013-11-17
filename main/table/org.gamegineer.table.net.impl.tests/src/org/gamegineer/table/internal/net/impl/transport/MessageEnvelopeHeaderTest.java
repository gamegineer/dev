/*
 * MessageEnvelopeHeaderTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Dec 5, 2011 at 7:52:23 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

import static org.junit.Assert.assertEquals;
import org.gamegineer.table.internal.net.impl.transport.MessageEnvelope.Header;
import org.junit.Test;

/**
 * A fixture for testing the {@link MessageEnvelope.Header} class.
 */
public final class MessageEnvelopeHeaderTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MessageEnvelopeHeaderTest}
     * class.
     */
    public MessageEnvelopeHeaderTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link Header#fromByteArray} method throws an exception when
     * passed an illegal byte array that has a length longer than the header
     * length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromByteArray_Bytes_Illegal_LengthLongerThanHeaderLength()
    {
        Header.fromByteArray( new byte[ Header.LENGTH + 1 ] );
    }

    /**
     * Ensures the {@link Header#fromByteArray} method throws an exception when
     * passed an illegal byte array that has a length shorter than the header
     * length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromByteArray_Bytes_Illegal_LengthShorterThanHeaderLength()
    {
        Header.fromByteArray( new byte[ Header.LENGTH - 1 ] );
    }

    /**
     * Ensures the {@link Header#fromByteArray} method throws an exception when
     * passed a {@code null} byte array.
     */
    @Test( expected = NullPointerException.class )
    public void testFromByteArray_Bytes_Null()
    {
        Header.fromByteArray( null );
    }

    /**
     * Ensures the {@link Header#getBodyLength} method correctly decodes the
     * length of the message envelope body from the header.
     */
    @Test
    public void testGetBodyLength()
    {
        final Header header = Header.fromByteArray( new byte[] {
            (byte)0xF0, (byte)0x0F, (byte)0x90, (byte)0x09
        } );

        assertEquals( 0x00009009, header.getBodyLength() );
    }

    /**
     * Ensures the {@link Header#getCorrelationId} method correctly decodes the
     * message correlation identifier from the header.
     */
    @Test
    public void testGetCorrelationId()
    {
        final Header header = Header.fromByteArray( new byte[] {
            (byte)0xF0, (byte)0x0F, (byte)0x90, (byte)0x09
        } );

        assertEquals( 0x0000000F, header.getCorrelationId() );
    }

    /**
     * Ensures the {@link Header#getId} method correctly decodes the message
     * identifier from the header.
     */
    @Test
    public void testGetId()
    {
        final Header header = Header.fromByteArray( new byte[] {
            (byte)0xF0, (byte)0x0F, (byte)0x90, (byte)0x09
        } );

        assertEquals( 0x000000F0, header.getId() );
    }

    /**
     * Ensures the {@link Header#toByteArray} method returns a byte array of
     * length {@link Header#LENGTH} when the byte array passed to the
     * constructor is longer than length {@link Header#LENGTH}.
     */
    @Test
    public void testToByteArray_ReturnValueLengthIsHeaderLength_BytesLengthLongerThanHeaderLength()
    {
        final Header header = new Header( new byte[ Header.LENGTH + 1 ] );

        assertEquals( Header.LENGTH, header.toByteArray().length );
    }
}
