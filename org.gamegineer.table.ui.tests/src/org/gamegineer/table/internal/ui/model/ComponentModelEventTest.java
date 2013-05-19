/*
 * ComponentModelEventTest.java
 * Copyright 2008-2013 Gamegineer.org
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

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.ui.TestComponents;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentModelEvent} class.
 */
public final class ComponentModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model event under test in the fixture. */
    private ComponentModelEvent event_;

    /** The table environment for use in the fixture. */
    private ITableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelEventTest} class.
     */
    public ComponentModelEventTest()
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
        tableEnvironment_ = TableEnvironmentFactory.createTableEnvironment();
        event_ = new ComponentModelEvent( new ComponentModel( TestComponents.createUniqueComponent( tableEnvironment_ ) ) );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        tableEnvironment_.dispose();
    }

    /**
     * Ensures the {@link ComponentModelEvent#ComponentModelEvent} constructor
     * throws an exception when passed a {@code null} source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new ComponentModelEvent( null );
    }

    /**
     * Ensures the {@link ComponentModelEvent#getComponentModel} method does not
     * return {@code null} .
     */
    @Test
    public void testGetComponentModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getComponentModel() );
    }

    /**
     * Ensures the {@link ComponentModelEvent#getSource} method returns the same
     * instance as the {@code getComponentModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameComponentModel()
    {
        assertSame( event_.getComponentModel(), event_.getSource() );
    }
}
