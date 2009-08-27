/*
 * CommandEventDelegateTest.java
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
 * Created on Jun 2, 2008 at 11:33:24 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.CommandEventDelegate}
 * class.
 */
public final class CommandEventDelegateTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandEventDelegateTest} class.
     */
    public CommandEventDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * command.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Command_Null()
    {
        new CommandEventDelegate( createDummy( IEngineContext.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * context.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Context_Null()
    {
        new CommandEventDelegate( null, createDummy( ICommand.class ) );
    }
}
