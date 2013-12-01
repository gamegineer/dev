/*
 * TableAdvisorAsTableAdvisorTest.java
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
 * Created on Sep 18, 2009 at 9:34:03 PM.
 */

package org.gamegineer.table.ui;

import java.util.ArrayList;
import org.gamegineer.table.ui.test.AbstractTableAdvisorTestCase;

/**
 * A fixture for testing the {@link TableAdvisor} class to ensure it does not
 * violate the contract of the {@link ITableAdvisor} interface.
 */
public final class TableAdvisorAsTableAdvisorTest
    extends AbstractTableAdvisorTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableAdvisorAsTableAdvisorTest}
     * class.
     */
    public TableAdvisorAsTableAdvisorTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.test.AbstractTableAdvisorTestCase#createTableAdvisor()
     */
    @Override
    protected ITableAdvisor createTableAdvisor()
    {
        return new TableAdvisor( new ArrayList<String>() );
    }
}
