/*
 * TabletopStrategy.java
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
 * Created on Aug 1, 2012 at 8:14:28 PM.
 */

package org.gamegineer.table.internal.core.strategies;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.TabletopOrientation;
import org.gamegineer.table.internal.core.layouts.ContainerLayouts;

/**
 * A component strategy that represents a tabletop.
 */
@Immutable
public final class TabletopStrategy
    extends AbstractContainerStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of supported tabletop orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( TabletopOrientation.values( TabletopOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TabletopStrategy} class.
     */
    public TabletopStrategy()
    {
        super( ComponentStrategyId.fromString( "org.gamegineer.componentStrategies.tabletop" ) ); //$NON-NLS-1$
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
     * @see org.gamegineer.table.internal.core.strategies.AbstractComponentStrategy#getDefaultLocation()
     */
    @Override
    public Point getDefaultLocation()
    {
        return new Point( Short.MIN_VALUE / 2, Short.MIN_VALUE / 2 );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrientation()
     */
    @Override
    public ComponentOrientation getDefaultOrientation()
    {
        return TabletopOrientation.DEFAULT;
    }

    /*
     * @see org.gamegineer.table.internal.core.strategies.AbstractComponentStrategy#getDefaultSurfaceDesignId()
     */
    @Override
    ComponentSurfaceDesignId getDefaultSurfaceDesignId()
    {
        return ComponentSurfaceDesignId.fromString( "org.gamegineer.tabletopSurfaceDesigns.default" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /*
     * @see org.gamegineer.table.internal.core.strategies.AbstractContainerStrategy#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return false;
    }
}