/*
 * PassiveTransportLayer.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jan 18, 2011 at 8:36:24 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;

/**
 * Implementation of {@link AbstractTransportLayer} for passive connections.
 */
@NotThreadSafe
final class PassiveTransportLayer
    extends AbstractTransportLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The acceptor that receives passive connections. */
    private @Nullable Acceptor acceptor_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PassiveTransportLayer} class.
     * 
     * @param executorService
     *        The transport layer executor service.
     * @param context
     *        The transport layer context.
     */
    private PassiveTransportLayer(
        final ExecutorService executorService,
        final ITransportLayerContext context )
    {
        super( executorService, context );

        acceptor_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractTransportLayer#close()
     */
    @Override
    void close()
    {
        assert isTransportLayerThread();

        if( acceptor_ != null )
        {
            acceptor_.close();
            acceptor_ = null;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractTransportLayer#open(java.lang.String, int)
     */
    @Override
    void open(
        final String hostName,
        final int port )
        throws IOException
    {
        assert isTransportLayerThread();

        acceptor_ = new Acceptor( this );
        acceptor_.bind( hostName, port );
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
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractTransportLayer.AbstractFactory#createTransportLayer(java.util.concurrent.ExecutorService, org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        AbstractTransportLayer createTransportLayer(
            final ExecutorService executorService,
            final ITransportLayerContext context )
        {
            return new PassiveTransportLayer( executorService, context );
        }
    }
}
