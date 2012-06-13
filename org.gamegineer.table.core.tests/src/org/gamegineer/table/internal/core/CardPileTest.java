/*
 * CardPileTest.java
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
 * Created on Jun 12, 2012 at 8:08:48 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class.
 */
public final class CardPileTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileTest} class.
     */
    public CardPileTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getPath} method returns the correct value when the
     * card pile is associated with a table.
     */
    @Test
    public void testGetPath()
    {
        final ITableEnvironment tableEnvironment = TableEnvironmentFactory.createTableEnvironment();
        final ITable table = tableEnvironment.createTable();
        final ICardPile cardPile1 = tableEnvironment.createCardPile();
        table.addCardPile( cardPile1 );
        final ICardPile cardPile2 = tableEnvironment.createCardPile();
        table.addCardPile( cardPile2 );
        final ICardPile cardPile3 = tableEnvironment.createCardPile();
        table.addCardPile( cardPile3 );
        final ComponentPath expectedComponentPath1 = new ComponentPath( null, 0 );
        final ComponentPath expectedComponentPath2 = new ComponentPath( null, 1 );
        final ComponentPath expectedComponentPath3 = new ComponentPath( null, 2 );

        final ComponentPath actualComponentPath1 = cardPile1.getPath();
        final ComponentPath actualComponentPath2 = cardPile2.getPath();
        final ComponentPath actualComponentPath3 = cardPile3.getPath();

        assertEquals( expectedComponentPath1, actualComponentPath1 );
        assertEquals( expectedComponentPath2, actualComponentPath2 );
        assertEquals( expectedComponentPath3, actualComponentPath3 );
    }
}
