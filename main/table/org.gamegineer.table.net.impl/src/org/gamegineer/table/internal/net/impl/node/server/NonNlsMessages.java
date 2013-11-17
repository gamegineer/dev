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
 * Created on Sep 30, 2011 at 10:35:55 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server;

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

    // --- ServerNode -------------------------------------------------------

    /** The table environment factory service is not available. */
    public static String ServerNode_initializeMasterTable_tableEnvironmentFactoryNotAvailable;

    /**
     * An attempt was made to modify the network table by a player without the
     * editor role.
     */
    public static String ServerNode_networkTableModification_playerNotEditor;


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

    // --- ServerNode -------------------------------------------------------

    /**
     * Gets the formatted message indicating an attempt was made to modify the
     * network table by a player without the editor role.
     * 
     * @param playerName
     *        The name of the player that attempted to modify the network table;
     *        must not be {@code null}.
     * 
     * @return The formatted message indicating an attempt was made to modify
     *         the network table by a player without the editor role; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ServerNode_networkTableModification_playerNotEditor(
        /* @NonNull */
        final String playerName )
    {
        return bind( ServerNode_networkTableModification_playerNotEditor, playerName );
    }
}
