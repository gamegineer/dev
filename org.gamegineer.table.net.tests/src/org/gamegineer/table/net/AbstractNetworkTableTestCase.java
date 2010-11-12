/*
 * AbstractNetworkTableTestCase.java
 * Copyright 2008-2010 Gamegineer.org
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
     * Ensures the {@code disconnect} method fires a network connection state
     * changed event when the network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Connected_FiresNetworkConnectionStateChangedEvent()
        throws Exception
    {
        networkTable_.host( new NetworkTableConnectionContext() );
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.disconnect();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code disconnect} method does not fire a network connection
     * state changed event when the network is connected.
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
        networkTable_.host( new NetworkTableConnectionContext() );

        networkTable_.host( new NetworkTableConnectionContext() );
    }

    /**
     * Ensures the {@code host} method throws an exception when passed a {@code
     * null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testHost_Context_Null()
        throws Exception
    {
        networkTable_.host( null );
    }

    /**
     * Ensures the {@code host} method fires a network connection state changed
     * event when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHost_Disconnected_FiresNetworkConnectionStateChangedEvent()
        throws Exception
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.host( new NetworkTableConnectionContext() );

        mocksControl_.verify();
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
        networkTable_.join( new NetworkTableConnectionContext() );

        networkTable_.join( new NetworkTableConnectionContext() );
    }

    /**
     * Ensures the {@code join} method throws an exception when passed a {@code
     * null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testJoin_Context_Null()
        throws Exception
    {
        networkTable_.join( null );
    }

    /**
     * Ensures the {@code join} method fires a network connection state changed
     * event when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testJoin_Disconnected_FiresNetworkConnectionStateChangedEvent()
        throws Exception
    {
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.join( new NetworkTableConnectionContext() );

        mocksControl_.verify();
    }

    /**
     * Ensures the network connection state changed event catches any exception
     * thrown by the {@code networkConnectionStateChanged} method of a network
     * table listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testNetworkConnectionStateChanged_CatchesListenerException()
        throws Exception
    {
        final INetworkTableListener listener1 = mocksControl_.createMock( INetworkTableListener.class );
        listener1.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final INetworkTableListener listener2 = mocksControl_.createMock( INetworkTableListener.class );
        listener2.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener1 );
        networkTable_.addNetworkTableListener( listener2 );

        networkTable_.host( new NetworkTableConnectionContext() );

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
        listener.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );
        networkTable_.host( new NetworkTableConnectionContext() );

        networkTable_.removeNetworkTableListener( listener );
        networkTable_.disconnect();

        mocksControl_.verify();
    }
}
