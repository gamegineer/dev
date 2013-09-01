/*
 * FakeEventHandler.java
 * Copyright 2008-2011 Gamegineer contributors and others.
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
 * Created on Jan 27, 2011 at 7:59:09 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.nio.channels.SelectableChannel;
import net.jcip.annotations.NotThreadSafe;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler}
 */
@NotThreadSafe
class FakeEventHandler
    extends AbstractEventHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The event handler channel. */
    private final SelectableChannel channel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeEventHandler} class with a
     * default fake channel.
     * 
     * @param transportLayer
     *        The transport layer associated with the event handler; must not be
     *        {@code null}.
     */
    FakeEventHandler(
        /* @NonNull */
        final AbstractTransportLayer transportLayer )
    {
        this( transportLayer, new FakeSelectableChannel() );
    }

    /**
     * Initializes a new instance of the {@code FakeEventHandler} class with the
     * specified channel.
     * 
     * @param transportLayer
     *        The transport layer associated with the event handler; must not be
     *        {@code null}.
     * @param channel
     *        The event handler channel; must not be {@code null}.
     */
    FakeEventHandler(
        /* @NonNull */
        final AbstractTransportLayer transportLayer,
        /* @NonNull */
        final SelectableChannel channel )
    {
        super( transportLayer );

        assert channel != null;

        channel_ = channel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#close(java.lang.Exception)
     */
    @Override
    void close(
        @SuppressWarnings( "unused" )
        final Exception exception )
    {
        assert isTransportLayerThread();

        setState( State.CLOSED );
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    SelectableChannel getChannel()
    {
        assert isTransportLayerThread();

        return channel_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    int getInterestOperations()
    {
        assert isTransportLayerThread();

        return 0;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#run()
     */
    @Override
    void run()
    {
        assert isTransportLayerThread();

        // do nothing
    }
}
