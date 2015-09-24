/*
 * ComponentSurfaceDesignUIRegistry.java
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
 * Created on Aug 18, 2012 at 8:19:09 PM.
 */

package org.gamegineer.table.ui;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.internal.ui.Activator;

/**
 * A facade for accessing the component surface design user interface registry.
 */
@ThreadSafe
public final class ComponentSurfaceDesignUIRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIRegistry} class.
     */
    private ComponentSurfaceDesignUIRegistry()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component surface design user interface with the specified
     * identifier.
     * 
     * @param id
     *        The component surface design identifier.
     * 
     * @return The component surface design user interface with the specified
     *         identifier.
     * 
     * @throws org.gamegineer.table.ui.NoSuchComponentSurfaceDesignUIException
     *         If {@code id} is not registered.
     */
    public static ComponentSurfaceDesignUI getComponentSurfaceDesignUI(
        final ComponentSurfaceDesignId id )
        throws NoSuchComponentSurfaceDesignUIException
    {
        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry = Activator.getDefault().getComponentSurfaceDesignUIRegistry();
        if( componentSurfaceDesignUIRegistry == null )
        {
            throw new NoSuchComponentSurfaceDesignUIException( NonNlsMessages.ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_componentSurfaceDesignUIRegistryNotAvailable );
        }

        final ComponentSurfaceDesignUI componentSurfaceDesignUI = componentSurfaceDesignUIRegistry.getObject( id );
        if( componentSurfaceDesignUI == null )
        {
            throw new NoSuchComponentSurfaceDesignUIException( NonNlsMessages.ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_unknownComponentSurfaceDesignId( id ) );
        }

        return componentSurfaceDesignUI;
    }
}
