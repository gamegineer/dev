/*
 * AbstractComponentModelTestCase.java
 * Copyright 2008-2013 Gamegineer.org
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
import org.gamegineer.test.core.MocksSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

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

    /** The default test timeout. */
    @Rule
    public final Timeout DEFAULT_TIMEOUT = new Timeout( 1000 );

    /** The component model under test in the fixture. */
    private T componentModel_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The mocks support for use in the fixture. */
    private MocksSupport mocksSupport_;

    /** The table model for use in the fixture. */
    private TableModel tableModel_;


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
     * Adds the specified component model listener to the fixture component
     * model.
     * 
     * <p>
     * This method ensures all pending table environment events have fired
     * before adding the listener.
     * </p>
     * 
     * @param listener
     *        The component model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered component model
     *         listener.
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    protected final void addComponentModelListener(
        /* @NonNull */
        final IComponentModelListener listener )
        throws InterruptedException
    {
        awaitPendingTableEnvironmentEvents();
        componentModel_.addComponentModelListener( listener );
    }

    /**
     * Awaits all pending events from the fixture table environment.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     */
    protected final void awaitPendingTableEnvironmentEvents()
        throws InterruptedException
    {
        tableModel_.getTable().getTableEnvironment().awaitPendingEvents();
    }

    /**
     * Creates the component model to be tested.
     * 
     * @param tableModel
     *        The table model; must not be {@code null}.
     * 
     * @return The component model to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code tableModel} is {@code null}.
     */
    /* @NonNull */
    protected abstract T createComponentModel(
        /* @NonNull */
        final TableModel tableModel )
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
     * Gets the mocks support for use in the fixture.
     * 
     * @return The mocks support for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final MocksSupport getMocksSupport()
    {
        assertNotNull( mocksSupport_ );
        return mocksSupport_;
    }

    /**
     * Switches the fixture mocks control from record mode to replay mode.
     */
    protected final void replayMocks()
    {
        mocksControl_.replay();
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
        mocksSupport_ = new MocksSupport();
        tableModel_ = new TableModel();
        componentModel_ = createComponentModel( tableModel_ );
        assertNotNull( componentModel_ );
        componentModel_.initialize( tableModel_ );
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
        tableModel_.dispose();
    }

    /**
     * Ensures the {@link ComponentModel#addComponentModelListener} method adds
     * a listener that is absent from the component model listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentModelListener_Listener_Absent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        fireComponentChangedEvent();
        addComponentModelListener( listener );
        fireComponentChangedEvent();

        verifyMocks();
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
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponent_BoundsChanged_FiresComponentBoundsChangedEventAndComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        replayMocks();

        addComponentModelListener( listener );

        final Point location = componentModel_.getComponent().getLocation();
        location.translate( 1, 1 );
        componentModel_.getComponent().setLocation( location );

        verifyMocks();
    }

    /**
     * Ensures a change to the underlying component orientation fires a
     * component orientation changed event and a component changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponent_OrientationChanged_FiresComponentOrientationChangedEventAndComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentOrientationChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        replayMocks();

        addComponentModelListener( listener );

        componentModel_.getComponent().setOrientation( componentModel_.getComponent().getOrientation().inverse() );

        verifyMocks();
    }

    /**
     * Ensures a change to the underlying component surface design fires a
     * component surface design changed event and a component changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponent_SurfaceDesignChanged_FiresComponentSurfaceDesignChangedEventAndComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        replayMocks();

        addComponentModelListener( listener );

        componentModel_.getComponent().setSurfaceDesign( componentModel_.getComponent().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        verifyMocks();
    }

    /**
     * Ensures the component bounds changed event catches any exception thrown
     * by the {@link IComponentModelListener#componentBoundsChanged} method of a
     * component model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentBoundsChanged_CatchesListenerException()
        throws Exception
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentBoundsChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentBoundsChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener1 );
        addComponentModelListener( listener2 );

        fireComponentBoundsChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the component changed event catches any exception thrown by the
     * {@link IComponentModelListener#componentChanged} method of a component
     * model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentChanged_CatchesListenerException()
        throws Exception
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener1 );
        addComponentModelListener( listener2 );

        fireComponentChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the component model focus changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentModelFocusChanged}
     * method of a component model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentModelFocusChanged_CatchesListenerException()
        throws Exception
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener1 );
        addComponentModelListener( listener2 );

        fireComponentModelFocusChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the component model hover changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentModelHoverChanged}
     * method of a component model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentModelHoverChanged_CatchesListenerException()
        throws Exception
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener1 );
        addComponentModelListener( listener2 );

        fireComponentModelHoverChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the component orientation changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentOrientationChanged}
     * method of a component model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentOrientationChanged_CatchesListenerException()
        throws Exception
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentOrientationChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentOrientationChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener1 );
        addComponentModelListener( listener2 );

        fireComponentOrientationChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the component surface design changed event catches any exception
     * thrown by the
     * {@link IComponentModelListener#componentSurfaceDesignChanged} method of a
     * component model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentSurfaceDesignChanged_CatchesListenerException()
        throws Exception
    {
        final IComponentModelListener listener1 = mocksControl_.createMock( IComponentModelListener.class );
        listener1.componentSurfaceDesignChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl_.createMock( IComponentModelListener.class );
        listener2.componentSurfaceDesignChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener1 );
        addComponentModelListener( listener2 );

        fireComponentSurfaceDesignChangedEvent();

        verifyMocks();
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
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponentModelListener_Listener_Present()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener );

        fireComponentChangedEvent();
        componentModel_.removeComponentModelListener( listener );
        fireComponentChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the {@link ComponentModel#setFocused} method fires a component
     * model focus changed event after the component model gained the focus.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresComponentModelFocusChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener );

        componentModel_.setFocused( true );

        verifyMocks();
    }

    /**
     * Ensures the {@link ComponentModel#setFocused} method fires a component
     * model focus changed event after the component model lost the focus.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetFocused_LostFocus_FiresComponentModelFocusChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener );

        componentModel_.setFocused( false );

        verifyMocks();
    }

    /**
     * Ensures the {@link ComponentModel#setHover} method fires a component
     * model hover changed event after the component model gained the hover.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetHover_GainedHover_FiresComponentModelHoverChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener );

        componentModel_.setHover( true );

        verifyMocks();
    }

    /**
     * Ensures the {@link ComponentModel#setHover} method fires a component
     * model hover changed event after the component model lost the hover.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetHover_LostHover_FiresComponentModelHoverChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentModelHoverChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        replayMocks();

        addComponentModelListener( listener );

        componentModel_.setHover( false );

        verifyMocks();
    }

    /**
     * Verifies that all expectations were met in the fixture mocks control.
     * 
     * <p>
     * This method waits for all asynchronous answers registered with the
     * fixture mocks support to complete before verifying expectations.
     * </p>
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     */
    protected final void verifyMocks()
        throws InterruptedException
    {
        mocksSupport_.awaitAsyncAnswers();
        mocksControl_.verify();
    }
}
