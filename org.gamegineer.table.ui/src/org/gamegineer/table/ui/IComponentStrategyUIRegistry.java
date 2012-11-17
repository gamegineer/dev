/*
 * IComponentStrategyUIRegistry.java
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
 * Created on Sep 25, 2012 at 8:05:19 PM.
 */

package org.gamegineer.table.ui;

import org.gamegineer.common.core.util.registry.IRegistry;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * A service for the management and discovery of component strategy user
 * interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IComponentStrategyUIRegistry
    extends IRegistry<ComponentStrategyId, IComponentStrategyUI>
{
    // ======================================================================
    // Methods
    // ======================================================================
}
