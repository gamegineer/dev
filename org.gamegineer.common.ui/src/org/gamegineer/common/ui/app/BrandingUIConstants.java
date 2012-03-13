/*
 * BrandingUIConstants.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 21, 2012 at 9:23:12 PM.
 */

package org.gamegineer.common.ui.app;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of constants that define the well-known application branding
 * properties for the user interface.
 */
@ThreadSafe
public final class BrandingUIConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the property that defines the window images for the
     * application.
     * 
     * <p>
     * This property represents an array of one or more images to be used for
     * the application. The expectation is that the array will contain the same
     * image rendered at different sizes (16x16, 32x32, etc.).
     * </p>
     * 
     * <p>
     * The property value is a comma-separated list of paths, where each path is
     * a fully-qualified URL or a path relative to the branding bundle.
     * </p>
     */
    public static String WINDOW_IMAGES = "windowImages"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BrandingUIConstants} class.
     */
    private BrandingUIConstants()
    {
    }
}
