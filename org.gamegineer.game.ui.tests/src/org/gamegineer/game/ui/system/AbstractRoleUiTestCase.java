/*
 * AbstractRoleUiTestCase.java
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
 * Created on Feb 27, 2009 at 9:51:29 PM.
 */

package org.gamegineer.game.ui.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.ui.system.IRoleUi} interface.
 */
public abstract class AbstractRoleUiTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role on which the user interface is based. */
    private IRole m_role;

    /** The role user interface under test in the fixture. */
    private IRoleUi m_roleUi;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRoleUiTestCase} class.
     */
    protected AbstractRoleUiTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the role user interface to be tested.
     * 
     * @param role
     *        The role on which the user interface is based; must not be
     *        {@code null}.
     * 
     * @return The role user interface to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code role} is {@code null}.
     */
    /* @NonNull */
    protected abstract IRoleUi createRoleUi(
        /* @NonNull */
        IRole role )
        throws Exception;

    /**
     * Gets the role on which the user interface is based.
     * 
     * @return The role on which the user interface is based; never {@code null}.
     */
    /* @NonNull */
    protected final IRole getRole()
    {
        assertNotNull( m_role );
        return m_role;
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
        m_role = GameSystems.createUniqueRole();
        m_roleUi = createRoleUi( m_role );
        assertNotNull( m_roleUi );
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
        m_roleUi = null;
        m_role = null;
    }

    /**
     * Ensures the {@code getId} method returns the expected value.
     */
    @Test
    public void testGetId()
    {
        assertEquals( m_role.getId(), m_roleUi.getId() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( m_roleUi.getName() );
    }
}
