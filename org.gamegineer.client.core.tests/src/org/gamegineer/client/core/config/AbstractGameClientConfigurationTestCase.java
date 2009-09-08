/*
 * AbstractGameClientConfigurationTestCase.java
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
 * Created on Mar 6, 2009 at 11:15:45 PM.
 */

package org.gamegineer.client.core.config;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.core.config.IGameClientConfiguration} interface.
 */
public abstract class AbstractGameClientConfigurationTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game client configuration under test in the fixture. */
    private IGameClientConfiguration config_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameClientConfigurationTestCase} class.
     */
    protected AbstractGameClientConfigurationTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game client configuration to be tested.
     * 
     * @return The game client configuration to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameClientConfiguration createGameClientConfiguration()
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
        config_ = createGameClientConfiguration();
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
     * Ensures the {@code getGameSystemUiSource} method does not return {@code
     * null}.
     */
    @Test
    public void testGetGameSystemUiSource_ReturnValue_NonNull()
    {
        assertNotNull( config_.getGameSystemUiSource() );
    }
}
