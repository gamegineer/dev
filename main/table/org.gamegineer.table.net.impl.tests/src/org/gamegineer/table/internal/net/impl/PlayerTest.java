/*
 * PlayerTest.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Aug 10, 2011 at 8:10:47 PM.
 */

package org.gamegineer.table.internal.net.impl;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.EnumSet;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.net.PlayerRole;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Player} class.
 */
@NonNullByDefault( false )
public final class PlayerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player under test in the fixture. */
    private Player player_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerTest} class.
     */
    public PlayerTest()
    {
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
        player_ = new Player( "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Player#addRoles} method adds player roles.
     */
    @Test
    public void testAddRoles()
    {
        assertEquals( 0, player_.getRoles().size() );

        player_.addRoles( nonNull( EnumSet.of( PlayerRole.LOCAL ) ) );

        assertEquals( 1, player_.getRoles().size() );
        assertTrue( player_.getRoles().contains( PlayerRole.LOCAL ) );
    }

    /**
     * Ensures the {@link Player#addRoles} method throws an exception when
     * passed an illegal role collection that contains a {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddRoles_Roles_Illegal_ContainsNullElement()
    {
        player_.addRoles( nonNull( Collections.<PlayerRole>singleton( null ) ) );
    }

    /**
     * Ensures the {@link Player#removeRoles} method removes player roles.
     */
    @Test
    public void testRemoveRoles()
    {
        player_.addRoles( nonNull( EnumSet.of( PlayerRole.LOCAL ) ) );
        final int originalRolesSize = player_.getRoles().size();
        assertTrue( player_.getRoles().contains( PlayerRole.LOCAL ) );

        player_.removeRoles( nonNull( EnumSet.of( PlayerRole.LOCAL ) ) );

        assertEquals( originalRolesSize - 1, player_.getRoles().size() );
        assertFalse( player_.getRoles().contains( PlayerRole.LOCAL ) );
    }

    /**
     * Ensures the {@link Player#removeRoles} method throws an exception when
     * passed an illegal role collection that contains a {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveRoles_Roles_Illegal_ContainsNullElement()
    {
        player_.removeRoles( nonNull( Collections.<PlayerRole>singleton( null ) ) );
    }
}
