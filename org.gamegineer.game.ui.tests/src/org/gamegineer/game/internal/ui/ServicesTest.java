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
 * Created on Feb 14, 2009 at 11:24:50 PM.
 */

package org.gamegineer.game.internal.ui;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.ui.Services}
 * class.
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
     * Ensures the {@code getGameSystemUiRegistry} method does not return
     * {@code null}, which validates the game system user interface registry
     * service was registered with OSGi correctly.
     */
    @Test
    public void testGetGameSystemUiRegistry_ReturnValue_NonNull()
    {
        assertNotNull( m_services.getGameSystemUiRegistry() );
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
