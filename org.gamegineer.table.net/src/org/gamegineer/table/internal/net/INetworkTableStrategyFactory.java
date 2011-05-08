/*
 * INetworkTableStrategyFactory.java
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
 * A factory for creating network table strategies.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INetworkTableStrategyFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new client network table strategy.
     * 
     * @param context
     *        The network table strategy context; must not be {@code null}.
     * 
     * @return The client network table strategy; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    public INetworkTableStrategy createClientNetworkTableStrategy(
        /* @NonNull */
        INetworkTableStrategyContext context );

    /**
     * Creates a new server network table strategy.
     * 
     * @param context
     *        The network table strategy context; must not be {@code null}.
     * 
     * @return The server network table strategy; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    public INetworkTableStrategy createServerNetworkTableStrategy(
        /* @NonNull */
        INetworkTableStrategyContext context );
}
