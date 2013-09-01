/*
 * AbstractComponentStrategyRegistryTestCase.java
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
 * Created on Aug 3, 2012 at 10:32:45 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponentStrategyRegistry} interface.
 */
public abstract class AbstractComponentStrategyRegistryTestCase
    extends AbstractRegistryTestCase<ComponentStrategyId, IComponentStrategy>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyRegistryTestCase} class.
     */
    protected AbstractComponentStrategyRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#cloneObject(java.lang.Object)
     */
    @Override
    protected final IComponentStrategy cloneObject(
        final IComponentStrategy object )
    {
        return TestComponentStrategies.cloneComponentStrategy( object );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#createUniqueObject()
     */
    @Override
    protected final IComponentStrategy createUniqueObject()
    {
        return TestComponentStrategies.createUniqueComponentStrategy();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#getObjectId(java.lang.Object)
     */
    @Override
    protected final ComponentStrategyId getObjectId(
        final IComponentStrategy object )
    {
        return object.getId();
    }
}
