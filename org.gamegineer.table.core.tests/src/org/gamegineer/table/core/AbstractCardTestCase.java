/*
 * AbstractCardTestCase.java
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
 * Created on Oct 11, 2009 at 9:46:16 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICard} interface.
 */
public abstract class AbstractCardTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card under test in the fixture. */
    private ICard card_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCardTestCase} class.
     */
    protected AbstractCardTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card to be tested.
     * 
     * @return The card to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICard createCard()
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
        card_ = createCard();
        assertNotNull( card_ );
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
        card_ = null;
    }

    /**
     * Ensures the {@code addCardListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardListener_Listener_Null()
    {
        card_.addCardListener( null );
    }

    /**
     * Ensures the {@code addCardListener} method throws an exception when
     * passed a listener that is present in the card listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardListener_Listener_Present()
    {
        final ICardListener listener = new MockCardListener();
        card_.addCardListener( listener );

        card_.addCardListener( listener );
    }

    /**
     * Ensures the {@code flip} method correctly changes the card orientation
     * when the card back is initially up.
     */
    @Test
    public void testFlip_BackUp()
    {
        card_.setOrientation( CardOrientation.BACK_UP );
        assertEquals( CardOrientation.BACK_UP, card_.getOrientation() );

        card_.flip();

        assertEquals( CardOrientation.FACE_UP, card_.getOrientation() );
    }

    /**
     * Ensures the {@code flip} method catches any exception thrown by the
     * {@code cardFlipped} method of a card listener.
     */
    @Test
    public void testFlip_CatchesListenerException()
    {
        final MockCardListener listener1 = new MockCardListener()
        {
            @Override
            public void cardFlipped(
                final CardEvent event )
            {
                super.cardFlipped( event );

                throw new RuntimeException();
            }
        };
        final MockCardListener listener2 = new MockCardListener();
        card_.addCardListener( listener1 );
        card_.addCardListener( listener2 );

        card_.flip();

        assertEquals( 1, listener2.getCardFlippedEventCount() );
    }

    /**
     * Ensures the {@code flip} method correctly changes the card orientation
     * when the card face is initially up.
     */
    @Test
    public void testFlip_FaceUp()
    {
        card_.setOrientation( CardOrientation.FACE_UP );
        assertEquals( CardOrientation.FACE_UP, card_.getOrientation() );

        card_.flip();

        assertEquals( CardOrientation.BACK_UP, card_.getOrientation() );
    }

    /**
     * Ensures the {@code flip} method fires a card flipped event.
     */
    @Test
    public void testFlip_FiresCardFlippedEvent()
    {
        final MockCardListener listener = new MockCardListener();
        card_.addCardListener( listener );

        card_.flip();

        assertEquals( 1, listener.getCardFlippedEventCount() );
    }

    /**
     * Ensures the {@code getBackDesign} method does not return {@code null}.
     */
    @Test
    public void testGetBackDesign_ReturnValue_NonNull()
    {
        assertNotNull( card_.getBackDesign() );
    }

    /**
     * Ensures the {@code getFaceDesign} method does not return {@code null}.
     */
    @Test
    public void testGetFaceDesign_ReturnValue_NonNull()
    {
        assertNotNull( card_.getFaceDesign() );
    }

    /**
     * Ensures the {@code getOrientation} method does not return {@code null}.
     */
    @Test
    public void testGetOrientation_ReturnValue_NonNull()
    {
        assertNotNull( card_.getOrientation() );
    }

    /**
     * Ensures the {@code removeCardListener} method throws an exception when
     * passed a listener that is absent from the card listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardListener_Listener_Absent()
    {
        card_.removeCardListener( new MockCardListener() );
    }

    /**
     * Ensures the {@code removeCardListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardListener_Listener_Null()
    {
        card_.removeCardListener( null );
    }

    /**
     * Ensures the {@code removeCardListener} removes a listener that is present
     * in the card listener collection.
     */
    @Test
    public void testRemoveCardListener_Listener_Present()
    {
        final MockCardListener listener = new MockCardListener();
        card_.addCardListener( listener );
        card_.flip();

        card_.removeCardListener( listener );

        card_.flip();
        assertEquals( 1, listener.getCardFlippedEventCount() );
    }

    /**
     * Ensures the {@code setOrientation} method throws an exception when passed
     * a {@code null} orientation.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOrientation_Orientation_Null()
    {
        card_.setOrientation( null );
    }
}
