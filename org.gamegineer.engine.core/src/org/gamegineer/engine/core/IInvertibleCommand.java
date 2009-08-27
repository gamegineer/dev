/*
 * IInvertibleCommand.java
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
 * Created on Jun 22, 2008 at 9:07:49 PM.
 */

package org.gamegineer.engine.core;

/**
 * A command that is invertible.
 * 
 * <p>
 * A command that modifies the engine state must be invertible. That is, such a
 * command must be able to undo the modifications it made to the engine state in
 * order for the engine to roll back changes made to its state.
 * </p>
 * 
 * <p>
 * Implementors of commands that modify the engine state have a choice of using
 * the default inverse command implementation provided by the engine or to
 * provide their own custom implementation. A command that does not implement
 * {@code IInvertibleCommand} will be adapted by the engine to use the default
 * implementation. Note that a custom implementation will almost always be more
 * efficient than the default implementation.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 * 
 * @param <T>
 *        The result type of the command.
 */
public interface IInvertibleCommand<T>
    extends ICommand<T>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a command that performs the inverse operation of this command.
     * 
     * <p>
     * The inverse command must revert any state changes made during the
     * execution of this command.
     * </p>
     * 
     * <p>
     * This operation must be symmetric. That is, the inverse of the inverse
     * command must yield a command that is functionally equivalent to this
     * command.
     * </p>
     * 
     * @return A command that performs the inverse operation of this command;
     *         never {@code null}.
     */
    /* @NonNull */
    public IInvertibleCommand<T> getInverseCommand();
}
