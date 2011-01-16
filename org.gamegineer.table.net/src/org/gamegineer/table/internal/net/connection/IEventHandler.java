/*
 * IEventHandler.java
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
 * Created on Jan 7, 2011 at 9:59:16 PM.
 */

package org.gamegineer.table.internal.net.connection;

/**
 * A handler of events dispatched in the network table Acceptor-Connector
 * pattern implementation.
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 */
public interface IEventHandler<H, E>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the event handler.
     */
    public void close();

    /**
     * Gets the event mask associated with the event handler.
     * 
     * @return The event mask associated with the event handler.
     */
    public int getEvents();

    /**
     * Gets the transport handle associated with the event handler.
     * 
     * @return The transport handle associated with the event handler or {@code
     *         null} if the transport handle is not available.
     */
    /* @Nullable */
    public H getTransportHandle();

    /**
     * Invoked by the dispatcher to handle the specified event.
     * 
     * @param event
     *        The event; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void handleEvent(
        /* @NonNull */
        E event );
}
