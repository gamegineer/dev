/*
 * Assert.java
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
 * Created on Feb 28, 2009 at 12:00:13 AM.
 */

package org.gamegineer.game.ui.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;

/**
 * A collection of custom assertion methods for testing game system user
 * interface types.
 */
public final class Assert
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Assert} class.
     */
    private Assert()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that two game system user interfaces are equal.
     * 
     * <p>
     * If the two game system user interfaces are not equal, an {@code
     * AssertionError} without a message is thrown. If {@code expected} and
     * {@code actual} are {@code null}, they are considered equal.
     * </p>
     * 
     * @param expected
     *        The expected game system user interface; may be {@code null}.
     * @param actual
     *        The actual game system user interface; may be {@code null}.
     */
    public static void assertGameSystemUiEquals(
        /* @Nullable */
        final IGameSystemUi expected,
        /* @Nullable */
        final IGameSystemUi actual )
    {
        if( expected == actual )
        {
            return;
        }
        assertNotNull( expected );
        assertNotNull( actual );

        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getName(), actual.getName() );

        final List<IRoleUi> expectedRoleUis = expected.getRoles();
        final List<IRoleUi> actualRoleUis = actual.getRoles();
        assertEquals( expectedRoleUis.size(), actualRoleUis.size() );
        for( int index = 0, size = expectedRoleUis.size(); index < size; ++index )
        {
            assertRoleUiEquals( expectedRoleUis.get( index ), actualRoleUis.get( index ) );
        }
    }

    /**
     * Asserts that two role user interfaces are equal.
     * 
     * <p>
     * If the two role user interfaces are not equal, an {@code AssertionError}
     * without a message is thrown. If {@code expected} and {@code actual} are
     * {@code null}, they are considered equal.
     * </p>
     * 
     * @param expected
     *        The expected role user interface; may be {@code null}.
     * @param actual
     *        The actual role user interface; may be {@code null}.
     */
    public static void assertRoleUiEquals(
        /* @Nullable */
        final IRoleUi expected,
        /* @Nullable */
        final IRoleUi actual )
    {
        if( expected == actual )
        {
            return;
        }
        assertNotNull( expected );
        assertNotNull( actual );

        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getName(), actual.getName() );
    }
}
