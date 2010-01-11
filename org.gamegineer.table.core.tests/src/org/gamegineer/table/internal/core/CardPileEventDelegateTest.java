/*
 * CardPileEventDelegateTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jan 10, 2010 at 10:29:04 PM.
 */

package org.gamegineer.table.internal.core;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardPileEventDelegate} class.
 */
public final class CardPileEventDelegateTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileEventDelegateTest}
     * class.
     */
    public CardPileEventDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card pile.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_CardPile_Null()
    {
        new CardPileEventDelegate( null );
    }
}
