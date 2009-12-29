/*
 * TableModelTest.java
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
 * Created on Dec 28, 2009 at 8:25:04 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.gamegineer.table.core.CardDesigns;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
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
     * Ensures the {@code getCardModel} throws an exception when passed a card
     * that is absent from the table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardModel_Card_Absent()
    {
        model_.getCardModel( createDummy( ICard.class ) );
    }

    /**
     * Ensures the {@code getCardModel} throws an exception when passed a
     * {@code null} card.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardModel_Card_Null()
    {
        model_.getCardModel( null );
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
        final ICard card = createCard();
        model_.getTable().addCard( card );
        final MockTableModelListener listener = new MockTableModelListener();
        model_.addTableModelListener( listener );
        model_.setFocus( card );

        model_.removeTableModelListener( listener );

        model_.setFocus( null );
        assertEquals( 1, listener.getCardFocusChangedEventCount() );
    }

    /**
     * Ensures the {@code setFocus} method correctly changes the focus.
     */
    @Test
    public void testSetFocus()
    {
        final ICard card1 = createCard();
        model_.getTable().addCard( card1 );
        final ICard card2 = createCard();
        model_.getTable().addCard( card2 );

        model_.setFocus( card1 );
        assertTrue( model_.getCardModel( card1 ).isFocused() );
        assertFalse( model_.getCardModel( card2 ).isFocused() );
        model_.setFocus( card2 );
        assertFalse( model_.getCardModel( card1 ).isFocused() );
        assertTrue( model_.getCardModel( card2 ).isFocused() );
    }

    /**
     * Ensures the {@code setFocus} method does not throw an exception when
     * passed a {@code null} card.
     */
    @Test
    public void testSetFocus_Card_Null()
    {
        model_.setFocus( null );
    }

    /**
     * Ensures the {@code setFocus} method catches any exception thrown by the
     * {@code cardFocusChanged} method of a table model listener.
     */
    @Test
    public void testSetFocus_CatchesListenerException()
    {
        final ICard card = createCard();
        model_.getTable().addCard( card );
        final MockTableModelListener listener = new MockTableModelListener()
        {
            @Override
            public void cardFocusChanged(
                final TableModelEvent event )
            {
                super.cardFocusChanged( event );

                throw new RuntimeException();
            }
        };
        model_.addTableModelListener( listener );

        model_.setFocus( card );
    }

    /**
     * Ensures the {@code setFocus} method fires a card focus changed event.
     */
    @Test
    public void testSetFocus_FiresCardFocusChangedEvent()
    {
        final ICard card = createCard();
        model_.getTable().addCard( card );
        final MockTableModelListener listener = new MockTableModelListener();
        model_.addTableModelListener( listener );

        model_.setFocus( card );

        assertEquals( 1, listener.getCardFocusChangedEventCount() );
    }
}
