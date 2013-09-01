/*
 * AbstractComponentStrategyUIRegistryTestCase.java
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
 * Created on Sep 25, 2012 at 8:09:16 PM.
 */

package org.gamegineer.table.ui;

import org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.TestComponentStrategies;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.IComponentStrategyUIRegistry} interface.
 */
public abstract class AbstractComponentStrategyUIRegistryTestCase
    extends AbstractRegistryTestCase<ComponentStrategyId, IComponentStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyUIRegistryTestCase} class.
     */
    protected AbstractComponentStrategyUIRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#cloneObject(java.lang.Object)
     */
    @Override
    protected IComponentStrategyUI cloneObject(
        final IComponentStrategyUI object )
    {
        return TestComponentStrategyUIs.cloneComponentStrategyUI( object );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#createUniqueObject()
     */
    @Override
    protected IComponentStrategyUI createUniqueObject()
    {
        return TestComponentStrategyUIs.createComponentStrategyUI( TestComponentStrategies.createUniqueComponentStrategy().getId() );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#getObjectId(java.lang.Object)
     */
    @Override
    protected ComponentStrategyId getObjectId(
        final IComponentStrategyUI object )
    {
        return object.getId();
    }
}
