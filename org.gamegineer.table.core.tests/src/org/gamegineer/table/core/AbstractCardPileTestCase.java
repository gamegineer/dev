/*
 * AbstractCardPileTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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
import static org.junit.Assert.assertTrue;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );
        final Rectangle originalCardPileBounds = cardPile_.getBounds();

        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( originalCardPileBounds.equals( cardPile_.getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCard} method fires a card added event when the card
     * is absent from the card pile.
     */
    @Test
    public void testAddCard_Card_Absent_FiresCardAddedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCard( Cards.createUniqueCard() );

        mocksControl_.verify();
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCard( card );

        mocksControl_.verify();
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        cardPile_.addCardPileListener( listener );

        cardPile_.addCardPileListener( listener );
    }

    /**
     * Ensures the {@code addCards} method adds cards that are absent from the
     * card pile.
     */
    @Test
    public void testAddCards_Cards_Absent_AddsCards()
    {
        final ICard card1 = Cards.createUniqueCard();
        final ICard card2 = Cards.createUniqueCard();

        cardPile_.addCards( Arrays.asList( card1, card2 ) );

        final List<ICard> cards = cardPile_.getCards();
        assertSame( card1, cards.get( 0 ) );
        assertSame( card2, cards.get( 1 ) );
    }

    /**
     * Ensures the {@code addCards} method changes the location of the cards to
     * reflect the card pile location when the cards are absent from the card
     * pile.
     */
    @Test
    public void testAddCards_Cards_Absent_ChangesCardLocation()
    {
        final ICard card = Cards.createUniqueCard();
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        cardPile_.addCards( Collections.singletonList( card ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCards} method changes the card pile bounds when the
     * cards are absent from the card pile.
     */
    @Test( timeout = 1000 )
    public void testAddCards_Cards_Absent_ChangesCardPileBounds()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );
        final Rectangle originalCardPileBounds = cardPile_.getBounds();

        do
        {
            cardPile_.addCards( Collections.singletonList( Cards.createUniqueCard() ) );

        } while( originalCardPileBounds.equals( cardPile_.getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCards} method fires a card added event when the
     * cards are absent from the card pile.
     */
    @Test
    public void testAddCards_Cards_Absent_FiresCardAddedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCards( Arrays.asList( Cards.createUniqueCard(), Cards.createUniqueCard() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCards} method throws an exception when passed a
     * card collection that contains a {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCards_Cards_ContainsNullElement()
    {
        cardPile_.addCards( Collections.<ICard>singletonList( null ) );
    }

    /**
     * Ensures the {@code addCards} method throws an exception when passed a
     * {@code null} card collection.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCards_Cards_Null()
    {
        cardPile_.addCards( null );
    }

    /**
     * Ensures the {@code addCards} method does not add cards that are present
     * in the card pile.
     */
    @Test
    public void testAddCards_Cards_Present_DoesNotAddCard()
    {
        final ICard card = Cards.createUniqueCard();
        cardPile_.addCards( Collections.singletonList( card ) );

        cardPile_.addCards( Collections.singletonList( card ) );

        final List<ICard> cards = cardPile_.getCards();
        assertSame( card, cards.get( cards.size() - 1 ) );
        assertEquals( 1, cards.size() );
    }

    /**
     * Ensures the {@code addCards} method does not fire a card added event when
     * the cards are present in the card pile.
     */
    @Test
    public void testAddCards_Cards_Present_DoesNotFireCardAddedEvent()
    {
        final ICard card = Cards.createUniqueCard();
        cardPile_.addCard( card );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.addCards( Collections.singletonList( card ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the card added event catches any exception thrown by the {@code
     * cardAdded} method of a card pile listener.
     */
    @Test
    public void testCardAdded_CatchesListenerException()
    {
        final ICardPileListener listener1 = mocksControl_.createMock( ICardPileListener.class );
        listener1.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardPileListener listener2 = mocksControl_.createMock( ICardPileListener.class );
        listener2.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener1 );
        cardPile_.addCardPileListener( listener2 );

        cardPile_.addCard( Cards.createUniqueCard() );

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile bounds changed event catches any exception thrown
     * by the {@code cardPileBoundsChanged} method of a card pile listener.
     */
    @Test
    public void testCardPileBoundsChanged_CatchesListenerException()
    {
        final ICardPileListener listener1 = mocksControl_.createMock( ICardPileListener.class );
        listener1.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardPileListener listener2 = mocksControl_.createMock( ICardPileListener.class );
        listener2.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener1 );
        cardPile_.addCardPileListener( listener2 );

        cardPile_.setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the card removed event catches any exception thrown by the
     * {@code cardRemoved} method of a card pile listener.
     */
    @Test
    public void testCardRemoved_CatchesListenerException()
    {
        cardPile_.addCard( Cards.createUniqueCard() );
        final ICardPileListener listener1 = mocksControl_.createMock( ICardPileListener.class );
        listener1.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardPileListener listener2 = mocksControl_.createMock( ICardPileListener.class );
        listener2.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener1 );
        cardPile_.addCardPileListener( listener2 );

        cardPile_.removeCard();

        mocksControl_.verify();
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
     * Ensures the {@code getBaseLocation} method returns a copy of the base
     * location.
     */
    @Test
    public void testGetBaseLocation_ReturnValue_Copy()
    {
        final Point baseLocation = cardPile_.getBaseLocation();
        final Point expectedBaseLocation = new Point( baseLocation );

        baseLocation.setLocation( 1010, 2020 );

        assertEquals( expectedBaseLocation, cardPile_.getBaseLocation() );
    }

    /**
     * Ensures the {@code getBaseLocation} method does not return {@code null}.
     */
    @Test
    public void testGetBaseLocation_ReturnValue_NonNull()
    {
        assertNotNull( cardPile_.getBaseLocation() );
    }

    /**
     * Ensures the {@code getBaseLocation} method returns the correct value
     * after a translation.
     */
    @Test
    public void testGetBaseLocation_Translate()
    {
        final Point expectedBaseLocation = new Point( 1010, 2020 );
        cardPile_.setBaseLocation( expectedBaseLocation );

        final Point actualBaseLocation = cardPile_.getBaseLocation();

        assertEquals( expectedBaseLocation, actualBaseLocation );
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
     * Ensures the {@code getCard} method returns {@code null} when a card is
     * absent at the specified location.
     */
    @Test
    public void testGetCard_Location_CardAbsent()
    {
        assertNull( cardPile_.getCard( new Point( 0, 0 ) ) );
    }

    /**
     * Ensures the {@code getCard} method returns the top-most card when
     * multiple cards are present at the specified location.
     */
    @Test
    public void testGetCard_Location_CardPresent_MultipleCards()
    {
        final ICard initialCard = Cards.createUniqueCard();
        cardPile_.addCard( initialCard );
        final ICard expectedCard = Cards.createUniqueCard();
        cardPile_.addCard( expectedCard );

        final ICard actualCard = cardPile_.getCard( new Point( 0, 0 ) );

        assertSame( expectedCard, actualCard );
    }

    /**
     * Ensures the {@code getCard} method returns {@code null} when a card is
     * present at the specified location but the card is not the top-most card
     * when the stacked layout is active.
     */
    @Test
    public void testGetCard_Location_CardPresent_NotTopCardInStackedLayout()
    {
        cardPile_.setLayout( CardPileLayout.STACKED );
        final Rectangle originalBounds = cardPile_.getBounds();
        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( originalBounds.equals( cardPile_.getBounds() ) );

        final Point location = new Point( 0, 0 );
        final ICard actualCard = cardPile_.getCard( location );

        assertTrue( cardPile_.getBounds().contains( location ) );
        assertNull( actualCard );
    }

    /**
     * Ensures the {@code getCard} method returns the appropriate card when a
     * single card is present at the specified location.
     */
    @Test
    public void testGetCard_Location_CardPresent_SingleCard()
    {
        final ICard expectedCard = Cards.createUniqueCard();
        cardPile_.addCard( expectedCard );

        final ICard actualCard = cardPile_.getCard( new Point( 0, 0 ) );

        assertSame( expectedCard, actualCard );
    }

    /**
     * Ensures the {@code getCard} method throws an exception when passed a
     * {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCard_Location_Null()
    {
        cardPile_.getCard( null );
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCard();

        mocksControl_.verify();
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
     * Ensures the {@code removeCard} method changes the card pile bounds when
     * the card pile is not empty.
     */
    @Test( timeout = 1000 )
    public void testRemoveCard_NotEmpty_ChangesCardPileBounds()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );
        final Rectangle originalCardPileBounds = cardPile_.getBounds();

        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( originalCardPileBounds.equals( cardPile_.getBounds() ) );
        cardPile_.removeCard();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCard} method fires a card removed event when the
     * card pile is not empty.
     */
    @Test
    public void testRemoveCard_NotEmpty_FiresCardRemovedEvent()
    {
        cardPile_.addCard( Cards.createUniqueCard() );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCard();

        mocksControl_.verify();
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
        cardPile_.removeCardPileListener( mocksControl_.createMock( ICardPileListener.class ) );
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );
        cardPile_.addCard( Cards.createUniqueCard() );

        cardPile_.removeCardPileListener( listener );
        cardPile_.addCard( Cards.createUniqueCard() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards()} method does not fire a card removed
     * event when the card pile is empty.
     */
    @Test
    public void testRemoveCards_Empty_DoesNotFireCardRemovedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCards();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards()} method returns an empty collection when
     * the card pile is empty.
     */
    @Test
    public void testRemoveCards_Empty_DoesNotRemoveCards()
    {
        final List<ICard> cards = cardPile_.removeCards();

        assertNotNull( cards );
        assertEquals( 0, cards.size() );
    }

    /**
     * Ensures the {@code removeCards()} method changes the card pile bounds
     * when the card pile is not empty.
     */
    @Test( timeout = 1000 )
    public void testRemoveCards_NotEmpty_ChangesCardPileBounds()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );
        final Rectangle originalCardPileBounds = cardPile_.getBounds();

        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( originalCardPileBounds.equals( cardPile_.getBounds() ) );
        cardPile_.removeCards();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards()} method fires a card removed event when
     * the card pile is not empty.
     */
    @Test
    public void testRemoveCards_NotEmpty_FiresCardRemovedEvent()
    {
        cardPile_.addCards( Arrays.asList( Cards.createUniqueCard(), Cards.createUniqueCard() ) );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCards();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards()} method removes all cards in the card
     * pile when the card pile is not empty.
     */
    @Test
    public void testRemoveCards_NotEmpty_RemovesAllCards()
    {
        final List<ICard> expectedCards = new ArrayList<ICard>();
        expectedCards.add( Cards.createUniqueCard() );
        expectedCards.add( Cards.createUniqueCard() );
        expectedCards.add( Cards.createUniqueCard() );
        cardPile_.addCards( expectedCards );

        final List<ICard> actualCards = cardPile_.removeCards();

        assertEquals( expectedCards, actualCards );
        assertEquals( 0, cardPile_.getCards().size() );
    }

    /**
     * Ensures the {@code removeCards(Point)} method does not fire a card
     * removed event when a card is absent at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardAbsent_DoesNotFireCardRemovedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCards( new Point( 0, 0 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards(Point)} method returns an empty collection
     * when a card is absent at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardAbsent_DoesNotRemoveCards()
    {
        final List<ICard> cards = cardPile_.removeCards( new Point( 0, 0 ) );

        assertNotNull( cards );
        assertEquals( 0, cards.size() );
    }

    /**
     * Ensures the {@code removeCards(Point)} method changes the card pile
     * bounds when a card is present at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardPresent_ChangesCardPileBounds()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );
        final Rectangle originalCardPileBounds = cardPile_.getBounds();

        do
        {
            cardPile_.addCard( Cards.createUniqueCard() );

        } while( originalCardPileBounds.equals( cardPile_.getBounds() ) );
        final List<ICard> cards = cardPile_.getCards();
        cardPile_.removeCards( cards.get( cards.size() - 1 ).getLocation() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards(Point)} method fires a card removed event
     * when a card is present at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardPresent_FiresCardRemovedEvent()
    {
        cardPile_.setLayout( CardPileLayout.ACCORDIAN_RIGHT );
        cardPile_.addCards( Arrays.asList( Cards.createUniqueCard(), Cards.createUniqueCard() ) );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.removeCards( cardPile_.getCards().get( 0 ).getLocation() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards(Point)} method removes the correct cards
     * from the card pile when a card is present at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardPresent_RemovesCards()
    {
        final List<ICard> cards = new ArrayList<ICard>();
        cards.add( Cards.createUniqueCard() );
        cards.add( Cards.createUniqueCard() );
        cards.add( Cards.createUniqueCard() );
        cardPile_.setLayout( CardPileLayout.ACCORDIAN_RIGHT );
        cardPile_.addCards( cards );
        final List<ICard> expectedCards = cards.subList( 1, cards.size() );

        final List<ICard> actualCards = cardPile_.removeCards( cards.get( 1 ).getLocation() );

        assertEquals( expectedCards, actualCards );
        assertEquals( cards.size() - expectedCards.size(), cardPile_.getCards().size() );
    }

    /**
     * Ensures the {@code removeCards(Point)} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardsFromPoint_Location_Null()
    {
        cardPile_.removeCards( null );
    }

    /**
     * Ensures the {@code setBaseLocation} method changes the location of all
     * child cards to reflect the new card pile base location.
     */
    @Test
    public void testSetBaseLocation_ChangesChildCardLocation()
    {
        final ICard card = Cards.createUniqueCard();
        cardPile_.addCard( card );
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        cardPile_.setBaseLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setBaseLocation} method fires a card pile bounds
     * changed event.
     */
    @Test
    public void testSetBaseLocation_FiresCardPileBoundsChangedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.setBaseLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setBaseLocation} method makes a copy of the base
     * location.
     */
    @Test
    public void testSetBaseLocation_BaseLocation_Copy()
    {
        final Point expectedBaseLocation = new Point( 1010, 2020 );
        final Point baseLocation = new Point( expectedBaseLocation );

        cardPile_.setBaseLocation( baseLocation );
        baseLocation.setLocation( 1, 2 );

        assertEquals( expectedBaseLocation, cardPile_.getBaseLocation() );
    }

    /**
     * Ensures the {@code setBaseLocation} method throws an exception when
     * passed a {@code null} base location.
     */
    @Test( expected = NullPointerException.class )
    public void testSetBaseLocation_BaseLocation_Null()
    {
        cardPile_.setBaseLocation( null );
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.setLayout( CardPileLayout.ACCORDIAN_DOWN );

        mocksControl_.verify();
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
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardPileBoundsChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        cardPile_.addCardPileListener( listener );

        cardPile_.setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
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
