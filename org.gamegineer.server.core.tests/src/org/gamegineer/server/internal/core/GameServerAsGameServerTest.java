/*
 * GameServerAsGameServerTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Dec 10, 2008 at 11:40:11 PM.
 */

package org.gamegineer.server.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.server.core.AbstractGameServerTestCase;
import org.gamegineer.server.core.IGameServer;
import org.gamegineer.server.core.config.IGameServerConfiguration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.GameServer} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.server.core.IGameServer} interface.
 */
public final class GameServerAsGameServerTest
    extends AbstractGameServerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerAsGameServerTest}
     * class.
     */
    public GameServerAsGameServerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.server.core.AbstractGameServerTestCase#createGameServer(org.gamegineer.server.core.config.IGameServerConfiguration)
     */
    @Override
    protected IGameServer createGameServer(
        final IGameServerConfiguration gameServerConfig )
        throws Exception
    {
        assertArgumentNotNull( gameServerConfig, "gameServerConfig" ); //$NON-NLS-1$

        return GameServer.createGameServer( gameServerConfig );
    }
}
