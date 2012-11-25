/*
 * AbstractComponentStrategy.java
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
 * Created on Aug 21, 2012 at 8:42:02 PM.
 */

package org.gamegineer.cards.internal.core.strategies;

import java.awt.Point;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.cards.internal.core.Loggers;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;
import org.gamegineer.table.internal.core.surfacedesigns.ComponentSurfaceDesigns;

/**
 * Superclass for all component strategies.
 */
@Immutable
abstract class AbstractComponentStrategy
    implements IComponentStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component strategy identifier. */
    private final ComponentStrategyId id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentStrategy}
     * class.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     */
    AbstractComponentStrategy(
        /* @NonNull */
        final ComponentStrategyId id )
    {
        assert id != null;

        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation returns the point (0,0). Subclasses may override and
     * are not required to call the superclass implementation.
     * 
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultLocation()
     */
    @Override
    public Point getDefaultLocation()
    {
        return new Point( 0, 0 );
    }

    /**
     * This implementation returns the same value as {@link #getDefaultLocation}
     * . Subclasses may override and are not required to call the superclass
     * implementation.
     * 
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrigin()
     */
    @Override
    public Point getDefaultOrigin()
    {
        return getDefaultLocation();
    }

    /**
     * Gets the default component surface design.
     * 
     * @return The default component surface design; never {@code null}.
     */
    /* @NonNull */
    private ComponentSurfaceDesign getDefaultSurfaceDesign()
    {
        try
        {
            return ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( getDefaultSurfaceDesignId() );
        }
        catch( final NoSuchComponentSurfaceDesignException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractComponentStrategy_getDefaultSurfaceDesign_notAvailable, e );
        }

        // FIXME: need to expose null implementation as public; remove internal import
        return ComponentSurfaceDesigns.NULL;
    }

    /**
     * Gets the identifier of the default component surface design.
     * 
     * @return The identifier of the default component surface design; never
     *         {@code null}.
     */
    /* @NonNull */
    abstract ComponentSurfaceDesignId getDefaultSurfaceDesignId();

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultSurfaceDesigns()
     */
    @Override
    public final Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        final Collection<ComponentOrientation> orientations = getSupportedOrientations();
        final ComponentSurfaceDesign defaultSurfaceDesign = getDefaultSurfaceDesign();
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>( orientations.size() );
        for( final ComponentOrientation orientation : orientations )
        {
            surfaceDesigns.put( orientation, defaultSurfaceDesign );
        }

        return surfaceDesigns;
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getId()
     */
    @Override
    public final ComponentStrategyId getId()
    {
        return id_;
    }
}
