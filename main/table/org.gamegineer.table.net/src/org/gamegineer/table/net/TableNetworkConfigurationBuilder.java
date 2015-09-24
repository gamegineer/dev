/*
 * TableNetworkConfigurationBuilder.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Nov 12, 2010 at 9:47:05 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;

/**
 * A factory for creating instances of {@link TableNetworkConfiguration}.
 */
@NotThreadSafe
public final class TableNetworkConfigurationBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the table network host. */
    private String hostName_;

    /** The name of the local player. */
    private String localPlayerName_;

    /** The local table to attach to the table network. */
    private final ITable localTable_;

    /** The password used to authenticate connections to the table network. */
    private SecureString password_;

    /** The port of the table network host. */
    private int port_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableNetworkConfigurationBuilder} class.
     * 
     * @param localTable
     *        The local table to attach to the table network.
     */
    public TableNetworkConfigurationBuilder(
        final ITable localTable )
    {
        hostName_ = "localhost"; //$NON-NLS-1$
        localPlayerName_ = "Player"; //$NON-NLS-1$
        localTable_ = localTable;
        password_ = new SecureString();
        port_ = TableNetworkConstants.DEFAULT_PORT;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the name of the table network host.
     * 
     * @param hostName
     *        The name of the table network host.
     * 
     * @return A reference to this builder.
     */
    public TableNetworkConfigurationBuilder setHostName(
        final String hostName )
    {
        hostName_ = hostName;

        return this;
    }

    /**
     * Sets the name of the local player.
     * 
     * @param localPlayerName
     *        The local player name.
     * 
     * @return A reference to this builder.
     */
    public TableNetworkConfigurationBuilder setLocalPlayerName(
        final String localPlayerName )
    {
        localPlayerName_ = localPlayerName;

        return this;
    }

    /**
     * Sets the password used to authenticate connections to the table network.
     * 
     * @param password
     *        The password.
     * 
     * @return A reference to this builder.
     */
    public TableNetworkConfigurationBuilder setPassword(
        final SecureString password )
    {
        password_ = password;

        return this;
    }

    /**
     * Sets the port of the table network host.
     * 
     * @param port
     *        The port of the table network host.
     * 
     * @return A reference to this builder.
     */
    public TableNetworkConfigurationBuilder setPort(
        final int port )
    {
        port_ = port;

        return this;
    }

    /**
     * Creates a new table network configuration based on the state of this
     * builder.
     * 
     * @return A new table network configuration.
     */
    public TableNetworkConfiguration toTableNetworkConfiguration()
    {
        return new TableNetworkConfiguration( hostName_, port_, password_, localPlayerName_, localTable_ );
    }
}
