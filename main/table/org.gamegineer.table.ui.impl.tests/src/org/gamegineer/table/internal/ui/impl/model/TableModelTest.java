/*
 * TableModelTest.java
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
 * Created on Dec 28, 2009 at 8:25:04 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.net.test.TestTableNetworks;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IContainerStrategyUI;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableModel} class.
 */
public final class TableModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The nice mocks control for use in the fixture. */
    private Optional<IMocksControl> niceMocksControl_;

    /** The table environment model for use in the fixture. */
    private Optional<TableEnvironmentModel> tableEnvironmentModel_;

    /** The table model under test in the fixture. */
    private Optional<TableModel> tableModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelTest} class.
     */
    public TableModelTest()
    {
        niceMocksControl_ = Optional.empty();
        tableEnvironmentModel_ = Optional.empty();
        tableModel_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a temporary file.
     * 
     * @return A temporary file.
     */
    private static File createTemporaryFile()
    {
        try
        {
            final File temporaryFile = File.createTempFile( TableModelTest.class.getName(), null );
            temporaryFile.deleteOnExit();
            return temporaryFile;
        }
        catch( final IOException e )
        {
            throw new AssertionError( "Failed to create temporary file.", e ); //$NON-NLS-1$
        }
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment and default user interface properties.
     * 
     * @return A new component.
     */
    private IComponent createUniqueComponent()
    {
        return createUniqueComponent( ComponentFocusability.NOT_FOCUSABLE );
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment and the specified user interface properties.
     * 
     * @param componentFocusability
     *        Indicates the component is focusable or not.
     * 
     * @return A new component.
     */
    private IComponent createUniqueComponent(
        final ComponentFocusability componentFocusability )
    {
        final IComponentStrategyUI componentStrategyUI = new IComponentStrategyUI()
        {
            @Override
            public ComponentStrategyId getId()
            {
                return ComponentStrategyId.fromString( "" ); //$NON-NLS-1$
            }

            @Override
            public boolean isFocusable()
            {
                return componentFocusability == ComponentFocusability.FOCUSABLE;
            }
        };
        return TestComponents.createUniqueComponent( getTableModel().getTable().getTableEnvironment(), componentStrategyUI );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment and default user interface properties.
     * 
     * @return A new container.
     */
    private IContainer createUniqueContainer()
    {
        return createUniqueContainer( ComponentFocusability.FOCUSABLE );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment and the specified user interface properties.
     * 
     * @param componentFocusability
     *        Indicates the container is focusable or not.
     * 
     * @return A new container.
     */
    private IContainer createUniqueContainer(
        final ComponentFocusability componentFocusability )
    {
        final IContainerStrategyUI containerStrategyUI = new IContainerStrategyUI()
        {
            @Override
            public ComponentStrategyId getId()
            {
                return ComponentStrategyId.fromString( "" ); //$NON-NLS-1$
            }

            @Override
            public boolean isFocusable()
            {
                return componentFocusability == ComponentFocusability.FOCUSABLE;
            }
        };
        return TestComponents.createUniqueContainer( getTableModel().getTable().getTableEnvironment(), containerStrategyUI );
    }

    /**
     * Fires a table changed event for the table model under test in the
     * fixture.
     */
    private void fireTableChangedEvent()
    {
        fireTableModelEvent( "fireTableChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model dirty flag changed event for the table model under
     * test in the fixture.
     */
    private void fireTableModelDirtyFlagChangedEvent()
    {
        fireTableModelEvent( "fireTableModelDirtyFlagChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the specified table model event for the table model under test in
     * the fixture.
     * 
     * @param methodName
     *        The name of the method that fires the table model event.
     */
    private void fireTableModelEvent(
        final String methodName )
    {
        try
        {
            final Method method = TableModel.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( getTableModel() );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires a table model file changed event for the table model under test in
     * the fixture.
     */
    private void fireTableModelFileChangedEvent()
    {
        fireTableModelEvent( "fireTableModelFileChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model focus changed event for the table model under test in
     * the fixture.
     */
    private void fireTableModelFocusChangedEvent()
    {
        fireTableModelEvent( "fireTableModelFocusChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model hover changed event for the table model under test in
     * the fixture.
     */
    private void fireTableModelHoverChangedEvent()
    {
        fireTableModelEvent( "fireTableModelHoverChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model origin offset changed event for the table model under
     * test in the fixture.
     */
    private void fireTableModelOriginOffsetChangedEvent()
    {
        fireTableModelEvent( "fireTableModelOriginOffsetChanged" ); //$NON-NLS-1$
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
     * Gets the fixture table environment model.
     * 
     * @return The fixture table environment model.
     */
    private TableEnvironmentModel getTableEnvironmentModel()
    {
        return tableEnvironmentModel_.get();
    }

    /**
     * Gets the table model under test in the fixture.
     * 
     * @return The table model under test in the fixture.
     */
    private TableModel getTableModel()
    {
        return tableModel_.get();
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
        niceMocksControl_ = Optional.of( EasyMock.createNiceControl() );
        final TableEnvironmentModel tableEnvironmentModel = new TableEnvironmentModel( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );
        tableEnvironmentModel_ = Optional.of( tableEnvironmentModel );
        tableModel_ = Optional.of( new TableModel( tableEnvironmentModel, tableEnvironmentModel.getTableEnvironment().createTable(), TestTableNetworks.createTableNetwork() ) );
    }

    /**
     * Ensures the {@link TableModel#addTableModelListener} method adds a
     * listener that is absent from the table model listener collection.
     */
    @Test
    public void testAddTableModelListener_Listener_Absent()
    {
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();

        fireTableChangedEvent();
        getTableModel().addTableModelListener( listener );
        fireTableChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#addTableModelListener} method throws an
     * exception when passed a listener that is present in the table model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableModelListener_Listener_Present()
    {
        final TableModel tableModel = getTableModel();
        final ITableModelListener listener = EasyMock.createMock( ITableModelListener.class );
        tableModel.addTableModelListener( listener );

        tableModel.addTableModelListener( listener );
    }

    /**
     * Ensures a change to a container associated with a container model owned
     * by the table model fires a table model dirty flag changed event.
     */
    @Test
    public void testContainerModel_ContainerChanged_FiresTableModelDirtyFlagChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponent component = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( component );
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        component.setLocation( new Point( 1000, 1000 ) );

        niceMocksControl.verify();
    }

    /**
     * Ensures a change to a container associated with a container model owned
     * by the table model fires a table changed event.
     */
    @Test
    public void testContainerModel_ContainerChanged_FiresTableChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponent component = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( component );
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        component.setLocation( new Point( 1000, 1000 ) );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#getChildPath} method throws an exception
     * when passed a component model that is not the tabletop model.
     */
    @Test( expected = AssertionError.class )
    public void testGetChildPath_ComponentModel_NotTabletopModel()
    {
        final TableEnvironmentModel tableEnvironmentModel = getTableEnvironmentModel();

        getTableModel().getChildPath( new ComponentModel( tableEnvironmentModel, TestComponents.createUniqueComponent( tableEnvironmentModel.getTableEnvironment() ) ) );
    }

    /**
     * Ensures the {@link TableModel#getComponentModel} method returns
     * {@code null} when passed a path that is absent.
     */
    @Test
    public void testGetComponentModel_Path_Absent()
    {
        final TableModel tableModel = getTableModel();
        final IContainer container = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container );
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );

        assertNull( tableModel.getComponentModel( new ComponentPath( null, 1 ) ) );
        assertNull( tableModel.getComponentModel( new ComponentPath( new ComponentPath( null, 0 ), 1 ) ) );
        assertNull( tableModel.getComponentModel( new ComponentPath( new ComponentPath( new ComponentPath( null, 0 ), 0 ), 3 ) ) );
    }

    /**
     * Ensures the {@link TableModel#getComponentModel} method returns the
     * correct component model when passed a path that is present.
     */
    @Test
    public void testGetComponentModel_Path_Present()
    {
        final TableModel tableModel = getTableModel();
        final IContainer expectedContainer = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( expectedContainer );
        expectedContainer.addComponent( createUniqueComponent() );
        expectedContainer.addComponent( createUniqueComponent() );
        final IComponent expectedComponent = createUniqueComponent();
        expectedContainer.addComponent( expectedComponent );

        final ComponentPath expectedContainerPath = expectedContainer.getPath();
        assertNotNull( expectedContainerPath );
        final ComponentModel actualContainerModel = tableModel.getComponentModel( expectedContainerPath );
        final ComponentPath expectedComponentPath = expectedComponent.getPath();
        assertNotNull( expectedComponentPath );
        final ComponentModel actualComponentModel = tableModel.getComponentModel( expectedComponentPath );

        assertNotNull( actualContainerModel );
        assertSame( expectedContainer, actualContainerModel.getComponent() );
        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the {@link TableModel#getComponentModels} method returns an empty
     * collection when a component model is absent at the specified location.
     */
    @Test
    public void testGetComponentModels_Location_ComponentModelAbsent()
    {
        assertTrue( getTableModel().getComponentModels( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ).isEmpty() );
    }

    /**
     * Ensures the {@link TableModel#getComponentModels} method returns the
     * correct component model collection when multiple component models are
     * present at the specified location.
     */
    @Test
    public void testGetComponentModels_Location_ComponentModelPresent_MultipleComponentModels()
    {
        final TableModel tableModel = getTableModel();
        //
        final Point location1 = new Point( 7, 420 );
        final Point location2 = new Point( 7, -420 );
        final Point location3 = new Point( 7, 840 );
        //
        final IContainer container1 = createUniqueContainer();
        container1.setLocation( location1 );
        tableModel.getTable().getTabletop().addComponent( container1 );
        final IComponent component1 = createUniqueComponent();
        component1.setLocation( location1 );
        container1.addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        component2.setLocation( location1 );
        container1.addComponent( component2 );
        //
        final IContainer container2 = createUniqueContainer();
        container2.setLocation( location2 );
        tableModel.getTable().getTabletop().addComponent( container2 );
        final IComponent component3 = createUniqueComponent();
        component3.setLocation( location2 );
        container2.addComponent( component3 );
        final IComponent component4 = createUniqueComponent();
        component4.setLocation( location2 );
        container2.addComponent( component4 );
        //
        final IContainer container3 = createUniqueContainer();
        container3.setLocation( location1 );
        tableModel.getTable().getTabletop().addComponent( container3 );
        final IComponent component5 = createUniqueComponent();
        component5.setLocation( location1 );
        container3.addComponent( component5 );
        final IComponent component6 = createUniqueComponent();
        component6.setLocation( location3 );
        container3.addComponent( component6 );
        //
        final List<IComponent> expectedComponents = Arrays.asList( //
            tableModel.getTable().getTabletop(), //
            container1, //
            container3, //
            component1, //
            component2, //
            component5 );

        final List<IComponent> actualComponents = new ArrayList<>();
        for( final ComponentModel componentModel : tableModel.getComponentModels( location1 ) )
        {
            actualComponents.add( componentModel.getComponent() );
        }

        assertEquals( expectedComponents, actualComponents );
    }

    /**
     * Ensures the {@link TableModel#getComponentModels} method returns the
     * correct component model collection when a single component model is
     * present at the specified location.
     */
    @Test
    public void testGetComponentModels_Location_ComponentModelPresent_SingleComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final Point location = new Point( 7, 42 );
        final IComponent component = createUniqueComponent();
        component.setLocation( location );
        tableModel.getTable().getTabletop().addComponent( component );
        final List<IComponent> expectedComponents = Arrays.asList( //
            tableModel.getTable().getTabletop(), //
            component );

        final List<IComponent> actualComponents = new ArrayList<>();
        for( final ComponentModel componentModel : tableModel.getComponentModels( location ) )
        {
            actualComponents.add( componentModel.getComponent() );
        }

        assertEquals( expectedComponents, actualComponents );
    }

    /**
     * Ensures the {@link TableModel#getComponentModels} method returns a copy
     * of the component model collection.
     */
    @Test
    public void testGetComponentModels_ReturnValue_Copy()
    {
        final TableModel tableModel = getTableModel();
        final List<ComponentModel> componentModels = tableModel.getComponentModels( new Point( 0, 0 ) );
        final int expectedComponentModelsSize = componentModels.size();

        tableModel.getTable().getTabletop().addComponent( createUniqueComponent() );

        assertEquals( expectedComponentModelsSize, componentModels.size() );
    }

    /**
     * Ensures the {@link TableModel#getFocusableComponentModel} method returns
     * the expected component model when a focusable component model exists at
     * the specified location.
     */
    @Test
    public void testGetFocusableComponentModel_Location_FocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IComponent expectedComponent = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( expectedComponent );

        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the {@link TableModel#getFocusableComponentModel} method returns
     * {@code null} when no component model exists at he specified location.
     */
    @Test
    public void testGetFocusableComponentModel_Location_NoComponentModel()
    {
        final TableModel tableModel = getTableModel();
        tableModel.getTable().getTabletop().addComponent( createUniqueContainer() );

        assertNull( tableModel.getFocusableComponentModel( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ) );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when a search vector using
     * the {@link ComponentAxis#FOLLOWING} direction is specified and a
     * focusable component model that follows the search vector origin component
     * model exists at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_FollowingDirection_Location_FollowingFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer parentContainer = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( parentContainer );
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final IComponent childComponent = createUniqueComponent( ComponentFocusability.FOCUSABLE );
        container.addComponent( childComponent );
        final IComponent expectedComponent = childComponent;

        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel componentModel = tableModel.getComponentModel( containerPath );
        assertNotNull( componentModel );
        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), new ComponentModelVector( componentModel, ComponentAxis.FOLLOWING ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when a search vector using
     * the {@link ComponentAxis#FOLLOWING} direction is specified and no other
     * focusable component model exists at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_FollowingDirection_Location_NoOtherFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer parentContainer = createUniqueContainer( ComponentFocusability.NOT_FOCUSABLE );
        tableModel.getTable().getTabletop().addComponent( parentContainer );
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final IComponent childComponent = createUniqueComponent();
        container.addComponent( childComponent );
        final IComponent expectedComponent = container;

        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel componentModel = tableModel.getComponentModel( containerPath );
        assertNotNull( componentModel );
        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), new ComponentModelVector( componentModel, ComponentAxis.FOLLOWING ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when a search vector using
     * the {@link ComponentAxis#FOLLOWING} direction is specified and a
     * focusable component model that precedes the search vector origin exists
     * at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_FollowingDirection_Location_PrecedingFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer parentContainer = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( parentContainer );
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final IComponent childComponent = createUniqueComponent();
        container.addComponent( childComponent );
        final IComponent expectedComponent = parentContainer;

        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel componentModel = tableModel.getComponentModel( containerPath );
        assertNotNull( componentModel );
        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), new ComponentModelVector( componentModel, ComponentAxis.FOLLOWING ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when no search vector is
     * specified and a focusable component model exists at the specified
     * location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_Null_Location_FocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer container = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container );
        final IComponent childComponent = createUniqueComponent();
        container.addComponent( childComponent );
        final IComponent expectedComponent = container;

        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), null );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns {@code null} when no search vector is specified and no
     * component model exists at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_Null_Location_NoComponentModel()
    {
        final TableModel tableModel = getTableModel();
        tableModel.getTable().getTabletop().addComponent( createUniqueContainer() );

        assertNull( tableModel.getFocusableComponentModel( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ), null ) );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns {@code null} when no search vector is specified and no
     * focusable component model exists at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_Null_Location_NoFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        tableModel.getTable().getTabletop().addComponent( createUniqueComponent() );

        assertNull( tableModel.getFocusableComponentModel( new Point( 0, 0 ), null ) );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when a search vector using
     * the {@link ComponentAxis#PRECEDING} direction is specified and a
     * focusable component model that follows the search vector origin component
     * model exists at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_PrecedingDirection_Location_FollowingFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer parentContainer = createUniqueContainer( ComponentFocusability.NOT_FOCUSABLE );
        tableModel.getTable().getTabletop().addComponent( parentContainer );
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final IComponent childComponent = createUniqueComponent( ComponentFocusability.FOCUSABLE );
        container.addComponent( childComponent );
        final IComponent expectedComponent = childComponent;

        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel componentModel = tableModel.getComponentModel( containerPath );
        assertNotNull( componentModel );
        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), new ComponentModelVector( componentModel, ComponentAxis.PRECEDING ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when a search vector using
     * the {@link ComponentAxis#PRECEDING} direction is specified and no other
     * focusable component model exists at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_PrecedingDirection_Location_NoOtherFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer parentContainer = createUniqueContainer( ComponentFocusability.NOT_FOCUSABLE );
        tableModel.getTable().getTabletop().addComponent( parentContainer );
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final IComponent childComponent = createUniqueComponent();
        container.addComponent( childComponent );
        final IComponent expectedComponent = container;

        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel componentModel = tableModel.getComponentModel( containerPath );
        assertNotNull( componentModel );
        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), new ComponentModelVector( componentModel, ComponentAxis.PRECEDING ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the
     * {@link TableModel#getFocusableComponentModel(Point, ComponentModelVector)}
     * method returns the expected component model when a search vector using
     * the {@link ComponentAxis#PRECEDING} direction is specified and a
     * focusable component model that precedes the search vector origin exists
     * at the specified location.
     */
    @Test
    public void testGetFocusableComponentModelWithSearchVector_SearchVector_PrecedingDirection_Location_PrecedingFocusableComponentModel()
    {
        final TableModel tableModel = getTableModel();
        final IContainer parentContainer = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( parentContainer );
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final IComponent childComponent = createUniqueComponent();
        container.addComponent( childComponent );
        final IComponent expectedComponent = parentContainer;

        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel componentModel = tableModel.getComponentModel( containerPath );
        assertNotNull( componentModel );
        final ComponentModel actualComponentModel = tableModel.getFocusableComponentModel( new Point( 0, 0 ), new ComponentModelVector( componentModel, ComponentAxis.PRECEDING ) );

        assertNotNull( actualComponentModel );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the {@link TableModel#getOriginOffset} method returns a copy of
     * the origin offset.
     */
    @Test
    public void testGetOriginOffset_ReturnValue_Copy()
    {
        final TableModel tableModel = getTableModel();
        final Dimension originOffset = tableModel.getOriginOffset();
        final Dimension expectedOriginOffset = new Dimension( originOffset );
        originOffset.setSize( expectedOriginOffset.width + 100, expectedOriginOffset.height + 200 );

        final Dimension actualOriginOffset = tableModel.getOriginOffset();

        assertEquals( expectedOriginOffset, actualOriginOffset );
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table changed event.
     */
    @Test
    public void testOpen_FiresTableChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.open();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model dirty
     * flag changed event.
     */
    @Test
    public void testOpen_FiresTableModelDirtyFlagChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.open();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model file
     * changed event.
     */
    @Test
    public void testOpen_FiresTableModelFileChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.open();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model focus
     * changed event.
     */
    @Test
    public void testOpen_FiresTableModelFocusChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.open();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model hover
     * changed event.
     */
    @Test
    public void testOpen_FiresTableModelHoverChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelHoverChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.open();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model origin
     * offset changed event.
     */
    @Test
    public void testOpen_FiresTableModelOriginOffsetChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.open();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table changed
     * event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.save( file );
        tableModel.addTableModelListener( listener );

        tableModel.open( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * dirty flag changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelDirtyFlagChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.save( file );
        tableModel.addTableModelListener( listener );

        tableModel.open( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model file
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelFileChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.save( file );
        tableModel.addTableModelListener( listener );

        getTableModel().open( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * focus changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelFocusChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.save( file );
        tableModel.addTableModelListener( listener );

        tableModel.open( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * hover changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelHoverChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelHoverChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.save( file );
        tableModel.addTableModelListener( listener );

        tableModel.open( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * origin offset changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelOriginOffsetChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.save( file );
        tableModel.addTableModelListener( listener );

        tableModel.open( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures a table model focus changed event is fired if the component model
     * with the focus is removed from the table model.
     */
    @Test
    public void testRemoveFocusedComponentModel_FiresTableModelFocusChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponent component = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( component );
        final ComponentPath componentPath = component.getPath();
        assertNotNull( componentPath );
        final ComponentModel componentModel = tableModel.getComponentModel( componentPath );
        tableModel.setFocus( componentModel );
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        assertEquals( componentModel, tableModel.getFocusedComponentModel() );
        tableModel.getTable().getTabletop().removeComponent( component );

        niceMocksControl.verify();
        assertNull( tableModel.getFocusedComponentModel() );
    }

    /**
     * Ensures a table model hover changed event is fired if the component model
     * with the hover is removed from the table model.
     */
    @Test
    public void testRemoveHoveredComponentModel_FiresTableModelHoverChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IComponent component = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( component );
        final ComponentPath componentPath = component.getPath();
        assertNotNull( componentPath );
        final ComponentModel componentModel = tableModel.getComponentModel( componentPath );
        tableModel.setHover( componentModel );
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelHoverChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        assertEquals( componentModel, tableModel.getHoveredComponentModel() );
        tableModel.getTable().getTabletop().removeComponent( component );

        niceMocksControl.verify();
        assertNull( tableModel.getHoveredComponentModel() );
    }

    /**
     * Ensures the {@link TableModel#removeTableModelListener} method throws an
     * exception when passed a listener that is absent from the table model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableModelListener_Listener_Absent()
    {
        getTableModel().removeTableModelListener( EasyMock.createMock( ITableModelListener.class ) );
    }

    /**
     * Ensures the {@link TableModel#removeTableModelListener} removes a
     * listener that is present in the table model listener collection.
     */
    @Test
    public void testRemoveTableModelListener_Listener_Present()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        fireTableChangedEvent();
        tableModel.removeTableModelListener( listener );
        fireTableChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#save} method fires a table model dirty flag
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSave_FiresTableModelDirtyFlagChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.save( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#save} method fires a table model file
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSave_FiresTableModelFileChangedEvent()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.save( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#setFocus} method correctly changes the
     * focus.
     */
    @Test
    public void testSetFocus()
    {
        final TableModel tableModel = getTableModel();
        final IContainer container1 = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container1 );
        final ComponentPath container1Path = container1.getPath();
        assertNotNull( container1Path );
        final ComponentModel containerModel1 = tableModel.getComponentModel( container1Path );
        final IContainer container2 = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container2 );
        final ComponentPath container2Path = container2.getPath();
        assertNotNull( container2Path );
        final ComponentModel containerModel2 = tableModel.getComponentModel( container2Path );

        assertNotNull( containerModel1 );
        assertNotNull( containerModel2 );
        tableModel.setFocus( containerModel1 );
        assertTrue( containerModel1.isFocused() );
        assertFalse( containerModel2.isFocused() );
        tableModel.setFocus( containerModel2 );
        assertFalse( containerModel1.isFocused() );
        assertTrue( containerModel2.isFocused() );
    }

    /**
     * Ensures the {@link TableModel#setFocus} method does not throw an
     * exception when passed a {@code null} component model.
     */
    @Test
    public void testSetFocus_ComponentModel_Null()
    {
        getTableModel().setFocus( null );
    }

    /**
     * Ensures the {@link TableModel#setFocus} method fires a table model focus
     * changed event.
     */
    @Test
    public void testSetFocus_FiresTableModelFocusChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainer container = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container );
        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel containerModel = tableModel.getComponentModel( containerPath );
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.setFocus( containerModel );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#setHover} method correctly changes the
     * hover.
     */
    @Test
    public void testSetHover()
    {
        final TableModel tableModel = getTableModel();
        final IContainer container1 = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container1 );
        final ComponentPath container1Path = container1.getPath();
        assertNotNull( container1Path );
        final ComponentModel containerModel1 = tableModel.getComponentModel( container1Path );
        final IContainer container2 = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container2 );
        final ComponentPath container2Path = container2.getPath();
        assertNotNull( container2Path );
        final ComponentModel containerModel2 = tableModel.getComponentModel( container2Path );

        assertNotNull( containerModel1 );
        assertNotNull( containerModel2 );
        tableModel.setHover( containerModel1 );
        assertTrue( containerModel1.isHovered() );
        assertFalse( containerModel2.isHovered() );
        tableModel.setHover( containerModel2 );
        assertFalse( containerModel1.isHovered() );
        assertTrue( containerModel2.isHovered() );
    }

    /**
     * Ensures the {@link TableModel#setHover} method does not throw an
     * exception when passed a {@code null} component model.
     */
    @Test
    public void testSetHover_ComponentModel_Null()
    {
        getTableModel().setHover( null );
    }

    /**
     * Ensures the {@link TableModel#setHover} method fires a table model hover
     * changed event.
     */
    @Test
    public void testSetHover_FiresTableModelHoverChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IContainer container = createUniqueContainer();
        tableModel.getTable().getTabletop().addComponent( container );
        final ComponentPath containerPath = container.getPath();
        assertNotNull( containerPath );
        final ComponentModel containerModel = tableModel.getComponentModel( containerPath );
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelHoverChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.setHover( containerModel );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link TableModel#setOriginOffset} method fires a table model
     * origin offset changed event.
     */
    @Test
    public void testSetOriginOffset_FiresTableModelOriginOffsetChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.setOriginOffset( new Dimension( 100, 200 ) );

        niceMocksControl.verify();
    }

    /**
     * Ensures a change to the underlying table state fires a table changed
     * event.
     */
    @Test
    public void testTable_StateChanged_FiresTableChangedEvent()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener = niceMocksControl.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener );

        tableModel.getTable().getTabletop().addComponent( createUniqueContainer() );

        niceMocksControl.verify();
    }

    /**
     * Ensures the table changed event catches any exception thrown by the
     * {@link ITableModelListener#tableChanged} method of a table model
     * listener.
     */
    @Test
    public void testTableChanged_CatchesListenerException()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener1 = niceMocksControl.createMock( ITableModelListener.class );
        listener1.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl.createMock( ITableModelListener.class );
        listener2.tableChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener1 );
        tableModel.addTableModelListener( listener2 );

        fireTableChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the table model dirty flag changed event catches any exception
     * thrown by the {@link ITableModelListener#tableModelDirtyFlagChanged}
     * method of a table model listener.
     */
    @Test
    public void testTableModelDirtyFlagChanged_CatchesListenerException()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener1 = niceMocksControl.createMock( ITableModelListener.class );
        listener1.tableModelDirtyFlagChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        final ITableModelListener listener2 = niceMocksControl.createMock( ITableModelListener.class );
        listener2.tableModelDirtyFlagChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener1 );
        tableModel.addTableModelListener( listener2 );

        fireTableModelDirtyFlagChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the table model file changed event catches any exception thrown
     * by the {@link ITableModelListener#tableModelFileChanged} method of a
     * table model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableModelFileChanged_CatchesListenerException()
        throws Exception
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener1 = niceMocksControl.createMock( ITableModelListener.class );
        listener1.tableModelFileChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl.createMock( ITableModelListener.class );
        listener2.tableModelFileChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener1 );
        tableModel.addTableModelListener( listener2 );

        fireTableModelFileChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the table model focus changed event catches any exception thrown
     * by the {@link ITableModelListener#tableModelFocusChanged} method of a
     * table model listener.
     */
    @Test
    public void testTableModelFocusChanged_CatchesListenerException()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener1 = niceMocksControl.createMock( ITableModelListener.class );
        listener1.tableModelFocusChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl.createMock( ITableModelListener.class );
        listener2.tableModelFocusChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener1 );
        tableModel.addTableModelListener( listener2 );

        fireTableModelFocusChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the table model hover changed event catches any exception thrown
     * by the {@link ITableModelListener#tableModelHoverChanged} method of a
     * table model listener.
     */
    @Test
    public void testTableModelHoverChanged_CatchesListenerException()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener1 = niceMocksControl.createMock( ITableModelListener.class );
        listener1.tableModelHoverChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl.createMock( ITableModelListener.class );
        listener2.tableModelHoverChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener1 );
        tableModel.addTableModelListener( listener2 );

        fireTableModelHoverChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the table model origin offset changed event catches any exception
     * thrown by the {@link ITableModelListener#tableModelOriginOffsetChanged}
     * method of a table model listener.
     */
    @Test
    public void testTableModelOriginOffsetChanged_CatchesListenerException()
    {
        final TableModel tableModel = getTableModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final ITableModelListener listener1 = niceMocksControl.createMock( ITableModelListener.class );
        listener1.tableModelOriginOffsetChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl.createMock( ITableModelListener.class );
        listener2.tableModelOriginOffsetChanged( EasyMock.<@NonNull TableModelEvent>notNull() );
        niceMocksControl.replay();
        tableModel.addTableModelListener( listener1 );
        tableModel.addTableModelListener( listener2 );

        fireTableModelOriginOffsetChangedEvent();

        niceMocksControl.verify();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Indicates the focusability of a table component.
     */
    private static enum ComponentFocusability
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** Indicates the component is focusable. */
        FOCUSABLE,

        /** Indicates the component is not focusable. */
        NOT_FOCUSABLE;
    }
}
