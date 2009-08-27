/*
 * GameServerFactoryTest.java
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
 * Created on Dec 13, 2008 at 10:01:04 PM.
 */

package org.gamegineer.server.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.gamegineer.server.core.config.Configurations;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.core.GameServerFactory} class.
 */
public final class GameServerFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerFactoryTest} class.
     */
    public GameServerFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal game server configuration.
     * 
     * @return A legal game server configuration; never {@code null}.
     */
    /* @NonNull */
    private static IGameServerConfiguration createLegalGameServerConfiguration()
    {
        return Configurations.createGameServerConfiguration();
    }

    /**
     * Ensures the {@code createGameServer} method throws an exception when
     * passed an illegal game server configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Ignore( "Currently, there is no way to create an illegal game server configuration." )
    @Test( expected = GameServerConfigurationException.class )
    public void testCreateGameServer_GameServerConfig_Illegal()
        throws Exception
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameServer} method throws an exception if passed
     * a {@code null} game server configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameServer_GameServerConfig_Null()
        throws Exception
    {
        GameServerFactory.createGameServer( null );
    }

    /**
     * Ensures the {@code createGameServer} method does not return {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameServer_ReturnValue_NonNull()
        throws Exception
    {
        assertNotNull( GameServerFactory.createGameServer( createLegalGameServerConfiguration() ) );
    }

    /**
     * Ensures the {@code createNullGameServer} method does not return
     * {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateNullGameServer_ReturnValue_NonNull()
        throws Exception
    {
        assertNotNull( GameServerFactory.createNullGameServer() );
    }
}
