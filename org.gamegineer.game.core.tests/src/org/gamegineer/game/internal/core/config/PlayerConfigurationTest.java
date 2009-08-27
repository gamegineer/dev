/*
 * PlayerConfigurationTest.java
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
 * Created on Jan 17, 2009 at 10:55:23 PM.
 */

package org.gamegineer.game.internal.core.config;

import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.config.PlayerConfiguration} class.
 */
public final class PlayerConfigurationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerConfigurationTest} class.
     */
    public PlayerConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createPlayerConfiguration} method throws an exception
     * when one or more of its arguments results in an illegal player
     * configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal player configuration attributes is
     * located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal player configuration." )
    @Test( expected = IllegalArgumentException.class )
    public void testCreatePlayerConfiguration_PlayerConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createPlayerConfiguration} method throws an exception
     * when passed a {@code null} role identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreatePlayerConfiguration_RoleId_Null()
    {
        PlayerConfiguration.createPlayerConfiguration( null, "user-id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createPlayerConfiguration} method throws an exception
     * when passed a {@code null} user identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreatePlayerConfiguration_UserId_Null()
    {
        PlayerConfiguration.createPlayerConfiguration( "role-id", null ); //$NON-NLS-1$
    }
}
