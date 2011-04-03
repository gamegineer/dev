/*
 * NetworkTableConfigurationTest.java
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
 * Created on Nov 12, 2010 at 10:17:28 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertArrayEquals;
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
     * Ensures the constructor makes a copy of the password.
     */
    @Test
    public void testConstructor_Password_Copy()
    {
        final char[] expectedPassword = "password".toCharArray(); //$NON-NLS-1$
        final SecureString password = new SecureString( expectedPassword );
        final NetworkTableConfiguration configuration = new NetworkTableConfiguration( "hostName", 0, password, "localPlayerName" ); //$NON-NLS-1$ //$NON-NLS-2$
        password.dispose();

        final char[] actualPassword = configuration.getPassword().toCharArray();

        assertArrayEquals( expectedPassword, actualPassword );
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

    /**
     * Ensures the {@code getPassword} method returns a copy of the password.
     */
    @Test
    public void testGetPassword_ReturnValue_Copy()
    {
        final char[] expectedPassword = "password".toCharArray(); //$NON-NLS-1$
        final NetworkTableConfiguration configuration = new NetworkTableConfiguration( "hostName", 0, new SecureString( expectedPassword ), "localPlayerName" ); //$NON-NLS-1$ //$NON-NLS-2$
        final SecureString password = configuration.getPassword();
        password.dispose();

        final char[] actualPassword = configuration.getPassword().toCharArray();

        assertArrayEquals( expectedPassword, actualPassword );
    }
}
