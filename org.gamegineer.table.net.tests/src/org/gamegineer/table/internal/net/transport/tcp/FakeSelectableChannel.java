/*
 * FakeSelectableChannel.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Jan 27, 2011 at 7:56:15 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import net.jcip.annotations.NotThreadSafe;

/**
 * Fake implementation of {@link java.nio.channels.SelectableChannel}.
 */
@NotThreadSafe
class FakeSelectableChannel
    extends SelectableChannel
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeSelectableChannel} class.
     */
    FakeSelectableChannel()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.nio.channels.SelectableChannel#blockingLock()
     */
    @Override
    public Object blockingLock()
    {
        return null;
    }

    /*
     * @see java.nio.channels.SelectableChannel#configureBlocking(boolean)
     */
    @Override
    @SuppressWarnings( "unused" )
    public SelectableChannel configureBlocking(
        final boolean block )
        throws IOException
    {
        return null;
    }

    /*
     * @see java.nio.channels.spi.AbstractInterruptibleChannel#implCloseChannel()
     */
    @Override
    @SuppressWarnings( "unused" )
    protected void implCloseChannel()
        throws IOException
    {
        // do nothing
    }

    /*
     * @see java.nio.channels.SelectableChannel#isBlocking()
     */
    @Override
    public boolean isBlocking()
    {
        return false;
    }

    /*
     * @see java.nio.channels.SelectableChannel#isRegistered()
     */
    @Override
    public boolean isRegistered()
    {
        return false;
    }

    /*
     * @see java.nio.channels.SelectableChannel#keyFor(java.nio.channels.Selector)
     */
    @Override
    public SelectionKey keyFor(
        @SuppressWarnings( "unused" )
        final Selector selector )
    {
        return null;
    }

    /*
     * @see java.nio.channels.SelectableChannel#provider()
     */
    @Override
    public SelectorProvider provider()
    {
        return null;
    }

    /*
     * @see java.nio.channels.SelectableChannel#register(java.nio.channels.Selector, int, java.lang.Object)
     */
    @Override
    @SuppressWarnings( "unused" )
    public SelectionKey register(
        final Selector selector,
        final int ops,
        final Object attachment )
        throws ClosedChannelException
    {
        return null;
    }

    /*
     * @see java.nio.channels.SelectableChannel#validOps()
     */
    @Override
    public int validOps()
    {
        return 0;
    }
}
