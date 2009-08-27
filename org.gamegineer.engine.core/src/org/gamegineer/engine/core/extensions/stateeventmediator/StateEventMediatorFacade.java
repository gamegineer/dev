/*
 * StateEventMediatorFacade.java
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
 * Created on Jul 20, 2008 at 11:41:19 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngine;

/**
 * A facade for exercising the functionality of the state event mediator
 * extension.
 */
public final class StateEventMediatorFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventMediatorFacade} class.
     */
    private StateEventMediatorFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified state listener to the engine.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param listener
     *        The state listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered state listener.
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code listener} is {@code null}.
     * 
     * @see IStateEventMediator#addStateListener(org.gamegineer.engine.core.IEngineContext,
     *      IStateListener)
     */
    public static void addStateListener(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final IStateListener listener )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            engine.executeCommand( StateEventMediatorCommands.createAddStateListenerCommand( listener ) );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /**
     * Removes the specified state listener from the engine.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param listener
     *        The state listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered state listener.
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code listener} is {@code null}.
     * 
     * @see IStateEventMediator#removeStateListener(org.gamegineer.engine.core.IEngineContext,
     *      IStateListener)
     */
    public static void removeStateListener(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final IStateListener listener )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            engine.executeCommand( StateEventMediatorCommands.createRemoveStateListenerCommand( listener ) );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }
}
