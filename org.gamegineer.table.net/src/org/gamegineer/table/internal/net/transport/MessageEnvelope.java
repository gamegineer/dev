/*
 * MessageEnvelope.java
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
 * Created on Feb 24, 2011 at 8:09:57 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import net.jcip.annotations.NotThreadSafe;

/**
 * A network protocol message envelope.
 */
@NotThreadSafe
public final class MessageEnvelope
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The header length in bytes. */
    private static final int HEADER_LENGTH = 4;

    /** The maximum message body length in bytes. */
    public static final int MAXIMUM_BODY_LENGTH = 0xFFFF;

    /** The network representation of the message envelope. */
    private final ByteBuffer buffer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MessageEnvelope} class.
     * 
     * @param id
     *        The message identifier.
     * @param correlationId
     *        The message correlation identifier.
     * @param body
     *        The message body; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code id} is less than {@link IMessage#MINIMUM_ID} or greater
     *         than {@link IMessage#MAXIMUM_ID}; if {@code correlationId} is
     *         less than {@link IMessage#MINIMUM_ID} or greater than
     *         {@link IMessage#MAXIMUM_ID} or not equal to
     *         {@link IMessage#NULL_CORRELATION_ID}; if the length of {@code
     *         body} is greater than {@link #MAXIMUM_BODY_LENGTH}.
     * @throws java.lang.NullPointerException
     *         If {@code body} is {@code null}.
     */
    public MessageEnvelope(
        final int id,
        final int correlationId,
        /* @NonNull */
        final byte[] body )
    {
        assertArgumentLegal( (id >= IMessage.MINIMUM_ID) && (id <= IMessage.MAXIMUM_ID), "id" ); //$NON-NLS-1$
        assertArgumentLegal( (correlationId == IMessage.NULL_CORRELATION_ID) || ((correlationId >= IMessage.MINIMUM_ID) && (correlationId <= IMessage.MAXIMUM_ID)), "correlationId" ); //$NON-NLS-1$
        assertArgumentNotNull( body, "body" ); //$NON-NLS-1$
        assertArgumentLegal( body.length <= MAXIMUM_BODY_LENGTH, "body" ); //$NON-NLS-1$

        buffer_ = ByteBuffer.allocate( HEADER_LENGTH + body.length );
        final int header = ((id << 24) & 0xFF000000) //
            | ((correlationId << 16) & 0x00FF0000) //
            | (body.length & 0x0000FFFF);
        buffer_.putInt( header );
        buffer_.put( body );
        buffer_.flip();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the specified byte buffer contains a complete message envelope.
     * 
     * @param buffer
     *        The byte buffer; must not be {@code null}.
     * 
     * @return {@code true} if the specified byte buffer contains a complete
     *         message envelope; otherwise {@code false}.
     */
    private static boolean containsCompleteMessageEnvelope(
        /* @NonNull */
        final ByteBuffer buffer )
    {
        assert buffer != null;

        if( buffer.remaining() < HEADER_LENGTH )
        {
            return false;
        }

        final int header = buffer.getInt( 0 );
        final int bodyLength = decodeMessageLength( header );
        if( buffer.remaining() < (HEADER_LENGTH + bodyLength) )
        {
            return false;
        }

        return true;
    }

    /**
     * Decodes the message correlation identifier from the specified encoded
     * header value.
     * 
     * @param header
     *        The encoded message header.
     * 
     * @return The message correlation identifier.
     */
    private static int decodeMessageCorrelationId(
        final int header )
    {
        return (header >>> 16) & 0x000000FF;
    }

    /**
     * Decodes the message length from the specified encoded header value.
     * 
     * @param header
     *        The encoded message header.
     * 
     * @return The message length.
     */
    private static int decodeMessageLength(
        final int header )
    {
        return header & 0x0000FFFF;
    }

    /**
     * Decodes the message identifier from the specified encoded header value.
     * 
     * @param header
     *        The encoded message header.
     * 
     * @return The message identifier.
     */
    private static int decodeMessageId(
        final int header )
    {
        return (header >>> 24) & 0x000000FF;
    }

    /**
     * Deserializes the message contained in the specified byte array.
     * 
     * @param body
     *        The byte array that contains the serialized message; must not be
     *        {@code null}.
     * 
     * @return The deserialized message; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     * @throws java.lang.ClassNotFoundException
     *         If the class of the serialized message cannot be found.
     */
    /* @NonNull */
    private static IMessage deserializeMessage(
        /* @NonNull */
        final byte[] body )
        throws IOException, ClassNotFoundException
    {
        assert body != null;

        final ObjectInputStream stream = new ObjectInputStream( new ByteArrayInputStream( body ) );
        try
        {
            return (IMessage)stream.readObject();
        }
        finally
        {
            stream.close();
        }
    }

    /**
     * Creates a new message envelope from the specified byte buffer.
     * 
     * @param buffer
     *        The byte buffer; must not be {@code null}.
     * 
     * @return A new message envelope or {@code null} if a complete message
     *         envelope is available from the specified byte buffer.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code buffer} is {@code null}.
     */
    /* @Nullable */
    public static MessageEnvelope fromByteBuffer(
        /* @NonNull */
        final ByteBuffer buffer )
    {
        assertArgumentNotNull( buffer, "buffer" ); //$NON-NLS-1$

        if( !containsCompleteMessageEnvelope( buffer ) )
        {
            return null;
        }

        final int header = buffer.getInt();
        final int id = decodeMessageId( header );
        final int correlationId = decodeMessageCorrelationId( header );
        final int bodyLength = decodeMessageLength( header );
        final byte[] body = new byte[ bodyLength ];
        buffer.get( body );

        return new MessageEnvelope( id, correlationId, body );
    }

    /**
     * Creates a new message envelope from the specified message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return A new message envelope; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If the specified message cannot be serialized to the message
     *         envelope.
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    /* @NonNull */
    public static MessageEnvelope fromMessage(
        /* @NonNull */
        final IMessage message )
        throws IOException
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

        return new MessageEnvelope( message.getId(), message.getCorrelationId(), serializeMessage( message ) );
    }

    /**
     * Gets an immutable view of the message envelope body.
     * 
     * @return An immutable view of the message envelope body; never {@code
     *         null}.
     */
    /* @NonNull */
    public ByteBuffer getBody()
    {
        final ByteBuffer body = buffer_.slice().asReadOnlyBuffer();
        body.position( HEADER_LENGTH );
        return body;
    }

    /**
     * Gets the message envelope body as a message.
     * 
     * @return The message envelope body as a message; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If the message cannot be deserialized from the message envelope
     *         body.
     * @throws java.lang.ClassNotFoundException
     *         If the class of the message cannot be found.
     */
    /* @NonNull */
    public IMessage getBodyAsMessage()
        throws IOException, ClassNotFoundException
    {
        final ByteBuffer buffer = getBody();
        final byte[] body = new byte[ buffer.remaining() ];
        buffer.get( body );
        return deserializeMessage( body );
    }

    /**
     * Gets the message correlation identifier.
     * 
     * @return The message correlation identifier.
     */
    public int getCorrelationId()
    {
        return decodeMessageCorrelationId( buffer_.getInt( 0 ) );
    }

    /**
     * Gets the message identifier.
     * 
     * @return The message identifier.
     */
    public int getId()
    {
        return decodeMessageId( buffer_.getInt( 0 ) );
    }

    /**
     * Serializes the specified message to a byte array.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return A byte array that contains the serialized message; never {@code
     *         null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private static byte[] serializeMessage(
        /* @NonNull */
        final IMessage message )
        throws IOException
    {
        assert message != null;

        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectStream = new ObjectOutputStream( byteStream );
        try
        {
            objectStream.writeObject( message );
        }
        finally
        {
            objectStream.close();
        }

        return byteStream.toByteArray();
    }

    /**
     * Gets an immutable byte buffer view of the message envelope.
     * 
     * @return An immutable byte buffer view of the message envelope; never
     *         {@code null}.
     */
    /* @NonNull */
    public ByteBuffer toByteBuffer()
    {
        return buffer_.asReadOnlyBuffer();
    }
}
