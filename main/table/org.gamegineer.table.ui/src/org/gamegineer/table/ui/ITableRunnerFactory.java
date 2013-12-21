/*
 * ITableRunnerFactory.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Dec 20, 2013 at 9:17:35 PM.
 */

package org.gamegineer.table.ui;

/**
 * A factory for creating table user interface runners.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableRunnerFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table user interface and returns an object capable of
     * running it.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @return An object capable of running the table user interface; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    /* @NonNull */
    public ITableRunner createTableRunner(
        /* @NonNull */
        ITableAdvisor advisor );
}
