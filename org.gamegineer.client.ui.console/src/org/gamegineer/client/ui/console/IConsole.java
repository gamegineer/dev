/*
 * IConsole.java
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
 * Created on Oct 5, 2008 at 10:23:45 PM.
 */

package org.gamegineer.client.ui.console;

/**
 * A console.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IConsole
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes this console.
     * 
     * <p>
     * The console will close after the current commandlet has completed
     * execution. Calling this method multiple times has no additional effect.
     * </p>
     */
    public void close();

    /**
     * Gets the display for this console.
     * 
     * @return The display for this console; never {@code null}.
     */
    /* @NonNull */
    public IDisplay getDisplay();
}
