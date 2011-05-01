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

import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.NetworkTableError;
import org.gamegineer.table.net.NetworkTableException;

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
     * @throws java.lang.NullPointerException
     *         If {@code tableGateway} is {@code null}.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If {@code tableGateway} is already associated with the execution
     *         context.
     */
    public void addTableGateway(
        /* @NonNull */
        ITableGateway tableGateway )
        throws NetworkTableException;

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
    /* @NonNull */
    public String getLocalPlayerName();

    /**
     * Gets the network password.
     * 
     * @return The network password; never {@code null}. The returned value is a
     *         copy and must be disposed when it is no longer needed.
     */
    /* @NonNull */
    public SecureString getPassword();

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
    public void removeTableGateway(
        /* @NonNull */
        ITableGateway tableGateway );
}
