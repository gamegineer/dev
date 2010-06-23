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
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.persistence.memento.IMemento;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

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
     * Creates a new table from the specified memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return A new table; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     */
    /* @NonNull */
    protected abstract ITable createTable(
        /* @NonNull */
        IMemento memento )
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
        mocksControl_ = EasyMock.createControl();
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
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code addCardPile} method adds a card pile that is absent
     * from the table.
     */
    @Test
    public void testAddCardPile_CardPile_Absent_AddsCardPile()
    {
        final ICardPile cardPile = CardPiles.createUniqueCardPile();

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
        final ITableListener listener1 = mocksControl_.createMock( ITableListener.class );
        listener1.cardPileAdded( EasyMock.notNull( TableContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableListener listener2 = mocksControl_.createMock( ITableListener.class );
        listener2.cardPileAdded( EasyMock.notNull( TableContentChangedEvent.class ) );
        mocksControl_.replay();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );

        table_.addCardPile( CardPiles.createUniqueCardPile() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCardPile} method fires a card pile added event when
     * the card pile is absent from the table.
     */
    @Test
    public void testAddCardPile_CardPile_Absent_FiresCardPileAddedEvent()
    {
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        listener.cardPileAdded( EasyMock.notNull( TableContentChangedEvent.class ) );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.addCardPile( CardPiles.createUniqueCardPile() );

        mocksControl_.verify();
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
        final ICardPile cardPile = CardPiles.createUniqueCardPile();
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
        final ICardPile cardPile = CardPiles.createUniqueCardPile();
        table_.addCardPile( cardPile );
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.addCardPile( cardPile );

        mocksControl_.verify();
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
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
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
        final ICardPile initialCardPile = CardPiles.createUniqueCardPile();
        initialCardPile.setLocation( location );
        table_.addCardPile( initialCardPile );
        final ICardPile expectedCardPile = CardPiles.createUniqueCardPile();
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
        final ICardPile expectedCardPile = CardPiles.createUniqueCardPile();
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

        table_.addCardPile( CardPiles.createUniqueCardPile() );

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
     * Ensures the {@code getMemento} method returns a well-formed memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetMemento()
        throws Exception
    {
        final ICardPile cardPile = CardPiles.createUniqueCardPile();
        cardPile.addCard( Cards.createUniqueCard() );
        table_.addCardPile( cardPile );
        final IMemento expectedMemento = table_.getMemento();

        final ITable actualTable = createTable( expectedMemento );
        final IMemento actualMemento = actualTable.getMemento();

        assertEquals( expectedMemento, actualMemento );
    }

    /**
     * Ensures the {@code getMemento} method does not return {@code null}.
     */
    @Test
    public void testGetMemento_ReturnValue_NonNull()
    {
        assertNotNull( table_.getMemento() );
    }

    /**
     * Ensures the {@code removeCardPile} method does not fire a card pile
     * removed event for a card pile that is absent from the table.
     */
    @Test
    public void testRemoveCardPile_Empty_DoesNotFireCardPileRemovedEvent()
    {
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.removeCardPile( CardPiles.createUniqueCardPile() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCardPile} method does not remove a card pile
     * that is absent from the table.
     */
    @Test
    public void testRemoveCardPile_CardPile_Absent_DoesNotRemoveCardPile()
    {
        final ICardPile cardPile = CardPiles.createUniqueCardPile();

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
        final ICardPile cardPile = CardPiles.createUniqueCardPile();
        table_.addCardPile( cardPile );
        final ITableListener listener1 = mocksControl_.createMock( ITableListener.class );
        listener1.cardPileRemoved( EasyMock.notNull( TableContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableListener listener2 = mocksControl_.createMock( ITableListener.class );
        listener2.cardPileRemoved( EasyMock.notNull( TableContentChangedEvent.class ) );
        mocksControl_.replay();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );

        table_.removeCardPile( cardPile );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCardPile} method fires a card pile removed event
     * when the card pile is present on the table.
     */
    @Test
    public void testRemoveCardPile_CardPile_Present_FiresCardPileRemovedEvent()
    {
        final ICardPile cardPile = CardPiles.createUniqueCardPile();
        table_.addCardPile( cardPile );
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        listener.cardPileRemoved( EasyMock.notNull( TableContentChangedEvent.class ) );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.removeCardPile( cardPile );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCardPile} method removes a card pile that is
     * present on the table.
     */
    @Test
    public void testRemoveCardPile_CardPile_Present_RemovesCardPile()
    {
        final ICardPile cardPile = CardPiles.createUniqueCardPile();
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
        table_.removeTableListener( mocksControl_.createMock( ITableListener.class ) );
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
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        listener.cardPileAdded( EasyMock.notNull( TableContentChangedEvent.class ) );
        mocksControl_.replay();
        table_.addTableListener( listener );
        table_.addCardPile( CardPiles.createUniqueCardPile() );

        table_.removeTableListener( listener );
        table_.addCardPile( CardPiles.createUniqueCardPile() );

        mocksControl_.verify();
    }
}
