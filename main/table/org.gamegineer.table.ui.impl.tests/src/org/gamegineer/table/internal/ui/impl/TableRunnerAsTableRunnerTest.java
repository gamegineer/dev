/*
 * TableRunnerAsTableRunnerTest.java
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
 * Created on Oct 3, 2009 at 8:10:57 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import java.util.Collections;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.ui.ITableRunner;
import org.gamegineer.table.ui.TableAdvisor;
import org.gamegineer.table.ui.test.AbstractTableRunnerTestCase;

/**
 * A fixture for testing the {@link TableRunner} class to ensure it does not
 * violate the contract of the {@link ITableRunner} interface.
 */
public final class TableRunnerAsTableRunnerTest
    extends AbstractTableRunnerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableRunnerAsTableRunnerTest}
     * class.
     */
    public TableRunnerAsTableRunnerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.test.AbstractTableRunnerTestCase#createTableRunner()
     */
    @Override
    protected ITableRunner createTableRunner()
    {
        return new TableRunner( new TableAdvisor( Collections.<@NonNull String>emptyList() ) );
    }
}
