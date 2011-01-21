/*
 * Mocks.java
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
 * Created on Jan 15, 2011 at 10:51:31 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for creating mock objects associated with the
 * TCP implementation of the network table Acceptor-Connector pattern.
 */
@ThreadSafe
final class Mocks
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Mocks} class.
     */
    private Mocks()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock event.
     * 
     * @return A mock event; never {@code null}.
     */
    /* @NonNull */
    static SelectionKey createMockEvent()
    {
        return new SelectionKey()
        {
            @Override
            public void cancel()
            {
                // do nothing
            }

            @Override
            public SelectableChannel channel()
            {
                return null;
            }

            @Override
            public int interestOps()
            {
                return 0;
            }

            @Override
            public SelectionKey interestOps(
                @SuppressWarnings( "unused" )
                final int ops )
            {
                return null;
            }

            @Override
            public boolean isValid()
            {
                return false;
            }

            @Override
            public int readyOps()
            {
                return 0;
            }

            @Override
            public Selector selector()
            {
                return null;
            }
        };
    }

    /**
     * Creates a mock event handler.
     * 
     * @return A mock event handler; never {@code null}.
     */
    /* @NonNull */
    static AbstractEventHandler createMockEventHandler()
    {
        return new AbstractEventHandler()
        {
            @Override
            void close()
            {
                // do nothing
            }

            @Override
            int getEvents()
            {
                return 0;
            }

            @Override
            SelectableChannel getTransportHandle()
            {
                return null;
            }

            @Override
            void handleEvent(
                @SuppressWarnings( "unused" )
                final SelectionKey event )
            {
                // do nothing
            }
        };
    }

    /**
     * Creates a mock transport handle.
     * 
     * @return A mock transport handle; never {@code null}.
     */
    /* @NonNull */
    static SelectableChannel createMockTransportHandle()
    {
        return new SelectableChannel()
        {
            @Override
            public Object blockingLock()
            {
                return null;
            }

            @Override
            public SelectableChannel configureBlocking(
                @SuppressWarnings( "unused" )
                final boolean block )
            {
                return null;
            }

            @Override
            protected void implCloseChannel()
            {
                // do nothing
            }

            @Override
            public boolean isBlocking()
            {
                return false;
            }

            @Override
            public boolean isRegistered()
            {
                return false;
            }

            @Override
            public SelectionKey keyFor(
                @SuppressWarnings( "unused" )
                final Selector sel )
            {
                return null;
            }

            @Override
            public SelectorProvider provider()
            {
                return null;
            }

            @Override
            public SelectionKey register(
                @SuppressWarnings( "unused" )
                final Selector sel,
                @SuppressWarnings( "unused" )
                final int ops,
                @SuppressWarnings( "unused" )
                final Object att )
            {
                return null;
            }

            @Override
            public int validOps()
            {
                return 0;
            }
        };
    }
}
