/*
 * GameClientTest.java
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
 * Created on Mar 7, 2009 at 9:49:53 PM.
 */

package org.gamegineer.client.internal.core;

import static org.junit.Assert.fail;
import org.gamegineer.client.core.GameClientConfigurationException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.GameClient} class.
 */
public final class GameClientTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientTest} class.
     */
    public GameClientTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGameClient} method throws an exception when the
     * game client configuration is illegal.
     */
    @Ignore( "Currently, there is no way to create an illegal game client configuration." )
    @Test( expected = GameClientConfigurationException.class )
    public void testCreateGameClient_GameClientConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameClient} method throws an exception when
     * passed a {@code null} game client configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameClient_GameClientConfig_Null()
        throws Exception
    {
        GameClient.createGameClient( null );
    }
}
