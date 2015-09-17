/*
 * EndAuthenticationMessageHandlerTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Apr 29, 2011 at 10:35:10 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.transport.FakeMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link EndAuthenticationMessageHandler} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class EndAuthenticationMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message handler under test in the fixture. */
    private IMessageHandler messageHandler_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code EndAuthenticationMessageHandlerTest} class.
     */
    public EndAuthenticationMessageHandlerTest()
    {
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
        mocksControl_ = EasyMock.createControl();
        messageHandler_ = EndAuthenticationMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@link EndAuthenticationMessageHandler#handleMessage} method
     * correctly handles an end authentication message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_EndAuthenticationMessage()
        throws Exception
    {
        final IRemoteServerNodeController remoteNodeController = mocksControl_.createMock( IRemoteServerNodeController.class );
        remoteNodeController.bind( EasyMock.<@NonNull String>notNull() );
        mocksControl_.replay();

        final EndAuthenticationMessage message = new EndAuthenticationMessage();
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link EndAuthenticationMessageHandler#handleMessage} method
     * correctly handles an error message.
     */
    @Test
    public void testHandleMessage_ErrorMessage()
    {
        final IRemoteServerNodeController remoteNodeController = mocksControl_.createMock( IRemoteServerNodeController.class );
        remoteNodeController.close( TableNetworkError.UNSPECIFIED_ERROR );
        mocksControl_.replay();

        final ErrorMessage message = new ErrorMessage();
        message.setError( TableNetworkError.UNSPECIFIED_ERROR );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link EndAuthenticationMessageHandler#handleMessage} method
     * correctly handles an unexpected message.
     */
    @Test
    public void testHandleMessage_UnexpectedMessage()
    {
        final IRemoteServerNodeController remoteNodeController = mocksControl_.createMock( IRemoteServerNodeController.class );
        remoteNodeController.sendMessage( EasyMock.<@NonNull IMessage>notNull(), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
        mocksControl_.replay();

        final FakeMessage message = new FakeMessage();
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
