/*
 * AbstractNetworkTableTestCase.java
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
 * Created on Nov 9, 2010 at 10:51:34 PM.
 */

package org.gamegineer.table.net;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.net.INetworkTable} interface.
 */
public abstract class AbstractNetworkTableTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The network table under test in the fixture. */
    private INetworkTable networkTable_;

    /** The network table configuration for use in the fixture. */
    private INetworkTableConfiguration networkTableConfiguration_;

    /** The table for use in the fixture. */
    private ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkTableTestCase}
     * class.
     */
    protected AbstractNetworkTableTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Connects the fixture network table.
     * 
     * <p>
     * This method blocks until the network table is connected.
     * </p>
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void connectNetworkTable()
        throws Exception
    {
        networkTable_.host( networkTableConfiguration_ );
    }

    /**
     * Creates the network table to be tested.
     * 
     * @param table
     *        The table associated with the network table; must not be {@code
     *        null}.
     * 
     * @return The network table to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    /* @NonNull */
    protected abstract INetworkTable createNetworkTable(
        /* @NonNull */
        ITable table )
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
        mocksControl_ = EasyMock.createControl();
        table_ = TableFactory.createTable();
        networkTable_ = createNetworkTable( table_ );
        assertNotNull( networkTable_ );

        final NetworkTableConfigurationBuilder networkTableConfigurationBuilder = new NetworkTableConfigurationBuilder();
        networkTableConfiguration_ = networkTableConfigurationBuilder //
            .setHostName( "hostName" ) //$NON-NLS-1$
            .setLocalPlayerName( "playerName" ) //$NON-NLS-1$
            .setPort( NetworkTableConstants.DEFAULT_PORT ) //
            .toNetworkTableConfiguration();
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
        networkTableConfiguration_ = null;
        networkTable_ = null;
        table_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code addNetworkTableListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddNetworkTableListener_Listener_Null()
    {
        networkTable_.addNetworkTableListener( null );
    }

    /**
     * Ensures the {@code addNetworkTableListener} method throws an exception
     * when passed a listener that is present in the network table listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddNetworkTableListener_Listener_Present()
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        networkTable_.addNetworkTableListener( listener );

        networkTable_.addNetworkTableListener( listener );
    }

    /**
     * Ensures the {@code disconnect} method fires a network disconnected event
     * when the network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Connected_FiresNetworkDisconnectedEvent()
        throws Exception
    {
        connectNetworkTable();
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkDisconnected( EasyMock.notNull( NetworkTableDisconnectedEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code disconnect} method does not fire a network
     * disconnected event when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Disconnected_DoesNotFireNetworkConnectionStateChangedEvent()
        throws Exception
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code host} method throws an exception when passed a {@code
     * null} configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testHost_Configuration_Null()
        throws Exception
    {
        networkTable_.host( null );
    }

    /**
     * Ensures the {@code host} method throws an exception when the network is
     * connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testHost_Connected_ThrowsException()
        throws Exception
    {
        networkTable_.host( networkTableConfiguration_ );

        networkTable_.host( networkTableConfiguration_ );
    }

    /**
     * Ensures the {@code host} method fires a network connected event when the
     * network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHost_Disconnected_FiresNetworkConnectedEvent()
        throws Exception
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnected( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.host( networkTableConfiguration_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code join} method throws an exception when passed a {@code
     * null} configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testJoin_Configuration_Null()
        throws Exception
    {
        networkTable_.join( null );
    }

    /**
     * Ensures the {@code join} method throws an exception when the network is
     * connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testJoin_Connected_ThrowsException()
        throws Exception
    {
        networkTable_.join( networkTableConfiguration_ );

        networkTable_.join( networkTableConfiguration_ );
    }

    /**
     * Ensures the {@code join} method fires a network connected event when the
     * network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testJoin_Disconnected_FiresNetworkConnectedEvent()
        throws Exception
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnected( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.join( networkTableConfiguration_ );

        mocksControl_.verify();
    }

    /**
     * Ensures the network connected event catches any exception thrown by the
     * {@code networkConnected} method of a network table listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testNetworkConnected_CatchesListenerException()
        throws Exception
    {
        final INetworkTableListener listener1 = mocksControl_.createMock( INetworkTableListener.class );
        listener1.networkConnected( EasyMock.notNull( NetworkTableEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final INetworkTableListener listener2 = mocksControl_.createMock( INetworkTableListener.class );
        listener2.networkConnected( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener1 );
        networkTable_.addNetworkTableListener( listener2 );

        connectNetworkTable();

        mocksControl_.verify();
    }

    /**
     * Ensures the network disconnected event catches any exception thrown by
     * the {@code networkDisconnected} method of a network table listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testNetworkDisconnected_CatchesListenerException()
        throws Exception
    {
        connectNetworkTable();
        final INetworkTableListener listener1 = mocksControl_.createMock( INetworkTableListener.class );
        listener1.networkDisconnected( EasyMock.notNull( NetworkTableDisconnectedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final INetworkTableListener listener2 = mocksControl_.createMock( INetworkTableListener.class );
        listener2.networkDisconnected( EasyMock.notNull( NetworkTableDisconnectedEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener1 );
        networkTable_.addNetworkTableListener( listener2 );

        networkTable_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeNetworkTableListener} method throws an exception
     * when passed a listener that is absent from the network table listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveNetworkTableListener_Listener_Absent()
    {
        networkTable_.removeNetworkTableListener( mocksControl_.createMock( INetworkTableListener.class ) );
    }

    /**
     * Ensures the {@code removeNetworkTableListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveNetworkTableListener_Listener_Null()
    {
        networkTable_.removeNetworkTableListener( null );
    }

    /**
     * Ensures the {@code removeNetworkTableListener} removes a listener that is
     * present in the network table listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveNetworkTableListener_Listener_Present()
        throws Exception
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnected( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );
        connectNetworkTable();

        networkTable_.removeNetworkTableListener( listener );
        networkTable_.disconnect();

        mocksControl_.verify();
    }
}
