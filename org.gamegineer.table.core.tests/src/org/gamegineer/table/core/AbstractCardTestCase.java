/*
 * AbstractCardTestCase.java
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
 * Created on Oct 11, 2009 at 9:46:16 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.persistence.memento.IMemento;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
     * Creates a new card from the specified memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return A new card; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     */
    /* @NonNull */
    protected abstract ICard createCard(
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
        mocksControl_ = null;
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
        final ICardListener listener = EasyMock.createMock( ICardListener.class );
        card_.addCardListener( listener );

        card_.addCardListener( listener );
    }

    /**
     * Ensures the card location changed event catches any exception thrown by
     * the {@code cardLocationChanged} method of a card listener.
     */
    @Test
    public void testCardLocationChanged_CatchesListenerException()
    {
        final ICardListener listener1 = mocksControl_.createMock( ICardListener.class );
        listener1.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardListener listener2 = mocksControl_.createMock( ICardListener.class );
        listener2.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card_.addCardListener( listener1 );
        card_.addCardListener( listener2 );

        card_.setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the card orientation changed event catches any exception thrown
     * by the {@code cardOrientationChanged} method of a card listener.
     */
    @Test
    public void testCardOrientationChanged_CatchesListenerException()
    {
        final ICardListener listener1 = mocksControl_.createMock( ICardListener.class );
        listener1.cardOrientationChanged( EasyMock.notNull( CardEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardListener listener2 = mocksControl_.createMock( ICardListener.class );
        listener2.cardOrientationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card_.addCardListener( listener1 );
        card_.addCardListener( listener2 );

        card_.setOrientation( card_.getOrientation().inverse() );

        mocksControl_.verify();
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
     * Ensures the {@code flip} method fires a card orientation changed event.
     */
    @Test
    public void testFlip_FiresCardOrientationChangedEvent()
    {
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardOrientationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card_.addCardListener( listener );

        card_.flip();

        mocksControl_.verify();
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
     * Ensures the {@code getBounds} method returns a copy of the bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = card_.getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, card_.getBounds() );

    }

    /**
     * Ensures the {@code getBounds} method does not return {@code null}.
     */
    @Test
    public void testGetBounds_ReturnValue_NonNull()
    {
        assertNotNull( card_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetBounds_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Rectangle expectedBounds = card_.getBounds();
        expectedBounds.setLocation( expectedLocation );
        card_.setLocation( expectedLocation );

        final Rectangle actualBounds = card_.getBounds();

        assertEquals( expectedBounds, actualBounds );
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
     * Ensures the {@code getLocation} method returns a copy of the location.
     */
    @Test
    public void testGetLocation_ReturnValue_Copy()
    {
        final Point location = card_.getLocation();
        final Point expectedLocation = new Point( location );

        location.setLocation( 1010, 2020 );

        assertEquals( expectedLocation, card_.getLocation() );
    }

    /**
     * Ensures the {@code getLocation} method does not return {@code null}.
     */
    @Test
    public void testGetLocation_ReturnValue_NonNull()
    {
        assertNotNull( card_.getLocation() );
    }

    /**
     * Ensures the {@code getLocation} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetLocation_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        card_.setLocation( expectedLocation );

        final Point actualLocation = card_.getLocation();

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
        final IMemento expectedMemento = card_.getMemento();

        final ICard actualCard = createCard( expectedMemento );
        final IMemento actualMemento = actualCard.getMemento();

        assertEquals( expectedMemento, actualMemento );
    }

    /**
     * Ensures the {@code getMemento} method does not return {@code null}.
     */
    @Test
    public void testGetMemento_ReturnValue_NonNull()
    {
        assertNotNull( card_.getMemento() );
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
     * Ensures the {@code getSize} method returns a copy of the size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final Dimension size = card_.getSize();
        final Dimension expectedSize = new Dimension( size );

        size.setSize( 101, 202 );

        assertEquals( expectedSize, card_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method does not return {@code null}.
     */
    @Test
    public void testGetSize_ReturnValue_NonNull()
    {
        assertNotNull( card_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetSize_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Dimension expectedSize = card_.getSize();
        card_.setLocation( expectedLocation );

        final Dimension actualSize = card_.getSize();

        assertEquals( expectedSize, actualSize );
    }

    /**
     * Ensures the {@code removeCardListener} method throws an exception when
     * passed a listener that is absent from the card listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardListener_Listener_Absent()
    {
        card_.removeCardListener( EasyMock.createMock( ICardListener.class ) );
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
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardOrientationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card_.addCardListener( listener );
        card_.flip();

        card_.removeCardListener( listener );
        card_.flip();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLocation} method fires a card location changed
     * event.
     */
    @Test
    public void testSetLocation_FiresCardLocationChangedEvent()
    {
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardLocationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card_.addCardListener( listener );

        card_.setLocation( new Point( 1010, 2020 ) );

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

        card_.setLocation( location );
        location.setLocation( 1, 2 );

        assertEquals( expectedLocation, card_.getLocation() );
    }

    /**
     * Ensures the {@code setLocation} method throws an exception when passed a
     * {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLocation_Location_Null()
    {
        card_.setLocation( null );
    }

    /**
     * Ensures the {@code setOrientation} method fires a card orientation
     * changed event.
     */
    @Test
    public void testSetOrientation_FiresCardOrientationChangedEvent()
    {
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardOrientationChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        card_.addCardListener( listener );

        card_.setOrientation( card_.getOrientation().inverse() );

        mocksControl_.verify();
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
