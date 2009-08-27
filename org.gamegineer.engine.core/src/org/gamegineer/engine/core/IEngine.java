/*
 * IEngine.java
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
 * Created on Feb 16, 2008 at 11:10:29 PM.
 */

package org.gamegineer.engine.core;

import java.util.concurrent.Future;

/**
 * An engine responsible for executing commands and managing the state of a
 * game.
 * 
 * <p>
 * The only functionality provided by an engine is the ability to execute
 * commands. All other behavior, no matter how fundamental it may seem, shall be
 * expressed in terms of commands.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IEngine
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Executes the specified command synchronously.
     * 
     * @param <T>
     *        The result type of the command.
     * @param command
     *        The command to be executed; must not be {@code null}.
     * 
     * @return The result of the command execution; may be {@code null}. The
     *         type of object returned is command-dependent and will be
     *         {@code null} for commands that do not return a result.
     * 
     * @throws java.lang.IllegalStateException
     *         If the engine has been shut down.
     * @throws java.lang.NullPointerException
     *         If {@code command} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the command.
     */
    /* @Nullable */
    public <T> T executeCommand(
        /* @NonNull */
        ICommand<T> command )
        throws EngineException;

    /**
     * Indicates the engine has been shut down.
     * 
     * @return {@code true} if the engine has been shut down; otherwise
     *         {@code false}.
     */
    public boolean isShutdown();

    /**
     * Initiates an orderly shutdown of the engine in which previously-submitted
     * commands are executed, but no new commands will be accepted.
     * 
     * <p>
     * This method blocks until all previously-submitted commands have completed
     * execution or the current thread is interrupted, whichever happens first.
     * Invocation has no additional effect if the engine is already shut down.
     * </p>
     * 
     * @throws java.lang.InterruptedException
     *         If the thread is interrupted during shutdown.
     */
    public void shutdown()
        throws InterruptedException;

    /**
     * Executes the specified command asynchronously.
     * 
     * @param <T>
     *        The result type of the command.
     * @param command
     *        The command to be executed; must not be {@code null}.
     * 
     * @return A handle to the asynchronous operation; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the engine has been shut down.
     * @throws java.lang.NullPointerException
     *         If {@code command} is {@code null}.
     * 
     * @see #executeCommand(ICommand)
     */
    /* @NonNull */
    public <T> Future<T> submitCommand(
        /* @NonNull */
        ICommand<T> command );
}
