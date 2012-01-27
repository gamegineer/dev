/*
 * TableRunnerAsTableRunnerTest.java
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
 * Created on Oct 3, 2009 at 8:10:57 PM.
 */

package org.gamegineer.table.internal.ui;

import java.util.Collections;
import org.gamegineer.table.ui.AbstractTableRunnerTestCase;
import org.gamegineer.table.ui.ITableRunner;
import org.gamegineer.table.ui.TableAdvisor;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.TableRunner} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.table.ui.ITableRunner}
 * interface.
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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    @Override
    protected ITableRunner createTableRunner()
    {
        return new TableRunner( new TableAdvisor( Collections.<String>emptyList() ) );
    }
}
