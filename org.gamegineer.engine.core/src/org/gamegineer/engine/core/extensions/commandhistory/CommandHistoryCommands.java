/*
 * CommandHistoryCommands.java
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
 * Created on Jul 19, 2008 at 11:36:47 PM.
 */

package org.gamegineer.engine.core.extensions.commandhistory;

import java.util.List;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.internal.core.extensions.commandhistory.GetCommandHistoryCommand;
import org.gamegineer.engine.internal.core.extensions.commandhistory.RedoCommand;
import org.gamegineer.engine.internal.core.extensions.commandhistory.UndoCommand;

/**
 * A collection of commands for exercising the functionality of the command
 * history extension.
 */
public final class CommandHistoryCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryCommands} class.
     */
    private CommandHistoryCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that gets the engine command history.
     * 
     * @return A command that gets the engine command history; never
     *         {@code null}.
     * 
     * @see ICommandHistory#getCommands(org.gamegineer.engine.core.IEngineContext)
     */
    /* @NonNull */
    public static ICommand<List<IInvertibleCommand<?>>> createGetCommandHistoryCommand()
    {
        return new GetCommandHistoryCommand();
    }

    /**
     * Creates a command that executes the most recent command in the undo
     * history and adds it to the command history.
     * 
     * @return A command that executes the most recent command in the undo
     *         history and adds it to the command history; never {@code null}.
     * 
     * @see ICommandHistory#redo(org.gamegineer.engine.core.IEngineContext)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createRedoCommand()
    {
        return new RedoCommand();
    }

    /**
     * Creates a command that executes the inverse of the most recent command in
     * the command history and moves it from the command history to the undo
     * history.
     * 
     * @return A command that executes the inverse of the most recent command in
     *         the command history and moves it from the command history to the
     *         undo history; never {@code null}.
     * 
     * @see ICommandHistory#undo(org.gamegineer.engine.core.IEngineContext)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createUndoCommand()
    {
        return new UndoCommand();
    }
}
