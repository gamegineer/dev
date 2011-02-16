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

package org.gamegineer.table.internal.net.tcp;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.INetworkInterface;
import org.gamegineer.table.internal.net.INetworkInterfaceListener;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.table.internal.net.INetworkInterface} that operate over
 * a TCP connection.
 */
@Immutable
abstract class AbstractNetworkInterface
    implements INetworkInterface
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher associated with the network interface. */
    private final Dispatcher dispatcher_;

    /** The network interface listener. */
    private final INetworkInterfaceListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkInterface} class.
     * 
     * @param listener
     *        The network interface listener; must not be {@code null}.
     * @param dispatcher
     *        The dispatcher associated with the network interface; must not be
     *        {@code null}.
     */
    AbstractNetworkInterface(
        /* @NonNull */
        final INetworkInterfaceListener listener,
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        assert listener != null;
        assert dispatcher != null;

        dispatcher_ = dispatcher;
        listener_ = listener;
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

    /**
     * Gets the network interface listener.
     * 
     * @return The network interface listener; never {@code null}.
     */
    /* @NonNull */
    final INetworkInterfaceListener getListener()
    {
        return listener_;
    }
}
