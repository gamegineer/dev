/*
 * NetworkTableConfigurations.java
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
 * Created on Apr 14, 2011 at 11:42:55 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableConfigurationBuilder;
import org.gamegineer.table.net.NetworkTableConstants;

/**
 * A collection of useful methods for working with network table configurations.
 */
@ThreadSafe
public final class NetworkTableConfigurations
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableConfigurations}
     * class.
     */
    private NetworkTableConfigurations()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new default network table configuration.
     * 
     * @return A new default network table configuration; never {@code null}.
     */
    /* @NonNull */
    public static INetworkTableConfiguration createDefaultNetworkTableConfiguration()
    {
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        try
        {
            final NetworkTableConfigurationBuilder networkTableConfigurationBuilder = new NetworkTableConfigurationBuilder();
            return networkTableConfigurationBuilder //
                .setHostName( "hostName" ) //$NON-NLS-1$
                .setLocalPlayerName( "playerName" ) //$NON-NLS-1$
                .setPassword( password ) //
                .setPort( NetworkTableConstants.DEFAULT_PORT ) //
                .toNetworkTableConfiguration();
        }
        finally
        {
            password.dispose();
        }
    }
}
