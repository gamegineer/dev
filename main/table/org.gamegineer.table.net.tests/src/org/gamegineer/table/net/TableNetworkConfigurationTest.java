/*
 * TableNetworkConfigurationTest.java
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
 * Created on Nov 12, 2010 at 10:17:28 PM.
 */

package org.gamegineer.table.net;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertArrayEquals;
import org.easymock.EasyMock;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableNetworkConfiguration} class.
 */
public final class TableNetworkConfigurationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkConfigurationTest}
     * class.
     */
    public TableNetworkConfigurationTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link TableNetworkConfiguration#TableNetworkConfiguration}
     * constructor makes a copy of the password.
     */
    @Test
    public void testConstructor_Password_Copy()
    {
        final char[] expectedPassword = nonNull( "password".toCharArray() ); //$NON-NLS-1$
        final SecureString password = new SecureString( expectedPassword );
        final TableNetworkConfiguration configuration = new TableNetworkConfiguration( "hostName", 0, password, "localPlayerName", EasyMock.createMock( ITable.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
        password.dispose();

        final char[] actualPassword = configuration.getPassword().toCharArray();

        assertArrayEquals( expectedPassword, actualPassword );
    }

    /**
     * Ensures the {@link TableNetworkConfiguration#getPassword} method returns
     * a copy of the password.
     */
    @Test
    public void testGetPassword_ReturnValue_Copy()
    {
        final char[] expectedPassword = nonNull( "password".toCharArray() ); //$NON-NLS-1$
        final TableNetworkConfiguration configuration = new TableNetworkConfiguration( "hostName", 0, new SecureString( expectedPassword ), "localPlayerName", EasyMock.createMock( ITable.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
        final SecureString password = configuration.getPassword();
        password.dispose();

        final char[] actualPassword = configuration.getPassword().toCharArray();

        assertArrayEquals( expectedPassword, actualPassword );
    }
}
