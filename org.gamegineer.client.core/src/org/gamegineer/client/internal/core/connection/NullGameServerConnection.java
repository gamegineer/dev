/*
 * NullGameServerConnection.java
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
 * Created on Dec 23, 2008 at 9:37:25 PM.
 */

package org.gamegineer.client.internal.core.connection;

import java.io.IOException;
import java.security.Principal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.engine.core.extensions.securitymanager.Principals;
import org.gamegineer.server.core.GameServerFactory;
import org.gamegineer.server.core.IGameServer;

/**
 * Implementation of
 * {@link org.gamegineer.client.core.connection.IGameServerConnection} that
 * represents a connection to a null server.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class NullGameServerConnection
    implements IGameServerConnection
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The connection to which all operations are delegated. */
    private final IGameServerConnection connection_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullGameServerConnection} class.
     */
    public NullGameServerConnection()
    {
        connection_ = new LocalGameServerConnection( GameServerFactory.createNullGameServer(), Principals.getAnonymousUserPrincipal() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#close()
     */
    public void close()
        throws IOException
    {
        connection_.close();
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getGameServer()
     */
    public IGameServer getGameServer()
    {
        return connection_.getGameServer();
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getName()
     */
    public String getName()
    {
        return Messages.NullGameServerConnection_name;
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getUserPrincipal()
     */
    public Principal getUserPrincipal()
    {
        return connection_.getUserPrincipal();
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#open()
     */
    public void open()
        throws IOException
    {
        connection_.open();
    }
}
