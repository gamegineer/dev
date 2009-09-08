/*
 * InternalCommandExecutedEvent.java
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
 * Created on May 9, 2008 at 9:02:23 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent;

/**
 * Implementation of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent}
 * .
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class InternalCommandExecutedEvent
    extends CommandExecutedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5066204946309030716L;

    /**
     * The command executed event implementation to which all behavior is
     * delegated.
     */
    private final ICommandExecutedEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCommandExecutedEvent}
     * class.
     * 
     * @param delegate
     *        The command executed event implementation to which all behavior is
     *        delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalCommandExecutedEvent(
        /* @NonNull */
        final ICommandExecutedEvent delegate )
    {
        super( delegate.getEngineContext() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalCommandExecutedEvent} class
     * for a failed command execution.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param exception
     *        The exception thrown during command execution; must not be {@code
     *        null}.
     * 
     * @return A new instance of the {@code InternalCommandExecutedEvent} class
     *         for a failed command execution.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} is {@code null}.
     */
    static InternalCommandExecutedEvent createFailedCommandExecutedEvent(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @NonNull */
        final Exception exception )
    {
        assertArgumentLegal( context != null, "context" ); //$NON-NLS-1$
        assert command != null;
        assert exception != null;

        return new InternalCommandExecutedEvent( CommandExecutedEventDelegate.createFailedCommandExecutedEventDelegate( context, command, exception ) );
    }

    /**
     * Creates a new instance of the {@code InternalCommandExecutedEvent} class
     * for a successful command execution.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param result
     *        The result of the command execution; may be {@code null}.
     * 
     * @return A new instance of the {@code InternalCommandExecutedEvent} class
     *         for a successful command execution.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} is {@code null}.
     */
    static InternalCommandExecutedEvent createSuccessfulCommandExecutedEvent(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @Nullable */
        final Object result )
    {
        assertArgumentLegal( context != null, "context" ); //$NON-NLS-1$
        assert command != null;

        return new InternalCommandExecutedEvent( CommandExecutedEventDelegate.createSuccessfulCommandExecutedEventDelegate( context, command, result ) );
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

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#getException()
     */
    public Exception getException()
    {
        return delegate_.getException();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#getResult()
     */
    public Object getResult()
    {
        return delegate_.getResult();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#hasException()
     */
    public boolean hasException()
    {
        return delegate_.hasException();
    }
}
