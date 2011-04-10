/*
 * LocalService.java
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
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

/**
 * An adapter that allows interaction with a local table through the
 * {@link IService} interface in order to make local and remote players look
 * identical to the network table.
 */
@Immutable
final class LocalService
    implements IService
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalService} class.
     */
    LocalService()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#messageReceived(org.gamegineer.table.internal.net.transport.IServiceContext, org.gamegineer.table.internal.net.transport.MessageEnvelope)
     */
    @Override
    public void messageReceived(
        final IServiceContext context,
        final MessageEnvelope messageEnvelope )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( messageEnvelope, "messageEnvelope" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#peerStopped(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    public void peerStopped(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#started(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    public void started(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#stopped(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    public void stopped(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }
}
