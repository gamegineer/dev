/*
 * CommandEventMediatorFacade.java
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
 * Created on Jul 19, 2008 at 9:58:22 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngine;

/**
 * A facade for exercising the functionality of the command event mediator
 * extension.
 */
public final class CommandEventMediatorFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandEventMediatorFacade}
     * class.
     */
    private CommandEventMediatorFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified command listener to the engine.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param listener
     *        The command listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered command listener.
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code listener} is {@code null}.
     * 
     * @see ICommandEventMediator#addCommandListener(org.gamegineer.engine.core.IEngineContext,
     *      ICommandListener)
     */
    public static void addCommandListener(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final ICommandListener listener )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            engine.executeCommand( CommandEventMediatorCommands.createAddCommandListenerCommand( listener ) );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /**
     * Removes the specified command listener from the engine.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param listener
     *        The command listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered command listener.
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code listener} is {@code null}.
     * 
     * @see ICommandEventMediator#removeCommandListener(org.gamegineer.engine.core.IEngineContext,
     *      ICommandListener)
     */
    public static void removeCommandListener(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final ICommandListener listener )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            engine.executeCommand( CommandEventMediatorCommands.createRemoveCommandListenerCommand( listener ) );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }
}
