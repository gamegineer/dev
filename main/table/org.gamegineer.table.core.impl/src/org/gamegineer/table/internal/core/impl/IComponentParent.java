/*
 * IComponentParent.java
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
 * Created on Jul 25, 2012 at 8:12:24 PM.
 */

package org.gamegineer.table.internal.core.impl;

import org.gamegineer.table.core.ComponentPath;

/**
 * A component parent.
 */
interface IComponentParent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the path to the specified child component from its associated table.
     * 
     * @param component
     *        The child component; must not be {@code null}.
     * 
     * @return The path to the specified child component from its associated
     *         table or {@code null} if this component parent is not associated
     *         with a table.
     */
    /* @NonNull */
    ComponentPath getChildPath(
        /* @NonNull */
        Component component );

    /**
     * Gets the table associated with this component parent.
     * 
     * @return The table associated with the component parent or {@code null} if
     *         this component parent is not associated with a table.
     */
    /* @Nullable */
    Table getTable();
}
