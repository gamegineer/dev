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
 * Created on Feb 26, 2009 at 11:26:21 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.internal.ui.system.bindings.xml.Messages"; //$NON-NLS-1$

    // --- LocalizedStringReplacer ------------------------------------------

    /** An access error occurred while accessing a field. */
    public static String LocalizedStringReplacer_fieldAccess_accessError;

    // --- XmlGameSystemUi --------------------------------------------------

    /** An error occurred while building the game system user interface. */
    public static String XmlGameSystemUi_toGameSystemUi_builderError;

    // --- XmlRoleUi --------------------------------------------------------

    /** An error occurred while building the role user interface. */
    public static String XmlRoleUi_toRoleUi_builderError;


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
