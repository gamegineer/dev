/*
 * AbstractMessageHandlerTest.java
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
 * Created on Apr 26, 2011 at 8:33:52 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.junit.Assert.assertEquals;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.IRemoteNodeController.IMessageHandler;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.AbstractMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.AbstractRemoteNode.AbstractMessageHandler}
 * class.
 */
public final class AbstractMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessageHandlerTest}
     * class.
     */
    public AbstractMessageHandlerTest()
    {
        super();
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
        mocksControl_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * remote node controller.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_RemoteNodeController_Null()
    {
        new AbstractRemoteNode.AbstractMessageHandler<IRemoteNodeController<?>>( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a supported
     * message.
     */
    @Test
    public void testHandleMessage_Message_Supported()
    {
        final IRemoteNodeController<?> controller = mocksControl_.createMock( IRemoteNodeController.class );
        final IMessage message = new FakeMessage();
        final MockMessageHandler messageHandler = new MockMessageHandler( controller );
        mocksControl_.replay();

        messageHandler.handleMessage( message );

        assertEquals( 1, messageHandler.getHandleFakeMessageCallCount() );
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles an unsupported
     * message.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_Message_Unsupported()
    {
        final IRemoteNodeController<?> controller = mocksControl_.createMock( IRemoteNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( controller.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) ) ).andReturn( true );
        final IMessage message = mocksControl_.createMock( IMessage.class );
        EasyMock.expect( message.getId() ).andReturn( IMessage.MINIMUM_ID ).anyTimes();
        EasyMock.expect( message.getCorrelationId() ).andReturn( IMessage.MAXIMUM_ID ).anyTimes();
        final MockMessageHandler messageHandler = new MockMessageHandler( controller );
        mocksControl_.replay();

        messageHandler.handleMessage( message );

        mocksControl_.verify();
        assertEquals( 1, messageHandler.getHandleUnexpectedMessageCallCount() );
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNEXPECTED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake message.
     */
    @Immutable
    private static final class FakeMessage
        extends AbstractMessage
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 1L;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeMessage} class.
         */
        FakeMessage()
        {
            super();
        }
    }

    /**
     * A mock message handler.
     */
    @NotThreadSafe
    private static final class MockMessageHandler
        extends AbstractRemoteNode.AbstractMessageHandler<IRemoteNodeController<?>>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The count of calls made to the {@link #handleMessage(FakeMessage)}
         * method.
         */
        private int handleFakeMessageCallCount_;

        /**
         * The count of calls made to the {@link #handleUnexpectedMessage()}
         * method.
         */
        private int handleUnexpectedMessageCallCount_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockMessageHandler} class.
         * 
         * @param remoteNodeController
         *        The remote node controller associated with the message
         *        handler; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteNodeController} is {@code null}.
         */
        MockMessageHandler(
            /* @NonNull */
            final IRemoteNodeController<?> remoteNodeController )
        {
            super( remoteNodeController );

            handleFakeMessageCallCount_ = 0;
            handleUnexpectedMessageCallCount_ = 0;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the count of calls made to the
         * {@link #handleMessage(FakeMessage)} method.
         * 
         * @return The count of calls made to the
         *         {@link #handleMessage(FakeMessage)} method.
         */
        int getHandleFakeMessageCallCount()
        {
            return handleFakeMessageCallCount_;
        }

        /**
         * Gets the count of calls made to the
         * {@link #handleUnexpectedMessage()} method.
         * 
         * @return The count of calls made to the
         *         {@link #handleUnexpectedMessage()} method.
         */
        int getHandleUnexpectedMessageCallCount()
        {
            return handleUnexpectedMessageCallCount_;
        }

        /**
         * Handles a {@code FakeMessage} message.
         * 
         * @param message
         *        The message; must not be {@code null}.
         */
        @SuppressWarnings( "unused" )
        private void handleMessage(
            /* @NonNull */
            final FakeMessage message )
        {
            assert message != null;

            ++handleFakeMessageCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode.AbstractMessageHandler#handleUnexpectedMessage()
         */
        @Override
        protected void handleUnexpectedMessage()
        {
            ++handleUnexpectedMessageCallCount_;
        }
    }
}
