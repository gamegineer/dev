/*
 * Messages.java
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
 * Created on Jan 8, 2011 at 10:43:56 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.MessageEnvelope;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- AbstractServiceHandler -------------------------------------------

    /** An I/O error occurred while closing the socket channel. */
    public static String AbstractServiceHandler_close_ioError;

    /** An error occurred while running the event handler. */
    public static String AbstractServiceHandler_run_error;

    /** An I/O error occurred while sending a message. */
    public static String AbstractServiceHandler_sendMessage_ioError;

    /** The client service handler state is not pristine. */
    public static String AbstractServiceHandler_state_notPristine;

    // --- Acceptor ---------------------------------------------------------

    /** An I/O error occurred while accepting a new connection. */
    public static String Acceptor_accept_ioError;

    /** An I/O error occurred while closing the server socket channel. */
    public static String Acceptor_close_ioError;

    /** The acceptor state is not pristine. */
    public static String Acceptor_state_notPristine;

    // --- ClientNetworkInterface -------------------------------------------

    /** An I/O error occurred while opening the network interface. */
    public static String ClientNetworkInterface_open_ioError;

    // --- ClientServiceHandler ---------------------------------------------

    /** An error occurred while deserializing a message. */
    public static String ClientServiceHandler_handleMessageEnvelope_deserializationError;

    /** The handler received an unknown message. */
    public static String ClientServiceHandler_handleMessageEnvelope_unknownMessage;

    // --- Connector --------------------------------------------------------

    /** The peer address cannot be resolved. */
    public static String Connector_createSocketChannel_addressUnresolved;

    /** The connector state is not pristine. */
    public static String Connector_state_notPristine;

    // --- Dispatcher -------------------------------------------------------

    /** An error occurred while closing the dispatcher. */
    public static String Dispatcher_close_error;

    /** An error occurred on the event dispatch thread. */
    public static String Dispatcher_dispatchEvents_error;

    /** An I/O error occurred while opening the dispatcher. */
    public static String Dispatcher_open_ioError;

    /** An unexpected error occurred while running an event handler. */
    public static String Dispatcher_processEvents_unexpectedError;

    /** The event handler is already registered. */
    public static String Dispatcher_registerEventHandler_eventHandlerRegistered;

    /** The dispatcher state is not open. */
    public static String Dispatcher_state_notOpen;

    /** The dispatcher state is not pristine. */
    public static String Dispatcher_state_notPristine;

    /** The event handler is not registered. */
    public static String Dispatcher_unregisterEventHandler_eventHandlerUnregistered;

    // --- ServerNetworkInterface -------------------------------------------

    /** An I/O error occurred while opening the network interface. */
    public static String ServerNetworkInterface_open_ioError;

    // --- ServerServiceHandler ---------------------------------------------

    /** An error occurred while deserializing a message. */
    public static String ServerServiceHandler_handleMessageEnvelope_deserializationError;

    /** The handler received an unknown message. */
    public static String ServerServiceHandler_handleMessageEnvelope_unknownMessage;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- AbstractServiceHandler -------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while sending a
     * message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while sending
     *         a message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String AbstractServiceHandler_sendMessage_ioError(
        /* @NonNull */
        final AbstractMessage message )
    {
        return bind( AbstractServiceHandler_sendMessage_ioError, message.getId(), message.getTag() );
    }

    // --- ClientServiceHandler ---------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while
     * deserializing a message.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while
     *         deserializing a message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String ClientServiceHandler_handleMessageEnvelope_deserializationError(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( ClientServiceHandler_handleMessageEnvelope_deserializationError, messageEnvelope.getId(), messageEnvelope.getTag() );
    }

    /**
     * Gets the formatted message indicating the handler received an unknown
     * message.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @return The formatted message indicating the handler received an unknown
     *         message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String ClientServiceHandler_handleMessageEnvelope_unknownMessage(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( ClientServiceHandler_handleMessageEnvelope_unknownMessage, messageEnvelope.getId(), messageEnvelope.getTag() );
    }

    // --- ServerServiceHandler ---------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while
     * deserializing a message.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while
     *         deserializing a message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String ServerServiceHandler_handleMessageEnvelope_deserializationError(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( ServerServiceHandler_handleMessageEnvelope_deserializationError, messageEnvelope.getId(), messageEnvelope.getTag() );
    }

    /**
     * Gets the formatted message indicating the handler received an unknown
     * message.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @return The formatted message indicating the handler received an unknown
     *         message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String ServerServiceHandler_handleMessageEnvelope_unknownMessage(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( ServerServiceHandler_handleMessageEnvelope_unknownMessage, messageEnvelope.getId(), messageEnvelope.getTag() );
    }
}
