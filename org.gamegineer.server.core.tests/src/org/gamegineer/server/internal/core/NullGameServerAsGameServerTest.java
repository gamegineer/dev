/*
 * NullGameServerAsGameServerTest.java
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
 * Created on Dec 23, 2008 at 10:27:42 PM.
 */

package org.gamegineer.server.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.game.core.GameConfigurationException;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.server.core.AbstractGameServerTestCase;
import org.gamegineer.server.core.IGameServer;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.NullGameServer} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.server.core.IGameServer} interface.
 */
public final class NullGameServerAsGameServerTest
    extends AbstractGameServerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullGameServerAsGameServerTest}
     * class.
     */
    public NullGameServerAsGameServerTest()
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
    {
        assertArgumentNotNull( gameServerConfig, "gameServerConfig" ); //$NON-NLS-1$

        return new NullGameServer();
    }

    /**
     * Ensures the {@code createGame} method throws an exception when passed a
     * legal game configuration because {@code NullGameServer} considers all
     * configurations to be illegal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * 
     * @see org.gamegineer.server.core.AbstractGameServerTestCase#testCreateGame_GameConfig_Legal()
     */
    @Override
    @Test( expected = GameConfigurationException.class )
    public void testCreateGame_GameConfig_Legal()
        throws Exception
    {
        getGameServer().createGame( Configurations.createMinimalGameConfiguration() );
    }
}
