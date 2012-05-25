/*
 * CardPileModelTest.java
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
 * Created on Jan 26, 2010 at 10:57:19 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Before;
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


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileModelTest} class.
     */
    public CardPileModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Fires a card pile changed event for the card pile model under test in the
     * fixture.
     */
    private void fireCardPileChangedEvent()
    {
        fireCardPileModelEvent( "fireCardPileChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the specified card pile model event for the card pile model under
     * test in the fixture.
     * 
     * @param methodName
     *        The name of the method that fires the card pile model event; must
     *        not be {@code null}.
     */
    private void fireCardPileModelEvent(
        /* @NonNull */
        final String methodName )
    {
        assert methodName != null;

        try
        {
            final Method method = CardPileModel.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( model_ );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires a card pile model focus changed event for the card pile model under
     * test in the fixture.
     */
    private void fireCardPileModelFocusChangedEvent()
    {
        fireCardPileModelEvent( "fireCardPileModelFocusChanged" ); //$NON-NLS-1$
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
        mocksControl_ = EasyMock.createControl();
        final ITable table = TableEnvironmentFactory.createTable();
        final ICardPile cardPile = CardPiles.createUniqueCardPile( table );
        table.addCardPile( cardPile );
        model_ = new CardPileModel( cardPile );
    }

    /**
     * Ensures the {@code addCardPileModelListener} method adds a listener that
     * is absent from the card pile model listener collection.
     */
    @Test
    public void testAddCardPileModelListener_Listener_Absent()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();

        fireCardPileChangedEvent();
        model_.addCardPileModelListener( listener );
        fireCardPileChangedEvent();

        mocksControl_.verify();
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
        final ICardPileModelListener listener = EasyMock.createMock( ICardPileModelListener.class );
        model_.addCardPileModelListener( listener );

        model_.addCardPileModelListener( listener );
    }

    /**
     * Ensures a change to a card associated with a card model owned by the card
     * pile model fires a card pile state changed event.
     */
    @Test
    public void testCardModel_CardChanged_FiresCardPileChangedEvent()
    {
        final ICard card = Cards.createUniqueCard( model_.getCardPile().getTable() );
        model_.getCardPile().addComponent( card );
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        card.setOrientation( card.getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures a change to the underlying card pile state fires a card pile
     * changed event.
     */
    @Test
    public void testCardPile_StateChanged_FiresCardPileChangedEvent()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.getCardPile().setLocation( new Point( 101, 102 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile changed event catches any exception thrown by the
     * {@code cardPileChanged} method of a card pile model listener.
     */
    @Test
    public void testCardPileChanged_CatchesListenerException()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        fireCardPileChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the card pile model focus changed event catches any exception
     * thrown by the {@code cardPileModelFocusChanged} method of a card pile
     * model listener.
     */
    @Test
    public void testCardPileModelFocusChanged_CatchesListenerException()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelFocusChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        fireCardPileModelFocusChangedEvent();

        mocksControl_.verify();
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
        model_.removeCardPileModelListener( EasyMock.createMock( ICardPileModelListener.class ) );
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
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        fireCardPileChangedEvent();
        model_.removeCardPileModelListener( listener );
        fireCardPileChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile model focus
     * changed event after the card pile model gained the focus.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresCardPileModelFocusChangedEvent()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelFocusChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( true );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a card pile model focus
     * changed event after the card pile model lost the focus.
     */
    @Test
    public void testSetFocused_LostFocus_FiresCardPileModelFocusChangedEvent()
    {
        final ICardPileModelListener listener = mocksControl_.createMock( ICardPileModelListener.class );
        listener.cardPileModelFocusChanged( EasyMock.notNull( CardPileModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardPileModelListener( listener );

        model_.setFocused( false );

    }
}
