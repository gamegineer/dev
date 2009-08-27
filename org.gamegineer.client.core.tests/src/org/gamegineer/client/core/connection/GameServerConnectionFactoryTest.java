/*
 * GameServerConnectionFactoryTest.java
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
 * Created on Dec 24, 2008 at 9:36:11 PM.
 */

package org.gamegineer.client.core.connection;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import java.security.Principal;
import org.gamegineer.server.core.GameServerFactory;
import org.gamegineer.server.core.IGameServer;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.core.connection.GameServerConnectionFactory}
 * class.
 */
public final class GameServerConnectionFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerConnectionFactoryTest}
     * class.
     */
    public GameServerConnectionFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createLocalGameServerConnection} method throws an
     * exception when passed a {@code null} game server.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateLocalGameServerConnection_GameServer_Null()
    {
        GameServerConnectionFactory.createLocalGameServerConnection( null, createDummy( Principal.class ) );
    }

    /**
     * Ensures the {@code createLocalGameServerConnection} method does not
     * return {@code null}.
     */
    @Test
    public void testCreateLocalGameServerConnection_ReturnValue_NonNull()
    {
        assertNotNull( GameServerConnectionFactory.createLocalGameServerConnection( GameServerFactory.createNullGameServer(), createDummy( Principal.class ) ) );
    }

    /**
     * Ensures the {@code createLocalGameServerConnection} method throws an
     * exception when passed a {@code null} user principal.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateLocalGameServerConnection_UserPrincipal_Null()
    {
        GameServerConnectionFactory.createLocalGameServerConnection( createDummy( IGameServer.class ), null );
    }

    /**
     * Ensures the {@code createNullGameServerConnection} method does not return
     * {@code null}.
     */
    @Test
    public void testCreateNullGameServerConnection_ReturnValue_NonNull()
    {
        assertNotNull( GameServerConnectionFactory.createNullGameServerConnection() );
    }
}
