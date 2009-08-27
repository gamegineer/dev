/*
 * ICommandletHelp.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Oct 24, 2008 at 10:54:33 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

/**
 * Provides help information for a commandlet.
 * 
 * <p>
 * Commandlets may optionally provide help information for display by the
 * console by implementing this interface.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface ICommandletHelp
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the commandlet detailed description.
     * 
     * @return The commandlet detailed description; never {@code null}.
     */
    /* @NonNull */
    public String getDetailedDescription();

    /**
     * Gets the commandlet synopsis.
     * 
     * @return The commandlet synopsis; never {@code null}.
     */
    /* @NonNull */
    public String getSynopsis();
}
