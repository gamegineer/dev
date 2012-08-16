/*
 * TestComponentStrategies.java
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
 * Created on Aug 3, 2012 at 10:39:33 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.NullComponentStrategy;
import org.gamegineer.table.internal.core.NullContainerStrategy;

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
     *        The component strategy to clone; must not be {@code null}.
     * 
     * @return A new component strategy; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategy} is {@code null}.
     */
    /* @NonNull */
    public static IComponentStrategy cloneComponentStrategy(
        /* @NonNull */
        final IComponentStrategy componentStrategy )
    {
        assertArgumentNotNull( componentStrategy, "componentStrategy" ); //$NON-NLS-1$

        return (componentStrategy instanceof IContainerStrategy) //
            ? createContainerStrategyDecorator( (IContainerStrategy)componentStrategy, componentStrategy.getId() ) //
            : createComponentStrategyDecorator( componentStrategy, componentStrategy.getId() );
    }

    /**
     * Creates a decorator for the specified component strategy.
     * 
     * @param componentStrategy
     *        The component strategy to be decorated; must not be {@code null}.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator; must not be
     *        {@code null}.
     * 
     * @return A decorator for the specified component strategy; never
     *         {@code null}.
     */
    /* @NonNull */
    private static IComponentStrategy createComponentStrategyDecorator(
        /* @NonNull */
        final IComponentStrategy componentStrategy,
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        assert componentStrategy != null;
        assert componentStrategyId != null;

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
            public ComponentStrategyId getId()
            {
                return componentStrategyId;
            }

            @Override
            public Collection<ComponentOrientation> getSupportedOrientations()
            {
                return componentStrategy.getSupportedOrientations();
            }

            @Override
            public boolean isFocusable()
            {
                return componentStrategy.isFocusable();
            }
        };
    }

    /**
     * Creates a decorator for the specified container strategy.
     * 
     * @param containerStrategy
     *        The container strategy to be decorated; must not be {@code null}.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator; must not be
     *        {@code null}.
     * 
     * @return A decorator for the specified container strategy; never
     *         {@code null}.
     */
    /* @NonNull */
    private static IContainerStrategy createContainerStrategyDecorator(
        /* @NonNull */
        final IContainerStrategy containerStrategy,
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        assert containerStrategy != null;
        assert componentStrategyId != null;

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
            public ComponentStrategyId getId()
            {
                return componentStrategyId;
            }

            @Override
            public Collection<ComponentOrientation> getSupportedOrientations()
            {
                return containerStrategy.getSupportedOrientations();
            }

            @Override
            public boolean isFocusable()
            {
                return containerStrategy.isFocusable();
            }
        };
    }

    /**
     * Creates a new component strategy with a unique identifier.
     * 
     * @return A new component strategy; never {@code null}.
     */
    /* @NonNull */
    public static IComponentStrategy createUniqueComponentStrategy()
    {
        return createComponentStrategyDecorator( //
            new NullComponentStrategy(), //
            getUniqueComponentStrategyId() );
    }

    /**
     * Creates a new container strategy with a unique identifier.
     * 
     * @return A new container strategy; never {@code null}.
     */
    /* @NonNull */
    public static IContainerStrategy createUniqueContainerStrategy()
    {
        return createContainerStrategyDecorator( //
            new NullContainerStrategy(), //
            getUniqueComponentStrategyId() );
    }

    /**
     * Gets a unique component strategy identifier.
     * 
     * @return A unique component strategy identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static ComponentStrategyId getUniqueComponentStrategyId()
    {
        return ComponentStrategyId.fromString( String.format( "component-strategy-%1$d", nextComponentStrategyId_.incrementAndGet() ) ); //$NON-NLS-1$
    }
}
