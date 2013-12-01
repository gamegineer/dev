/*
 * IComponentSurfaceDesignUIRegistry.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Apr 23, 2012 at 7:39:35 PM.
 */

package org.gamegineer.table.ui;

import org.gamegineer.common.core.util.registry.IRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A service for the management and discovery of component surface design user
 * interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IComponentSurfaceDesignUIRegistry
    extends IRegistry<ComponentSurfaceDesignId, ComponentSurfaceDesignUI>
{
    // ======================================================================
    // Methods
    // ======================================================================
}
