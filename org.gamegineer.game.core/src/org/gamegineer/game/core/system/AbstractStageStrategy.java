/*
 * AbstractStageStrategy.java
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
 * Created on Jul 29, 2008 at 10:55:51 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.game.core.GameException;

/**
 * Superclass for implementations of
 * {@link org.gamegineer.game.core.system.IStageStrategy}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public abstract class AbstractStageStrategy
    implements IStageStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStageStrategy} class.
     */
    protected AbstractStageStrategy()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * The default implementation does nothing.
     * 
     * @see org.gamegineer.game.core.system.IStageStrategy#activate(org.gamegineer.game.core.system.IStage,
     *      org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public void activate(
        final IStage stage,
        final IEngineContext context )
        throws GameException
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }

    /**
     * The default implementation does nothing.
     * 
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuted(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent)
     */
    public void commandExecuted(
        final CommandExecutedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * The default implementation does nothing.
     * 
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener#commandExecuting(org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent)
     */
    @SuppressWarnings( "unused" )
    public void commandExecuting(
        final CommandExecutingEvent event )
        throws EngineException
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * The default implementation does nothing.
     * 
     * @see org.gamegineer.game.core.system.IStageStrategy#deactivate(org.gamegineer.game.core.system.IStage,
     *      org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public void deactivate(
        final IStage stage,
        final IEngineContext context )
        throws GameException
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
    }

    /**
     * The default implementation does nothing.
     * 
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanged(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    public void stateChanged(
        final StateChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * The default implementation does nothing.
     * 
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener#stateChanging(org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent)
     */
    @SuppressWarnings( "unused" )
    public void stateChanging(
        final StateChangeEvent event )
        throws EngineException
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }
}
