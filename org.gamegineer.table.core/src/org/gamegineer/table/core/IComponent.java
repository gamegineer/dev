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
     * @return The location of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Point getLocation();

    /**
     * Gets the size of this component in table coordinates.
     * 
     * @return The size of this component in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Dimension getSize();

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
}
