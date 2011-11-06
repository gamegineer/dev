/*
 * ActiveTransportLayer.java
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
 * Created on Jan 16, 2011 at 5:14:58 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.transport.tcp.AbstractTransportLayer}
 * for active connections.
 */
@NotThreadSafe
final class ActiveTransportLayer
    extends AbstractTransportLayer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActiveTransportLayer} class.
     * 
     * @param context
     *        The transport layer context; must not be {@code null}.
     * @param executorService
     *        The transport layer executor service; must not be {@code null}.
     */
    private ActiveTransportLayer(
        /* @NonNull */
        final ITransportLayerContext context,
        /* @NonNull */
        final ExecutorService executorService )
    {
        super( context, executorService );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractTransportLayer#close()
     */
    @Override
    void close()
    {
        assert isTransportLayerThread();

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractTransportLayer#open(java.lang.String, int)
     */
    @Override
    void open(
        final String hostName,
        final int port )
        throws IOException
    {
        assert hostName != null;
        assert isTransportLayerThread();

        final Connector connector = new Connector( this );
        try
        {
            connector.connect( hostName, port );
        }
        finally
        {
            connector.close();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A factory for creating instances of {@link PassiveTransportLayer}.
     */
    @Immutable
    static final class Factory
        extends AbstractFactory
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Factory} class.
         */
        Factory()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.transport.tcp.AbstractTransportLayer.AbstractFactory#createTransportLayer(org.gamegineer.table.internal.net.transport.ITransportLayerContext, java.util.concurrent.ExecutorService)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        AbstractTransportLayer createTransportLayer(
            final ITransportLayerContext context,
            final ExecutorService executorService )
        {
            return new ActiveTransportLayer( context, executorService );
        }
    }
}
