/*
 * FakeEventHandler.java
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
     */
    FakeEventHandler()
    {
        this( new FakeSelectableChannel() );
    }

    /**
     * Initializes a new instance of the {@code FakeEventHandler} class with the
     * specified channel.
     * 
     * @param channel
     *        The event handler channel; must not be {@code null}.
     */
    FakeEventHandler(
        /* @NonNull */
        final SelectableChannel channel )
    {
        assert channel != null;

        channel_ = channel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#close()
     */
    @Override
    void close()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    SelectableChannel getChannel()
    {
        return channel_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    int getInterestOperations()
    {
        return 0;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#run()
     */
    @Override
    void run()
    {
        // do nothing
    }
}
