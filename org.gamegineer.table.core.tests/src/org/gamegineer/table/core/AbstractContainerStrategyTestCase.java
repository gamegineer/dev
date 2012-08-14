/*
 * AbstractContainerStrategyTestCase.java
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
 * Created on Aug 1, 2012 at 7:55:23 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IContainerStrategy} interface.
 * 
 * @param <ContainerStrategyType>
 *        The type of the container strategy.
 */
public abstract class AbstractContainerStrategyTestCase<ContainerStrategyType extends IContainerStrategy>
    extends AbstractComponentStrategyTestCase<ContainerStrategyType>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractContainerStrategyTestCase} class.
     */
    protected AbstractContainerStrategyTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container strategy under test in the fixture.
     * 
     * @return The container strategy under test in the fixture; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final ContainerStrategyType getContainerStrategy()
    {
        return getComponentStrategy();
    }

    /**
     * Ensures the {@code getDefaultLayout} method does not return {@code null}.
     */
    @Test
    public void testGetDefaultLayout_ReturnValue_NonNull()
    {
        assertNotNull( getContainerStrategy().getDefaultLayout() );
    }
}