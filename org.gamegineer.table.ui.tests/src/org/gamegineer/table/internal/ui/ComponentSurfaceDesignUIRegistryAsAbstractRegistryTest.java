/*
 * ComponentSurfaceDesignUIRegistryAsAbstractRegistryTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Nov 16, 2012 at 10:35:28 PM.
 */

package org.gamegineer.table.internal.ui;

import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryTestCase;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.ComponentSurfaceDesignUIRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.util.registry.AbstractRegistry} class.
 */
public final class ComponentSurfaceDesignUIRegistryAsAbstractRegistryTest
    extends AbstractAbstractRegistryTestCase<ComponentSurfaceDesignUIRegistry, ComponentSurfaceDesignId, ComponentSurfaceDesignUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIRegistryAsAbstractRegistryTest} class.
     */
    public ComponentSurfaceDesignUIRegistryAsAbstractRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected ComponentSurfaceDesignUIRegistry createRegistry()
    {
        return new ComponentSurfaceDesignUIRegistry();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryTestCase#getObjectId(org.gamegineer.common.core.util.registry.AbstractRegistry, java.lang.Object)
     */
    @Override
    protected ComponentSurfaceDesignId getObjectId(
        final ComponentSurfaceDesignUIRegistry registry,
        final ComponentSurfaceDesignUI object )
    {
        return registry.getObjectId( object );
    }
}
