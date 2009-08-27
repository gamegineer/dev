/*
 * UndoCommand.java
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
 * Created on Apr 26, 2008 at 10:22:59 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractInvertibleCommand;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;
import org.gamegineer.engine.internal.core.PhantomCommand;

/**
 * A command that executes the inverse of the most recent command in the command
 * history and moves it from the command history to the undo history.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@PhantomCommand
public final class UndoCommand
    extends AbstractInvertibleCommand<Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code UndoCommand} class.
     */
    public UndoCommand()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public Void execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final ICommandHistory history = context.getExtension( ICommandHistory.class );
        if( history == null )
        {
            throw new EngineException( Messages.Common_commandHistoryExtension_unavailable );
        }

        history.undo( context );
        return null;
    }

    /*
     * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
     */
    public IInvertibleCommand<Void> getInverseCommand()
    {
        return new RedoCommand();
    }
}