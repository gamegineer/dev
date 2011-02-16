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
import java.io.IOException;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.INetworkInterfaceListener;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of {@link org.gamegineer.table.internal.net.INetworkInterface}
 * for the client role over a TCP connection.
 */
@Immutable
final class ClientNetworkInterface
    extends AbstractNetworkInterface
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNetworkInterface} class.
     * 
     * @param listener
     *        The network interface listener; must not be {@code null}.
     */
    ClientNetworkInterface(
        /* @NonNull */
        final INetworkInterfaceListener listener )
    {
        super( listener, new Dispatcher() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterface#open(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void open(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        getDispatcher().open();

        final Connector connector = new Connector( this );
        try
        {
            connector.connect( configuration );
        }
        catch( final IOException e )
        {
            close();
            throw new NetworkTableException( Messages.ClientNetworkInterface_open_ioError, e );
        }
        finally
        {
            connector.close();
        }
    }
}
