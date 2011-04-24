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
     * Creates a new stub message handler.
     * 
     * @return A new stub message handler; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "unchecked" )
    private static IRemoteTableGateway.IMessageHandler<?, IMessage> createStubMessageHandler()
    {
        return EasyMock.createMock( IRemoteTableGateway.IMessageHandler.class );
    }

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
            // no overrides
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
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message handler.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_MessageHandler_Null()
    {
        remoteTableGateway_.registerUncorrelatedMessageHandler( IMessage.class, null );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message type.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Null()
    {
        remoteTableGateway_.registerUncorrelatedMessageHandler( null, createStubMessageHandler() );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a message type that is present in the message
     * handler collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Present()
    {
        remoteTableGateway_.registerUncorrelatedMessageHandler( IMessage.class, createStubMessageHandler() );

        remoteTableGateway_.registerUncorrelatedMessageHandler( IMessage.class, createStubMessageHandler() );
    }
}
