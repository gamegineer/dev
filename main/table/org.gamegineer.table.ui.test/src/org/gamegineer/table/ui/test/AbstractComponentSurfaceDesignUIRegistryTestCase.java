/*
 * AbstractComponentSurfaceDesignUIRegistryTestCase.java
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
 * Created on Apr 23, 2012 at 8:06:29 PM.
 */

package org.gamegineer.table.ui.test;

import org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IComponentSurfaceDesignUIRegistry} interface.
 */
public abstract class AbstractComponentSurfaceDesignUIRegistryTestCase
    extends AbstractRegistryTestCase<ComponentSurfaceDesignId, ComponentSurfaceDesignUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentSurfaceDesignUIRegistryTestCase} class.
     */
    protected AbstractComponentSurfaceDesignUIRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#cloneObject(java.lang.Object)
     */
    @Override
    protected ComponentSurfaceDesignUI cloneObject(
        final ComponentSurfaceDesignUI object )
    {
        return TestComponentSurfaceDesignUIs.cloneComponentSurfaceDesignUI( object );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#createUniqueObject()
     */
    @Override
    protected ComponentSurfaceDesignUI createUniqueObject()
    {
        return TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#getObjectId(java.lang.Object)
     */
    @Override
    protected ComponentSurfaceDesignId getObjectId(
        final ComponentSurfaceDesignUI object )
    {
        return object.getId();
    }
}
