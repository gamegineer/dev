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

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertEquals;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.messages.AbstractMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway.AbstractMessageHandler}
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
     * remote table gateway.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_RemoteTableGateway_Null()
    {
        new AbstractRemoteTableGateway.AbstractMessageHandler<IRemoteTableGateway>( null )
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
        final IRemoteTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteTableGateway.class );
        final IMessage message = new FakeMessage();
        final MockMessageHandler messageHandler = new MockMessageHandler( remoteTableGateway );
        mocksControl_.replay();

        messageHandler.handleMessage( message );

        assertEquals( 1, messageHandler.getHandleFakeMessageCallCount() );
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles an unsupported
     * message.
     */
    @Test
    public void testHandleMessage_Message_Unsupported()
    {
        final IRemoteTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteTableGateway.class );
        final IMessage message = mocksControl_.createMock( IMessage.class );
        final MockMessageHandler messageHandler = new MockMessageHandler( remoteTableGateway );
        mocksControl_.replay();

        messageHandler.handleMessage( message );

        assertEquals( 1, messageHandler.getHandleUnsupportedMessageCallCount() );

        // TODO: Eventually assert that handler sends an error response via
        // the remote table gateway.
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
        extends AbstractRemoteTableGateway.AbstractMessageHandler<IRemoteTableGateway>
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
         * The count of calls made to the {@link #handleUnsupportedMessage()}
         * method.
         */
        private int handleUnsupportedMessageCallCount_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockMessageHandler} class.
         * 
         * @param remoteTableGateway
         *        The remote table gateway associated with the message handler;
         *        must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableGateway} is {@code null}.
         */
        MockMessageHandler(
            /* @NonNull */
            final IRemoteTableGateway remoteTableGateway )
        {
            super( remoteTableGateway );

            handleFakeMessageCallCount_ = 0;
            handleUnsupportedMessageCallCount_ = 0;
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
         * {@link #handleUnsupportedMessage()} method.
         * 
         * @return The count of calls made to the
         *         {@link #handleUnsupportedMessage()} method.
         */
        int getHandleUnsupportedMessageCallCount()
        {
            return handleUnsupportedMessageCallCount_;
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
         * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway.AbstractMessageHandler#handleUnsupportedMessage()
         */
        @Override
        protected void handleUnsupportedMessage()
        {
            ++handleUnsupportedMessageCallCount_;
        }
    }
}
