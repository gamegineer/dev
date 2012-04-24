/*
 * ComponentSurfaceDesignUIAsComponentSurfaceDesignUITest.java
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
 * Created on Apr 23, 2012 at 8:17:20 PM.
 */

package org.gamegineer.table.internal.ui;

import javax.swing.Icon;
import org.easymock.EasyMock;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.ui.AbstractComponentSurfaceDesignUITestCase;
import org.gamegineer.table.ui.IComponentSurfaceDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.ComponentSurfaceDesignUI} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.IComponentSurfaceDesignUI} interface.
 */
public final class ComponentSurfaceDesignUIAsComponentSurfaceDesignUITest
    extends AbstractComponentSurfaceDesignUITestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIAsComponentSurfaceDesignUITest} class.
     */
    public ComponentSurfaceDesignUIAsComponentSurfaceDesignUITest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractComponentSurfaceDesignUITestCase#createComponentSurfaceDesignUI()
     */
    @Override
    protected IComponentSurfaceDesignUI createComponentSurfaceDesignUI()
    {
        return new ComponentSurfaceDesignUI( ComponentSurfaceDesignId.fromString( "id" ), "name", EasyMock.createMock( Icon.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
