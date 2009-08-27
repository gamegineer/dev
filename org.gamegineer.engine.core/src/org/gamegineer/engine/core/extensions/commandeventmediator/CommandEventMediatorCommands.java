/*
 * CommandEventMediatorCommands.java
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
 * Created on Jul 19, 2008 at 9:31:51 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.internal.core.extensions.commandeventmediator.AddCommandListenerCommand;
import org.gamegineer.engine.internal.core.extensions.commandeventmediator.RemoveCommandListenerCommand;

/**
 * A collection of commands for exercising the functionality of the command
 * event mediator extension.
 */
public final class CommandEventMediatorCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandEventMediatorCommands}
     * class.
     */
    private CommandEventMediatorCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that adds the specified command listener to the engine.
     * 
     * @param listener
     *        The command listener; must not be {@code null}.
     * 
     * @return A command that adds the specified command listener to the engine;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     * 
     * @see ICommandEventMediator#addCommandListener(org.gamegineer.engine.core.IEngineContext,
     *      ICommandListener)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createAddCommandListenerCommand(
        /* @NonNull */
        final ICommandListener listener )
    {
        return new AddCommandListenerCommand( listener );
    }

    /**
     * Creates a command that removes the specified command listener from the
     * engine.
     * 
     * @param listener
     *        The command listener; must not be {@code null}.
     * 
     * @return A command that removes the specified command listener from the
     *         engine; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     * 
     * @see ICommandEventMediator#removeCommandListener(org.gamegineer.engine.core.IEngineContext,
     *      ICommandListener)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createRemoveCommandListenerCommand(
        /* @NonNull */
        final ICommandListener listener )
    {
        return new RemoveCommandListenerCommand( listener );
    }
}
