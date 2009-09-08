/*
 * CommandEventDelegate.java
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
 * Created on Jun 2, 2008 at 11:30:41 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent;
import org.gamegineer.engine.internal.core.ImmutableEngineContext;

/**
 * An implementation of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent}
 * to which implementations of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandEvent}
 * can delegate their behavior.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
class CommandEventDelegate
    implements ICommandEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command associated with the event. */
    private final ICommand<?> command_;

    /** The engine context. */
    private final IEngineContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandEventDelegate} class.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     */
    CommandEventDelegate(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command )
    {
        assert context != null;
        assert command != null;

        context_ = new ImmutableEngineContext( context );
        command_ = command;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getCommand()
     */
    public final ICommand<?> getCommand()
    {
        return command_;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getEngineContext()
     */
    public final IEngineContext getEngineContext()
    {
        return context_;
    }
}
