/*
 * IDispatcher.java
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
 * Created on Jan 6, 2011 at 10:59:26 PM.
 */

package org.gamegineer.table.internal.net.connection;

/**
 * An event dispatcher in the network table Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * An event dispatcher is responsible for managing event handlers and
 * appropriately dispatching events that occur on the event handler transport
 * handles.
 * </p>
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 */
public interface IDispatcher<H, E>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the dispatcher.
     */
    public void close();

    /**
     * Opens the dispatcher.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher has already been opened or is closed.
     */
    public void open();

    /**
     * Registers the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is closed.
     * @throws java.lang.NullPointerException
     *         If {@code eventHandler} is {@code null}.
     */
    public void registerEventHandler(
        /* @NonNull */
        IEventHandler<H, E> eventHandler );

    /**
     * Unregisters the specified event handler.
     * 
     * @param eventHandler
     *        The event handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the dispatcher is closed.
     * @throws java.lang.NullPointerException
     *         If {@code eventHandler} is {@code null}.
     */
    public void unregisterEventHandler(
        /* @NonNull */
        IEventHandler<H, E> eventHandler );
}
