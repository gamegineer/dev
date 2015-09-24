/*
 * ITableEnvironmentFactory.java
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
 * Created on Oct 1, 2013 at 8:15:41 PM.
 */

package org.gamegineer.table.core;

/**
 * A factory for creating table environments.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableEnvironmentFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table environment.
     * 
     * @param context
     *        The table environment context.
     * 
     * @return A new table environment.
     */
    public ITableEnvironment createTableEnvironment(
        ITableEnvironmentContext context );
}
