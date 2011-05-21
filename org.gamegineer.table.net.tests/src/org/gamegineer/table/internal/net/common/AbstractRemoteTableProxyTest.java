/*
 * AbstractRemoteTableProxyTest.java
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

import static org.junit.Assert.assertEquals;
import java.util.Collection;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableProxy}
 * class.
 */
public final class AbstractRemoteTableProxyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The remote table proxy under test in the fixture. */
    private AbstractRemoteTableProxy remoteTableProxy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteTableProxyTest}
     * class.
     */
    public AbstractRemoteTableProxyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractRemoteTableProxy} class.
     * 
     * @param node
     *        The local table network node; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractRemoteTableProxy} class;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    /* @NonNull */
    private static AbstractRemoteTableProxy createRemoteTableProxy(
        /* @NonNull */
        final ITableNetworkNode node )
    {
        return new AbstractRemoteTableProxy( node )
        {
            @Override
            public void setPlayers(
                @SuppressWarnings( "unused" )
                final Collection<String> players )
            {
                // do nothing
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
        mocksControl_ = EasyMock.createControl();
        remoteTableProxy_ = createRemoteTableProxy( EasyMock.createMock( ITableNetworkNode.class ) );
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
        remoteTableProxy_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network node.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableNetworkNode_Null()
    {
        createRemoteTableProxy( null );
    }

    /**
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unhandled message with a
     * non-null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Correlated()
        throws Exception
    {
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( serviceContext.sendMessage( EasyMock.capture( messageCapture ) ) ).andReturn( true );
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.MAXIMUM_ID );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl_.replay();
        remoteTableProxy_.started( serviceContext );

        remoteTableProxy_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNHANDLED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unhandled message with a
     * null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Uncorrelated()
        throws Exception
    {
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( serviceContext.sendMessage( EasyMock.capture( messageCapture ) ) ).andReturn( true );
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.NULL_CORRELATION_ID );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl_.replay();
        remoteTableProxy_.started( serviceContext );

        remoteTableProxy_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNHANDLED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unknown message.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnknownMessage()
    {
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( serviceContext.sendMessage( EasyMock.capture( messageCapture ) ) ).andReturn( true );
        final MessageEnvelope messageEnvelope = new MessageEnvelope( IMessage.MINIMUM_ID, IMessage.NULL_CORRELATION_ID, new byte[ 0 ] );
        mocksControl_.replay();
        remoteTableProxy_.started( serviceContext );

        remoteTableProxy_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNKNOWN_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message handler.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_MessageHandler_Null()
    {
        remoteTableProxy_.registerUncorrelatedMessageHandler( IMessage.class, null );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message type.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Null()
    {
        remoteTableProxy_.registerUncorrelatedMessageHandler( null, EasyMock.createMock( IRemoteTableProxyController.IMessageHandler.class ) );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a message type that is present in the message
     * handler collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Present()
    {
        remoteTableProxy_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IRemoteTableProxyController.IMessageHandler.class ) );

        remoteTableProxy_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IRemoteTableProxyController.IMessageHandler.class ) );
    }
}
