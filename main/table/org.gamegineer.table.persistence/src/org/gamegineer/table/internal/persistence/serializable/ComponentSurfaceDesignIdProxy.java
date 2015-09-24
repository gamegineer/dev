/*
 * ComponentSurfaceDesignIdProxy.java
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
 * Created on Aug 16, 2012 at 8:51:45 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A serializable proxy for the {@code ComponentSurfaceDesignId} class.
 */
@NotThreadSafe
public final class ComponentSurfaceDesignIdProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3917184738395528058L;

    /**
     * The component surface design identifier.
     * 
     * @serial
     */
    private String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignIdProxy}
     * class.
     */
    @SuppressWarnings( "unused" )
    private ComponentSurfaceDesignIdProxy()
    {
        id_ = ""; //$NON-NLS-1$
    }

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignIdProxy}
     * class from the specified {@code ComponentSurfaceDesignId} instance.
     * 
     * @param componentSurfaceDesignId
     *        The {@code ComponentSurfaceDesignId} instance.
     */
    public ComponentSurfaceDesignIdProxy(
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        id_ = componentSurfaceDesignId.toString();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a replacement object for this instance after it has been
     * deserialized.
     * 
     * @return A replacement object for this instance after it has been
     *         deserialized.
     */
    private Object readResolve()
    {
        return ComponentSurfaceDesignId.fromString( id_ );
    }
}
