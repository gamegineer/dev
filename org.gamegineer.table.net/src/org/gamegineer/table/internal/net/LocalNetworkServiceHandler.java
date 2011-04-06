/*
 * LocalNetworkServiceHandler.java
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
 * Created on Apr 5, 2011 at 8:19:02 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;

/**
 * An adapter that allows interaction with a local table through the
 * {@link INetworkServiceHandler} interface in order to make local and remote
 * players look identical to the network table.
 */
@Immutable
final class LocalNetworkServiceHandler
    implements INetworkServiceHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalNetworkServiceHandler}
     * class.
     */
    LocalNetworkServiceHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#messageReceived(org.gamegineer.table.internal.net.INetworkServiceContext, org.gamegineer.table.internal.net.MessageEnvelope)
     */
    @Override
    public void messageReceived(
        final INetworkServiceContext context,
        final MessageEnvelope messageEnvelope )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( messageEnvelope, "messageEnvelope" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#peerStopped(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    public void peerStopped(
        final INetworkServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#started(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    public void started(
        final INetworkServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#stopped(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    public void stopped(
        final INetworkServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }
}
