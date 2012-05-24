/*
 * ComponentSurfaceDesignProxy.java
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
 * Created on Apr 26, 2012 at 9:54:25 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A serializable proxy for the {@code ComponentSurfaceDesign} class.
 */
@NotThreadSafe
public final class ComponentSurfaceDesignProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3381623697729837412L;

    /**
     * The component surface design height in table coordinates.
     * 
     * @serial
     */
    private int height_;

    /**
     * The component surface design identifier.
     * 
     * @serial
     */
    private String id_;

    /**
     * The component surface design width in table coordinates.
     * 
     * @serial
     */
    private int width_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignProxy}
     * class.
     */
    @SuppressWarnings( "unused" )
    private ComponentSurfaceDesignProxy()
    {
        height_ = 0;
        id_ = null;
        width_ = 0;
    }

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignProxy}
     * class from the specified {@code ComponentSurfaceDesign} instance.
     * 
     * @param componentSurfaceDesign
     *        The {@code ComponentSurfaceDesign} instance; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesign} is {@code null}.
     */
    public ComponentSurfaceDesignProxy(
        /* @NonNull */
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        assertArgumentNotNull( componentSurfaceDesign, "componentSurfaceDesign" ); //$NON-NLS-1$

        id_ = componentSurfaceDesign.getId().toString();
        final Dimension size = componentSurfaceDesign.getSize();
        height_ = size.height;
        width_ = size.width;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a replacement object for this instance after it has been
     * deserialized.
     * 
     * @return A replacement object for this instance after it has been
     *         deserialized; never {@code null}.
     */
    /* @NonNull */
    private Object readResolve()
    {
        return new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( id_ ), width_, height_ );
    }
}
