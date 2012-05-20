/*
 * AbstractTableContextTestCase.java
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
 * Created on May 19, 2012 at 9:34:42 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITableContext} interface.
 */
public abstract class AbstractTableContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table context under test in the fixture. */
    private ITableContext tableContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableContextTestCase}
     * class.
     */
    protected AbstractTableContextTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table context to be tested.
     * 
     * @return The table context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableContext createTableContext()
        throws Exception;

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        tableContext_ = createTableContext();
        assertNotNull( tableContext_ );
    }

    /**
     * Ensures the {@code createCard} method returns a card that is associated
     * with the table context.
     */
    @Test
    public void testCreateCard_ReturnValue_AssociatedWithTableContext()
    {
        final ICard card = tableContext_.createCard();

        assertNotNull( card );
        assertEquals( tableContext_, card.getTableContext() );
    }

    /**
     * Ensures the {@code createCardPile} method returns a card pile that is
     * associated with the table context.
     */
    @Test
    public void testCreateCardPile_ReturnValue_AssociatedWithTableContext()
    {
        final ICardPile cardPile = tableContext_.createCardPile();

        assertNotNull( cardPile );
        assertEquals( tableContext_, cardPile.getTableContext() );
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromSize_Height_Negative()
    {
        tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( 0, -1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentSurfaceDesignFromSize_Id_Null()
    {
        tableContext_.createComponentSurfaceDesign( null, new Dimension( 0, 0 ) );
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method does not return {@code null}.
     */
    @Test
    public void testCreateComponentSurfaceDesignFromSize_ReturnValue_NonNull()
    {
        assertNotNull( tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( 0, 0 ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a {@code null} size.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentSurfaceDesignFromSize_Size_Null()
    {
        tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createComponentSurfaceDesign(ComponentSurfaceDesignId,
     * Dimension)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromSize_Width_Negative()
    {
        tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), new Dimension( -1, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromWidthHeight_Height_Negative()
    {
        tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
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
        tableContext_.createComponentSurfaceDesign( null, 0, 0 );
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method does not return {@code null}.
     */
    @Test
    public void testCreateComponentSurfaceDesignFromWidthHeight_ReturnValue_NonNull()
    {
        assertNotNull( tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), 0, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code createComponentSurfaceDesign(ComponentSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentSurfaceDesignFromWidthHeight_Width_Negative()
    {
        tableContext_.createComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createTable} method returns a table that is associated
     * with the table context.
     */
    @Test
    public void testCreateTable_ReturnValue_AssociatedWithTableContext()
    {
        final ITable table = tableContext_.createTable();

        assertNotNull( table );
        assertEquals( tableContext_, table.getTableContext() );
    }
}
