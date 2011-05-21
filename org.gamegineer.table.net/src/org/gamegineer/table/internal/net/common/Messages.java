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
 * Created on Apr 10, 2011 at 6:46:05 PM.
 */

package org.gamegineer.table.internal.net.common;

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

    // --- AbstractRemoteTableProxy -----------------------------------------

    /** The remote table proxy is already bound. */
    public static String AbstractRemoteTableProxy_bound;

    /** The remote table proxy is closed. */
    public static String AbstractRemoteTableProxy_closed;

    /** An error occurred while deserializing a message. */
    public static String AbstractRemoteTableProxy_extractMessage_deserializationError;

    /** The service received an unhandled message. */
    public static String AbstractRemoteTableProxy_messageReceived_unhandledMessage;

    /** The player has not been authenticated. */
    public static String AbstractRemoteTableProxy_playerNotAuthenticated;

    /** The message type is already registered. */
    public static String AbstractRemoteTableProxy_registerUncorrelatedMessageHandler_messageTypeRegistered;

    // --- AbstractRemoteTableProxy.AbstractMessageHandler ------------------

    /** An unexpected error occurred while handling a message. */
    public static String AbstractMessageHandler_handleMessage_unexpectedError;

    /** The message handler received an unexpected message. */
    public static String AbstractMessageHandler_messageReceived_unexpectedMessage;

    // --- AbstractRemoteTableProxy.ErrorMessageHandler ---------------------

    /** An uncorrelated error message was received. */
    public static String ErrorMessageHandler_handleMessage_errorReceived;

    // --- AbstractTableNetworkNode -----------------------------------------

    /** The table proxy is already registered. */
    public static String AbstractTableNetworkNode_addTableProxy_tableProxyRegistered;

    /** The network is disconnected. */
    public static String AbstractTableNetworkNode_networkDisconnected;

    /** The table proxy is not registered. */
    public static String AbstractTableNetworkNode_removeTableProxy_tableProxyNotRegistered;

    // --- Authenticator ----------------------------------------------------

    /** Failed to create an authentication response. */
    public static String Authenticator_createResponse_failed;

    /** Failed to create a secret key. */
    public static String Authenticator_createSecretKey_failed;

    /** Failed to create a secure random byte buffer. */
    public static String Authenticator_createSecureRandomBytes_failed;


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

    // --- AbstractRemoteTableProxy -----------------------------------------

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
    static String AbstractRemoteTableProxy_extractMessage_deserializationError(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( AbstractRemoteTableProxy_extractMessage_deserializationError, messageEnvelope.getId(), messageEnvelope.getCorrelationId() );
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
    static String AbstractRemoteTableProxy_messageReceived_unhandledMessage(
        /* @NonNull */
        final IMessage message )
    {
        return bind( AbstractRemoteTableProxy_messageReceived_unhandledMessage, new Object[] {
            message.getClass().getSimpleName(), //
            message.getId(), //
            message.getCorrelationId()
        } );
    }

    // --- AbstractRemoteTableProxy.AbstractMessageHandler ------------------

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
        final IRemoteTableProxyController.IMessageHandler messageHandler,
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

    // --- AbstractRemoteTableProxy.ErrorMessageHandler ---------------------

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
