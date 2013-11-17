/*
 * TableNetworkConfigurationBuilder.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
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
     *        The local table to attach to the table network; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code localTable} is {@code null}.
     */
    public TableNetworkConfigurationBuilder(
        /* @NonNull */
        final ITable localTable )
    {
        assertArgumentNotNull( localTable, "localTable" ); //$NON-NLS-1$

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
     *        The name of the table network host; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code hostName} is {@code null}.
     */
    /* @NonNull */
    public TableNetworkConfigurationBuilder setHostName(
        /* @NonNull */
        final String hostName )
    {
        assertArgumentNotNull( hostName, "hostName" ); //$NON-NLS-1$

        hostName_ = hostName;

        return this;
    }

    /**
     * Sets the name of the local player.
     * 
     * @param localPlayerName
     *        The local player name; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code localPlayerName} is {@code null}.
     */
    /* @NonNull */
    public TableNetworkConfigurationBuilder setLocalPlayerName(
        /* @NonNull */
        final String localPlayerName )
    {
        assertArgumentNotNull( localPlayerName, "localPlayerName" ); //$NON-NLS-1$

        localPlayerName_ = localPlayerName;

        return this;
    }

    /**
     * Sets the password used to authenticate connections to the table network.
     * 
     * @param password
     *        The password; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code password} is {@code null}.
     */
    /* @NonNull */
    public TableNetworkConfigurationBuilder setPassword(
        /* @NonNull */
        final SecureString password )
    {
        assertArgumentNotNull( password, "password" ); //$NON-NLS-1$

        password_ = password;

        return this;
    }

    /**
     * Sets the port of the table network host.
     * 
     * @param port
     *        The port of the table network host.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
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
     * @return A new table network configuration; never {@code null}.
     */
    /* @NonNull */
    public TableNetworkConfiguration toTableNetworkConfiguration()
    {
        return new TableNetworkConfiguration( hostName_, port_, password_, localPlayerName_, localTable_ );
    }
}
