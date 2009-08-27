/*
 * ActivateStageCommand.java
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
 * Created on Aug 18, 2008 at 9:00:58 PM.
 */

package org.gamegineer.game.internal.core.commands;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.CommandBehavior;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.internal.core.GameAttributes;
import org.gamegineer.game.internal.core.StageVersion;

/**
 * A command that activates the next appropriate stage.
 * 
 * <p>
 * A stage submits this command to trigger the activation of the next
 * appropriate stage. If this command is received when the originating stage is
 * no longer the currently executing stage or is not at the same change level
 * when it submitted the command, it will be silently ignored.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@CommandBehavior( writeLockRequired = true )
@Immutable
public final class ActivateStageCommand
    extends AbstractStageCommand
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActivateStageCommand} class.
     * 
     * @param sourceStageId
     *        The identifier of the stage that submitted the command; must not
     *        be {@code null}.
     * @param sourceStageVersion
     *        The version of the stage that submitted the command; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code sourceStageId} or {@code sourceStageVersion} is
     *         {@code null}.
     */
    public ActivateStageCommand(
        /* @NonNull */
        final String sourceStageId,
        /* @NonNull */
        final StageVersion sourceStageVersion )
    {
        super( sourceStageId, sourceStageVersion );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.internal.core.commands.AbstractStageCommand#executeInternal(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected void executeInternal(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        GameAttributes.ROOT_STAGE.getValue( context.getState() ).activate( context );
    }
}
