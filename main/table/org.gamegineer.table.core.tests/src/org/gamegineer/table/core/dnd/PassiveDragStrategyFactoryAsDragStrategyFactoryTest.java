/*
 * PassiveDragStrategyFactoryAsDragStrategyFactoryTest.java
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
 * Created on Jun 27, 2013 at 11:40:16 PM.
 */

package org.gamegineer.table.core.dnd;

import org.gamegineer.table.core.dnd.test.AbstractDragStrategyFactoryTestCase;

/**
 * A fixture for testing the {@link PassiveDragStrategyFactory} class to ensure
 * it does not violate the contract of the {@link IDragStrategyFactory}
 * interface.
 */
public final class PassiveDragStrategyFactoryAsDragStrategyFactoryTest
    extends AbstractDragStrategyFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code PassiveDragStrategyFactoryAsDragStrategyFactoryTest} class.
     */
    public PassiveDragStrategyFactoryAsDragStrategyFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.test.AbstractDragStrategyFactoryTestCase#createDragStrategyFactory()
     */
    @Override
    protected IDragStrategyFactory createDragStrategyFactory()
    {
        return PassiveDragStrategyFactory.INSTANCE;
    }
}
