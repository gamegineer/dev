/*
 * ICommandExecutedEvent.java
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
 * Created on Jun 2, 2008 at 10:57:29 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

/**
 * The interface that defines the behavior for all event objects used to notify
 * listeners that an engine command has been executed.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ICommandExecutedEvent
    extends ICommandEvent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the exception thrown by the command during its execution.
     * 
     * @return The exception thrown by the command during its execution.
     * 
     * @throws java.lang.IllegalStateException
     *         If the command did not throw an exception during its execution.
     */
    /* @NonNull */
    public Exception getException();

    /**
     * Gets the result of the command execution.
     * 
     * @return The result of the command execution; may be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the command threw an exception during its execution.
     */
    /* @Nullable */
    public Object getResult();

    /**
     * Indicates the command threw an exception during its execution.
     * 
     * @return {@code true} if the command threw an execution during its
     *         execution; otherwise {@code false}.
     */
    public boolean hasException();
}
