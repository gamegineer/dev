/*
 * InternalCommandExecutingEvent.java
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
 * Created on May 9, 2008 at 10:01:28 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutingEvent;

/**
 * Implementation of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent}
 * .
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class InternalCommandExecutingEvent
    extends CommandExecutingEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -2315929388342713067L;

    /**
     * The command executing event implementation to which all behavior is
     * delegated.
     */
    private final ICommandExecutingEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCommandExecutingEvent}
     * class.
     * 
     * @param delegate
     *        The command executing event implementation to which all behavior
     *        is delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalCommandExecutingEvent(
        /* @NonNull */
        final ICommandExecutingEvent delegate )
    {
        super( delegate.getEngineContext() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalCommandExecutingEvent}
     * class.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalCommandExecutingEvent}
     *         class.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} is {@code null}.
     */
    static InternalCommandExecutingEvent createCommandExecutingEvent(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command )
    {
        assertArgumentLegal( context != null, "context" ); //$NON-NLS-1$
        assert command != null;

        return new InternalCommandExecutingEvent( new CommandExecutingEventDelegate( context, command ) );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getCommand()
     */
    public ICommand<?> getCommand()
    {
        return delegate_.getCommand();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getEngineContext()
     */
    public IEngineContext getEngineContext()
    {
        return delegate_.getEngineContext();
    }
}
