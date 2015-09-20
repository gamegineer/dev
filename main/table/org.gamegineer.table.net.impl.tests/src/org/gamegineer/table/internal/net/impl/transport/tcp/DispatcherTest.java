/*
 * DispatcherTest.java
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
 * Created on Jan 13, 2011 at 10:58:09 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Dispatcher} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class DispatcherTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher under test in the fixture. */
    private volatile Dispatcher dispatcher_;

    /** The transport layer for use in the fixture. */
    private volatile AbstractTransportLayer transportLayer_;

    /** The transport layer runner for use in the fixture. */
    private TransportLayerRunner transportLayerRunner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DispatcherTest} class.
     */
    public DispatcherTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Synchronously closes the dispatcher under test in the fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void closeDispatcher()
        throws Exception
    {
        // Invoke beginClose() on transport layer thread
        final Future<@Nullable Void> dispatcherCloseFuture = transportLayerRunner_.run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<@Nullable Void> call()
            {
                return dispatcher_.beginClose();
            }
        } );
        assertNotNull( dispatcherCloseFuture );

        // Wait for dispatcher to close on current thread
        dispatcher_.endClose( dispatcherCloseFuture );
    }

    /**
     * Gets the fixture transport layer.
     * 
     * @return The fixture transport layer; never {@code null}.
     */
    private AbstractTransportLayer getTransportLayer()
    {
        assertNotNull( transportLayer_ );
        return transportLayer_;
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
        final AbstractTransportLayer transportLayer = transportLayer_ = new FakeTransportLayer.Factory().createTransportLayer( EasyMock.createMock( ITransportLayerContext.class ) );
        transportLayerRunner_ = new TransportLayerRunner( transportLayer );

        transportLayerRunner_.run( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
            {
                dispatcher_ = new Dispatcher( transportLayer );
                dispatcher_.setEventHandlerShutdownTimeout( 500L );
                return null;
            }
        } );
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
        closeDispatcher();
        transportLayerRunner_.close();
    }

    /**
     * Ensures closing the dispatcher closes any event handler that is still
     * registered when the dispatcher is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testClose_ClosesRegisteredEventHandlers()
        throws Exception
    {
        final AtomicReference<AbstractEventHandler> eventHandlerRef = new AtomicReference<>();
        transportLayerRunner_.run( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
                throws Exception
            {
                dispatcher_.open();
                final AbstractEventHandler eventHandler = new FakeEventHandler( getTransportLayer() )
                {
                    {
                        setState( State.OPEN );
                    }
                };
                eventHandlerRef.set( eventHandler );
                dispatcher_.registerEventHandler( eventHandler );
                assertEquals( State.OPEN, eventHandler.getState() );

                return null;
            }
        } );
        closeDispatcher();
        transportLayerRunner_.run( new Callable<@Nullable Void>()
        {
            @Override
            public @Nullable Void call()
            {
                final AbstractEventHandler eventHandler = eventHandlerRef.get();
                assertEquals( State.CLOSED, eventHandler.getState() );

                return null;
            }
        } );
    }

    /**
     * Ensures the {@link Dispatcher#registerEventHandler} method throws an
     * exception if the event handler channel is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = ClosedChannelException.class )
    public void testRegisterEventHandler_EventHandler_ChannelClosed()
        throws Exception
    {
        transportLayerRunner_.run( //
            new Callable<@Nullable Void>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public @Nullable Void call()
                    throws Exception
                {
                    final SelectableChannel channel = new FakeSelectableChannel()
                    {
                        @Override
                        public @Nullable SelectionKey register(
                            final @Nullable Selector selector,
                            final int ops,
                            final @Nullable Object attachment )
                            throws ClosedChannelException
                        {
                            throw new ClosedChannelException();
                        }
                    };
                    dispatcher_.open();

                    dispatcher_.registerEventHandler( new FakeEventHandler( getTransportLayer(), channel ) );

                    return null;
                }
            }, //
            nonNull( ClosedChannelException.class ) );
    }
}
