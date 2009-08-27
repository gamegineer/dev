/*
 * RedoCommandTest.java
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
 * Created on Apr 29, 2008 at 9:26:36 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.RedoCommand}
 * class.
 */
public final class RedoCommandTest
    extends AbstractCommandHistoryCommandTestCase<RedoCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RedoCommandTest} class.
     */
    public RedoCommandTest()
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
    protected RedoCommand createCommand()
    {
        return new RedoCommand();
    }

    /**
     * Ensures the {@code execute} method throws an exception if the redo
     * operation is not available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testExecute_Redo_Unavailable()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );
    }
}
