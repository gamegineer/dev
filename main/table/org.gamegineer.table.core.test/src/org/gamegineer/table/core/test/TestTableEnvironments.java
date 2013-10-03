/*
 * TestTableEnvironments.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 2, 2013 at 8:26:26 PM.
 */

package org.gamegineer.table.core.test;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.gamegineer.table.internal.core.test.Activator;

/**
 * A factory for creating various types of table environments suitable for
 * testing.
 */
@ThreadSafe
public final class TestTableEnvironments
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestTableEnvironments} class.
     */
    private TestTableEnvironments()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table environment.
     * 
     * @param context
     *        The table environment context; must not be {@code null}.
     * 
     * @return A new table environment; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    public static ITableEnvironment createTableEnvironment(
        /* @NonNull */
        final ITableEnvironmentContext context )
    {
        final ITableEnvironmentFactory tableEnvironmentFactory = Activator.getDefault().getTableEnvironmentFactory();
        assert tableEnvironmentFactory != null;
        return tableEnvironmentFactory.createTableEnvironment( context );
    }
}
