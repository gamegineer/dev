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
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
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
    }

    /**
     * Ensures the {@code addComponentModelListener} method adds a listener that
     * is absent from the component model listener collection.
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
     * Ensures the {@code addComponentModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponentModelListener_Listener_Null()
    {
        componentModel_.addComponentModelListener( null );
    }

    /**
     * Ensures the {@code addComponentModelListener} method throws an exception
     * when passed a listener that is present in the component model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentModelListener_Listener_Present()
    {
        final IComponentModelListener listener = EasyMock.createMock( IComponentModelListener.class );
        componentModel_.addComponentModelListener( listener );

        componentModel_.addComponentModelListener( listener );
    }

    /**
     * Ensures a change to the underlying component state fires a component
     * changed event.
     */
    @Test
    public void testComponent_StateChanged_FiresComponentChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        componentModel_.getComponent().setOrientation( componentModel_.getComponent().getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component changed event catches any exception thrown by the
     * {@code componentChanged} method of a component model listener.
     */
    @Test
    public void testComponentChanged_CatchesListenerException()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        fireComponentChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the component model focus changed event catches any exception
     * thrown by the {@code componentModelFocusChanged} method of a component
     * model listener.
     */
    @Test
    public void testComponentModelFocusChanged_CatchesListenerException()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        componentModel_.addComponentModelListener( listener );

        fireComponentModelFocusChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getComponent} method does not return {@code null}.
     */
    @Test
    public void testGetComponent_ReturnValue_NonNull()
    {
        assertNotNull( componentModel_.getComponent() );
    }

    /**
     * Ensures the {@code removeComponentModelListener} method throws an
     * exception when passed a listener that is absent from the component model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponentModelListener_Listener_Absent()
    {
        componentModel_.removeComponentModelListener( EasyMock.createMock( IComponentModelListener.class ) );
    }

    /**
     * Ensures the {@code removeComponentModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponentModelListener_Listener_Null()
    {
        componentModel_.removeComponentModelListener( null );
    }

    /**
     * Ensures the {@code removeComponentModelListener} removes a listener that
     * is present in the component model listener collection.
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
     * Ensures the {@code setFocused} method fires a component model focus
     * changed event after the component model gained the focus.
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
     * Ensures the {@code setFocused} method fires a component model focus
     * changed event after the component model lost the focus.
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
}
