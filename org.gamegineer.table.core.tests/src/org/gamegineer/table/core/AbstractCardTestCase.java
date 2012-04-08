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
     * Ensures the {@code flip} method fires a component orientation changed
     * event.
     */
    @Test
    public void testFlip_FiresComponentOrientationChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentOrientationChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCard().addComponentListener( listener );

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
     * Ensures the {@code setOrientation} method fires a component orientation
     * changed event.
     */
    @Test
    public void testSetOrientation_FiresComponentOrientationChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentOrientationChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCard().addComponentListener( listener );

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
     * Ensures the {@code setSurfaceDesigns} method fires a component surface
     * design changed event.
     */
    @Test
    public void testSetSurfaceDesigns_FiresComponentSurfaceDesignChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCard().addComponentListener( listener );

        getCard().setSurfaceDesigns( CardSurfaceDesigns.createUniqueCardSurfaceDesign( 10, 10 ), CardSurfaceDesigns.createUniqueCardSurfaceDesign( 10, 10 ) );

        mocksControl_.verify();
    }
}
