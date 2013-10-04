/*
 * TableTest.java
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
 * Created on Jul 26, 2012 at 9:23:23 PM.
 */

package org.gamegineer.table.internal.core.impl;

import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestComponentStrategies;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Table} class.
 */
public final class TableTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table under test in the fixture. */
    private Table table_;

    /** The table environment for use in the fixture. */
    private TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableTest} class.
     */
    public TableTest()
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
        tableEnvironment_ = new TableEnvironment( new SingleThreadedTableEnvironmentContext() );
        table_ = new Table( tableEnvironment_ );
    }

    /**
     * Ensures the {@link Table#getChildPath} method throws an exception when
     * passed a component that is not the tabletop.
     */
    @Test( expected = AssertionError.class )
    public void testGetChildPath_Component_NotTabletop()
    {
        table_.getChildPath( new Component( tableEnvironment_, TestComponentStrategies.createUniqueComponentStrategy() ) );
    }
}