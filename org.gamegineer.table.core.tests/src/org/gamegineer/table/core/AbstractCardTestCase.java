/*
 * AbstractCardTestCase.java
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
 * Created on Oct 11, 2009 at 9:46:16 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.table.core.Assert.assertCardEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICard} interface.
 */
public abstract class AbstractCardTestCase
    extends AbstractComponentTestCase<ICard>
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
     * Initializes a new instance of the {@code AbstractCardTestCase} class.
     */
    protected AbstractCardTestCase()
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
        final ICard expectedCard = (ICard)expected;
        final ICard actualCard = (ICard)actual;
        assertCardEquals( expectedCard, actualCard );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected IMementoOriginator createMementoOriginator()
    {
        final ICard card = getCard();
        card.setLocation( new Point( 0, 0 ) );
        card.setOrientation( CardOrientation.BACK_UP );
        return card;
    }

    /**
     * Gets the card under test in the fixture.
     * 
     * @return The card under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ICard getCard()
    {
        return getComponent();
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final ICard card = (ICard)mementoOriginator;
        card.setLocation( new Point( Integer.MAX_VALUE, Integer.MIN_VALUE ) );
        card.setOrientation( CardOrientation.FACE_UP );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();

        super.setUp();

        getCard().setSurfaceDesigns( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }

    /**
     * Ensures the {@code addCardListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCardListener_Listener_Null()
    {
        getCard().addCardListener( null );
    }

    /**
     * Ensures the {@code addCardListener} method throws an exception when
     * passed a listener that is present in the card listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCardListener_Listener_Present()
    {
        final ICardListener listener = EasyMock.createMock( ICardListener.class );
        getCard().addCardListener( listener );

        getCard().addCardListener( listener );
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
        getCard().addCardListener( listener1 );
        getCard().addCardListener( listener2 );

        getCard().setLocation( new Point( 1010, 2020 ) );

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
        getCard().addCardListener( listener1 );
        getCard().addCardListener( listener2 );

        getCard().setOrientation( getCard().getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the card surface designs changed event catches any exception
     * thrown by the {@code cardSurfaceDesignsChanged} method of a card
     * listener.
     */
    @Test
    public void testCardSurfaceDesignsChanged_CatchesListenerException()
    {
        final ICardListener listener1 = mocksControl_.createMock( ICardListener.class );
        listener1.cardSurfaceDesignsChanged( EasyMock.notNull( CardEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ICardListener listener2 = mocksControl_.createMock( ICardListener.class );
        listener2.cardSurfaceDesignsChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        getCard().addCardListener( listener1 );
        getCard().addCardListener( listener2 );

        getCard().setSurfaceDesigns( CardSurfaceDesigns.createUniqueCardSurfaceDesign( 10, 10 ), CardSurfaceDesigns.createUniqueCardSurfaceDesign( 10, 10 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code flip} method correctly changes the card orientation
     * when the card back is initially up.
     */
    @Test
    public void testFlip_BackUp()
    {
        getCard().setOrientation( CardOrientation.BACK_UP );
        assertEquals( CardOrientation.BACK_UP, getCard().getOrientation() );

        getCard().flip();

        assertEquals( CardOrientation.FACE_UP, getCard().getOrientation() );
    }

    /**
     * Ensures the {@code flip} method correctly changes the card orientation
     * when the card face is initially up.
     */
    @Test
    public void testFlip_FaceUp()
    {
        getCard().setOrientation( CardOrientation.FACE_UP );
        assertEquals( CardOrientation.FACE_UP, getCard().getOrientation() );

        getCard().flip();

        assertEquals( CardOrientation.BACK_UP, getCard().getOrientation() );
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
        getCard().addCardListener( listener );

        getCard().flip();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getBackDesign} method does not return {@code null}.
     */
    @Test
    public void testGetBackDesign_ReturnValue_NonNull()
    {
        assertNotNull( getCard().getBackDesign() );
    }

    /**
     * Ensures the {@code getFaceDesign} method does not return {@code null}.
     */
    @Test
    public void testGetFaceDesign_ReturnValue_NonNull()
    {
        assertNotNull( getCard().getFaceDesign() );
    }

    /**
     * Ensures the {@code getOrientation} method does not return {@code null}.
     */
    @Test
    public void testGetOrientation_ReturnValue_NonNull()
    {
        assertNotNull( getCard().getOrientation() );
    }

    /**
     * Ensures the {@code removeCardListener} method throws an exception when
     * passed a listener that is absent from the card listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCardListener_Listener_Absent()
    {
        getCard().removeCardListener( EasyMock.createMock( ICardListener.class ) );
    }

    /**
     * Ensures the {@code removeCardListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCardListener_Listener_Null()
    {
        getCard().removeCardListener( null );
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
        getCard().addCardListener( listener );
        getCard().flip();

        getCard().removeCardListener( listener );
        getCard().flip();

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
        getCard().addCardListener( listener );

        getCard().setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
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
        getCard().addCardListener( listener );

        getCard().setOrientation( getCard().getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setOrientation} method throws an exception when passed
     * a {@code null} orientation.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOrientation_Orientation_Null()
    {
        getCard().setOrientation( null );
    }

    /**
     * Ensures the {@code setSurfaceDesigns} method throws an exception when
     * passed a {@code null} back design.
     */
    @Test( expected = NullPointerException.class )
    public void testSetSurfaceDesigns_BackDesign_Null()
    {
        getCard().setSurfaceDesigns( null, CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }

    /**
     * Ensures the {@code setSurfaceDesigns} method throws an exception when
     * passed a {@code null} face design.
     */
    @Test( expected = NullPointerException.class )
    public void testSetSurfaceDesigns_FaceDesign_Null()
    {
        getCard().setSurfaceDesigns( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), null );
    }

    /**
     * Ensures the {@code setSurfaceDesigns} method throws an exception when
     * passed a face design that has a size different from the back design.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSurfaceDesigns_FaceDesign_SizeNotEqual()
    {
        final int width = 10;
        final int height = 20;
        final ICardSurfaceDesign backDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( width, height );
        final ICardSurfaceDesign faceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( 2 * width, 2 * height );

        getCard().setSurfaceDesigns( backDesign, faceDesign );
    }

    /**
     * Ensures the {@code setSurfaceDesigns} method fires a card surface designs
     * changed event.
     */
    @Test
    public void testSetSurfaceDesigns_FiresCardSurfaceDesignsChangedEvent()
    {
        final ICardListener listener = mocksControl_.createMock( ICardListener.class );
        listener.cardSurfaceDesignsChanged( EasyMock.notNull( CardEvent.class ) );
        mocksControl_.replay();
        getCard().addCardListener( listener );

        getCard().setSurfaceDesigns( CardSurfaceDesigns.createUniqueCardSurfaceDesign( 10, 10 ), CardSurfaceDesigns.createUniqueCardSurfaceDesign( 10, 10 ) );

        mocksControl_.verify();
    }
}
