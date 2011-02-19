/*
 * LoggingServiceTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jun 10, 2010 at 11:02:38 PM.
 */

package org.gamegineer.common.internal.core.logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.logging.LoggingService} class.
 */
public final class LoggingServiceTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The logging service under test in the fixture. */
    private LoggingService loggingService_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingServiceTest} class.
     */
    public LoggingServiceTest()
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
        loggingService_ = new LoggingService();
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
        loggingService_ = null;
    }

    /**
     * Ensures the {@code activate} method throws an exception when passed a
     * {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testActivate_BundleContext_Null()
    {
        loggingService_.activate( null );
    }
}
