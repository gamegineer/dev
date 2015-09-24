/*
 * ContainerTest.java
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
 * Created on Jun 12, 2012 at 8:08:48 PM.
 */

package org.gamegineer.table.internal.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Optional;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestComponentStrategies;
import org.gamegineer.table.core.test.TestComponentSurfaceDesigns;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Container} class.
 */
public final class ContainerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container under test in the fixture. */
    private Optional<Container> container_;

    /** The table environment for use in the fixture. */
    private Optional<TableEnvironment> tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerTest} class.
     */
    public ContainerTest()
    {
        container_ = Optional.empty();
        tableEnvironment_ = Optional.empty();
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
    private Component createUniqueComponent()
    {
        final Component component = new Component( getTableEnvironment(), TestComponentStrategies.createUniqueComponentStrategy() );
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }

        return component;
    }

    /**
     * Gets the container under test in the fixture.
     * 
     * @return The container under test in the fixture.
     */
    private Container getContainer()
    {
        return container_.get();
    }

    /**
     * Gets the fixture table environment.
     * 
     * @return The fixture table environment.
     */
    private TableEnvironment getTableEnvironment()
    {
        return tableEnvironment_.get();
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
        final TableEnvironment tableEnvironment = new TableEnvironment( new SingleThreadedTableEnvironmentContext() );
        tableEnvironment_ = Optional.of( tableEnvironment );
        container_ = Optional.of( new Container( tableEnvironment, TestComponentStrategies.createUniqueContainerStrategy() ) );
    }

    /**
     * Ensures the {@link Container#getChildPath} method throws an exception
     * when passed a component that is absent from the component collection and
     * the container is associated with a table.
     */
    @Test( expected = AssertionError.class )
    public void testGetChildPath_Component_Absent_AssociatedTable()
    {
        final Container container = getContainer();
        final ITable table = getTableEnvironment().createTable();
        table.getTabletop().addComponent( container );

        container.getLock().lock();
        try
        {
            container.getChildPath( createUniqueComponent() );
        }
        finally
        {
            container.getLock().unlock();
        }
    }

    /**
     * Ensures the {@link Container#getChildPath} method returns the correct
     * value when passed a component present in the component collection and the
     * container is associated with a table.
     */
    @Test
    public void testGetChildPath_Component_Present_AssociatedTable()
    {
        final Container container = getContainer();
        final ITable table = getTableEnvironment().createTable();
        table.getTabletop().addComponent( container );
        final Component component = createUniqueComponent();
        container.addComponent( createUniqueComponent() );
        container.addComponent( component );
        container.addComponent( createUniqueComponent() );

        final ComponentPath actualValue;
        container.getLock().lock();
        try
        {
            actualValue = container.getChildPath( component );
        }
        finally
        {
            container.getLock().unlock();
        }

        assertEquals( new ComponentPath( new ComponentPath( new ComponentPath( null, 0 ), 0 ), 1 ), actualValue );
    }

    /**
     * Ensures the {@link Container#getChildPath} method returns {@code null}
     * when the container is not associated with a table.
     */
    @Test
    public void testGetChildPath_Container_NoAssociatedTable()
    {
        final Container container = getContainer();
        final Component component = createUniqueComponent();
        container.addComponent( component );

        final ComponentPath actualValue;
        container.getLock().lock();
        try
        {
            actualValue = container.getChildPath( component );
        }
        finally
        {
            container.getLock().unlock();
        }

        assertNull( actualValue );
    }
}
