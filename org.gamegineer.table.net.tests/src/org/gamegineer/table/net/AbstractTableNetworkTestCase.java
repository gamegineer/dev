/*
 * AbstractTableNetworkTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Nov 9, 2010 at 10:51:34 PM.
 */

package org.gamegineer.table.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collection;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.net.ITableNetwork} interface.
 */
public abstract class AbstractTableNetworkTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table network under test in the fixture. */
    private ITableNetwork tableNetwork_;

    /** The table network configuration for use in the fixture. */
    private ITableNetworkConfiguration tableNetworkConfiguration_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableNetworkTestCase}
     * class.
     */
    protected AbstractTableNetworkTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Connects the fixture table network.
     * 
     * <p>
     * This method blocks until the table network is connected.
     * </p>
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void connectTableNetwork()
        throws Exception
    {
        tableNetwork_.host( tableNetworkConfiguration_ );
    }

    /**
     * Creates the table network to be tested.
     * 
     * @return The table network to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableNetwork createTableNetwork()
        throws Exception;

    /**
     * Fires the table network players updated event for the specified table
     * network.
     * 
     * @param tableNetwork
     *        The table network; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetwork} is {@code null}.
     */
    protected abstract void fireTableNetworkPlayersUpdatedEvent(
        /* @NonNull */
        ITableNetwork tableNetwork );

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
        mocksControl_ = EasyMock.createControl();
        tableNetwork_ = createTableNetwork();
        assertNotNull( tableNetwork_ );

        final TableNetworkConfigurationBuilder builder = new TableNetworkConfigurationBuilder();
        tableNetworkConfiguration_ = builder //
            .setHostName( "hostName" ) //$NON-NLS-1$
            .setLocalPlayerName( "playerName" ) //$NON-NLS-1$
            .setLocalTable( TableEnvironmentFactory.createTableEnvironment().createTable() ) //
            .setPort( TableNetworkConstants.DEFAULT_PORT ) //
            .toTableNetworkConfiguration();
    }

    /**
     * Ensures the {@code addTableNetworkListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableNetworkListener_Listener_Null()
    {
        tableNetwork_.addTableNetworkListener( null );
    }

    /**
     * Ensures the {@code addTableNetworkListener} method throws an exception
     * when passed a listener that is present in the table network listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableNetworkListener_Listener_Present()
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        tableNetwork_.addTableNetworkListener( listener );

        tableNetwork_.addTableNetworkListener( listener );
    }

    /**
     * Ensures the {@code disconnect} method fires a disconnected event when the
     * network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Connected_FiresDisconnectedEvent()
        throws Exception
    {
        connectTableNetwork();
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkDisconnected( EasyMock.notNull( TableNetworkDisconnectedEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener );

        tableNetwork_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code disconnect} method does not fire a disconnected event
     * when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Disconnected_DoesNotFireDisconnectedEvent()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener );

        tableNetwork_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getPlayers} method returns a copy of the player
     * collection.
     */
    @Test
    public void testGetPlayers_ReturnValue_Copy()
    {
        final Collection<IPlayer> players = tableNetwork_.getPlayers();
        final Collection<IPlayer> expectedValue = new ArrayList<IPlayer>( players );

        players.add( EasyMock.createMock( IPlayer.class ) );
        final Collection<IPlayer> actualValue = tableNetwork_.getPlayers();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getPlayers} method does not return {@code null}.
     */
    @Test
    public void testGetPlayers_ReturnValue_NonNull()
    {
        assertNotNull( tableNetwork_.getPlayers() );
    }

    /**
     * Ensures the {@code giveControl} method throws an exception when passed a
     * {@code null} player.
     */
    @Test( expected = NullPointerException.class )
    public void testGiveControl_Player_Null()
    {
        tableNetwork_.giveControl( null );
    }

    /**
     * Ensures the {@code host} method throws an exception when passed a
     * {@code null} configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testHost_Configuration_Null()
        throws Exception
    {
        tableNetwork_.host( null );
    }

    /**
     * Ensures the {@code host} method throws an exception when the network is
     * connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testHost_Connected_ThrowsException()
        throws Exception
    {
        tableNetwork_.host( tableNetworkConfiguration_ );

        tableNetwork_.host( tableNetworkConfiguration_ );
    }

    /**
     * Ensures the {@code host} method fires a connected event when the network
     * is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHost_Disconnected_FiresConnectedEvent()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkConnected( EasyMock.notNull( TableNetworkEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener );

        tableNetwork_.host( tableNetworkConfiguration_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code join} method throws an exception when passed a
     * {@code null} configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testJoin_Configuration_Null()
        throws Exception
    {
        tableNetwork_.join( null );
    }

    /**
     * Ensures the {@code join} method throws an exception when the network is
     * connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testJoin_Connected_ThrowsException()
        throws Exception
    {
        tableNetwork_.join( tableNetworkConfiguration_ );

        tableNetwork_.join( tableNetworkConfiguration_ );
    }

    /**
     * Ensures the {@code join} method fires a connected event when the network
     * is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testJoin_Disconnected_FiresConnectedEvent()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkConnected( EasyMock.notNull( TableNetworkEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener );

        tableNetwork_.join( tableNetworkConfiguration_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeTableNetworkListener} method throws an exception
     * when passed a listener that is absent from the table network listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableNetworkListener_Listener_Absent()
    {
        tableNetwork_.removeTableNetworkListener( mocksControl_.createMock( ITableNetworkListener.class ) );
    }

    /**
     * Ensures the {@code removeTableNetworkListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableNetworkListener_Listener_Null()
    {
        tableNetwork_.removeTableNetworkListener( null );
    }

    /**
     * Ensures the {@code removeTableNetworkListener} removes a listener that is
     * present in the table network listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveTableNetworkListener_Listener_Present()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkConnected( EasyMock.notNull( TableNetworkEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener );
        connectTableNetwork();

        tableNetwork_.removeTableNetworkListener( listener );
        tableNetwork_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the table network connected event catches any exception thrown by
     * the {@code tableNetworkConnected} method of a table network listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableNetworkConnected_CatchesListenerException()
        throws Exception
    {
        final ITableNetworkListener listener1 = mocksControl_.createMock( ITableNetworkListener.class );
        listener1.tableNetworkConnected( EasyMock.notNull( TableNetworkEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableNetworkListener listener2 = mocksControl_.createMock( ITableNetworkListener.class );
        listener2.tableNetworkConnected( EasyMock.notNull( TableNetworkEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener1 );
        tableNetwork_.addTableNetworkListener( listener2 );

        connectTableNetwork();

        mocksControl_.verify();
    }

    /**
     * Ensures the table network disconnected event catches any exception thrown
     * by the {@code tableNetworkDisconnected} method of a table network
     * listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableNetworkDisconnected_CatchesListenerException()
        throws Exception
    {
        connectTableNetwork();
        final ITableNetworkListener listener1 = mocksControl_.createMock( ITableNetworkListener.class );
        listener1.tableNetworkDisconnected( EasyMock.notNull( TableNetworkDisconnectedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableNetworkListener listener2 = mocksControl_.createMock( ITableNetworkListener.class );
        listener2.tableNetworkDisconnected( EasyMock.notNull( TableNetworkDisconnectedEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener1 );
        tableNetwork_.addTableNetworkListener( listener2 );

        tableNetwork_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the table network players updated event catches any exception
     * thrown by the {@code tableNetworkPlayersUpdated} method of a table
     * network listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableNetworkPlayersUpdated_CatchesListenerException()
        throws Exception
    {
        final ITableNetworkListener listener1 = mocksControl_.createMock( ITableNetworkListener.class );
        listener1.tableNetworkPlayersUpdated( EasyMock.notNull( TableNetworkEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableNetworkListener listener2 = mocksControl_.createMock( ITableNetworkListener.class );
        listener2.tableNetworkPlayersUpdated( EasyMock.notNull( TableNetworkEvent.class ) );
        mocksControl_.replay();
        tableNetwork_.addTableNetworkListener( listener1 );
        tableNetwork_.addTableNetworkListener( listener2 );

        fireTableNetworkPlayersUpdatedEvent( tableNetwork_ );

        mocksControl_.verify();
    }
}
