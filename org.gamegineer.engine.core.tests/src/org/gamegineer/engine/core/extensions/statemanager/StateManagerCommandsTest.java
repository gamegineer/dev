/*
 * StateManagerCommandsTest.java
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
 * Created on Jul 20, 2008 at 11:51:48 PM.
 */

package org.gamegineer.engine.core.extensions.statemanager;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.statemanager.StateManagerCommands}
 * class.
 */
public final class StateManagerCommandsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateManagerCommandsTest} class.
     */
    public StateManagerCommandsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createSetMementoCommand} method throws an exception
     * when passed a {@code null} memento.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateSetMementoCommand_Memento_Null()
    {
        StateManagerCommands.createSetMementoCommand( null );
    }
}
