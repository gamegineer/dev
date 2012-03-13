/*
 * TableNetworkConfigurations.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Apr 14, 2011 at 11:42:55 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkConfigurationBuilder;
import org.gamegineer.table.net.TableNetworkConstants;

/**
 * A collection of useful methods for working with table network configurations.
 */
@ThreadSafe
public final class TableNetworkConfigurations
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkConfigurations}
     * class.
     */
    private TableNetworkConfigurations()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new default table network configuration.
     * 
     * @return A new default table network configuration; never {@code null}.
     */
    /* @NonNull */
    public static ITableNetworkConfiguration createDefaultTableNetworkConfiguration()
    {
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        try
        {
            final TableNetworkConfigurationBuilder builder = new TableNetworkConfigurationBuilder();
            return builder //
                .setHostName( "hostName" ) //$NON-NLS-1$
                .setLocalPlayerName( "playerName" ) //$NON-NLS-1$
                .setLocalTable( TableFactory.createTable() ) //
                .setPassword( password ) //
                .setPort( TableNetworkConstants.DEFAULT_PORT ) //
                .toTableNetworkConfiguration();
        }
        finally
        {
            password.dispose();
        }
    }
}
