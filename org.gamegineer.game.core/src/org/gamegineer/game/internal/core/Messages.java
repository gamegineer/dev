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
 * Created on Jul 17, 2008 at 11:26:19 PM.
 */

package org.gamegineer.game.internal.core;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.internal.core.Messages"; //$NON-NLS-1$

    // --- Game -------------------------------------------------------------

    /** The game engine could not be created due to a configuration error. */
    public static String Game_engine_configError;

    /** The game configuration is illegal. */
    public static String Game_gameConfig_illegal;

    // --- GameFactory ------------------------------------------------------

    /** The requested class name is unsupported. */
    public static String GameFactory_createComponent_unsupportedType;

    // --- Services ---------------------------------------------------------

    /** The game system registry service tracker is not set. */
    public static String Services_gameSystemRegistryServiceTracker_notSet;

    /** The game system service tracker is not set. */
    public static String Services_gameSystemServiceTracker_notSet;

    /** The package administration service tracker is not set. */
    public static String Services_packageAdminServiceTracker_notSet;

    // --- Stage ------------------------------------------------------------

    /** The currently executing stage has no child stages. */
    public static String Stage_activate_noChildStages;

    /** The command queue extension is not available. */
    public static String Stage_commandQueueExtension_unavailable;

    /** The stage cannot be deactivated because it is not active. */
    public static String Stage_deactivate_notActive;

    /** The stage is not active. */
    public static String Stage_notActive;


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


    // ======================================================================
    // Methods
    // ======================================================================

    // --- GameFactory ------------------------------------------------------

    /**
     * Gets the formatted message indicating the requested class name is
     * unsupported.
     * 
     * @param className
     *        The class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the requested class name is
     *         unsupported; never {@code null}.
     */
    /* @NonNull */
    static String GameFactory_createComponent_unsupportedType(
        /* @NonNull */
        final String className )
    {
        return bind( GameFactory_createComponent_unsupportedType, className );
    }

    // --- Stage ------------------------------------------------------------

    /**
     * Gets the formatted message indicating the stage is not active.
     * 
     * @param stageId
     *        The stage identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the stage is not active; never
     *         {@code null}.
     */
    /* @NonNull */
    static String Stage_notActive(
        /* @NonNull */
        final String stageId )
    {
        return bind( Stage_notActive, stageId );
    }
}
