/*
 * GameClientConfigurationTest.java
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
 * Created on Mar 6, 2009 at 11:46:36 PM.
 */

package org.gamegineer.client.internal.core.config;

import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.config.GameClientConfiguration}
 * class.
 */
public final class GameClientConfigurationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientConfigurationTest}
     * class.
     */
    public GameClientConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGameClientConfiguration} method throws an
     * exception when one or more of its arguments results in an illegal game
     * client configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game client configuration attributes
     * is located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal game client configuration." )
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameClientConfiguration_GameClientConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameClientConfiguration} method throws an
     * exception when passed a {@code null} game system user interface source.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameClientConfiguration_GameSystemUiSource_Null()
    {
        GameClientConfiguration.createGameClientConfiguration( null );
    }
}
