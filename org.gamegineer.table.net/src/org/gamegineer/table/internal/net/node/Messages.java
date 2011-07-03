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
 * Created on May 30, 2011 at 8:06:47 PM.
 */

package org.gamegineer.table.internal.net.node;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;

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

    // --- AbstractMessageHandler -------------------------------------------

    /** An unexpected error occurred while handling a message. */
    public static String AbstractMessageHandler_handleMessage_unexpectedError;

    /** The message handler received an unexpected message. */
    public static String AbstractMessageHandler_messageReceived_unexpectedMessage;

    // --- AbstractNode -----------------------------------------------------

    /** The remote node is already bound. */
    public static String AbstractNode_bindRemoteNode_remoteNodeBound;

    /** The network is disconnected. */
    public static String AbstractNode_networkDisconnected;

    /** The remote node is not bound. */
    public static String AbstractNode_unbindRemoteNode_remoteNodeNotBound;

    // --- AbstractRemoteNode -----------------------------------------------

    /** The remote node is already bound. */
    public static String AbstractRemoteNode_bound;

    /** The remote node is closed. */
    public static String AbstractRemoteNode_closed;

    /** An error occurred while deserializing a message. */
    public static String AbstractRemoteNode_extractMessage_deserializationError;

    /** The service received an unhandled message. */
    public static String AbstractRemoteNode_messageReceived_unhandledMessage;

    /** The player has not been authenticated. */
    public static String AbstractRemoteNode_playerNotAuthenticated;

    /** The message type is already registered. */
    public static String AbstractRemoteNode_registerUncorrelatedMessageHandler_messageTypeRegistered;

    // --- AbstractRemoteNode.ErrorMessageHandler ---------------------------

    /** An uncorrelated error message was received. */
    public static String ErrorMessageHandler_handleMessage_errorReceived;

    // --- LocalNetworkTable ------------------------------------------------

    /** Failed to set the table memento. */
    public static String LocalNetworkTable_setTableMemento_failed;


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

    // --- AbstractMessageHandler -------------------------------------------

    /**
     * Gets the formatted message indicating the message handler received an
     * unexpected message.
     * 
     * @param messageHandler
     *        The message handler; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return The formatted message indicating the message handler received an
     *         unexpected message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String AbstractMessageHandler_messageReceived_unexpectedMessage(
        /* @NonNull */
        final IMessageHandler messageHandler,
        /* @NonNull */
        final IMessage message )
    {
        return bind( AbstractMessageHandler_messageReceived_unexpectedMessage, new Object[] {
            messageHandler.getClass().getSimpleName(), //
            message.getClass().getSimpleName(), //
            message.getId(), //
            message.getCorrelationId()
        } );
    }

    // --- AbstractRemoteNode -----------------------------------------------

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
    static String AbstractRemoteNode_extractMessage_deserializationError(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( AbstractRemoteNode_extractMessage_deserializationError, messageEnvelope.getId(), messageEnvelope.getCorrelationId() );
    }

    /**
     * Gets the formatted message indicating the service received an unhandled
     * message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return The formatted message indicating the service received an
     *         unhandled message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String AbstractRemoteNode_messageReceived_unhandledMessage(
        /* @NonNull */
        final IMessage message )
    {
        return bind( AbstractRemoteNode_messageReceived_unhandledMessage, new Object[] {
            message.getClass().getSimpleName(), //
            message.getId(), //
            message.getCorrelationId()
        } );
    }

    // --- AbstractRemoteNode.ErrorMessageHandler ---------------------------

    /**
     * Gets the formatted message indicating an uncorrelated error message was
     * received.
     * 
     * @param error
     *        The table network error that was received; must not be {@code
     *        null}.
     * 
     * @return The formatted message indicating an uncorrelated error message
     *         was received; never {@code null}.
     */
    /* @NonNull */
    static String ErrorMessageHandler_handleMessage_errorReceived(
        /* @NonNull */
        final TableNetworkError error )
    {
        return bind( ErrorMessageHandler_handleMessage_errorReceived, error );
    }
}
