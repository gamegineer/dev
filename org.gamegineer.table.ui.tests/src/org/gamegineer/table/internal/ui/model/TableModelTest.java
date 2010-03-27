/*
 * TableModelTest.java
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
 * Created on Dec 28, 2009 at 8:25:04 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.awt.Dimension;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.CardPileFactory;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.TableFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.TableModel} class.
 */
public final class TableModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table model under test in the fixture. */
    private TableModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelTest} class.
     */
    public TableModelTest()
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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        model_ = new TableModel( TableFactory.createTable() );
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
        model_ = null;
    }

    /**
     * Ensures the {@code addTableModelListener} method throws an exception when
     * passed a listener that is absent from the table model listener collection
     * but another listener is present.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddTableModelListener_Listener_Absent_OtherListenerPresent()
    {
        model_.addTableModelListener( new MockTableModelListener() );

        model_.addTableModelListener( new MockTableModelListener() );
    }

    /**
     * Ensures the {@code addTableModelListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableModelListener_Listener_Null()
    {
        model_.addTableModelListener( null );
    }

    /**
     * Ensures the {@code addTableModelListener} method throws an exception when
     * passed a listener that is present in the table model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableModelListener_Listener_Present()
    {
        final ITableModelListener listener = new MockTableModelListener();
        model_.addTableModelListener( listener );

        model_.addTableModelListener( listener );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Table_Null()
    {
        new TableModel( null );
    }

    /**
     * Ensures the {@code getCardPileModel} throws an exception when passed a
     * card pile that is absent from the table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardPileModel_CardPile_Absent()
    {
        model_.getCardPileModel( createDummy( ICardPile.class ) );
    }

    /**
     * Ensures the {@code getCardPileModel} throws an exception when passed a
     * {@code null} card pile.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileModel_CardPile_Null()
    {
        model_.getCardPileModel( null );
    }

    /**
     * Ensures the {@code getOriginOffset} method returns a copy of the origin
     * offset.
     */
    @Test
    public void testGetOriginOffset_ReturnValue_Copy()
    {
        final Dimension originOffset = model_.getOriginOffset();
        final Dimension expectedOriginOffset = new Dimension( originOffset );
        originOffset.setSize( expectedOriginOffset.width + 100, expectedOriginOffset.height + 200 );

        final Dimension actualOriginOffset = model_.getOriginOffset();

        assertEquals( expectedOriginOffset, actualOriginOffset );
    }

    /**
     * Ensures the {@code getOriginOffset} method does not return {@code null}.
     */
    @Test
    public void testGetOriginOffset_ReturnValue_NonNull()
    {
        assertNotNull( model_.getOriginOffset() );
    }

    /**
     * Ensures the {@code getTable} method does not return {@code null}.
     */
    @Test
    public void testGetTable_ReturnValue_NonNull()
    {
        assertNotNull( model_.getTable() );
    }

    /**
     * Ensures the {@code removeTableModelListener} method throws an exception
     * when passed a listener that is absent from the table model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableModelListener_Listener_Absent()
    {
        model_.removeTableModelListener( new MockTableModelListener() );
    }

    /**
     * Ensures the {@code removeTableModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableModelListener_Listener_Null()
    {
        model_.removeTableModelListener( null );
    }

    /**
     * Ensures the {@code removeTableModelListener} removes a listener that is
     * present in the table model listener collection.
     */
    @Test
    public void testRemoveTableModelListener_Listener_Present()
    {
        final ICardPile cardPile = createCardPile();
        model_.getTable().addCardPile( cardPile );
        final MockTableModelListener listener = new MockTableModelListener();
        model_.addTableModelListener( listener );
        model_.setFocus( cardPile );

        model_.removeTableModelListener( listener );

        model_.setFocus( null );
        assertEquals( 1, listener.getCardPileFocusChangedEventCount() );
    }

    /**
     * Ensures the {@code setFocus} method correctly changes the focus.
     */
    @Test
    public void testSetFocus()
    {
        final ICardPile cardPile1 = createCardPile();
        model_.getTable().addCardPile( cardPile1 );
        final ICardPile cardPile2 = createCardPile();
        model_.getTable().addCardPile( cardPile2 );

        model_.setFocus( cardPile1 );
        assertTrue( model_.getCardPileModel( cardPile1 ).isFocused() );
        assertFalse( model_.getCardPileModel( cardPile2 ).isFocused() );
        model_.setFocus( cardPile2 );
        assertFalse( model_.getCardPileModel( cardPile1 ).isFocused() );
        assertTrue( model_.getCardPileModel( cardPile2 ).isFocused() );
    }

    /**
     * Ensures the {@code setFocus} method does not throw an exception when
     * passed a {@code null} card pile.
     */
    @Test
    public void testSetFocus_CardPile_Null()
    {
        model_.setFocus( null );
    }

    /**
     * Ensures the {@code setFocus} method catches any exception thrown by the
     * {@code cardPileFocusChanged} method of a table model listener.
     */
    @Test
    public void testSetFocus_CatchesListenerException()
    {
        final ICardPile cardPile = createCardPile();
        model_.getTable().addCardPile( cardPile );
        final MockTableModelListener listener = new MockTableModelListener()
        {
            @Override
            public void cardPileFocusChanged(
                final TableModelEvent event )
            {
                super.cardPileFocusChanged( event );

                throw new RuntimeException();
            }
        };
        model_.addTableModelListener( listener );

        model_.setFocus( cardPile );
    }

    /**
     * Ensures the {@code setFocus} method fires a card pile focus changed
     * event.
     */
    @Test
    public void testSetFocus_FiresCardPileFocusChangedEvent()
    {
        final ICardPile cardPile = createCardPile();
        model_.getTable().addCardPile( cardPile );
        final MockTableModelListener listener = new MockTableModelListener();
        model_.addTableModelListener( listener );

        model_.setFocus( cardPile );

        assertEquals( 1, listener.getCardPileFocusChangedEventCount() );
    }

    /**
     * Ensures the {@code setOriginOffset} method catches any exception thrown
     * by the {@code originOffsetChanged} method of a table model listener.
     */
    @Test
    public void testSetOriginOffset_CatchesListenerException()
    {
        final MockTableModelListener listener = new MockTableModelListener()
        {
            @Override
            public void originOffsetChanged(
                final TableModelEvent event )
            {
                super.originOffsetChanged( event );

                throw new RuntimeException();
            }
        };
        model_.addTableModelListener( listener );

        model_.setOriginOffset( new Dimension( 100, 200 ) );
    }

    /**
     * Ensures the {@code setOriginOffset} method fires an origin offset changed
     * event.
     */
    @Test
    public void testSetOriginOffset_FiresOriginOffsetChangedEvent()
    {
        final MockTableModelListener listener = new MockTableModelListener();
        model_.addTableModelListener( listener );

        model_.setOriginOffset( new Dimension( 100, 200 ) );

        assertEquals( 1, listener.getOriginOffsetChangedEventCount() );
    }

    /**
     * Ensures the {@code setOriginOffset} method throws an exception when
     * passed a {@code null} origin offset.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOriginOffset_OriginOffset_Null()
    {
        model_.setOriginOffset( null );
    }
}
