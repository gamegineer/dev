/*
 * AbstractAbstractRemoteTableGatewayTestCase.java
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
 * Created on Apr 14, 2011 at 11:58:10 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.gamegineer.table.internal.net.AbstractTableGatewayTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public abstract class AbstractAbstractRemoteTableGatewayTestCase
    extends AbstractTableGatewayTestCase<AbstractRemoteTableGateway>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractRemoteTableGatewayTestCase} class.
     */
    protected AbstractAbstractRemoteTableGatewayTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code messageReceivedInternal} method throws an exception
     * when passed a {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceivedInternal_Message_Null()
    {
        getTableGateway().messageReceivedInternal( null );
    }
}
