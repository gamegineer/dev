/*
 * ClientServiceHandler.java
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
 * Created on Jan 7, 2011 at 10:31:56 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import net.jcip.annotations.ThreadSafe;

/**
 * A service handler that represents the client half of a network table
 * connection.
 */
@ThreadSafe
final class ClientServiceHandler
    extends AbstractServiceHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientServiceHandler} class.
     * 
     * @param dispatcher
     *        The dispatcher associated with the service handler; must not be
     *        {@code null}.
     */
    ClientServiceHandler(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        super( dispatcher );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#operationReady()
     */
    @Override
    void operationReady()
    {
        // TODO: process events as needed
    }
}
