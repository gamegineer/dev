/*
 * INetworkInterfaceFactory.java
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
 * Created on Jan 6, 2011 at 11:00:18 PM.
 */

package org.gamegineer.table.internal.net;

/**
 * A factory for creating a network table network interface for a specific role
 * and transport implementation.
 */
public interface INetworkInterfaceFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new client network interface for the specified listener.
     * 
     * @param listener
     *        The network interface listener; must not be {@code null}.
     * 
     * @return A new client network interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    /* @NonNull */
    public INetworkInterface createClientNetworkInterface(
        /* @NonNull */
        INetworkInterfaceListener listener );

    /**
     * Creates a new server network interface for the specified listener.
     * 
     * @param listener
     *        The network interface listener; must not be {@code null}.
     * 
     * @return A new server network interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    /* @NonNull */
    public INetworkInterface createServerNetworkInterface(
        /* @NonNull */
        INetworkInterfaceListener listener );
}
