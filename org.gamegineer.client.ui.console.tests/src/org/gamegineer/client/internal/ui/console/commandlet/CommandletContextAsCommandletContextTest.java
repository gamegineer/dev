/*
 * CommandletContextAsCommandletContextTest.java
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
 * Created on Oct 3, 2008 at 9:43:10 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import java.util.ArrayList;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.commandlet.AbstractCommandletContextTestCase;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.IStatelet;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletContext}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandletContext}
 * interface.
 */
public final class CommandletContextAsCommandletContextTest
    extends AbstractCommandletContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandletContextAsCommandletContextTest} class.
     */
    public CommandletContextAsCommandletContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.AbstractCommandletContextTestCase#createCommandletContext()
     */
    @Override
    protected ICommandletContext createCommandletContext()
    {
        return new CommandletContext( new CommandletExecutor( createDummy( ICommandlet.class ), new ArrayList<String>() ), createDummy( IConsole.class ), createDummy( IStatelet.class ), createDummy( IGameClient.class ) );
    }
}
