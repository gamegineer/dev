/*
 * ContainerModelEventTest.java
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
 * Created on Jan 26, 2010 at 10:45:12 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ContainerModelEvent} class.
 */
@NonNullByDefault( false )
public final class ContainerModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container model event under test in the fixture. */
    private ContainerModelEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerModelEventTest} class.
     */
    public ContainerModelEventTest()
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
        event_ = new ContainerModelEvent( new ContainerModel( tableEnvironmentModel, TestComponents.createUniqueContainer( tableEnvironmentModel.getTableEnvironment() ) ) );
    }

    /**
     * Ensures the {@link ContainerModelEvent#getContainerModel} method does not
     * return {@code null} .
     */
    @Test
    public void testGetContainerModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getContainerModel() );
    }

    /**
     * Ensures the {@link ContainerModelEvent#getSource} method returns the same
     * instance as the {@link ContainerModelEvent#getContainerModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameContainerModel()
    {
        assertSame( event_.getContainerModel(), event_.getSource() );
    }
}
