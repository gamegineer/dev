/*
 * ComponentPathProxy.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Jun 14, 2012 at 10:00:47 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentPath;

/**
 * A serializable proxy for the {@code ComponentPath} class.
 */
@NotThreadSafe
public final class ComponentPathProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -1400726262099665541L;

    /**
     * The collection of component indexes ordered from the penultimate-root
     * ancestor component to the leaf component.
     * 
     * <p>
     * The root component index is <b>NOT</b> included in this collection. The
     * root path component is always assumed to be {@link ComponentPath#ROOT}.
     * Therefore, this collection will be empty only when the root component
     * path is serialized.
     * </p>
     * 
     * @serial
     */
    private int[] indexes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPathProxy} class.
     */
    @SuppressWarnings( "unused" )
    private ComponentPathProxy()
    {
        indexes_ = new int[ 0 ];
    }

    /**
     * Initializes a new instance of the {@code ComponentPathProxy} class from
     * the specified {@code ComponentPath} instance.
     * 
     * @param componentPath
     *        The {@code ComponentPath} instance.
     */
    public ComponentPathProxy(
        final ComponentPath componentPath )
    {
        indexes_ = componentPath.toList().stream() //
            .filter( path -> !path.equals( ComponentPath.ROOT ) ) //
            .mapToInt( ComponentPath::getIndex ) //
            .toArray();
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
        ComponentPath componentPath = ComponentPath.ROOT;
        for( final int index : indexes_ )
        {
            componentPath = new ComponentPath( componentPath, index );
        }

        return componentPath;
    }
}
