/*
 * ComponentModelFactoryTest.java
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
 * Created on Jun 1, 2012 at 9:00:48 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentModelFactory} class.
 */
public final class ComponentModelFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelFactoryTest}
     * class.
     */
    public ComponentModelFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createComponentModel} method throws an exception when
     * passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentModel_Component_Null()
    {
        ComponentModelFactory.createComponentModel( null );
    }

    /**
     * Ensures the {@code createContainerModel} method throws an exception when
     * passed a {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateContainerModel_Container_Null()
    {
        ComponentModelFactory.createContainerModel( null );
    }
}
