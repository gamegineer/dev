/*
 * ComponentStrategyUIRegistryAsAbstractRegistryTest.java
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
 * Created on Nov 16, 2012 at 10:28:24 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import org.gamegineer.common.core.util.registry.AbstractRegistry;
import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryTestCase;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.IComponentStrategyUI;

/**
 * A fixture for testing the {@link ComponentStrategyUIRegistry} class to ensure
 * it does not violate the contract of the {@link AbstractRegistry} class.
 */
public final class ComponentStrategyUIRegistryAsAbstractRegistryTest
    extends AbstractAbstractRegistryTestCase<ComponentStrategyUIRegistry, ComponentStrategyId, IComponentStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyUIRegistryAsAbstractRegistryTest} class.
     */
    public ComponentStrategyUIRegistryAsAbstractRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected ComponentStrategyUIRegistry createRegistry()
    {
        return new ComponentStrategyUIRegistry();
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryTestCase#getObjectId(org.gamegineer.common.core.util.registry.AbstractRegistry, java.lang.Object)
     */
    @Override
    protected ComponentStrategyId getObjectId(
        final ComponentStrategyUIRegistry registry,
        final IComponentStrategyUI object )
    {
        return registry.getObjectId( object );
    }
}
