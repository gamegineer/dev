/*
 * IsGameCompleteCommandTest.java
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
 * Created on Sep 2, 2008 at 11:42:31 PM.
 */

package org.gamegineer.game.internal.core.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.internal.core.GameAttributes;
import org.gamegineer.game.internal.core.Stage;
import org.gamegineer.game.internal.core.Stages;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.commands.IsGameCompleteCommand}
 * class.
 */
public final class IsGameCompleteCommandTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code IsGameCompleteCommandTest}
     * class.
     */
    public IsGameCompleteCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code execute} method returns {@code true} when the game is
     * complete.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_Game_Complete()
        throws Exception
    {
        final IsGameCompleteCommand command = new IsGameCompleteCommand();
        final IEngineContext context = new FakeEngineContext();
        final Stage rootStage = Stages.createUniqueStage();
        GameAttributes.ROOT_STAGE.add( context.getState(), rootStage );

        final boolean isGameComplete = command.execute( context );

        assertTrue( isGameComplete );
    }

    /**
     * Ensures the {@code execute} method returns {@code false} when the game is
     * incomplete.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_Game_Incomplete()
        throws Exception
    {
        final IsGameCompleteCommand command = new IsGameCompleteCommand();
        final IEngineContext context = new FakeEngineContext();
        final Stage rootStage = Stages.createUniqueStage();
        GameAttributes.ROOT_STAGE.add( context.getState(), rootStage );
        rootStage.activate( context );

        final boolean isGameComplete = command.execute( context );

        assertFalse( isGameComplete );
    }
}
