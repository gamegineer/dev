/*
 * CommandExecutedEventDelegateTest.java
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
 * Created on Jun 2, 2008 at 11:52:44 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.CommandExecutedEventDelegate}
 * class.
 */
public final class CommandExecutedEventDelegateTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandExecutedEventDelegateTest} class.
     */
    public CommandExecutedEventDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createFailedCommandExecutedEventDelegate} method
     * throws an exception when passed a {@code null} command.
     */
    @Test( expected = AssertionError.class )
    public void testCreateFailedCommandExecutedEventDelegate_Command_Null()
    {
        CommandExecutedEventDelegate.createFailedCommandExecutedEventDelegate( new FakeEngineContext(), null, new Exception() );
    }

    /**
     * Ensures the {@code createFailedCommandExecutedEventDelegate} method
     * throws an exception when passed a {@code null} context.
     */
    @Test( expected = AssertionError.class )
    public void testCreateFailedCommandExecutedEventDelegate_Context_Null()
    {
        CommandExecutedEventDelegate.createFailedCommandExecutedEventDelegate( null, new MockCommand<Void>(), new Exception() );
    }

    /**
     * Ensures the {@code createFailedCommandExecutedEventDelegate} method
     * throws an exception when passed a {@code null} exception.
     */
    @Test( expected = AssertionError.class )
    public void testCreateFailedCommandExecutedEventDelegate_Exception_Null()
    {
        CommandExecutedEventDelegate.createFailedCommandExecutedEventDelegate( new FakeEngineContext(), new MockCommand<Void>(), null );
    }

    /**
     * Ensures the {@code createSuccessfulCommandExecutedEventDelegate} method
     * throws an exception when passed a {@code null} command.
     */
    @Test( expected = AssertionError.class )
    public void testCreateSuccessfulCommandExecutedEventDelegate_Command_Null()
    {
        CommandExecutedEventDelegate.createSuccessfulCommandExecutedEventDelegate( new FakeEngineContext(), null, new Object() );
    }

    /**
     * Ensures the {@code createSuccessfulCommandExecutedEventDelegate} method
     * throws an exception when passed a {@code null} context.
     */
    @Test( expected = AssertionError.class )
    public void testCreateSuccessfulCommandExecutedEventDelegate_Context_Null()
    {
        CommandExecutedEventDelegate.createSuccessfulCommandExecutedEventDelegate( null, new MockCommand<Void>(), new Object() );
    }

    /**
     * Ensures the {@code createSuccessfulCommandExecutedEventDelegate} method
     * does not throw an exception when passed a {@code null} result.
     */
    @Test
    public void testCreateSuccessfulCommandExecutedEventDelegate_Result_Null()
    {
        CommandExecutedEventDelegate.createSuccessfulCommandExecutedEventDelegate( new FakeEngineContext(), new MockCommand<Void>(), null );
    }
}
