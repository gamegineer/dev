/*
 * AbstractRemoteTableGatewayTest.java
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
 * Created on Apr 14, 2011 at 11:03:54 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public final class AbstractRemoteTableGatewayTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteTableGatewayTest}
     * class.
     */
    public AbstractRemoteTableGatewayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table gateway context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableGatewayContext_Null()
    {
        new AbstractRemoteTableGateway( null )
        {
            // no overrides
        };
    }
}
