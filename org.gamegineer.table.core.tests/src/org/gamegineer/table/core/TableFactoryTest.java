/*
 * TableFactoryTest.java
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
 * Created on Oct 6, 2009 at 11:07:48 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.TableFactory}
 * class.
 */
public final class TableFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFactoryTest} class.
     */
    public TableFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromSize_Height_Negative()
    {
        TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( 0, -1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentSurfaceDesignFromSize_Id_Null()
    {
        TableFactory.createComponentSurfaceDesign( null, new Dimension( 0, 0 ) );
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method does not return {@code null}.
     */
    @Test
    public void testCreateComponentSurfaceDesignFromSize_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( 0, 0 ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a {@code null} size.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentSurfaceDesignFromSize_Size_Null()
    {
        TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromSize_Width_Negative()
    {
        TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( -1, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromWidthHeight_Height_Negative()
    {
        TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentSurfaceDesignFromWidthHeight_Id_Null()
    {
        TableFactory.createComponentSurfaceDesign( null, 0, 0 );
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method does not return {@code null}.
     */
    @Test
    public void testCreateComponentSurfaceDesignFromWidthHeight_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 0, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromWidthHeight_Width_Negative()
    {
        TableFactory.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createTable()} method does not return {@code null}.
     */
    @Test
    public void testCreateTable_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createTable() );
    }
}
