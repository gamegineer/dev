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
 * Created on Apr 17, 2011 at 6:49:29 PM.
 */

package org.gamegineer.table.internal.net.client;

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

    // --- BeginAuthenticationRequestMessageHandler -------------------------

    /** Failed to create the begin authentication response. */
    public static String BeginAuthenticationRequestMessageHandler_beginAuthenticationResponseFailed;

    // --- ClientNetworkTableStrategy ---------------------------------------

    /** The protocol handshake was interrupted waiting for completion. */
    public static String ClientNetworkTableStrategy_handshake_interrupted;

    /** The protocol handshake timed out waiting for completion. */
    public static String ClientNetworkTableStrategy_handshake_timedOut;

    // --- RemoteServerTableGateway -----------------------------------------

    /** Failed to register the server table gateway. */
    public static String RemoteServerTableGateway_serverTableGatewayNotRegistered;


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
}
