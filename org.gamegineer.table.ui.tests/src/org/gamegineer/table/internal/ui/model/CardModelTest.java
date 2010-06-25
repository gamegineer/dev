/*
 * CardModelTest.java
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
 * Created on Dec 25, 2009 at 11:02:59 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.Cards;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

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
        mocksControl_ = EasyMock.createControl();
        model_ = new CardModel( Cards.createUniqueCard() );
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
        mocksControl_ = null;
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
        final ICardModelListener listener = mocksControl_.createMock( ICardModelListener.class );
        model_.addCardModelListener( listener );

        model_.addCardModelListener( listener );
    }

    /**
     * Ensures a change to the underlying card state fires a card model state
     * changed event.
     */
    @Test
    public void testCard_StateChanged_FiresCardModelStateChangedEvent()
    {
        final ICardModelListener listener = mocksControl_.createMock( ICardModelListener.class );
        listener.cardModelStateChanged( EasyMock.notNull( CardModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardModelListener( listener );

        model_.getCard().flip();

        mocksControl_.verify();
    }

    /**
     * Ensures the card model state changed event catches any exception thrown
     * by the {@code cardModelStateChanged} method of a card model listener.
     */
    @Test
    public void testCardModelStateChanged_CatchesListenerException()
    {
        final ICardModelListener listener = mocksControl_.createMock( ICardModelListener.class );
        listener.cardModelStateChanged( EasyMock.notNull( CardModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        model_.addCardModelListener( listener );

        model_.getCard().flip();

        mocksControl_.verify();
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
        model_.removeCardModelListener( mocksControl_.createMock( ICardModelListener.class ) );
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
        final ICardModelListener listener = mocksControl_.createMock( ICardModelListener.class );
        listener.cardModelStateChanged( EasyMock.notNull( CardModelEvent.class ) );
        mocksControl_.replay();
        model_.addCardModelListener( listener );
        model_.getCard().flip();

        model_.removeCardModelListener( listener );
        model_.getCard().flip();

        mocksControl_.verify();
    }
}
