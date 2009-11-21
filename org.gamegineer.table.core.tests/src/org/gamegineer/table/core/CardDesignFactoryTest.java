/*
 * CardDesignFactoryTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Nov 20, 2009 at 10:12:36 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.CardDesignFactory}
 * class.
 */
public final class CardDesignFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignFactoryTest} class.
     */
    public CardDesignFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Dimension)} method
     * throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardDesignFromSize_Height_Negative()
    {
        CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), new Dimension( 0, -1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Dimension)} method
     * throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardDesignFromSize_Id_Null()
    {
        CardDesignFactory.createCardDesign( null, new Dimension( 0, 0 ) );
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Dimension)} method does
     * not return {@code null}.
     */
    @Test
    public void testCreateCardDesignFromSize_ReturnValue_NonNull()
    {
        assertNotNull( CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), new Dimension( 0, 0 ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Dimension)} method
     * throws an exception when passed a {@code null} size.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardDesignFromSize_Size_Null()
    {
        CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Dimension)} method
     * throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardDesignFromSize_Width_Negative()
    {
        CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), new Dimension( -1, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Integer, Integer)}
     * method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardDesignFromWidthHeight_Height_Negative()
    {
        CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Integer, Integer)}
     * method throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardDesignFromWidthHeight_Id_Null()
    {
        CardDesignFactory.createCardDesign( null, 0, 0 );
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Integer, Integer)}
     * method does not return {@code null}.
     */
    @Test
    public void testCreateCardDesignFromWidthHeight_ReturnValue_NonNull()
    {
        assertNotNull( CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), 0, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardDesign(CardDesignId, Integer, Integer)}
     * method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardDesignFromWidthHeight_Width_Negative()
    {
        CardDesignFactory.createCardDesign( CardDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }
}
