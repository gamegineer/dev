/*
 * NonNlsMessages.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

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

    // --- TableNetwork -----------------------------------------------------

    /** The table network listener is already registered. */
    public static String TableNetwork_addTableNetworkListener_listener_registered = ""; //$NON-NLS-1$

    /** The table network listener is not registered. */
    public static String TableNetwork_removeTableNetworkListener_listener_notRegistered = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * ITableNetworkListener.tableNetworkConnected().
     */
    public static String TableNetwork_tableNetworkConnected_unexpectedException = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * ITableNetworkListener.tableNetworkDisconnected().
     */
    public static String TableNetwork_tableNetworkDisconnected_unexpectedException = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * ITableNetworkListener.tableNetworkPlayersUpdated().
     */
    public static String TableNetwork_tableNetworkPlayersUpdated_unexpectedException = ""; //$NON-NLS-1$


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
}
