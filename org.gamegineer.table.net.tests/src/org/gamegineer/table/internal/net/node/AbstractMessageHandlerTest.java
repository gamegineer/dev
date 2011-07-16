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
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.AbstractMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.AbstractMessageHandler} class.
 */
public final class AbstractMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message handler under test in the fixture. */
    private MockMessageHandler messageHandler_;

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
        messageHandler_ = new MockMessageHandler();
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * remote node controller type.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_RemoteNodeControllerType_Null()
    {
        new AbstractMessageHandler<IRemoteNodeController<?>>( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code handleMessage} method throws an exception when passed
     * a {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testHandleMessage_Message_Null()
    {
        messageHandler_.handleMessage( mocksControl_.createMock( IRemoteNodeController.class ), (IMessage)null );
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a supported
     * message.
     */
    @Test
    public void testHandleMessage_Message_Supported()
    {
        final IRemoteNodeController<?> remoteNodeController = mocksControl_.createMock( IRemoteNodeController.class );
        final IMessage message = new FakeMessage();
        mocksControl_.replay();

        messageHandler_.handleMessage( remoteNodeController, message );

        assertEquals( 1, messageHandler_.getHandleFakeMessageCallCount() );
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles an unsupported
     * message.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_Message_Unsupported()
    {
        final IRemoteNodeController<?> remoteNodeController = mocksControl_.createMock( IRemoteNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) );
        final IMessage message = mocksControl_.createMock( IMessage.class );
        EasyMock.expect( message.getId() ).andReturn( IMessage.MINIMUM_ID ).anyTimes();
        EasyMock.expect( message.getCorrelationId() ).andReturn( IMessage.MAXIMUM_ID ).anyTimes();
        mocksControl_.replay();

        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
        assertEquals( 1, messageHandler_.getHandleUnexpectedMessageCallCount() );
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNEXPECTED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@code handleMessage} method throws an exception when passed
     * a {@code null} remote node controller.
     */
    @Test( expected = NullPointerException.class )
    public void testHandleMessage_RemoteNodeController_Null()
    {
        final IMessage message = new FakeMessage();

        messageHandler_.handleMessage( null, message );
    }

    /**
     * Ensures the {@code handleUnexpectedMessage} method throws an exception
     * when passed a {@code null} remote node controller.
     */
    @Test( expected = NullPointerException.class )
    public void testHandleUnexpectedMessage_RemoteNodeController_Null()
    {
        messageHandler_.handleUnexpectedMessage( null );
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
    @SuppressWarnings( "unchecked" )
    private static final class MockMessageHandler
        extends AbstractMessageHandler<IRemoteNodeController>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The count of calls made to the
         * {@link #handleMessage(IRemoteNodeController, FakeMessage)} method.
         */
        private int handleFakeMessageCallCount_;

        /**
         * The count of calls made to the
         * {@link #handleUnexpectedMessage(IRemoteNodeController)} method.
         */
        private int handleUnexpectedMessageCallCount_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockMessageHandler} class.
         */
        MockMessageHandler()
        {
            super( IRemoteNodeController.class );

            handleFakeMessageCallCount_ = 0;
            handleUnexpectedMessageCallCount_ = 0;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the count of calls made to the
         * {@link #handleMessage(IRemoteNodeController, FakeMessage)} method.
         * 
         * @return The count of calls made to the
         *         {@link #handleMessage(IRemoteNodeController, FakeMessage)}
         *         method.
         */
        int getHandleFakeMessageCallCount()
        {
            return handleFakeMessageCallCount_;
        }

        /**
         * Gets the count of calls made to the
         * {@link #handleUnexpectedMessage(IRemoteNodeController)} method.
         * 
         * @return The count of calls made to the
         *         {@link #handleUnexpectedMessage(IRemoteNodeController)}
         *         method.
         */
        int getHandleUnexpectedMessageCallCount()
        {
            return handleUnexpectedMessageCallCount_;
        }

        /**
         * Handles a {@code FakeMessage} message.
         * 
         * @param remoteNodeController
         *        The control interface for the remote node that received the
         *        message; must not be {@code null}.
         * @param message
         *        The message; must not be {@code null}.
         */
        @SuppressWarnings( "unused" )
        private void handleMessage(
            /* @NonNull */
            final IRemoteNodeController<?> remoteNodeController,
            /* @NonNull */
            final FakeMessage message )
        {
            assert remoteNodeController != null;
            assert message != null;

            ++handleFakeMessageCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractMessageHandler#handleUnexpectedMessage(org.gamegineer.table.internal.net.node.IRemoteNodeController)
         */
        @Override
        protected void handleUnexpectedMessage(
            final IRemoteNodeController remoteNodeController )
        {
            super.handleUnexpectedMessage( remoteNodeController );

            ++handleUnexpectedMessageCallCount_;
        }
    }
}
