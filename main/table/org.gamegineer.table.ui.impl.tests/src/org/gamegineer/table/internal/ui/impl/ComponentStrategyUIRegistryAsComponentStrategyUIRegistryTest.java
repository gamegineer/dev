/*
 * ComponentStrategyUIRegistryAsComponentStrategyUIRegistryTest.java
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
 * Created on Sep 27, 2012 at 8:59:41 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import org.gamegineer.common.core.util.registry.IRegistry;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IComponentStrategyUIRegistry;
import org.gamegineer.table.ui.test.AbstractComponentStrategyUIRegistryTestCase;

/**
 * A fixture for testing the {@link ComponentStrategyUIRegistry} class to ensure
 * it does not violate the contract of the {@link IComponentStrategyUIRegistry}
 * interface.
 */
public final class ComponentStrategyUIRegistryAsComponentStrategyUIRegistryTest
    extends AbstractComponentStrategyUIRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyUIRegistryAsComponentStrategyUIRegistryTest}
     * class.
     */
    public ComponentStrategyUIRegistryAsComponentStrategyUIRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected IRegistry<ComponentStrategyId, IComponentStrategyUI> createRegistry()
    {
        return new ComponentStrategyUIRegistry();
    }
}
