/*
 * ComponentModelEventTest.java
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
 * Created on Dec 25, 2009 at 10:25:12 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.junit.Assert.assertSame;
import java.util.Optional;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentModelEvent} class.
 */
public final class ComponentModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model event under test in the fixture. */
    private Optional<ComponentModelEvent> event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelEventTest} class.
     */
    public ComponentModelEventTest()
    {
        event_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component model event under test in the fixture.
     * 
     * @return The component model event under test in the fixture.
     */
    private ComponentModelEvent getEvent()
    {
        return event_.get();
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
        final TableEnvironmentModel tableEnvironmentModel = new TableEnvironmentModel( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );
        event_ = Optional.of( new ComponentModelEvent( new ComponentModel( tableEnvironmentModel, TestComponents.createUniqueComponent( tableEnvironmentModel.getTableEnvironment() ) ) ) );
    }

    /**
     * Ensures the {@link ComponentModelEvent#getSource} method returns the same
     * instance as the {@code getComponentModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameComponentModel()
    {
        final ComponentModelEvent event = getEvent();

        assertSame( event.getComponentModel(), event.getSource() );
    }
}
