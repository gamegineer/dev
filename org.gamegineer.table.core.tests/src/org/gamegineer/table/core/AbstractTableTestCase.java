/*
 * AbstractTableTestCase.java
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
 * Created on Oct 6, 2009 at 10:58:22 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.table.core.Assert.assertTableEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITable} interface.
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
    private TableType table_;

    /** The table environment for use in the fixture. */
    private TableEnvironmentType tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableTestCase} class.
     */
    protected AbstractTableTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation compares the expected and actual values according to
     * the specification of the
     * {@link org.gamegineer.table.core.Assert#assertTableEquals} method.
     * 
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#assertMementoOriginatorEquals(org.gamegineer.common.core.util.memento.IMementoOriginator,
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
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected final IMementoOriginator createMementoOriginator()
        throws Exception
    {
        return createTable( tableEnvironment_ );
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
    /* @NonNull */
    protected abstract TableType createTable(
        /* @NonNull */
        TableEnvironmentType tableEnvironment )
        throws Exception;

    /**
     * Creates the table environment for use in the fixture.
     * 
     * @return The table environment for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected abstract TableEnvironmentType createTableEnvironment();

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private IComponent createUniqueComponent()
    {
        return TestComponents.createUniqueComponent( tableEnvironment_ );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container; never {@code null}.
     */
    /* @NonNull */
    private IContainer createUniqueContainer()
    {
        return TestComponents.createUniqueContainer( tableEnvironment_ );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final ITable table = (ITable)mementoOriginator;

        table.getTabletop().addComponent( TestComponents.createUniqueComponent( table.getTableEnvironment() ) );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        tableEnvironment_ = createTableEnvironment();
        assertNotNull( tableEnvironment_ );
        table_ = createTable( tableEnvironment_ );
        assertNotNull( table_ );

        super.setUp();
    }

    /**
     * Ensures the {@link ITable#getComponent(Point)} method returns
     * {@code null} when a component is absent at the specified location.
     */
    @Test
    public void testGetComponentFromLocation_Location_ComponentAbsent()
    {
        assertNull( table_.getComponent( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ) );
    }

    /**
     * Ensures the {@link ITable#getComponent(Point)} method returns the
     * top-most component when multiple components are present at the specified
     * location.
     */
    @Test
    public void testGetComponentFromLocation_Location_ComponentPresent_MultipleComponents()
    {
        final Point location = new Point( 7, 42 );
        final IContainer container1 = createUniqueContainer();
        container1.setLocation( location );
        table_.getTabletop().addComponent( container1 );
        final IComponent component1 = createUniqueComponent();
        component1.setLocation( location );
        container1.addComponent( component1 );
        final IContainer container2 = createUniqueContainer();
        container2.setLocation( location );
        table_.getTabletop().addComponent( container2 );
        final IComponent expectedComponent = createUniqueComponent();
        expectedComponent.setLocation( location );
        container2.addComponent( expectedComponent );

        final IComponent actualComponent = table_.getComponent( location );

        assertSame( expectedComponent, actualComponent );
    }

    /**
     * Ensures the {@link ITable#getComponent(Point)} method returns the
     * appropriate component when a single component is present at the specified
     * location.
     */
    @Test
    public void testGetComponentFromLocation_Location_ComponentPresent_SingleComponent()
    {
        final Point location = new Point( 7, 42 );
        final IComponent expectedComponent = createUniqueComponent();
        expectedComponent.setLocation( location );
        table_.getTabletop().addComponent( expectedComponent );

        final IComponent actualComponent = table_.getComponent( location );

        assertSame( expectedComponent, actualComponent );
    }

    /**
     * Ensures the {@link ITable#getComponent(Point)} method throws an exception
     * when passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentFromLocation_Location_Null()
    {
        table_.getComponent( (Point)null );
    }

    /**
     * Ensures the {@link ITable#getComponent(ComponentPath)} method returns
     * {@code null} when passed a path that is absent.
     */
    @Test
    public void testGetComponentFromPath_Path_Absent()
    {
        assertNull( table_.getComponent( new ComponentPath( null, 1 ) ) );
    }

    /**
     * Ensures the {@link ITable#getComponent(ComponentPath)} method throws an
     * exception when passed a {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentFromPath_Path_Null()
    {
        table_.getComponent( (ComponentPath)null );
    }

    /**
     * Ensures the {@link ITable#getComponent(ComponentPath)} method returns the
     * correct component when passed a path that is present.
     */
    @Test
    public void testGetComponentFromPath_Path_Present()
    {
        final IContainer expectedTabletop = table_.getTabletop();
        final IContainer expectedContainer = createUniqueContainer();
        expectedTabletop.addComponent( expectedContainer );
        expectedContainer.addComponent( createUniqueComponent() );
        expectedContainer.addComponent( createUniqueComponent() );
        final IComponent expectedComponent = createUniqueComponent();
        expectedContainer.addComponent( expectedComponent );

        final IComponent actualTabletop = table_.getComponent( expectedTabletop.getPath() );
        final IComponent actualContainer = table_.getComponent( expectedContainer.getPath() );
        final IComponent actualComponent = table_.getComponent( expectedComponent.getPath() );

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
        assertTrue( table_.getComponents( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ).isEmpty() );
    }

    /**
     * Ensures the {@link ITable#getComponents} method returns the correct
     * component collection when multiple components are present at the
     * specified location.
     */
    @Test
    public void testGetComponents_Location_ComponentPresent_MultipleComponents()
    {
        final Point location1 = new Point( 7, 420 );
        final Point location2 = new Point( 7, -420 );
        final Point location3 = new Point( 7, 840 );
        //
        final IContainer container1 = createUniqueContainer();
        container1.setLocation( location1 );
        table_.getTabletop().addComponent( container1 );
        final IComponent component1 = createUniqueComponent();
        component1.setLocation( location1 );
        container1.addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        component2.setLocation( location1 );
        container1.addComponent( component2 );
        //
        final IContainer container2 = createUniqueContainer();
        container2.setLocation( location2 );
        table_.getTabletop().addComponent( container2 );
        final IComponent component3 = createUniqueComponent();
        component3.setLocation( location2 );
        container2.addComponent( component3 );
        final IComponent component4 = createUniqueComponent();
        component4.setLocation( location2 );
        container2.addComponent( component4 );
        //
        final IContainer container3 = createUniqueContainer();
        container3.setLocation( location1 );
        table_.getTabletop().addComponent( container3 );
        final IComponent component5 = createUniqueComponent();
        component5.setLocation( location1 );
        container3.addComponent( component5 );
        final IComponent component6 = createUniqueComponent();
        component6.setLocation( location3 );
        container3.addComponent( component6 );
        //
        final List<IComponent> expectedComponents = Arrays.asList( //
            table_.getTabletop(), //
            container1, //
            container3, //
            component1, //
            component2, //
            component5 );

        final List<IComponent> actualComponents = table_.getComponents( location1 );

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
        final Point location = new Point( 7, 42 );
        final IComponent component = createUniqueComponent();
        component.setLocation( location );
        table_.getTabletop().addComponent( component );
        final List<IComponent> expectedComponents = Arrays.asList( //
            table_.getTabletop(), //
            component );

        final List<IComponent> actualComponents = table_.getComponents( location );

        assertEquals( expectedComponents, actualComponents );
    }

    /**
     * Ensures the {@link ITable#getComponents} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponents_Location_Null()
    {
        table_.getComponents( null );
    }

    /**
     * Ensures the {@link ITable#getComponents} method returns a copy of the
     * component collection.
     */
    @Test
    public void testGetComponents_ReturnValue_Copy()
    {
        final List<IComponent> components = table_.getComponents( new Point( 0, 0 ) );
        final int expectedComponentsSize = components.size();

        table_.getTabletop().addComponent( createUniqueComponent() );

        assertEquals( expectedComponentsSize, components.size() );
    }

    /**
     * Ensures the {@link ITable#getComponents} method does not return
     * {@code null}.
     */
    @Test
    public void testGetComponents_ReturnValue_NonNull()
    {
        assertNotNull( table_.getComponents( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ) );
    }

    /**
     * Ensures the {@link ITable#getExtension} method throws an exception when
     * passed a {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtension_Type_Null()
    {
        table_.getExtension( null );
    }

    /**
     * Ensures the {@link ITable#getTableEnvironment} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTableEnvironment_ReturnValue_NonNull()
    {
        assertNotNull( table_.getTableEnvironment() );
    }

    /**
     * Ensures the {@link ITable#getTabletop} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTabletop_ReturnValue_NonNull()
    {
        assertNotNull( table_.getTabletop() );
    }
}
