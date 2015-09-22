/*
 * MainModelEventTest.java
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
 * Created on Apr 13, 2010 at 10:06:26 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.junit.Assert.assertSame;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.net.test.TestTableNetworks;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link MainModelEvent} class.
 */
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
public final class MainModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model event under test in the fixture. */
    private MainModelEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelEventTest} class.
     */
    public MainModelEventTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        final TableEnvironmentModel tableEnvironmentModel = new TableEnvironmentModel( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );
        event_ = new MainModelEvent( new MainModel( new TableModel( tableEnvironmentModel, tableEnvironmentModel.getTableEnvironment().createTable(), TestTableNetworks.createTableNetwork() ) ) );
    }

    /**
     * Ensures the {@link MainModelEvent#getSource} method returns the same
     * instance as the {@link MainModelEvent#getMainModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameMainModel()
    {
        assertSame( event_.getMainModel(), event_.getSource() );
    }
}
