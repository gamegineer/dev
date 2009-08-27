/*
 * ConsoleAdvisorAsConsoleAdvisorTest.java
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
 * Created on Oct 10, 2008 at 11:42:24 PM.
 */

package org.gamegineer.client.ui.console;

import java.util.ArrayList;
import org.osgi.framework.Version;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.ui.console.ConsoleAdvisor} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.IConsoleAdvisor} interface.
 */
public final class ConsoleAdvisorAsConsoleAdvisorTest
    extends AbstractConsoleAdvisorTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ConsoleAdvisorAsConsoleAdvisorTest} class.
     */
    public ConsoleAdvisorAsConsoleAdvisorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.AbstractConsoleAdvisorTestCase#createConsoleAdvisor()
     */
    @Override
    protected IConsoleAdvisor createConsoleAdvisor()
        throws Exception
    {
        return new ConsoleAdvisor( new ArrayList<String>(), Version.emptyVersion );
    }
}
