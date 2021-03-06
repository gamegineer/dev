/*
 * IContainerLayoutRegistry.java
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
 * Created on Aug 9, 2012 at 8:09:51 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.common.core.util.registry.IRegistry;

/**
 * A service for the management and discovery of container layouts.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IContainerLayoutRegistry
    extends IRegistry<ContainerLayoutId, IContainerLayout>
{
    // ======================================================================
    // Methods
    // ======================================================================
}
