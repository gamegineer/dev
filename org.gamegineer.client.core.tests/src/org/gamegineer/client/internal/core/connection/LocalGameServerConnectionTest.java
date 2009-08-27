/*
 * LocalGameServerConnectionTest.java
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
 * Created on Dec 25, 2008 at 9:20:26 PM.
 */

package org.gamegineer.client.internal.core.connection;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import java.security.Principal;
import org.gamegineer.engine.core.extensions.securitymanager.Principals;
import org.gamegineer.engine.core.extensions.securitymanager.ThreadPrincipals;
import org.gamegineer.server.core.GameServerFactory;
import org.gamegineer.server.core.IGameServer;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.connection.LocalGameServerConnection}
 * class.
 */
public final class LocalGameServerConnectionTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalGameServerConnectionTest}
     * class.
     */
    public LocalGameServerConnectionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new local game server connection.
     * 
     * @param userPrincipal
     *        The user principal associated with the connection; must not be
     *        {@code null}.
     * 
     * @return A new local game server connection; never {@code null}.
     */
    /* @NonNull */
    private static LocalGameServerConnection createLocalGameServerConnection(
        /* @NonNull */
        final Principal userPrincipal )
    {
        assert userPrincipal != null;

        return new LocalGameServerConnection( GameServerFactory.createNullGameServer(), userPrincipal );
    }

    /**
     * Ensures the {@code close} method clears the thread user principal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testClose_ClearsThreadUserPrincipal()
        throws Exception
    {
        final Principal expectedUserPrincipal = Principals.createUserPrincipal( "name" ); //$NON-NLS-1$
        final LocalGameServerConnection connection = createLocalGameServerConnection( expectedUserPrincipal );
        connection.open();

        connection.close();

        assertFalse( expectedUserPrincipal.equals( ThreadPrincipals.getUserPrincipal() ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * game server.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_GameServer_Null()
    {
        new LocalGameServerConnection( null, createDummy( Principal.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * user principal.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_UserPrincipal_Null()
    {
        new LocalGameServerConnection( createDummy( IGameServer.class ), null );
    }

    /**
     * Ensures the {@code open} method sets the thread user principal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpen_SetsThreadUserPrincipal()
        throws Exception
    {
        final Principal expectedUserPrincipal = Principals.createUserPrincipal( "name" ); //$NON-NLS-1$
        final LocalGameServerConnection connection = createLocalGameServerConnection( expectedUserPrincipal );

        connection.open();

        assertSame( expectedUserPrincipal, ThreadPrincipals.getUserPrincipal() );
    }
}
