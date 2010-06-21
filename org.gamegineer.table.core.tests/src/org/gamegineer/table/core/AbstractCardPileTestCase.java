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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.persistence.memento.IMemento;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
     * Creates a new card pile from the specified memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     */
    /* @NonNull */
    protected abstract ICardPile createCardPile(
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
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code addCard} method adds a card that is absent from the
     * card pile.
     */
    @Test
    public void testAddCard_Card_Absent_AddsCard()
    {
        final ICard card = Cards.createUniqueCard();

        cardPile_.addCard( card );

        final List<ICard> cards = cardPile_.getCards();
        assertSame( card, cards.get( cards.size() - 1 ) );
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

        cardPile_.addCard( Cards.createUniqueCard() );

        assertEquals( 1, listener2.getCardAddedEventCount() );
    }

    /**
     * Ensures the {@code addCard} method changes the location of the card to
     * reflect the card pile location when the card is absent from the card
     * pile.
     */
    @Test
    public void testAddCard_Card_Absent_ChangesCardLocation()
    {
        final ICard card = Cards.createUniqueCard();
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        cardPile_.addCard( card );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCard} method changes the card pile bounds when the
     * card is absent from the card pile.
     */
    @Test( timeout = 1000 )
    public void testAddCard_Card_Absent_ChangesCardPileBounds()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );
        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( listener.getCardPileBoundsChangedEventCount() == 0 );

        assertEquals( 1, listener.getCardPileBoundsChangedEventCount() );
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

        cardPile_.addCard( Cards.createUniqueCard() );

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
    public void testAddCard_Card_Present_DoesNotAddCard()
    {
        final ICard card = Cards.createUniqueCard();
        cardPile_.addCard( card );

        cardPile_.addCard( card );

        final List<ICard> cards = cardPile_.getCards();
        assertSame( card, cards.get( cards.size() - 1 ) );
        assertEquals( 1, cards.size() );
    }

    /**
     * Ensures the {@code addCard} method does not fire a card added event when
     * the card is present in the card pile.
     */
    @Test
    public void testAddCard_Card_Present_DoesNotFireCardAddedEvent()
    {
        final ICard card = Cards.createUniqueCard();
        cardPile_.addCard( card );
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCard( card );

        assertEquals( 0, listener.getCardAddedEventCount() );
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
     * Ensures the {@code getBaseDesign} method does not return {@code null}.
     */
    @Test
    public void testGetBaseDesign_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getBaseDesign() );
    }

    /**
     * Ensures the {@code getBounds} method returns a copy of the bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = cardPile_.getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, cardPile_.getBounds() );

    }

    /**
     * Ensures the {@code getBounds} method does not return {@code null}.
     */
    @Test
    public void testGetBounds_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetBounds_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Rectangle expectedBounds = cardPile_.getBounds();
        expectedBounds.setLocation( expectedLocation );
        cardPile_.setLocation( expectedLocation );

        final Rectangle actualBounds = cardPile_.getBounds();

        assertEquals( expectedBounds, actualBounds );
    }

    /**
     * Ensures the {@code getCards} method returns a copy of the card
     * collection.
     */
    @Test
    public void testGetCards_ReturnValue_Copy()
    {
        final List<ICard> cards = cardPile_.getCards();
        final int expectedCardsSize = cards.size();

        cardPile_.addCard( Cards.createUniqueCard() );

        assertEquals( expectedCardsSize, cards.size() );
    }

    /**
     * Ensures the {@code getCards} method does not return {@code null}.
     */
    @Test
    public void testGetCards_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getCards() );
    }

    /**
     * Ensures the {@code getLayout} method does not return {@code null}.
     */
    @Test
    public void testGetLayout_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getLayout() );
    }

    /**
     * Ensures the {@code getLocation} method returns a copy of the location.
     */
    @Test
    public void testGetLocation_ReturnValue_Copy()
    {
        final Point location = cardPile_.getLocation();
        final Point expectedLocation = new Point( location );

        location.setLocation( 1010, 2020 );

        assertEquals( expectedLocation, cardPile_.getLocation() );
    }

    /**
     * Ensures the {@code getLocation} method does not return {@code null}.
     */
    @Test
    public void testGetLocation_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getLocation() );
    }

    /**
     * Ensures the {@code getLocation} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetLocation_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        cardPile_.setLocation( expectedLocation );

        final Point actualLocation = cardPile_.getLocation();

        assertEquals( expectedLocation, actualLocation );
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
        cardPile_.addCard( Cards.createUniqueCard() );
        final IMemento expectedMemento = cardPile_.getMemento();

        final ICardPile actualCardPile = createCardPile( expectedMemento );
        final IMemento actualMemento = actualCardPile.getMemento();

        assertEquals( expectedMemento, actualMemento );
    }

    /**
     * Ensures the {@code getMemento} method does not return {@code null}.
     */
    @Test
    public void testGetMemento_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getMemento() );
    }

    /**
     * Ensures the {@code getSize} method returns a copy of the size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final Dimension size = cardPile_.getSize();
        final Dimension expectedSize = new Dimension( size );

        size.setSize( 101, 202 );

        assertEquals( expectedSize, cardPile_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method does not return {@code null}.
     */
    @Test
    public void testGetSize_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetSize_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Dimension expectedSize = cardPile_.getSize();
        cardPile_.setLocation( expectedLocation );

        final Dimension actualSize = cardPile_.getSize();

        assertEquals( expectedSize, actualSize );
    }

    /**
     * Ensures the {@code removeCard} method does not fire a card removed event
     * when the card pile is empty.
     */
    @Test
    public void testRemoveCard_Empty_DoesNotFireCardRemovedEvent()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCard();

        assertEquals( 0, listener.getCardRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCard} method returns {@code null} when the card
     * pile is empty.
     */
    @Test
    public void testRemoveCard_Empty_DoesNotRemoveCard()
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
        cardPile_.addCard( Cards.createUniqueCard() );

        cardPile_.removeCard();

        assertEquals( 1, listener2.getCardRemovedEventCount() );
    }

    /**
     * Ensures the {@code removeCard} method changes the card pile bounds when
     * the card pile is not empty.
     */
    @Test( timeout = 1000 )
    public void testRemoveCard_NotEmpty_ChangesCardPileBounds()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );
        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( listener.getCardPileBoundsChangedEventCount() == 0 );

        cardPile_.removeCard();

        assertEquals( 2, listener.getCardPileBoundsChangedEventCount() );
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
        cardPile_.addCard( Cards.createUniqueCard() );

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
        final ICard expectedCard = Cards.createUniqueCard();
        cardPile_.addCard( expectedCard );

        final ICard actualCard = cardPile_.removeCard();

        assertSame( expectedCard, actualCard );
        assertEquals( 0, cardPile_.getCards().size() );
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
        cardPile_.addCard( Cards.createUniqueCard() );

        cardPile_.removeCardPileListener( listener );

        cardPile_.addCard( Cards.createUniqueCard() );
        assertEquals( 1, listener.getCardAddedEventCount() );
    }

    /**
     * Ensures the {@code setLayout} method changes the card pile bounds when
     * appropriate.
     */
    @Test
    public void testSetLayout_ChangesCardPileBounds()
    {
        cardPile_.setLayout( CardPileLayout.STACKED );
        cardPile_.addCard( Cards.createUniqueCard() );
        cardPile_.addCard( Cards.createUniqueCard() );
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );

        cardPile_.setLayout( CardPileLayout.ACCORDIAN_DOWN );

        assertEquals( 1, listener.getCardPileBoundsChangedEventCount() );
    }

    /**
     * Ensures the {@code setLayout} method throws an exception when passed a
     * {@code null} layout.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLayout_Layout_Null()
    {
        cardPile_.setLayout( null );
    }

    /**
     * Ensures the {@code setLocation} method catches any exception thrown by
     * the {@code cardPileBoundsChanged} method of a card pile listener.
     */
    @Test
    public void testSetLocation_CatchesListenerException()
    {
        final MockCardPileListener listener1 = new MockCardPileListener()
        {
            @Override
            public void cardPileBoundsChanged(
                final CardPileEvent event )
            {
                super.cardPileBoundsChanged( event );

                throw new RuntimeException();
            }
        };
        final MockCardPileListener listener2 = new MockCardPileListener();
        cardPile_.addCardPileListener( listener1 );
        cardPile_.addCardPileListener( listener2 );

        cardPile_.setLocation( new Point( 1010, 2020 ) );

        assertEquals( 1, listener2.getCardPileBoundsChangedEventCount() );
    }

    /**
     * Ensures the {@code setLocation} method changes the location of all child
     * cards to reflect the new card pile location.
     */
    @Test
    public void testSetLocation_ChangesChildCardLocation()
    {
        final ICard card = Cards.createUniqueCard();
        cardPile_.addCard( card );
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        cardPile_.setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLocation} method fires a card pile bounds changed
     * event.
     */
    @Test
    public void testSetLocation_FiresCardPileBoundsChangedEvent()
    {
        final MockCardPileListener listener = new MockCardPileListener();
        cardPile_.addCardPileListener( listener );

        cardPile_.setLocation( new Point( 1010, 2020 ) );

        assertEquals( 1, listener.getCardPileBoundsChangedEventCount() );
    }

    /**
     * Ensures the {@code setLocation} method makes a copy of the location.
     */
    @Test
    public void testSetLocation_Location_Copy()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Point location = new Point( expectedLocation );

        cardPile_.setLocation( location );
        location.setLocation( 1, 2 );

        assertEquals( expectedLocation, cardPile_.getLocation() );
    }

    /**
     * Ensures the {@code setLocation} method throws an exception when passed a
     * {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLocation_Location_Null()
    {
        cardPile_.setLocation( null );
    }
}
