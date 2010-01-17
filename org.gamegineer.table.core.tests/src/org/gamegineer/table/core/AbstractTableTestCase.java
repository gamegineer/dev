/*
 * AbstractTableTestCase.java
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
 * Created on Oct 6, 2009 at 10:58:22 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.util.List;
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
        return CardFactory.createCard( CardDesigns.createUniqueCardDesign(), CardDesigns.createUniqueCardDesign() );
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
    public void testAddCard_Card_Absent_AddsCard()
    {
        final ICard card = createCard();

        table_.addCard( card );

        assertTrue( table_.getCards().contains( card ) );
    }

    /**
     * Ensures the {@code addCard} method catches any exception thrown by the
     * {@code cardAdded} method of a table listener.
     */
    @Test
    public void testAddCard_Card_Absent_CatchesListenerException()
    {
        final MockTableListener listener1 = new MockTableListener()
        {
            @Override
            public void cardAdded(
                final TableContentChangedEvent event )
            {
                super.cardAdded( event );

                throw new RuntimeException();
            }
        };
        final MockTableListener listener2 = new MockTableListener();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );

        table_.addCard( createCard() );

        assertEquals( 1, listener2.getCardAddedEventCount() );
    }

    /**
     * Ensures the {@code addCard} method fires a card added event when the card
     * is absent from the table.
     */
    @Test
    public void testAddCard_Card_Absent_FiresCardAddedEvent()
    {
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );

        table_.addCard( createCard() );

        assertEquals( 1, listener.getCardAddedEventCount() );
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

        final List<ICard> cards = table_.getCards();
        assertTrue( cards.contains( card ) );
        assertEquals( 1, cards.size() );
    }

    /**
     * Ensures the {@code addTableListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableListener_Listener_Null()
    {
        table_.addTableListener( null );
    }

    /**
     * Ensures the {@code addTableListener} method throws an exception when
     * passed a listener that is present in the table listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableListener_Listener_Present()
    {
        final ITableListener listener = new MockTableListener();
        table_.addTableListener( listener );

        table_.addTableListener( listener );
    }

    /**
     * Ensures the {@code getCard} method returns {@code null} when a card is
     * absent at the specified location.
     */
    @Test
    public void testGetCard_Location_CardAbsent()
    {
        assertNull( table_.getCard( new Point( 0, 0 ) ) );
    }

    /**
     * Ensures the {@code getCard} method returns the most recently added card
     * when multiple cards are present at the specified location.
     */
    @Test
    public void testGetCard_Location_MultipleCardsPresent()
    {
        final Point location = new Point( 7, 42 );
        final ICard initialCard = createCard();
        initialCard.setLocation( location );
        table_.addCard( initialCard );
        final ICard expectedCard = createCard();
        expectedCard.setLocation( location );
        table_.addCard( expectedCard );

        final ICard actualCard = table_.getCard( location );

        assertSame( expectedCard, actualCard );
    }

    /**
     * Ensures the {@code getCard} method returns the appropriate card when a
     * single card is present at the specified location.
     */
    @Test
    public void testGetCard_Location_SingleCardPresent()
    {
        final Point location = new Point( 7, 42 );
        final ICard expectedCard = createCard();
        expectedCard.setLocation( location );
        table_.addCard( expectedCard );

        final ICard actualCard = table_.getCard( location );

        assertSame( expectedCard, actualCard );
    }

    /**
     * Ensures the {@code getCard} method throws an exception when passed a
     * {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCard_Location_Null()
    {
        table_.getCard( null );
    }

    /**
     * Ensures the {@code getCards} method returns a copy of the card
     * collection.
     */
    @Test
    public void testGetCards_ReturnValue_Copy()
    {
        final List<ICard> cards = table_.getCards();
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
     * Ensures the {@code removeCard} method catches any exception thrown by the
     * {@code cardRemoved} method of a table listener.
     */
    @Test
    public void testRemoveCard_Card_Present_CatchesListenerException()
    {
        final MockTableListener listener1 = new MockTableListener()
        {
            @Override
            public void cardRemoved(
                final TableContentChangedEvent event )
            {
                super.cardRemoved( event );

                throw new RuntimeException();
            }
        };
        final MockTableListener listener2 = new MockTableListener();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );
        final ICard card = createCard();
        table_.addCard( card );

        table_.removeCard( card );

        assertEquals( 1, listener2.getCardRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCard} method fires a card removed event when the
     * card is present on the table.
     */
    @Test
    public void testRemoveCard_Card_Present_FiresCardRemovedEvent()
    {
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );
        final ICard card = createCard();
        table_.addCard( card );

        table_.removeCard( card );

        assertEquals( 1, listener.getCardRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCard} method removes a card that is present on
     * the table.
     */
    @Test
    public void testRemoveCard_Card_Present_RemovesCard()
    {
        final ICard card = createCard();
        table_.addCard( card );

        table_.removeCard( card );

        final List<ICard> cards = table_.getCards();
        assertFalse( cards.contains( card ) );
        assertEquals( 0, cards.size() );
    }

    /**
     * Ensures the {@code removeTableListener} method throws an exception when
     * passed a listener that is absent from the table listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableListener_Listener_Absent()
    {
        table_.removeTableListener( new MockTableListener() );
    }

    /**
     * Ensures the {@code removeTableListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableListener_Listener_Null()
    {
        table_.removeTableListener( null );
    }

    /**
     * Ensures the {@code removeTableListener} removes a listener that is
     * present in the table listener collection.
     */
    @Test
    public void testRemoveTableListener_Listener_Present()
    {
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );
        table_.addCard( createCard() );

        table_.removeTableListener( listener );

        table_.addCard( createCard() );
        assertEquals( 1, listener.getCardAddedEventCount() );
    }
}
