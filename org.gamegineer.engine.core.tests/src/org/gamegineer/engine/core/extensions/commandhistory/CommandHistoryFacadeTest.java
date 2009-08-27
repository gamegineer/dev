/*
 * CommandHistoryFacadeTest.java
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
 * Created on Jul 20, 2008 at 10:58:08 PM.
 */

package org.gamegineer.engine.core.extensions.commandhistory;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.commandhistory.CommandHistoryFacade}
 * class.
 */
public final class CommandHistoryFacadeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryFacadeTest} class.
     */
    public CommandHistoryFacadeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getCommandHistory} method throws an exception when
     * passed a {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCommandHistory_Engine_Null()
    {
        CommandHistoryFacade.getCommandHistory( null );
    }

    /**
     * Ensures the {@code redo} method throws an exception when passed a
     * {@code null} engine.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRedo_Engine_Null()
        throws Exception
    {
        CommandHistoryFacade.redo( null );
    }

    /**
     * Ensures the {@code undo} method throws an exception when passed a
     * {@code null} engine.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testUndo_Engine_Null()
        throws Exception
    {
        CommandHistoryFacade.undo( null );
    }
}
