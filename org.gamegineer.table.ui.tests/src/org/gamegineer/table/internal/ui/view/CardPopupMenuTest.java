/*
 * CardPopupMenuTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Dec 22, 2009 at 10:05:24 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.CardPopupMenu} class.
 */
public final class CardPopupMenuTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPopupMenuTest} class.
     */
    public CardPopupMenuTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Card_Null()
    {
        new CardPopupMenu( createDummy( ITable.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Table_Null()
    {
        new CardPopupMenu( null, createDummy( ICard.class ) );
    }
}
