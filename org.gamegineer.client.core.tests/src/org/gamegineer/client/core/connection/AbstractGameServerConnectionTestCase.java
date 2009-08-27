/*
 * AbstractGameServerConnectionTestCase.java
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
 * Created on Dec 22, 2008 at 11:29:16 PM.
 */

package org.gamegineer.client.core.connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.gamegineer.common.remoting.RemoteException;
import org.gamegineer.server.core.IGameServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.core.connection.IGameServerConnection}
 * interface.
 */
public abstract class AbstractGameServerConnectionTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game server connection under test in the fixture. */
    private IGameServerConnection m_gameServerConnection;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractGameServerConnectionTestCase} class.
     */
    protected AbstractGameServerConnectionTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game server connection to be tested.
     * 
     * @return The game server connection to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameServerConnection createGameServerConnection()
        throws Exception;

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_gameServerConnection = createGameServerConnection();
        assertNotNull( m_gameServerConnection );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_gameServerConnection = null;
    }

    /**
     * Ensures the {@code close} method does nothing when the connection is
     * closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testClose_Closed()
        throws Exception
    {
        m_gameServerConnection.close();

        m_gameServerConnection.close();
    }

    /**
     * Ensures the {@code close} method closes the connection when the
     * connection is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testClose_Open()
        throws Exception
    {
        m_gameServerConnection.open();

        m_gameServerConnection.close();
    }

    /**
     * Ensures the {@code close} method closes the connection when the
     * connection is not yet open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IOException.class )
    public void testClose_Pristine()
        throws Exception
    {
        m_gameServerConnection.close();

        m_gameServerConnection.open();
    }

    /**
     * Ensures the {@code getGameServer} method throws an exception when the
     * connection is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetGameServer_Closed()
        throws Exception
    {
        m_gameServerConnection.close();

        m_gameServerConnection.getGameServer();
    }

    /**
     * Ensures the {@code getGameServer} method returns a game server whose
     * methods all throw an instance of {@code RemoteException} when invoked
     * after the connection is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetGameServer_Closed_GameServerThrowsRemoteException()
        throws Exception
    {
        m_gameServerConnection.open();
        final IGameServer gameServer = m_gameServerConnection.getGameServer();
        m_gameServerConnection.close();

        for( final Method method : IGameServer.class.getMethods() )
        {
            try
            {
                // NB: We pass all null arguments because, for this test to
                // succeed, the actual method should never be invoked, and
                // thus no argument validation should occur.
                method.invoke( gameServer, new Object[ method.getParameterTypes().length ] );
                fail( String.format( "Expected an exception of type RemoteException to be thrown for method '%1$s', but not exception was thrown.", method.getName() ) ); //$NON-NLS-1$
            }
            catch( final InvocationTargetException e )
            {
                if( !(e.getCause() instanceof RemoteException) )
                {
                    fail( String.format( "Expected an exception of type RemoteException to be thrown for method '%1$s', but an exception of type '%2$s' was thrown instead.", method.getName(), e.getCause().getClass().getName() ) ); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Ensures the {@code getGameServer} method does not return {@code null}
     * when the connection is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetGameServer_Open_ReturnValue_NonNull()
        throws Exception
    {
        m_gameServerConnection.open();

        assertNotNull( m_gameServerConnection.getGameServer() );
    }

    /**
     * Ensures the {@code getGameServer} method throws an exception when the
     * connection is not yet open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetGameServer_Pristine()
        throws Exception
    {
        m_gameServerConnection.getGameServer();
    }

    /**
     * Ensures the {@code getName} method does not return {@code null} when the
     * connection is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetName_Closed_ReturnValue_NonNull()
        throws Exception
    {
        m_gameServerConnection.close();

        assertNotNull( m_gameServerConnection.getName() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null} when the
     * connection is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetName_Open_ReturnValue_NonNull()
        throws Exception
    {
        m_gameServerConnection.open();

        assertNotNull( m_gameServerConnection.getName() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null} when the
     * connection is not yet open.
     */
    @Test
    public void testGetName_Pristine_ReturnValue_NonNull()
    {
        assertNotNull( m_gameServerConnection.getName() );
    }

    /**
     * Ensures the {@code getUserPrincipal} method does not return {@code null}
     * when the connection is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetUserPrincipal_Closed_ReturnValue_NonNull()
        throws Exception
    {
        m_gameServerConnection.close();

        assertNotNull( m_gameServerConnection.getUserPrincipal() );
    }

    /**
     * Ensures the {@code getUserPrincipal} method does not return {@code null}
     * when the connection is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetUserPrincipal_Open_ReturnValue_NonNull()
        throws Exception
    {
        m_gameServerConnection.open();

        assertNotNull( m_gameServerConnection.getUserPrincipal() );
    }

    /**
     * Ensures the {@code getUserPrincipal} method does not return {@code null}
     * when the connection is not yet open.
     */
    @Test
    public void testGetUserPrincipal_Pristine_ReturnValue_NonNull()
    {
        assertNotNull( m_gameServerConnection.getUserPrincipal() );
    }

    /**
     * Ensures the {@code open} method throws an exception when the connection
     * is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IOException.class )
    public void testOpen_Closed()
        throws Exception
    {
        m_gameServerConnection.close();

        m_gameServerConnection.open();
    }

    /**
     * Ensures the {@code open} method does nothing when the connection is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpen_Open()
        throws Exception
    {
        m_gameServerConnection.open();

        m_gameServerConnection.open();
    }
}
