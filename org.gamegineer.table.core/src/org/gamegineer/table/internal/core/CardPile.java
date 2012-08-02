/*
 * CardPile.java
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
 * Created on Jan 14, 2010 at 11:11:09 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileLayouts;
import org.gamegineer.table.core.CardPileOrientation;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A component that represents a card pile.
 */
@ThreadSafe
final class CardPile
    extends Container
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default card pile surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.CardPile.defaultSurfaceDesign" ), 0, 0 ); //$NON-NLS-1$

    /** The collection of supported card pile orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardPileOrientation.values( CardPileOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPile} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the card pile; must not be
     *        {@code null}.
     */
    CardPile(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.core.Container#getDefaultLayout()
     */
    @Override
    IContainerLayout getDefaultLayout()
    {
        return CardPileLayouts.STACKED;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultLocation()
     */
    @Override
    Point getDefaultLocation()
    {
        return getDefaultOrigin();
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrientation()
     */
    @Override
    ComponentOrientation getDefaultOrientation()
    {
        return CardPileOrientation.BASE;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrigin()
     */
    @Override
    Point getDefaultOrigin()
    {
        return new Point( 0, 0 );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultSurfaceDesigns()
     */
    @Override
    Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>();
        surfaceDesigns.put( CardPileOrientation.BASE, DEFAULT_SURFACE_DESIGN );
        return surfaceDesigns;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }
}
