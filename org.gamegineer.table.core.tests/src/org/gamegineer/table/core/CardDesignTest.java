/*
 * CardDesignTest.java
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
 * Created on Oct 10, 2009 at 11:59:09 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.CardDesign} class.
 */
public final class CardDesignTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignTest} class.
     */
    public CardDesignTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Name_Null()
    {
        new CardDesign( null );
    }

    /**
     * Ensures the {@code equals} method correctly indicates two equal but
     * different card designs are equal.
     */
    @Test
    public void testEquals_Equal_NotSame()
    {
        final String NAME = "name"; //$NON-NLS-1$
        final CardDesign design1 = new CardDesign( NAME );
        final CardDesign design2 = new CardDesign( NAME );

        assertNotSame( design1, design2 );
        assertEquals( design1, design2 );
        assertEquals( design2, design1 ); // symmetric
    }

    /**
     * Ensures the {@code equals} method correctly indicates a card design is
     * equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        final CardDesign design = new CardDesign( "name" ); //$NON-NLS-1$

        assertEquals( design, design ); // reflexive
    }

    /**
     * Ensures the {@code equals} method correctly indicates two card designs
     * whose names differ are unequal.
     */
    @Test
    public void testEquals_Unequal_Name()
    {
        final CardDesign design1 = new CardDesign( "name1" ); //$NON-NLS-1$
        final CardDesign design2 = new CardDesign( "name2" ); //$NON-NLS-1$

        assertFalse( design1.equals( design2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null} card
     * design.
     */
    @Test
    public void testEquals_Unequal_Null()
    {
        final CardDesign design = new CardDesign( "name" ); //$NON-NLS-1$

        assertFalse( design.equals( null ) );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        final CardDesign design = new CardDesign( "name" ); //$NON-NLS-1$

        assertNotNull( design.getName() );
    }

    /**
     * Ensures the {@code hashCode} method returns the same hash code for equal
     * card designs.
     */
    @Test
    public void testHashCode_Equal()
    {
        final String NAME = "name"; //$NON-NLS-1$
        final CardDesign design1 = new CardDesign( NAME );
        final CardDesign design2 = new CardDesign( NAME );

        assertNotSame( design1, design2 );
        assertEquals( design1.hashCode(), design2.hashCode() );
    }

    /**
     * Ensures the {@code hashCode} method returns a different hash code for
     * unequal card designs.
     */
    @Test
    public void testHashCode_Unequal()
    {
        final CardDesign design1 = new CardDesign( "name1" ); //$NON-NLS-1$
        final CardDesign design2 = new CardDesign( "name2" ); //$NON-NLS-1$

        assertTrue( design1.hashCode() != design2.hashCode() );
    }
}
