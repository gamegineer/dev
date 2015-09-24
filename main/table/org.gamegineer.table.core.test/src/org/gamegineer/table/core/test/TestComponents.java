/*
 * TestComponents.java
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
 * Created on Jul 20, 2012 at 9:56:32 PM.
 */

package org.gamegineer.table.core.test;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.ITableEnvironment;

/**
 * A factory for creating various types of components suitable for testing.
 */
@ThreadSafe
public final class TestComponents
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestComponents} class.
     */
    private TestComponents()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique surface designs for the specified
     * table environment using a default component strategy.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component.
     * 
     * @return A new component.
     */
    public static IComponent createUniqueComponent(
        final ITableEnvironment tableEnvironment )
    {
        return createUniqueComponent( tableEnvironment, TestComponentStrategies.createUniqueComponentStrategy() );
    }

    /**
     * Creates a new component with unique surface designs for the specified
     * table environment using the specified component strategy.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component.
     * @param componentStrategy
     *        The component strategy.
     * 
     * @return A new component.
     */
    public static IComponent createUniqueComponent(
        final ITableEnvironment tableEnvironment,
        final IComponentStrategy componentStrategy )
    {
        final IComponent component = tableEnvironment.createComponent( componentStrategy );
        setUniqueSurfaceDesigns( component );
        return component;
    }

    /**
     * Creates a new container with unique surface designs for the specified
     * table environment using a default container strategy.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new container.
     * 
     * @return A new container.
     */
    public static IContainer createUniqueContainer(
        final ITableEnvironment tableEnvironment )
    {
        return createUniqueContainer( tableEnvironment, TestComponentStrategies.createUniqueContainerStrategy() );
    }

    /**
     * Creates a new container with unique surface designs for the specified
     * table environment using the specified container strategy.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new container.
     * @param containerStrategy
     *        The container strategy.
     * 
     * @return A new container.
     */
    public static IContainer createUniqueContainer(
        final ITableEnvironment tableEnvironment,
        final IContainerStrategy containerStrategy )
    {
        final IContainer container = tableEnvironment.createContainer( containerStrategy );
        setUniqueSurfaceDesigns( container );
        return container;
    }

    /**
     * Sets a unique surface design for every supported orientation in the
     * specified component.
     * 
     * @param component
     *        The component.
     */
    private static void setUniqueSurfaceDesigns(
        final IComponent component )
    {
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }
    }
}
