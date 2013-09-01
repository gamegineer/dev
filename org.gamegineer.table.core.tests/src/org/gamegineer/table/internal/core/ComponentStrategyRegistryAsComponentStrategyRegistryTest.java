/*
 * ComponentStrategyRegistryAsComponentStrategyRegistryTest.java
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
 * Created on Aug 3, 2012 at 11:05:34 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.common.core.util.registry.IRegistry;
import org.gamegineer.table.core.AbstractComponentStrategyRegistryTestCase;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ComponentStrategyRegistry} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.IComponentStrategyRegistry} interface.
 */
public final class ComponentStrategyRegistryAsComponentStrategyRegistryTest
    extends AbstractComponentStrategyRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyRegistryAsComponentStrategyRegistryTest} class.
     */
    public ComponentStrategyRegistryAsComponentStrategyRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected IRegistry<ComponentStrategyId, IComponentStrategy> createRegistry()
    {
        return new ComponentStrategyRegistry();
    }
}
