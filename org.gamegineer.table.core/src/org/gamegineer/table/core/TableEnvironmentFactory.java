/*
 * TableEnvironmentFactory.java
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
 * Created on Oct 6, 2009 at 11:05:05 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Table;
import org.gamegineer.table.internal.core.TableEnvironment;

/**
 * A factory for creating table environments.
 */
@ThreadSafe
public final class TableEnvironmentFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentFactory} class.
     */
    private TableEnvironmentFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // TODO: remove method
    /**
     * Creates a new table.
     * 
     * @return A new table; never {@code null}.
     */
    /* @NonNull */
    public static ITable createTable()
    {
        return new Table();
    }

    /**
     * Creates a new table environment.
     * 
     * @return A new table environment; never {@code null}.
     */
    /* @NonNull */
    public static ITableEnvironment createTableEnvironment()
    {
        return new TableEnvironment();
    }
}
