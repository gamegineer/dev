/*
 * AbstractPlayerTestCase.java
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
 * Created on Jul 21, 2008 at 10:51:13 PM.
 */

package org.gamegineer.game.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.IPlayer} interface.
 */
public abstract class AbstractPlayerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player under test in the fixture. */
    private IPlayer m_player;

    /** The player configuration for the player under test. */
    private IPlayerConfiguration m_playerConfig;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractPlayerTestCase} class.
     */
    protected AbstractPlayerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the player to be tested.
     * 
     * @param playerConfig
     *        The player configuration; must not be {@code null}.
     * 
     * @return The player to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code playerConfig} is {@code null}.
     */
    /* @NonNull */
    protected abstract IPlayer createPlayer(
        /* @NonNull */
        IPlayerConfiguration playerConfig )
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
        m_playerConfig = Configurations.createUniquePlayerConfiguration();
        m_player = createPlayer( m_playerConfig );
        assertNotNull( m_player );
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
        m_player = null;
        m_playerConfig = null;
    }

    /**
     * Ensures the {@code getRoleId} method returns the expected role
     * identifier.
     */
    @Test
    public void testGetRoleId()
    {
        assertEquals( m_playerConfig.getRoleId(), m_player.getRoleId() );
    }

    /**
     * Ensures the {@code getUserId} method returns the expected user
     * identifier.
     */
    @Test
    public void testGetUserId()
    {
        assertEquals( m_playerConfig.getUserId(), m_player.getUserId() );
    }
}
