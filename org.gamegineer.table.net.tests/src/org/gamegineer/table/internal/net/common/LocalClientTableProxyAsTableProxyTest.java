/*
 * LocalClientTableProxyAsTableProxyTest.java
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
 * Created on Apr 16, 2011 at 11:13:25 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.gamegineer.table.internal.net.AbstractTableProxyTestCase;
import org.gamegineer.table.internal.net.ITableProxy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.LocalClientTableProxy} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.ITableProxy} interface.
 */
public final class LocalClientTableProxyAsTableProxyTest
    extends AbstractTableProxyTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * LocalClientTableProxyAsTableProxyTest} class.
     */
    public LocalClientTableProxyAsTableProxyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableProxyTestCase#createTableProxy()
     */
    @Override
    protected ITableProxy createTableProxy()
    {
        return new LocalClientTableProxy( "playerName" ); //$NON-NLS-1$
    }
}
