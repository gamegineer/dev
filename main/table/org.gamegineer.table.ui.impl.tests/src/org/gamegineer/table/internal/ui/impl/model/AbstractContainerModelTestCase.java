/*
 * AbstractContainerModelTestCase.java
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
 * Created on Jan 26, 2010 at 10:57:19 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.test.TestContainerLayouts;
import org.gamegineer.table.net.test.TestTableNetworks;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ContainerModel} class.
 */
public abstract class AbstractContainerModelTestCase
    extends AbstractComponentModelTestCase<ContainerModel>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The nice mocks control for use in the fixture. */
    private Optional<IMocksControl> niceMocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerModelTestCase}
     * class.
     */
    protected AbstractContainerModelTestCase()
    {
        niceMocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component.
     */
    private IComponent createUniqueComponent()
    {
        return TestComponents.createUniqueComponent( getTableEnvironmentModel().getTableEnvironment() );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container.
     */
    private IContainer createUniqueContainer()
    {
        return TestComponents.createUniqueContainer( getTableEnvironmentModel().getTableEnvironment() );
    }

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
     *        The container model.
     */
    protected abstract void fireComponentModelAddedEvent(
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
     *        The container model.
     */
    protected abstract void fireComponentModelRemovedEvent(
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
     *        The container model.
     */
    protected abstract void fireContainerLayoutChangedEvent(
        ContainerModel containerModel );

    /**
     * Gets the container model under test in the fixture.
     * 
     * @return The container model under test in the fixture
     *         .
     */
    protected final ContainerModel getContainerModel()
    {
        return getComponentModel();
    }

    /**
     * Gets the fixture nice mocks control.
     * 
     * @return The fixture nice mocks control.
     */
    private IMocksControl getNiceMocksControl()
    {
        return niceMocksControl_.get();
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
        niceMocksControl_ = Optional.of( EasyMock.createNiceControl() );

        super.setUp();
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method adds
     * a listener that is absent from the container model listener collection.
     */
    @Test
    public void testAddContainerModelListener_Listener_Absent()
    {
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainerModelListener listener = niceMocksControl.createMock( IContainerModelListener.class );
        listener.containerLayoutChanged( EasyMock.<@NonNull ContainerModelEvent>notNull() );
        niceMocksControl.replay();

        fireContainerLayoutChangedEvent();
        getContainerModel().addContainerModelListener( listener );
        fireContainerLayoutChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method
     * throws an exception when passed a listener that is present in the
     * container model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddContainerModelListener_Listener_Present()
    {
        final ContainerModel containerModel = getContainerModel();
        final IContainerModelListener listener = EasyMock.createMock( IContainerModelListener.class );
        containerModel.addContainerModelListener( listener );

        containerModel.addContainerModelListener( listener );
    }

    /**
     * Ensures a change to a component associated with a component model owned
     * by the container model fires a component changed event.
     */
    @Test
    public void testComponentModel_ComponentChanged_FiresComponentChangedEvent()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponent component = TestComponents.createUniqueComponent( containerModel.getComponent().getTableEnvironment() );
        containerModel.getComponent().addComponent( component );
        final IComponentModelListener listener = niceMocksControl.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addComponentModelListener( listener );

        component.setOrientation( component.getOrientation().inverse() );

        niceMocksControl.verify();
    }

    /**
     * Ensures the component model added event catches any exception thrown by
     * the {@link IContainerModelListener#componentModelAdded} method of a
     * container model listener.
     */
    @Test
    public void testComponentModelAdded_CatchesListenerException()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainerModelListener listener1 = niceMocksControl.createMock( IContainerModelListener.class );
        listener1.componentModelAdded( EasyMock.<@NonNull ContainerModelContentChangedEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerModelListener listener2 = niceMocksControl.createMock( IContainerModelListener.class );
        listener2.componentModelAdded( EasyMock.<@NonNull ContainerModelContentChangedEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addContainerModelListener( listener1 );
        containerModel.addContainerModelListener( listener2 );

        fireComponentModelAddedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the component model removed event catches any exception thrown by
     * the {@link IContainerModelListener#componentModelRemoved} method of a
     * container model listener.
     */
    @Test
    public void testComponentModelRemoved_CatchesListenerException()
    {
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainerModelListener listener = niceMocksControl.createMock( IContainerModelListener.class );
        listener.componentModelRemoved( EasyMock.<@NonNull ContainerModelContentChangedEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl.replay();
        getContainerModel().addContainerModelListener( listener );

        fireComponentModelRemovedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the addition of a component to the container associated with the
     * container model fires a component model added event and a component
     * changed event.
     */
    @Test
    public void testContainer_ComponentAdded_FiresComponentModelAddedEventAndComponentChangedEvent()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponentModelListener componentModelListener = niceMocksControl.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        final IContainerModelListener containerModelListener = niceMocksControl.createMock( IContainerModelListener.class );
        containerModelListener.componentModelAdded( EasyMock.<@NonNull ContainerModelContentChangedEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addComponentModelListener( componentModelListener );
        containerModel.addContainerModelListener( containerModelListener );

        containerModel.getComponent().addComponent( TestComponents.createUniqueComponent( containerModel.getComponent().getTableEnvironment() ) );

        niceMocksControl.verify();
    }

    /**
     * Ensures the removal of a component from the container associated with the
     * container model fires a component model removed event and a component
     * changed event.
     */
    @Test
    public void testContainer_ComponentRemoved_FiresComponentModelRemovedEventAndComponentChangedEvent()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponent component = TestComponents.createUniqueComponent( containerModel.getComponent().getTableEnvironment() );
        containerModel.getComponent().addComponent( component );
        final IComponentModelListener componentModelListener = niceMocksControl.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        final IContainerModelListener containerModelListener = niceMocksControl.createMock( IContainerModelListener.class );
        containerModelListener.componentModelRemoved( EasyMock.<@NonNull ContainerModelContentChangedEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addComponentModelListener( componentModelListener );
        containerModel.addContainerModelListener( containerModelListener );

        containerModel.getComponent().removeComponent( component );

        niceMocksControl.verify();
    }

    /**
     * Ensures a change to the underlying container layout fires a container
     * layout changed event and a component changed event.
     */
    @Test
    public void testContainer_LayoutChanged_FiresContainerLayoutChangedEventAndComponentChangedEvent()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponentModelListener componentModelListener = niceMocksControl.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.<@NonNull ComponentModelEvent>notNull() );
        final IContainerModelListener containerModelListener = niceMocksControl.createMock( IContainerModelListener.class );
        containerModelListener.containerLayoutChanged( EasyMock.<@NonNull ContainerModelEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addComponentModelListener( componentModelListener );
        containerModel.addContainerModelListener( containerModelListener );

        containerModel.getComponent().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );

        niceMocksControl.verify();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@link IContainerModelListener#containerLayoutChanged} method of a
     * container model listener.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainerModelListener listener1 = niceMocksControl.createMock( IContainerModelListener.class );
        listener1.containerLayoutChanged( EasyMock.<@NonNull ContainerModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerModelListener listener2 = niceMocksControl.createMock( IContainerModelListener.class );
        listener2.containerLayoutChanged( EasyMock.<@NonNull ContainerModelEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addContainerModelListener( listener1 );
        containerModel.addContainerModelListener( listener2 );

        fireContainerLayoutChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link ContainerModel#getChildPath} method throws an
     * exception when passed a component model that is absent from the component
     * model collection and the container model is associated with a table
     * model.
     */
    @Test( expected = AssertionError.class )
    public void testGetChildPath_ComponentModel_Absent_AssociatedTableModel()
    {
        final ITable table = getTableEnvironmentModel().getTableEnvironment().createTable();
        final TableModel tableModel = getTableEnvironmentModel().createTableModel( table, TestTableNetworks.createTableNetwork() );
        final IContainer container = createUniqueContainer();
        table.getTabletop().addComponent( container );
        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ContainerModel containerModel = (ContainerModel)tableModel.getComponentModel( containerPath );
        assertNotNull( containerModel );

        getTableEnvironmentModel().getLock().lock();
        try
        {
            containerModel.getChildPath( new ComponentModel( getTableEnvironmentModel(), createUniqueComponent() ) );
        }
        finally
        {
            getTableEnvironmentModel().getLock().unlock();
        }
    }

    /**
     * Ensures the {@link ContainerModel#getChildPath} method returns the
     * correct value when passed a component model present in the component
     * model collection and the container model is associated with a table
     * model.
     */
    @Test
    public void testGetChildPath_ComponentModel_Present_AssociatedTableModel()
    {
        final ITable table = getTableEnvironmentModel().getTableEnvironment().createTable();
        final TableModel tableModel = getTableEnvironmentModel().createTableModel( table, TestTableNetworks.createTableNetwork() );
        final IContainer container = createUniqueContainer();
        table.getTabletop().addComponent( container );
        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ContainerModel containerModel = (ContainerModel)tableModel.getComponentModel( containerPath );
        assertNotNull( containerModel );
        final IComponent component = createUniqueComponent();
        container.addComponent( createUniqueComponent() );
        container.addComponent( component );
        container.addComponent( createUniqueComponent() );
        final ComponentPath componentPath = component.getPath();
        assertNotNull( componentPath );
        final ComponentModel componentModel = tableModel.getComponentModel( componentPath );
        assertNotNull( componentModel );

        final ComponentPath actualValue;
        getTableEnvironmentModel().getLock().lock();
        try
        {
            actualValue = containerModel.getChildPath( componentModel );
        }
        finally
        {
            getTableEnvironmentModel().getLock().unlock();
        }

        assertEquals( new ComponentPath( new ComponentPath( new ComponentPath( null, 0 ), 0 ), 1 ), actualValue );
    }

    /**
     * Ensures the {@link ContainerModel#getChildPath} method returns
     * {@code null} when the container model is not associated with a table
     * model.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetChildPath_ContainerModel_NoAssociatedTableModel()
        throws Exception
    {
        final ITable table = getTableEnvironmentModel().getTableEnvironment().createTable();
        final TableModel tableModel = getTableEnvironmentModel().createTableModel( table, TestTableNetworks.createTableNetwork() );
        final IContainer container = createUniqueContainer();
        table.getTabletop().addComponent( container );
        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ContainerModel containerModel = (ContainerModel)tableModel.getComponentModel( containerPath );
        assertNotNull( containerModel );
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final ComponentPath componentPath = component.getPath();
        assertNotNull( componentPath );
        final ComponentModel componentModel = tableModel.getComponentModel( componentPath );
        assertNotNull( componentModel );
        table.getTabletop().removeComponent( container ); // remove from table model

        final ComponentPath actualValue;
        getTableEnvironmentModel().getLock().lock();
        try
        {
            actualValue = containerModel.getChildPath( componentModel );
        }
        finally
        {
            getTableEnvironmentModel().getLock().unlock();
        }

        assertNull( actualValue );
    }

    /**
     * Ensures the {@link ContainerModel#getComponentModelCount} method returns
     * the correct value.
     */
    @Test
    public void testGetComponentModelCount()
    {
        final ContainerModel containerModel = getContainerModel();
        containerModel.getComponent().addComponent( createUniqueComponent() );
        containerModel.getComponent().addComponent( createUniqueComponent() );
        containerModel.getComponent().addComponent( createUniqueComponent() );

        final int actualValue = containerModel.getComponentModelCount();

        assertEquals( 3, actualValue );
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
     * Ensures the {@link ContainerModel#removeContainerModelListener} removes a
     * listener that is present in the container model listener collection.
     */
    @Test
    public void testRemoveContainerModelListener_Listener_Present()
    {
        final ContainerModel containerModel = getContainerModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainerModelListener listener = niceMocksControl.createMock( IContainerModelListener.class );
        listener.containerLayoutChanged( EasyMock.<@NonNull ContainerModelEvent>notNull() );
        niceMocksControl.replay();
        containerModel.addContainerModelListener( listener );

        fireContainerLayoutChangedEvent();
        containerModel.removeContainerModelListener( listener );
        fireContainerLayoutChangedEvent();

        niceMocksControl.verify();
    }
}
