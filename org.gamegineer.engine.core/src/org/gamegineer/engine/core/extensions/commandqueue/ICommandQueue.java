/*
 * ICommandQueue.java
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
 * Created on Jun 8, 2008 at 11:44:15 PM.
 */

package org.gamegineer.engine.core.extensions.commandqueue;

import java.util.concurrent.Future;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;

/**
 * An engine extension that exposes the engine command queue.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ICommandQueue
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Executes the specified command asynchronously.
     * 
     * @param <T>
     *        The result type of the command.
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param command
     *        The command to be executed; must not be {@code null}.
     * 
     * @return A handle to the asynchronous operation; never {@code null}.
     *         Clients must never invoke any {@code get} method of this handle
     *         from an engine worker thread.
     * 
     * @throws java.lang.IllegalStateException
     *         If the engine has been shut down.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code command} is {@code null}.
     * 
     * @see org.gamegineer.engine.core.IEngine#submitCommand(ICommand)
     */
    /* @NonNull */
    public <T> Future<T> submitCommand(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        ICommand<T> command );
}
