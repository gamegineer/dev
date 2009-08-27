/*
 * CommandletContextTest.java
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
 * Created on Oct 3, 2008 at 9:45:42 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import java.util.Collections;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.IStatelet;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletContext}
 * class.
 */
public final class CommandletContextTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletContextTest} class.
     */
    public CommandletContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * console.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Console_Null()
    {
        new CommandletContext( new CommandletExecutor( createDummy( ICommandlet.class ), Collections.<String>emptyList() ), null, createDummy( IStatelet.class ), createDummy( IGameClient.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * commandlet executor.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Executor_Null()
    {
        new CommandletContext( null, createDummy( IConsole.class ), createDummy( IStatelet.class ), createDummy( IGameClient.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * game client.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_GameClient_Null()
    {
        new CommandletContext( new CommandletExecutor( createDummy( ICommandlet.class ), Collections.<String>emptyList() ), createDummy( IConsole.class ), createDummy( IStatelet.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * statelet.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Statelet_Null()
    {
        new CommandletContext( new CommandletExecutor( createDummy( ICommandlet.class ), Collections.<String>emptyList() ), createDummy( IConsole.class ), null, createDummy( IGameClient.class ) );
    }
}
