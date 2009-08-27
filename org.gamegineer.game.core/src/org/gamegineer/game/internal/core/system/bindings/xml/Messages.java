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
 * Created on Nov 11, 2008 at 10:44:24 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.internal.core.system.bindings.xml.Messages"; //$NON-NLS-1$

    // --- XmlGameSystem ----------------------------------------------------

    /** An error occurred while building the game system. */
    public static String XmlGameSystem_toGameSystem_builderError;

    // --- XmlRole ----------------------------------------------------------

    /** An error occurred while building the role. */
    public static String XmlRole_toRole_builderError;

    // --- XmlStage ---------------------------------------------------------

    /** The strategy class is not accessible. */
    public static String XmlStage_createStrategy_accessError;

    /** The strategy class could not be instantiated. */
    public static String XmlStage_createStrategy_instantiationError;

    /** The strategy class does not implement {@code IStageStrategy}. */
    public static String XmlStage_createStrategy_notStrategy;

    /** The strategy class could not be found. */
    public static String XmlStage_createStrategy_unknownClass;

    /** An error occurred while building the stage. */
    public static String XmlStage_toStage_builderError;


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

    // --- XmlStage ---------------------------------------------------------

    /**
     * Gets the formatted message indicating the strategy class is not
     * accessible.
     * 
     * @param className
     *        The strategy class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the strategy class is not
     *         accessible; never {@code null}.
     */
    /* @NonNull */
    static String XmlStage_createStrategy_accessError(
        /* @NonNull */
        final String className )
    {
        return bind( XmlStage_createStrategy_accessError, className );
    }

    /**
     * Gets the formatted message indicating the strategy class could not be
     * instantiated.
     * 
     * @param className
     *        The strategy class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the strategy class could not be
     *         instantiated; never {@code null}.
     */
    /* @NonNull */
    static String XmlStage_createStrategy_instantiationError(
        /* @NonNull */
        final String className )
    {
        return bind( XmlStage_createStrategy_instantiationError, className );
    }

    /**
     * Gets the formatted message indicating the strategy class does not
     * implement {@code IStageStrategy}.
     * 
     * @param className
     *        The strategy class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the strategy class does not
     *         implement {@code IStageStrategy}; never {@code null}.
     */
    /* @NonNull */
    static String XmlStage_createStrategy_notStrategy(
        /* @NonNull */
        final String className )
    {
        return bind( XmlStage_createStrategy_notStrategy, className );
    }

    /**
     * Gets the formatted message indicating the strategy class could not be
     * found.
     * 
     * @param className
     *        The strategy class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the strategy class could not be
     *         found; never {@code null}.
     */
    /* @NonNull */
    static String XmlStage_createStrategy_unknownClass(
        /* @NonNull */
        final String className )
    {
        return bind( XmlStage_createStrategy_unknownClass, className );
    }
}
