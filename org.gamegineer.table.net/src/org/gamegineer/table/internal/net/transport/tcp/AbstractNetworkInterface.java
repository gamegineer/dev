/*
 * AbstractNetworkInterface.java
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
 * Created on Feb 12, 2011 at 10:54:54 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.transport.INetworkInterface;
import org.gamegineer.table.internal.net.transport.INetworkInterfaceContext;
import org.gamegineer.table.internal.net.transport.INetworkServiceHandler;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.table.internal.net.transport.INetworkInterface} that
 * operate over a TCP connection.
 */
@Immutable
abstract class AbstractNetworkInterface
    implements INetworkInterface
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network interface context. */
    private final INetworkInterfaceContext context_;

    /** The dispatcher associated with the network interface. */
    private final Dispatcher dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkInterface} class.
     * 
     * @param context
     *        The network interface context; must not be {@code null}.
     * @param dispatcher
     *        The dispatcher associated with the network interface; must not be
     *        {@code null}.
     */
    AbstractNetworkInterface(
        /* @NonNull */
        final INetworkInterfaceContext context,
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        assert context != null;
        assert dispatcher != null;

        context_ = context;
        dispatcher_ = dispatcher;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterface#close()
     */
    @Override
    public void close()
    {
        dispatcher_.close();
    }

    /**
     * Creates a new network service handler.
     * 
     * @return A new network service handler; never {@code null}.
     */
    /* @NonNull */
    abstract INetworkServiceHandler createNetworkServiceHandler();

    /**
     * Invoked when the network interface has been disconnected.
     */
    final void disconnected()
    {
        context_.networkInterfaceDisconnected();
    }

    /**
     * Gets the network interface context.
     * 
     * @return The network interface context; never {@code null}.
     */
    /* @NonNull */
    final INetworkInterfaceContext getContext()
    {
        return context_;
    }

    /**
     * Gets the dispatcher associated with the network interface.
     * 
     * @return The dispatcher associated with the network interface; never
     *         {@code null}.
     */
    /* @NonNull */
    final Dispatcher getDispatcher()
    {
        return dispatcher_;
    }
}
