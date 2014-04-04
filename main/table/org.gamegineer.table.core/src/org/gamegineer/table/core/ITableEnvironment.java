/*
 * ITableEnvironment.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on May 19, 2012 at 9:21:05 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.common.core.util.memento.MementoException;

/**
 * An environment for the execution of virtual game tables.
 * 
 * <p>
 * A table environment is responsible for the creation and management of tables
 * and their components. A table environment instance is isolated from all other
 * table environment instances even if the table environments share the same
 * implementation. Therefore, a table or component created by one table
 * environment must not be used by a different table environment. Conversely, a
 * component created by a table environment may be used by any table created by
 * the same table environment.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableEnvironment
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component from the specified memento.
     * 
     * @param memento
     *        The memento that represents the state of the component; must not
     *        be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento is unknown or malformed.
     */
    public IComponent createComponent(
        Object memento )
        throws MementoException;

    /**
     * Creates a new component using the specified strategy.
     * 
     * @param strategy
     *        The component strategy; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     */
    public IComponent createComponent(
        IComponentStrategy strategy );

    /**
     * Creates a new container using the specified strategy.
     * 
     * @param strategy
     *        The container strategy; must not be {@code null}.
     * 
     * @return A new container; never {@code null}.
     */
    public IContainer createContainer(
        IContainerStrategy strategy );

    /**
     * Creates a new table.
     * 
     * @return A new table; never {@code null}.
     */
    public ITable createTable();

    /**
     * Gets the table environment lock.
     * 
     * <p>
     * Any modification to a table or table component must be executed while the
     * table environment lock is held. All public methods of all public types in
     * this package will acquire the table environment lock as needed. Clients
     * must manually acquire the table environment lock when invoking multiple
     * methods that should be treated as an atomic operation.
     * </p>
     * 
     * @return The table environment lock; never {@code null}.
     */
    public ITableEnvironmentLock getLock();
}
