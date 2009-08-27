/*
 * ConsoleAsConsoleTest.java
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
 * Created on Oct 5, 2008 at 10:42:42 PM.
 */

package org.gamegineer.client.internal.ui.console;

import java.util.concurrent.Callable;
import org.gamegineer.client.ui.console.AbstractConsoleTestCase;
import org.gamegineer.client.ui.console.ConsoleResult;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.IConsoleAdvisor;
import org.gamegineer.client.ui.console.IDisplay;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.Console} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.IConsole} interface.
 */
public final class ConsoleAsConsoleTest
    extends AbstractConsoleTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConsoleAsConsoleTest} class.
     */
    public ConsoleAsConsoleTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.AbstractConsoleTestCase#awaitConsoleFinished(org.gamegineer.client.ui.console.IConsole)
     */
    @Override
    protected void awaitConsoleFinished(
        final IConsole console )
        throws InterruptedException
    {
        ConsoleTest.awaitConsoleState( (Console)console, Console.State.FINISHED );
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractConsoleTestCase#awaitConsoleRunning(org.gamegineer.client.ui.console.IConsole)
     */
    @Override
    protected void awaitConsoleRunning(
        final IConsole console )
        throws InterruptedException
    {
        ConsoleTest.awaitConsoleState( (Console)console, Console.State.RUNNING );
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractConsoleTestCase#createConsole(org.gamegineer.client.ui.console.IDisplay, org.gamegineer.client.ui.console.IConsoleAdvisor)
     */
    @Override
    protected IConsole createConsole(
        final IDisplay display,
        final IConsoleAdvisor advisor )
        throws Exception
    {
        return new Console( display, advisor );
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractConsoleTestCase#createConsoleRunner(org.gamegineer.client.ui.console.IConsole)
     */
    @Override
    protected Callable<ConsoleResult> createConsoleRunner(
        final IConsole console )
        throws Exception
    {
        return ((Console)console).createRunner();
    }
}
