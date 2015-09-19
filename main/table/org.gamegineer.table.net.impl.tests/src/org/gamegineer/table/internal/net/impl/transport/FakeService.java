/*
 * FakeService.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Mar 31, 2011 at 10:42:08 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Fake implementation of {@link IService}.
 */
@NotThreadSafe
public class FakeService
    implements IService
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeService} class.
     */
    public FakeService()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#messageReceived(org.gamegineer.table.internal.net.impl.transport.MessageEnvelope)
     */
    @Override
    public void messageReceived(
        @SuppressWarnings( "unused" )
        final MessageEnvelope messageEnvelope )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#peerStopped()
     */
    @Override
    public void peerStopped()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#started(org.gamegineer.table.internal.net.impl.transport.IServiceContext)
     */
    @Override
    public void started(
        @SuppressWarnings( "unused" )
        final IServiceContext context )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#stopped(java.lang.Exception)
     */
    @Override
    public void stopped(
        @SuppressWarnings( "unused" )
        final @Nullable Exception exception )
    {
        // do nothing
    }
}
