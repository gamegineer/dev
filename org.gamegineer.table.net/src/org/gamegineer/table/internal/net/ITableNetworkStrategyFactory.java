/*
 * ITableNetworkStrategyFactory.java
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
 * Created on May 6, 2011 at 11:38:45 PM.
 */

package org.gamegineer.table.internal.net;

/**
 * A factory for creating table network strategies.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableNetworkStrategyFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new client table network strategy.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @return The client table network strategy; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    /* @NonNull */
    public ITableNetworkStrategy createClientTableNetworkStrategy(
        /* @NonNull */
        ITableNetworkController tableNetworkController );

    /**
     * Creates a new server table network strategy.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @return The server table network strategy; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    /* @NonNull */
    public ITableNetworkStrategy createServerTableNetworkStrategy(
        /* @NonNull */
        ITableNetworkController tableNetworkController );
}
