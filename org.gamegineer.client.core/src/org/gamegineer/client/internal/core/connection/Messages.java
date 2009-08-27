/*
 * Messages.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Dec 23, 2008 at 9:47:22 PM.
 */

package org.gamegineer.client.internal.core.connection;

import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.core.connection.Messages"; //$NON-NLS-1$

    // --- LocalGameServerConnection ----------------------------------------

    /** The connection is not open. */
    public static String LocalGameServerConnection_createGameServerProxy_connectionNotOpen;

    /** The connection is not open. */
    public static String LocalGameServerConnection_getGameServer_connectionNotOpen;

    /** The connection name. */
    public static String LocalGameServerConnection_name;

    /** The connection is closed. */
    public static String LocalGameServerConnection_open_connectionClosed;

    // --- NullGameServerConnection -----------------------------------------

    /** The connection name. */
    public static String NullGameServerConnection_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }
}
