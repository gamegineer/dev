/*
 * AbstractContainerModelTestCase.java
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
 * Created on Jan 26, 2010 at 10:57:19 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.TestContainerLayouts;
import org.gamegineer.table.ui.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ContainerModel} class.
 */
public abstract class AbstractContainerModelTestCase
    extends AbstractComponentModelTestCase<ContainerModel>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The nice mocks control for use in the fixture. */
    private IMocksControl niceMocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerModelTestCase}
     * class.
     */
    protected AbstractContainerModelTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Fires a component model added event for the fixture container model.
     */
    private void fireComponentModelAddedEvent()
    {
        fireComponentModelAddedEvent( getContainerModel() );
    }

    /**
     * Fires a component model added event for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} is {@code null}.
     */
    protected abstract void fireComponentModelAddedEvent(
        /* @NonNull */
        ContainerModel containerModel );

    /**
     * Fires a component model removed event for the fixture container model.
     */
    private void fireComponentModelRemovedEvent()
    {
        fireComponentModelRemovedEvent( getContainerModel() );
    }

    /**
     * Fires a component model removed event for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} is {@code null}.
     */
    protected abstract void fireComponentModelRemovedEvent(
        /* @NonNull */
        ContainerModel containerModel );

    /**
     * Fires a container layout changed event for the fixture container model.
     */
    private void fireContainerLayoutChangedEvent()
    {
        fireContainerLayoutChangedEvent( getContainerModel() );
    }

    /**
     * Fires a container layout changed event for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} is {@code null}.
     */
    protected abstract void fireContainerLayoutChangedEvent(
        /* @NonNull */
        ContainerModel containerModel );

    /**
     * Gets the container model under test in the fixture.
     * 
     * @return The container model under test in the fixture; never {@code null}
     *         .
     */
    /* @NonNull */
    protected final ContainerModel getContainerModel()
    {
        return getComponentModel();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        niceMocksControl_ = EasyMock.createNiceControl();

        super.setUp();
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method adds
     * a listener that is absent from the container model listener collection.
     */
    @Test
    public void testAddContainerModelListener_Listener_Absent()
    {
        final IContainerModelListener listener = niceMocksControl_.createMock( IContainerModelListener.class );
        listener.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        niceMocksControl_.replay();

        fireContainerLayoutChangedEvent();
        getContainerModel().addContainerModelListener( listener );
        fireContainerLayoutChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method
     * throws an exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerModelListener_Listener_Null()
    {
        getContainerModel().addContainerModelListener( null );
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method
     * throws an exception when passed a listener that is present in the
     * container model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddContainerModelListener_Listener_Present()
    {
        final IContainerModelListener listener = EasyMock.createMock( IContainerModelListener.class );
        getContainerModel().addContainerModelListener( listener );

        getContainerModel().addContainerModelListener( listener );
    }

    /**
     * Ensures a change to a component associated with a component model owned
     * by the container model fires a component changed event.
     */
    @Test
    public void testComponentModel_ComponentChanged_FiresComponentChangedEvent()
    {
        final IComponent component = TestComponents.createUniqueComponent( getContainerModel().getComponent().getTableEnvironment() );
        getContainerModel().getComponent().addComponent( component );
        final IComponentModelListener listener = niceMocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addComponentModelListener( listener );

        component.setOrientation( component.getOrientation().inverse() );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the component model added event catches any exception thrown by
     * the {@link IContainerModelListener#componentModelAdded} method of a
     * container model listener.
     */
    @Test
    public void testComponentModelAdded_CatchesListenerException()
    {
        final IContainerModelListener listener1 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener1.componentModelAdded( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerModelListener listener2 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener2.componentModelAdded( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addContainerModelListener( listener1 );
        getContainerModel().addContainerModelListener( listener2 );

        fireComponentModelAddedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the component model removed event catches any exception thrown by
     * the {@link IContainerModelListener#componentModelRemoved} method of a
     * container model listener.
     */
    @Test
    public void testComponentModelRemoved_CatchesListenerException()
    {
        final IContainerModelListener listener = niceMocksControl_.createMock( IContainerModelListener.class );
        listener.componentModelRemoved( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        getContainerModel().addContainerModelListener( listener );

        fireComponentModelRemovedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the addition of a component to the container associated with the
     * container model fires a component model added event and a component
     * changed event.
     */
    @Test
    public void testContainer_ComponentAdded_FiresComponentModelAddedEventAndComponentChangedEvent()
    {
        final IComponentModelListener componentModelListener = niceMocksControl_.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        final IContainerModelListener containerModelListener = niceMocksControl_.createMock( IContainerModelListener.class );
        containerModelListener.componentModelAdded( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addComponentModelListener( componentModelListener );
        getContainerModel().addContainerModelListener( containerModelListener );

        getContainerModel().getComponent().addComponent( TestComponents.createUniqueComponent( getContainerModel().getComponent().getTableEnvironment() ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the removal of a component from the container associated with the
     * container model fires a component model removed event and a component
     * changed event.
     */
    @Test
    public void testContainer_ComponentRemoved_FiresComponentModelRemovedEventAndComponentChangedEvent()
    {
        final IComponent component = TestComponents.createUniqueComponent( getContainerModel().getComponent().getTableEnvironment() );
        getContainerModel().getComponent().addComponent( component );
        final IComponentModelListener componentModelListener = niceMocksControl_.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        final IContainerModelListener containerModelListener = niceMocksControl_.createMock( IContainerModelListener.class );
        containerModelListener.componentModelRemoved( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addComponentModelListener( componentModelListener );
        getContainerModel().addContainerModelListener( containerModelListener );

        getContainerModel().getComponent().removeComponent( component );

        niceMocksControl_.verify();
    }

    /**
     * Ensures a change to the underlying container layout fires a container
     * layout changed event and a component changed event.
     */
    @Test
    public void testContainer_LayoutChanged_FiresContainerLayoutChangedEventAndComponentChangedEvent()
    {
        final IComponentModelListener componentModelListener = niceMocksControl_.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        final IContainerModelListener containerModelListener = niceMocksControl_.createMock( IContainerModelListener.class );
        containerModelListener.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addComponentModelListener( componentModelListener );
        getContainerModel().addContainerModelListener( containerModelListener );

        getContainerModel().getComponent().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@link IContainerModelListener#containerLayoutChanged} method of a
     * container model listener.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
    {
        final IContainerModelListener listener1 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener1.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerModelListener listener2 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener2.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addContainerModelListener( listener1 );
        getContainerModel().addContainerModelListener( listener2 );

        fireContainerLayoutChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link ContainerModel#removeContainerModelListener} method
     * throws an exception when passed a listener that is absent from the
     * container model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveContainerModelListener_Listener_Absent()
    {
        getContainerModel().removeContainerModelListener( EasyMock.createMock( IContainerModelListener.class ) );
    }

    /**
     * Ensures the {@link ContainerModel#removeContainerModelListener} method
     * throws an exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveContainerModelListener_Listener_Null()
    {
        getContainerModel().removeContainerModelListener( null );
    }

    /**
     * Ensures the {@link ContainerModel#removeContainerModelListener} removes a
     * listener that is present in the container model listener collection.
     */
    @Test
    public void testRemoveContainerModelListener_Listener_Present()
    {
        final IContainerModelListener listener = niceMocksControl_.createMock( IContainerModelListener.class );
        listener.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        niceMocksControl_.replay();
        getContainerModel().addContainerModelListener( listener );

        fireContainerLayoutChangedEvent();
        getContainerModel().removeContainerModelListener( listener );
        fireContainerLayoutChangedEvent();

        niceMocksControl_.verify();
    }
}
