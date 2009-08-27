/*
 * CommandExecutingEventDelegate.java
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
 * Created on Jun 2, 2008 at 11:37:16 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutingEvent;

/**
 * An implementation of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutingEvent}
 * to which implementations of
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent}
 * can delegate their behavior.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class CommandExecutingEventDelegate
    extends CommandEventDelegate
    implements ICommandExecutingEvent
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandExecutingEventDelegate}
     * class.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     */
    CommandExecutingEventDelegate(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command )
    {
        super( context, command );
    }
}
