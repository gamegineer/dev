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
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.persistence.serializable.ObjectInputStream;
import org.gamegineer.common.persistence.serializable.ObjectOutputStream;
import org.gamegineer.common.persistence.serializable.ObjectStreams;

/**
 * A network protocol message envelope.
 */
@Immutable
public final class MessageEnvelope
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network representation of the message envelope. */
    private final byte[] bytes_;

    /** The logical representation of the message envelope header. */
    private final Header header_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MessageEnvelope} class.
     * 
     * @param bytes
     *        The network representation of the message envelope; must not be
     *        {@code null} and must not have a length less than the sum of
     *        {@link Header#LENGTH} and the body length decoded from the header.
     */
    private MessageEnvelope(
        /* @NonNull */
        final byte[] bytes )
    {
        assert bytes != null;

        bytes_ = bytes;
        header_ = new Header( bytes );
        assert bytes.length >= (Header.LENGTH + header_.getBodyLength());
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new message envelope from the specified byte array.
     * 
     * @param bytes
     *        The byte array; must not be {@code null}. No copy is made of this
     *        array and it must not be modified after calling this method.
     * 
     * @return A new message envelope; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the length of {@code bytes} is not exactly equal to the sum of
     *         {@link Header#LENGTH} and the body length decoded from the
     *         header.
     * @throws java.lang.NullPointerException
     *         If {@code bytes} is {@code null}.
     */
    /* @NonNull */
    public static MessageEnvelope fromByteArray(
        /* @NonNull */
        final byte[] bytes )
    {
        assertArgumentNotNull( bytes, "bytes" ); //$NON-NLS-1$
        assertArgumentLegal( bytes.length >= Header.LENGTH, "bytes" ); //$NON-NLS-1$

        final Header header = new Header( bytes );
        assertArgumentLegal( bytes.length == (Header.LENGTH + header.getBodyLength()), "bytes" ); //$NON-NLS-1$

        return new MessageEnvelope( bytes );
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

        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // reserve space for header
        for( int index = 0; index < Header.LENGTH; ++index )
        {
            byteStream.write( 0x00 );
        }

        // write body
        final ObjectOutputStream objectStream = ObjectStreams.createPlatformObjectOutputStream( byteStream );
        try
        {
            objectStream.writeObject( message );
        }
        finally
        {
            objectStream.close();
        }

        // write header
        final byte[] bytes = byteStream.toByteArray();
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        final Header header = headerBuilder //
            .setId( message.getId() ) //
            .setCorrelationId( message.getCorrelationId() ) //
            .setBodyLength( bytes.length - Header.LENGTH ) //
            .toHeader();
        final byte[] headerBytes = header.toByteArray();
        System.arraycopy( headerBytes, 0, bytes, 0, headerBytes.length );

        return new MessageEnvelope( bytes );
    }

    /**
     * Gets the message envelope header.
     * 
     * @return The message envelope header; never {@code null}.
     */
    /* @NonNull */
    public Header getHeader()
    {
        return header_;
    }

    /**
     * Gets the message contained in the message envelope body.
     * 
     * @return The message contained in the message envelope body; never {@code
     *         null}.
     * 
     * @throws java.io.IOException
     *         If the message cannot be deserialized from the message envelope
     *         body.
     * @throws java.lang.ClassNotFoundException
     *         If the class of the message cannot be found.
     */
    /* @NonNull */
    public IMessage getMessage()
        throws IOException, ClassNotFoundException
    {
        final ObjectInputStream stream = ObjectStreams.createPlatformObjectInputStream( new ByteArrayInputStream( bytes_, Header.LENGTH, bytes_.length - Header.LENGTH ) );
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
     * Gets a byte array representing the message envelope.
     * 
     * @return A byte array representing the message envelope; never {@code
     *         null}. The returned array is not a copy and must not be modified
     *         by the caller.
     */
    /* @NonNull */
    public byte[] toByteArray()
    {
        return bytes_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A message envelope header.
     */
    @Immutable
    public static final class Header
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The length of a message envelope header in bytes. */
        public static final int LENGTH = 4;

        /** The maximum message body length in bytes. */
        public static final int MAXIMUM_BODY_LENGTH = 0xFFFF;

        /** The network representation of the message envelope header. */
        private final byte[] bytes_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Header} class.
         * 
         * @param bytes
         *        The network representation of the message envelope header;
         *        must not be {@code null} and must not have a length less than
         *        {@link #LENGTH}.
         */
        Header(
            /* @NonNull */
            final byte[] bytes )
        {
            assert bytes != null;
            assert bytes.length >= LENGTH;

            bytes_ = bytes;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates a new message envelope header from the specified byte array.
         * 
         * @param bytes
         *        The byte array; must not be {@code null}. No copy is made of
         *        this array and it must not be modified after calling this
         *        method.
         * 
         * @return A new message envelope header; never {@code null}.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If the length of {@code bytes} is not equal to
         *         {@link #LENGTH}.
         * @throws java.lang.NullPointerException
         *         If {@code bytes} is {@code null}.
         */
        /* @NonNull */
        public static Header fromByteArray(
            /* @NonNull */
            final byte[] bytes )
        {
            assertArgumentNotNull( bytes, "bytes" ); //$NON-NLS-1$
            assertArgumentLegal( bytes.length == LENGTH, "bytes" ); //$NON-NLS-1$

            return new Header( bytes );
        }

        /**
         * Gets the length of the message envelope body in bytes.
         * 
         * @return The length of the message envelope body in bytes.
         */
        public int getBodyLength()
        {
            return ((bytes_[ 2 ] & 0x000000FF) << 8) | (bytes_[ 3 ] & 0x000000FF);
        }

        /**
         * Gets the message correlation identifier.
         * 
         * @return The message correlation identifier.
         */
        public int getCorrelationId()
        {
            return bytes_[ 1 ] & 0x000000FF;
        }

        /**
         * Gets the message identifier.
         * 
         * @return The message identifier.
         */
        public int getId()
        {
            return bytes_[ 0 ] & 0x000000FF;
        }

        /**
         * Gets a byte array representing the message envelope header.
         * 
         * @return A byte array representing the message envelope header; never
         *         {@code null}. The returned array is not a copy and must not
         *         be modified by the caller.
         */
        /* @NonNull */
        public byte[] toByteArray()
        {
            if( bytes_.length == LENGTH )
            {
                return bytes_;
            }

            final byte[] bytes = new byte[ LENGTH ];
            System.arraycopy( bytes_, 0, bytes, 0, bytes.length );
            return bytes;
        }
    }

    /**
     * A message envelope header builder.
     */
    @NotThreadSafe
    public static final class HeaderBuilder
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The network representation of the message envelope header. */
        private final byte[] bytes_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code HeaderBuilder} class.
         */
        public HeaderBuilder()
        {
            bytes_ = new byte[ Header.LENGTH ];
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Sets the length of the message envelope body.
         * 
         * @param bodyLength
         *        The length of the message envelope body in bytes.
         * 
         * @return A reference to this builder; never {@code null}.
         */
        /* @NonNull */
        public HeaderBuilder setBodyLength(
            final int bodyLength )
        {
            bytes_[ 2 ] = (byte)((bodyLength >>> 8) & 0x000000FF);
            bytes_[ 3 ] = (byte)(bodyLength & 0x000000FF);
            return this;
        }

        /**
         * Sets the message correlation identifier.
         * 
         * @param correlationId
         *        The message correlation identifier.
         * 
         * @return A reference to this builder; never {@code null}.
         */
        /* @NonNull */
        public HeaderBuilder setCorrelationId(
            final int correlationId )
        {
            bytes_[ 1 ] = (byte)correlationId;
            return this;
        }

        /**
         * Sets the message identifier.
         * 
         * @param id
         *        The message identifier.
         * 
         * @return A reference to this builder; never {@code null}.
         */
        /* @NonNull */
        public HeaderBuilder setId(
            final int id )
        {
            bytes_[ 0 ] = (byte)id;

            return this;
        }

        /**
         * Creates a new message envelope header based on the state of this
         * builder.
         * 
         * @return A new message envelope header; never {@code null}.
         */
        /* @NonNull */
        public Header toHeader()
        {
            return new Header( bytes_ );
        }
    }
}
