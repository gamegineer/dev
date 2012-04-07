/*
 * ComponentSurfaceDesign.java
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
 * Created on Apr 6, 2012 at 11:28:46 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentSurfaceDesign;

/**
 * Implementation of {@link org.gamegineer.table.core.IComponentSurfaceDesign}.
 */
@Immutable
public final class ComponentSurfaceDesign
    implements IComponentSurfaceDesign
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
     * Initializes a new instance of the {@code ComponentSurfaceDesign} class.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param width
     *        The component surface design width in table coordinates; must not
     *        be negative.
     * @param height
     *        The component surface design height in table coordinates; must not
     *        be negative.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public ComponentSurfaceDesign(
        /* @NonNull */
        final ComponentSurfaceDesignId id,
        final int width,
        final int height )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentLegal( width >= 0, "width", NonNlsMessages.ComponentSurfaceDesign_ctor_width_negative ); //$NON-NLS-1$
        assertArgumentLegal( height >= 0, "height", NonNlsMessages.ComponentSurfaceDesign_ctor_height_negative ); //$NON-NLS-1$

        id_ = id;
        size_ = new Dimension( width, height );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponentSurfaceDesign#getId()
     */
    @Override
    public ComponentSurfaceDesignId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.core.IComponentSurfaceDesign#getSize()
     */
    @Override
    public Dimension getSize()
    {
        return new Dimension( size_ );
    }
}
