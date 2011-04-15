/*
 * ServerNetworkTableStrategyTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Apr 10, 2011 at 6:11:21 PM.
 */

package org.gamegineer.table.internal.net.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.easymock.EasyMock;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.NetworkTable;
import org.gamegineer.table.internal.net.NetworkTableConfigurations;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.transport.fake.FakeTransportLayerFactory;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.server.ServerNetworkTableStrategy}
 * class.
 */
public final class ServerNetworkTableStrategyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The server network table strategy under test in the fixture. */
    private ServerNetworkTableStrategy serverNetworkTableStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerNetworkTableStrategyTest}
     * class.
     */
    public ServerNetworkTableStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        serverNetworkTableStrategy_ = new ServerNetworkTableStrategy( new NetworkTable( EasyMock.createMock( ITable.class ) ), new FakeTransportLayerFactory() );
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
        serverNetworkTableStrategy_ = null;
    }

    /**
     * Ensures the {@code connect} method adds the local player to the connected
     * players collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_AddsLocalPlayer()
        throws Exception
    {
        final INetworkTableConfiguration configuration = NetworkTableConfigurations.createDefaultNetworkTableConfiguration();
        serverNetworkTableStrategy_.connect( configuration );

        assertTrue( serverNetworkTableStrategy_.getConnectedPlayerNames().contains( configuration.getLocalPlayerName() ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * network table.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_NetworkTable_Null()
    {
        new ServerNetworkTableStrategy( null, EasyMock.createMock( ITransportLayerFactory.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * transport layer factory.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TransportLayerFactory_Null()
    {
        new ServerNetworkTableStrategy( new NetworkTable( EasyMock.createMock( ITable.class ) ), null );
    }

    /**
     * Ensures the {@code playerConnected} method adds the player when the
     * player name is absent from the connected players collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testPlayerConnected_PlayerName_Absent()
        throws Exception
    {
        final String playerName = "newPlayerName"; //$NON-NLS-1$
        serverNetworkTableStrategy_.connect( NetworkTableConfigurations.createDefaultNetworkTableConfiguration() );
        assertFalse( serverNetworkTableStrategy_.getConnectedPlayerNames().contains( playerName ) );

        serverNetworkTableStrategy_.playerConnected( playerName, EasyMock.createMock( ITableGateway.class ) );

        assertTrue( serverNetworkTableStrategy_.getConnectedPlayerNames().contains( playerName ) );
    }

    /**
     * Ensures the {@code playerConnected} method throws an exception when the
     * player name is present in the connected players collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testPlayerConnected_PlayerName_Present()
        throws Exception
    {
        final String playerName = "newPlayerName"; //$NON-NLS-1$
        serverNetworkTableStrategy_.connect( NetworkTableConfigurations.createDefaultNetworkTableConfiguration() );
        serverNetworkTableStrategy_.playerConnected( playerName, EasyMock.createMock( ITableGateway.class ) );

        serverNetworkTableStrategy_.playerConnected( playerName, EasyMock.createMock( ITableGateway.class ) );
    }

    /**
     * Ensures the {@code playerDisconnected} method does nothing when the
     * player name is absent from the connected players collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testPlayerDisconnected_PlayerName_Absent()
        throws Exception
    {
        serverNetworkTableStrategy_.connect( NetworkTableConfigurations.createDefaultNetworkTableConfiguration() );
        final int originalConnectedPlayerNamesSize = serverNetworkTableStrategy_.getConnectedPlayerNames().size();

        serverNetworkTableStrategy_.playerDisconnected( "unknownPlayerName" ); //$NON-NLS-1$

        assertEquals( originalConnectedPlayerNamesSize, serverNetworkTableStrategy_.getConnectedPlayerNames().size() );
    }

    /**
     * Ensures the {@code playerDisconnected} method removes the player when the
     * player name is present in the connected players collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testPlayerDisconnected_PlayerName_Present()
        throws Exception
    {
        final String playerName = "newPlayerName"; //$NON-NLS-1$
        serverNetworkTableStrategy_.connect( NetworkTableConfigurations.createDefaultNetworkTableConfiguration() );
        serverNetworkTableStrategy_.playerConnected( playerName, EasyMock.createMock( ITableGateway.class ) );
        assertTrue( serverNetworkTableStrategy_.getConnectedPlayerNames().contains( playerName ) );

        serverNetworkTableStrategy_.playerDisconnected( playerName );

        assertFalse( serverNetworkTableStrategy_.getConnectedPlayerNames().contains( playerName ) );
    }
}
