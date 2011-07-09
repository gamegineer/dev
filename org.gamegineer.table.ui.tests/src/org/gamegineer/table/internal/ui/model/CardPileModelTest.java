/*
 * CardPileModelTest.java
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
 * Created on Jan 26, 2010 at 10:57:19 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.TableFactory;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The card pile model under test in the fixture. */
    private CardPileModel model_;

    /** The nice mocks control for use in the fixture. */
    private IMocksControl niceMocksControl_;


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
        niceMocksControl_ = EasyMock.createNiceControl();
        model_ = new CardPileModel( CardPiles.createUniqueCardPile( TableFactory.createTable() ) );
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
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
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
        final ICard card = Cards.createUniqueCard( model_.getCardPile().getTable() );
        model_.getCardPile().addCard( card );
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelStateChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        // NB: change card model state here when applicable

        mocksControl_.verify();
    }

    /**
     * Ensures a change to the underlying card pile state fires a card pile
     * model state changed event.
     */
    @Test
    public void testCardPile_StateChanged_FiresCardPileModelStateChangedEvent()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelStateChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.getCardPile().setLocation( new Point( 101, 102 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile focus gained event catches any exception thrown by
     * the {@code cardPileFocusGained} method of a card pile model listener.
     */
    @Test
    public void testCardPileFocusGained_CatchesListenerException()
    {
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileFocusGained( EasyMock.notNull( CardPileModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the card pile focus lost event catches any exception thrown by
     * the {@code cardPileFocusLost} method of a card pile model listener.
     */
    @Test
    public void testCardPileFocusLost_CatchesListenerException()
    {
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileFocusLost( EasyMock.notNull( CardPileModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( false );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the card pile model state changed event catches any exception
     * thrown by the {@code cardPileModelStateChanged} method of a card pile
     * model listener.
     */
    @Test
    public void testCardPileModelStateChanged_CatchesListenerException()
    {
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelStateChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.getCardPile().setLocation( new Point( 101, 102 ) );

        niceMocksControl_.verify();
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
        model_.getCardModel( EasyMock.createMock( ICard.class ) );
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
        model_.removeCardPileModelListener( mocksControl_.createMock( ICardPileModelListener.class ) );
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
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileFocusGained( EasyMock.notNull( CardPileModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );
        model_.setFocused( true );

        model_.removeCardPileModelListener( listener );
        model_.setFocused( false );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile model state
     * changed event.
     */
    @Test
    public void testSetFocused_FiresCardPileModelStateChangedEvent()
    {
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelStateChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile focus gained
     * event.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresCardPileFocusGainedEvent()
    {
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileFocusGained( EasyMock.notNull( CardPileModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile focus lost event.
     */
    @Test
    public void testSetFocused_LostFocus_FiresCardPileFocusLostEvent()
    {
        final ICardPileModelListener listener = niceMocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileFocusLost( EasyMock.notNull( CardPileModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( false );

        niceMocksControl_.verify();
    }
}
