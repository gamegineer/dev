/*
 * MainModelTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 6, 2009 at 11:57:40 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.ui.TableAdvisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModel} class.
 */
public final class MainModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model under test in the fixture. */
    private MainModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelTest} class.
     */
    public MainModelTest()
    {
        super();
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
        model_ = new MainModel( new TableAdvisor() );
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
        model_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Advisor_Null()
    {
        new MainModel( null );
    }

    /**
     * Ensures the {@code getTable} method does not return {@code null}.
     */
    @Test
    public void testGetTable_ReturnValue_NonNull()
    {
        assertNotNull( model_.getTable() );
    }

    /**
     * Ensures the {@code getVersion} method does not return {@code null}.
     */
    @Test
    public void testGetVersion_ReturnValue_NonNull()
    {
        assertNotNull( model_.getVersion() );
    }
}
