/*
 * CardStrategy.java
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
 * Created on Aug 1, 2012 at 8:14:04 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A component strategy that represents a card.
 */
@Immutable
public final class CardStrategy
    implements IComponentStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default card surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.CardStrategy.defaultSurfaceDesign" ), 71, 96 ); //$NON-NLS-1$

    /** The singleton instance of this class. */
    public static final CardStrategy INSTANCE = new CardStrategy();

    /** The collection of supported card orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardOrientation.values( CardOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardStrategy} class.
     */
    private CardStrategy()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultLocation()
     */
    @Override
    public Point getDefaultLocation()
    {
        return new Point( 0, 0 );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrientation()
     */
    @Override
    public ComponentOrientation getDefaultOrientation()
    {
        return CardOrientation.FACE;
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrigin()
     */
    @Override
    public Point getDefaultOrigin()
    {
        return getDefaultLocation();
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultSurfaceDesigns()
     */
    @Override
    public Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>();
        surfaceDesigns.put( CardOrientation.BACK, DEFAULT_SURFACE_DESIGN );
        surfaceDesigns.put( CardOrientation.FACE, DEFAULT_SURFACE_DESIGN );
        return surfaceDesigns;
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
        return false;
    }
}
