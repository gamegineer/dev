/*
 * CardModelTest.java
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
 * Created on Dec 25, 2009 at 11:02:59 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.core.CardDesigns;
import org.gamegineer.table.core.CardFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.CardModel} class.
 */
public final class CardModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card model under test in the fixture. */
    private CardModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardModelTest} class.
     */
    public CardModelTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        model_ = new CardModel( CardFactory.createCard( CardDesigns.createUniqueCardDesign(), CardDesigns.createUniqueCardDesign() ) );
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
     * Ensures the {@code addCardModelListener} method throws an exception when
     * passed a listener that is absent from the card model listener collection
     * but another listener is present.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddCardModelListener_Listener_Absent_OtherListenerPresent()
    {
        model_.addCardModelListener( new MockCardModelListener() );

        model_.addCardModelListener( new MockCardModelListener() );
    }

    /**
     * Ensures the {@code addCardModelListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardModelListener_Listener_Null()
    {
        model_.addCardModelListener( null );
    }

    /**
     * Ensures the {@code addCardModelListener} method throws an exception when
     * passed a listener that is present in the card model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardModelListener_Listener_Present()
    {
        final ICardModelListener listener = new MockCardModelListener();
        model_.addCardModelListener( listener );

        model_.addCardModelListener( listener );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Card_Null()
    {
        new CardModel( null );
    }

    /**
     * Ensures the {@code getCard} method does not return {@code null}.
     */
    @Test
    public void testGetCard_ReturnValue_NonNull()
    {
        assertNotNull( model_.getCard() );
    }

    /**
     * Ensures the {@code removeCardModelListener} method throws an exception
     * when passed a listener that is absent from the card model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardModelListener_Listener_Absent()
    {
        model_.removeCardModelListener( new MockCardModelListener() );
    }

    /**
     * Ensures the {@code removeCardModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardModelListener_Listener_Null()
    {
        model_.removeCardModelListener( null );
    }

    /**
     * Ensures the {@code removeCardModelListener} removes a listener that is
     * present in the card model listener collection.
     */
    @Test
    public void testRemoveCardModelListener_Listener_Present()
    {
        final MockCardModelListener listener = new MockCardModelListener();
        model_.addCardModelListener( listener );
        model_.setFocused( true );

        model_.removeCardModelListener( listener );

        model_.setFocused( false );
        assertEquals( 1, listener.getCardFocusChangedEventCount() );
    }

    /**
     * Ensures the {@code setFocused} method catches any exception thrown by the
     * {@code cardFocusChanged} method of a card model listener.
     */
    @Test
    public void testSetFocused_CatchesListenerException()
    {
        final MockCardModelListener listener = new MockCardModelListener()
        {
            @Override
            public void cardFocusChanged(
                final CardModelEvent event )
            {
                super.cardFocusChanged( event );

                throw new RuntimeException();
            }
        };
        model_.addCardModelListener( listener );

        model_.setFocused( true );
    }

    /**
     * Ensures the {@code setFocused} method fires a card focus changed event.
     */
    @Test
    public void testSetFocused_FiresCardFocusChangedEvent()
    {
        final MockCardModelListener listener = new MockCardModelListener();
        model_.addCardModelListener( listener );

        model_.setFocused( true );

        assertEquals( 1, listener.getCardFocusChangedEventCount() );
    }
}
