/*
 * NonNlsMessages.java
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
 * Created on May 30, 2011 at 8:06:47 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.internal.net.impl.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A utility class to manage non-localized messages for the package.
 */
@ThreadSafe
final class NonNlsMessages
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

    /** An error occurred while attempting to disconnect the network. */
    public static String AbstractNode_connect_disconnectError;

    /** The network is disconnected. */
    public static String AbstractNode_networkDisconnected;

    /** The node layer has been shutdown. */
    public static String AbstractNode_nodeLayer_shutdown;

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

    /** An error occurred during the operation. */
    public static String LocalNetworkTable_syncExec_error;

    /** The operation was interrupted. */
    public static String LocalNetworkTable_syncExec_interrupted;

    // --- NetworkTableUtils ------------------------------------------------

    /** Failed to set the surface designs. */
    public static String NetworkTableUtils_incrementComponentState_setSurfaceDesignsFailed;

    /** Failed to set the component state. */
    public static String NetworkTableUtils_incrementContainerState_setComponentStateFailed;

    /** Failed to set the layout. */
    public static String NetworkTableUtils_incrementContainerState_setLayoutFailed;

    /** Failed to set the table state. */
    public static String NetworkTableUtils_setTableState_failed;

    // --- NodeControllerProxy ----------------------------------------------

    /** The operation was interrupted. */
    public static String NodeControllerProxy_interrupted;

    // --- NodeLayer --------------------------------------------------------

    /** The name of the node layer thread. */
    public static String NodeLayer_thread_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NonNlsMessages.class.getName(), NonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NonNlsMessages} class.
     */
    private NonNlsMessages()
    {
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
        final MessageEnvelope.Header header = messageEnvelope.getHeader();
        return bind( AbstractRemoteNode_extractMessage_deserializationError, header.getId(), header.getCorrelationId() );
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
     *        The table network error that was received; must not be
     *        {@code null}.
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
