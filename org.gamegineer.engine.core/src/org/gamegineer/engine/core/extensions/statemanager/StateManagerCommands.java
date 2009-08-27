/*
 * StateManagerCommands.java
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
 * Created on Jul 20, 2008 at 11:48:25 PM.
 */

package org.gamegineer.engine.core.extensions.statemanager;

import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.internal.core.extensions.statemanager.GetMementoCommand;
import org.gamegineer.engine.internal.core.extensions.statemanager.SetMementoCommand;

/**
 * A collection of commands for exercising the functionality of the state
 * manager extension.
 */
public final class StateManagerCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateManagerCommands} class.
     */
    private StateManagerCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that gets a memento that represents the engine state.
     * 
     * @return Creates a command that gets a memento that represents the engine
     *         state; never {@code null}.
     * 
     * @see IStateManager#getMemento(org.gamegineer.engine.core.IEngineContext)
     */
    /* @NonNull */
    public static ICommand<IMemento> createGetMementoCommand()
    {
        return new GetMementoCommand();
    }

    /**
     * Creates a command that sets the engine state using the specified memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return A command that sets the engine state using the specified memento.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * 
     * @see IStateManager#setMemento(org.gamegineer.engine.core.IEngineContext,
     *      IMemento)
     */
    /* @NonNull */
    public static ICommand<Void> createSetMementoCommand(
        /* @NonNull */
        final IMemento memento )
    {
        return new SetMementoCommand( memento );
    }
}
