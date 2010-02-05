/*
 * CardPileBaseDesignIdTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jan 18, 2010 at 10:20:59 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.CardPileBaseDesignId} class.
 */
public final class CardPileBaseDesignIdTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignIdTest} class.
     */
    public CardPileBaseDesignIdTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code equals} method correctly indicates two equal but
     * different card pile base design identifiers are equal.
     */
    @Test
    public void testEquals_Equal_NotSame()
    {
        final String ID = "id"; //$NON-NLS-1$
        final CardPileBaseDesignId id1 = CardPileBaseDesignId.fromString( ID );
        final CardPileBaseDesignId id2 = CardPileBaseDesignId.fromString( ID );

        assertNotSame( id1, id2 );
        assertEquals( id1, id2 );
        assertEquals( id2, id1 ); // symmetric
    }

    /**
     * Ensures the {@code equals} method correctly indicates a card pile base
     * design identifier is equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( "id" ); //$NON-NLS-1$

        assertEquals( id, id ); // reflexive
    }

    /**
     * Ensures the {@code equals} method correctly indicates two card pile base
     * design identifiers whose underlying identifiers differ are unequal.
     */
    @Test
    public void testEquals_Unequal_Id()
    {
        final CardPileBaseDesignId id1 = CardPileBaseDesignId.fromString( "id1" ); //$NON-NLS-1$
        final CardPileBaseDesignId id2 = CardPileBaseDesignId.fromString( "id2" ); //$NON-NLS-1$

        assertFalse( id1.equals( id2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null} card
     * pile base design identifier.
     */
    @Test
    public void testEquals_Unequal_Null()
    {
        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( "id" ); //$NON-NLS-1$

        assertFalse( id.equals( null ) );
    }

    /**
     * Ensures the {@code fromString} method throws an exception when passed a
     * {@code null} underlying identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testFromString_Id_Null()
    {
        CardPileBaseDesignId.fromString( null );
    }

    /**
     * Ensures the {@code hashCode} method returns the same hash code for equal
     * card pile base design identifiers.
     */
    @Test
    public void testHashCode_Equal()
    {
        final String ID = "id"; //$NON-NLS-1$
        final CardPileBaseDesignId id1 = CardPileBaseDesignId.fromString( ID );
        final CardPileBaseDesignId id2 = CardPileBaseDesignId.fromString( ID );

        assertNotSame( id1, id2 );
        assertEquals( id1.hashCode(), id2.hashCode() );
    }

    /**
     * Ensures the {@code hashCode} method returns a different hash code for
     * unequal card pile base design identifiers.
     */
    @Test
    public void testHashCode_Unequal()
    {
        final CardPileBaseDesignId id1 = CardPileBaseDesignId.fromString( "id1" ); //$NON-NLS-1$
        final CardPileBaseDesignId id2 = CardPileBaseDesignId.fromString( "id2" ); //$NON-NLS-1$

        assertTrue( id1.hashCode() != id2.hashCode() );
    }

    /**
     * Ensures the {@code toString} method does not return {@code null}.
     */
    @Test
    public void testToString_ReturnValue_NonNull()
    {
        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( "id" ); //$NON-NLS-1$

        assertNotNull( id.toString() );
    }

    /**
     * Ensures the {@code toString} method returns the same underlying
     * identifier that was used to construct the card pile base design
     * identifier.
     */
    @Test
    public void testToString_ReturnValue_SameId()
    {
        final String expectedId = "id"; //$NON-NLS-1$
        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( expectedId );

        final String actualId = id.toString();

        assertEquals( expectedId, actualId );
    }
}
