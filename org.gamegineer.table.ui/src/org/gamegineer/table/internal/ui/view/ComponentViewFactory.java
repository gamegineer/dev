/*
 * ComponentViewFactory.java
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
 * Created on Jun 4, 2012 at 7:34:24 PM.
 */

package org.gamegineer.table.internal.ui.view;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.gamegineer.table.internal.ui.model.ContainerModel;

/**
 * A factory for creating component views.
 */
@ThreadSafe
final class ComponentViewFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentViewFactory} class.
     */
    private ComponentViewFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component view for the specified component model.
     * 
     * @param componentModel
     *        The component model; must not be {@code null}.
     * 
     * @return A new component view for the specified component model; never
     *         {@code null}.
     */
    /* @NonNull */
    static ComponentView createComponentView(
        /* @NonNull */
        final ComponentModel componentModel )
    {
        assert componentModel != null;

        if( componentModel instanceof ContainerModel )
        {
            return createContainerView( (ContainerModel)componentModel );
        }

        return new ComponentView( componentModel );
    }

    /**
     * Creates a new container view for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @return A new container view for the specified container model; never
     *         {@code null}.
     */
    /* @NonNull */
    static ContainerView createContainerView(
        /* @NonNull */
        final ContainerModel containerModel )
    {
        assert containerModel != null;

        return new ContainerView( containerModel );
    }
}
