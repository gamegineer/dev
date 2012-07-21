/*
 * IContainer.java
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
 * Created on Mar 26, 2012 at 8:05:53 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;
import java.util.List;

/**
 * A table component that can contain other table components.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IContainer
    extends IComponent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified component to the top of this container.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} is already contained in a container or if
     *         {@code component} was created by a table environment other than
     *         the table environment that created this container.
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    public void addComponent(
        /* @NonNull */
        IComponent component );

    /**
     * Adds the specified collection of components to the top of this container.
     * 
     * @param components
     *        The collection of components to be added to this container; must
     *        not be {@code null}. The components are added to the top of this
     *        container in the order they appear in the collection.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code components} contains a {@code null} element; if any
     *         component is already contained in a container; or if any
     *         component was created by a table environment other than the table
     *         environment that created this container.
     * @throws java.lang.NullPointerException
     *         If {@code components} is {@code null}.
     */
    public void addComponents(
        /* @NonNull */
        List<IComponent> components );

    /**
     * Adds the specified container listener to this container.
     * 
     * @param listener
     *        The container listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered container listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addContainerListener(
        /* @NonNull */
        IContainerListener listener );

    /**
     * Gets the component in this container at the specified index.
     * 
     * @param index
     *        The component index.
     * 
     * @return The component in this container at the specified index; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is less than zero or greater than or equal to
     *         the component count.
     */
    /* @NonNull */
    public IComponent getComponent(
        int index );

    /**
     * Gets the count of components in this container.
     * 
     * @return The count of components in this container.
     */
    public int getComponentCount();

    /**
     * Gets the collection of components in this container.
     * 
     * @return The collection of components in this container; never
     *         {@code null}. The components are returned in order from the
     *         component at the bottom of the container to the component at the
     *         top of the container.
     */
    /* @NonNull */
    public List<IComponent> getComponents();

    /**
     * Gets the layout of components in this container.
     * 
     * @return The layout of components in this container; never {@code null}.
     */
    /* @NonNull */
    public IContainerLayout getLayout();

    /**
     * Removes the specified component from this container.
     * 
     * @param component
     *        The component to remove; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} is not contained within this container.
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    public void removeComponent(
        /* @NonNull */
        IComponent component );

    /**
     * Removes all components in this container.
     * 
     * @return The collection of components removed from this container; never
     *         {@code null}. The components are returned in order from the
     *         component at the bottom of the container to the component at the
     *         top of the container.
     */
    /* @NonNull */
    public List<IComponent> removeComponents();

    /**
     * Removes all components in this container from the component at the
     * specified location to the top-most component.
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The collection of components removed from this container; never
     *         {@code null}. The components are returned in order from the
     *         component at the specified location to the component at the top
     *         of the container.
     */
    /* @NonNull */
    public List<IComponent> removeComponents(
        /* @NonNull */
        Point location );

    /**
     * Removes the specified container listener from this container.
     * 
     * @param listener
     *        The container listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered container listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeContainerListener(
        /* @NonNull */
        IContainerListener listener );

    /**
     * Removes the component at the top of this container.
     * 
     * @return The component that was removed or {@code null} if this container
     *         is empty.
     */
    /* @Nullable */
    public IComponent removeTopComponent();

    /**
     * Sets the layout of components in this container.
     * 
     * @param layout
     *        The layout of components in this container; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code layout} is {@code null}.
     */
    public void setLayout(
        /* @NonNull */
        IContainerLayout layout );
}
