/*
 * TableNetworkConfiguration.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Nov 12, 2010 at 9:47:16 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.net.ITableNetworkConfiguration;

/**
 * Implementation of {@link ITableNetworkConfiguration}.
 */
@Immutable
public final class TableNetworkConfiguration
    implements ITableNetworkConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the table network host. */
    private final String hostName_;

    /** The name of the local player. */
    private final String localPlayerName_;

    /** The local table to attach to the table network. */
    private final ITable localTable_;

    /** The password used to authenticate connections to the table network. */
    private final SecureString password_;

    /** The port of the table network host. */
    private final int port_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkConfiguration}
     * class.
     * 
     * @param hostName
     *        The name of the table network host; must not be {@code null}.
     * @param port
     *        The port of the table network host.
     * @param password
     *        The password used to authenticate connections to the table
     *        network; must not be {@code null}.
     * @param localPlayerName
     *        The name of the local player; must not be {@code null}.
     * @param localTable
     *        The local table to attach to the table network; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code hostName}, {@code password}, {@code localPlayerName},
     *         or {@code localTable} is {@code null},
     */
    public TableNetworkConfiguration(
        /* @NonNull */
        final String hostName,
        final int port,
        /* @NonNull */
        final SecureString password,
        /* @NonNull */
        final String localPlayerName,
        /* @NonNull */
        final ITable localTable )
    {
        assertArgumentNotNull( hostName, "hostName" ); //$NON-NLS-1$
        assertArgumentNotNull( password, "password" ); //$NON-NLS-1$
        assertArgumentNotNull( localPlayerName, "localPlayerName" ); //$NON-NLS-1$
        assertArgumentNotNull( localTable, "localTable" ); //$NON-NLS-1$

        hostName_ = hostName;
        localPlayerName_ = localPlayerName;
        localTable_ = localTable;
        password_ = new SecureString( password );
        port_ = port;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.ITableNetworkConfiguration#getHostName()
     */
    @Override
    public String getHostName()
    {
        return hostName_;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkConfiguration#getLocalPlayerName()
     */
    @Override
    public String getLocalPlayerName()
    {
        return localPlayerName_;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkConfiguration#getLocalTable()
     */
    @Override
    public ITable getLocalTable()
    {
        return localTable_;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkConfiguration#getPassword()
     */
    @Override
    public SecureString getPassword()
    {
        return new SecureString( password_ );
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkConfiguration#getPort()
     */
    @Override
    public int getPort()
    {
        return port_;
    }
}
