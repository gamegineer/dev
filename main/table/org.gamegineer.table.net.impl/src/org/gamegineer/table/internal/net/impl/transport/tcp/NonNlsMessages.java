/*
 * NonNlsMessages.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.transport.tcp;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.internal.net.impl.transport.IMessage;

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

    // --- AbstractTransportLayer -------------------------------------------

    /** Interrupted while creating the transport layer. */
    public static String AbstractTransportLayer_createTransportLayer_interrupted = ""; //$NON-NLS-1$

    /** An I/O error occurred while opening the transport layer. */
    public static String AbstractTransportLayer_open_ioError = ""; //$NON-NLS-1$

    /** The transport layer state is not pristine. */
    public static String AbstractTransportLayer_state_notPristine = ""; //$NON-NLS-1$

    /** The name of the transport layer thread. */
    public static String AbstractTransportLayer_transportLayerThread_name = ""; //$NON-NLS-1$

    // --- Acceptor ---------------------------------------------------------

    /** An I/O error occurred while accepting a new connection. */
    public static String Acceptor_accept_ioError = ""; //$NON-NLS-1$

    /** An I/O error occurred while closing the server socket channel. */
    public static String Acceptor_close_ioError = ""; //$NON-NLS-1$

    // --- Connector --------------------------------------------------------

    /** The peer address cannot be resolved. */
    public static String Connector_createSocketChannel_addressUnresolved = ""; //$NON-NLS-1$

    // --- Dispatcher -------------------------------------------------------

    /** An error occurred while closing the dispatcher. */
    public static String Dispatcher_closeDispatcher_error = ""; //$NON-NLS-1$

    /** An error occurred on the event dispatch thread. */
    public static String Dispatcher_dispatchEvents_error = ""; //$NON-NLS-1$

    /** The name of the event dispatch thread. */
    public static String Dispatcher_eventDispatchThread_name = ""; //$NON-NLS-1$

    /** An unexpected error occurred while running an event handler. */
    public static String Dispatcher_processEvents_unexpectedError = ""; //$NON-NLS-1$

    /** Timed out waiting for the event dispatch task to shutdown. */
    public static String Dispatcher_waitForEventDispatchTaskToShutdown_timeout = ""; //$NON-NLS-1$

    // --- ServiceHandler ---------------------------------------------------

    /** An I/O error occurred while closing the socket channel. */
    public static String ServiceHandler_close_ioError = ""; //$NON-NLS-1$

    /** An error occurred while running the service handler. */
    public static String ServiceHandler_run_error = ""; //$NON-NLS-1$

    /** An I/O error occurred while sending a message. */
    public static String ServiceHandler_sendMessage_ioError = ""; //$NON-NLS-1$

    /** The transport layer associated with the service has been shutdown. */
    public static String ServiceHandler_transportLayer_shutdown = ""; //$NON-NLS-1$

    // --- TransportLayerProxy ----------------------------------------------

    /** The transport layer is closed. */
    public static String TransportLayerProxy_beginOpen_transportLayerClosed = ""; //$NON-NLS-1$


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

    // --- ServiceHandler ---------------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while sending a
     * message.
     * 
     * @param message
     *        The message.
     * 
     * @return The formatted message indicating an error occurred while sending
     *         a message.
     */
    @SuppressWarnings( "boxing" )
    static String ServiceHandler_sendMessage_ioError(
        final IMessage message )
    {
        return bind( ServiceHandler_sendMessage_ioError, message.getId(), message.getCorrelationId() );
    }
}
