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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
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
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
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
    private Component createUniqueComponent()
    {
        final Component component = new Component( getTableEnvironment(), TestComponentStrategies.createUniqueComponentStrategy() );
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            assert orientation != null;
            component.setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }

        return component;
    }

    /**
     * Gets the container under test in the fixture.
     * 
     * @return The container under test in the fixture; never {@code null}.
     */
    private Container getContainer()
    {
        assertNotNull( container_ );
        return container_;
    }

    /**
     * Gets the fixture table environment.
     * 
     * @return The fixture table environment; never {@code null}.
     */
    private TableEnvironment getTableEnvironment()
    {
        assertNotNull( tableEnvironment_ );
        return tableEnvironment_;
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
        final TableEnvironment tableEnvironment = tableEnvironment_ = new TableEnvironment( new SingleThreadedTableEnvironmentContext() );
        container_ = new Container( tableEnvironment, TestComponentStrategies.createUniqueContainerStrategy() );
    }

    /**
     * Ensures the {@link Container#getChildPath} method throws an exception
     * when passed a component that is absent from the component collection and
     * the container is associated with a table.
     */
    @Test( expected = AssertionError.class )
    public void testGetChildPath_Component_Absent_AssociatedTable()
    {
        final ITable table = getTableEnvironment().createTable();
        table.getTabletop().addComponent( getContainer() );

        getContainer().getLock().lock();
        try
        {
            getContainer().getChildPath( createUniqueComponent() );
        }
        finally
        {
            getContainer().getLock().unlock();
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
        final ITable table = getTableEnvironment().createTable();
        table.getTabletop().addComponent( getContainer() );
        final Component component = createUniqueComponent();
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( component );
        getContainer().addComponent( createUniqueComponent() );

        final ComponentPath actualValue;
        getContainer().getLock().lock();
        try
        {
            actualValue = getContainer().getChildPath( component );
        }
        finally
        {
            getContainer().getLock().unlock();
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
        getContainer().addComponent( component );

        final ComponentPath actualValue;
        getContainer().getLock().lock();
        try
        {
            actualValue = getContainer().getChildPath( component );
        }
        finally
        {
            getContainer().getLock().unlock();
        }

        assertNull( actualValue );
    }
}
