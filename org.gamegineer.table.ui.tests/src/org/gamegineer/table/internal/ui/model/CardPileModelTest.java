/*
 * CardPileModelTest.java
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
 * Created on Jan 26, 2010 at 10:57:19 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.CardPileFactory;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICard;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.CardPileModel} class.
 */
public final class CardPileModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile model under test in the fixture. */
    private CardPileModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileModelTest} class.
     */
    public CardPileModelTest()
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
        return CardFactory.createCard( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
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
        model_ = new CardPileModel( CardPileFactory.createCardPile( CardPileBaseDesigns.createUniqueCardPileBaseDesign() ) );
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
     * Ensures the {@code addCardPileModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardPileModelListener_Listener_Null()
    {
        model_.addCardPileModelListener( null );
    }

    /**
     * Ensures the {@code addCardPileModelListener} method throws an exception
     * when passed a listener that is present in the card pile model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardPileModelListener_Listener_Present()
    {
        final ICardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );

        model_.addCardPileModelListener( listener );
    }

    /**
     * Ensures a change to a card model owned by the card pile model fires a
     * card pile model state changed event.
     */
    @Ignore( "currently no mutating methods on CardModel" )
    @Test
    public void testCardModel_StateChanged_FiresCardPileModelStateChangedEvent()
    {
        final ICard card = createCard();
        model_.getCardPile().addCard( card );
        final MockCardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );

        // NB: change card model state here when applicable

        assertEquals( 1, listener.getCardPileModelStateChangedEventCount() );
    }

    /**
     * Ensures a change to the underlying card pile state fires a card pile
     * model state changed event.
     */
    @Test
    public void testCardPile_StateChanged_FiresCardPileModelStateChangedEvent()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );

        model_.getCardPile().setLocation( new Point( 101, 102 ) );

        assertEquals( 1, listener.getCardPileModelStateChangedEventCount() );
    }

    /**
     * Ensures the card pile focus gained event catches any exception thrown by
     * the {@code cardPileFocusGained} method of a card pile model listener.
     */
    @Test
    public void testCardPileFocusGained_CatchesListenerException()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener()
        {
            @Override
            public void cardPileFocusGained(
                final CardPileModelEvent event )
            {
                super.cardPileFocusGained( event );

                throw new RuntimeException();
            }
        };
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );
    }

    /**
     * Ensures the card pile focus lost event catches any exception thrown by
     * the {@code cardPileFocusLost} method of a card pile model listener.
     */
    @Test
    public void testCardPileFocusLost_CatchesListenerException()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener()
        {
            @Override
            public void cardPileFocusLost(
                final CardPileModelEvent event )
            {
                super.cardPileFocusLost( event );

                throw new RuntimeException();
            }
        };
        model_.addCardPileModelListener( listener );

        model_.setFocused( false );
    }

    /**
     * Ensures the card pile model state changed event catches any exception
     * thrown by the {@code cardPileModelStateChanged} method of a card pile
     * model listener.
     */
    @Test
    public void testCardPileModelStateChanged_CatchesListenerException()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener()
        {
            @Override
            public void cardPileModelStateChanged(
                final CardPileModelEvent event )
            {
                super.cardPileModelStateChanged( event );

                throw new RuntimeException();
            }
        };
        model_.addCardPileModelListener( listener );

        model_.getCardPile().setLocation( new Point( 101, 102 ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card pile.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_CardPile_Null()
    {
        new CardPileModel( null );
    }

    /**
     * Ensures the {@code getCardModel} throws an exception when passed a card
     * that is absent from the card pile.
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
     * Ensures the {@code getCardPile} method does not return {@code null}.
     */
    @Test
    public void testGetCardPile_ReturnValue_NonNull()
    {
        assertNotNull( model_.getCardPile() );
    }

    /**
     * Ensures the {@code removeCardPileModelListener} method throws an
     * exception when passed a listener that is absent from the card pile model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardPileModelListener_Listener_Absent()
    {
        model_.removeCardPileModelListener( new MockCardPileModelListener() );
    }

    /**
     * Ensures the {@code removeCardPileModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardPileModelListener_Listener_Null()
    {
        model_.removeCardPileModelListener( null );
    }

    /**
     * Ensures the {@code removeCardPileModelListener} removes a listener that
     * is present in the card pile model listener collection.
     */
    @Test
    public void testRemoveCardPileModelListener_Listener_Present()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );
        model_.setFocused( true );

        model_.removeCardPileModelListener( listener );

        model_.setFocused( false );
        assertEquals( 1, listener.getCardPileFocusGainedEventCount() );
        assertEquals( 0, listener.getCardPileFocusLostEventCount() );
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile model state
     * changed event.
     */
    @Test
    public void testSetFocused_FiresCardPileModelStateChangedEvent()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );

        assertEquals( 1, listener.getCardPileModelStateChangedEventCount() );
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile focus gained
     * event.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresCardPileFocusGainedEvent()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );

        assertEquals( 1, listener.getCardPileFocusGainedEventCount() );
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile focus lost event.
     */
    @Test
    public void testSetFocused_LostFocus_FiresCardPileFocusLostEvent()
    {
        final MockCardPileModelListener listener = new MockCardPileModelListener();
        model_.addCardPileModelListener( listener );

        model_.setFocused( false );

        assertEquals( 1, listener.getCardPileFocusLostEventCount() );
    }
}
