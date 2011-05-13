/*
 * ITableGatewayContext.java
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
 * Created on Apr 8, 2011 at 9:21:36 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.GuardedBy;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.NetworkTableError;

/**
 * The execution context for a table gateway.
 * 
 * <p>
 * Provides operations that allow a table gateway to interact with the network
 * table.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableGatewayContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified table gateway to the execution context.
     * 
     * @param tableGateway
     *        The table gateway; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code tableGateway} is already a registered table gateway.
     * @throws java.lang.NullPointerException
     *         If {@code tableGateway} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public void addTableGateway(
        /* @NonNull */
        ITableGateway tableGateway );

    /**
     * Disconnects the network table for the specified cause.
     * 
     * @param error
     *        The error that caused the network table to be disconnected from
     *        the network or {@code null} if the network table was disconnected
     *        normally.
     */
    public void disconnectNetworkTable(
        /* @Nullable */
        NetworkTableError error );

    /**
     * Gets the local player name.
     * 
     * @return The local player name; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    public String getLocalPlayerName();

    /**
     * Gets the instance lock for the execution context.
     * 
     * @return The instance lock for the execution context; never {@code null}.
     */
    /* @NonNull */
    public Object getLock();

    /**
     * Gets the network password.
     * 
     * @return The network password; never {@code null}. The returned value is a
     *         copy and must be disposed when it is no longer needed.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    public SecureString getPassword();

    /**
     * Indicates a table gateway has been registered for the specified player
     * name.
     * 
     * @param playerName
     *        The name of the player associated with the table gateway; must not
     *        be {@code null}.
     * 
     * @return {@code true} if a table gateway has been registered for the
     *         specified player name; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public boolean isTableGatewayPresent(
        /* @NonNull */
        String playerName );

    /**
     * Removes the specified table gateway from the execution context.
     * 
     * @param tableGateway
     *        The table gateway; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code tableGateway} is not a registered table gateway.
     * @throws java.lang.NullPointerException
     *         If {@code tableGateway} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public void removeTableGateway(
        /* @NonNull */
        ITableGateway tableGateway );
}
