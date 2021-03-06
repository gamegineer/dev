/*
 * DefaultDragStrategyAsDragStrategyTest.java
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
 * Created on Mar 8, 2013 at 10:05:47 PM.
 */

package org.gamegineer.table.core.dnd;

import org.easymock.EasyMock;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.dnd.test.AbstractDragStrategyTestCase;

/**
 * A fixture for testing the {@link DefaultDragStrategy} class to ensure it does
 * not violate the contract of the {@link IDragStrategy} interface.
 */
public final class DefaultDragStrategyAsDragStrategyTest
    extends AbstractDragStrategyTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultDragStrategyAsDragStrategyTest} class.
     */
    public DefaultDragStrategyAsDragStrategyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.test.AbstractDragStrategyTestCase#createDragStrategy()
     */
    @Override
    protected IDragStrategy createDragStrategy()
    {
        return new DefaultDragStrategy( EasyMock.createMock( IComponent.class ) );
    }
}
