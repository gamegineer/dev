/*
 * AbstractTableNetworkTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.net.test;

import static org.gamegineer.common.core.runtime.NullAnalysis.assumeNonNull;
import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collection;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.ITableNetworkListener;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkConfigurationBuilder;
import org.gamegineer.table.net.TableNetworkConstants;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkEvent;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableNetwork} interface.
 */
@NonNullByDefault( false )
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
    private TableNetworkConfiguration tableNetworkConfiguration_;


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
        tableNetwork_.host( getTableNetworkConfiguration() );
    }

    /**
     * Creates the table network to be tested.
     * 
     * @return The table network to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @NonNull
    protected abstract ITableNetwork createTableNetwork()
        throws Exception;

    /**
     * Fires the table network players updated event for the specified table
     * network.
     * 
     * @param tableNetwork
     *        The table network; must not be {@code null}.
     */
    protected abstract void fireTableNetworkPlayersUpdatedEvent(
        @NonNull
        ITableNetwork tableNetwork );

    /**
     * Gets the table network under test in the fixture.
     * 
     * @return The table network under test in the fixture; never {@code null}.
     */
    @NonNull
    private ITableNetwork getTableNetwork()
    {
        assertNotNull( tableNetwork_ );
        return tableNetwork_;
    }

    /**
     * Gets the fixture table network configuration.
     * 
     * @return The fixture table network configuration; never {@code null}.
     */
    @NonNull
    private TableNetworkConfiguration getTableNetworkConfiguration()
    {
        assertNotNull( tableNetworkConfiguration_ );
        return tableNetworkConfiguration_;
    }

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

        final TableNetworkConfigurationBuilder builder = new TableNetworkConfigurationBuilder( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ).createTable() );
        tableNetworkConfiguration_ = builder //
            .setHostName( "hostName" ) //$NON-NLS-1$
            .setLocalPlayerName( "playerName" ) //$NON-NLS-1$
            .setPort( TableNetworkConstants.DEFAULT_PORT ) //
            .toTableNetworkConfiguration();
    }

    /**
     * Ensures the {@link ITableNetwork#addTableNetworkListener} method throws
     * an exception when passed a listener that is present in the table network
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableNetworkListener_Listener_Present()
    {
        final ITableNetworkListener listener = nonNull( mocksControl_.createMock( ITableNetworkListener.class ) );
        getTableNetwork().addTableNetworkListener( listener );

        getTableNetwork().addTableNetworkListener( listener );
    }

    /**
     * Ensures the {@link ITableNetwork#disconnect} method fires a disconnected
     * event when the network is connected.
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
        listener.tableNetworkDisconnected( assumeNonNull( EasyMock.notNull( TableNetworkDisconnectedEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener );

        getTableNetwork().disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ITableNetwork#disconnect} method does not fire a
     * disconnected event when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Disconnected_DoesNotFireDisconnectedEvent()
        throws Exception
    {
        final ITableNetworkListener listener = nonNull( mocksControl_.createMock( ITableNetworkListener.class ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener );

        getTableNetwork().disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ITableNetwork#getPlayers} method returns a copy of the
     * player collection.
     */
    @Test
    public void testGetPlayers_ReturnValue_Copy()
    {
        final Collection<IPlayer> players = getTableNetwork().getPlayers();
        final Collection<IPlayer> expectedValue = new ArrayList<>( players );

        players.add( EasyMock.createMock( IPlayer.class ) );
        final Collection<IPlayer> actualValue = getTableNetwork().getPlayers();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link ITableNetwork#getPlayers} method does not return
     * {@code null}.
     */
    @Test
    public void testGetPlayers_ReturnValue_NonNull()
    {
        assertNotNull( getTableNetwork().getPlayers() );
    }

    /**
     * Ensures the {@link ITableNetwork#host} method throws an exception when
     * the network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testHost_Connected_ThrowsException()
        throws Exception
    {
        getTableNetwork().host( getTableNetworkConfiguration() );

        getTableNetwork().host( getTableNetworkConfiguration() );
    }

    /**
     * Ensures the {@link ITableNetwork#host} method fires a connected event
     * when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHost_Disconnected_FiresConnectedEvent()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkConnected( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener );

        getTableNetwork().host( getTableNetworkConfiguration() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ITableNetwork#join} method throws an exception when
     * the network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testJoin_Connected_ThrowsException()
        throws Exception
    {
        getTableNetwork().join( getTableNetworkConfiguration() );

        getTableNetwork().join( getTableNetworkConfiguration() );
    }

    /**
     * Ensures the {@link ITableNetwork#join} method fires a connected event
     * when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testJoin_Disconnected_FiresConnectedEvent()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkConnected( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener );

        getTableNetwork().join( getTableNetworkConfiguration() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ITableNetwork#removeTableNetworkListener} method
     * throws an exception when passed a listener that is absent from the table
     * network listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableNetworkListener_Listener_Absent()
    {
        getTableNetwork().removeTableNetworkListener( nonNull( mocksControl_.createMock( ITableNetworkListener.class ) ) );
    }

    /**
     * Ensures the {@link ITableNetwork#removeTableNetworkListener} removes a
     * listener that is present in the table network listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveTableNetworkListener_Listener_Present()
        throws Exception
    {
        final ITableNetworkListener listener = mocksControl_.createMock( ITableNetworkListener.class );
        listener.tableNetworkConnected( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener );
        connectTableNetwork();

        getTableNetwork().removeTableNetworkListener( listener );
        getTableNetwork().disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the table network connected event catches any exception thrown by
     * the {@link ITableNetworkListener#tableNetworkConnected} method of a table
     * network listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableNetworkConnected_CatchesListenerException()
        throws Exception
    {
        final ITableNetworkListener listener1 = mocksControl_.createMock( ITableNetworkListener.class );
        listener1.tableNetworkConnected( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableNetworkListener listener2 = mocksControl_.createMock( ITableNetworkListener.class );
        listener2.tableNetworkConnected( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener1 );
        getTableNetwork().addTableNetworkListener( listener2 );

        connectTableNetwork();

        mocksControl_.verify();
    }

    /**
     * Ensures the table network disconnected event catches any exception thrown
     * by the {@link ITableNetworkListener#tableNetworkDisconnected} method of a
     * table network listener.
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
        listener1.tableNetworkDisconnected( assumeNonNull( EasyMock.notNull( TableNetworkDisconnectedEvent.class ) ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableNetworkListener listener2 = mocksControl_.createMock( ITableNetworkListener.class );
        listener2.tableNetworkDisconnected( assumeNonNull( EasyMock.notNull( TableNetworkDisconnectedEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener1 );
        getTableNetwork().addTableNetworkListener( listener2 );

        getTableNetwork().disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the table network players updated event catches any exception
     * thrown by the {@link ITableNetworkListener#tableNetworkPlayersUpdated}
     * method of a table network listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableNetworkPlayersUpdated_CatchesListenerException()
        throws Exception
    {
        final ITableNetworkListener listener1 = mocksControl_.createMock( ITableNetworkListener.class );
        listener1.tableNetworkPlayersUpdated( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableNetworkListener listener2 = mocksControl_.createMock( ITableNetworkListener.class );
        listener2.tableNetworkPlayersUpdated( assumeNonNull( EasyMock.notNull( TableNetworkEvent.class ) ) );
        mocksControl_.replay();
        getTableNetwork().addTableNetworkListener( listener1 );
        getTableNetwork().addTableNetworkListener( listener2 );

        fireTableNetworkPlayersUpdatedEvent( getTableNetwork() );

        mocksControl_.verify();
    }
}
