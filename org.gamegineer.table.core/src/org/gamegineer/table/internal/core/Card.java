/*
 * Card.java
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
 * Created on Oct 11, 2009 at 9:53:36 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A component that represents a card.
 */
@ThreadSafe
final class Card
    extends Component
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default card surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Card.defaultSurfaceDesign" ), 0, 0 ); //$NON-NLS-1$

    /** The collection of supported card orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardOrientation.values( CardOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Card} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the card; must not be
     *        {@code null}.
     */
    Card(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultLocation()
     */
    @Override
    Point getDefaultLocation()
    {
        return new Point( 0, 0 );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrientation()
     */
    @Override
    ComponentOrientation getDefaultOrientation()
    {
        return CardOrientation.FACE;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrigin()
     */
    @Override
    Point getDefaultOrigin()
    {
        return getDefaultLocation();
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultSurfaceDesigns()
     */
    @Override
    Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>();
        surfaceDesigns.put( CardOrientation.BACK, DEFAULT_SURFACE_DESIGN );
        surfaceDesigns.put( CardOrientation.FACE, DEFAULT_SURFACE_DESIGN );
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
}
