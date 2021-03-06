/*
 * ITable.java
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
 * Created on Oct 6, 2009 at 10:56:35 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;
import java.util.List;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.memento.IMementoOriginator;

/**
 * A virtual game table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITable
    extends IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component in this table at the specified path.
     * 
     * @param path
     *        The component path.
     * 
     * @return The component in this table at the specified path or {@code null}
     *         if no component exists at the specified path.
     */
    public @Nullable IComponent getComponent(
        ComponentPath path );

    /**
     * Gets the components in this table at the specified location.
     * 
     * <p>
     * Note that the returned components may have been moved by the time this
     * method returns to the caller. Therefore, callers should not cache the
     * results of this method for an extended period of time.
     * </p>
     * 
     * @param location
     *        The location in table coordinates.
     * 
     * @return The collection of components in this table at the specified
     *         location. The components are returned in order from the
     *         bottom-most component to the top-most component.
     */
    public List<IComponent> getComponents(
        Point location );

    /**
     * Gets the table extension of the specified type.
     * 
     * @param <T>
     *        The table extension type.
     * 
     * @param type
     *        The table extension type.
     * 
     * @return The table extension of the specified type or {@code null} if the
     *         table does not support the specified extension.
     */
    public <T> @Nullable T getExtension(
        Class<T> type );

    /**
     * Gets the table environment associated with this table.
     * 
     * @return The table environment associated with this table.
     */
    public ITableEnvironment getTableEnvironment();

    /**
     * Gets the tabletop for this table.
     * 
     * <p>
     * The tabletop represents the root component of the table.
     * </p>
     * 
     * @return The tabletop.
     */
    public IContainer getTabletop();
}
