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

import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
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
public final class EndAuthenticationMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code EndAuthenticationMessageHandlerTest} class.
     */
    public EndAuthenticationMessageHandlerTest()
    {
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture; never {@code null}
     *         .
     */
    private IMessageHandler getMessageHandler()
    {
        return EndAuthenticationMessageHandler.INSTANCE;
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control; never {@code null}.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
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
        mocksControl_ = Optional.of( EasyMock.createControl() );
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
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteServerNodeController remoteNodeController = mocksControl.createMock( IRemoteServerNodeController.class );
        remoteNodeController.bind( EasyMock.<@NonNull String>notNull() );
        mocksControl.replay();

        final EndAuthenticationMessage message = new EndAuthenticationMessage();
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link EndAuthenticationMessageHandler#handleMessage} method
     * correctly handles an error message.
     */
    @Test
    public void testHandleMessage_ErrorMessage()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteServerNodeController remoteNodeController = mocksControl.createMock( IRemoteServerNodeController.class );
        remoteNodeController.close( TableNetworkError.UNSPECIFIED_ERROR );
        mocksControl.replay();

        final ErrorMessage message = new ErrorMessage();
        message.setError( TableNetworkError.UNSPECIFIED_ERROR );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link EndAuthenticationMessageHandler#handleMessage} method
     * correctly handles an unexpected message.
     */
    @Test
    public void testHandleMessage_UnexpectedMessage()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteServerNodeController remoteNodeController = mocksControl.createMock( IRemoteServerNodeController.class );
        remoteNodeController.sendMessage( EasyMock.<@NonNull IMessage>notNull(), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
        mocksControl.replay();

        final FakeMessage message = new FakeMessage();
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }
}
