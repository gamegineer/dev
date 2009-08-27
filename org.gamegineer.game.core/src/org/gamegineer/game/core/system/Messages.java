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
 * Created on Nov 15, 2008 at 8:56:31 PM.
 */

package org.gamegineer.game.core.system;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.core.system.Messages"; //$NON-NLS-1$

    // --- GameSystemBuilder ------------------------------------------------

    /** The game system identifier is not set. */
    public static String GameSystemBuilder_id_notSet;

    /** The builder state is illegal. */
    public static String GameSystemBuilder_state_illegal;

    // --- RoleBuilder ------------------------------------------------------

    /** The role identifier is not set. */
    public static String RoleBuilder_id_notSet;

    /** The builder state is illegal. */
    public static String RoleBuilder_state_illegal;

    // --- StageBuilder -----------------------------------------------------

    /** The cardinality is not set. */
    public static String StageBuilder_cardinality_notSet;

    /** The stage identifier is not set. */
    public static String StageBuilder_id_notSet;

    /** The builder state is illegal. */
    public static String StageBuilder_state_illegal;

    /** The strategy is not set. */
    public static String StageBuilder_strategy_notSet;


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
