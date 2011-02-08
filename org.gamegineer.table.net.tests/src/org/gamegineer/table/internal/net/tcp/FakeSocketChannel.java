/*
 * FakeSocketChannel.java
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
 * Created on Feb 7, 2011 at 8:28:05 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import net.jcip.annotations.NotThreadSafe;

/**
 * Fake implementation of {@link java.nio.channels.SocketChannel}.
 */
@NotThreadSafe
class FakeSocketChannel
    extends SocketChannel
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeSocketChannel} class using a
     * default selector provider.
     */
    FakeSocketChannel()
    {
        this( new FakeSelectorProvider() );
    }

    /**
     * Initializes a new instance of the {@code FakeSocketChannel} class using
     * the specified selector provider; must not be {@code null}.
     * 
     * @param provider
     *        The selector provider; must not be {@code null}.
     */
    FakeSocketChannel(
        final SelectorProvider provider )
    {
        super( provider );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.nio.channels.SocketChannel#connect(java.net.SocketAddress)
     */
    @Override
    @SuppressWarnings( "unused" )
    public boolean connect(
        final SocketAddress remote )
        throws IOException
    {
        return false;
    }

    /*
     * @see java.nio.channels.SocketChannel#finishConnect()
     */
    @Override
    @SuppressWarnings( "unused" )
    public boolean finishConnect()
        throws IOException
    {
        return false;
    }

    /*
     * @see java.nio.channels.spi.AbstractSelectableChannel#implCloseSelectableChannel()
     */
    @Override
    @SuppressWarnings( "unused" )
    protected void implCloseSelectableChannel()
        throws IOException
    {
        // do nothing
    }

    /*
     * @see java.nio.channels.spi.AbstractSelectableChannel#implConfigureBlocking(boolean)
     */
    @Override
    @SuppressWarnings( "unused" )
    protected void implConfigureBlocking(
        final boolean block )
        throws IOException
    {
        // do nothing
    }

    /*
     * @see java.nio.channels.SocketChannel#isConnected()
     */
    @Override
    public boolean isConnected()
    {
        return false;
    }

    /*
     * @see java.nio.channels.SocketChannel#isConnectionPending()
     */
    @Override
    public boolean isConnectionPending()
    {
        return false;
    }

    /*
     * @see java.nio.channels.SocketChannel#read(java.nio.ByteBuffer)
     */
    @Override
    @SuppressWarnings( "unused" )
    public int read(
        final ByteBuffer dst )
        throws IOException
    {
        return 0;
    }

    /*
     * @see java.nio.channels.SocketChannel#read(java.nio.ByteBuffer[], int, int)
     */
    @Override
    @SuppressWarnings( "unused" )
    public long read(
        final ByteBuffer[] dsts,
        final int offset,
        final int length )
        throws IOException
    {
        return 0;
    }

    /*
     * @see java.nio.channels.SocketChannel#socket()
     */
    @Override
    public Socket socket()
    {
        return null;
    }

    /*
     * @see java.nio.channels.SocketChannel#write(java.nio.ByteBuffer)
     */
    @Override
    @SuppressWarnings( "unused" )
    public int write(
        final ByteBuffer src )
        throws IOException
    {
        return 0;
    }

    /*
     * @see java.nio.channels.SocketChannel#write(java.nio.ByteBuffer[], int, int)
     */
    @Override
    @SuppressWarnings( "unused" )
    public long write(
        final ByteBuffer[] srcs,
        final int offset,
        final int length )
        throws IOException
    {
        return 0;
    }
}
