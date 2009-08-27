/*
 * StateManagerFacade.java
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
 * Created on Jul 20, 2008 at 11:53:59 PM.
 */

package org.gamegineer.engine.core.extensions.statemanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngine;

/**
 * A facade for exercising the functionality of the state manager extension.
 */
public final class StateManagerFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateManagerFacade} class.
     */
    private StateManagerFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a memento that represents the engine state.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * 
     * @return A memento that represents the engine state; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code engine} is {@code null}.
     * 
     * @see IStateManager#getMemento(org.gamegineer.engine.core.IEngineContext)
     */
    /* @NonNull */
    public static IMemento getMemento(
        /* @NonNull */
        final IEngine engine )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            return engine.executeCommand( StateManagerCommands.createGetMementoCommand() );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }

    /**
     * Sets the engine state using the specified memento.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code memento} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If the memento does not represent a valid engine state.
     * 
     * @see IStateManager#setMemento(org.gamegineer.engine.core.IEngineContext,
     *      IMemento)
     */
    public static void setMemento(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final IMemento memento )
        throws EngineException
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        engine.executeCommand( StateManagerCommands.createSetMementoCommand( memento ) );
    }
}
