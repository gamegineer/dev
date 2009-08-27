/*
 * ICommand.java
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
 * Created on Feb 16, 2008 at 11:13:31 PM.
 */

package org.gamegineer.engine.core;

/**
 * A command is an abstract representation for some semantic game behavior.
 * 
 * <p>
 * Commands are instances of the Command pattern. They encapsulate game behavior
 * to be executed within the context of a game engine. Commands are the only
 * model entities that can read and modify the engine state. Thus, a game is
 * driven solely by the execution of commands.
 * </p>
 * 
 * <p>
 * In order to modify the engine state, a command must acquire the engine write
 * lock. A command implementation acquires the write lock by applying the
 * {@link CommandBehavior} annotation to the command class with the
 * {@code writeLockRequired} element set to {@code true}. Otherwise, the
 * command will not be allowed to modify the engine state. The engine will throw
 * an exception if the write lock is not acquired before the command attempts to
 * modify the engine state.
 * </p>
 * 
 * <p>
 * A command that modifies the engine state may choose to implement the
 * {@link IInvertibleCommand} interface to provide a custom implementation for
 * undoing the modifications it makes to the engine state. If a command does not
 * support this interface, the engine will provide an adapter that uses a
 * default undo implementation. The default implementation may not be as
 * efficient as a custom implementation. Therefore, implementors should
 * seriously consider providing their own inverse command implementations when
 * appropriate.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 * 
 * @param <T>
 *        The result type of the command.
 */
public interface ICommand<T>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Executes this command.
     * 
     * @param context
     *        The context within which the command is executed; never
     *        {@code null}.
     * 
     * @return The result of the execution; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of this command.
     */
    /* @Nullable */
    public T execute(
        /* @NonNull */
        IEngineContext context )
        throws EngineException;

    /**
     * Gets the unique identifier of the type of this command.
     * 
     * @return The unique identifier of the type of this command; never
     *         {@code null}.
     */
    /* @NonNull */
    public String getType();
}
