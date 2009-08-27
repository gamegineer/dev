/*
 * SquareStateTest.java
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
 * Created on Jul 11, 2009 at 9:29:57 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.tictactoe.core.SquareId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.SquareState} class.
 */
public final class SquareStateTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The square state under test in the fixture. */
    private SquareState m_state;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SquareStateTest} class.
     */
    public SquareStateTest()
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
        m_state = new SquareState( SquareId.MIDDLE_CENTER );
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
        m_state = null;
    }

    /**
     * Ensures the {@code changeOwner} method returns a new square state that
     * reflects the new owner role identifier.
     */
    @Test
    public void testChangeOwner_State_Legal()
    {
        final String expectedOwnerRoleId = "role-id"; //$NON-NLS-1$
        final SquareState newState = m_state.changeOwner( expectedOwnerRoleId );

        assertEquals( expectedOwnerRoleId, newState.getOwnerRoleId() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new SquareState( null );
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( m_state.getId() );
    }
}
