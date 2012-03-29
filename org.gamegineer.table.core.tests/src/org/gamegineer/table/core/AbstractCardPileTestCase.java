/*
 * AbstractCardPileTestCase.java
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
 * Created on Jan 14, 2010 at 10:46:47 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.table.core.Assert.assertCardPileEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPile} interface.
 */
public abstract class AbstractCardPileTestCase
    extends AbstractContainerTestCase<ICardPile>
{
    // ======================================================================
    // Fields
    // ======================================================================

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
        final ICardPile expectedCardPile = (ICardPile)expected;
        final ICardPile actualCardPile = (ICardPile)actual;
        assertCardPileEquals( expectedCardPile, actualCardPile );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected IMementoOriginator createMementoOriginator()
    {
        final ICardPile cardPile = getCardPile();
        cardPile.setBaseLocation( new Point( 0, 0 ) );
        cardPile.setLayout( CardPileLayout.STACKED );
        return cardPile;
    }

    /**
     * Creates a new card with unique back and face designs for the fixture
     * table.
     * 
     * @return A new card; never {@code null}.
     */
    /* @NonNull */
    private ICard createUniqueCard()
    {
        return Cards.createUniqueCard( getTable() );
    }

    /**
     * Creates a new card pile with a unique base design for the fixture table.
     * 
     * @return A new card pile; never {@code null}.
     */
    /* @NonNull */
    private ICardPile createUniqueCardPile()
    {
        return CardPiles.createUniqueCardPile( getTable() );
    }

    /**
     * Gets the card pile under test in the fixture.
     * 
     * @return The card pile under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ICardPile getCardPile()
    {
        return getContainer();
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final ICardPile cardPile = (ICardPile)mementoOriginator;
        cardPile.setBaseLocation( new Point( Integer.MAX_VALUE, Integer.MIN_VALUE ) );
        cardPile.setLayout( CardPileLayout.ACCORDIAN_DOWN );
        cardPile.addCard( Cards.createUniqueCard( getTable() ) );
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();

        super.setUp();

        getCardPile().setBaseDesign( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );
    }

    /**
     * Ensures the {@code addCard} method adds a card to the card pile.
     */
    @Test
    public void testAddCard_AddsCard()
    {
        final ICard card = createUniqueCard();

        getCardPile().addCard( card );

        final List<ICard> cards = getCardPile().getCards();
        assertSame( card, cards.get( cards.size() - 1 ) );
        assertSame( getCardPile(), card.getCardPile() );
    }

    /**
     * Ensures the {@code addCard} method throws an exception when passed an
     * illegal card that was created by a different table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCard_Card_Illegal_CreatedByDifferentTable()
    {
        final ITable otherTable = TableFactory.createTable();
        final ICard otherCard = Cards.createUniqueCard( otherTable );

        getCardPile().addCard( otherCard );
    }

    /**
     * Ensures the {@code addCard} method throws an exception when passed an
     * illegal card that is already contained in a card pile.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCard_Card_Illegal_Owned()
    {
        final ICardPile otherCardPile = createUniqueCardPile();
        final ICard card = createUniqueCard();
        otherCardPile.addCard( card );

        getCardPile().addCard( card );
    }

    /**
     * Ensures the {@code addCard} method throws an exception when passed a
     * {@code null} card.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCard_Card_Null()
    {
        getCardPile().addCard( null );
    }

    /**
     * Ensures the {@code addCard} method changes the location of the card to
     * reflect the card pile location.
     */
    @Test
    public void testAddCard_ChangesCardLocation()
    {
        final ICard card = createUniqueCard();
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        getCardPile().addCard( card );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCard} method changes the card pile bounds.
     */
    @Test( timeout = 1000 )
    public void testAddCard_ChangesCardPileBounds()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );
        final Rectangle originalCardPileBounds = getCardPile().getBounds();

        do
        {
            getCardPile().addCard( createUniqueCard() );

        } while( originalCardPileBounds.equals( getCardPile().getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCard} method fires a card added event.
     */
    @Test
    public void testAddCard_FiresCardAddedEvent()
    {
        final ICard card = createUniqueCard();
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        final Capture<CardPileContentChangedEvent> eventCapture = new Capture<CardPileContentChangedEvent>();
        listener.cardAdded( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().addCard( card );

        mocksControl_.verify();
        assertSame( getCardPile(), eventCapture.getValue().getCardPile() );
        assertSame( card, eventCapture.getValue().getCard() );
        assertEquals( 0, eventCapture.getValue().getCardIndex() );
    }

    /**
     * Ensures the {@code addCardPileListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardPileListener_Listener_Null()
    {
        getCardPile().addCardPileListener( null );
    }

    /**
     * Ensures the {@code addCardPileListener} method throws an exception when
     * passed a listener that is present in the card pile listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardPileListener_Listener_Present()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        getCardPile().addCardPileListener( listener );

        getCardPile().addCardPileListener( listener );
    }

    /**
     * Ensures the {@code addCards} method adds cards to the card pile.
     */
    @Test
    public void testAddCards_AddsCards()
    {
        final ICard card1 = createUniqueCard();
        final ICard card2 = createUniqueCard();

        getCardPile().addCards( Arrays.asList( card1, card2 ) );

        final List<ICard> cards = getCardPile().getCards();
        assertSame( card1, cards.get( 0 ) );
        assertSame( getCardPile(), card1.getCardPile() );
        assertSame( card2, cards.get( 1 ) );
        assertSame( getCardPile(), card2.getCardPile() );
    }

    /**
     * Ensures the {@code addCards} method throws an exception when passed an
     * illegal card collection that contains at least one card that was created
     * by a different table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCards_Cards_Illegal_ContainsCardCreatedByDifferentTable()
    {
        final ITable otherTable = TableFactory.createTable();
        final ICard otherCard = Cards.createUniqueCard( otherTable );

        getCardPile().addCards( Arrays.asList( createUniqueCard(), otherCard ) );
    }

    /**
     * Ensures the {@code addCards} method throws an exception when passed an
     * illegal card collection that contains at least one card already contained
     * in a card pile.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCards_Cards_Illegal_ContainsOwnedCard()
    {
        final ICardPile otherCardPile = createUniqueCardPile();
        final ICard card = createUniqueCard();
        otherCardPile.addCard( card );

        getCardPile().addCards( Arrays.asList( createUniqueCard(), card ) );
    }

    /**
     * Ensures the {@code addCards} method throws an exception when passed an
     * illegal card collection that contains a {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCards_Cards_Illegal_ContainsNullElement()
    {
        getCardPile().addCards( Collections.<ICard>singletonList( null ) );
    }

    /**
     * Ensures the {@code addCards} method throws an exception when passed a
     * {@code null} card collection.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCards_Cards_Null()
    {
        getCardPile().addCards( null );
    }

    /**
     * Ensures the {@code addCards} method changes the location of the cards to
     * reflect the card pile location.
     */
    @Test
    public void testAddCards_ChangesCardLocation()
    {
        final ICard card = createUniqueCard();
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        getCardPile().addCards( Collections.singletonList( card ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCards} method changes the card pile bounds.
     */
    @Test( timeout = 1000 )
    public void testAddCards_ChangesCardPileBounds()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );
        final Rectangle originalCardPileBounds = getCardPile().getBounds();

        do
        {
            getCardPile().addCards( Collections.singletonList( createUniqueCard() ) );

        } while( originalCardPileBounds.equals( getCardPile().getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addCards} method fires a card added event.
     */
    @Test
    public void testAddCards_FiresCardAddedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardAdded( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().addCards( Arrays.asList( createUniqueCard(), createUniqueCard() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the card added event catches any exception thrown by the
     * {@code cardAdded} method of a card pile listener.
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
        getCardPile().addCardPileListener( listener1 );
        getCardPile().addCardPileListener( listener2 );

        getCardPile().addCard( createUniqueCard() );

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile base design changed event catches any exception
     * thrown by the {@code cardPileBaseDesignChanged} method of a card pile
     * listener.
     */
    @Test
    public void testCardPileBaseDesignChanged_CatchesListenerException()
    {
        final ICardPileListener listener1 = mocksControl_.createMock( ICardPileListener.class );
        listener1.cardPileBaseDesignChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardPileListener listener2 = mocksControl_.createMock( ICardPileListener.class );
        listener2.cardPileBaseDesignChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener1 );
        getCardPile().addCardPileListener( listener2 );

        getCardPile().setBaseDesign( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile layout changed event catches any exception thrown
     * by the {@code cardPileLayoutChanged} method of a card pile listener.
     */
    @Test
    public void testCardPileLayoutChanged_CatchesListenerException()
    {
        final ICardPileListener listener1 = mocksControl_.createMock( ICardPileListener.class );
        listener1.cardPileLayoutChanged( EasyMock.notNull( CardPileEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardPileListener listener2 = mocksControl_.createMock( ICardPileListener.class );
        listener2.cardPileLayoutChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener1 );
        getCardPile().addCardPileListener( listener2 );

        getCardPile().setLayout( CardPileLayout.ACCORDIAN_DOWN );

        mocksControl_.verify();
    }

    /**
     * Ensures the card removed event catches any exception thrown by the
     * {@code cardRemoved} method of a card pile listener.
     */
    @Test
    public void testCardRemoved_CatchesListenerException()
    {
        getCardPile().addCard( createUniqueCard() );
        final ICardPileListener listener1 = mocksControl_.createMock( ICardPileListener.class );
        listener1.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardPileListener listener2 = mocksControl_.createMock( ICardPileListener.class );
        listener2.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener1 );
        getCardPile().addCardPileListener( listener2 );

        getCardPile().removeCard();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getBaseDesign} method does not return {@code null}.
     */
    @Test
    public void testGetBaseDesign_ReturnValue_NonNull()
    {
        assertNotNull( getCardPile().getBaseDesign() );
    }

    /**
     * Ensures the {@code getBaseLocation} method returns a copy of the base
     * location.
     */
    @Test
    public void testGetBaseLocation_ReturnValue_Copy()
    {
        final Point baseLocation = getCardPile().getBaseLocation();
        final Point expectedBaseLocation = new Point( baseLocation );

        baseLocation.setLocation( 1010, 2020 );

        assertEquals( expectedBaseLocation, getCardPile().getBaseLocation() );
    }

    /**
     * Ensures the {@code getBaseLocation} method does not return {@code null}.
     */
    @Test
    public void testGetBaseLocation_ReturnValue_NonNull()
    {
        assertNotNull( getCardPile().getBaseLocation() );
    }

    /**
     * Ensures the {@code getBaseLocation} method returns the correct value
     * after a translation.
     */
    @Test
    public void testGetBaseLocation_Translate()
    {
        final Point expectedBaseLocation = new Point( 1010, 2020 );
        getCardPile().setBaseLocation( expectedBaseLocation );

        final Point actualBaseLocation = getCardPile().getBaseLocation();

        assertEquals( expectedBaseLocation, actualBaseLocation );
    }

    /**
     * Ensures the {@code getCard(int)} method throws an exception when passed
     * an illegal index greater than the maximum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardFromIndex_Index_Illegal_GreaterThanMaximumLegalValue()
    {
        getCardPile().getCard( 0 );
    }

    /**
     * Ensures the {@code getCard(int)} method throws an exception when passed
     * an illegal index less than the minimum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardFromIndex_Index_Illegal_LessThanMinimumLegalValue()
    {
        getCardPile().getCard( -1 );
    }

    /**
     * Ensures the {@code getCard(int)} method returns the correct card when
     * passed a legal index.
     */
    @Test
    public void testGetCardFromIndex_Index_Legal()
    {
        final ICard expectedValue = createUniqueCard();
        getCardPile().addCard( expectedValue );

        final ICard actualValue = getCardPile().getCard( 0 );

        assertSame( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getCard(Point)} method returns {@code null} when a
     * card is absent at the specified location.
     */
    @Test
    public void testGetCardFromLocation_Location_CardAbsent()
    {
        assertNull( getCardPile().getCard( new Point( 0, 0 ) ) );
    }

    /**
     * Ensures the {@code getCard(Point)} method returns the top-most card when
     * multiple cards are present at the specified location.
     */
    @Test
    public void testGetCardFromLocation_Location_CardPresent_MultipleCards()
    {
        final ICard initialCard = createUniqueCard();
        getCardPile().addCard( initialCard );
        final ICard expectedCard = createUniqueCard();
        getCardPile().addCard( expectedCard );

        final ICard actualCard = getCardPile().getCard( new Point( 0, 0 ) );

        assertSame( expectedCard, actualCard );
    }

    /**
     * Ensures the {@code getCard(Point)} method returns {@code null} when a
     * card is present at the specified location but the card is not the
     * top-most card when the stacked layout is active.
     */
    @Test
    public void testGetCardFromLocation_Location_CardPresent_NotTopCardInStackedLayout()
    {
        getCardPile().setLayout( CardPileLayout.STACKED );
        final Rectangle originalBounds = getCardPile().getBounds();
        do
        {
            getCardPile().addCard( createUniqueCard() );

        } while( originalBounds.equals( getCardPile().getBounds() ) );

        final Point location = new Point( 0, 0 );
        final ICard actualCard = getCardPile().getCard( location );

        assertTrue( getCardPile().getBounds().contains( location ) );
        assertNull( actualCard );
    }

    /**
     * Ensures the {@code getCard(Point)} method returns the appropriate card
     * when a single card is present at the specified location.
     */
    @Test
    public void testGetCardFromLocation_Location_CardPresent_SingleCard()
    {
        final ICard expectedCard = createUniqueCard();
        getCardPile().addCard( expectedCard );

        final ICard actualCard = getCardPile().getCard( new Point( 0, 0 ) );

        assertSame( expectedCard, actualCard );
    }

    /**
     * Ensures the {@code getCard(Point)} method throws an exception when passed
     * a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardFromLocation_Location_Null()
    {
        getCardPile().getCard( null );
    }

    /**
     * Ensures the {@code getCardCount} method returns the correct value.
     */
    @Test
    public void testGetCardCount()
    {
        getCardPile().addCard( createUniqueCard() );
        getCardPile().addCard( createUniqueCard() );
        getCardPile().addCard( createUniqueCard() );

        final int actualValue = getCardPile().getCardCount();

        assertEquals( 3, actualValue );
    }

    /**
     * Ensures the {@code getCardIndex} method throws an exception when passed a
     * card that is absent from the card collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetCardIndex_Card_Absent()
    {
        getCardPile().getCardIndex( createUniqueCard() );
    }

    /**
     * Ensures the {@code getCardIndex} method throws an exception when passed a
     * {@code null} card.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardIndex_Card_Null()
    {
        getCardPile().getCardIndex( null );
    }

    /**
     * Ensures the {@code getCardIndex} method returns the correct value when
     * passed a card present in the card collection.
     */
    @Test
    public void testGetCardIndex_Card_Present()
    {
        final ICard card = createUniqueCard();
        getCardPile().addCard( createUniqueCard() );
        getCardPile().addCard( card );
        getCardPile().addCard( createUniqueCard() );

        final int actualValue = getCardPile().getCardIndex( card );

        assertEquals( 1, actualValue );
    }

    /**
     * Ensures the {@code getCards} method returns a copy of the card
     * collection.
     */
    @Test
    public void testGetCards_ReturnValue_Copy()
    {
        final List<ICard> cards = getCardPile().getCards();
        final int expectedCardsSize = cards.size();

        getCardPile().addCard( createUniqueCard() );

        assertEquals( expectedCardsSize, cards.size() );
    }

    /**
     * Ensures the {@code getCards} method does not return {@code null}.
     */
    @Test
    public void testGetCards_ReturnValue_NonNull()
    {
        assertNotNull( getCardPile().getCards() );
    }

    /**
     * Ensures the {@code getLayout} method does not return {@code null}.
     */
    @Test
    public void testGetLayout_ReturnValue_NonNull()
    {
        assertNotNull( getCardPile().getLayout() );
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
        getCardPile().addCardPileListener( listener );

        getCardPile().removeCard();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCard} method returns {@code null} when the card
     * pile is empty.
     */
    @Test
    public void testRemoveCard_Empty_DoesNotRemoveCard()
    {
        final ICard card = getCardPile().removeCard();

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
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );
        final Rectangle originalCardPileBounds = getCardPile().getBounds();

        do
        {
            getCardPile().addCard( createUniqueCard() );

        } while( originalCardPileBounds.equals( getCardPile().getBounds() ) );
        getCardPile().removeCard();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCard} method fires a card removed event when the
     * card pile is not empty.
     */
    @Test
    public void testRemoveCard_NotEmpty_FiresCardRemovedEvent()
    {
        final ICard card = createUniqueCard();
        getCardPile().addCard( card );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        final Capture<CardPileContentChangedEvent> eventCapture = new Capture<CardPileContentChangedEvent>();
        listener.cardRemoved( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().removeCard();

        mocksControl_.verify();
        assertSame( getCardPile(), eventCapture.getValue().getCardPile() );
        assertSame( card, eventCapture.getValue().getCard() );
        assertEquals( 0, eventCapture.getValue().getCardIndex() );
    }

    /**
     * Ensures the {@code removeCard} method removes the card at the top of the
     * card pile when the card pile is not empty.
     */
    @Test
    public void testRemoveCard_NotEmpty_RemovesCard()
    {
        final ICard expectedCard = createUniqueCard();
        getCardPile().addCard( expectedCard );

        final ICard actualCard = getCardPile().removeCard();

        assertSame( expectedCard, actualCard );
        assertEquals( 0, getCardPile().getCardCount() );
        assertNull( actualCard.getCardPile() );
    }

    /**
     * Ensures the {@code removeCardPileListener} method throws an exception
     * when passed a listener that is absent from the card pile listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardPileListener_Listener_Absent()
    {
        getCardPile().removeCardPileListener( mocksControl_.createMock( ICardPileListener.class ) );
    }

    /**
     * Ensures the {@code removeCardPileListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardPileListener_Listener_Null()
    {
        getCardPile().removeCardPileListener( null );
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
        getCardPile().addCardPileListener( listener );
        getCardPile().addCard( createUniqueCard() );

        getCardPile().removeCardPileListener( listener );
        getCardPile().addCard( createUniqueCard() );

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
        getCardPile().addCardPileListener( listener );

        getCardPile().removeCards();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards()} method returns an empty collection when
     * the card pile is empty.
     */
    @Test
    public void testRemoveCards_Empty_DoesNotRemoveCards()
    {
        final List<ICard> cards = getCardPile().removeCards();

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
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );
        final Rectangle originalCardPileBounds = getCardPile().getBounds();

        do
        {
            getCardPile().addCard( createUniqueCard() );

        } while( originalCardPileBounds.equals( getCardPile().getBounds() ) );
        getCardPile().removeCards();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards()} method fires a card removed event when
     * the card pile is not empty.
     */
    @Test
    public void testRemoveCards_NotEmpty_FiresCardRemovedEvent()
    {
        final List<ICard> cards = Arrays.asList( createUniqueCard(), createUniqueCard() );
        getCardPile().addCards( cards );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        final Capture<CardPileContentChangedEvent> eventCapture1 = new Capture<CardPileContentChangedEvent>();
        listener.cardRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<CardPileContentChangedEvent> eventCapture2 = new Capture<CardPileContentChangedEvent>();
        listener.cardRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().removeCards();

        mocksControl_.verify();
        assertSame( getCardPile(), eventCapture1.getValue().getCardPile() );
        assertSame( cards.get( 1 ), eventCapture1.getValue().getCard() );
        assertEquals( 1, eventCapture1.getValue().getCardIndex() );
        assertSame( getCardPile(), eventCapture2.getValue().getCardPile() );
        assertSame( cards.get( 0 ), eventCapture2.getValue().getCard() );
        assertEquals( 0, eventCapture2.getValue().getCardIndex() );
    }

    /**
     * Ensures the {@code removeCards()} method removes all cards in the card
     * pile when the card pile is not empty.
     */
    @Test
    public void testRemoveCards_NotEmpty_RemovesAllCards()
    {
        final List<ICard> expectedCards = new ArrayList<ICard>();
        expectedCards.add( createUniqueCard() );
        expectedCards.add( createUniqueCard() );
        expectedCards.add( createUniqueCard() );
        getCardPile().addCards( expectedCards );

        final List<ICard> actualCards = getCardPile().removeCards();

        assertEquals( expectedCards, actualCards );
        assertEquals( 0, getCardPile().getCardCount() );
        for( final ICard actualCard : actualCards )
        {
            assertNull( actualCard.getCardPile() );
        }
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
        getCardPile().addCardPileListener( listener );

        getCardPile().removeCards( new Point( 0, 0 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards(Point)} method returns an empty collection
     * when a card is absent at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardAbsent_DoesNotRemoveCards()
    {
        final List<ICard> cards = getCardPile().removeCards( new Point( 0, 0 ) );

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
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        listener.cardRemoved( EasyMock.notNull( CardPileContentChangedEvent.class ) );
        EasyMock.expectLastCall().anyTimes();
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );
        final Rectangle originalCardPileBounds = getCardPile().getBounds();

        do
        {
            getCardPile().addCard( createUniqueCard() );

        } while( originalCardPileBounds.equals( getCardPile().getBounds() ) );
        final List<ICard> cards = getCardPile().getCards();
        getCardPile().removeCards( cards.get( cards.size() - 1 ).getLocation() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeCards(Point)} method fires a card removed event
     * when a card is present at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardPresent_FiresCardRemovedEvent()
    {
        final List<ICard> cards = Arrays.asList( createUniqueCard(), createUniqueCard() );
        getCardPile().setLayout( CardPileLayout.ACCORDIAN_RIGHT );
        getCardPile().addCards( cards );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        final Capture<CardPileContentChangedEvent> eventCapture1 = new Capture<CardPileContentChangedEvent>();
        listener.cardRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<CardPileContentChangedEvent> eventCapture2 = new Capture<CardPileContentChangedEvent>();
        listener.cardRemoved( EasyMock.capture( eventCapture2 ) );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().removeCards( getCardPile().getCards().get( 0 ).getLocation() );

        mocksControl_.verify();
        assertSame( getCardPile(), eventCapture1.getValue().getCardPile() );
        assertSame( cards.get( 1 ), eventCapture1.getValue().getCard() );
        assertEquals( 1, eventCapture1.getValue().getCardIndex() );
        assertSame( getCardPile(), eventCapture2.getValue().getCardPile() );
        assertSame( cards.get( 0 ), eventCapture2.getValue().getCard() );
        assertEquals( 0, eventCapture2.getValue().getCardIndex() );
    }

    /**
     * Ensures the {@code removeCards(Point)} method removes the correct cards
     * from the card pile when a card is present at the specified location.
     */
    @Test
    public void testRemoveCardsFromPoint_Location_CardPresent_RemovesCards()
    {
        final List<ICard> cards = new ArrayList<ICard>();
        cards.add( createUniqueCard() );
        cards.add( createUniqueCard() );
        cards.add( createUniqueCard() );
        getCardPile().setLayout( CardPileLayout.ACCORDIAN_RIGHT );
        getCardPile().addCards( cards );
        final List<ICard> expectedCards = cards.subList( 1, cards.size() );

        final List<ICard> actualCards = getCardPile().removeCards( cards.get( 1 ).getLocation() );

        assertEquals( expectedCards, actualCards );
        assertEquals( cards.size() - expectedCards.size(), getCardPile().getCardCount() );
        for( final ICard actualCard : actualCards )
        {
            assertNull( actualCard.getCardPile() );
        }
    }

    /**
     * Ensures the {@code removeCards(Point)} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardsFromPoint_Location_Null()
    {
        getCardPile().removeCards( null );
    }

    /**
     * Ensures the {@code setBaseDesign} method fires a card pile base design
     * changed event.
     */
    @Test
    public void testSetBaseDesign_FiresCardPileBaseDesignChangedEvent()
    {
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardPileBaseDesignChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().setBaseDesign( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setBaseDesign} method throws an exception when passed
     * a {@code null} base design.
     */
    @Test( expected = NullPointerException.class )
    public void testSetBaseDesign_BaseDesign_Null()
    {
        getCardPile().setBaseDesign( null );
    }

    /**
     * Ensures the {@code setBaseLocation} method changes the location of all
     * child cards to reflect the new card pile base location.
     */
    @Test
    public void testSetBaseLocation_ChangesChildCardLocation()
    {
        final ICard card = createUniqueCard();
        getCardPile().addCard( card );
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        getCardPile().setBaseLocation( new Point( 1010, 2020 ) );

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
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().setBaseLocation( new Point( 1010, 2020 ) );

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

        getCardPile().setBaseLocation( baseLocation );
        baseLocation.setLocation( 1, 2 );

        assertEquals( expectedBaseLocation, getCardPile().getBaseLocation() );
    }

    /**
     * Ensures the {@code setBaseLocation} method throws an exception when
     * passed a {@code null} base location.
     */
    @Test( expected = NullPointerException.class )
    public void testSetBaseLocation_BaseLocation_Null()
    {
        getCardPile().setBaseLocation( null );
    }

    /**
     * Ensures the {@code setLayout} method changes the card pile bounds when
     * appropriate.
     */
    @Test
    public void testSetLayout_ChangesCardPileBounds()
    {
        getCardPile().setLayout( CardPileLayout.STACKED );
        getCardPile().addCard( createUniqueCard() );
        getCardPile().addCard( createUniqueCard() );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardPileLayoutChanged( EasyMock.notNull( CardPileEvent.class ) );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().setLayout( CardPileLayout.ACCORDIAN_DOWN );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLayout} method fires a card pile layout changed
     * event.
     */
    @Test
    public void testSetLayout_FiresCardPileLayoutChangedEvent()
    {
        getCardPile().setLayout( CardPileLayout.STACKED );
        final ICardPileListener listener = mocksControl_.createMock( ICardPileListener.class );
        listener.cardPileLayoutChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );

        getCardPile().setLayout( CardPileLayout.ACCORDIAN_DOWN );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLayout} method throws an exception when passed a
     * {@code null} layout.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLayout_Layout_Null()
    {
        getCardPile().setLayout( null );
    }

    /**
     * Ensures the {@code setLocation} method changes the location of all child
     * cards to reflect the new card pile location.
     */
    @Test
    public void testSetLocation_ChangesChildCardLocation()
    {
        final ICard card = createUniqueCard();
        getCardPile().addCard( card );
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        card.addCardListener( listener );

        getCardPile().setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }
}
