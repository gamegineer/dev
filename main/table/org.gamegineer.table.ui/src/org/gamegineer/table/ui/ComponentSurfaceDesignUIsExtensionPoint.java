/*
 * ComponentSurfaceDesignUIsExtensionPoint.java
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
 * Created on Nov 29, 2013 at 9:25:26 PM.
 */

package org.gamegineer.table.ui;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.BundleConstants;

/**
 * A facade for working with the
 * {@code org.gamegineer.table.ui.componentSurfaceDesignUIs} extension point.
 */
@ThreadSafe
public final class ComponentSurfaceDesignUIsExtensionPoint
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The simple identifier of the extension point. */
    public static final String SIMPLE_ID = "componentSurfaceDesignUIs"; //$NON-NLS-1$

    /** The unique identifier of the extension point. */
    public static final String UNIQUE_ID = BundleConstants.SYMBOLIC_NAME + "." + SIMPLE_ID; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIsExtensionPoint} class.
     */
    private ComponentSurfaceDesignUIsExtensionPoint()
    {
    }
}
