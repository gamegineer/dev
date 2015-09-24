/*
 * AbstractComponentModelTestCase.java
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
 * Created on Dec 25, 2009 at 11:02:59 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.awt.Point;
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestComponentSurfaceDesigns;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.net.test.TestTableNetworks;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentModel} class and any classes that
 * extend it.
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
    private Optional<T> componentModel_;

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;

    /** The table environment model for use in the fixture. */
    private Optional<TableEnvironmentModel> tableEnvironmentModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentModelTestCase}
     * class.
     */
    protected AbstractComponentModelTestCase()
    {
        componentModel_ = Optional.empty();
        mocksControl_ = Optional.empty();
        tableEnvironmentModel_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component model to be tested.
     * 
     * @param tableEnvironmentModel
     *        The fixture table environment model.
     * 
     * @return The component model to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract T createComponentModel(
        TableEnvironmentModel tableEnvironmentModel )
        throws Exception;

    /**
     * Fires a component bounds changed event for the fixture component model.
     */
    private void fireComponentBoundsChangedEvent()
    {
        getComponentModel().fireComponentBoundsChanged();
    }

    /**
     * Fires a component changed event for the fixture component model.
     */
    private void fireComponentChangedEvent()
    {
        getComponentModel().fireComponentChanged();
    }

    /**
     * Fires a component model focus changed event for the fixture component
     * model.
     */
    private void fireComponentModelFocusChangedEvent()
    {
        getComponentModel().fireComponentModelFocusChanged();
    }

    /**
     * Fires a component model hover changed event for the fixture component
     * model.
     */
    private void fireComponentModelHoverChangedEvent()
    {
        getComponentModel().fireComponentModelHoverChanged();
    }

    /**
     * Fires a component orientation changed event for the fixture component
     * model.
     */
    private void fireComponentOrientationChangedEvent()
    {
        getComponentModel().fireComponentOrientationChanged();
    }

    /**
     * Fires a component surface design changed event for the fixture component
     * model.
     */
    private void fireComponentSurfaceDesignChangedEvent()
    {
        getComponentModel().fireComponentSurfaceDesignChanged();
    }

    /**
     * Gets the component model under test in the fixture.
     * 
     * @return The component model under test in the fixture
     *         .
     */
    protected final T getComponentModel()
    {
        return componentModel_.get();
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
    }

    /**
     * Gets the table environment model for use in the fixture.
     * 
     * @return The table environment model for use in the fixture.
     */
    protected final TableEnvironmentModel getTableEnvironmentModel()
    {
        return tableEnvironmentModel_.get();
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
        mocksControl_ = Optional.of( EasyMock.createControl() );

        final TableEnvironmentModel tableEnvironmentModel = new TableEnvironmentModel( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );
        tableEnvironmentModel_ = Optional.of( tableEnvironmentModel );

        final T componentModel = createComponentModel( tableEnvironmentModel );
        final TableModel tableModel = new TableModel( tableEnvironmentModel, tableEnvironmentModel.getTableEnvironment().createTable(), TestTableNetworks.createTableNetwork() );
        componentModel.initialize( tableModel.getTabletopModel() );
        componentModel_ = Optional.of( componentModel );
    }

    /**
     * Ensures the {@link ComponentModel#addComponentModelListener} method adds
     * a listener that is absent from the component model listener collection.
     */
    @Test
    public void testAddComponentModelListener_Listener_Absent()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();

        fireComponentChangedEvent();
        getComponentModel().addComponentModelListener( listener );
        fireComponentChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link ComponentModel#addComponentModelListener} method
     * throws an exception when passed a listener that is present in the
     * component model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentModelListener_Listener_Present()
    {
        final T componentModel = getComponentModel();
        final IComponentModelListener listener = EasyMock.createMock( IComponentModelListener.class );
        componentModel.addComponentModelListener( listener );

        componentModel.addComponentModelListener( listener );
    }

    /**
     * Ensures a change to the underlying component bounds fires a component
     * bounds changed event and a component changed event.
     */
    @Test
    public void testComponent_BoundsChanged_FiresComponentBoundsChangedEventAndComponentChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        listener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        final Point location = componentModel.getComponent().getLocation();
        location.translate( 1, 1 );
        componentModel.getComponent().setLocation( location );

        mocksControl.verify();
    }

    /**
     * Ensures a change to the underlying component orientation fires a
     * component orientation changed event and a component changed event.
     */
    @Test
    public void testComponent_OrientationChanged_FiresComponentOrientationChangedEventAndComponentChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentOrientationChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        listener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        componentModel.getComponent().setOrientation( componentModel.getComponent().getOrientation().inverse() );

        mocksControl.verify();
    }

    /**
     * Ensures a change to the underlying component surface design fires a
     * component surface design changed event and a component changed event.
     */
    @Test
    public void testComponent_SurfaceDesignChanged_FiresComponentSurfaceDesignChangedEventAndComponentChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        listener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        componentModel.getComponent().setSurfaceDesign( componentModel.getComponent().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        mocksControl.verify();
    }

    /**
     * Ensures the component bounds changed event catches any exception thrown
     * by the {@link IComponentModelListener#componentBoundsChanged} method of a
     * component model listener.
     */
    @Test
    public void testComponentBoundsChanged_CatchesListenerException()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener1 = mocksControl.createMock( IComponentModelListener.class );
        listener1.componentBoundsChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl.createMock( IComponentModelListener.class );
        listener2.componentBoundsChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener1 );
        componentModel.addComponentModelListener( listener2 );

        fireComponentBoundsChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the component changed event catches any exception thrown by the
     * {@link IComponentModelListener#componentChanged} method of a component
     * model listener.
     */
    @Test
    public void testComponentChanged_CatchesListenerException()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener1 = mocksControl.createMock( IComponentModelListener.class );
        listener1.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl.createMock( IComponentModelListener.class );
        listener2.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener1 );
        componentModel.addComponentModelListener( listener2 );

        fireComponentChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the component model focus changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentModelFocusChanged}
     * method of a component model listener.
     */
    @Test
    public void testComponentModelFocusChanged_CatchesListenerException()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener1 = mocksControl.createMock( IComponentModelListener.class );
        listener1.componentModelFocusChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl.createMock( IComponentModelListener.class );
        listener2.componentModelFocusChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener1 );
        componentModel.addComponentModelListener( listener2 );

        fireComponentModelFocusChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the component model hover changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentModelHoverChanged}
     * method of a component model listener.
     */
    @Test
    public void testComponentModelHoverChanged_CatchesListenerException()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener1 = mocksControl.createMock( IComponentModelListener.class );
        listener1.componentModelHoverChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl.createMock( IComponentModelListener.class );
        listener2.componentModelHoverChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener1 );
        componentModel.addComponentModelListener( listener2 );

        fireComponentModelHoverChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the component orientation changed event catches any exception
     * thrown by the {@link IComponentModelListener#componentOrientationChanged}
     * method of a component model listener.
     */
    @Test
    public void testComponentOrientationChanged_CatchesListenerException()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener1 = mocksControl.createMock( IComponentModelListener.class );
        listener1.componentOrientationChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl.createMock( IComponentModelListener.class );
        listener2.componentOrientationChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener1 );
        componentModel.addComponentModelListener( listener2 );

        fireComponentOrientationChangedEvent();

        mocksControl.verify();
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
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener1 = mocksControl.createMock( IComponentModelListener.class );
        listener1.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IComponentModelListener listener2 = mocksControl.createMock( IComponentModelListener.class );
        listener2.componentSurfaceDesignChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener1 );
        componentModel.addComponentModelListener( listener2 );

        fireComponentSurfaceDesignChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link ComponentModel#removeComponentModelListener} method
     * throws an exception when passed a listener that is absent from the
     * component model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponentModelListener_Listener_Absent()
    {
        getComponentModel().removeComponentModelListener( EasyMock.createMock( IComponentModelListener.class ) );
    }

    /**
     * Ensures the {@link ComponentModel#removeComponentModelListener} removes a
     * listener that is present in the component model listener collection.
     */
    @Test
    public void testRemoveComponentModelListener_Listener_Present()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        fireComponentChangedEvent();
        componentModel.removeComponentModelListener( listener );
        fireComponentChangedEvent();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setFocused} method fires a component
     * model focus changed event after the component model gained the focus.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresComponentModelFocusChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        componentModel.getLock().lock();
        try
        {
            componentModel.setFocused( true );
        }
        finally
        {
            componentModel.getLock().unlock();
        }

        mocksControl.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setFocused} method fires a component
     * model focus changed event after the component model lost the focus.
     */
    @Test
    public void testSetFocused_LostFocus_FiresComponentModelFocusChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentModelFocusChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        componentModel.getLock().lock();
        try
        {
            componentModel.setFocused( false );
        }
        finally
        {
            componentModel.getLock().unlock();
        }

        mocksControl.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setHover} method fires a component
     * model hover changed event after the component model gained the hover.
     */
    @Test
    public void testSetHover_GainedHover_FiresComponentModelHoverChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentModelHoverChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        componentModel.getLock().lock();
        try
        {
            componentModel.setHover( true );
        }
        finally
        {
            componentModel.getLock().unlock();
        }

        mocksControl.verify();
    }

    /**
     * Ensures the {@link ComponentModel#setHover} method fires a component
     * model hover changed event after the component model lost the hover.
     */
    @Test
    public void testSetHover_LostHover_FiresComponentModelHoverChangedEvent()
    {
        final T componentModel = getComponentModel();
        final IMocksControl mocksControl = getMocksControl();
        final IComponentModelListener listener = mocksControl.createMock( IComponentModelListener.class );
        listener.componentModelHoverChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        mocksControl.replay();
        componentModel.addComponentModelListener( listener );

        componentModel.getLock().lock();
        try
        {
            componentModel.setHover( false );
        }
        finally
        {
            componentModel.getLock().unlock();
        }

        mocksControl.verify();
    }
}
