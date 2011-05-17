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
     * @return The remote table proxy controller to be tested; never {@code
     *         null}. The remote table proxy associated with the returned
     *         controller should be in a disconnected state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createRemoteTableProxyController()
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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        controller_ = createRemoteTableProxyController();
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
    }

    /**
     * Ensures the {@code close} method throws an exception when the network is
     * not connected.
     */
    @Test( expected = IllegalStateException.class )
    public void testClose_Disconnected()
    {
        synchronized( controller_.getLock() )
        {
            controller_.close( null );
        }
    }

    /**
     * Ensures the {@code getLocalNode} method does not return {@code null}.
     */
    @Test
    public void testGetLocalNode_ReturnValue_NonNull()
    {
        assertNotNull( controller_.getLocalNode() );
    }

    /**
     * Ensures the {@code getPlayerName} method throws an exception when the
     * network is not connected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPlayerName_Disconnected()
    {
        controller_.getPlayerName();
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when the
     * network is not connected.
     */
    @Test( expected = IllegalStateException.class )
    public void testSendMessage_Disconnected()
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
