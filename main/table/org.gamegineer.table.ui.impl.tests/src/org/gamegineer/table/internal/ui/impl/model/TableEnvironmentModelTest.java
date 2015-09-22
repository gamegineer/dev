/*
 * TableEnvironmentModelTest.java
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
 * Created on May 31, 2013 at 10:33:40 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.Optional;
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
    private Optional<TableEnvironmentModel> tableEnvironmentModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentModelTest}
     * class.
     */
    public TableEnvironmentModelTest()
    {
        tableEnvironmentModel_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table environment.
     * 
     * @return A new table environment; never {@code null}.
     */
    private static ITableEnvironment createTableEnvironment()
    {
        return TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
    }

    /**
     * Gets the table environment model under test in the fixture.
     * 
     * @return The table environment model under test in the fixture; never
     *         {@code null}.
     */
    private TableEnvironmentModel getTableEnvironmentModel()
    {
        return tableEnvironmentModel_.get();
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
        tableEnvironmentModel_ = Optional.of( new TableEnvironmentModel( createTableEnvironment() ) );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createComponentModel} method
     * throws an exception when passed an illegal component that was created by
     * a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentModel_Component_Illegal_CreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentModel tableEnvironmentModel = getTableEnvironmentModel();
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();

        tableEnvironmentModel.createComponentModel( TestComponents.createUniqueComponent( otherTableEnvironment ) );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createContainerModel} method
     * throws an exception when passed an illegal container that was created by
     * a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateContainerModel_Container_Illegal_CreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentModel tableEnvironmentModel = getTableEnvironmentModel();
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();

        tableEnvironmentModel.createContainerModel( TestComponents.createUniqueContainer( otherTableEnvironment ) );
    }

    /**
     * Ensures the {@link TableEnvironmentModel#createTableModel} method throws
     * an exception when passed an illegal table that was created by a different
     * table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateTableModel_Table_Illegal_CreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentModel tableEnvironmentModel = getTableEnvironmentModel();
        final ITableEnvironment otherTableEnvironment = createTableEnvironment();

        tableEnvironmentModel.createTableModel( otherTableEnvironment.createTable(), TestTableNetworks.createTableNetwork() );
    }
}
