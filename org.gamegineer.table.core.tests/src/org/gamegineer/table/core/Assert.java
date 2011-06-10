/*
 * Assert.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jun 9, 2011 at 10:31:32 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.List;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of assertion methods useful for writing table model tests.
 */
@ThreadSafe
public final class Assert
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Assert} class.
     */
    private Assert()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that two cards are equal.
     * 
     * @param expected
     *        The expected value; may be {@code null}.
     * @param actual
     *        The actual value; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two values are not equal.
     */
    public static void assertCardEquals(
        /* @Nullable */
        final ICard expected,
        /* @Nullable */
        final ICard actual )
    {
        if( expected == null )
        {
            assertNull( actual );
        }
        else if( actual == null )
        {
            assertNull( expected );
        }
        else
        {
            assertEquals( expected.getBackDesign(), actual.getBackDesign() );
            assertEquals( expected.getFaceDesign(), actual.getFaceDesign() );
            assertEquals( expected.getLocation(), actual.getLocation() );
            assertEquals( expected.getOrientation(), actual.getOrientation() );
        }
    }

    /**
     * Asserts that two card piles are equal.
     * 
     * @param expected
     *        The expected value; may be {@code null}.
     * @param actual
     *        The actual value; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two values are not equal.
     */
    public static void assertCardPileEquals(
        /* @Nullable */
        final ICardPile expected,
        /* @Nullable */
        final ICardPile actual )
    {
        if( expected == null )
        {
            assertNull( actual );
        }
        else if( actual == null )
        {
            assertNull( expected );
        }
        else
        {
            assertEquals( expected.getBaseDesign(), actual.getBaseDesign() );
            assertEquals( expected.getBaseLocation(), actual.getBaseLocation() );
            assertEquals( expected.getLayout(), actual.getLayout() );

            final List<ICard> expectedCards = expected.getCards();
            final List<ICard> actualCards = actual.getCards();
            assertEquals( expectedCards.size(), actualCards.size() );
            for( int index = 0, size = expectedCards.size(); index < size; ++index )
            {
                assertCardEquals( expectedCards.get( index ), actualCards.get( index ) );
            }
        }
    }

    /**
     * Asserts that two tables are equal.
     * 
     * @param expected
     *        The expected value; may be {@code null}.
     * @param actual
     *        The actual value; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two values are not equal.
     */
    public static void assertTableEquals(
        /* @Nullable */
        final ITable expected,
        /* @Nullable */
        final ITable actual )
    {
        if( expected == null )
        {
            assertNull( actual );
        }
        else if( actual == null )
        {
            assertNull( expected );
        }
        else
        {
            final List<ICardPile> expectedCardPiles = expected.getCardPiles();
            final List<ICardPile> actualCardPiles = actual.getCardPiles();
            assertEquals( expectedCardPiles.size(), actualCardPiles.size() );
            for( int index = 0, size = expectedCardPiles.size(); index < size; ++index )
            {
                assertCardPileEquals( expectedCardPiles.get( index ), actualCardPiles.get( index ) );
            }
        }
    }
}
