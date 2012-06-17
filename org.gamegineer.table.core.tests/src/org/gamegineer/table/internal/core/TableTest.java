/*
 * TableTest.java
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
 * Created on Jun 16, 2012 at 9:33:20 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.ICardPile;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Table}
 * class.
 */
public final class TableTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table under test in the fixture. */
    private Table table_;

    /** The table environment for use in the fixture. */
    private TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableTest} class.
     */
    public TableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile with unique attributes using the fixture table
     * environment.
     * 
     * @return A new card pile; never {@code null}.
     */
    /* @NonNull */
    private ICardPile createUniqueCardPile()
    {
        return CardPiles.createUniqueCardPile( tableEnvironment_ );
    }

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
        tableEnvironment_ = new TableEnvironment();
        table_ = new Table( tableEnvironment_ );
    }

    /**
     * Ensures the {@code getCardPileIndex} method throws an exception when
     * passed a card pile that is absent from the card pile collection.
     */
    @Test( expected = AssertionError.class )
    public void testGetCardPileIndex_CardPile_Absent()
    {
        table_.getCardPileIndex( createUniqueCardPile() );
    }

    /**
     * Ensures the {@code getCardPileIndex} method returns the correct value
     * when passed a card pile present in the card pile collection.
     */
    @Test
    public void testGetCardPileIndex_CardPile_Present()
    {
        final ICardPile cardPile = createUniqueCardPile();
        table_.addCardPile( createUniqueCardPile() );
        table_.addCardPile( cardPile );
        table_.addCardPile( createUniqueCardPile() );

        final int actualValue;
        table_.getTableEnvironment().getLock().lock();
        try
        {
            actualValue = table_.getCardPileIndex( cardPile );
        }
        finally
        {
            table_.getTableEnvironment().getLock().unlock();
        }

        assertEquals( 1, actualValue );
    }
}
