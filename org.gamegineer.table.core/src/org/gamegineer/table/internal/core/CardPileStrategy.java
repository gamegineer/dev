/*
 * CardPileStrategy.java
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
 * Created on Aug 1, 2012 at 8:14:11 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.CardPileLayouts;
import org.gamegineer.table.core.CardPileOrientation;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerStrategy;

/**
 * A component strategy that represents a card pile.
 */
@Immutable
public final class CardPileStrategy
    implements IContainerStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default card pile surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.CardPileStrategy.defaultSurfaceDesign" ), 71, 96 ); //$NON-NLS-1$

    /** The strategy identifier. */
    public static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.componentStrategies.cardPile" ); //$NON-NLS-1$

    /** The singleton instance of this class. */
    public static final CardPileStrategy INSTANCE = new CardPileStrategy();

    /** The collection of supported card pile orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardPileOrientation.values( CardPileOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileStrategy} class.
     */
    private CardPileStrategy()
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
        return CardPileLayouts.STACKED;
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultLocation()
     */
    @Override
    public Point getDefaultLocation()
    {
        return getDefaultOrigin();
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrientation()
     */
    @Override
    public ComponentOrientation getDefaultOrientation()
    {
        return CardPileOrientation.BASE;
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrigin()
     */
    @Override
    public Point getDefaultOrigin()
    {
        return new Point( 0, 0 );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultSurfaceDesigns()
     */
    @Override
    public Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>();
        surfaceDesigns.put( CardPileOrientation.BASE, DEFAULT_SURFACE_DESIGN );
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
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }
}
