/*
 * InternalCommandExecutingEventTest.java
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
 * Created on Jun 2, 2008 at 11:14:50 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.InternalCommandExecutingEvent}
 * class.
 */
public final class InternalCommandExecutingEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InternalCommandExecutingEventTest} class.
     */
    public InternalCommandExecutingEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCommandExecutingEvent} method throws an
     * exception when passed a {@code null} command.
     */
    @Test( expected = AssertionError.class )
    public void testCreateCommandExecutingEvent_Command_Null()
    {
        InternalCommandExecutingEvent.createCommandExecutingEvent( createDummy( IEngineContext.class ), null );
    }

    /**
     * Ensures the {@code createCommandExecutingEvent} method throws an
     * exception when passed a {@code null} context.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCommandExecutingEvent_Context_Null()
    {
        InternalCommandExecutingEvent.createCommandExecutingEvent( null, createDummy( ICommand.class ) );
    }
}
