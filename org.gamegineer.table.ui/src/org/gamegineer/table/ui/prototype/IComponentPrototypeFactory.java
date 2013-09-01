/*
 * IComponentPrototypeFactory.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Oct 25, 2012 at 10:29:54 PM.
 */

package org.gamegineer.table.ui.prototype;

import java.util.List;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironment;

/**
 * A factory for creating a table component prototypes.
 * 
 * <p>
 * A component prototype is a collection of one or more pre-configured
 * components that represents a commonly-used paradigm on a table.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IComponentPrototypeFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component prototype for the specified table environment.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * 
     * @return A new component prototype; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     * @throws org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException
     *         If the component prototype cannot be created.
     */
    /* @NonNull */
    public List<IComponent> createComponentPrototype(
        /* @NonNull */
        ITableEnvironment tableEnvironment )
        throws ComponentPrototypeFactoryException;
}
