/*
 * ServicesTest.java
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
 * Created on Feb 29, 2008 at 11:50:12 PM.
 */

package org.gamegineer.common.internal.core;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.Services} class.
 */
public final class ServicesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The services under test in the fixture. */
    private Services m_services;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServicesTest} class.
     */
    public ServicesTest()
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
        m_services = Services.getDefault();
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
        m_services = null;
    }

    /**
     * Ensures the {@code getComponentService} method does not return
     * {@code null}, which validates the component service was registered with
     * OSGi correctly.
     */
    @Test
    public void testGetComponentService_ReturnValue_NonNull()
    {
        assertNotNull( m_services.getComponentService() );
    }

    /**
     * Ensures the {@code getLoggingService} method does not return {@code null},
     * which validates the logging service was registered with OSGi correctly.
     */
    @Test
    public void testGetLoggingService_ReturnValue_NonNull()
    {
        assertNotNull( m_services.getLoggingService() );
    }

    /**
     * Ensures the {@code open} method throws an exception when passed a
     * {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_Context_Null()
    {
        m_services.open( null );
    }
}
