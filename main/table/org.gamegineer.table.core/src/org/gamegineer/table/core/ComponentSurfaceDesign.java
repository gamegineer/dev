/*
 * ComponentSurfaceDesign.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Apr 6, 2012 at 11:28:46 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.awt.Dimension;
import net.jcip.annotations.Immutable;

/**
 * A component surface design.
 */
@Immutable
public final class ComponentSurfaceDesign
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component surface design identifier. */
    private final ComponentSurfaceDesignId id_;

    /** The component surface design size in table coordinates. */
    private final Dimension size_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesign} class
     * from the specified size.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param size
     *        The component surface design size in table coordinates; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the width or height of {@code size} is negative.
     */
    public ComponentSurfaceDesign(
        final ComponentSurfaceDesignId id,
        final Dimension size )
    {
        this( id, size.width, size.height );
    }

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesign} class
     * from the specified width and height.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param width
     *        The component surface design width in table coordinates.
     * @param height
     *        The component surface design height in table coordinates.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     */
    public ComponentSurfaceDesign(
        final ComponentSurfaceDesignId id,
        final int width,
        final int height )
    {
        assertArgumentLegal( width >= 0, "width", NonNlsMessages.ComponentSurfaceDesign_ctor_width_negative ); //$NON-NLS-1$
        assertArgumentLegal( height >= 0, "height", NonNlsMessages.ComponentSurfaceDesign_ctor_height_negative ); //$NON-NLS-1$

        id_ = id;
        size_ = new Dimension( width, height );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component surface design identifier.
     * 
     * @return The component surface design identifier; never {@code null}.
     */
    public ComponentSurfaceDesignId getId()
    {
        return id_;
    }

    /**
     * Gets the component surface design size in table coordinates.
     * 
     * @return The component surface design size in table coordinates; never
     *         {@code null}.
     */
    public Dimension getSize()
    {
        return new Dimension( size_ );
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ComponentSurfaceDesign[" ); //$NON-NLS-1$
        sb.append( "id_=" ); //$NON-NLS-1$
        sb.append( id_ );
        sb.append( ", size_=" ); //$NON-NLS-1$
        sb.append( size_ );
        sb.append( "]" ); //$NON-NLS-1$
        return nonNull( sb.toString() );
    }
}
