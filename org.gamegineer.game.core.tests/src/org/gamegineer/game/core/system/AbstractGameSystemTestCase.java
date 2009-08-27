/*
 * AbstractGameSystemTestCase.java
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
 * Created on Nov 13, 2008 at 8:11:39 PM.
 */

package org.gamegineer.game.core.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.system.IGameSystem} interface.
 */
public abstract class AbstractGameSystemTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system under test in the fixture. */
    private IGameSystem m_gameSystem;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractGameSystemTestCase}
     * class.
     */
    protected AbstractGameSystemTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game system to be tested.
     * 
     * @return The game system to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameSystem createGameSystem()
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
        m_gameSystem = createGameSystem();
        assertNotNull( m_gameSystem );
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
        m_gameSystem = null;
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( m_gameSystem.getId() );
    }

    /**
     * Ensures the {@code getRoles} method returns a copy of the role list.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetRoles_ReturnValue_Copy()
    {
        final List<IRole> roles = m_gameSystem.getRoles();
        final int originalRolesSize = roles.size();

        roles.add( null );

        assertEquals( originalRolesSize, m_gameSystem.getRoles().size() );
    }

    /**
     * Ensures the {@code getRoles} method does not return {@code null}.
     */
    @Test
    public void testGetRoles_ReturnValue_NonNull()
    {
        assertNotNull( m_gameSystem.getRoles() );
    }

    /**
     * Ensures the {@code getStages} method returns a copy of the stage list.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetStages_ReturnValue_Copy()
    {
        final List<IStage> stages = m_gameSystem.getStages();
        final int originalStagesSize = stages.size();

        stages.add( null );

        assertEquals( originalStagesSize, m_gameSystem.getStages().size() );
    }

    /**
     * Ensures the {@code getStages} method does not return {@code null}.
     */
    @Test
    public void testGetStages_ReturnValue_NonNull()
    {
        assertNotNull( m_gameSystem.getStages() );
    }
}
