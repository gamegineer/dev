/*
 * TableAdvisorTest.java
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
 * Created on Sep 18, 2009 at 9:36:22 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.osgi.framework.Version;

/**
 * A fixture for testing the {@link org.gamegineer.table.ui.TableAdvisor} class.
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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor makes a deep copy of the application argument
     * collection.
     */
    @Test
    public void testConstructor_AppArgs_DeepCopy()
    {
        final List<String> appArgs = new ArrayList<String>();
        final TableAdvisor tableAdvisor = new TableAdvisor( appArgs, Version.emptyVersion );

        appArgs.add( "arg" ); //$NON-NLS-1$

        assertEquals( 0, tableAdvisor.getApplicationArguments().size() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * application argument collection.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_AppArgs_Null()
    {
        new TableAdvisor( null, Version.emptyVersion );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * application version.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_AppVersion_Null()
    {
        new TableAdvisor( Collections.<String>emptyList(), null );
    }
}