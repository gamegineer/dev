/*
 * Geometry.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Sep 11, 2010 at 3:35:03 PM.
 */

package org.gamegineer.common.ui.util;

import java.awt.Point;
import java.awt.Rectangle;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for performing simple geometric operations on
 * the AWT geometry classes.
 */
@ThreadSafe
public final class Geometry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Geometry} class.
     */
    private Geometry()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Calculates the center point of the specified rectangle.
     * 
     * @param rect
     *        A rectangle; must not be {@code null}.
     * 
     * @return The center point of the specified rectangle; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code rect} is {@code null}.
     */
    /* @NonNull */
    public static Point calculateCenterPoint(
        /* @NonNull */
        final Rectangle rect )
    {
        return new Point( rect.x + rect.width / 2, rect.y + rect.height / 2 );
    }
}
