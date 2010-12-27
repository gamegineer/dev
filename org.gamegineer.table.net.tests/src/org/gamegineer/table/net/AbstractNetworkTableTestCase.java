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
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
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
        networkTable_.endHost( networkTable_.beginHost( networkTableConfiguration_ ) );
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
        networkTableConfiguration_ = mocksControl_.createMock( INetworkTableConfiguration.class );
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
     * Ensures the {@code beginHost} method throws an exception when passed a
     * {@code null} configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testBeginHost_Configuration_Null()
    {
        networkTable_.beginHost( null );
    }

    /**
     * Ensures the {@code beginJoin} method throws an exception when passed a
     * {@code null} configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testBeginJoin_Configuration_Null()
    {
        networkTable_.beginJoin( null );
    }

    /**
     * Ensures the disconnect operation fires a network connection state changed
     * event when the network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Connected_FiresNetworkConnectionStateChangedEvent()
        throws Exception
    {
        connectNetworkTable();
        final INetworkTableListener listener = mocksControl_.createMock( INetworkTableListener.class );
        listener.networkConnectionStateChanged( EasyMock.notNull( NetworkTableEvent.class ) );
        mocksControl_.replay();
        networkTable_.addNetworkTableListener( listener );

        networkTable_.endDisconnect( networkTable_.beginDisconnect() );

        mocksControl_.verify();
    }

    /**
     * Ensures the disconnect operation does not fire a network connection state
     * changed event when the network is connected.
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

        networkTable_.endDisconnect( networkTable_.beginDisconnect() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code endDisconnect} method throws an exception when passed
     * an illegal token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testEndDisconnect_Token_Illegal()
        throws Exception
    {
        networkTable_.endDisconnect( new FutureTask<Void>( new Callable<Void>()
        {
            @Override
            public Void call()
            {
                return null;
            }
        } ) );
    }

    /**
     * Ensures the {@code endDisconnect} method throws an exception when passed
     * a {@code null} token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testEndDisconnect_Token_Null()
        throws Exception
    {
        networkTable_.endDisconnect( null );
    }

    /**
     * Ensures the {@code endHost} method throws an exception when the network
     * is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testEndHost_Connected_ThrowsException()
        throws Exception
    {
        networkTable_.endHost( networkTable_.beginHost( networkTableConfiguration_ ) );

        networkTable_.endHost( networkTable_.beginHost( networkTableConfiguration_ ) );
    }

    /**
     * Ensures the {@code endHost} method throws an exception when passed an
     * illegal token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testEndHost_Token_Illegal()
        throws Exception
    {
        networkTable_.endHost( new FutureTask<Void>( new Callable<Void>()
        {
            @Override
            public Void call()
            {
                return null;
            }
        } ) );
    }

    /**
     * Ensures the {@code endHost} method throws an exception when passed a
     * {@code null} token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testEndHost_Token_Null()
        throws Exception
    {
        networkTable_.endHost( null );
    }

    /**
     * Ensures the {@code endJoin} method throws an exception when the network
     * is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testEndJoin_Connected_ThrowsException()
        throws Exception
    {
        networkTable_.endJoin( networkTable_.beginJoin( networkTableConfiguration_ ) );

        networkTable_.endJoin( networkTable_.beginJoin( networkTableConfiguration_ ) );
    }

    /**
     * Ensures the {@code endJoin} method throws an exception when passed an
     * illegal token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testEndJoin_Token_Illegal()
        throws Exception
    {
        networkTable_.endJoin( new FutureTask<Void>( new Callable<Void>()
        {
            @Override
            public Void call()
            {
                return null;
            }
        } ) );
    }

    /**
     * Ensures the {@code endHost} method throws an exception when passed a
     * {@code null} token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testEndJoin_Token_Null()
        throws Exception
    {
        networkTable_.endJoin( null );
    }

    /**
     * Ensures the host operation fires a network connection state changed event
     * when the network is disconnected.
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

        networkTable_.endHost( networkTable_.beginHost( networkTableConfiguration_ ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the join operation fires a network connection state changed event
     * when the network is disconnected.
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

        networkTable_.endJoin( networkTable_.beginJoin( networkTableConfiguration_ ) );

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

        connectNetworkTable();

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
        connectNetworkTable();

        networkTable_.removeNetworkTableListener( listener );
        networkTable_.endDisconnect( networkTable_.beginDisconnect() );

        mocksControl_.verify();
    }
}
