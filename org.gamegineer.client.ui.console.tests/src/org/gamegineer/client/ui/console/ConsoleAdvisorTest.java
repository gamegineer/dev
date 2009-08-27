/*
 * ConsoleAdvisorTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Oct 10, 2008 at 11:44:34 PM.
 */

package org.gamegineer.client.ui.console;

import java.util.Collections;
import org.junit.Test;
import org.osgi.framework.Version;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.ui.console.ConsoleAdvisor} class.
 */
public final class ConsoleAdvisorTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConsoleAdvisorTest} class.
     */
    public ConsoleAdvisorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * application argument list.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_AppArgList_Null()
    {
        new ConsoleAdvisor( null, Version.emptyVersion );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * application version.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_AppVersion_Null()
    {
        new ConsoleAdvisor( Collections.<String>emptyList(), null );
    }
}
