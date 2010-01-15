/*
 * AbstractCardPileTestCase.java
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
 * Created on Jan 14, 2010 at 10:46:47 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPile} interface.
 */
public abstract class AbstractCardPileTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile under test in the fixture. */
    private ICardPile cardPile_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCardPileTestCase} class.
     */
    protected AbstractCardPileTestCase()
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
     * Creates the card pile to be tested.
     * 
     * @return The card pile to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPile createCardPile()
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
        cardPile_ = createCardPile();
        assertNotNull( cardPile_ );
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
        cardPile_ = null;
    }

    /**
     * Ensures the {@code addCard} method adds a card that is absent from the
     * card pile.
     */
    @Test
    public void testAddCard_Card_Absent_AddsCard()
    {
        final ICard card = createCard();

        cardPile_.addCard( card );

        assertSame( card, cardPile_.getCard() );
    }

    /**
     * Ensures the {@code addCard} method catches any exception thrown by the
     * {@code cardAdded} method of a card pile listener.
     */
    @Test
    public void testAddCard_Card_Absent_CatchesListenerException()
    {
        final MockCardPileListener listener1 = new MockCardPileListener()
        {
            @Override
            public void cardAdded(
                final CardPileContentChangedEvent event )
            {
                super.cardAdded( event );

                throw new RuntimeException();
            }
        };
        final MockCardPileListener listener2 = new MockCardPileListener();
        cardPile_.addCardPileListener( listener1 );
        cardPile_.addCardPileListener( listener2 );

        cardPile_.addCard( createCard() );

        assertEquals( 1, listener2.getCardAddedEventCount() );
    }

    /**
     * Ensures the {@code addCard} method fires a card added event when the card
     * is absent from the card pile.
     */
    @Test
    public void testAddCard_Card_Absent_FiresCardAddedEvent()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCard( createCard() );

        assertEquals( 1, listener.getCardAddedEventCount() );
    }

    /**
     * Ensures the {@code addCard} method throws an exception when passed a
     * {@code null} card.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCard_Card_Null()
    {
        cardPile_.addCard( null );
    }

    /**
     * Ensures the {@code addCard} method does not add a card that is present in
     * the card pile.
     */
    @Test
    public void testAddCard_Card_Present()
    {
        // TODO
    }

    /**
     * Ensures the {@code addCardPileListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardPileListener_Listener_Null()
    {
        cardPile_.addCardPileListener( null );
    }

    /**
     * Ensures the {@code addCardPileListener} method throws an exception when
     * passed a listener that is present in the card pile listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardPileListener_Listener_Present()
    {
        final ICardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCardPileListener( listener );
    }

    /**
     * Ensures the {@code getCard} method returns {@code null} when the card
     * pile is empty.
     */
    @Test
    public void testGetCard_Empty()
    {
        assertNull( cardPile_.getCard() );
    }

    /**
     * Ensures the {@code getCard} method returns the card at the top of the
     * card pile when the card pile is not empty.
     */
    @Test
    public void testGetCard_NotEmpty()
    {
        final ICard card = createCard();
        cardPile_.addCard( card );

        assertSame( card, cardPile_.getCard() );
    }

    /**
     * Ensures the {@code isEmpty} method returns {@code true} if the card pile
     * is empty.
     */
    @Test
    public void testIsEmpty_Empty()
    {
        assertTrue( cardPile_.isEmpty() );
    }

    /**
     * Ensures the {@code isEmpty} method returns {@code false} if the card pile
     * is not empty.
     */
    @Test
    public void testIsEmpty_NotEmpty()
    {
        cardPile_.addCard( createCard() );

        assertFalse( cardPile_.isEmpty() );
    }

    /**
     * Ensures the {@code removeCard} method returns {@code null} when the card
     * pile is empty.
     */
    @Test
    public void testRemoveCard_Empty()
    {
        final ICard card = cardPile_.removeCard();

        assertNull( card );
    }

    /**
     * Ensures the {@code removeCard} method catches any exception thrown by the
     * {@code cardRemoved} method of a card pile listener when the card pile is
     * not empty.
     */
    @Test
    public void testRemoveCard_NotEmpty_CatchesListenerException()
    {
        final MockCardPileListener listener1 = new MockCardPileListener()
        {
            @Override
            public void cardRemoved(
                final CardPileContentChangedEvent event )
            {
                super.cardRemoved( event );

                throw new RuntimeException();
            }
        };
        final MockCardPileListener listener2 = new MockCardPileListener();
        cardPile_.addCardPileListener( listener1 );
        cardPile_.addCardPileListener( listener2 );
        cardPile_.addCard( createCard() );

        cardPile_.removeCard();

        assertEquals( 1, listener2.getCardRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCard} method fires a card removed event when the
     * card pile is not empty.
     */
    @Test
    public void testRemoveCard_NotEmpty_FiresCardRemovedEvent()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );
        cardPile_.addCard( createCard() );

        cardPile_.removeCard();

        assertEquals( 1, listener.getCardRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCard} method removes the card at the top of the
     * card pile when the card pile is not empty.
     */
    @Test
    public void testRemoveCard_NotEmpty_RemovesCard()
    {
        final ICard expectedCard = createCard();
        cardPile_.addCard( expectedCard );

        final ICard actualCard = cardPile_.removeCard();

        assertSame( expectedCard, actualCard );
        assertTrue( cardPile_.isEmpty() );
    }

    /**
     * Ensures the {@code removeCardPileListener} method throws an exception
     * when passed a listener that is absent from the card pile listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardPileListener_Listener_Absent()
    {
        cardPile_.removeCardPileListener( new MockCardPileListener() );
    }

    /**
     * Ensures the {@code removeCardPileListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardPileListener_Listener_Null()
    {
        cardPile_.removeCardPileListener( null );
    }

    /**
     * Ensures the {@code removeCardPileListener} removes a listener that is
     * present in the card pile listener collection.
     */
    @Test
    public void testRemoveCardPileListener_Listener_Present()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );
        cardPile_.addCard( createCard() );

        cardPile_.removeCardPileListener( listener );

        cardPile_.addCard( createCard() );
        assertEquals( 1, listener.getCardAddedEventCount() );
    }
}
