/*
 * ICommandListener.java
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
 * Created on May 8, 2008 at 8:32:14 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import java.util.EventListener;
import org.gamegineer.engine.core.EngineException;

/**
 * The listener interface for use by clients to be notified of events pertaining
 * to the execution of engine commands.
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface ICommandListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after a command has completed execution.
     * 
     * <p>
     * This method is invoked irrespective of whether or not the command
     * executed successfully. If execution succeeded, the event reports the
     * result returned by the command. If execution failed, the event reports
     * the exception thrown by the command.
     * </p>
     * 
     * @param event
     *        The event describing the executed command; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void commandExecuted(
        /* @NonNull */
        CommandExecutedEvent event );

    /**
     * Invoked before a command is about to be executed.
     * 
     * <p>
     * Clients may abort the execution of a command by throwing an
     * {@code EngineException} from this method. In this case, the thrown
     * exception will be reported to the original executor of the command, but
     * the engine state will be as if the command was never submitted to the
     * engine for execution.
     * </p>
     * 
     * @param event
     *        The event describing the command to be executed; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If the command should not be executed.
     */
    public void commandExecuting(
        /* @NonNull */
        CommandExecutingEvent event )
        throws EngineException;
}
