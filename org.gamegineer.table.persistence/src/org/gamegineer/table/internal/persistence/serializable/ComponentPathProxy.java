/*
 * ComponentPathProxy.java
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
 * Created on Jun 14, 2012 at 10:00:47 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.Serializable;
import java.util.List;
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
     * The collection of component indexes ordered from the root-most ancestor
     * component to the leaf-most component.
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
        indexes_ = null;
    }

    /**
     * Initializes a new instance of the {@code ComponentPathProxy} class from
     * the specified {@code ComponentPath} instance.
     * 
     * @param componentPath
     *        The {@code ComponentPath} instance; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentPath} is {@code null}.
     */
    public ComponentPathProxy(
        /* @NonNull */
        final ComponentPath componentPath )
    {
        assertArgumentNotNull( componentPath, "componentPath" ); //$NON-NLS-1$

        final List<ComponentPath> componentPaths = componentPath.toList();
        indexes_ = new int[ componentPaths.size() ];
        for( int index = 0; index < indexes_.length; ++index )
        {
            indexes_[ index ] = componentPaths.get( index ).getIndex();
        }
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
        assert indexes_.length > 0;

        ComponentPath componentPath = null;
        for( int index = 0; index < indexes_.length; ++index )
        {
            componentPath = new ComponentPath( componentPath, indexes_[ index ] );
        }

        return componentPath;
    }
}
