/*
 * DefaultDragStrategyFactoryAsDragStrategyFactoryTest.java
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
 * Created on Mar 9, 2013 at 9:01:31 PM.
 */

package org.gamegineer.table.core;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.DefaultDragStrategyFactory} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.core.IDragStrategyFactory} interface.
 */
public final class DefaultDragStrategyFactoryAsDragStrategyFactoryTest
    extends AbstractDragStrategyFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultDragStrategyFactoryAsDragStrategyFactoryTest} class.
     */
    public DefaultDragStrategyFactoryAsDragStrategyFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractDragStrategyFactoryTestCase#createDragStrategyFactory()
     */
    @Override
    protected IDragStrategyFactory createDragStrategyFactory()
    {
        return new DefaultDragStrategyFactory();
    }
}
