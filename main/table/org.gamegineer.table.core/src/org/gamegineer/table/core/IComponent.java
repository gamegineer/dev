/*
 * IComponent.java
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
 * Created on Mar 26, 2012 at 8:05:47 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Map;
import org.gamegineer.common.core.util.memento.IMementoOriginator;

/**
 * A table component.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IComponent
    extends IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified component listener to this component.
     * 
     * @param listener
     *        The component listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered component listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addComponentListener(
        /* @NonNull */
        IComponentListener listener );

    /**
     * Gets the bounds of this component in table coordinates.
     * 
     * @return The bounds of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Rectangle getBounds();

    /**
     * Gets the container that contains this component.
     * 
     * @return The container that contains this component or {@code null} if
     *         this component is not contained in a container.
     */
    /* @Nullable */
    public IContainer getContainer();

    /**
     * Gets the location of this component in table coordinates.
     * 
     * <p>
     * The location represents the position of the top-left corner of the
     * component.
     * </p>
     * 
     * @return The location of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Point getLocation();

    /**
     * Gets the orientation of this component.
     * 
     * @return The orientation of this component; never {@code null}.
     */
    /* @NonNull */
    public ComponentOrientation getOrientation();

    /**
     * Gets the origin of this component in table coordinates.
     * 
     * <p>
     * The origin represents the position that remains unchanged when the
     * component orientation (or some other attribute that affects the component
     * size) is changed. The origin may or may not occupy the same position as
     * the location.
     * </p>
     * 
     * @return The origin of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Point getOrigin();

    /**
     * Gets the path to this component from its associated table.
     * 
     * @return The path to this component from its associated table or
     *         {@code null} if the component is not associated with a table.
     */
    /* @Nullable */
    public ComponentPath getPath();

    /**
     * Gets the size of this component in table coordinates.
     * 
     * @return The size of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Dimension getSize();

    /**
     * Gets the component strategy.
     * 
     * @return The component strategy; never {@code null}.
     */
    /* @NonNull */
    public IComponentStrategy getStrategy();

    /**
     * Gets an immutable view of the collection of supported orientations for
     * this component.
     * 
     * @return An immutable view of the collection of supported orientations for
     *         this component; never {@code null}. The returned collection is
     *         guaranteed to not be empty.
     */
    /* @NonNull */
    public Collection<ComponentOrientation> getSupportedOrientations();

    /**
     * Gets the design on the surface of this component associated with the
     * specified orientation.
     * 
     * @param orientation
     *        The surface orientation; must not be {@code null}.
     * 
     * @return The design on the surface of this component associated with the
     *         specified orientation; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code orientation} is not a legal orientation for this
     *         component.
     * @throws java.lang.NullPointerException
     *         If {@code orientation} is {@code null}.
     */
    /* @NonNull */
    public ComponentSurfaceDesign getSurfaceDesign(
        /* @NonNull */
        ComponentOrientation orientation );

    /**
     * Gets the collection of surface designs for this component.
     * 
     * @return The collection of surface designs for this component; never
     *         {@code null}.
     */
    /* @NonNull */
    public Map<ComponentOrientation, ComponentSurfaceDesign> getSurfaceDesigns();

    /**
     * Gets the table associated with this component.
     * 
     * @return The table associated with this component or {@code null} if this
     *         component is not associated with a table.
     */
    /* @Nullable */
    public ITable getTable();

    /**
     * Gets the table environment associated with this component.
     * 
     * @return The table environment associated with this component; never
     *         {@code null}.
     */
    /* @NonNull */
    public ITableEnvironment getTableEnvironment();

    /**
     * Removes the specified component listener from this component.
     * 
     * @param listener
     *        The component listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered component listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeComponentListener(
        /* @NonNull */
        IComponentListener listener );

    /**
     * Sets the location of this component in table coordinates.
     * 
     * @param location
     *        The location of this component in table coordinates; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    public void setLocation(
        /* @NonNull */
        Point location );

    /**
     * Sets the orientation of this component.
     * 
     * @param orientation
     *        The orientation of this component; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code orientation} is not a legal orientation for this
     *         component.
     * @throws java.lang.NullPointerException
     *         If {@code orientation} is {@code null}.
     */
    public void setOrientation(
        /* @NonNull */
        ComponentOrientation orientation );

    /**
     * Sets the origin of this component in table coordinates.
     * 
     * @param origin
     *        The origin of this component in table coordinates; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code origin} is {@code null}.
     */
    public void setOrigin(
        /* @NonNull */
        Point origin );

    /**
     * Sets the design on the surface of this component associated with the
     * specified orientation.
     * 
     * @param orientation
     *        The surface orientation; must not be {@code null}.
     * @param surfaceDesign
     *        The design on the surface of this component associated with the
     *        specified orientation; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code orientation} is not a legal orientation for this
     *         component.
     * @throws java.lang.NullPointerException
     *         If {@code orientation} or {@code surfaceDesign} is {@code null}.
     */
    public void setSurfaceDesign(
        /* @NonNull */
        ComponentOrientation orientation,
        /* @NonNull */
        ComponentSurfaceDesign surfaceDesign );

    /**
     * Sets the collection of surface designs for this component.
     * 
     * @param surfaceDesigns
     *        The collection of surface designs; must not be {@code null}. The
     *        collection may contain a subset of the supported component surface
     *        designs.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code surfaceDesigns} contains an illegal orientation for
     *         this component or contains a {@code null} surface design.
     * @throws java.lang.NullPointerException
     *         If {@code surfaceDesigns} is {@code null}.
     */
    public void setSurfaceDesigns(
        /* @NonNull */
        Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns );
}