/*
 * NullComponentStrategy.java
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
 * Created on Aug 1, 2012 at 10:46:56 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A null component strategy.
 */
@Immutable
public class NullComponentStrategy
    implements IComponentStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default component surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.NullComponentStrategy.defaultSurfaceDesign" ), 1, 1 ); //$NON-NLS-1$

    /** The strategy identifier. */
    private static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.table.internal.core.NullComponentStrategy" ); //$NON-NLS-1$

    /** The collection of supported component orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( NullOrientation.values( NullOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullComponentStrategy} class.
     */
    public NullComponentStrategy()
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
        surfaceDesigns.put( NullOrientation.DEFAULT, DEFAULT_SURFACE_DESIGN );
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


    // ======================================================================
    // Nested Types
    // ======================================================================

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
