/*
 * IHelpSystem.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jan 4, 2012 at 8:06:42 PM.
 */

package org.gamegineer.common.ui.help;

/**
 * Provides access to the platform help system.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IHelpSystem
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Displays the help viewer.
     * 
     * @param activationObject
     *        The activation object for the help viewer.
     */
    public void displayHelp(
        Object activationObject );

    /**
     * Shuts down the help system.
     * 
     * <p>
     * This method should be called immediately before exiting the application.
     * </p>
     */
    public void shutdown();
}
