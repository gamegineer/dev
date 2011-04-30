/*
 * FakeTransportLayer.java
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
 * Created on Feb 12, 2011 at 11:15:36 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.TransportException;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.transport.tcp.AbstractTransportLayer}
 * .
 */
@Immutable
final class FakeTransportLayer
    extends AbstractTransportLayer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeTransportLayer} class using
     * a new dispatcher.
     */
    FakeTransportLayer()
    {
        this( new Dispatcher() );
    }

    /**
     * Initializes a new instance of the {@code FakeTransportLayer} class using
     * the specified dispatcher.
     * 
     * @param dispatcher
     *        The dispatcher associated with the transport layer; must not be
     *        {@code null}.
     */
    FakeTransportLayer(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        super( EasyMock.createMock( ITransportLayerContext.class ), dispatcher );
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
        @SuppressWarnings( "unused" )
        final int port )
        throws TransportException
    {
        assertArgumentNotNull( hostName, "hostName" ); //$NON-NLS-1$

        getDispatcher().open();
    }
}
