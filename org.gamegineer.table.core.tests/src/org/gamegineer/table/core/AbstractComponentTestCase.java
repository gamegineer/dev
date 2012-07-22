/*
 * AbstractComponentTestCase.java
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
 * Created on Mar 26, 2012 at 8:15:10 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.table.core.Assert.assertComponentEquals;
import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponent} interface.
 * 
 * @param <TableEnvironmentType>
 *        The type of the table environment.
 * @param <ComponentType>
 *        The type of the component.
 */
public abstract class AbstractComponentTestCase<TableEnvironmentType extends ITableEnvironment, ComponentType extends IComponent>
    extends AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component under test in the fixture. */
    private ComponentType component_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table environment for use in the fixture. */
    private TableEnvironmentType tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentTestCase}
     * class.
     */
    protected AbstractComponentTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation compares the expected and actual values according to
     * the specification of the
     * {@link org.gamegineer.table.core.Assert#assertComponentEquals} method.
     * 
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#assertMementoOriginatorEquals(org.gamegineer.common.core.util.memento.IMementoOriginator,
     *      org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void assertMementoOriginatorEquals(
        final IMementoOriginator expected,
        final IMementoOriginator actual )
    {
        final IComponent expectedComponent = (IComponent)expected;
        final IComponent actualComponent = (IComponent)actual;
        assertComponentEquals( expectedComponent, actualComponent );
    }

    /**
     * Creates the component to be tested.
     * 
     * @param tableEnvironment
     *        The fixture table environment; must not be {@code null}.
     * 
     * @return The component to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    /* @NonNull */
    protected abstract ComponentType createComponent(
        /* @NonNull */
        TableEnvironmentType tableEnvironment )
        throws Exception;

    /**
     * Creates a component orientation that is guaranteed to be illegal for all
     * components.
     * 
     * @return An illegal component orientation; never {@code null}.
     */
    /* @NonNull */
    private static ComponentOrientation createIllegalOrientation()
    {
        return new ComponentOrientation( "illegal", 0 ) //$NON-NLS-1$
        {
            private static final long serialVersionUID = 1L;

            @Override
            public ComponentOrientation inverse()
            {
                return this;
            }
        };
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected final IMementoOriginator createMementoOriginator()
        throws Exception
    {
        return createComponent( tableEnvironment_ );
    }

    /**
     * Creates the table environment for use in the fixture.
     * 
     * @return The table environment for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected abstract TableEnvironmentType createTableEnvironment();

    /**
     * Fires a component bounds changed event for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    protected abstract void fireComponentBoundsChanged(
        /* @NonNull */
        ComponentType component );

    /**
     * Fires a component orientation changed event for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    protected abstract void fireComponentOrientationChanged(
        /* @NonNull */
        ComponentType component );

    /**
     * Fires a component surface design changed event for the specified
     * component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    protected abstract void fireComponentSurfaceDesignChanged(
        /* @NonNull */
        ComponentType component );

    /**
     * Gets an alternate to the specified component orientation from the
     * specified collection of supported component orientations.
     * 
     * @param orientation
     *        The component orientation for which an alternate is desired; must
     *        not be {@code null}.
     * @param supportedOrientations
     *        The collection of supported component orientations; must not be
     *        {@code null}.
     * 
     * @return The alternate component orientation; never {@code null}. If no
     *         alternate component orientation is available, the specified
     *         component orientation is returned.
     */
    /* @NonNull */
    private static ComponentOrientation getAlternateOrientation(
        /* @NonNull */
        final ComponentOrientation orientation,
        /* @NonNull */
        final Collection<ComponentOrientation> supportedOrientations )
    {
        for( final ComponentOrientation supportedOrientation : supportedOrientations )
        {
            if( supportedOrientation != orientation )
            {
                return supportedOrientation;
            }
        }

        return orientation;
    }

    /**
     * Gets an alternate to the specified point.
     * 
     * @param point
     *        The point for which an alternate is desired; must not be
     *        {@code null}.
     * 
     * @return The alternate point; never {@code null}.
     */
    /* @NonNull */
    private static Point getAlternatePoint(
        /* @NonNull */
        final Point point )
    {
        assert point != null;

        return new Point( point.x + 1000, point.y + 1000 );
    }

    /**
     * Gets the component under test in the fixture.
     * 
     * @return The component under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ComponentType getComponent()
    {
        assertNotNull( component_ );
        return component_;
    }

    /**
     * Gets the table environment for use in the fixture.
     * 
     * @return The table environment for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final TableEnvironmentType getTableEnvironment()
    {
        assertNotNull( tableEnvironment_ );
        return tableEnvironment_;
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final IComponent component = (IComponent)mementoOriginator;
        component.setLocation( getAlternatePoint( component.getLocation() ) );
        component.setOrientation( getAlternateOrientation( component.getOrientation(), component.getSupportedOrientations() ) );
        component.setOrigin( getAlternatePoint( component.getOrigin() ) );
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        tableEnvironment_ = createTableEnvironment();
        assertNotNull( tableEnvironment_ );
        component_ = createComponent( tableEnvironment_ );
        assertNotNull( component_ );

        super.setUp();
    }

    /**
     * Ensures the {@code addComponentListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponentListener_Listener_Null()
    {
        component_.addComponentListener( null );
    }

    /**
     * Ensures the {@code addComponentListener} method throws an exception when
     * passed a listener that is present in the component listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentListener_Listener_Present()
    {
        final IComponentListener listener = EasyMock.createMock( IComponentListener.class );
        component_.addComponentListener( listener );

        component_.addComponentListener( listener );
    }

    /**
     * Ensures the component bounds changed event catches any exception thrown
     * by the {@code componentBoundsChanged} method of a component listener.
     */
    @Test
    public void testComponentBoundsChanged_CatchesListenerException()
    {
        final IComponentListener listener1 = mocksControl_.createMock( IComponentListener.class );
        listener1.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentListener listener2 = mocksControl_.createMock( IComponentListener.class );
        listener2.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener1 );
        component_.addComponentListener( listener2 );

        fireComponentBoundsChanged( component_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the component orientation changed event catches any exception
     * thrown by the {@code componentOrientationChanged} method of a component
     * listener.
     */
    @Test
    public void testComponentOrientationChanged_CatchesListenerException()
    {
        final IComponentListener listener1 = mocksControl_.createMock( IComponentListener.class );
        listener1.componentOrientationChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentListener listener2 = mocksControl_.createMock( IComponentListener.class );
        listener2.componentOrientationChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener1 );
        component_.addComponentListener( listener2 );

        fireComponentOrientationChanged( component_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the component surface designs changed event catches any exception
     * thrown by the {@code componentSurfaceDesignChanged} method of a component
     * listener.
     */
    @Test
    public void testComponentSurfaceDesignChanged_CatchesListenerException()
    {
        final IComponentListener listener1 = mocksControl_.createMock( IComponentListener.class );
        listener1.componentSurfaceDesignChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentListener listener2 = mocksControl_.createMock( IComponentListener.class );
        listener2.componentSurfaceDesignChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener1 );
        component_.addComponentListener( listener2 );

        fireComponentSurfaceDesignChanged( component_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getBounds} method returns the bounds of the surface
     * associated with the current orientation.
     */
    @Test
    public void testGetBounds_MatchesCurrentOrientationSurface()
    {
        int length = 0;
        for( final ComponentOrientation orientation : component_.getSupportedOrientations() )
        {
            length += 10;
            component_.setSurfaceDesign( orientation, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign( length, length ) );
            component_.setOrientation( orientation );
            final Rectangle bounds = component_.getBounds();
            assertEquals( length, bounds.height );
            assertEquals( length, bounds.width );
        }
    }

    /**
     * Ensures the {@code getBounds} method returns a copy of the bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = component_.getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, component_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method does not return {@code null}.
     */
    @Test
    public void testGetBounds_ReturnValue_NonNull()
    {
        assertNotNull( component_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetBounds_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Rectangle expectedBounds = component_.getBounds();
        expectedBounds.setLocation( expectedLocation );
        component_.setLocation( expectedLocation );

        final Rectangle actualBounds = component_.getBounds();

        assertEquals( expectedBounds, actualBounds );
    }

    /**
     * Ensures the {@code getLocation} method returns a copy of the location.
     */
    @Test
    public void testGetLocation_ReturnValue_Copy()
    {
        final Point location = component_.getLocation();
        final Point expectedLocation = new Point( location );

        location.setLocation( 1010, 2020 );

        assertEquals( expectedLocation, component_.getLocation() );
    }

    /**
     * Ensures the {@code getLocation} method does not return {@code null}.
     */
    @Test
    public void testGetLocation_ReturnValue_NonNull()
    {
        assertNotNull( component_.getLocation() );
    }

    /**
     * Ensures the {@code getLocation} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetLocation_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        component_.setLocation( expectedLocation );

        final Point actualLocation = component_.getLocation();

        assertEquals( expectedLocation, actualLocation );
    }

    /**
     * Ensures the {@code getOrientation} method does not return {@code null}.
     */
    @Test
    public void testGetOrientation_ReturnValue_NonNull()
    {
        assertNotNull( component_.getOrientation() );
    }

    /**
     * Ensures the {@code getOrigin} method returns a copy of the origin.
     */
    @Test
    public void testGetOrigin_ReturnValue_Copy()
    {
        final Point origin = component_.getOrigin();
        final Point expectedOrigin = new Point( origin );

        origin.setLocation( 1010, 2020 );

        assertEquals( expectedOrigin, component_.getOrigin() );
    }

    /**
     * Ensures the {@code getOrigin} method does not return {@code null}.
     */
    @Test
    public void testGetOrigin_ReturnValue_NonNull()
    {
        assertNotNull( component_.getOrigin() );
    }

    /**
     * Ensures the {@code getOrigin} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetOrigin_Translate()
    {
        final Point expectedOrigin = new Point( 1010, 2020 );
        component_.setOrigin( expectedOrigin );

        final Point actualOrigin = component_.getOrigin();

        assertEquals( expectedOrigin, actualOrigin );
    }

    /**
     * Ensures the {@code getPath} method returns the correct value when the
     * component is associated with a table.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPath_AssociatedTable()
        throws Exception
    {
        final ITable table = tableEnvironment_.createTable();
        final IContainer container = tableEnvironment_.createCardPile();
        table.getTabletop().addComponent( container );
        final IComponent component1 = createComponent( tableEnvironment_ );
        container.addComponent( component1 );
        final IComponent component2 = createComponent( tableEnvironment_ );
        container.addComponent( component2 );
        final IComponent component3 = createComponent( tableEnvironment_ );
        container.addComponent( component3 );
        final ComponentPath expectedTabletopPath = new ComponentPath( null, 0 );
        final ComponentPath expectedContainerPath = new ComponentPath( expectedTabletopPath, 0 );
        final ComponentPath expectedComponentPath1 = new ComponentPath( expectedContainerPath, 0 );
        final ComponentPath expectedComponentPath2 = new ComponentPath( expectedContainerPath, 1 );
        final ComponentPath expectedComponentPath3 = new ComponentPath( expectedContainerPath, 2 );

        final ComponentPath actualTabletopPath = table.getTabletop().getPath();
        final ComponentPath actualContainerPath = container.getPath();
        final ComponentPath actualComponentPath1 = component1.getPath();
        final ComponentPath actualComponentPath2 = component2.getPath();
        final ComponentPath actualComponentPath3 = component3.getPath();

        assertEquals( expectedTabletopPath, actualTabletopPath );
        assertEquals( expectedContainerPath, actualContainerPath );
        assertEquals( expectedComponentPath1, actualComponentPath1 );
        assertEquals( expectedComponentPath2, actualComponentPath2 );
        assertEquals( expectedComponentPath3, actualComponentPath3 );
    }

    /**
     * Ensures the {@code getPath} method returns {@code null} when the
     * component is not associated with a table.
     */
    @Test
    public void testGetPath_NoAssociatedTable()
    {
        assertNull( component_.getTable() );
        assertNull( component_.getPath() );
    }

    /**
     * Ensures the {@code getSize} method returns the size of the surface
     * associated with the current orientation.
     */
    @Test
    public void testGetSize_MatchesCurrentOrientationSurface()
    {
        int length = 0;
        for( final ComponentOrientation orientation : component_.getSupportedOrientations() )
        {
            length += 10;
            component_.setSurfaceDesign( orientation, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign( length, length ) );
            component_.setOrientation( orientation );
            final Dimension size = component_.getSize();
            assertEquals( length, size.height );
            assertEquals( length, size.width );
        }
    }

    /**
     * Ensures the {@code getSize} method returns a copy of the size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final Dimension size = component_.getSize();
        final Dimension expectedSize = new Dimension( size );

        size.setSize( 101, 202 );

        assertEquals( expectedSize, component_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method does not return {@code null}.
     */
    @Test
    public void testGetSize_ReturnValue_NonNull()
    {
        assertNotNull( component_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method returns the correct value after a
     * translation.
     */
    @Test
    public void testGetSize_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Dimension expectedSize = component_.getSize();
        component_.setLocation( expectedLocation );

        final Dimension actualSize = component_.getSize();

        assertEquals( expectedSize, actualSize );
    }

    /**
     * Ensures the {@code getSupportedOrientations} method returns an immutable
     * collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_Immutable()
    {
        assertImmutableCollection( component_.getSupportedOrientations() );
    }

    /**
     * Ensures the {@code getSupportedOrientations} method does not return an
     * empty collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_NonEmpty()
    {
        assertFalse( component_.getSupportedOrientations().isEmpty() );
    }

    /**
     * Ensures the {@code getSupportedOrientations} method does not return
     * {@code null}.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_NonNull()
    {
        assertNotNull( component_.getSupportedOrientations() );
    }

    /**
     * Ensures the {@code getSurfaceDesign} method throws an exception when
     * passed an illegal orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetSurfaceDesign_Orientation_Illegal()
    {
        component_.getSurfaceDesign( createIllegalOrientation() );
    }

    /**
     * Ensures the {@code getSurfaceDesign} method throws an exception when
     * passed a {@code null} orientation.
     */
    @Test( expected = NullPointerException.class )
    public void testGetSurfaceDesign_Orientation_Null()
    {
        component_.getSurfaceDesign( null );
    }

    /**
     * Ensures the {@code getSurfaceDesign} method does not return {@code null}.
     */
    @Test
    public void testGetSurfaceDesign_ReturnValue_NonNull()
    {
        for( final ComponentOrientation orientation : component_.getSupportedOrientations() )
        {
            assertNotNull( component_.getSurfaceDesign( orientation ) );
        }
    }

    /**
     * Ensures the {@code getTableEnvironment} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTableEnvironment_ReturnValue_NonNull()
    {
        assertNotNull( component_.getTableEnvironment() );
    }

    /**
     * Ensures the {@code removeComponentListener} method throws an exception
     * when passed a listener that is absent from the component listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponentListener_Listener_Absent()
    {
        component_.removeComponentListener( EasyMock.createMock( IComponentListener.class ) );
    }

    /**
     * Ensures the {@code removeComponentListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponentListener_Listener_Null()
    {
        component_.removeComponentListener( null );
    }

    /**
     * Ensures the {@code removeComponentListener} removes a listener that is
     * present in the component listener collection.
     */
    @Test
    public void testRemoveComponentListener_Listener_Present()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentOrientationChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener );
        fireComponentOrientationChanged( component_ );

        component_.removeComponentListener( listener );
        fireComponentOrientationChanged( component_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLocation} method fires a component bounds changed
     * event.
     */
    @Test
    public void testSetLocation_FiresComponentBoundsChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener );

        component_.setLocation( new Point( 1010, 2020 ) );

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

        component_.setLocation( location );
        location.setLocation( 1, 2 );

        assertEquals( expectedLocation, component_.getLocation() );
    }

    /**
     * Ensures the {@code setLocation} method throws an exception when passed a
     * {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLocation_Location_Null()
    {
        component_.setLocation( null );
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
        component_.addComponentListener( listener );

        component_.setOrientation( component_.getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setOrientation} method throws an exception when passed
     * an illegal orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetOrientation_Orientation_Illegal()
    {
        component_.setOrientation( createIllegalOrientation() );
    }

    /**
     * Ensures the {@code setOrientation} method throws an exception when passed
     * a {@code null} orientation.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOrientation_Orientation_Null()
    {
        component_.setOrientation( null );
    }

    /**
     * Ensures the {@code setOrigin} method fires a component bounds changed
     * event.
     */
    @Test
    public void testSetOrigin_FiresComponentBoundsChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener );

        component_.setOrigin( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setOrigin} method makes a copy of the origin.
     */
    @Test
    public void testSetOrigin_Origin_Copy()
    {
        final Point expectedOrigin = new Point( 1010, 2020 );
        final Point origin = new Point( expectedOrigin );

        component_.setOrigin( origin );
        origin.setLocation( 1, 2 );

        assertEquals( expectedOrigin, component_.getOrigin() );
    }

    /**
     * Ensures the {@code setOrigin} method throws an exception when passed a
     * {@code null} origin.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOrigin_Origin_Null()
    {
        component_.setOrigin( null );
    }

    /**
     * Ensures the {@code setSurfaceDesign} method fires a component surface
     * design changed event.
     */
    @Test
    public void testSetSurfaceDesign_FiresComponentSurfaceDesignChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component_.addComponentListener( listener );

        component_.setSurfaceDesign( component_.getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setSurfaceDesign} method throws an exception when
     * passed an illegal orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSurfaceDesign_Orientation_Illegal()
    {
        component_.setSurfaceDesign( createIllegalOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
    }

    /**
     * Ensures the {@code setSurfaceDesign} method throws an exception when
     * passed a {@code null} orientation.
     */
    @Test( expected = NullPointerException.class )
    public void testSetSurfaceDesign_Orientation_Null()
    {
        component_.setSurfaceDesign( null, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
    }

    /**
     * Ensures the {@code setSurfaceDesign} method throws an exception when
     * passed a {@code null} surface design.
     */
    @Test( expected = NullPointerException.class )
    public void testSetSurfaceDesign_SurfaceDesign_Null()
    {
        component_.setSurfaceDesign( component_.getOrientation(), null );
    }
}
