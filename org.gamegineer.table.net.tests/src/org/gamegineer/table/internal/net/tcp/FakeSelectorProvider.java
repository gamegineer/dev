/*
 * FakeSelectorProvider.java
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
 * Created on Feb 7, 2011 at 8:33:32 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import net.jcip.annotations.NotThreadSafe;

/**
 * Fake implementation of {@link java.nio.channels.spi.SelectorProvider}.
 */
@NotThreadSafe
class FakeSelectorProvider
    extends SelectorProvider
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeSelectorProvider} class.
     */
    FakeSelectorProvider()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.nio.channels.spi.SelectorProvider#openDatagramChannel()
     */
    @Override
    @SuppressWarnings( "unused" )
    public DatagramChannel openDatagramChannel()
        throws IOException
    {
        return null;
    }

    /*
     * @see java.nio.channels.spi.SelectorProvider#openPipe()
     */
    @Override
    @SuppressWarnings( "unused" )
    public Pipe openPipe()
        throws IOException
    {
        return null;
    }

    /*
     * @see java.nio.channels.spi.SelectorProvider#openSelector()
     */
    @Override
    @SuppressWarnings( "unused" )
    public AbstractSelector openSelector()
        throws IOException
    {
        return null;
    }

    /*
     * @see java.nio.channels.spi.SelectorProvider#openServerSocketChannel()
     */
    @Override
    @SuppressWarnings( "unused" )
    public ServerSocketChannel openServerSocketChannel()
        throws IOException
    {
        return null;
    }

    /*
     * @see java.nio.channels.spi.SelectorProvider#openSocketChannel()
     */
    @Override
    @SuppressWarnings( "unused" )
    public SocketChannel openSocketChannel()
        throws IOException
    {
        return null;
    }
}