/*
 * AbstractLoggingServiceTestCase.java
 * Copyright 2008 Gamegineer.org
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

package org.gamegineer.common.core.services.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.gamegineer.common.internal.core.Activator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.services.logging.ILoggingService}
 * interface.
 */
public abstract class AbstractLoggingServiceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The log service under test in the fixture. */
    private ILoggingService m_service;


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
     * Creates the log service to be tested.
     * 
     * @return The log service to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract ILoggingService createLogService();

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
        m_service = createLogService();
        assertNotNull( m_service );
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
        m_service = null;
    }

    /**
     * Ensures the {@code getLogger(Bundle)} method throws an exception when
     * passed a {@code null} bundle.
     */
    @Test( expected = NullPointerException.class )
    public void testGetDefaultLogger_Bundle_Null()
    {
        m_service.getLogger( null );
    }

    /**
     * Ensures the {@code getLogger(Bundle)} method does not return {@code null}.
     */
    @Test
    public void testGetDefaultLogger_ReturnValue_NonNull()
    {
        assertNotNull( m_service.getLogger( Activator.getDefault().getBundleContext().getBundle() ) );
    }

    /**
     * Ensures the {@code getLogger(Bundle,String)} method throws an exception
     * when passed a {@code null} bundle.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLogger_Bundle_Null()
    {
        m_service.getLogger( null, "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLogger(Bundle,String)} method returns the default
     * logger when passed an empty name.
     */
    @Test
    public void testGetLogger_Name_Empty()
    {
        final Bundle bundle = Activator.getDefault().getBundleContext().getBundle();
        assertSame( m_service.getLogger( bundle ), m_service.getLogger( bundle, "" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLogger(Bundle,String)} method returns the default
     * logger when passed a {@code null} name.
     */
    @Test
    public void testGetLogger_Name_Null()
    {
        final Bundle bundle = Activator.getDefault().getBundleContext().getBundle();
        assertSame( m_service.getLogger( bundle ), m_service.getLogger( bundle, null ) );
    }

    /**
     * Ensures the {@code getLogger(Bundle,String)} method does not return
     * {@code null}.
     */
    @Test
    public void testGetLogger_ReturnValue_NonNull()
    {
        assertNotNull( m_service.getLogger( Activator.getDefault().getBundleContext().getBundle(), "name" ) ); //$NON-NLS-1$
    }
}
