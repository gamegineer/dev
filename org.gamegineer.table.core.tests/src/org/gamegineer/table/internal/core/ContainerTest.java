/*
 * ContainerTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.TestComponentStrategies;
import org.gamegineer.table.core.TestComponentSurfaceDesigns;
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
    private Container container_;

    /** The table environment for use in the fixture. */
    private TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerTest} class.
     */
    public ContainerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private Component createUniqueComponent()
    {
        final Component component = new Component( tableEnvironment_, TestComponentStrategies.createUniqueComponentStrategy() );
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }

        return component;
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
        tableEnvironment_ = new TableEnvironment( new SingleThreadedTableEnvironmentContext() );
        container_ = new Container( tableEnvironment_, TestComponentStrategies.createUniqueContainerStrategy() );
    }

    /**
     * Ensures the {@link Container#getChildPath} method throws an exception
     * when passed a component that is absent from the component collection and
     * the container is associated with a table.
     */
    @Test( expected = AssertionError.class )
    public void testGetChildPath_Component_Absent_AssociatedTable()
    {
        final ITable table = tableEnvironment_.createTable();
        table.getTabletop().addComponent( container_ );

        container_.getLock().lock();
        try
        {
            container_.getChildPath( createUniqueComponent() );
        }
        finally
        {
            container_.getLock().unlock();
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
        final ITable table = tableEnvironment_.createTable();
        table.getTabletop().addComponent( container_ );
        final Component component = createUniqueComponent();
        container_.addComponent( createUniqueComponent() );
        container_.addComponent( component );
        container_.addComponent( createUniqueComponent() );

        final ComponentPath actualValue;
        container_.getLock().lock();
        try
        {
            actualValue = container_.getChildPath( component );
        }
        finally
        {
            container_.getLock().unlock();
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
        final Component component = createUniqueComponent();
        container_.addComponent( component );

        final ComponentPath actualValue;
        container_.getLock().lock();
        try
        {
            actualValue = container_.getChildPath( component );
        }
        finally
        {
            container_.getLock().unlock();
        }

        assertNull( actualValue );
    }
}
