/*
 * GameCommands.java
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
 * Created on Jul 23, 2008 at 11:42:55 PM.
 */

package org.gamegineer.game.core;

import org.gamegineer.engine.core.ICommand;
import org.gamegineer.game.internal.core.commands.GetGameIdCommand;
import org.gamegineer.game.internal.core.commands.GetGameNameCommand;
import org.gamegineer.game.internal.core.commands.GetGameSystemIdCommand;
import org.gamegineer.game.internal.core.commands.IsGameCompleteCommand;

/**
 * A collection of commands for exercising the functionality of a game.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class GameCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameCommands} class.
     */
    protected GameCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that retrieves the game identifier.
     * 
     * @return A command that retrieves the game identifier; never {@code null}.
     */
    /* @NonNull */
    public static ICommand<String> createGetGameIdCommand()
    {
        return new GetGameIdCommand();
    }

    /**
     * Creates a command that retrieves the game name.
     * 
     * @return A command that retrieves the game name; never {@code null}.
     */
    /* @NonNull */
    public static ICommand<String> createGetGameNameCommand()
    {
        return new GetGameNameCommand();
    }

    /**
     * Creates a command that retrieves the game system identifier.
     * 
     * @return A command that retrieves the game system identifier; never
     *         {@code null}.
     */
    /* @NonNull */
    public static ICommand<String> createGetGameSystemIdCommand()
    {
        return new GetGameSystemIdCommand();
    }

    /**
     * Creates a command that indicates the game is complete.
     * 
     * @return A command that indicates the game is complete; never {@code null}.
     */
    /* @NonNull */
    public static ICommand<Boolean> createIsGameCompleteCommand()
    {
        return new IsGameCompleteCommand();
    }
}
