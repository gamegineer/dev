/*
 * AbstractTableTestCase.java
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
 * Created on Oct 6, 2009 at 10:58:22 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITable} interface.
 */
public abstract class AbstractTableTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table under test in the fixture. */
    private ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableTestCase} class.
     */
    protected AbstractTableTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a card suitable for testing.
     * 
     * @return A new card; never {@code null}.
     */
    /* @NonNull */
    private static ICard createCard()
    {
        return CardFactory.createCard( CardDesign.EMPTY, CardDesign.EMPTY );
    }

    /**
     * Creates the table to be tested.
     * 
     * @return The table to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITable createTable()
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
        table_ = createTable();
        assertNotNull( table_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        table_ = null;
    }

    /**
     * Ensures the {@code addCard} method adds a card that is absent from the
     * table.
     */
    @Test
    public void testAddCard_Card_Absent()
    {
        final ICard card = createCard();

        table_.addCard( card );

        assertTrue( table_.getCards().contains( card ) );
    }

    /**
     * Ensures the {@code addCard} method throws an exception when passed a
     * {@code null} card.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCard_Card_Null()
    {
        table_.addCard( null );
    }

    /**
     * Ensures the {@code addCard} method does not add a card that is present on
     * the table.
     */
    @Test
    public void testAddCard_Card_Present()
    {
        final ICard card = createCard();
        table_.addCard( card );

        table_.addCard( card );

        final Collection<ICard> cards = table_.getCards();
        assertTrue( cards.contains( card ) );
        assertEquals( 1, cards.size() );
    }

    /**
     * Ensures the {@code getCards} method returns a copy of the card
     * collection.
     */
    @Test
    public void testGetCards_ReturnValue_Copy()
    {
        final Collection<ICard> cards = table_.getCards();
        final int expectedCardsSize = cards.size();

        table_.addCard( createCard() );

        assertEquals( expectedCardsSize, cards.size() );
    }

    /**
     * Ensures the {@code getCards} method does not return {@code null}.
     */
    @Test
    public void testGetCards_ReturnValue_NonNull()
    {
        assertNotNull( table_.getCards() );
    }

    /**
     * Ensures the {@code removeCard} method does not remove a card that is
     * absent from the table.
     */
    @Test
    public void testRemoveCard_Card_Absent()
    {
        final ICard card = createCard();

        table_.removeCard( card );

        assertEquals( 0, table_.getCards().size() );
    }

    /**
     * Ensures the {@code removeCard} method throws an exception when passed a
     * {@code null} card.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCard_Card_Null()
    {
        table_.removeCard( null );
    }

    /**
     * Ensures the {@code removeCard} method removes a card that is present on
     * the table.
     */
    @Test
    public void testRemoveCard_Card_Present()
    {
        final ICard card = createCard();
        table_.addCard( card );

        table_.removeCard( card );

        final Collection<ICard> cards = table_.getCards();
        assertFalse( cards.contains( card ) );
        assertEquals( 0, cards.size() );
    }
}
