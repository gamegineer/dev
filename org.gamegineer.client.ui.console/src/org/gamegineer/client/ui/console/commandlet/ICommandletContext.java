/*
 * ICommandletContext.java
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
 * Created on Oct 3, 2008 at 8:37:28 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import java.util.List;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;

/**
 * The context within which a commandlet is executed.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ICommandletContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an immutable view of the commandlet arguments.
     * 
     * @return An immutable view of the commandlet arguments; never {@code null}.
     */
    /* @NonNull */
    public List<String> getArguments();

    /**
     * Gets the console from which the commandlet was executed.
     * 
     * @return The console from which the commandlet was executed; never
     *         {@code null}.
     */
    /* @NonNull */
    public IConsole getConsole();

    /**
     * Gets the game client against which the commandlet is executed.
     * 
     * @return The game client against which the commandlet is executed; never
     *         {@code null}.
     */
    /* @NonNull */
    public IGameClient getGameClient();

    /**
     * Gets the console statelet.
     * 
     * @return The console statelet; never {@code null}.
     */
    /* @NonNull */
    public IStatelet getStatelet();
}
