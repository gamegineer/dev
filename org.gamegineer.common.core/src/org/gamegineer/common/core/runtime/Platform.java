/*
 * Platform.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Sep 15, 2008 at 10:29:47 PM.
 */

package org.gamegineer.common.core.runtime;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.common.core.services.component.IComponentService;
import org.gamegineer.common.internal.core.Services;

/**
 * A collection of useful methods for clients running on the Gamegineer
 * platform.
 */
public final class Platform
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Platform} class.
     */
    private Platform()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the platform component service.
     * 
     * @return The platform component service; never {@code null}.
     */
    /* @NonNull */
    public static IComponentService getComponentService()
    {
        return Services.getDefault().getComponentService();
    }

    /**
     * Gets the platform extension registry.
     * 
     * @return The platform extension registry; never {@code null}.
     */
    /* @NonNull */
    public static IExtensionRegistry getExtensionRegistry()
    {
        return Services.getDefault().getExtensionRegistry();
    }
}
