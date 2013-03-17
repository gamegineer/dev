/*
 * IComponentStrategy.java
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
 * Created on Aug 1, 2012 at 7:45:50 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;
import org.gamegineer.table.core.dnd.IDragStrategyFactory;

/**
 * A strategy for customizing the behavior of a component.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IComponentStrategy
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the default component location.
     * 
     * @return The default component location; never {@code null}.
     */
    /* @NonNull */
    public Point getDefaultLocation();

    /**
     * Gets the default component orientation.
     * 
     * @return The default component orientation; never {@code null}.
     */
    /* @NonNull */
    public ComponentOrientation getDefaultOrientation();

    /**
     * Gets the default component origin.
     * 
     * @return The default component origin; never {@code null}.
     */
    /* @NonNull */
    public Point getDefaultOrigin();

    /**
     * Gets the default collection of component surface designs.
     * 
     * @return The default collection of component surface designs; never
     *         {@code null}. The keys in the returned collection must be
     *         identical to the keys in the collection returned from
     *         {@link #getSupportedOrientations}.
     */
    /* @NonNull */
    public Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns();

    /**
     * Gets the drag strategy factory for components associated with this
     * strategy.
     * 
     * @return The drag strategy factory for components associated with this
     *         strategy; never {@code null}.
     */
    /* @NonNull */
    public IDragStrategyFactory getDragStrategyFactory();

    /**
     * Gets the component strategy extension of the specified type.
     * 
     * @param <T>
     *        The component strategy extension type.
     * 
     * @param type
     *        The component strategy extension type; must not be {@code null}.
     * 
     * @return The component strategy extension of the specified type or
     *         {@code null} if the component strategy does not support the
     *         specified extension.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    /* @Nullable */
    public <T> T getExtension(
        /* @NonNull */
        Class<T> type );

    /**
     * Gets the component strategy identifier.
     * 
     * @return The component strategy identifier; never {@code null}.
     */
    /* @NonNull */
    public ComponentStrategyId getId();

    /**
     * Gets an immutable view of the collection of supported orientations for
     * the component.
     * 
     * @return An immutable view of the collection of supported orientations for
     *         the component; never {@code null}. The returned collection is
     *         guaranteed to not be empty.
     */
    /* @NonNull */
    public Collection<ComponentOrientation> getSupportedOrientations();
}
