/*
 * AdaptersTest.java
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
 * Created on Jul 1, 2008 at 11:43:34 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.memento.Adapters} class.
 */
public final class AdaptersTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The adapters under test in the fixture. */
    private Adapters adapters_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AdaptersTest} class.
     */
    public AdaptersTest()
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
        adapters_ = Adapters.getDefault();
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
        adapters_ = null;
    }

    /**
     * Ensures the {@code register} method throws an exception when passed a
     * {@code null} adapter manager.
     */
    @Test( expected = NullPointerException.class )
    public void testRegister_Manager_Null()
    {
        adapters_.register( null );
    }

    /**
     * Ensures the {@code unregister} method throws an exception when passed a
     * {@code null} adapter manager.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregister_Manager_Null()
    {
        adapters_.unregister( null );
    }
}
