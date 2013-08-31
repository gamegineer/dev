/*
 * ComponentStrategyRegistryAsAbstractRegistryTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Nov 16, 2012 at 10:10:44 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryTestCase;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ComponentStrategyRegistry} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.util.registry.AbstractRegistry} class.
 */
public final class ComponentStrategyRegistryAsAbstractRegistryTest
    extends AbstractAbstractRegistryTestCase<ComponentStrategyRegistry, ComponentStrategyId, IComponentStrategy>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyRegistryAsAbstractRegistryTest} class.
     */
    public ComponentStrategyRegistryAsAbstractRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected ComponentStrategyRegistry createRegistry()
    {
        return new ComponentStrategyRegistry();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryTestCase#getObjectId(org.gamegineer.common.core.util.registry.AbstractRegistry, java.lang.Object)
     */
    @Override
    protected ComponentStrategyId getObjectId(
        final ComponentStrategyRegistry registry,
        final IComponentStrategy object )
    {
        return registry.getObjectId( object );
    }
}
