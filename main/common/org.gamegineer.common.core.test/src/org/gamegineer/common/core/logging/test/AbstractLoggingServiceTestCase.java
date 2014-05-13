/*
 * AbstractLoggingServiceTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.common.core.logging.ILoggingService;
import org.gamegineer.common.internal.core.test.Activator;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ILoggingService} interface.
 */
@NonNullByDefault( false )
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
    @NonNull
    protected abstract ILoggingService createLoggingService()
        throws Exception;

    /**
     * Gets the associated bundle.
     * 
     * @return The associated bundle; never {@code null}.
     */
    @NonNull
    private static Bundle getBundle()
    {
        return nonNull( Activator.getDefault().getBundleContext().getBundle() );
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
        service_ = createLoggingService();
        assertNotNull( service_ );
    }

    /**
     * Ensures the {@link ILoggingService#getLogger(Bundle, String)} method
     * returns the default logger when passed an empty name.
     */
    @Test
    public void testGetLoggerFromBundleAndName_Name_Empty()
    {
        final Bundle bundle = getBundle();

        assertSame( service_.getLogger( bundle ), service_.getLogger( bundle, "" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link ILoggingService#getLogger(Bundle, String)} method
     * returns the default logger when passed a {@code null} name.
     */
    @Test
    public void testGetLoggerFromBundleAndName_Name_Null()
    {
        final Bundle bundle = getBundle();

        assertSame( service_.getLogger( bundle ), service_.getLogger( bundle, null ) );
    }
}
