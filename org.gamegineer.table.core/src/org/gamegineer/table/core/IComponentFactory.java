/*
 * IComponentFactory.java
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
 * Created on Oct 1, 2012 at 7:38:38 PM.
 */

package org.gamegineer.table.core;

/**
 * A factory for creating table components.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IComponentFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component for the specified table environment.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     * @throws org.gamegineer.table.core.ComponentFactoryException
     *         If the component cannot be created.
     */
    /* @NonNull */
    public IComponent createComponent(
        /* @NonNull */
        ITableEnvironment tableEnvironment )
        throws ComponentFactoryException;
}
