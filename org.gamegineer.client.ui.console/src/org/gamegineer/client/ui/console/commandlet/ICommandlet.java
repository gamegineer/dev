/*
 * ICommandlet.java
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
 * Created on Oct 2, 2008 at 11:10:05 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

/**
 * A commandlet that can be executed from a console.
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface ICommandlet
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Executes this commandlet.
     * 
     * @param context
     *        The commandlet context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.client.ui.console.commandlet.CommandletException
     *         If an error occurs during the execution of the commandlet.
     */
    public void execute(
        /* @NonNull */
        ICommandletContext context )
        throws CommandletException;
}
