/*
 * AbstractComponentSurfaceDesignRegistryTestCase.java
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
 * Created on Apr 7, 2012 at 9:15:35 PM.
 */

package org.gamegineer.table.core.test;

import org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentSurfaceDesignRegistry;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IComponentSurfaceDesignRegistry} interface.
 */
public abstract class AbstractComponentSurfaceDesignRegistryTestCase
    extends AbstractRegistryTestCase<ComponentSurfaceDesignId, ComponentSurfaceDesign>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentSurfaceDesignRegistryTestCase} class.
     */
    protected AbstractComponentSurfaceDesignRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#cloneObject(java.lang.Object)
     */
    @Override
    protected ComponentSurfaceDesign cloneObject(
        final ComponentSurfaceDesign object )
    {
        return TestComponentSurfaceDesigns.cloneComponentSurfaceDesign( object );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#createUniqueObject()
     */
    @Override
    protected ComponentSurfaceDesign createUniqueObject()
    {
        return TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#getObjectId(java.lang.Object)
     */
    @Override
    protected ComponentSurfaceDesignId getObjectId(
        final ComponentSurfaceDesign object )
    {
        return object.getId();
    }
}
