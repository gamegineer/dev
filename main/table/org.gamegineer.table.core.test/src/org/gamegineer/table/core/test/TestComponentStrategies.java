/*
 * TestComponentStrategies.java
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
 * Created on Aug 3, 2012 at 10:39:33 PM.
 */

package org.gamegineer.table.core.test;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IComponentStrategyRegistry;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.internal.core.test.Activator;

/**
 * A factory for creating various types of component strategies suitable for
 * testing.
 */
@ThreadSafe
public final class TestComponentStrategies
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique component strategy identifier. */
    private static final AtomicLong nextComponentStrategyId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestComponentStrategies} class.
     */
    private TestComponentStrategies()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified component strategy.
     * 
     * @param componentStrategy
     *        The component strategy to clone.
     * 
     * @return A new component strategy.
     */
    public static IComponentStrategy cloneComponentStrategy(
        final IComponentStrategy componentStrategy )
    {
        return (componentStrategy instanceof IContainerStrategy) //
            ? createContainerStrategyDecorator( (IContainerStrategy)componentStrategy, componentStrategy.getId() ) //
            : createComponentStrategyDecorator( componentStrategy, componentStrategy.getId() );
    }

    /**
     * Creates a decorator for the specified component strategy.
     * 
     * @param componentStrategy
     *        The component strategy to be decorated.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator.
     * 
     * @return A decorator for the specified component strategy.
     */
    private static IComponentStrategy createComponentStrategyDecorator(
        final IComponentStrategy componentStrategy,
        final ComponentStrategyId componentStrategyId )
    {
        return new IComponentStrategy()
        {
            @Override
            public Point getDefaultLocation()
            {
                return componentStrategy.getDefaultLocation();
            }

            @Override
            public ComponentOrientation getDefaultOrientation()
            {
                return componentStrategy.getDefaultOrientation();
            }

            @Override
            public Point getDefaultOrigin()
            {
                return componentStrategy.getDefaultOrigin();
            }

            @Override
            public Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
            {
                return componentStrategy.getDefaultSurfaceDesigns();
            }

            @Override
            public <T> @Nullable T getExtension(
                final Class<T> type )
            {
                return componentStrategy.getExtension( type );
            }

            @Override
            public ComponentStrategyId getId()
            {
                return componentStrategyId;
            }

            @Override
            public Collection<ComponentOrientation> getSupportedOrientations()
            {
                return componentStrategy.getSupportedOrientations();
            }
        };
    }

    /**
     * Creates a decorator for the specified container strategy.
     * 
     * @param containerStrategy
     *        The container strategy to be decorated.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator.
     * 
     * @return A decorator for the specified container strategy.
     */
    private static IContainerStrategy createContainerStrategyDecorator(
        final IContainerStrategy containerStrategy,
        final ComponentStrategyId componentStrategyId )
    {
        return new IContainerStrategy()
        {
            @Override
            public IContainerLayout getDefaultLayout()
            {
                return containerStrategy.getDefaultLayout();
            }

            @Override
            public Point getDefaultLocation()
            {
                return containerStrategy.getDefaultLocation();
            }

            @Override
            public ComponentOrientation getDefaultOrientation()
            {
                return containerStrategy.getDefaultOrientation();
            }

            @Override
            public Point getDefaultOrigin()
            {
                return containerStrategy.getDefaultOrigin();
            }

            @Override
            public Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
            {
                return containerStrategy.getDefaultSurfaceDesigns();
            }

            @Override
            public <T> @Nullable T getExtension(
                final Class<T> type )
            {
                return containerStrategy.getExtension( type );
            }

            @Override
            public ComponentStrategyId getId()
            {
                return componentStrategyId;
            }

            @Override
            public Collection<ComponentOrientation> getSupportedOrientations()
            {
                return containerStrategy.getSupportedOrientations();
            }
        };
    }

    /**
     * Creates a new component strategy with a unique identifier.
     * 
     * @return A new component strategy.
     */
    public static IComponentStrategy createUniqueComponentStrategy()
    {
        return registerComponentStrategy( createComponentStrategyDecorator( ComponentStrategies.NULL_COMPONENT, getUniqueComponentStrategyId() ) );
    }

    /**
     * Creates a new container strategy with a unique identifier.
     * 
     * @return A new container strategy.
     */
    public static IContainerStrategy createUniqueContainerStrategy()
    {
        return registerComponentStrategy( createContainerStrategyDecorator( ComponentStrategies.NULL_CONTAINER, getUniqueComponentStrategyId() ) );
    }

    /**
     * Gets a unique component strategy identifier.
     * 
     * @return A unique component strategy identifier.
     */
    @SuppressWarnings( "boxing" )
    private static ComponentStrategyId getUniqueComponentStrategyId()
    {
        return ComponentStrategyId.fromString( String.format( "component-strategy-%1$d", nextComponentStrategyId_.incrementAndGet() ) ); //$NON-NLS-1$
    }

    /**
     * Registers the specified component strategy with the component strategy
     * registry.
     * 
     * @param <T>
     *        The type of the component strategy.
     * 
     * @param componentStrategy
     *        The component strategy.
     * 
     * @return The registered component strategy.
     */
    private static <T extends IComponentStrategy> T registerComponentStrategy(
        final T componentStrategy )
    {
        final IComponentStrategyRegistry componentStrategyRegistry = Activator.getDefault().getComponentStrategyRegistry();
        assert componentStrategyRegistry != null;
        componentStrategyRegistry.registerObject( componentStrategy );
        return componentStrategy;
    }
}
