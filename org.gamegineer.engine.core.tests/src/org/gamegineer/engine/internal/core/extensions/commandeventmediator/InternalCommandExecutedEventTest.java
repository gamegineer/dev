/*
 * InternalCommandExecutedEventTest.java
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
 * Created on May 9, 2008 at 9:22:17 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.InternalCommandExecutedEvent}
 * class.
 */
public final class InternalCommandExecutedEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InternalCommandExecutedEventTest} class.
     */
    public InternalCommandExecutedEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createFailedCommandExecutedEvent} method throws an
     * exception when passed a {@code null} command.
     */
    @Test( expected = AssertionError.class )
    public void testCreateFailedCommandExecutedEvent_Command_Null()
    {
        InternalCommandExecutedEvent.createFailedCommandExecutedEvent( createDummy( IEngineContext.class ), null, new Exception() );
    }

    /**
     * Ensures the {@code createFailedCommandExecutedEvent} method throws an
     * exception when passed a {@code null} context.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateFailedCommandExecutedEvent_Context_Null()
    {
        InternalCommandExecutedEvent.createFailedCommandExecutedEvent( null, createDummy( ICommand.class ), new Exception() );
    }

    /**
     * Ensures the {@code createFailedCommandExecutedEvent} method throws an
     * exception when passed a {@code null} exception.
     */
    @Test( expected = AssertionError.class )
    public void testCreateFailedCommandExecutedEvent_Exception_Null()
    {
        InternalCommandExecutedEvent.createFailedCommandExecutedEvent( createDummy( IEngineContext.class ), createDummy( ICommand.class ), null );
    }

    /**
     * Ensures the {@code createSuccessfulCommandExecutedEvent} method throws an
     * exception when passed a {@code null} command.
     */
    @Test( expected = AssertionError.class )
    public void testCreateSuccessfulCommandExecutedEvent_Command_Null()
    {
        InternalCommandExecutedEvent.createSuccessfulCommandExecutedEvent( createDummy( IEngineContext.class ), null, new Object() );
    }

    /**
     * Ensures the {@code createSuccessfulCommandExecutedEvent} method throws an
     * exception when passed a {@code null} context.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateSuccessfulCommandExecutedEvent_Context_Null()
    {
        InternalCommandExecutedEvent.createSuccessfulCommandExecutedEvent( null, createDummy( ICommand.class ), new Object() );
    }

    /**
     * Ensures the {@code createSuccessfulCommandExecutedEvent} method does not
     * throw an exception when passed a {@code null} result.
     */
    @Test
    public void testCreateSuccessfulCommandExecutedEvent_Result_Null()
    {
        InternalCommandExecutedEvent.createSuccessfulCommandExecutedEvent( new FakeEngineContext(), new MockCommand<Void>(), null );
    }
}
