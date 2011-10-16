/*
 * PassiveTransportLayer.java
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
 * Created on Jan 18, 2011 at 8:31:02 PM.
 */

package org.gamegineer.table.internal.net.transport.fake;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Future;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.table.internal.net.transport.ITransportLayer;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.transport.ITransportLayer} for a
 * passive connection.
 */
@Immutable
final class PassiveTransportLayer
    implements ITransportLayer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PassiveTransportLayer} class.
     */
    PassiveTransportLayer()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#beginClose()
     */
    @Override
    public Future<Void> beginClose()
    {
        return new SynchronousFuture<Void>();
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#beginOpen(java.lang.String, int)
     */
    @Override
    public Future<Void> beginOpen(
        final String hostName,
        @SuppressWarnings( "unused" )
        final int port )
    {
        assertArgumentNotNull( hostName, "hostName" ); //$NON-NLS-1$

        return new SynchronousFuture<Void>();
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#close()
     */
    @Override
    public void close()
    {
        endClose( beginClose() );
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#endClose(java.util.concurrent.Future)
     */
    @Override
    public void endClose(
        final Future<Void> future )
    {
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#endOpen(java.util.concurrent.Future)
     */
    @Override
    public void endOpen(
        final Future<Void> future )
    {
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.ITransportLayer#open(java.lang.String, int)
     */
    @Override
    public void open(
        final String hostName,
        final int port )
    {
        endOpen( beginOpen( hostName, port ) );
    }
}
