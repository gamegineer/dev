/*
 * ComponentSurfaceDesignsExtensionPoint.java
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
 * Created on Sep 27, 2013 at 10:43:57 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.BundleConstants;

/**
 * A facade for working with the
 * {@code org.gamegineer.table.core.componentSurfaceDesigns} extension point.
 */
@ThreadSafe
public final class ComponentSurfaceDesignsExtensionPoint
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The simple identifier of the extension point. */
    public static final String SIMPLE_ID = "componentSurfaceDesigns"; //$NON-NLS-1$

    /** The unique identifier of the extension point. */
    public static final String UNIQUE_ID = BundleConstants.SYMBOLIC_NAME + "." + SIMPLE_ID; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignsExtensionPoint} class.
     */
    private ComponentSurfaceDesignsExtensionPoint()
    {
    }
}
