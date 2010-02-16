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
     * Creates a card pile suitable for testing.
     * 
     * @return A new card pile; never {@code null}.
     */
    /* @NonNull */
    private static ICardPile createCardPile()
    {
        return CardPileFactory.createCardPile( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );
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
     * Ensures the {@code addCardPile} method adds a card pile that is absent
     * from the table.
     */
    @Test
    public void testAddCardPile_CardPile_Absent_AddsCardPile()
    {
        final ICardPile cardPile = createCardPile();

        table_.addCardPile( cardPile );

        assertTrue( table_.getCardPiles().contains( cardPile ) );
    }

    /**
     * Ensures the {@code addCardPile} method catches any exception thrown by
     * the {@code cardPileAdded} method of a table listener.
     */
    @Test
    public void testAddCardPile_CardPile_Absent_CatchesListenerException()
    {
        final MockTableListener listener1 = new MockTableListener()
        {
            @Override
            public void cardPileAdded(
                final TableContentChangedEvent event )
            {
                super.cardPileAdded( event );

                throw new RuntimeException();
            }
        };
        final MockTableListener listener2 = new MockTableListener();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );

        table_.addCardPile( createCardPile() );

        assertEquals( 1, listener2.getCardPileAddedEventCount() );
    }

    /**
     * Ensures the {@code addCardPile} method fires a card pile added event when
     * the card pile is absent from the table.
     */
    @Test
    public void testAddCardPile_CardPile_Absent_FiresCardPileAddedEvent()
    {
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );

        table_.addCardPile( createCardPile() );

        assertEquals( 1, listener.getCardPileAddedEventCount() );
    }

    /**
     * Ensures the {@code addCardPile} method throws an exception when passed a
     * {@code null} card pile.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardPile_CardPile_Null()
    {
        table_.addCardPile( null );
    }

    /**
     * Ensures the {@code addCardPile} method does not add a card pile that is
     * present on the table.
     */
    @Test
    public void testAddCardPile_CardPile_Present_DoesNotAddCard()
    {
        final ICardPile cardPile = createCardPile();
        table_.addCardPile( cardPile );

        table_.addCardPile( cardPile );

        final List<ICardPile> cardPiles = table_.getCardPiles();
        assertTrue( cardPiles.contains( cardPile ) );
        assertEquals( 1, cardPiles.size() );
    }

    /**
     * Ensures the {@code addCardPile} method does not fire a card pile added
     * event when the card pile is present on the table.
     */
    @Test
    public void testAddCardPile_CardPile_Present_DoesNotFireCardPileAddedEvent()
    {
        final ICardPile cardPile = createCardPile();
        table_.addCardPile( cardPile );
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );

        table_.addCardPile( cardPile );

        assertEquals( 0, listener.getCardPileAddedEventCount() );
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
     * Ensures the {@code getCardPile} method returns {@code null} when a card
     * pile is absent at the specified location.
     */
    @Test
    public void testGetCardPile_Location_CardPileAbsent()
    {
        assertNull( table_.getCardPile( new Point( 0, 0 ) ) );
    }

    /**
     * Ensures the {@code getCardPile} method returns the most recently added
     * card pile when multiple card piles are present at the specified location.
     */
    @Test
    public void testGetCardPile_Location_MultipleCardPilesPresent()
    {
        final Point location = new Point( 7, 42 );
        final ICardPile initialCardPile = createCardPile();
        initialCardPile.setLocation( location );
        table_.addCardPile( initialCardPile );
        final ICardPile expectedCardPile = createCardPile();
        expectedCardPile.setLocation( location );
        table_.addCardPile( expectedCardPile );

        final ICardPile actualCardPile = table_.getCardPile( location );

        assertSame( expectedCardPile, actualCardPile );
    }

    /**
     * Ensures the {@code getCardPile} method returns the appropriate card pile
     * when a single card pile is present at the specified location.
     */
    @Test
    public void testGetCardPile_Location_SingleCardPilePresent()
    {
        final Point location = new Point( 7, 42 );
        final ICardPile expectedCardPile = createCardPile();
        expectedCardPile.setLocation( location );
        table_.addCardPile( expectedCardPile );

        final ICardPile actualCardPile = table_.getCardPile( location );

        assertSame( expectedCardPile, actualCardPile );
    }

    /**
     * Ensures the {@code getCardPile} method throws an exception when passed a
     * {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPile_Location_Null()
    {
        table_.getCardPile( null );
    }

    /**
     * Ensures the {@code getCardPiles} method returns a copy of the card pile
     * collection.
     */
    @Test
    public void testGetCardPiles_ReturnValue_Copy()
    {
        final List<ICardPile> cardPiles = table_.getCardPiles();
        final int expectedCardPilesSize = cardPiles.size();

        table_.addCardPile( createCardPile() );

        assertEquals( expectedCardPilesSize, cardPiles.size() );
    }

    /**
     * Ensures the {@code getCardPiles} method does not return {@code null}.
     */
    @Test
    public void testGetCardPiles_ReturnValue_NonNull()
    {
        assertNotNull( table_.getCardPiles() );
    }

    /**
     * Ensures the {@code removeCardPile} method does not fire a card pile
     * removed event for a card pile that is absent from the table.
     */
    @Test
    public void testRemoveCardPile_Empty_DoesNotFireCardPileRemovedEvent()
    {
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );

        table_.removeCardPile( createCardPile() );

        assertEquals( 0, listener.getCardPileRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCardPile} method does not remove a card pile
     * that is absent from the table.
     */
    @Test
    public void testRemoveCardPile_CardPile_Absent_DoesNotRemoveCardPile()
    {
        final ICardPile cardPile = createCardPile();

        table_.removeCardPile( cardPile );

        assertEquals( 0, table_.getCardPiles().size() );
    }

    /**
     * Ensures the {@code removeCardPile} method throws an exception when passed
     * a {@code null} card pile.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardPile_CardPile_Null()
    {
        table_.removeCardPile( null );
    }

    /**
     * Ensures the {@code removeCardPile} method catches any exception thrown by
     * the {@code cardPileRemoved} method of a table listener.
     */
    @Test
    public void testRemoveCardPile_CardPile_Present_CatchesListenerException()
    {
        final MockTableListener listener1 = new MockTableListener()
        {
            @Override
            public void cardPileRemoved(
                final TableContentChangedEvent event )
            {
                super.cardPileRemoved( event );

                throw new RuntimeException();
            }
        };
        final MockTableListener listener2 = new MockTableListener();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );
        final ICardPile cardPile = createCardPile();
        table_.addCardPile( cardPile );

        table_.removeCardPile( cardPile );

        assertEquals( 1, listener2.getCardPileRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCardPile} method fires a card pile removed event
     * when the card pile is present on the table.
     */
    @Test
    public void testRemoveCardPile_CardPile_Present_FiresCardPileRemovedEvent()
    {
        final MockTableListener listener = new MockTableListener();
        table_.addTableListener( listener );
        final ICardPile cardPile = createCardPile();
        table_.addCardPile( cardPile );

        table_.removeCardPile( cardPile );

        assertEquals( 1, listener.getCardPileRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCardPile} method removes a card pile that is
     * present on the table.
     */
    @Test
    public void testRemoveCardPile_CardPile_Present_RemovesCardPile()
    {
        final ICardPile cardPile = createCardPile();
        table_.addCardPile( cardPile );

        table_.removeCardPile( cardPile );

        final List<ICardPile> cardPiles = table_.getCardPiles();
        assertFalse( cardPiles.contains( cardPile ) );
        assertEquals( 0, cardPiles.size() );
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
        table_.addCardPile( createCardPile() );

        table_.removeTableListener( listener );

        table_.addCardPile( createCardPile() );
        assertEquals( 1, listener.getCardPileAddedEventCount() );
    }
}
