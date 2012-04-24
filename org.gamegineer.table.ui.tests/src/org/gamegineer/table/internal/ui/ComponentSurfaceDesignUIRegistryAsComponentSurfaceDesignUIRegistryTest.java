/*
 * ComponentSurfaceDesignUIRegistryAsComponentSurfaceDesignUIRegistryTest.java
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
 * Created on Apr 23, 2012 at 8:23:08 PM.
 */

package org.gamegineer.table.internal.ui;

import org.gamegineer.table.ui.AbstractComponentSurfaceDesignUIRegistryTestCase;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.ComponentSurfaceDesignUIRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry} interface.
 */
public final class ComponentSurfaceDesignUIRegistryAsComponentSurfaceDesignUIRegistryTest
    extends AbstractComponentSurfaceDesignUIRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIRegistryAsComponentSurfaceDesignUIRegistryTest}
     * class.
     */
    public ComponentSurfaceDesignUIRegistryAsComponentSurfaceDesignUIRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractComponentSurfaceDesignUIRegistryTestCase#createComponentSurfaceDesignUIRegistry()
     */
    @Override
    protected IComponentSurfaceDesignUIRegistry createComponentSurfaceDesignUIRegistry()
    {
        return new ComponentSurfaceDesignUIRegistry();
    }
}
