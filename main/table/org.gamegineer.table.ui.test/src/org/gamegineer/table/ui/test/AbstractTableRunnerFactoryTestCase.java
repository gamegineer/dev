/*
 * AbstractTableRunnerFactoryTestCase.java
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
 * Created on Dec 20, 2013 at 9:20:02 PM.
 */

package org.gamegineer.table.ui.test;

import static org.junit.Assert.assertNotNull;
import java.util.Collections;
import org.gamegineer.table.ui.ITableRunnerFactory;
import org.gamegineer.table.ui.TableAdvisor;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableRunnerFactory} interface.
 */
public abstract class AbstractTableRunnerFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table runner factory under test in the fixture. */
    private ITableRunnerFactory tableRunnerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableRunnerFactoryTestCase} class.
     */
    protected AbstractTableRunnerFactoryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table runner factory to be tested.
     * 
     * @return The table runner factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableRunnerFactory createTableRunnerFactory()
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
        tableRunnerFactory_ = createTableRunnerFactory();
        assertNotNull( tableRunnerFactory_ );
    }

    /**
     * Ensures the {@link ITableRunnerFactory#createTableRunner} method throws
     * an exception when passed a {@code null} advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableRunner_Advisor_Null()
    {
        tableRunnerFactory_.createTableRunner( null );
    }

    /**
     * Ensures the {@link ITableRunnerFactory#createTableRunner} method does not
     * return {@code null}.
     */
    @Test
    public void testCreateTableRunner_ReturnValue_NonNull()
    {
        assertNotNull( tableRunnerFactory_.createTableRunner( new TableAdvisor( Collections.<String>emptyList() ) ) );
    }
}
