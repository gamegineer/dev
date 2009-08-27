/*
 * ICommandHistory.java
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
 * Created on Apr 24, 2008 at 11:07:44 PM.
 */

package org.gamegineer.engine.core.extensions.commandhistory;

import java.util.List;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;

/**
 * An engine extension that exposes the engine command history.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ICommandHistory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates redo is available.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @return {@code true} if redo is available; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public boolean canRedo(
        /* @NonNull */
        IEngineContext context );

    /**
     * Indicates undo is available.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @return {@code true} if undo is available; otherwise {@code false}.
     */
    public boolean canUndo(
        /* @NonNull */
        IEngineContext context );

    /**
     * Gets the command history.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @return The command history; never {@code null}. The first command in
     *         the list represents the earliest command executed by the engine.
     *         The last command in the list represents the latest command
     *         executed by the engine.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    public List<IInvertibleCommand<?>> getCommands(
        /* @NonNull */
        IEngineContext context );

    /**
     * Executes the most recent command in the undo history and adds it to the
     * command history.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If redo is not available.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the command.
     */
    public void redo(
        /* @NonNull */
        IEngineContext context )
        throws EngineException;

    /**
     * Executes the inverse of the most recent command in the command history
     * and moves it from the command history to the undo history.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If undo is not available.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the inverse command.
     */
    public void undo(
        /* @NonNull */
        IEngineContext context )
        throws EngineException;
}
