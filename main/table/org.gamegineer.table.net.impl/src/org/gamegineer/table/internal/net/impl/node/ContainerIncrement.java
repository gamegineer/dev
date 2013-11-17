/*
 * ContainerIncrement.java
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
 * Created on Jul 9, 2011 at 10:29:55 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ContainerLayoutId;

/**
 * An incremental change to the state of a container.
 */
@NotThreadSafe
public final class ContainerIncrement
    extends ComponentIncrement
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8032659310899173988L;

    /**
     * The index of the first component added to the container or {@code null}
     * if no components were added.
     * 
     * @serial The index of the first component added to the container.
     */
    private Integer addedComponentIndex_;

    /**
     * The collection of mementos representing the components added to the
     * container ordered from bottom to top or {@code null} if no components
     * were added.
     * 
     * @serial The collection of mementos representing the components added to
     *         the container.
     */
    private List<Object> addedComponentMementos_;

    /**
     * The new container layout identifier or {@code null} if unchanged.
     * 
     * @serial The new container layout identifier.
     */
    private ContainerLayoutId layoutId_;

    /**
     * The count of components removed from the container or {@code null} if no
     * components were removed.
     * 
     * @serial The count of components removed from the top of the container.
     */
    private Integer removedComponentCount_;

    /**
     * The index of the first component removed from the container or
     * {@code null} if no components were removed.
     * 
     * @serial The index of the first component removed from the container.
     */
    private Integer removedComponentIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerIncrement} class.
     */
    public ContainerIncrement()
    {
        addedComponentIndex_ = null;
        addedComponentMementos_ = null;
        layoutId_ = null;
        removedComponentCount_ = null;
        removedComponentIndex_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the index of the first component added to the container.
     * 
     * @return The index of the first component added to the container or
     *         {@code null} if no components were added.
     */
    /* @Nullable */
    public Integer getAddedComponentIndex()
    {
        return addedComponentIndex_;
    }

    /**
     * Gets the collection of mementos representing the components added to the
     * container.
     * 
     * @return The collection of mementos representing the components added to
     *         the container ordered from bottom to top or {@code null} if no
     *         components were added. The returned value is not a copy and must
     *         not be modified.
     */
    /* @Nullable */
    public List<Object> getAddedComponentMementos()
    {
        return addedComponentMementos_;
    }

    /**
     * Gets the new container layout identifier.
     * 
     * @return The new container layout identifier or {@code null} if unchanged.
     */
    /* @Nullable */
    public ContainerLayoutId getLayoutId()
    {
        return layoutId_;
    }

    /**
     * Gets the count of components removed from the container.
     * 
     * @return The count of components removed from the container or
     *         {@code null} if no components were removed.
     */
    /* @Nullable */
    public Integer getRemovedComponentCount()
    {
        return removedComponentCount_;
    }

    /**
     * Gets the index of the first component removed from the container.
     * 
     * @return The index of the first component removed from the container or
     *         {@code null} if no components were removed.
     */
    /* @Nullable */
    public Integer getRemovedComponentIndex()
    {
        return removedComponentIndex_;
    }

    /**
     * Sets the index of the first component added to the container.
     * 
     * @param addedComponentIndex
     *        The index of the first component added to the container or
     *        {@code null} if no components were added.
     */
    public void setAddedComponentIndex(
        /* @Nullable */
        final Integer addedComponentIndex )
    {
        addedComponentIndex_ = addedComponentIndex;
    }

    /**
     * Sets the collection of mementos representing the components added to the
     * container.
     * 
     * @param addedComponentMementos
     *        The collection of mementos representing the components added to
     *        the container ordered from bottom to top or {@code null} if no
     *        components were added. No copy is made of the specified value and
     *        it must not be modified after calling this method.
     */
    public void setAddedComponentMementos(
        /* @Nullable */
        final List<Object> addedComponentMementos )
    {
        addedComponentMementos_ = addedComponentMementos;
    }

    /**
     * Sets the new container layout identifier.
     * 
     * @param layoutId
     *        The new container layout identifier or {@code null} if unchanged.
     */
    public void setLayoutId(
        /* @Nullable */
        final ContainerLayoutId layoutId )
    {
        layoutId_ = layoutId;
    }

    /**
     * Sets the count of components removed from the container.
     * 
     * @param removedComponentCount
     *        The count of components removed from the container or {@code null}
     *        if no components were removed.
     */
    public void setRemovedComponentCount(
        /* @Nullable */
        final Integer removedComponentCount )
    {
        removedComponentCount_ = removedComponentCount;
    }

    /**
     * Sets the index of the first component removed from the container.
     * 
     * @param removedComponentIndex
     *        The index of the first component removed from the container or
     *        {@code null} if no components were removed.
     */
    public void setRemovedComponentIndex(
        /* @Nullable */
        final Integer removedComponentIndex )
    {
        removedComponentIndex_ = removedComponentIndex;
    }
}
