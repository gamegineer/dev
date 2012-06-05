/*
 * ComponentModelFactory.java
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
 * Created on Jun 1, 2012 at 8:59:25 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * A factory for creating component models.
 */
@ThreadSafe
public final class ComponentModelFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelFactory} class.
     */
    private ComponentModelFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component model for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return A new component model for the specified component; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    /* @NonNull */
    public static ComponentModel createComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        if( component instanceof IContainer )
        {
            return createContainerModel( (IContainer)component );
        }

        return new ComponentModel( component );
    }

    /**
     * Creates a new container model for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @return A new container model for the specified container; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    /* @NonNull */
    public static ContainerModel createContainerModel(
        /* @NonNull */
        final IContainer container )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$

        return new ContainerModel( container );
    }
}
