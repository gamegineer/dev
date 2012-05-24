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

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import java.awt.Dimension;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.ComponentSurfaceDesign} class.
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
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, Dimension)}
     * constructor throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromSize_Id_Null()
    {
        new ComponentSurfaceDesign( null, new Dimension( 0, 0 ) );
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, Dimension)}
     * constructor makes a copy of the size.
     */
    @Test
    public void testConstructorFromSize_Size_Copy()
    {
        final Dimension size = new Dimension( 1, 2 );
        final Dimension expectedValue = new Dimension( size );
        final ComponentSurfaceDesign componentSurfaceDesign = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), size ); //$NON-NLS-1$

        size.setSize( 100, 200 );
        final Dimension actualValue = componentSurfaceDesign.getSize();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, Dimension)}
     * constructor throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructorFromSize_Size_NegativeHeight()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( 0, -1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, Dimension)}
     * constructor throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructorFromSize_Size_NegativeWidth()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( -1, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, Dimension)}
     * constructor throws an exception when passed a {@code null} size.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromSize_Size_Null()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, int, int)}
     * constructor throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructorFromWidthAndHeight_Height_Negative()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, int, int)}
     * constructor throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromWidthAndHeight_Id_Null()
    {
        new ComponentSurfaceDesign( null, 0, 0 );
    }

    /**
     * Ensures the
     * {@code ComponentSurfaceDesign(ComponentSurfaceDesignId, int, int)}
     * constructor throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructorFromWidthAndHeight_Width_Negative()
    {
        new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getSize} method returns a copy of the size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 1, 1 ); //$NON-NLS-1$
        final Dimension size = componentSurfaceDesign.getSize();
        final Dimension expectedSize = new Dimension( size );

        size.setSize( 101, 202 );

        assertEquals( expectedSize, componentSurfaceDesign.getSize() );
    }
}
