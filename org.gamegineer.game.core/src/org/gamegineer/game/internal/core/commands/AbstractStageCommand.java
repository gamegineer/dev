/*
 * AbstractStageCommand.java
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
 * Created on Aug 30, 2008 at 11:05:47 PM.
 */

package org.gamegineer.game.internal.core.commands;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;
import org.gamegineer.game.internal.core.Debug;
import org.gamegineer.game.internal.core.Stage;
import org.gamegineer.game.internal.core.StageVersion;

/**
 * Superclass for stage commands.
 * 
 * <p>
 * A stage command is a command submitted by a stage after another command has
 * completed execution for the purposes of modifying the stage state. Because
 * engine event listeners cannot modify the engine state, a new command must be
 * submitted to perform the modification.
 * </p>
 * 
 * <p>
 * Because a stage command is submitted in response to a particular view of the
 * engine state, it is very important that a stage command only be executed if
 * the stage that submitted it is still in the same state it was when it
 * originally submitted the command. Therefore, all stage commands carry with
 * them the source stage identifier and version at the time the command was
 * submitted.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public abstract class AbstractStageCommand
    extends AbstractCommand<Void>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The identifier of the stage that submitted the command. */
    private final String sourceStageId_;

    /** The version of the stage that submitted the command. */
    private final StageVersion sourceStageVersion_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStageCommand} class.
     * 
     * @param sourceStageId
     *        The identifier of the stage that submitted the command; must not
     *        be {@code null}.
     * @param sourceStageVersion
     *        The version of the stage that submitted the command; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code sourceStageId} or {@code sourceStageVersion} is {@code
     *         null}.
     */
    protected AbstractStageCommand(
        /* @NonNull */
        final String sourceStageId,
        /* @NonNull */
        final StageVersion sourceStageVersion )
    {
        assertArgumentNotNull( sourceStageId, "sourceStageId" ); //$NON-NLS-1$
        assertArgumentNotNull( sourceStageVersion, "sourceStageVersion" ); //$NON-NLS-1$

        sourceStageId_ = sourceStageId;
        sourceStageVersion_ = sourceStageVersion;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public final Void execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        if( hasSourceStageStateChanged( context.getState() ) )
        {
            if( Debug.DEFAULT )
            {
                Debug.trace( String.format( "Ignoring the command '%1$s' submitted by the stage '%2$s' (version='%3$s') because the stage is not in the same state it was when it submitted the command.", getType(), sourceStageId_, sourceStageVersion_ ) ); //$NON-NLS-1$
            }
        }
        else
        {
            executeInternal( context );
        }

        return null;
    }

    /**
     * Performs the command-specific execution logic.
     * 
     * <p>
     * Implementors may assume that the source stage is in the same state it was
     * when the command was originally submitted.
     * </p>
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of this command.
     */
    protected abstract void executeInternal(
        /* @NonNull */
        IEngineContext context )
        throws EngineException;

    /**
     * Indicates the source stage stage has changed since this command was first
     * submitted.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return {@code true} if the source stage state has changed since this
     *         command was first submitted; otherwise {@code false}.
     */
    private boolean hasSourceStageStateChanged(
        /* @NonNull */
        final IState state )
    {
        assert state != null;

        final Stage stage = Stage.getActiveStage( state, sourceStageId_ );
        if( stage == null )
        {
            return true;
        }

        if( !stage.isExecuting( state ) )
        {
            return true;
        }

        if( !stage.getVersion( state ).equals( sourceStageVersion_ ) )
        {
            return true;
        }

        return false;
    }
}
