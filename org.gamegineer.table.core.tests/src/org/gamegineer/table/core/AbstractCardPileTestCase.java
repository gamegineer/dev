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
        cardPile.addComponent( createUniqueComponent() );
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
     * Ensures the {@code getComponent(Point)} method returns {@code null} when
     * a component is present at the specified location but the component is not
     * the top-most component when the stacked layout is active.
     */
    @Test
    public void testGetComponentFromLocation_Location_ComponentPresent_NotTopComponentInStackedLayout()
    {
        getContainer().setLayout( CardPileLayout.STACKED );
        final Rectangle originalBounds = getContainer().getBounds();
        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalBounds.equals( getContainer().getBounds() ) );

        final Point location = new Point( 0, 0 );
        final IComponent actualComponent = getContainer().getComponent( location );

        assertTrue( getContainer().getBounds().contains( location ) );
        assertNull( actualComponent );
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
        listener.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        mocksControl_.replay();
        getCardPile().addCardPileListener( listener );
        getCardPile().addComponent( createUniqueComponent() );

        getCardPile().removeCardPileListener( listener );
        getCardPile().addComponent( createUniqueComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeComponents(Point)} method fires a component
     * removed event when a component is present at the specified location.
     */
    @Test
    public void testRemoveComponentsFromPoint_Location_ComponentPresent_FiresComponentRemovedEvent()
    {
        final List<IComponent> components = Arrays.asList( createUniqueComponent(), createUniqueComponent() );
        getContainer().setLayout( CardPileLayout.ACCORDIAN_RIGHT );
        getContainer().addComponents( components );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        addContainerListener( getContainer(), listener );

        getContainer().removeComponents( getContainer().getComponents().get( 0 ).getLocation() );

        mocksControl_.verify();
        assertSame( getContainer(), eventCapture1.getValue().getContainer() );
        assertSame( components.get( 1 ), eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( getContainer(), eventCapture2.getValue().getContainer() );
        assertSame( components.get( 0 ), eventCapture2.getValue().getComponent() );
        assertEquals( 0, eventCapture2.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@code removeComponents(Point)} method removes the correct
     * components from the container when a component is present at the
     * specified location.
     */
    @Test
    public void testRemoveComponentsFromPoint_Location_ComponentPresent_RemovesComponents()
    {
        final List<IComponent> components = new ArrayList<IComponent>();
        components.add( createUniqueComponent() );
        components.add( createUniqueComponent() );
        components.add( createUniqueComponent() );
        getContainer().setLayout( CardPileLayout.ACCORDIAN_RIGHT );
        getContainer().addComponents( components );
        final List<IComponent> expectedComponents = components.subList( 1, components.size() );

        final List<IComponent> actualComponents = getContainer().removeComponents( components.get( 1 ).getLocation() );

        assertEquals( expectedComponents, actualComponents );
        assertEquals( components.size() - expectedComponents.size(), getContainer().getComponentCount() );
        for( final IComponent actualComponent : actualComponents )
        {
            assertNull( actualComponent.getContainer() );
        }
    }

    /**
     * Ensures the {@code setBaseDesign} method fires a component surface design
     * changed event.
     */
    @Test
    public void testSetBaseDesign_FiresComponentSurfaceDesignChangedEvent()
    {
        final IComponentListener componentListener = mocksControl_.createMock( IComponentListener.class );
        componentListener.componentSurfaceDesignChanged( EasyMock.notNull( CardPileEvent.class ) );
        final ICardPileListener cardPileListener = mocksControl_.createMock( ICardPileListener.class );
        cardPileListener.componentSurfaceDesignChanged( EasyMock.notNull( CardPileEvent.class ) );
        mocksControl_.replay();
        getCardPile().addComponentListener( componentListener );
        getCardPile().addCardPileListener( cardPileListener );

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
        final ICard card = Cards.createUniqueCard( getTable() );
        getCardPile().addComponent( card );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        card.addComponentListener( listener );

        getCardPile().setBaseLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setBaseLocation} method fires a component bounds
     * changed event.
     */
    @Test
    public void testSetBaseLocation_FiresComponentBoundsChangedEvent()
    {
        final IComponentListener componentListener = mocksControl_.createMock( IComponentListener.class );
        componentListener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        final ICardPileListener cardPileListener = mocksControl_.createMock( ICardPileListener.class );
        cardPileListener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getCardPile().addComponentListener( componentListener );
        getCardPile().addCardPileListener( cardPileListener );

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
        getCardPile().addComponent( createUniqueComponent() );
        getCardPile().addComponent( createUniqueComponent() );
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
}
