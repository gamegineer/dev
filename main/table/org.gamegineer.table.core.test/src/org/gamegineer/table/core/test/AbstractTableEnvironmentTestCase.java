/*
 * AbstractTableEnvironmentTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on May 19, 2012 at 9:34:42 PM.
 */

package org.gamegineer.table.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableEnvironment} interface.
 */
@NonNullByDefault( false )
public abstract class AbstractTableEnvironmentTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment under test in the fixture. */
    private ITableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableEnvironmentTestCase} class.
     */
    protected AbstractTableEnvironmentTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table environment to be tested.
     * 
     * @param context
     *        The table environment context; must not be {@code null}.
     * 
     * @return The table environment to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @NonNull
    protected abstract ITableEnvironment createTableEnvironment(
        @NonNull
        final ITableEnvironmentContext context )
        throws Exception;

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
        tableEnvironment_ = createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
        assertNotNull( tableEnvironment_ );
    }

    /**
     * Ensures the {@link ITableEnvironment#createComponent(IComponentStrategy)}
     * method returns a component that is associated with the table environment.
     */
    @Test
    public void testCreateComponentFromStrategy_ReturnValue_AssociatedWithTableEnvironment()
    {
        final IComponent component = tableEnvironment_.createComponent( TestComponentStrategies.createUniqueComponentStrategy() );

        assertNotNull( component );
        assertEquals( tableEnvironment_, component.getTableEnvironment() );
    }

    /**
     * Ensures the {@link ITableEnvironment#createContainer} method returns a
     * container that is associated with the table environment.
     */
    @Test
    public void testCreateContainer_ReturnValue_AssociatedWithTableEnvironment()
    {
        final IContainer container = tableEnvironment_.createContainer( TestComponentStrategies.createUniqueContainerStrategy() );

        assertNotNull( container );
        assertEquals( tableEnvironment_, container.getTableEnvironment() );
    }

    /**
     * Ensures the {@link ITableEnvironment#createTable} method returns a table
     * that is associated with the table environment.
     */
    @Test
    public void testCreateTable_ReturnValue_AssociatedWithTableEnvironment()
    {
        final ITable table = tableEnvironment_.createTable();

        assertNotNull( table );
        assertEquals( tableEnvironment_, table.getTableEnvironment() );
    }
}
