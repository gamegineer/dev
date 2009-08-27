/*
 * GetGameIdCommandTest.java
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
 * Created on Jan 6, 2009 at 11:58:51 PM.
 */

package org.gamegineer.game.internal.core.commands;

import static org.junit.Assert.assertEquals;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.internal.core.GameAttributes;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.commands.GetGameIdCommand} class.
 */
public final class GetGameIdCommandTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetGameIdCommandTest} class.
     */
    public GetGameIdCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code execute} method returns the expected game identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_ReturnValue()
        throws Exception
    {
        final GetGameIdCommand command = new GetGameIdCommand();
        final IEngineContext context = new FakeEngineContext();
        final String expectedId = "game-id"; //$NON-NLS-1$
        GameAttributes.GAME_ID.add( context.getState(), expectedId );

        final String actualId = command.execute( context );

        assertEquals( expectedId, actualId );
    }
}
