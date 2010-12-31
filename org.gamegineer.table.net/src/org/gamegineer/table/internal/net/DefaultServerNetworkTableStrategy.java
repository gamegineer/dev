/*
 * DefaultServerNetworkTableStrategy.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Dec 27, 2010 at 8:10:00 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * The default server network table strategy.
 * 
 * <p>
 * This strategy represents a server table that communicates with all client
 * tables using TCP.
 * </p>
 */
@ThreadSafe
final class DefaultServerNetworkTableStrategy
    extends AbstractNetworkTableStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * DefaultServerNetworkTableStrategy} class.
     * 
     * @param networkTable
     *        The network table that hosts the strategy; must not be {@code
     *        null}.
     */
    DefaultServerNetworkTableStrategy(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        super( networkTable );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategy#connect(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    void connect(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assert configuration != null;

        // TODO
        try
        {
            Thread.sleep( 5000L );
        }
        catch( final InterruptedException e )
        {
            throw new NetworkTableException( e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategy#disconnect()
     */
    @Override
    void disconnect()
    {
        // TODO
    }
}
