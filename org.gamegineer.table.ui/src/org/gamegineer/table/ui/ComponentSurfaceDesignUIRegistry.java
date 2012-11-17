/*
 * ComponentSurfaceDesignUIRegistry.java
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
 * Created on Aug 18, 2012 at 8:19:09 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
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
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The component surface design user interface with the specified
     *         identifier; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     * @throws org.gamegineer.table.ui.NoSuchComponentSurfaceDesignUIException
     *         If {@code id} is not registered.
     */
    /* @NonNull */
    public static ComponentSurfaceDesignUI getComponentSurfaceDesignUI(
        /* @NonNull */
        final ComponentSurfaceDesignId id )
        throws NoSuchComponentSurfaceDesignUIException
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry = Activator.getDefault().getComponentSurfaceDesignUIRegistry();
        if( componentSurfaceDesignUIRegistry == null )
        {
            throw new NoSuchComponentSurfaceDesignUIException( NonNlsMessages.ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_componentSurfaceDesignUIRegistryNotAvailable );
        }

        final ComponentSurfaceDesignUI componentSurfaceDesignUI = componentSurfaceDesignUIRegistry.get( id );
        if( componentSurfaceDesignUI == null )
        {
            throw new NoSuchComponentSurfaceDesignUIException( NonNlsMessages.ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_unknownComponentSurfaceDesignId( id ) );
        }

        return componentSurfaceDesignUI;
    }
}
