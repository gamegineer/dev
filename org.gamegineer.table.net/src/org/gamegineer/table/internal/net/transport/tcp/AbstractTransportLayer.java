/*
 * AbstractTransportLayer.java
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
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.table.internal.net.transport.ITransportLayer} that
 * operate over a TCP connection.
 */
@Immutable
abstract class AbstractTransportLayer
    implements ITransportLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer context. */
    private final ITransportLayerContext context_;

    /** The dispatcher associated with the transport layer. */
    private final Dispatcher dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTransportLayer} class.
     * 
     * @param context
     *        The transport layer context; must not be {@code null}.
     * @param dispatcher
     *        The dispatcher associated with the transport layer; must not be
     *        {@code null}.
     */
    AbstractTransportLayer(
        /* @NonNull */
        final ITransportLayerContext context,
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
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#close()
     */
    @Override
    public void close()
    {
        dispatcher_.close();
    }

    /**
     * Invoked when the transport layer has been disconnected.
     * 
     * @param exception
     *        The exception that caused the transport layer to be disconnected
     *        or {@code null} if the transport layer was disconnected normally.
     */
    final void disconnected(
        /* @Nullable */
        final Exception exception )
    {
        context_.transportLayerDisconnected( exception );
    }

    /**
     * Gets the transport layer context.
     * 
     * @return The transport layer context; never {@code null}.
     */
    /* @NonNull */
    final ITransportLayerContext getContext()
    {
        return context_;
    }

    /**
     * Gets the dispatcher associated with the transport layer.
     * 
     * @return The dispatcher associated with the transport layer; never {@code
     *         null}.
     */
    /* @NonNull */
    final Dispatcher getDispatcher()
    {
        return dispatcher_;
    }
}
