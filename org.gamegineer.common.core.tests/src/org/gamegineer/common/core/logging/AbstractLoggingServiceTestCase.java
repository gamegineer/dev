/*
 * AbstractLoggingServiceTestCase.java
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
 * Created on May 14, 2008 at 10:59:39 PM.
 */

package org.gamegineer.common.core.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.gamegineer.common.internal.core.Activator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.logging.ILoggingService} interface.
 */
public abstract class AbstractLoggingServiceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The logging service under test in the fixture. */
    private ILoggingService service_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractLoggingServiceTestCase}
     * class.
     */
    protected AbstractLoggingServiceTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the logging service to be tested.
     * 
     * @return The logging service to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ILoggingService createLogService()
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
        service_ = createLogService();
        assertNotNull( service_ );
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
        service_ = null;
    }

    /**
     * Ensures the {@code getLogger(Bundle)} method throws an exception when
     * passed a {@code null} bundle.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLoggerFromBundle_Bundle_Null()
    {
        service_.getLogger( null );
    }

    /**
     * Ensures the {@code getLogger(Bundle)} method does not return {@code null}
     * .
     */
    @Test
    public void testGetLoggerFromBundle_ReturnValue_NonNull()
    {
        assertNotNull( service_.getLogger( Activator.getDefault().getBundleContext().getBundle() ) );
    }

    /**
     * Ensures the {@code getLogger(Bundle, String)} method throws an exception
     * when passed a {@code null} bundle.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLoggerFromBundleAndName_Bundle_Null()
    {
        service_.getLogger( null, "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLogger(Bundle, String)} method returns the default
     * logger when passed an empty name.
     */
    @Test
    public void testGetLoggerFromBundleAndName_Name_Empty()
    {
        final Bundle bundle = Activator.getDefault().getBundleContext().getBundle();

        assertSame( service_.getLogger( bundle ), service_.getLogger( bundle, "" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLogger(Bundle, String)} method returns the default
     * logger when passed a {@code null} name.
     */
    @Test
    public void testGetLoggerFromBundleAndName_Name_Null()
    {
        final Bundle bundle = Activator.getDefault().getBundleContext().getBundle();

        assertSame( service_.getLogger( bundle ), service_.getLogger( bundle, null ) );
    }

    /**
     * Ensures the {@code getLogger(Bundle, String)} method does not return
     * {@code null}.
     */
    @Test
    public void testGetLoggerFromBundleAndName_ReturnValue_NonNull()
    {
        assertNotNull( service_.getLogger( Activator.getDefault().getBundleContext().getBundle(), "name" ) ); //$NON-NLS-1$
    }
}
