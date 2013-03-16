/*
 * ITable.java
 * Copyright 2008-2013 Gamegineer.org
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
import org.gamegineer.common.core.util.memento.IMementoOriginator;

/**
 * A virtual game table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITable
    extends IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Begins a drag-and-drop operation.
     * 
     * @param location
     *        The beginning drag location in table coordinates; must not be
     *        {@code null}.
     * @param component
     *        The component from which the drag-and-drop operation will begin;
     *        must not be {@code null}.
     * 
     * @return A context defining the new drag-and-drop operation or
     *         {@code null} if a drag-and-drop operation is not possible for the
     *         specified arguments.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} does not exist in this table or
     *         {@code component} has no container (i.e. {@code component} is the
     *         tabletop).
     * @throws java.lang.IllegalStateException
     *         If there is an active drag-and-drop operation.
     * @throws java.lang.NullPointerException
     *         If {@code location} or {@code component} is {@code null}.
     */
    /* @Nullable */
    public IDragContext beginDrag(
        /* @NonNull */
        Point location,
        /* @NonNull */
        IComponent component );

    /**
     * Gets the component in this table at the specified path.
     * 
     * @param path
     *        The component path; must not be {@code null}.
     * 
     * @return The component in this table at the specified path; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If no component exists at the specified path.
     * @throws java.lang.NullPointerException
     *         If {@code path} is {@code null}.
     */
    /* @NonNull */
    public IComponent getComponent(
        /* @NonNull */
        ComponentPath path );

    /**
     * Gets the top-most component in this table at the specified location.
     * 
     * <p>
     * Note that the returned component may have been moved by the time this
     * method returns to the caller. Therefore, callers should not cache the
     * results of this method for an extended period of time.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The top-most component in this table at the specified location or
     *         {@code null} if no component in this table is at that location.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @Nullable */
    public IComponent getComponent(
        /* @NonNull */
        Point location );

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
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The collection of components in this table at the specified
     *         location; never {@code null}. The components are returned in
     *         order from the bottom-most component to the top-most component;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @NonNull */
    public List<IComponent> getComponents(
        /* @NonNull */
        Point location );

    /**
     * Gets the table extension of the specified type.
     * 
     * @param <T>
     *        The table extension type.
     * 
     * @param type
     *        The table extension type; must not be {@code null}.
     * 
     * @return The table extension of the specified type or {@code null} if the
     *         table does not support the specified extension.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    /* @Nullable */
    public <T> T getExtension(
        /* @NonNull */
        Class<T> type );

    /**
     * Gets the table environment associated with this table.
     * 
     * @return The table environment associated with this table; never
     *         {@code null}.
     */
    /* @NonNull */
    public ITableEnvironment getTableEnvironment();

    /**
     * Gets the tabletop for this table.
     * 
     * <p>
     * The tabletop represents the root component of the table.
     * </p>
     * 
     * @return The tabletop; never {@code null}.
     */
    /* @NonNull */
    public IContainer getTabletop();
}
