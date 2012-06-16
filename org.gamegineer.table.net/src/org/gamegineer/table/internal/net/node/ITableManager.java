/*
 * ITableManager.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jun 27, 2011 at 7:51:16 PM.
 */

package org.gamegineer.table.internal.net.node;

import org.gamegineer.table.core.ComponentPath;

/**
 * Manages the local and remote tables connected to a node on the table network
 * to ensure they remain synchronized.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableManager
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Increments the state of the component at the specified path throughout
     * the table network
     * 
     * @param sourceTable
     *        The table that originated the request; must not be {@code null}.
     * @param componentPath
     *        The component path; must not be {@code null}.
     * @param componentIncrement
     *        The incremental change to the state of the component; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code sourceTable}, {@code componentPath}, or
     *         {@code componentIncrement} is {@code null}.
     */
    public void incrementComponentState(
        /* @NonNull */
        INetworkTable sourceTable,
        /* @NonNull */
        ComponentPath componentPath,
        /* @NonNull */
        ComponentIncrement componentIncrement );

    /**
     * Increments the state of the table throughout the table network
     * 
     * @param sourceTable
     *        The table that originated the request; must not be {@code null}.
     * @param tableIncrement
     *        The incremental change to the state of the table; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code sourceTable} or {@code tableIncrement} is {@code null}.
     */
    public void incrementTableState(
        /* @NonNull */
        INetworkTable sourceTable,
        /* @NonNull */
        TableIncrement tableIncrement );

    /**
     * Sets the state of the table throughout the table network.
     * 
     * @param sourceTable
     *        The table that originated the request; must not be {@code null}.
     * @param tableMemento
     *        The memento containing the table state; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code sourceTable} or {@code tableMemento} is {@code null}.
     */
    public void setTableState(
        /* @NonNull */
        INetworkTable sourceTable,
        /* @NonNull */
        Object tableMemento );
}
