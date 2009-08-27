/*
 * AddStateListenerCommand.java
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
 * Created on Jun 10, 2008 at 11:41:50 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractInvertibleCommand;
import org.gamegineer.engine.core.CommandBehavior;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateEventMediator;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;

/**
 * A command that adds a state listener.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@CommandBehavior( writeLockRequired = true )
@Immutable
public final class AddStateListenerCommand
    extends AbstractInvertibleCommand<Void>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state listener to add. */
    private final IStateListener m_listener;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AddStateListenerCommand} class.
     * 
     * @param listener
     *        The state listener to add; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public AddStateListenerCommand(
        /* @NonNull */
        final IStateListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        m_listener = listener;
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

        final IStateEventMediator mediator = context.getExtension( IStateEventMediator.class );
        if( mediator == null )
        {
            throw new EngineException( Messages.Common_stateEventMediatorExtension_unavailable );
        }

        mediator.addStateListener( context, m_listener );

        return null;
    }

    /*
     * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
     */
    public IInvertibleCommand<Void> getInverseCommand()
    {
        return new RemoveStateListenerCommand( m_listener );
    }
}
