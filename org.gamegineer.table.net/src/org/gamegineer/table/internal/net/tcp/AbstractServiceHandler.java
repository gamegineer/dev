/*
 * AbstractServiceHandler.java
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
 * Created on Jan 6, 2011 at 10:59:45 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.nio.channels.SelectableChannel;
import net.jcip.annotations.Immutable;

/**
 * A service handler in the TCP network interface Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * A service handler implements one half of an end-to-end protocol in a
 * networked application.
 * </p>
 */
@Immutable
abstract class AbstractServiceHandler
    extends AbstractEventHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractServiceHandler} class.
     */
    AbstractServiceHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Opens the service handler.
     * 
     * @param handle
     *        The transport handle associated with the service handler; must not
     *        be {@code null}.
     */
    abstract void open(
        /* @NonNull */
        SelectableChannel handle );
}
