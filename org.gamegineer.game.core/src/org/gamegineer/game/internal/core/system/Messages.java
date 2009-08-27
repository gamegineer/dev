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
 * Created on Nov 15, 2008 at 10:15:49 PM.
 */

package org.gamegineer.game.internal.core.system;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.internal.core.system.Messages"; //$NON-NLS-1$

    // --- GameSystemUtils --------------------------------------------------

    /** The role list is empty. */
    public static String GameSystemUtils_assertGameSystemLegal_roles_empty;

    /** The role list contains a non-unique role identifier. */
    public static String GameSystemUtils_assertGameSystemLegal_roles_notUnique;

    /** The stage list is empty. */
    public static String GameSystemUtils_assertGameSystemLegal_stages_empty;

    /** The stage list contains a non-unique stage identifier. */
    public static String GameSystemUtils_assertGameSystemLegal_stages_notUnique;

    /** The cardinality is negative. */
    public static String GameSystemUtils_assertStageLegal_cardinality_negative;

    /** The child stage list contains a non-unique stage identifier. */
    public static String GameSystemUtils_assertStageLegal_stages_notUnique;


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
