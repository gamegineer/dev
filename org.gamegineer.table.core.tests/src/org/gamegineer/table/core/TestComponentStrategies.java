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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.layouts.ContainerLayouts;
import org.gamegineer.table.internal.core.surfacedesigns.ComponentSurfaceDesigns;

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

    /** The null component strategy. */
    private static final IComponentStrategy NULL_COMPONENT_STRATEGY = new NullComponentStrategy();

    /** The null container strategy. */
    private static final IContainerStrategy NULL_CONTAINER_STRATEGY = new NullContainerStrategy();

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
        return registerComponentStrategy( createComponentStrategyDecorator( NULL_COMPONENT_STRATEGY, getUniqueComponentStrategyId() ) );
    }

    /**
     * Creates a new container strategy with a unique identifier.
     * 
     * @return A new container strategy; never {@code null}.
     */
    /* @NonNull */
    public static IContainerStrategy createUniqueContainerStrategy()
    {
        return registerComponentStrategy( createContainerStrategyDecorator( NULL_CONTAINER_STRATEGY, getUniqueComponentStrategyId() ) );
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

    /**
     * Registers the specified component strategy with the component strategy
     * registry.
     * 
     * @param <T>
     *        The type of the component strategy.
     * 
     * @param componentStrategy
     *        The component strategy; must not be {@code null}.
     * 
     * @return The registered component strategy; never {@code null}.
     */
    /* @NonNull */
    private static <T extends IComponentStrategy> T registerComponentStrategy(
        /* @NonNull */
        final T componentStrategy )
    {
        assert componentStrategy != null;

        final IComponentStrategyRegistry componentStrategyRegistry = Activator.getDefault().getComponentStrategyRegistry();
        assert componentStrategyRegistry != null;
        componentStrategyRegistry.registerComponentStrategy( componentStrategy );
        return componentStrategy;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A null component strategy.
     */
    @Immutable
    private static class NullComponentStrategy
        implements IComponentStrategy
    {
        // ======================================================================
        // Fields
        // ======================================================================

        /** The strategy identifier. */
        private static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.componentStrategies.nullComponent" ); //$NON-NLS-1$

        /** The collection of supported component orientations. */
        private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( NullOrientation.values( NullOrientation.class ) ) );


        // ======================================================================
        // Constructors
        // ======================================================================

        /**
         * Initializes a new instance of the {@code NullComponentStrategy}
         * class.
         */
        NullComponentStrategy()
        {
        }


        // ======================================================================
        // Methods
        // ======================================================================

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getDefaultLocation()
         */
        @Override
        public final Point getDefaultLocation()
        {
            return new Point( 0, 0 );
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrientation()
         */
        @Override
        public final ComponentOrientation getDefaultOrientation()
        {
            return NullOrientation.DEFAULT;
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrigin()
         */
        @Override
        public final Point getDefaultOrigin()
        {
            return getDefaultLocation();
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getDefaultSurfaceDesigns()
         */
        @Override
        public final Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
        {
            final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new HashMap<ComponentOrientation, ComponentSurfaceDesign>();
            surfaceDesigns.put( NullOrientation.DEFAULT, ComponentSurfaceDesigns.NULL );
            return surfaceDesigns;
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getId()
         */
        @Override
        public ComponentStrategyId getId()
        {
            return ID;
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getSupportedOrientations()
         */
        @Override
        public final Collection<ComponentOrientation> getSupportedOrientations()
        {
            return SUPPORTED_ORIENTATIONS;
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#isFocusable()
         */
        @Override
        public boolean isFocusable()
        {
            return false;
        }
    }

    /**
     * A null container strategy.
     */
    @Immutable
    private static final class NullContainerStrategy
        extends NullComponentStrategy
        implements IContainerStrategy
    {
        // ======================================================================
        // Fields
        // ======================================================================

        /** The strategy identifier. */
        private static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.componentStrategies.nullContainer" ); //$NON-NLS-1$


        // ======================================================================
        // Constructors
        // ======================================================================

        /**
         * Initializes a new instance of the {@code NullContainerStrategy}
         * class.
         */
        NullContainerStrategy()
        {
        }


        // ======================================================================
        // Methods
        // ======================================================================

        /*
         * @see org.gamegineer.table.core.IContainerStrategy#getDefaultLayout()
         */
        @Override
        public IContainerLayout getDefaultLayout()
        {
            return ContainerLayouts.ABSOLUTE;
        }

        /*
         * @see org.gamegineer.table.internal.core.NullComponentStrategy#getId()
         */
        @Override
        public ComponentStrategyId getId()
        {
            return ID;
        }

        /*
         * @see org.gamegineer.table.internal.core.NullComponentStrategy#isFocusable()
         */
        @Override
        public boolean isFocusable()
        {
            return true;
        }
    }

    /**
     * A null component orientation.
     */
    @Immutable
    private static final class NullOrientation
        extends ComponentOrientation
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 1L;

        /** The default orientation. */
        static final NullOrientation DEFAULT = new NullOrientation( "default", 0 ); //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullOrientation} class.
         * 
         * @param name
         *        The name of the enum constant; must not be {@code null}.
         * @param ordinal
         *        The ordinal of the enum constant.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code ordinal} is negative.
         * @throws java.lang.NullPointerException
         *         If {@code name} is {@code null}.
         */
        private NullOrientation(
            /* @NonNull */
            final String name,
            final int ordinal )
        {
            super( name, ordinal );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ComponentOrientation#inverse()
         */
        @Override
        public ComponentOrientation inverse()
        {
            return this;
        }
    }
}
