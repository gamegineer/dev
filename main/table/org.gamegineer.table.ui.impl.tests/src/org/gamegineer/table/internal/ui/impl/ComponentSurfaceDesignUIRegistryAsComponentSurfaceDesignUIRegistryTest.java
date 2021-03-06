/*
 * ComponentSurfaceDesignUIRegistryAsComponentSurfaceDesignUIRegistryTest.java
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
 * Created on Apr 23, 2012 at 8:23:08 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import org.gamegineer.common.core.util.registry.IRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;
import org.gamegineer.table.ui.test.AbstractComponentSurfaceDesignUIRegistryTestCase;

/**
 * A fixture for testing the {@link ComponentSurfaceDesignUIRegistry} class to
 * ensure it does not violate the contract of the
 * {@link IComponentSurfaceDesignUIRegistry} interface.
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
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected IRegistry<ComponentSurfaceDesignId, ComponentSurfaceDesignUI> createRegistry()
    {
        return new ComponentSurfaceDesignUIRegistry();
    }
}
