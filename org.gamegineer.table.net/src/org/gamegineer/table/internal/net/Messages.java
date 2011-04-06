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
 * Created on Nov 9, 2010 at 11:03:33 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

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

    // --- AbstractNetworkServiceHandler ------------------------------------

    /** An error occurred while deserializing a message. */
    public static String AbstractNetworkServiceHandler_messageReceived_deserializationError;

    /** The handler received an unknown message. */
    public static String AbstractNetworkServiceHandler_messageReceived_unknownMessage;

    // --- Authenticator ----------------------------------------------------

    /** Failed to create an authentication response. */
    public static String Authenticator_createResponse_failed;

    /** Failed to create a secret key. */
    public static String Authenticator_createSecretKey_failed;

    /** Failed to create a secure random byte buffer. */
    public static String Authenticator_createSecureRandomBytes_failed;

    // --- NetworkTable -----------------------------------------------------

    /** The network table listener is already registered. */
    public static String NetworkTable_addNetworkTableListener_listener_registered;

    /** The network is already connected. */
    public static String NetworkTable_connect_networkConnected;

    /**
     * An unexpected exception was thrown from
     * INetworkTableListener.networkConnected().
     */
    public static String NetworkTable_networkConnected_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * INetworkTableListener.networkDisconnected().
     */
    public static String NetworkTable_networkDisconnected_unexpectedException;

    /** The player has connected. */
    public static String NetworkTable_playerConnected_playerConnected;

    /** The player is already registered. */
    public static String NetworkTable_playerConnected_playerRegistered;

    /** The player has disconnected. */
    public static String NetworkTable_playerDisconnected_playerDisconnected;

    /** The network table listener is not registered. */
    public static String NetworkTable_removeNetworkTableListener_listener_notRegistered;


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

    // --- AbstractNetworkServiceHandler ------------------------------------

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
    static String AbstractNetworkServiceHandler_messageReceived_deserializationError(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( AbstractNetworkServiceHandler_messageReceived_deserializationError, messageEnvelope.getId(), messageEnvelope.getTag() );
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
    static String AbstractNetworkServiceHandler_messageReceived_unknownMessage(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        return bind( AbstractNetworkServiceHandler_messageReceived_unknownMessage, messageEnvelope.getId(), messageEnvelope.getTag() );
    }

    // --- NetworkTable -----------------------------------------------------

    /**
     * Gets the formatted message indicating the player has connected.
     * 
     * @param playerName
     *        The name of the player that has connected; must not be {@code
     *        null}.
     * 
     * @return The formatted message indicating the player has connected; never
     *         {@code null}.
     */
    /* @NonNull */
    static String NetworkTable_playerConnected_playerConnected(
        /* @NonNull */
        final String playerName )
    {
        return bind( NetworkTable_playerConnected_playerConnected, playerName );
    }

    /**
     * Gets the formatted message indicating the player has disconnected.
     * 
     * @param playerName
     *        The name of the player that has disconnected; must not be {@code
     *        null}.
     * 
     * @return The formatted message indicating the player has disconnected;
     *         never {@code null}.
     */
    /* @NonNull */
    static String NetworkTable_playerDisconnected_playerDisconnected(
        /* @NonNull */
        final String playerName )
    {
        return bind( NetworkTable_playerDisconnected_playerDisconnected, playerName );
    }
}
