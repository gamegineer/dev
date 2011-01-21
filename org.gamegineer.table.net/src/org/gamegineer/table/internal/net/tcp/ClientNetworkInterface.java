/*
 * ClientNetworkInterface.java
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

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.INetworkInterface;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of {@link org.gamegineer.table.internal.net.INetworkInterface}
 * for the client role over a TCP connection.
 */
@Immutable
final class ClientNetworkInterface
    implements INetworkInterface
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher associated with the network interface. */
    private final Dispatcher dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNetworkInterface} class.
     */
    ClientNetworkInterface()
    {
        dispatcher_ = new Dispatcher();
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

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterface#open(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void open(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        dispatcher_.open();

        final Connector connector = new Connector( dispatcher_ );
        try
        {
            connector.connect( configuration );
        }
        catch( final NetworkTableException e )
        {
            close();
            throw e;
        }
        finally
        {
            connector.close();
        }
    }
}
