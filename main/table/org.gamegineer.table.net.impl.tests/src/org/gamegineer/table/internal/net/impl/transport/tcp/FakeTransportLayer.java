/*
 * FakeTransportLayer.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Feb 12, 2011 at 11:15:36 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.util.concurrent.ExecutorService;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;

/**
 * Fake implementation of {@link AbstractTransportLayer}.
 */
@Immutable
final class FakeTransportLayer
    extends AbstractTransportLayer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeTransportLayer} class.
     * 
     * @param executorService
     *        The transport layer executor service; must not be {@code null}.
     * @param context
     *        The transport layer context; must not be {@code null}.
     */
    private FakeTransportLayer(
        final ExecutorService executorService,
        final ITransportLayerContext context )
    {
        super( executorService, context );
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

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractTransportLayer#open(java.lang.String, int)
     */
    @Override
    void open(
        @SuppressWarnings( "unused" )
        final String hostName,
        @SuppressWarnings( "unused" )
        final int port )
    {
        assert isTransportLayerThread();

        // do nothing
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A factory for creating instances of {@link FakeTransportLayer}.
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
         * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractTransportLayer.AbstractFactory#createTransportLayer(java.util.concurrent.ExecutorService, org.gamegineer.table.internal.net.transport.ITransportLayerContext)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        AbstractTransportLayer createTransportLayer(
            final ExecutorService executorService,
            final ITransportLayerContext context )
        {
            return new FakeTransportLayer( executorService, context );
        }
    }
}
