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
 * Created on Aug 16, 2009 at 9:30:03 PM.
 */

package org.gamegineer.tictactoe.internal.core.system.stages;

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
    private static final String BUNDLE_NAME = "org.gamegineer.tictactoe.internal.core.system.stages.Messages"; //$NON-NLS-1$

    // --- StageStrategyFactory ---------------------------------------------

    /** Component creation failed. */
    public static String StageStrategyFactory_createComponent_failed;

    /** The requested class name is unsupported. */
    public static String StageStrategyFactory_createComponent_unsupportedType;


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

    // --- StageStrategyFactory ---------------------------------------------

    /**
     * Gets the formatted message indicating component creation failed.
     * 
     * @param className
     *        The class name of the component; must not be {@code null}.
     * 
     * @return The formatted message indicating component creation failed; never
     *         {@code null}.
     */
    /* @NonNull */
    static String StageStrategyFactory_createComponent_failed(
        /* @NonNull */
        final String className )
    {
        return bind( StageStrategyFactory_createComponent_failed, className );
    }

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
    static String StageStrategyFactory_createComponent_unsupportedType(
        /* @NonNull */
        final String className )
    {
        return bind( StageStrategyFactory_createComponent_unsupportedType, className );
    }
}
