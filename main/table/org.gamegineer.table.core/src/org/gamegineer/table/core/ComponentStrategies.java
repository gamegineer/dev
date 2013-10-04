/*
 * ComponentStrategies.java
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
 * Created on Mar 14, 2013 at 10:22:06 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of common component strategies.
 */
@ThreadSafe
public final class ComponentStrategies
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The null component strategy. */
    public static final IComponentStrategy NULL_COMPONENT = new NullComponentStrategy();

    /** The null container strategy. */
    public static final IContainerStrategy NULL_CONTAINER = new NullContainerStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategies} class.
     */
    private ComponentStrategies()
    {
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
        // ==================================================================
        // Fields
        // ==================================================================

        /** The strategy identifier. */
        private static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.table.componentStrategies.nullComponent" ); //$NON-NLS-1$

        /** The collection of supported component orientations. */
        private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( NullOrientation.values( NullOrientation.class ) ) );


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullComponentStrategy}
         * class.
         */
        NullComponentStrategy()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

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
            final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new HashMap<>();
            surfaceDesigns.put( NullOrientation.DEFAULT, ComponentSurfaceDesigns.NULL );
            return surfaceDesigns;
        }

        /*
         * @see org.gamegineer.table.core.IComponentStrategy#getExtension(java.lang.Class)
         */
        @Override
        public final <T> T getExtension(
            final Class<T> type )
        {
            assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

            return null;
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
    }

    /**
     * A null container strategy.
     */
    @Immutable
    private static final class NullContainerStrategy
        extends NullComponentStrategy
        implements IContainerStrategy
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The container strategy. */
        private static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.table.componentStrategies.nullContainer" ); //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullContainerStrategy}
         * class.
         */
        NullContainerStrategy()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IContainerStrategy#getDefaultLayout()
         */
        @Override
        public IContainerLayout getDefaultLayout()
        {
            return ContainerLayouts.ABSOLUTE;
        }

        /*
         * @see org.gamegineer.table.core.ComponentStrategies.NullComponentStrategy#getId()
         */
        @Override
        public ComponentStrategyId getId()
        {
            return ID;
        }
    }
}
