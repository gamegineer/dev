/*
 * DefaultComponentStrategyUIFactoryTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Sep 29, 2012 at 10:10:12 PM.
 */

package org.gamegineer.table.internal.ui.impl.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.gamegineer.table.core.test.TestComponentStrategies;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IContainerStrategyUI;
import org.junit.Test;

/**
 * A fixture for testing the {@link DefaultComponentStrategyUIFactory} class.
 */
public final class DefaultComponentStrategyUIFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultComponentStrategyUIFactoryTest} class.
     */
    public DefaultComponentStrategyUIFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the
     * {@link DefaultComponentStrategyUIFactory#createDefaultComponentStrategyUI}
     * method returns a component strategy user interface when passed a
     * component strategy.
     */
    @Test
    public void testCreateDefaultComponentStrategyUI_ComponentStrategy_ComponentStrategy()
    {
        final IComponentStrategyUI actualValue = DefaultComponentStrategyUIFactory.createDefaultComponentStrategyUI( TestComponentStrategies.createUniqueComponentStrategy() );

        assertFalse( actualValue instanceof IContainerStrategyUI );
    }

    /**
     * Ensures the
     * {@link DefaultComponentStrategyUIFactory#createDefaultComponentStrategyUI}
     * method returns a container strategy user interface when passed a
     * container strategy.
     */
    @Test
    public void testCreateDefaultComponentStrategyUI_ComponentStrategy_ContainerStrategy()
    {
        final IComponentStrategyUI actualValue = DefaultComponentStrategyUIFactory.createDefaultComponentStrategyUI( TestComponentStrategies.createUniqueContainerStrategy() );

        assertTrue( actualValue instanceof IContainerStrategyUI );
    }
}
