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

    /** The offset to the message identifier in the header in bytes. */
    private static final int HEADER_ID_OFFSET = 0;

    /** The header length in bytes. */
    private static final int HEADER_LENGTH = 8;

    /** The offset to the message tag and length in the header in bytes. */
    private static final int HEADER_TAG_AND_LENGTH_OFFSET = 4;

    /** The maximum message body length in bytes. */
    public static final int MAX_BODY_LENGTH = 0x003FFFFF;

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
     * @param tag
     *        The message tag.
     * @param body
     *        The message body; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code tag} is less than {@link IMessage#MAX_TAG} or greater
     *         than {@link IMessage#MAX_TAG} or not equal to
     *         {@link IMessage#NO_TAG}; if the length of {@code body} is greater
     *         than {@link #MAX_BODY_LENGTH}.
     * @throws java.lang.NullPointerException
     *         If {@code body} is {@code null}.
     */
    public MessageEnvelope(
        final int id,
        final int tag,
        /* @NonNull */
        final byte[] body )
    {
        assertArgumentLegal( (tag == IMessage.NO_TAG) || (tag >= IMessage.MIN_TAG) && (tag <= IMessage.MAX_TAG), "tag" ); //$NON-NLS-1$
        assertArgumentNotNull( body, "body" ); //$NON-NLS-1$
        assertArgumentLegal( body.length <= MAX_BODY_LENGTH, "body" ); //$NON-NLS-1$

        buffer_ = ByteBuffer.allocate( HEADER_LENGTH + body.length );
        buffer_.putInt( id );
        final int tagAndLength = ((tag << 22) & 0xFFC00000) | (body.length & 0x003FFFFF);
        buffer_.putInt( tagAndLength );
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

        final int tagAndLength = buffer.getInt( HEADER_TAG_AND_LENGTH_OFFSET );
        final int bodyLength = decodeMessageLength( tagAndLength );
        if( buffer.remaining() < (HEADER_LENGTH + bodyLength) )
        {
            return false;
        }

        return true;
    }

    /**
     * Decodes the message length from the specified encoded value.
     * 
     * @param tagAndLength
     *        The encoded message tag and length.
     * 
     * @return The message length.
     */
    private static int decodeMessageLength(
        final int tagAndLength )
    {
        return tagAndLength & 0x003FFFFF;
    }

    /**
     * Decodes the message tag from the specified encoded value.
     * 
     * @param tagAndLength
     *        The encoded message tag and length.
     * 
     * @return The message tag.
     */
    private static int decodeMessageTag(
        final int tagAndLength )
    {
        return (tagAndLength >>> 22) & 0x000003FF;
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

        final int id = buffer.getInt();
        final int tagAndLength = buffer.getInt();
        final int tag = decodeMessageTag( tagAndLength );
        final int bodyLength = decodeMessageLength( tagAndLength );
        final byte[] body = new byte[ bodyLength ];
        buffer.get( body );

        return new MessageEnvelope( id, tag, body );
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

        return new MessageEnvelope( message.getId(), message.getTag(), serializeMessage( message ) );
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
     * Gets the message identifier.
     * 
     * @return The message identifier.
     */
    public int getId()
    {
        return buffer_.getInt( HEADER_ID_OFFSET );
    }

    /**
     * Gets the message tag.
     * 
     * @return The message tag.
     */
    public int getTag()
    {
        final int tagAndLength = buffer_.getInt( HEADER_TAG_AND_LENGTH_OFFSET );
        return decodeMessageTag( tagAndLength );
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
