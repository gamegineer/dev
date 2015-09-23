/*
 * AbstractTableTestCase.java
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
 * Created on Oct 6, 2009 at 10:58:22 PM.
 */

package org.gamegineer.table.core.test;

import static org.gamegineer.table.core.test.Assert.assertTableEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITable} interface.
 * 
 * @param <TableEnvironmentType>
 *        The type of the table environment.
 * @param <TableType>
 *        The type of the table.
 */
public abstract class AbstractTableTestCase<TableEnvironmentType extends ITableEnvironment, TableType extends ITable>
    extends AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table under test in the fixture. */
    private Optional<TableType> table_;

    /** The table environment for use in the fixture. */
    private Optional<TableEnvironmentType> tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableTestCase} class.
     */
    protected AbstractTableTestCase()
    {
        table_ = Optional.empty();
        tableEnvironment_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation compares the expected and actual values according to
     * the specification of the {@link Assert#assertTableEquals} method.
     * 
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#assertMementoOriginatorEquals(org.gamegineer.common.core.util.memento.IMementoOriginator,
     *      org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void assertMementoOriginatorEquals(
        final IMementoOriginator expected,
        final IMementoOriginator actual )
    {
        final ITable expectedTable = (ITable)expected;
        final ITable actualTable = (ITable)actual;
        assertTableEquals( expectedTable, actualTable );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected final IMementoOriginator createMementoOriginator()
        throws Exception
    {
        return createTable( getTableEnvironment() );
    }

    /**
     * Creates the table to be tested.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * 
     * @return The table to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract TableType createTable(
        TableEnvironmentType tableEnvironment )
        throws Exception;

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
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    private IComponent createUniqueComponent()
    {
        return TestComponents.createUniqueComponent( getTableEnvironment() );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container; never {@code null}.
     */
    private IContainer createUniqueContainer()
    {
        return TestComponents.createUniqueContainer( getTableEnvironment() );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final ITable table = (ITable)mementoOriginator;

        table.getTabletop().addComponent( TestComponents.createUniqueComponent( table.getTableEnvironment() ) );
    }

    /**
     * Gets the table under test in the fixture.
     * 
     * @return The table under test in the fixture; never {@code null}.
     */
    protected final TableType getTable()
    {
        return table_.get();
    }

    /**
     * Gets the fixture table environment.
     * 
     * @return The fixture table environment; never {@code null}.
     */
    private TableEnvironmentType getTableEnvironment()
    {
        return tableEnvironment_.get();
    }

    /*
     * @see org.gamegineer.common.core.util.memento.test.AbstractMementoOriginatorTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        final TableEnvironmentType tableEnvironment = createTableEnvironment();
        tableEnvironment_ = Optional.of( tableEnvironment );
        table_ = Optional.of( createTable( tableEnvironment ) );

        super.setUp();
    }

    /**
     * Ensures the {@link ITable#getComponent} method returns {@code null} when
     * passed a path that is absent.
     */
    @Test
    public void testGetComponent_Path_Absent()
    {
        assertNull( getTable().getComponent( new ComponentPath( null, 1 ) ) );
    }

    /**
     * Ensures the {@link ITable#getComponent} method returns the correct
     * component when passed a path that is present.
     */
    @Test
    public void testGetComponent_Path_Present()
    {
        final TableType table = getTable();
        final IContainer expectedTabletop = table.getTabletop();
        final IContainer expectedContainer = createUniqueContainer();
        expectedTabletop.addComponent( expectedContainer );
        expectedContainer.addComponent( createUniqueComponent() );
        expectedContainer.addComponent( createUniqueComponent() );
        final IComponent expectedComponent = createUniqueComponent();
        expectedContainer.addComponent( expectedComponent );

        final ComponentPath expectedTabletopPath = expectedTabletop.getPath();
        assertNotNull( expectedTabletopPath );
        final IComponent actualTabletop = table.getComponent( expectedTabletopPath );
        final ComponentPath expectedContainerPath = expectedContainer.getPath();
        assertNotNull( expectedContainerPath );
        final IComponent actualContainer = table.getComponent( expectedContainerPath );
        final ComponentPath expectedComponentPath = expectedComponent.getPath();
        assertNotNull( expectedComponentPath );
        final IComponent actualComponent = table.getComponent( expectedComponentPath );

        assertSame( expectedTabletop, actualTabletop );
        assertSame( expectedContainer, actualContainer );
        assertSame( expectedComponent, actualComponent );
    }

    /**
     * Ensures the {@link ITable#getComponents} method returns an empty
     * collection when a component is absent at the specified location.
     */
    @Test
    public void testGetComponents_Location_ComponentAbsent()
    {
        assertTrue( getTable().getComponents( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ).isEmpty() );
    }

    /**
     * Ensures the {@link ITable#getComponents} method returns the correct
     * component collection when multiple components are present at the
     * specified location.
     */
    @Test
    public void testGetComponents_Location_ComponentPresent_MultipleComponents()
    {
        final TableType table = getTable();
        //
        final Point location1 = new Point( 7, 420 );
        final Point location2 = new Point( 7, -420 );
        final Point location3 = new Point( 7, 840 );
        //
        final IContainer container1 = createUniqueContainer();
        container1.setLocation( location1 );
        table.getTabletop().addComponent( container1 );
        final IComponent component1 = createUniqueComponent();
        component1.setLocation( location1 );
        container1.addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        component2.setLocation( location1 );
        container1.addComponent( component2 );
        //
        final IContainer container2 = createUniqueContainer();
        container2.setLocation( location2 );
        table.getTabletop().addComponent( container2 );
        final IComponent component3 = createUniqueComponent();
        component3.setLocation( location2 );
        container2.addComponent( component3 );
        final IComponent component4 = createUniqueComponent();
        component4.setLocation( location2 );
        container2.addComponent( component4 );
        //
        final IContainer container3 = createUniqueContainer();
        container3.setLocation( location1 );
        table.getTabletop().addComponent( container3 );
        final IComponent component5 = createUniqueComponent();
        component5.setLocation( location1 );
        container3.addComponent( component5 );
        final IComponent component6 = createUniqueComponent();
        component6.setLocation( location3 );
        container3.addComponent( component6 );
        //
        final List<IComponent> expectedComponents = Arrays.asList( //
            table.getTabletop(), //
            container1, //
            container3, //
            component1, //
            component2, //
            component5 );

        final List<IComponent> actualComponents = table.getComponents( location1 );

        assertEquals( expectedComponents, actualComponents );
    }

    /**
     * Ensures the {@link ITable#getComponents} method returns the correct
     * component collection when a single component is present at the specified
     * location.
     */
    @Test
    public void testGetComponents_Location_ComponentPresent_SingleComponent()
    {
        final TableType table = getTable();
        final Point location = new Point( 7, 42 );
        final IComponent component = createUniqueComponent();
        component.setLocation( location );
        table.getTabletop().addComponent( component );
        final List<IComponent> expectedComponents = Arrays.asList( //
            table.getTabletop(), //
            component );

        final List<IComponent> actualComponents = table.getComponents( location );

        assertEquals( expectedComponents, actualComponents );
    }

    /**
     * Ensures the {@link ITable#getComponents} method returns a copy of the
     * component collection.
     */
    @Test
    public void testGetComponents_ReturnValue_Copy()
    {
        final TableType table = getTable();
        final List<IComponent> components = table.getComponents( new Point( 0, 0 ) );
        final int expectedComponentsSize = components.size();

        table.getTabletop().addComponent( createUniqueComponent() );

        assertEquals( expectedComponentsSize, components.size() );
    }
}
