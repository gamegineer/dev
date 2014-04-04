/*
 * ComponentSurfaceDesignRegistry.java
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
 * Created on Aug 16, 2012 at 9:20:04 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Activator;

/**
 * A facade for accessing the component surface design registry.
 */
@ThreadSafe
public final class ComponentSurfaceDesignRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignRegistry}
     * class.
     */
    private ComponentSurfaceDesignRegistry()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component surface design with the specified identifier.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The component surface design with the specified identifier; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.table.core.NoSuchComponentSurfaceDesignException
     *         If {@code id} is not registered.
     */
    public static ComponentSurfaceDesign getComponentSurfaceDesign(
        final ComponentSurfaceDesignId id )
        throws NoSuchComponentSurfaceDesignException
    {
        final IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry = Activator.getDefault().getComponentSurfaceDesignRegistry();
        if( componentSurfaceDesignRegistry == null )
        {
            throw new NoSuchComponentSurfaceDesignException( NonNlsMessages.ComponentSurfaceDesignRegistry_getComponentSurfaceDesign_componentSurfaceDesignRegistryNotAvailable );
        }

        final ComponentSurfaceDesign componentSurfaceDesign = componentSurfaceDesignRegistry.getObject( id );
        if( componentSurfaceDesign == null )
        {
            throw new NoSuchComponentSurfaceDesignException( NonNlsMessages.ComponentSurfaceDesignRegistry_getComponentSurfaceDesign_unknownComponentSurfaceDesignId( id ) );
        }

        return componentSurfaceDesign;
    }
}
