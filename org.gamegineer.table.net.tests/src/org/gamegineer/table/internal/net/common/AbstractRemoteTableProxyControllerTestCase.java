/*
 * AbstractRemoteTableProxyControllerTestCase.java
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
 * Created on Apr 23, 2011 at 9:12:20 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.ITableProxy;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.common.IRemoteTableProxyController}
 * interface.
 * 
 * @param <T>
 *        The type of the remote table proxy controller.
 */
public abstract class AbstractRemoteTableProxyControllerTestCase<T extends IRemoteTableProxyController>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /** The remote table proxy controller under test in the fixture. */
    private T controller_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table network node for use in the fixture. */
    private ITableNetworkNode node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractRemoteTableProxyControllerTestCase} class.
     */
    protected AbstractRemoteTableProxyControllerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the remote table proxy controller to be tested.
     * 
     * @param node
     *        local table network node; must not be {@code null}.
     * 
     * @return The remote table proxy controller to be tested; never {@code
     *         null}. The remote table proxy associated with the returned
     *         controller should be in the closed state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    /* @NonNull */
    protected abstract T createRemoteTableProxyController(
        /* @NonNull */
        ITableNetworkNode node )
        throws Exception;

    /**
     * Gets the remote table proxy controller under test in the fixture.
     * 
     * @return The remote table proxy controller under test in the fixture;
     *         never {@code null}.
     */
    /* @NonNull */
    protected final T getRemoteTableProxyController()
    {
        assertNotNull( controller_ );
        return controller_;
    }

    /**
     * Opens the remote table proxy associated with the specified controller.
     * 
     * @param controller
     *        The remote table proxy controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code controller} is {@code null}.
     */
    protected abstract void openRemoteTableProxy(
        /* @NonNull */
        T controller );

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
        node_ = mocksControl_.createMock( ITableNetworkNode.class );
        controller_ = createRemoteTableProxyController( node_ );
        assertNotNull( controller_ );
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
        controller_ = null;
        node_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code bind} method throws an exception when the remote table
     * proxy is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_Bound()
    {
        synchronized( controller_.getLock() )
        {
            openRemoteTableProxy( controller_ );
            controller_.bind( "playerName" ); //$NON-NLS-1$

            controller_.bind( "playerName" ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@code bind} method throws an exception when the remote table
     * proxy is closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_Closed()
    {
        synchronized( controller_.getLock() )
        {
            controller_.bind( "playerName" ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@code bind} method throws an exception when passed a player
     * name that is associated with a table that is already bound to the local
     * node.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBind_PlayerName_Bound()
    {
        final String playerName = "playerName"; //$NON-NLS-1$
        node_.addTableProxy( EasyMock.notNull( ITableProxy.class ) );
        EasyMock.expectLastCall().andThrow( new IllegalArgumentException() );
        mocksControl_.replay();

        synchronized( controller_.getLock() )
        {
            openRemoteTableProxy( controller_ );

            controller_.bind( playerName );
        }
    }

    /**
     * Ensures the {@code bind} method throws an exception when passed a {@code
     * null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testBind_PlayerName_Null()
    {
        synchronized( controller_.getLock() )
        {
            controller_.bind( null );
        }
    }

    /**
     * Ensures the {@code close} method throws an exception when the remote
     * table proxy is closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testClose_Closed()
    {
        synchronized( controller_.getLock() )
        {
            controller_.close( null );
        }
    }

    /**
     * Ensures the {@code getNode} method does not return {@code null}.
     */
    @Test
    public void testGetNode_ReturnValue_NonNull()
    {
        assertNotNull( controller_.getNode() );
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when the
     * remote table proxy is closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testSendMessage_Closed()
    {
        synchronized( controller_.getLock() )
        {
            controller_.sendMessage( EasyMock.createMock( IMessage.class ), EasyMock.createMock( IRemoteTableProxyController.IMessageHandler.class ) );
        }
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when passed a
     * {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testSendMessage_Message_Null()
    {
        synchronized( controller_.getLock() )
        {
            controller_.sendMessage( null, EasyMock.createMock( IRemoteTableProxyController.IMessageHandler.class ) );
        }
    }
}
