/*
 * CommandHistoryFacade.java
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
 * Created on Jul 20, 2008 at 10:56:14 PM.
 */

package org.gamegineer.engine.core.extensions.commandhistory;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IInvertibleCommand;

/**
 * A facade for exercising the functionality of the command history extension.
 */
public final class CommandHistoryFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryFacade} class.
     */
    private CommandHistoryFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the command history.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * 
     * @return The command history; never {@code null}. The first command in
     *         the list represents the earliest command executed by the engine.
     *         The last command in the list represents the latest command
     *         executed by the engine.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code engine} is {@code null}.
     * 
     * @see ICommandHistory#getCommands(org.gamegineer.engine.core.IEngineContext)
     */
    /* @NonNull */
    public static List<IInvertibleCommand<?>> getCommandHistory(
        /* @NonNull */
        final IEngine engine )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            return engine.executeCommand( CommandHistoryCommands.createGetCommandHistoryCommand() );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /**
     * Executes the most recent command in the undo history and adds it to the
     * command history.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If redo is not available.
     * @throws java.lang.NullPointerException
     *         If {@code engine} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the command.
     * 
     * @see ICommandHistory#redo(org.gamegineer.engine.core.IEngineContext)
     */
    public static void redo(
        /* @NonNull */
        final IEngine engine )
        throws EngineException
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        engine.executeCommand( CommandHistoryCommands.createRedoCommand() );
    }

    /**
     * Executes the inverse of the most recent command in the command history
     * and moves it from the command history to the undo history.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If undo is not available.
     * @throws java.lang.NullPointerException
     *         If {@code engine} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the inverse command.
     * 
     * @see ICommandHistory#undo(org.gamegineer.engine.core.IEngineContext)
     */
    public static void undo(
        /* @NonNull */
        final IEngine engine )
        throws EngineException
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        engine.executeCommand( CommandHistoryCommands.createUndoCommand() );
    }
}
