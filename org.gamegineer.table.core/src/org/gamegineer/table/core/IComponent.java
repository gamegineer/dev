/*
 * IComponent.java
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
 * Created on Mar 26, 2012 at 8:05:47 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
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
     * Gets the size of this component in table coordinates.
     * 
     * @return The size of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Dimension getSize();

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
    public IComponentSurfaceDesign getSurfaceDesign(
        /* @NonNull */
        ComponentOrientation orientation );

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
     * Sets the orientation of this card.
     * 
     * @param orientation
     *        The orientation of this card; must not be {@code null}.
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
        IComponentSurfaceDesign surfaceDesign );
}
