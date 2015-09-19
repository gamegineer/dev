/*
 * TabletopStrategy.java
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
 * Created on Aug 1, 2012 at 8:14:28 PM.
 */

package org.gamegineer.table.internal.core.impl.strategies;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.AbstractContainerStrategy;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayouts;
import org.gamegineer.table.core.TabletopOrientation;
import org.gamegineer.table.core.dnd.IDragStrategyFactory;
import org.gamegineer.table.core.dnd.NullDragStrategyFactory;
import org.gamegineer.table.core.impl.ComponentStrategyIds;

/**
 * A component strategy that represents a tabletop.
 */
@Immutable
final class TabletopStrategy
    extends AbstractContainerStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default tabletop surface design identifier. */
    private static final ComponentSurfaceDesignId DEFAULT_SURFACE_DESIGN_ID = ComponentSurfaceDesignId.fromString( "org.gamegineer.table.tabletopSurfaceDesigns.default" ); //$NON-NLS-1$

    /** The collection of supported tabletop orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<@NonNull ComponentOrientation>asList( TabletopOrientation.values( TabletopOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TabletopStrategy} class.
     */
    TabletopStrategy()
    {
        super( ComponentStrategyIds.TABLETOP );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerStrategy#getDefaultLayoutId()
     */
    @Override
    protected ContainerLayoutId getDefaultLayoutId()
    {
        return ContainerLayouts.NULL.getId();
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
     * @see org.gamegineer.table.core.AbstractComponentStrategy#getDefaultSurfaceDesignId()
     */
    @Override
    protected ComponentSurfaceDesignId getDefaultSurfaceDesignId()
    {
        return DEFAULT_SURFACE_DESIGN_ID;
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentStrategy#getExtension(java.lang.Class)
     */
    @Override
    public <T> @Nullable T getExtension(
        final Class<T> type )
    {
        if( type == IDragStrategyFactory.class )
        {
            return type.cast( NullDragStrategyFactory.INSTANCE );
        }

        return super.getExtension( type );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }
}
