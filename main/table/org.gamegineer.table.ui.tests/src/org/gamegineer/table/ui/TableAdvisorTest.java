/*
 * TableAdvisorTest.java
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
 * Created on Sep 18, 2009 at 9:36:22 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableAdvisor} class.
 */
public final class TableAdvisorTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableAdvisorTest} class.
     */
    public TableAdvisorTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link TableAdvisor#TableAdvisor} constructor makes a deep
     * copy of the application argument collection.
     */
    @Test
    public void testConstructor_AppArgs_DeepCopy()
    {
        final List<String> appArgs = new ArrayList<>();
        final TableAdvisor tableAdvisor = new TableAdvisor( appArgs );

        appArgs.add( "arg" ); //$NON-NLS-1$

        assertEquals( 0, tableAdvisor.getApplicationArguments().size() );
    }
}
