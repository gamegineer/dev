/*
 * TableRunnerFactory.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Dec 20, 2013 at 9:24:20 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.ITableRunner;
import org.gamegineer.table.ui.ITableRunnerFactory;

/**
 * Implementation of {@link ITableRunnerFactory}.
 */
@Immutable
public final class TableRunnerFactory
    implements ITableRunnerFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableRunnerFactory} class.
     */
    public TableRunnerFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.ITableRunnerFactory#createTableRunner(org.gamegineer.table.ui.ITableAdvisor)
     */
    @Override
    public ITableRunner createTableRunner(
        final ITableAdvisor advisor )
    {
        return new TableRunner( advisor );
    }
}
