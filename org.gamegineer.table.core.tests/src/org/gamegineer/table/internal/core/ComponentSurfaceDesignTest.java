/*
 * ComponentSurfaceDesignTest.java
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
 * Created on Apr 6, 2012 at 11:32:25 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ComponentSurfaceDesign} class.
 */
public final class ComponentSurfaceDesignTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignTest}
     * class.
     */
    public ComponentSurfaceDesignTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a negative
     * height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Height_Negative()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new ComponentSurfaceDesign( null, 0, 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Width_Negative()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }
}