/*
 * ClientNetworkTableStrategy.java
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
 * Created on Apr 9, 2011 at 10:45:05 PM.
 */

package org.gamegineer.table.internal.net.client;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.INetworkTableStrategyContext;
import org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategy} for client
 * network tables.
 */
@ThreadSafe
public final class ClientNetworkTableStrategy
    extends AbstractNetworkTableStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNetworkTableStrategy}
     * class.
     * 
     * @param context
     *        The network table strategy context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public ClientNetworkTableStrategy(
        /* @NonNull */
        final INetworkTableStrategyContext context )
    {
        super( context );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
    {
        assert Thread.holdsLock( getLock() );

        return getContext().getTransportLayerFactory().createActiveTransportLayer( new AbstractTransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new RemoteServerTableGateway( ClientNetworkTableStrategy.this );
            }
        } );
    }
}
