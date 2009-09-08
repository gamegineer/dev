/*
 * AbstractCommandletHelpTestCase.java
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
 * Created on Oct 24, 2008 at 11:15:51 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandletHelp}
 * interface.
 */
public abstract class AbstractCommandletHelpTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet help under test in the fixture. */
    private ICommandletHelp help_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommandletHelpTestCase}
     * class.
     */
    protected AbstractCommandletHelpTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the commandlet help to be tested.
     * 
     * @return The commandlet help to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICommandletHelp createCommandletHelp()
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
        help_ = createCommandletHelp();
        assertNotNull( help_ );
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
        help_ = null;
    }

    /**
     * Ensures the {@code getDetailedDescription} method does not return {@code
     * null}.
     */
    @Test
    public void testGetDetailedDescription_ReturnValue_NonNull()
    {
        assertNotNull( help_.getDetailedDescription() );
    }

    /**
     * Ensures the {@code getSynopsis} method does not return {@code null}
     * value.
     */
    @Test
    public void testGetSynopsis_ReturnValue_NonNull()
    {
        assertNotNull( help_.getSynopsis() );
    }
}
