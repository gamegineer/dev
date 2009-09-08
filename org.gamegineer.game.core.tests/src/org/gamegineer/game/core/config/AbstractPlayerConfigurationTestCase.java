/*
 * AbstractPlayerConfigurationTestCase.java
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
 * Created on Jan 17, 2009 at 10:39:13 PM.
 */

package org.gamegineer.game.core.config;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.config.IPlayerConfiguration} interface.
 */
public abstract class AbstractPlayerConfigurationTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player configuration under test in the fixture. */
    private IPlayerConfiguration config_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractPlayerConfigurationTestCase} class.
     */
    protected AbstractPlayerConfigurationTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the player configuration to be tested.
     * 
     * @return The player configuration to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IPlayerConfiguration createPlayerConfiguration()
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
        config_ = createPlayerConfiguration();
        assertNotNull( config_ );
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
        config_ = null;
    }

    /**
     * Ensures the {@code getRoleId} method does not return {@code null}.
     */
    @Test
    public void testGetRole_ReturnValue_NonNull()
    {
        assertNotNull( config_.getRoleId() );
    }

    /**
     * Ensures the {@code getUserId} method does not return {@code null}.
     */
    @Test
    public void testGetUser_ReturnValue_NonNull()
    {
        assertNotNull( config_.getUserId() );
    }
}
