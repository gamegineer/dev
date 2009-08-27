/*
 * StateEventMediatorCommands.java
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
 * Created on Jul 20, 2008 at 11:32:12 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.internal.core.extensions.stateeventmediator.AddStateListenerCommand;
import org.gamegineer.engine.internal.core.extensions.stateeventmediator.RemoveStateListenerCommand;

/**
 * A collection of commands for exercising the functionality of the state event
 * mediator extension.
 */
public final class StateEventMediatorCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventMediatorCommands}
     * class.
     */
    private StateEventMediatorCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that adds the specified state listener to the engine.
     * 
     * @param listener
     *        The state listener; must not be {@code null}.
     * 
     * @return A command that adds the specified state listener to the engine;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     * 
     * @see IStateEventMediator#addStateListener(org.gamegineer.engine.core.IEngineContext,
     *      IStateListener)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createAddStateListenerCommand(
        /* @NonNull */
        final IStateListener listener )
    {
        return new AddStateListenerCommand( listener );
    }

    /**
     * Creates a command that removes the specified state listener from the
     * engine.
     * 
     * @param listener
     *        The state listener; must not be {@code null}.
     * 
     * @return A command that removes the specified state listener from the
     *         engine.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     * 
     * @see IStateEventMediator#removeStateListener(org.gamegineer.engine.core.IEngineContext,
     *      IStateListener)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createRemoveStateListenerCommand(
        /* @NonNull */
        final IStateListener listener )
    {
        return new RemoveStateListenerCommand( listener );
    }
}
