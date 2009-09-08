/*
 * AbstractGameServerConfigurationTestCase.java
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
 * Created on Nov 30, 2008 at 10:25:08 PM.
 */

package org.gamegineer.server.core.config;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.server.core.config.IGameServerConfiguration} interface.
 */
public abstract class AbstractGameServerConfigurationTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game server configuration under test in the fixture. */
    private IGameServerConfiguration config_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameServerConfigurationTestCase} class.
     */
    protected AbstractGameServerConfigurationTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game server configuration to be tested.
     * 
     * @return The game server configuration to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameServerConfiguration createGameServerConfiguration()
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
        config_ = createGameServerConfiguration();
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
     * Ensures the {@code getGameSystemSource} method does not return {@code
     * null}.
     */
    @Test
    public void testGetGameSystemSource_ReturnValue_NonNull()
    {
        assertNotNull( config_.getGameSystemSource() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( config_.getName() );
    }
}
