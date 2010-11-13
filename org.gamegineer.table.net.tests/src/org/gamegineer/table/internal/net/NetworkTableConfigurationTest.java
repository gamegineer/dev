/*
 * NetworkTableConfigurationTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 12, 2010 at 10:17:28 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.common.core.security.SecureString;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.NetworkTableConfiguration} class.
 */
public final class NetworkTableConfigurationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableConfigurationTest}
     * class.
     */
    public NetworkTableConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * host name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_HostName_Null()
    {
        new NetworkTableConfiguration( null, 0, new SecureString(), "localPlayerName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * local player name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_LocalPlayerName_Null()
    {
        new NetworkTableConfiguration( "hostName", 0, new SecureString(), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * password.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Password_Null()
    {
        new NetworkTableConfiguration( "hostName", 0, null, "localPlayerName" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
