/*
 * AbstractTableTestCase.java
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
 * Created on Oct 6, 2009 at 10:58:22 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.table.core.Assert.assertTableEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.util.List;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITable} interface.
 * 
 * @param <TableEnvironmentType>
 *        The type of the table environment.
 * @param <TableType>
 *        The type of the table.
 */
public abstract class AbstractTableTestCase<TableEnvironmentType extends ITableEnvironment, TableType extends ITable>
    extends AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table under test in the fixture. */
    private TableType table_;

    /** The table environment for use in the fixture. */
    private TableEnvironmentType tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableTestCase} class.
     */
    protected AbstractTableTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#assertMementoOriginatorEquals(org.gamegineer.common.core.util.memento.IMementoOriginator, org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void assertMementoOriginatorEquals(
        final IMementoOriginator expected,
        final IMementoOriginator actual )
    {
        final ITable expectedTable = (ITable)expected;
        final ITable actualTable = (ITable)actual;
        assertTableEquals( expectedTable, actualTable );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected IMementoOriginator createMementoOriginator()
    {
        return table_;
    }

    /**
     * Creates the table to be tested.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * 
     * @return The table to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract TableType createTable(
        /* @NonNull */
        TableEnvironmentType tableEnvironment )
        throws Exception;

    /**
     * Creates the table environment for use in the fixture.
     * 
     * @return The table environment for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected abstract TableEnvironmentType createTableEnvironment();

    /**
     * Creates a new card pile with a unique base design for the fixture table.
     * 
     * @return A new card pile; never {@code null}.
     */
    /* @NonNull */
    private ICardPile createUniqueCardPile()
    {
        return CardPiles.createUniqueCardPile( table_ );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final ITable table = (ITable)mementoOriginator;
        final ICardPile cardPile = CardPiles.createUniqueCardPile( table );
        cardPile.addComponent( Cards.createUniqueCard( table ) );
        table.addCardPile( cardPile );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        tableEnvironment_ = createTableEnvironment();
        assertNotNull( tableEnvironment_ );
        table_ = createTable( tableEnvironment_ );
        assertNotNull( table_ );

        super.setUp();
    }

    /**
     * Ensures the {@code addCardPile} method adds a card pile to the table.
     */
    @Test
    public void testAddCardPile_AddsCardPile()
    {
        final ICardPile cardPile = createUniqueCardPile();

        table_.addCardPile( cardPile );

        assertTrue( table_.getCardPiles().contains( cardPile ) );
        assertSame( table_, cardPile.getTable() );
    }

    /**
     * Ensures the {@code addCardPile} method throws an exception when passed an
     * illegal card pile that was created by a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardPile_CardPile_Illegal_CreatedByDifferentTableEnvironment()
    {
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();
        final ICardPile cardPile = CardPiles.createUniqueCardPile( otherTableEnvironment.createTable() );

        table_.addCardPile( cardPile );
    }

    /**
     * Ensures the {@code addCardPile} method throws an exception when passed an
     * illegal card pile that is already contained in a table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardPile_CardPile_Illegal_Owned()
    {
        final ICardPile cardPile = createUniqueCardPile();
        table_.addCardPile( cardPile );

        table_.addCardPile( cardPile );
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
     * Ensures the {@code addCardPile} method fires a card pile added event.
     */
    @Test
    public void testAddCardPile_FiresCardPileAddedEvent()
    {
        final ICardPile cardPile = createUniqueCardPile();
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        final Capture<TableContentChangedEvent> eventCapture = new Capture<TableContentChangedEvent>();
        listener.cardPileAdded( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.addCardPile( cardPile );

        mocksControl_.verify();
        assertSame( table_, eventCapture.getValue().getTable() );
        assertSame( cardPile, eventCapture.getValue().getCardPile() );
        assertEquals( 0, eventCapture.getValue().getCardPileIndex() );
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
     * Ensures the card pile added event catches any exception thrown by the
     * {@code cardPileAdded} method of a table listener.
     */
    @Test
    public void testCardPileAdded_CatchesListenerException()
    {
        final ITableListener listener1 = mocksControl_.createMock( ITableListener.class );
        listener1.cardPileAdded( EasyMock.notNull( TableContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableListener listener2 = mocksControl_.createMock( ITableListener.class );
        listener2.cardPileAdded( EasyMock.notNull( TableContentChangedEvent.class ) );
        mocksControl_.replay();
        table_.addTableListener( listener1 );
        table_.addTableListener( listener2 );

        table_.addCardPile( createUniqueCardPile() );

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile removed event catches any exception thrown by the
     * {@code cardPileRemoved} method of a table listener.
     */
    @Test
    public void testCardPileRemoved_CatchesListenerException()
    {
        final ICardPile cardPile = createUniqueCardPile();
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
     * Ensures the {@code createCard} method does not return {@code null}.
     */
    @Test
    public void testCreateCard_ReturnValue_NonNull()
    {
        assertNotNull( table_.createCard() );
    }

    /**
     * Ensures the {@code createCardPile} method does not return {@code null}.
     */
    @Test
    public void testCreateCardPile_ReturnValue_NonNull()
    {
        assertNotNull( table_.createCardPile() );
    }

    /**
     * Ensures the {@code getCardPile(int)} method throws an exception when
     * passed an illegal index greater than the maximum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardPileFromIndex_Index_Illegal_GreaterThanMaximumLegalValue()
    {
        table_.getCardPile( 0 );
    }

    /**
     * Ensures the {@code getCardPile(int)} method throws an exception when
     * passed an illegal index less than the minimum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardPileFromIndex_Index_Illegal_LessThanMinimumLegalValue()
    {
        table_.getCardPile( -1 );
    }

    /**
     * Ensures the {@code getCardPile(int)} method returns the correct card pile
     * when passed a legal index.
     */
    @Test
    public void testGetCardPileFromIndex_Index_Legal()
    {
        final ICardPile expectedValue = createUniqueCardPile();
        table_.addCardPile( expectedValue );

        final ICardPile actualValue = table_.getCardPile( 0 );

        assertSame( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getCardPile(Point)} method returns {@code null} when a
     * card pile is absent at the specified location.
     */
    @Test
    public void testGetCardPileFromLocation_Location_CardPileAbsent()
    {
        assertNull( table_.getCardPile( new Point( 0, 0 ) ) );
    }

    /**
     * Ensures the {@code getCardPile(Point)} method returns the most recently
     * added card pile when multiple card piles are present at the specified
     * location.
     */
    @Test
    public void testGetCardPileFromLocation_Location_MultipleCardPilesPresent()
    {
        final Point location = new Point( 7, 42 );
        final ICardPile initialCardPile = createUniqueCardPile();
        initialCardPile.setLocation( location );
        table_.addCardPile( initialCardPile );
        final ICardPile expectedCardPile = createUniqueCardPile();
        expectedCardPile.setLocation( location );
        table_.addCardPile( expectedCardPile );

        final ICardPile actualCardPile = table_.getCardPile( location );

        assertSame( expectedCardPile, actualCardPile );
    }

    /**
     * Ensures the {@code getCardPile(Point)} method returns the appropriate
     * card pile when a single card pile is present at the specified location.
     */
    @Test
    public void testGetCardPileFromLocation_Location_SingleCardPilePresent()
    {
        final Point location = new Point( 7, 42 );
        final ICardPile expectedCardPile = createUniqueCardPile();
        expectedCardPile.setLocation( location );
        table_.addCardPile( expectedCardPile );

        final ICardPile actualCardPile = table_.getCardPile( location );

        assertSame( expectedCardPile, actualCardPile );
    }

    /**
     * Ensures the {@code getCardPile(Point)} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileFromLocation_Location_Null()
    {
        table_.getCardPile( null );
    }

    /**
     * Ensures the {@code getCardPileCount} method returns the correct value.
     */
    @Test
    public void testGetCardPileCount()
    {
        table_.addCardPile( createUniqueCardPile() );
        table_.addCardPile( createUniqueCardPile() );
        table_.addCardPile( createUniqueCardPile() );

        final int actualValue = table_.getCardPileCount();

        assertEquals( 3, actualValue );
    }

    /**
     * Ensures the {@code getCardPileIndex} method throws an exception when
     * passed a card pile that is absent from the card pile collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardPileIndex_CardPile_Absent()
    {
        table_.getCardPileIndex( createUniqueCardPile() );
    }

    /**
     * Ensures the {@code getCardPileIndex} method throws an exception when
     * passed a {@code null} card pile.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileIndex_CardPile_Null()
    {
        table_.getCardPileIndex( null );
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

        final int actualValue = table_.getCardPileIndex( cardPile );

        assertEquals( 1, actualValue );
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

        table_.addCardPile( createUniqueCardPile() );

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
     * Ensures the {@code getTableEnvironment} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTableEnvironment_ReturnValue_NonNull()
    {
        assertNotNull( table_.getTableEnvironment() );
    }

    /**
     * Ensures the {@code removeCardPile} method throws an exception when passed
     * an illegal card pile that is not contained by the table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardPile_CardPile_Illegal_NotOwnedByTable()
    {
        table_.removeCardPile( createUniqueCardPile() );
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
     * Ensures the {@code removeCardPile} method fires a card pile removed
     * event.
     */
    @Test
    public void testRemoveCardPile_FiresCardPileRemovedEvent()
    {
        final ICardPile cardPile = createUniqueCardPile();
        table_.addCardPile( cardPile );
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        final Capture<TableContentChangedEvent> eventCapture = new Capture<TableContentChangedEvent>();
        listener.cardPileRemoved( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.removeCardPile( cardPile );

        mocksControl_.verify();
        assertSame( table_, eventCapture.getValue().getTable() );
        assertSame( cardPile, eventCapture.getValue().getCardPile() );
        assertEquals( 0, eventCapture.getValue().getCardPileIndex() );
    }

    /**
     * Ensures the {@code removeCardPile} method removes a card pile.
     */
    @Test
    public void testRemoveCardPile_RemovesCardPile()
    {
        final ICardPile cardPile = createUniqueCardPile();
        table_.addCardPile( cardPile );

        table_.removeCardPile( cardPile );

        final List<ICardPile> cardPiles = table_.getCardPiles();
        assertFalse( cardPiles.contains( cardPile ) );
        assertEquals( 0, cardPiles.size() );
        assertNull( cardPile.getTable() );
    }

    /**
     * Ensures the {@code removeCardPiles} method does not fire a card pile
     * removed event when the table is empty.
     */
    @Test
    public void testRemoveCardPiles_Empty_DoesNotFireCardPileRemovedEvent()
    {
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.removeCardPiles();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCardPiles} method fires a card pile removed
     * event for each card pile present on the table.
     */
    @Test
    public void testRemoveCardPiles_NotEmpty_FiresCardPileRemovedEvent()
    {
        final ICardPile cardPile1 = createUniqueCardPile();
        final ICardPile cardPile2 = createUniqueCardPile();
        table_.addCardPile( cardPile1 );
        table_.addCardPile( cardPile2 );
        final ITableListener listener = mocksControl_.createMock( ITableListener.class );
        final Capture<TableContentChangedEvent> eventCapture1 = new Capture<TableContentChangedEvent>();
        listener.cardPileRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<TableContentChangedEvent> eventCapture2 = new Capture<TableContentChangedEvent>();
        listener.cardPileRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        table_.addTableListener( listener );

        table_.removeCardPiles();

        mocksControl_.verify();
        assertSame( table_, eventCapture1.getValue().getTable() );
        assertSame( cardPile2, eventCapture1.getValue().getCardPile() );
        assertEquals( 1, eventCapture1.getValue().getCardPileIndex() );
        assertSame( table_, eventCapture2.getValue().getTable() );
        assertSame( cardPile1, eventCapture2.getValue().getCardPile() );
        assertEquals( 0, eventCapture2.getValue().getCardPileIndex() );
    }

    /**
     * Ensures the {@code removeCardPiles} method removes all card piles
     * contained in the table.
     */
    @Test
    public void testRemoveCardPiles_NotEmpty_RemovesCardPiles()
    {
        table_.addCardPile( createUniqueCardPile() );
        table_.addCardPile( createUniqueCardPile() );

        final List<ICardPile> actualCardPiles = table_.removeCardPiles();

        assertEquals( 0, table_.getCardPileCount() );
        for( final ICardPile cardPile : actualCardPiles )
        {
            assertNull( cardPile.getTable() );
        }
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
        table_.addCardPile( createUniqueCardPile() );

        table_.removeTableListener( listener );
        table_.addCardPile( createUniqueCardPile() );

        mocksControl_.verify();
    }
}
