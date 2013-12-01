/*
 * TableEnvironmentModelTest.java
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
 * Created on May 31, 2013 at 10:33:40 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.net.test.TestTableNetworks;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableEnvironmentModel} class.
 */
public final class TableEnvironmentModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment model under test in the fixture. */
    private TableEnvironmentModel tableEnvironmentModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentModelTest}
     * class.
     */
    public TableEnvironmentModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table environment.
     * 
     * @return A new table environment; never {@code null}.
     */
    /* @NonNull */
    private static ITableEnvironment createTableEnvironment()
    {
        return TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
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
        tableEnvironmentModel_ = new TableEnvironmentModel( createTableEnvironment() );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#TableEnvironmentModel}
     * constructor throws an exception when passed a {@code null} table
     * environment.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableEnvironment_Null()
    {
        new TableEnvironmentModel( null );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createComponentModel} method
     * throws an exception when passed an illegal component that was created by
     * a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentModel_Component_Illegal_CreatedByDifferentTableEnvironment()
    {
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();

        tableEnvironmentModel_.createComponentModel( TestComponents.createUniqueComponent( otherTableEnvironment ) );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createComponentModel} method
     * throws an exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentModel_Component_Null()
    {
        tableEnvironmentModel_.createComponentModel( null );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createContainerModel} method
     * throws an exception when passed an illegal container that was created by
     * a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateContainerModel_Container_Illegal_CreatedByDifferentTableEnvironment()
    {
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();

        tableEnvironmentModel_.createContainerModel( TestComponents.createUniqueContainer( otherTableEnvironment ) );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createContainerModel} method
     * throws an exception when passed a {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateContainerModel_Container_Null()
    {
        tableEnvironmentModel_.createContainerModel( null );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createTableModel} method throws
     * an exception when passed an illegal table that was created by a different
     * table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateTableModel_Table_Illegal_CreatedByDifferentTableEnvironment()
    {
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();

        tableEnvironmentModel_.createTableModel( otherTableEnvironment.createTable(), TestTableNetworks.createTableNetwork() );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createTableModel} method throws
     * an exception when passed a {@code null} table.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableModel_Table_Null()
    {
        tableEnvironmentModel_.createTableModel( null, TestTableNetworks.createTableNetwork() );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createTableModel} method throws
     * an exception when passed a {@code null} table network.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableModel_TableNetwork_Null()
    {
        tableEnvironmentModel_.createTableModel( tableEnvironmentModel_.getTableEnvironment().createTable(), null );
    }
}
