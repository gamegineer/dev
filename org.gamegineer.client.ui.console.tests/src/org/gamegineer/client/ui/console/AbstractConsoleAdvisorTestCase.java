/*
 * AbstractConsoleAdvisorTestCase.java
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
 * Created on Oct 10, 2008 at 11:33:05 PM.
 */

package org.gamegineer.client.ui.console;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.IConsoleAdvisor} interface.
 */
public abstract class AbstractConsoleAdvisorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The console advisor under test in the fixture. */
    private IConsoleAdvisor m_advisor;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractConsoleAdvisorTestCase}
     * class.
     */
    protected AbstractConsoleAdvisorTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the console advisor to be tested.
     * 
     * @return The console advisor to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IConsoleAdvisor createConsoleAdvisor()
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
        m_advisor = createConsoleAdvisor();
        assertNotNull( m_advisor );
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
        m_advisor = null;
    }

    /**
     * Ensures the {@code getApplicationArguments} method returns an immutable
     * collection.
     */
    @Test
    public void testGetApplicationArguments_ReturnValue_Immutable()
    {
        assertImmutableCollection( m_advisor.getApplicationArguments() );
    }

    /**
     * Ensures the {@code getApplicationArguments} method does not return
     * {@code null}.
     */
    @Test
    public void testGetApplicationArguments_ReturnValue_NonNull()
    {
        assertNotNull( m_advisor.getApplicationArguments() );
    }

    /**
     * Ensures the {@code getApplicationVersion} method does not return
     * {@code null}.
     */
    @Test
    public void testGetVersion_ReturnValue_NonNull()
    {
        assertNotNull( m_advisor.getApplicationVersion() );
    }
}
