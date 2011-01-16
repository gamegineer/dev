/*
 * IServiceHandler.java
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

package org.gamegineer.table.internal.net.connection;

/**
 * A service handler in the network table Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * A service handler implements one half of an end-to-end protocol in a
 * networked application.
 * </p>
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 */
public interface IServiceHandler<H, E>
    extends IEventHandler<H, E>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Opens the service handler.
     * 
     * @param handle
     *        The transport handle associated with the service handler; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code handle} is {@code null}.
     */
    public void open(
        /* @NonNull */
        H handle );
}
