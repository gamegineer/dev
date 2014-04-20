/*
 * ITransportLayerFactory.java
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
 * Created on Jan 6, 2011 at 11:00:18 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

/**
 * A factory for creating a network transport layer.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITransportLayerFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new active transport layer.
     * 
     * @param context
     *        The transport layer context; must not be {@code null}.
     * 
     * @return A new active transport layer; never {@code null}.
     * 
     * @throws org.gamegineer.table.internal.net.impl.transport.TransportException
     *         If the transport layer cannot be created.
     */
    public ITransportLayer createActiveTransportLayer(
        ITransportLayerContext context )
        throws TransportException;

    /**
     * Creates a new passive transport layer.
     * 
     * @param context
     *        The transport layer context; must not be {@code null}.
     * 
     * @return A new passive transport layer; never {@code null}.
     * 
     * @throws org.gamegineer.table.internal.net.impl.transport.TransportException
     *         If the transport layer cannot be created.
     */
    public ITransportLayer createPassiveTransportLayer(
        ITransportLayerContext context )
        throws TransportException;
}
