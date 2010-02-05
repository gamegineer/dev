/*
 * CardPileFactoryTest.java
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
 * Created on Jan 14, 2010 at 11:43:34 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.CardPileFactory}
 * class.
 */
public final class CardPileFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileFactoryTest} class.
     */
    public CardPileFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCardPile} method throws an exception when passed
     * a {@code null} card pile base design.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardPile_BaseDesign_Null()
    {
        CardPileFactory.createCardPile( null );
    }

    /**
     * Ensures the {@code createCardPile} method does not return {@code null}.
     */
    @Test
    public void testCreateCardPile_ReturnValue_NonNull()
    {
        assertNotNull( CardPileFactory.createCardPile( CardPileBaseDesigns.createUniqueCardPileBaseDesign() ) );
    }
}
