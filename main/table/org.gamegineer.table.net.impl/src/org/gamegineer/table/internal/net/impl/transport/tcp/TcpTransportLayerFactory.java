/*
 * TcpTransportLayerFactory.java
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
 * Created on Jan 7, 2011 at 10:32:35 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.impl.transport.TransportException;

/**
 * Implementation of {@link ITransportLayerFactory} for TCP connections.
 */
@Immutable
public final class TcpTransportLayerFactory
    implements ITransportLayerFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TcpTransportLayerFactory}.
     */
    public TcpTransportLayerFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory#createActiveTransportLayer(org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext)
     */
    @Override
    public ITransportLayer createActiveTransportLayer(
        final ITransportLayerContext context )
        throws TransportException
    {
        return createTransportLayer( new ActiveTransportLayer.Factory(), context );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory#createPassiveTransportLayer(org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext)
     */
    @Override
    public ITransportLayer createPassiveTransportLayer(
        final ITransportLayerContext context )
        throws TransportException
    {
        return createTransportLayer( new PassiveTransportLayer.Factory(), context );
    }

    /**
     * Creates a new transport layer using the specified factory.
     * 
     * @param factory
     *        The transport layer factory; must not be {@code null}.
     * @param context
     *        The transport layer context; must not be {@code null}.
     * 
     * @return A new transport layer; never {@code null}.
     * 
     * @throws org.gamegineer.table.internal.net.impl.transport.TransportException
     *         If the transport layer cannot be created.
     */
    private static ITransportLayer createTransportLayer(
        final AbstractTransportLayer.AbstractFactory factory,
        final ITransportLayerContext context )
        throws TransportException
    {
        return new TransportLayerProxy( factory.createTransportLayer( context ) );
    }
}
