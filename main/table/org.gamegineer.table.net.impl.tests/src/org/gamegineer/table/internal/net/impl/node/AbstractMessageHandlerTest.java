/*
 * AbstractMessageHandlerTest.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link AbstractMessageHandler} class.
 */
public final class AbstractMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message handler under test in the fixture. */
    private Optional<MockMessageHandler> messageHandler_;

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessageHandlerTest}
     * class.
     */
    public AbstractMessageHandlerTest()
    {
        messageHandler_ = Optional.empty();
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture.
     */
    private MockMessageHandler getMessageHandler()
    {
        return messageHandler_.get();
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control.
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
        messageHandler_ = Optional.of( new MockMessageHandler() );
    }

    /**
     * Ensures the {@link AbstractMessageHandler#handleMessage} method correctly
     * handles a supported message.
     */
    @Test
    public void testHandleMessage_Message_Supported()
    {
        final MockMessageHandler messageHandler = getMessageHandler();
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteNodeController<@NonNull ?> remoteNodeController = mocksControl.createMock( IRemoteNodeController.class );
        final IMessage message = new FakeMessage();
        mocksControl.replay();

        messageHandler.handleMessage( remoteNodeController, message );

        assertEquals( 1, messageHandler.getHandleFakeMessageCallCount() );
    }

    /**
     * Ensures the {@link AbstractMessageHandler#handleMessage} method correctly
     * handles an unsupported message.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_Message_Unsupported()
    {
        final MockMessageHandler messageHandler = getMessageHandler();
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteNodeController<@NonNull ?> remoteNodeController = mocksControl.createMock( IRemoteNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@Nullable IMessageHandler>isNull() );
        final IMessage message = mocksControl.createMock( IMessage.class );
        EasyMock.expect( message.getId() ).andReturn( IMessage.MINIMUM_ID ).anyTimes();
        EasyMock.expect( message.getCorrelationId() ).andReturn( IMessage.MAXIMUM_ID ).anyTimes();
        mocksControl.replay();

        messageHandler.handleMessage( remoteNodeController, message );

        mocksControl.verify();
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
        }
    }

    /**
     * A mock message handler.
     */
    @NotThreadSafe
    @SuppressWarnings( "rawtypes" )
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
         *        message.
         * @param message
         *        The message.
         */
        @SuppressWarnings( "unused" )
        private void handleMessage(
            final IRemoteNodeController<@NonNull ?> remoteNodeController,
            final FakeMessage message )
        {
            ++handleFakeMessageCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.AbstractMessageHandler#handleUnexpectedMessage(org.gamegineer.table.internal.net.impl.node.IRemoteNodeController)
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
