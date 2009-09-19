/*
 * TableUITest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Sep 18, 2009 at 9:55:33 PM.
 */

package org.gamegineer.table.ui;

import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.ui.TableUI} class.
 */
public final class TableUITest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableUITest} class.
     */
    public TableUITest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createAndRunTable} method throws an exception when
     * passed a {@code null} table advisor.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateAndRunTable_Advisor_Null()
        throws Exception
    {
        TableUI.createAndRunTable( null );
    }

    /**
     * Ensures the {@code createTableRunner} method throws an exception when
     * passed a {@code null} table advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableRunner_Advisor_Null()
    {
        TableUI.createTableRunner( null );
    }
}
