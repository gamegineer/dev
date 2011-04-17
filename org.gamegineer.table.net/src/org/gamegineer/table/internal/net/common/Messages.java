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
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

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

    // --- AbstractNetworkTableStrategy -------------------------------------

    /** The table gateway is already registered. */
    public static String AbstractNetworkTableStrategy_addTableGateway_tableGatewayRegistered;

    /** The network is already connected. */
    public static String AbstractNetworkTableStrategy_connect_networkConnected;

    /** The network is disconnected. */
    public static String AbstractNetworkTableStrategy_networkDisconnected;

    /** The table gateway is not registered. */
    public static String AbstractNetworkTableStrategy_removeTableGateway_tableGatewayNotRegistered;


    // --- AbstractRemoteTableGateway ---------------------------------------

    /** An error occurred while deserializing a message. */
    public static String AbstractRemoteTableGateway_messageReceived_deserializationError;

    /** The service received an unknown message. */
    public static String AbstractRemoteTableGateway_messageReceived_unknownMessage;

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

    // --- AbstractRemoteTableGateway ---------------------------------------

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
    static String AbstractRemoteTableGateway_messageReceived_deserializationError(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( AbstractRemoteTableGateway_messageReceived_deserializationError, messageEnvelope.getId(), messageEnvelope.getTag() );
    }

    /**
     * Gets the formatted message indicating the service received an unknown
     * message.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @return The formatted message indicating the service received an unknown
     *         message; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    static String AbstractRemoteTableGateway_messageReceived_unknownMessage(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( AbstractRemoteTableGateway_messageReceived_unknownMessage, messageEnvelope.getId(), messageEnvelope.getTag() );
    }
}
