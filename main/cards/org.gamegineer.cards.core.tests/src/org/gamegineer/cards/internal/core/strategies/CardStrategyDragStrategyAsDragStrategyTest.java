/*
 * CardStrategyDragStrategyAsDragStrategyTest.java
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
 * Created on Jun 27, 2013 at 11:55:42 PM.
 */

package org.gamegineer.cards.internal.core.strategies;

import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.dnd.IDragStrategy;
import org.gamegineer.table.core.dnd.test.AbstractDragStrategyTestCase;
import org.gamegineer.table.core.impl.TableEnvironmentFactory;
import org.gamegineer.table.core.test.TestComponents;

/**
 * A fixture for testing the {@link CardStrategy.DragStrategy} class to ensure
 * it does not violate the contract of the {@link IDragStrategy} interface.
 */
public final class CardStrategyDragStrategyAsDragStrategyTest
    extends AbstractDragStrategyTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardStrategyDragStrategyAsDragStrategyTest} class.
     */
    public CardStrategyDragStrategyAsDragStrategyTest()
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
        final ITableEnvironment tableEnvironment = TableEnvironmentFactory.createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
        final ITable table = tableEnvironment.createTable();
        final IContainer container = TestComponents.createUniqueContainer( tableEnvironment );
        table.getTabletop().addComponent( container );
        final IComponent component = TestComponents.createUniqueComponent( tableEnvironment );
        container.addComponent( component );
        return new CardStrategy.DragStrategy( component );
    }
}
