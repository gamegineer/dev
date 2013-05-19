/*
 * AbstractComponentModelTestCase.java
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
 * Created on Dec 25, 2009 at 11:02:59 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.TestComponentSurfaceDesigns;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentModel} class and any
 * classes that extend it.
 * 
 * @param <T>
 *        The type of the component model.
 */
public abstract class AbstractComponentModelTestCase<T extends ComponentModel>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model under test in the fixture. */
    private T componentModel_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentModelTestCase}
     * class.
     */
    protected AbstractComponentModelTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component model to be tested.
     * 
     * @return The component model to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createComponentModel()
        throws Exception;

    /**
     * Fires a component bounds changed event for the fixture component model.
     */
    private void fireComponentBoundsChangedEvent()
    {
        componentModel_.fireComponentBoundsChanged();
    }

    /**
     * Fires a component changed event for the fixture component model.
     */
    private void fireComponentChangedEvent()
    {
        componentModel_.fireComponentChanged();
    }

    /**
     * Fires a component model focus changed event for the fixture component
     * model.
     */
    private void fireComponentModelFocusChangedEvent()
    {
        componentModel_.fireComponentModelFocusChanged();
    }

    /**
     * Fires a component model hover changed event for the fixture component
     * model.
     */
    private void fireComponentModelHoverChangedEvent()
    {
        componentModel_.fireComponentModelHoverChanged();
    }

    /**
     * Fires a component orientation changed event for the fixture component
     * model.
     */
    private void fireComponentOrientationChangedEvent()
    {
        componentModel_.fireComponentOrientationChanged();
    }

    /**
     * Fires a component surface design changed event for the fixture component
     * model.
     */
    private void fireComponentSurfaceDesignChangedEvent()
    {
        componentModel_.fireComponentSurfaceDesignChanged();
    }

    /**
     * Gets the component model under test in the fixture.
     * 
     * @return The component model under test in the fixture; never {@code null}
     *         .
     */
    /* @NonNull */
    protected final T getComponentModel()
    {
        assertNotNull( componentModel_ );
        return componentModel_;
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
        componentModel_ = createComponentModel();
        assertNotNull( componentModel_ );
        componentModel_.initialize( new TableModel() );
    }

    /**
     * Ensures the {@link ComponentModel#addComponentModelListener} method adds
     * a listener that is absent from the component model listener collection.
     */
    @Test
    public void testAddComponentModelListener_Listener_Absent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();

        fireComponentChangedEvent();
        componentModel_.addComponentModelListener( listener );
        fireComponentChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ComponentModel#addComponentModelListener} method
     * throws an exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponentModelListener_Listener_Null()
    {
        componentModel_.addComponentModelListener( null );
    }

    /**
     * Ensures the {@link ComponentModel#addComponentModelListener} method
     * throws an exception when passed a listener that is present in the
     * component model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentModelListener_Listener_Present()
    {
        final IComponentModelListener listener = EasyMock.createMock( IComponentModelListener.class );
        componentModel_.addComponentModelListener( listener );

        componentModel_.addComponentModelListener( listener );
    }

    /**
     * Ensures a change to the underlying component bounds fires a component
     * bounds changed event and a component changed event.
     */
    @Test
    public void testComponent_BoundsChanged_FiresComponentBoundsChangedEventAndComponentChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        final Point location = componentModel_.getComponent().getLocation();
        location.translate( 1, 1 );
        componentModel_.getComponent().setLocation( location );

        mocksControl_.verify();
    }

    /**
     * Ensures a change to the underlying component orientation fires a
     * component orientation changed event and a component changed event.
     */
    @Test
    public void testComponent_OrientationChanged_FiresComponentOrientationChangedEventAndComponentChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentOrientationChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.getComponent().setOrientation( componentModel_.getComponent().getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures a change to the underlying component surface design fires a
     * component surface design changed event and a component changed event.
     */
    @Test
    public void testComponent_SurfaceDesignChanged_FiresComponentSurfaceDesignChangedEventAndComponentChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.getComponent().setSurfaceDesign( componentModel_.getComponent().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component bounds changed event catches any exception thrown
     * by the {@link IComponentModelListener#componentBoundsChanged} method of a
     * component model listener.
     */
    @Test
    public void testComponentBoundsChanged_CatchesListenerException()
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentBoundsChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentBoundsChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener1 );
        componentModel_.addComponentModelListener( listener2 );

        fireComponentBoundsChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the component changed event catches any exception thrown by the
     * {@link IComponentModelListener#componentChanged} method of a component
     * model listener.
     */
    @Test
    public void testComponentChanged_CatchesListenerException()
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener1 );
        componentModel_.addComponentModelListener( listener2 );

        fireComponentChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the component model focus changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentModelFocusChanged}
     * method of a component model listener.
     */
    @Test
    public void testComponentModelFocusChanged_CatchesListenerException()
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener1 );
        componentModel_.addComponentModelListener( listener2 );

        fireComponentModelFocusChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the component model hover changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentModelHoverChanged}
     * method of a component model listener.
     */
    @Test
    public void testComponentModelHoverChanged_CatchesListenerException()
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener1 );
        componentModel_.addComponentModelListener( listener2 );

        fireComponentModelHoverChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the component orientation changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentOrientationChanged}
     * method of a component model listener.
     */
    @Test
    public void testComponentOrientationChanged_CatchesListenerException()
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentOrientationChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentOrientationChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener1 );
        componentModel_.addComponentModelListener( listener2 );

        fireComponentOrientationChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the component surface design changed event catches any exception
     * thrown by the
     * {@link IComponentModelListener#componentSurfaceDesignChanged} method of a
     * component model listener.
     */
    @Test
    public void testComponentSurfaceDesignChanged_CatchesListenerException()
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentSurfaceDesignChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentSurfaceDesignChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener1 );
        componentModel_.addComponentModelListener( listener2 );

        fireComponentSurfaceDesignChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ComponentModel#getComponent} method does not return
     * {@code null}.
     */
    @Test
    public void testGetComponent_ReturnValue_NonNull()
    {
        assertNotNull( componentModel_.getComponent() );
    }

    /**
     * Ensures the {@link ComponentModel#removeComponentModelListener} method
     * throws an exception when passed a listener that is absent from the
     * component model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponentModelListener_Listener_Absent()
    {
        componentModel_.removeComponentModelListener( EasyMock.createMock( IComponentModelListener.class ) );
    }

    /**
     * Ensures the {@link ComponentModel#removeComponentModelListener} method
     * throws an exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponentModelListener_Listener_Null()
    {
        componentModel_.removeComponentModelListener( null );
    }

    /**
     * Ensures the {@link ComponentModel#removeComponentModelListener} removes a
     * listener that is present in the component model listener collection.
     */
    @Test
    public void testRemoveComponentModelListener_Listener_Present()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        fireComponentChangedEvent();
        componentModel_.removeComponentModelListener( listener );
        fireComponentChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setFocused} method fires a component
     * model focus changed event after the component model gained the focus.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresComponentModelFocusChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.setFocused( true );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setFocused} method fires a component
     * model focus changed event after the component model lost the focus.
     */
    @Test
    public void testSetFocused_LostFocus_FiresComponentModelFocusChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.setFocused( false );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setHover} method fires a component
     * model hover changed event after the component model gained the hover.
     */
    @Test
    public void testSetHover_GainedHover_FiresComponentModelHoverChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.setHover( true );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setHover} method fires a component
     * model hover changed event after the component model lost the hover.
     */
    @Test
    public void testSetHover_LostHover_FiresComponentModelHoverChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.setHover( false );

        mocksControl_.verify();
    }
}
