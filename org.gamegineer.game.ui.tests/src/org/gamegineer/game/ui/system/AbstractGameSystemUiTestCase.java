/*
 * AbstractGameSystemUiTestCase.java
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
 * Created on Feb 27, 2009 at 9:06:25 PM.
 */

package org.gamegineer.game.ui.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.ui.system.IGameSystemUi} interface.
 */
public abstract class AbstractGameSystemUiTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system on which the user interface is based. */
    private IGameSystem m_gameSystem;

    /** The game system user interface under test in the fixture. */
    private IGameSystemUi m_gameSystemUi;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractGameSystemUiTestCase}
     * class.
     */
    protected AbstractGameSystemUiTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game system user interface to be tested.
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * 
     * @return The game system user interface to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    /* @NonNull */
    protected abstract IGameSystemUi createGameSystemUi(
        /* @NonNull */
        IGameSystem gameSystem )
        throws Exception;

    /**
     * Gets the game system on which the user interface is based.
     * 
     * @return The game system on which the user interface is based; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final IGameSystem getGameSystem()
    {
        assertNotNull( m_gameSystem );
        return m_gameSystem;
    }

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
        m_gameSystem = GameSystems.createUniqueGameSystem();
        m_gameSystemUi = createGameSystemUi( m_gameSystem );
        assertNotNull( m_gameSystemUi );
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
        m_gameSystemUi = null;
        m_gameSystem = null;
    }

    /**
     * Ensures the {@code getId} method returns the expected value.
     */
    @Test
    public void testGetId()
    {
        assertEquals( m_gameSystem.getId(), m_gameSystemUi.getId() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( m_gameSystemUi.getName() );
    }

    /**
     * Ensures the {@code getRoles} method returns a copy of the role user
     * interface list.
     */
    @Test
    public void testGetRoles_ReturnValue_Copy()
    {
        final List<IRoleUi> roleUis = m_gameSystemUi.getRoles();
        final int originalRoleUisSize = roleUis.size();

        roleUis.add( null );

        assertEquals( originalRoleUisSize, m_gameSystemUi.getRoles().size() );
    }

    /**
     * Ensures the {@code getRoles} method does not return {@code null}.
     */
    @Test
    public void testGetRoles_ReturnValue_NonNull()
    {
        assertNotNull( m_gameSystemUi.getRoles() );
    }
}
