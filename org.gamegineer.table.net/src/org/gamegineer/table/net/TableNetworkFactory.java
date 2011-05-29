/*
 * TableNetworkFactory.java
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
 * Created on Nov 4, 2010 at 11:44:35 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.TableNetwork;

/**
 * A factory for creating table networks.
 */
@ThreadSafe
public final class TableNetworkFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkFactory} class.
     */
    private TableNetworkFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table network.
     * 
     * @return A new table network; never {@code null}.
     */
    /* @NonNull */
    public static ITableNetwork createTableNetwork()
    {
        return new TableNetwork();
    }
}
