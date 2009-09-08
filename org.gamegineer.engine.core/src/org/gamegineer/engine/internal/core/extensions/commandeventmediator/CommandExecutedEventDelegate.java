/*
 * CommandExecutedEventDelegate.java
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
 * Created on Jun 2, 2008 at 11:47:04 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent;

/**
 * An implementation of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent}
 * to which implementations of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent}
 * can delegate their behavior.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class CommandExecutedEventDelegate
    extends CommandEventDelegate
    implements ICommandExecutedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The exception thrown by the command during a failed execution. */
    private final Exception exception_;

    /** The result returned by the command after a successful execution. */
    private final Object result_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandExecutedEventDelegate}
     * class.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param result
     *        The result of the command execution; may be {@code null}.
     * @param exception
     *        The exception thrown during command execution if applicable; may
     *        be {@code null}.
     */
    private CommandExecutedEventDelegate(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @Nullable */
        final Object result,
        /* @Nullable */
        final Exception exception )
    {
        super( context, command );

        result_ = result;
        exception_ = exception;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code CommandExecutedEventDelegate} class
     * for a failed command execution.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param exception
     *        The exception thrown during command execution; must not be {@code
     *        null}.
     * 
     * @return A new instance of the {@code CommandExecutedEventDelegate} class
     *         for a failed command execution.
     */
    static CommandExecutedEventDelegate createFailedCommandExecutedEventDelegate(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @NonNull */
        final Exception exception )
    {
        assert exception != null;

        return new CommandExecutedEventDelegate( context, command, null, exception );
    }

    /**
     * Creates a new instance of the {@code CommandExecutedEventDelegate} class
     * for a successful command execution.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param result
     *        The result of the command execution; may be {@code null}.
     * 
     * @return A new instance of the {@code CommandExecutedEventDelegate} class
     *         for a successful command execution.
     */
    static CommandExecutedEventDelegate createSuccessfulCommandExecutedEventDelegate(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @Nullable */
        final Object result )
    {
        return new CommandExecutedEventDelegate( context, command, result, null );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#getException()
     */
    public Exception getException()
    {
        assertStateLegal( exception_ != null, Messages.CommandExecutedEventDelegate_getException_noException );

        return exception_;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#getResult()
     */
    public Object getResult()
    {
        assertStateLegal( exception_ == null, Messages.CommandExecutedEventDelegate_getResult_noResult );

        return result_;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#hasException()
     */
    public boolean hasException()
    {
        return exception_ != null;
    }
}
