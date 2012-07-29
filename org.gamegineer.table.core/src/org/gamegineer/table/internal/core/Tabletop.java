/*
 * Tabletop.java
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
 * Created on Jun 28, 2012 at 8:07:30 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.TabletopLayouts;
import org.gamegineer.table.core.TabletopOrientation;

/**
 * A tabletop.
 */
@ThreadSafe
final class Tabletop
    extends Container
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of supported component orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( TabletopOrientation.values( TabletopOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Tabletop} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the tabletop; must not be
     *        {@code null}.
     */
    Tabletop(
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
        return TabletopLayouts.ABSOLUTE;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultLocation()
     */
    @Override
    Point getDefaultLocation()
    {
        return new Point( Short.MIN_VALUE / 2, Short.MIN_VALUE / 2 );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrientation()
     */
    @Override
    ComponentOrientation getDefaultOrientation()
    {
        return TabletopOrientation.DEFAULT;
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
        return Collections.<ComponentOrientation, ComponentSurfaceDesign>singletonMap( //
            TabletopOrientation.DEFAULT, //
            new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Tabletop.DEFAULT_SURFACE_DESIGN" ), Short.MAX_VALUE, Short.MAX_VALUE ) ); //$NON-NLS-1$ );
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
