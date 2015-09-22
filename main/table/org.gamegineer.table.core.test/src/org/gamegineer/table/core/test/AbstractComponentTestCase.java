/*
 * AbstractComponentTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.core.test;

import static org.gamegineer.table.core.test.Assert.assertComponentEquals;
import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IComponent} interface.
 * 
 * @param <TableEnvironmentType>
 *        The type of the table environment.
 * @param <ComponentType>
 *        The type of the component.
 */
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
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
     * the specification of the {@link Assert#assertComponentEquals} method.
     * 
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#assertMementoOriginatorEquals(org.gamegineer.common.core.util.memento.IMementoOriginator,
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
     */
    protected abstract ComponentType createComponent(
        TableEnvironmentType tableEnvironment )
        throws Exception;

    /**
     * Creates a component orientation that is guaranteed to be illegal for all
     * components.
     * 
     * @return An illegal component orientation; never {@code null}.
     */
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
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected final IMementoOriginator createMementoOriginator()
        throws Exception
    {
        return createComponent( getTableEnvironment() );
    }

    /**
     * Creates the table environment for use in the fixture.
     * 
     * @return The table environment for use in the fixture; never {@code null}.
     */
    protected final TableEnvironmentType createTableEnvironment()
    {
        return createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
    }

    /**
     * Creates the table environment for use in the fixture using the specified
     * context.
     * 
     * @param context
     *        The table environment context; must not be {@code null}.
     * 
     * @return The table environment for use in the fixture; never {@code null}.
     */
    protected abstract TableEnvironmentType createTableEnvironment(
        final ITableEnvironmentContext context );

    /**
     * Fires a component bounds changed event for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    protected abstract void fireComponentBoundsChanged(
        ComponentType component );

    /**
     * Fires a component orientation changed event for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    protected abstract void fireComponentOrientationChanged(
        ComponentType component );

    /**
     * Fires a component surface design changed event for the specified
     * component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    protected abstract void fireComponentSurfaceDesignChanged(
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
    private static ComponentOrientation getAlternateOrientation(
        final ComponentOrientation orientation,
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
    private static Point getAlternatePoint(
        final Point point )
    {
        return new Point( point.x + 1000, point.y + 1000 );
    }

    /**
     * Gets the component under test in the fixture.
     * 
     * @return The component under test in the fixture; never {@code null}.
     */
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
    protected final TableEnvironmentType getTableEnvironment()
    {
        assertNotNull( tableEnvironment_ );
        return tableEnvironment_;
    }

    /*
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
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
            component.setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }
    }

    /*
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#setUp()
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
     * Ensures the {@link IComponent#addComponentListener} method throws an
     * exception when passed a listener that is present in the component
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentListener_Listener_Present()
    {
        final IComponentListener listener = EasyMock.createMock( IComponentListener.class );
        getComponent().addComponentListener( listener );

        getComponent().addComponentListener( listener );
    }

    /**
     * Ensures the component bounds changed event catches any exception thrown
     * by the {@link IComponentListener#componentBoundsChanged} method of a
     * component listener.
     */
    @Test
    public void testComponentBoundsChanged_CatchesListenerException()
    {
        final IComponentListener listener1 = mocksControl_.createMock( IComponentListener.class );
        listener1.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentListener listener2 = mocksControl_.createMock( IComponentListener.class );
        listener2.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener1 );
        getComponent().addComponentListener( listener2 );

        fireComponentBoundsChanged( getComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component orientation changed event catches any exception
     * thrown by the {@link IComponentListener#componentOrientationChanged}
     * method of a component listener.
     */
    @Test
    public void testComponentOrientationChanged_CatchesListenerException()
    {
        final IComponentListener listener1 = mocksControl_.createMock( IComponentListener.class );
        listener1.componentOrientationChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentListener listener2 = mocksControl_.createMock( IComponentListener.class );
        listener2.componentOrientationChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener1 );
        getComponent().addComponentListener( listener2 );

        fireComponentOrientationChanged( getComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component surface designs changed event catches any exception
     * thrown by the {@link IComponentListener#componentSurfaceDesignChanged}
     * method of a component listener.
     */
    @Test
    public void testComponentSurfaceDesignChanged_CatchesListenerException()
    {
        final IComponentListener listener1 = mocksControl_.createMock( IComponentListener.class );
        listener1.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentListener listener2 = mocksControl_.createMock( IComponentListener.class );
        listener2.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener1 );
        getComponent().addComponentListener( listener2 );

        fireComponentSurfaceDesignChanged( getComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#getBounds} method returns the bounds of the
     * surface associated with the current orientation.
     */
    @Test
    public void testGetBounds_MatchesCurrentOrientationSurface()
    {
        int length = 0;
        for( final ComponentOrientation orientation : getComponent().getSupportedOrientations() )
        {
            length += 10;
            getComponent().setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign( length, length ) );
            getComponent().setOrientation( orientation );
            final Rectangle bounds = getComponent().getBounds();
            assertEquals( length, bounds.height );
            assertEquals( length, bounds.width );
        }
    }

    /**
     * Ensures the {@link IComponent#getBounds} method returns a copy of the
     * bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = getComponent().getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, getComponent().getBounds() );
    }

    /**
     * Ensures the {@link IComponent#getBounds} method returns the correct value
     * after a translation.
     */
    @Test
    public void testGetBounds_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Rectangle expectedBounds = getComponent().getBounds();
        expectedBounds.setLocation( expectedLocation );
        getComponent().setLocation( expectedLocation );

        final Rectangle actualBounds = getComponent().getBounds();

        assertEquals( expectedBounds, actualBounds );
    }

    /**
     * Ensures the {@link IComponent#getLocation} method returns a copy of the
     * location.
     */
    @Test
    public void testGetLocation_ReturnValue_Copy()
    {
        final Point location = getComponent().getLocation();
        final Point expectedLocation = new Point( location );

        location.setLocation( 1010, 2020 );

        assertEquals( expectedLocation, getComponent().getLocation() );
    }

    /**
     * Ensures the {@link IComponent#getLocation} method returns the correct
     * value after a translation.
     */
    @Test
    public void testGetLocation_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        getComponent().setLocation( expectedLocation );

        final Point actualLocation = getComponent().getLocation();

        assertEquals( expectedLocation, actualLocation );
    }

    /**
     * Ensures the {@link IComponent#getOrigin} method returns a copy of the
     * origin.
     */
    @Test
    public void testGetOrigin_ReturnValue_Copy()
    {
        final Point origin = getComponent().getOrigin();
        final Point expectedOrigin = new Point( origin );

        origin.setLocation( 1010, 2020 );

        assertEquals( expectedOrigin, getComponent().getOrigin() );
    }

    /**
     * Ensures the {@link IComponent#getOrigin} method returns the correct value
     * after a translation.
     */
    @Test
    public void testGetOrigin_Translate()
    {
        final Point expectedOrigin = new Point( 1010, 2020 );
        getComponent().setOrigin( expectedOrigin );

        final Point actualOrigin = getComponent().getOrigin();

        assertEquals( expectedOrigin, actualOrigin );
    }

    /**
     * Ensures the {@link IComponent#getPath} method returns the correct value
     * when the component is associated with a table.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPath_AssociatedTable()
        throws Exception
    {
        final ITable table = getTableEnvironment().createTable();
        final IContainer container = TestComponents.createUniqueContainer( getTableEnvironment() );
        table.getTabletop().addComponent( container );
        final IComponent component1 = createComponent( getTableEnvironment() );
        container.addComponent( component1 );
        final IComponent component2 = createComponent( getTableEnvironment() );
        container.addComponent( component2 );
        final IComponent component3 = createComponent( getTableEnvironment() );
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
     * Ensures the {@link IComponent#getPath} method returns {@code null} when
     * the component is not associated with a table and has no parent.
     */
    @Test
    public void testGetPath_NoAssociatedTable_NoParent()
    {
        assertNull( getComponent().getTable() );
        assertNull( getComponent().getPath() );
    }

    /**
     * Ensures the {@link IComponent#getPath} method returns {@code null} when
     * the component is not associated with a table but has a parent.
     */
    @Test
    public void testGetPath_NoAssociatedTable_WithParent()
    {
        final IContainer container = TestComponents.createUniqueContainer( getTableEnvironment() );
        container.addComponent( getComponent() );

        assertNull( getComponent().getTable() );
        assertNull( getComponent().getPath() );
    }

    /**
     * Ensures the {@link IComponent#getSize} method returns the size of the
     * surface associated with the current orientation.
     */
    @Test
    public void testGetSize_MatchesCurrentOrientationSurface()
    {
        int length = 0;
        for( final ComponentOrientation orientation : getComponent().getSupportedOrientations() )
        {
            length += 10;
            getComponent().setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign( length, length ) );
            getComponent().setOrientation( orientation );
            final Dimension size = getComponent().getSize();
            assertEquals( length, size.height );
            assertEquals( length, size.width );
        }
    }

    /**
     * Ensures the {@link IComponent#getSize} method returns a copy of the size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final Dimension size = getComponent().getSize();
        final Dimension expectedSize = new Dimension( size );

        size.setSize( 101, 202 );

        assertEquals( expectedSize, getComponent().getSize() );
    }

    /**
     * Ensures the {@link IComponent#getSize} method returns the correct value
     * after a translation.
     */
    @Test
    public void testGetSize_Translate()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Dimension expectedSize = getComponent().getSize();
        getComponent().setLocation( expectedLocation );

        final Dimension actualSize = getComponent().getSize();

        assertEquals( expectedSize, actualSize );
    }

    /**
     * Ensures the {@link IComponent#getSupportedOrientations} method returns an
     * immutable collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_Immutable()
    {
        assertImmutableCollection( getComponent().getSupportedOrientations(), getComponent().getOrientation() );
    }

    /**
     * Ensures the {@link IComponent#getSupportedOrientations} method does not
     * return an empty collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_NonEmpty()
    {
        assertFalse( getComponent().getSupportedOrientations().isEmpty() );
    }

    /**
     * Ensures the {@link IComponent#getSurfaceDesign} method throws an
     * exception when passed an illegal orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetSurfaceDesign_Orientation_Illegal()
    {
        getComponent().getSurfaceDesign( createIllegalOrientation() );
    }

    /**
     * Ensures the {@link IComponent#getSurfaceDesigns} method returns a copy of
     * the surface designs collection.
     */
    @Test
    public void testGetSurfaceDesigns_ReturnValue_Copy()
    {
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = getComponent().getSurfaceDesigns();
        final Map<ComponentOrientation, ComponentSurfaceDesign> expectedSurfaceDesigns = new IdentityHashMap<>( surfaceDesigns );

        surfaceDesigns.put( createIllegalOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        assertEquals( expectedSurfaceDesigns, getComponent().getSurfaceDesigns() );
    }

    /**
     * Ensures the {@link IComponent#getSurfaceDesigns} method returns a
     * collection whose keys equal the supported orientations collection.
     */
    @Test
    public void testGetSurfaceDesigns_ReturnValue_Keys_SupportedOrientations()
    {
        final Set<ComponentOrientation> expectedValue = new HashSet<>( getComponent().getSupportedOrientations() );

        final Set<ComponentOrientation> actualValue = getComponent().getSurfaceDesigns().keySet();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link IComponent#getSurfaceDesigns} method returns a
     * collection whose values are not {@code null}.
     */
    @Test
    public void testGetSurfaceDesigns_ReturnValue_Values_NonNull()
    {
        for( final ComponentSurfaceDesign surfaceDesign : getComponent().getSurfaceDesigns().values() )
        {
            assertNotNull( surfaceDesign );
        }
    }

    /**
     * Ensures the {@link IComponent#removeComponentListener} method throws an
     * exception when passed a listener that is absent from the component
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponentListener_Listener_Absent()
    {
        getComponent().removeComponentListener( EasyMock.createMock( IComponentListener.class ) );
    }

    /**
     * Ensures the {@link IComponent#removeComponentListener} removes a listener
     * that is present in the component listener collection.
     */
    @Test
    public void testRemoveComponentListener_Listener_Present()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentOrientationChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener );
        fireComponentOrientationChanged( getComponent() );

        getComponent().removeComponentListener( listener );
        fireComponentOrientationChanged( getComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#setLocation} method fires a component
     * bounds changed event.
     */
    @Test
    public void testSetLocation_FiresComponentBoundsChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener );

        getComponent().setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#setLocation} method makes a copy of the
     * location.
     */
    @Test
    public void testSetLocation_Location_Copy()
    {
        final Point expectedLocation = new Point( 1010, 2020 );
        final Point location = new Point( expectedLocation );

        getComponent().setLocation( location );
        location.setLocation( 1, 2 );

        assertEquals( expectedLocation, getComponent().getLocation() );
    }

    /**
     * Ensures the {@link IComponent#setOrientation} method fires a component
     * orientation changed event.
     */
    @Test
    public void testSetOrientation_FiresComponentOrientationChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentOrientationChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener );

        getComponent().setOrientation( getComponent().getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#setOrientation} method throws an exception
     * when passed an illegal orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetOrientation_Orientation_Illegal()
    {
        getComponent().setOrientation( createIllegalOrientation() );
    }

    /**
     * Ensures the {@link IComponent#setOrigin} method fires a component bounds
     * changed event.
     */
    @Test
    public void testSetOrigin_FiresComponentBoundsChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener );

        getComponent().setOrigin( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#setOrigin} method makes a copy of the
     * origin.
     */
    @Test
    public void testSetOrigin_Origin_Copy()
    {
        final Point expectedOrigin = new Point( 1010, 2020 );
        final Point origin = new Point( expectedOrigin );

        getComponent().setOrigin( origin );
        origin.setLocation( 1, 2 );

        assertEquals( expectedOrigin, getComponent().getOrigin() );
    }

    /**
     * Ensures the {@link IComponent#setSurfaceDesign} method fires a component
     * surface design changed event.
     */
    @Test
    public void testSetSurfaceDesign_FiresComponentSurfaceDesignChangedEvent()
    {
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener );

        getComponent().setSurfaceDesign( getComponent().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#setSurfaceDesign} method throws an
     * exception when passed an illegal orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSurfaceDesign_Orientation_Illegal()
    {
        getComponent().setSurfaceDesign( createIllegalOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
    }

    /**
     * Ensures the {@link IComponent#setSurfaceDesigns} method fires a component
     * surface design changed event for each entry in the surface designs
     * collection.
     */
    @Test
    public void testSetSurfaceDesigns_FiresComponentSurfaceDesignChangedEvent()
    {
        final Collection<ComponentOrientation> orientations = getComponent().getSupportedOrientations();
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<>( orientations.size() );
        for( final ComponentOrientation orientation : orientations )
        {
            surfaceDesigns.put( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( orientations.size() );
        mocksControl_.replay();
        getComponent().addComponentListener( listener );

        getComponent().setSurfaceDesigns( surfaceDesigns );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IComponent#setSurfaceDesigns} method does not throw an
     * exception when passed an empty surface designs collection.
     */
    @Test
    public void testSetSurfaceDesigns_SurfaceDesigns_Empty()
    {
        getComponent().setSurfaceDesigns( Collections.<@NonNull ComponentOrientation, @NonNull ComponentSurfaceDesign>emptyMap() );
    }

    /**
     * Ensures the {@link IComponent#setSurfaceDesigns} method throws an
     * exception when passed a surface designs collection that contains an
     * unsupported orientation.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSurfaceDesigns_SurfaceDesigns_Illegal_ContainsUnsupportedOrientation()
    {
        getComponent().setSurfaceDesigns( Collections.singletonMap( createIllegalOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() ) );
    }
}
