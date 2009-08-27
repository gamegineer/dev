/*
 * UndoCommandTest.java
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
 * Created on Apr 29, 2008 at 9:56:10 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.UndoCommand}
 * class.
 */
public final class UndoCommandTest
    extends AbstractCommandHistoryCommandTestCase<UndoCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code UndoCommandTest} class.
     */
    public UndoCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.commandhistory.AbstractCommandHistoryCommandTestCase#createCommand()
     */
    @Override
    protected UndoCommand createCommand()
    {
        return new UndoCommand();
    }

    /**
     * Ensures the {@code execute} method throws an exception if the undo
     * operation is not available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testExecute_Undo_Unavailable()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );
    }
}
