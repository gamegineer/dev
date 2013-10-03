/*
 * TableEnvironmentFactory.java
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
 * Created on Oct 6, 2009 at 11:05:05 PM.
 */

package org.gamegineer.table.internal.core.impl;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.ITableEnvironmentFactory;

/**
 * Implementation of {@link ITableEnvironmentFactory}.
 */
@Immutable
public final class TableEnvironmentFactory
    implements ITableEnvironmentFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentFactory} class.
     */
    public TableEnvironmentFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITableEnvironmentFactory#createTableEnvironment(org.gamegineer.table.core.ITableEnvironmentContext)
     */
    @Override
    public ITableEnvironment createTableEnvironment(
        final ITableEnvironmentContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return new TableEnvironment( context );
    }
}
