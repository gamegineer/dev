/*
 * AbstractLoggingServiceTestCase.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on May 14, 2008 at 10:59:39 PM.
 */

package org.gamegineer.common.core.logging.test;

import static org.junit.Assert.assertSame;
import java.util.Optional;
import org.gamegineer.common.core.logging.ILoggingService;
import org.gamegineer.common.internal.core.test.Activator;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ILoggingService} interface.
 */
public abstract class AbstractLoggingServiceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The logging service under test in the fixture. */
    private Optional<ILoggingService> loggingService_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractLoggingServiceTestCase}
     * class.
     */
    protected AbstractLoggingServiceTestCase()
    {
        loggingService_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the logging service to be tested.
     * 
     * @return The logging service to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ILoggingService createLoggingService()
        throws Exception;

    /**
     * Gets the associated bundle.
     * 
     * @return The associated bundle.
     */
    private static Bundle getBundle()
    {
        return Activator.getDefault().getBundleContext().getBundle();
    }

    /**
     * Gets the logging service under test in the fixture.
     * 
     * @return The logging service under test in the fixture.
     */
    protected final ILoggingService getLoggingService()
    {
        return loggingService_.get();
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
        loggingService_ = Optional.of( createLoggingService() );
    }

    /**
     * Ensures the {@link ILoggingService#getLogger(Bundle, String)} method
     * returns the default logger when passed an empty name.
     */
    @Test
    public void testGetLoggerFromBundleAndName_Name_Empty()
    {
        final ILoggingService loggingService = getLoggingService();
        final Bundle bundle = getBundle();

        assertSame( loggingService.getLogger( bundle ), loggingService.getLogger( bundle, "" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link ILoggingService#getLogger(Bundle, String)} method
     * returns the default logger when passed a {@code null} name.
     */
    @Test
    public void testGetLoggerFromBundleAndName_Name_Null()
    {
        final ILoggingService loggingService = getLoggingService();
        final Bundle bundle = getBundle();

        assertSame( loggingService.getLogger( bundle ), loggingService.getLogger( bundle, null ) );
    }
}
