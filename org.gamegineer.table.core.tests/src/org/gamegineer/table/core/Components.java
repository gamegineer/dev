/*
 * Components.java
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
 * Created on Jul 20, 2012 at 9:56:32 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of components suitable for testing.
 */
@ThreadSafe
public final class Components
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Components} class.
     */
    private Components()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique surface designs for the specified
     * table environment.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component; must not
     *        be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    /* @NonNull */
    public static IComponent createUniqueComponent(
        /* @NonNull */
        final ITableEnvironment tableEnvironment )
    {
        final IComponent component = tableEnvironment.createCard();
        setUniqueSurfaceDesigns( component );
        return component;
    }

    /**
     * Creates a new container with unique surface designs for the specified
     * table environment.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new container; must not
     *        be {@code null}.
     * 
     * @return A new container; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    /* @NonNull */
    public static IContainer createUniqueContainer(
        /* @NonNull */
        final ITableEnvironment tableEnvironment )
    {
        final IContainer container = tableEnvironment.createCardPile();
        setUniqueSurfaceDesigns( container );
        return container;
    }

    /**
     * Sets a unique surface design for every supported orientation in the
     * specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    private static void setUniqueSurfaceDesigns(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }
    }
}