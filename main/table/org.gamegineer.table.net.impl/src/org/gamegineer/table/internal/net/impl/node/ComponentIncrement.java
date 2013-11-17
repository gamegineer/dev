/*
 * ComponentIncrement.java
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
 * Created on Jul 9, 2011 at 10:03:31 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import java.awt.Point;
import java.io.Serializable;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * An incremental change to the state of a component.
 */
@NotThreadSafe
public class ComponentIncrement
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 9216681710456705624L;

    /**
     * The new component location or {@code null} if unchanged.
     * 
     * @serial The new component location.
     */
    private Point location_;

    /**
     * The new component orientation or {@code null} if unchanged.
     * 
     * @serial The new component orientation.
     */
    private ComponentOrientation orientation_;

    /**
     * The collection of new component surface design identifiers or
     * {@code null} if unchanged. The key is the component orientation. The
     * value is the new component surface design identifier.
     * 
     * @serial The collection of new component surface design identifiers.
     */
    private Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentIncrement} class.
     */
    public ComponentIncrement()
    {
        location_ = null;
        orientation_ = null;
        surfaceDesignIds_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the new component location.
     * 
     * @return The new component location or {@code null} if the component
     *         location is unchanged. The returned value is not a copy and must
     *         not be modified.
     */
    /* @Nullable */
    public final Point getLocation()
    {
        return location_;
    }

    /**
     * Gets the new component orientation.
     * 
     * @return The new component orientation or {@code null} if the component
     *         orientation is unchanged.
     */
    /* @Nullable */
    public final ComponentOrientation getOrientation()
    {
        return orientation_;
    }

    /**
     * Gets the collection of new component surface design identifiers.
     * 
     * @return The collection of new component surface design identifiers or
     *         {@code null} if the component surface design identifiers are
     *         unchanged. The key is the component orientation. The value is the
     *         new component surface design identifier. The returned value is
     *         not a copy and must not be modified.
     */
    /* @Nullable */
    public final Map<ComponentOrientation, ComponentSurfaceDesignId> getSurfaceDesignIds()
    {
        return surfaceDesignIds_;
    }

    /**
     * Sets the new component location.
     * 
     * @param location
     *        The component location or {@code null} if the component location
     *        is unchanged. No copy is made of the specified value and it must
     *        not be modified after calling this method.
     */
    public final void setLocation(
        /* @Nullable */
        final Point location )
    {
        location_ = location;
    }

    /**
     * Sets the new component orientation.
     * 
     * @param orientation
     *        The new component orientation or {@code null} if the component
     *        orientation is unchanged.
     */
    public final void setOrientation(
        /* @Nullable */
        final ComponentOrientation orientation )
    {
        orientation_ = orientation;
    }

    /**
     * Sets the collection of new component surface design identifiers.
     * 
     * @param surfaceDesignIds
     *        The collection of new component surface design identifiers or
     *        {@code null} if the component surface design identifiers are
     *        unchanged. The key is the component orientation. The value is the
     *        new component surface design identifier. No copy is made of the
     *        specified value and it must not be modified after calling this
     *        method.
     */
    public final void setSurfaceDesignIds(
        /* @Nullable */
        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds )
    {
        surfaceDesignIds_ = surfaceDesignIds;
    }
}
