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

    // --- TableNetwork -----------------------------------------------------

    /** The table network listener is already registered. */
    public static String TableNetwork_addTableNetworkListener_listener_registered;

    /** The table network listener is not registered. */
    public static String TableNetwork_removeTableNetworkListener_listener_notRegistered;

    /**
     * An unexpected exception was thrown from
     * ITableNetworkListener.tableNetworkConnected().
     */
    public static String TableNetwork_tableNetworkConnected_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ITableNetworkListener.tableNetworkDisconnected().
     */
    public static String TableNetwork_tableNetworkDisconnected_unexpectedException;


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
