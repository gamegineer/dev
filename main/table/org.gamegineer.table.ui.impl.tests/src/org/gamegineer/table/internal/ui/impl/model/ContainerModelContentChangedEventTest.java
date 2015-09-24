/*
 * ContainerModelContentChangedEventTest.java
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
 * Created on Sep 14, 2012 at 9:12:15 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.Optional;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ContainerModelContentChangedEvent} class.
 */
public final class ContainerModelContentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment model for use in the fixture. */
    private Optional<TableEnvironmentModel> tableEnvironmentModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerModelContentChangedEventTest} class.
     */
    public ContainerModelContentChangedEventTest()
    {
        tableEnvironmentModel_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new unique component model.
     * 
     * @return A new unique component model.
     */
    private ComponentModel createUniqueComponentModel()
    {
        return new ComponentModel( getTableEnvironmentModel(), TestComponents.createUniqueComponent( getTableEnvironmentModel().getTableEnvironment() ) );
    }

    /**
     * Creates a new unique container model.
     * 
     * @return A new unique container model.
     */
    private ContainerModel createUniqueContainerModel()
    {
        return new ContainerModel( getTableEnvironmentModel(), TestComponents.createUniqueContainer( getTableEnvironmentModel().getTableEnvironment() ) );
    }

    /**
     * Gets the fixture table environment model.
     * 
     * @return The fixture table environment model.
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
        tableEnvironmentModel_ = Optional.of( new TableEnvironmentModel( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) ) );
    }

    /**
     * Ensures the
     * {@link ContainerModelContentChangedEvent#ContainerModelContentChangedEvent}
     * constructor throws an exception when passed an illegal component model
     * index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_ComponentModelIndex_Illegal_Negative()
    {
        new ContainerModelContentChangedEvent( createUniqueContainerModel(), createUniqueComponentModel(), -1 );
    }
}
