/*
 * SetMementoCommand.java
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
 * Created on Jul 3, 2008 at 9:47:05 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.statemanager.IStateManager;
import org.gamegineer.engine.internal.core.PhantomCommand;

/**
 * A command that sets the engine state using a memento.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@PhantomCommand
public final class SetMementoCommand
    extends AbstractCommand<Void>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The memento. */
    private final IMemento memento_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SetMementoCommand} class.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     */
    public SetMementoCommand(
        /* @NonNull */
        final IMemento memento )
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        memento_ = memento;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public Void execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IStateManager manager = context.getExtension( IStateManager.class );
        if( manager == null )
        {
            throw new EngineException( Messages.Common_stateManagerExtension_unavailable );
        }

        manager.setMemento( context, memento_ );

        return null;
    }
}
