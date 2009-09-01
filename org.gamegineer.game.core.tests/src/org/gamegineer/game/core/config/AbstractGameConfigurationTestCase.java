/*
 * AbstractGameConfigurationTestCase.java
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
 * Created on Jul 12, 2008 at 8:45:39 PM.
 */

package org.gamegineer.game.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.config.IGameConfiguration} interface.
 */
public abstract class AbstractGameConfigurationTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game configuration under test in the fixture. */
    private IGameConfiguration m_config;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameConfigurationTestCase} class.
     */
    protected AbstractGameConfigurationTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game configuration to be tested.
     * 
     * @return The game configuration to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameConfiguration createGameConfiguration()
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
        m_config = createGameConfiguration();
        assertNotNull( m_config );
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
        m_config = null;
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( m_config.getId() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( m_config.getName() );
    }

    /**
     * Ensures the {@code getPlayers} method returns a copy of the player
     * configuration list.
     */
    @Test
    public void testGetPlayers_ReturnValue_Copy()
    {
        final List<IPlayerConfiguration> playerConfigs = m_config.getPlayers();
        final int originalListSize = playerConfigs.size();

        playerConfigs.add( null );

        assertEquals( originalListSize, m_config.getPlayers().size() );
    }

    /**
     * Ensures the {@code getPlayers} method does not return {@code null}.
     */
    @Test
    public void testGetPlayers_ReturnValue_NonNull()
    {
        assertNotNull( m_config.getPlayers() );
    }

    /**
     * Ensures the {@code getSystem} method does not return {@code null}.
     */
    @Test
    public void testGetSystem_ReturnValue_NonNull()
    {
        assertNotNull( m_config.getSystem() );
    }
}
