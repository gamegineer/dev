/*
 * GameServerConnectionFactory.java
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
 * Created on Dec 23, 2008 at 11:16:34 PM.
 */

package org.gamegineer.client.core.connection;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.Principal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.internal.core.connection.LocalGameServerConnection;
import org.gamegineer.client.internal.core.connection.NullGameServerConnection;
import org.gamegineer.server.core.IGameServer;

/**
 * A factory for creating game server connections.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class GameServerConnectionFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerConnectionFactory}
     * class.
     */
    private GameServerConnectionFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // TODO: Eventually the following method must accept some sort of
    // "authentication token" that contains a digital signature rather than
    // simply the principal establishing the connection.  Otherwise, clients
    // can impersonate other users.  The Gamegineer architecture will
    // eventually introduce the concept of an "authentication authority" that
    // will provide signed tokens based on PKI.  We will return to that
    // problem in the future once we have a better understanding of what is
    // required.

    /**
     * Creates a connection to the specified local game server.
     * 
     * @param gameServer
     *        The local game server; must not be {@code null}.
     * @param userPrincipal
     *        The user principal associated with the connection; must not be
     *        {@code null}.
     * 
     * @return A connection to the specified local game server; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameServer} or {@code userPrincipal} is {@code null}.
     */
    /* @NonNull */
    public static IGameServerConnection createLocalGameServerConnection(
        /* @NonNull */
        final IGameServer gameServer,
        /* @NonNull */
        final Principal userPrincipal )
    {
        assertArgumentNotNull( gameServer, "gameServer" ); //$NON-NLS-1$
        assertArgumentNotNull( userPrincipal, "userPrincipal" ); //$NON-NLS-1$

        return new LocalGameServerConnection( gameServer, userPrincipal );
    }

    /**
     * Creates a null game server connection.
     * 
     * <p>
     * A null game server connection logically represents no connection.
     * </p>
     * 
     * @return A null game server connection; never {@code null}.
     */
    /* @NonNull */
    public static IGameServerConnection createNullGameServerConnection()
    {
        return new NullGameServerConnection();
    }
}
