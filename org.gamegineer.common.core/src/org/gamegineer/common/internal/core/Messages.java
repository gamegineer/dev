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
 * Created on May 26, 2008 at 9:05:14 PM.
 */

package org.gamegineer.common.internal.core;

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
    private static final String BUNDLE_NAME = "org.gamegineer.common.internal.core.Messages"; //$NON-NLS-1$

    // --- Services ---------------------------------------------------------

    /** The component factory service tracker is not set. */
    public static String Services_componentFactoryServiceTracker_notSet;

    /** The component service tracker is not set. */
    public static String Services_componentServiceTracker_notSet;

    /** The debug options service tracker is not set. */
    public static String Services_debugOptionsServiceTracker_notSet;

    /** The extension registry service tracker is not set. */
    public static String Services_extensionRegistryServiceTracker_notSet;

    /** The framework log service tracker is not set. */
    public static String Services_frameworkLogServiceTracker_notSet;

    /** The logging service tracker is not set. */
    public static String Services_loggingServiceTracker_notSet;


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
