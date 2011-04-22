/*
 * AbstractRemoteTableGatewayTest.java
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
 * Created on Apr 14, 2011 at 11:03:54 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public final class AbstractRemoteTableGatewayTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The remote table gateway under test in the fixture. */
    private AbstractRemoteTableGateway remoteTableGateway_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteTableGatewayTest}
     * class.
     */
    public AbstractRemoteTableGatewayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractRemoteTableGateway} class.
     * 
     * @param tableGatewayContext
     *        The table gateway context; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractRemoteTableGateway} class;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableGatewayContext} is {@code null}.
     */
    /* @NonNull */
    private static AbstractRemoteTableGateway createRemoteTableGateway(
        /* @NonNull */
        final ITableGatewayContext tableGatewayContext )
    {
        return new AbstractRemoteTableGateway( tableGatewayContext )
        {
            @Override
            public String getPlayerName()
            {
                throw new AssertionError( "not implemented" ); //$NON-NLS-1$
            }
        };
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
        remoteTableGateway_ = createRemoteTableGateway( EasyMock.createMock( ITableGatewayContext.class ) );
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
        remoteTableGateway_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table gateway context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableGatewayContext_Null()
    {
        createRemoteTableGateway( null );
    }

    /**
     * Ensures the {@code getServiceContext} method throws an exception when the
     * network is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetServiceContext_Disconnected()
    {
        remoteTableGateway_.getServiceContext();
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when the
     * network is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testSendMessage_Disconnected()
    {
        synchronized( remoteTableGateway_.getLock() )
        {
            remoteTableGateway_.sendMessage( EasyMock.createMock( IMessage.class ) );
        }
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when passed a
     * {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testSendMessage_Message_Null()
    {
        remoteTableGateway_.sendMessage( null );
    }

    /**
     * Ensures the {@code stop} method throws an exception when the network is
     * disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testStop_Disconnected()
    {
        synchronized( remoteTableGateway_.getLock() )
        {
            remoteTableGateway_.stop();
        }
    }
}
