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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.net.NetworkTableError;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.transport.ITransportLayer} for
 * active TCP connections.
 */
@Immutable
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
     */
    ActiveTransportLayer(
        /* @NonNull */
        final ITransportLayerContext context )
    {
        super( context, new Dispatcher() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#open(java.lang.String, int)
     */
    @Override
    public void open(
        final String hostName,
        final int port )
        throws NetworkTableException
    {
        assertArgumentNotNull( hostName, "hostName" ); //$NON-NLS-1$

        getDispatcher().open();

        final Connector connector = new Connector( this );
        try
        {
            connector.connect( hostName, port );
        }
        catch( final IOException e )
        {
            close();
            throw new NetworkTableException( NetworkTableError.UNSPECIFIED_ERROR, Messages.ActiveTransportLayer_open_ioError, e );
        }
        finally
        {
            connector.close();
        }
    }
}
