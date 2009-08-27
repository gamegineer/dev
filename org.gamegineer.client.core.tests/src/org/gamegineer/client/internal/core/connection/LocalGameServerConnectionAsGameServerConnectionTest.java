/*
 * LocalGameServerConnectionAsGameServerConnectionTest.java
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
 * Created on Dec 25, 2008 at 9:18:41 PM.
 */

package org.gamegineer.client.internal.core.connection;

import org.gamegineer.client.core.connection.AbstractGameServerConnectionTestCase;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.engine.core.extensions.securitymanager.Principals;
import org.gamegineer.server.core.GameServerFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.connection.LocalGameServerConnection}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.core.connection.IGameServerConnection}
 * interface.
 */
public final class LocalGameServerConnectionAsGameServerConnectionTest
    extends AbstractGameServerConnectionTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code LocalGameServerConnectionAsGameServerConnectionTest} class.
     */
    public LocalGameServerConnectionAsGameServerConnectionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.connection.AbstractGameServerConnectionTestCase#createGameServerConnection()
     */
    @Override
    protected IGameServerConnection createGameServerConnection()
    {
        return new LocalGameServerConnection( GameServerFactory.createNullGameServer(), Principals.getAnonymousUserPrincipal() );
    }
}
