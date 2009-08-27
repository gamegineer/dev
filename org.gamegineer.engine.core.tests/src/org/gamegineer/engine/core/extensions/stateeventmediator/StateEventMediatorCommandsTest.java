/*
 * StateEventMediatorCommandsTest.java
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
 * Created on Jul 20, 2008 at 11:37:37 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateEventMediatorCommands}
 * class.
 */
public final class StateEventMediatorCommandsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventMediatorCommandsTest}
     * class.
     */
    public StateEventMediatorCommandsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createAddStateListenerCommand} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateAddStateListenerCommand_Listener_Null()
    {
        StateEventMediatorCommands.createAddStateListenerCommand( null );
    }

    /**
     * Ensures the {@code createRemoveStateListenerCommand} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateRemoveStateListenerCommand_Listener_Null()
    {
        StateEventMediatorCommands.createRemoveStateListenerCommand( null );
    }
}
