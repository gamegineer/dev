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
 * Created on Jan 17, 2009 at 11:26:17 PM.
 */

package org.gamegineer.game.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A collection of custom assertion methods for testing game configurations.
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
     * Asserts that two player configurations are equal.
     * 
     * <p>
     * If the two player configurations are not equal, an {@code AssertionError}
     * without a message is thrown. If {@code expected} and {@code actual} are
     * {@code null}, they are considered equal.
     * </p>
     * 
     * @param expected
     *        The expected player configuration; may be {@code null}.
     * @param actual
     *        The actual player configuration; may be {@code null}.
     */
    public static void assertPlayerConfigurationEquals(
        /* @Nullable */
        final IPlayerConfiguration expected,
        /* @Nullable */
        final IPlayerConfiguration actual )
    {
        if( expected == actual )
        {
            return;
        }
        assertNotNull( expected );
        assertNotNull( actual );

        assertEquals( expected.getRoleId(), actual.getRoleId() );
    }
}
