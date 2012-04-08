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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponent} interface.
 * 
 * @param <T>
 *        The type of the component.
 */
public abstract class AbstractComponentTestCase<T extends IComponent>
    extends AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component under test in the fixture. */
    private T component_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table for use in the fixture. */
    private ITable table_;


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
     * Creates the component to be tested.
     * 
     * @param table
     *        The fixture table; must not be {@code null}.
     * 
     * @return The component to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    /* @NonNull */
    protected abstract T createComponent(
        /* @NonNull */
        final ITable table )
        throws Exception;

    /**
     * Creates the table for use in the fixture.
     * 
     * @return The table for use in the fixture; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITable createTable()
        throws Exception;

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
        T component );

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
        T component );

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
        T component );

    /**
     * Gets the component under test in the fixture.
     * 
     * @return The component under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getComponent()
    {
        assertNotNull( component_ );
        return component_;
    }

    /**
     * Gets the table for use in the fixture.
     * 
     * @return The table for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ITable getTable()
    {
        assertNotNull( table_ );
        return table_;
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
        table_ = createTable();
        assertNotNull( table_ );
        component_ = createComponent( table_ );
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
}
