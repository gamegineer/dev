/*
 * CardPileOrientationTest.java
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
 * Created on Apr 12, 2012 at 8:02:46 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.CardPileOrientation} enumeration.
 */
public final class CardPileOrientationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileOrientationTest} class.
     */
    public CardPileOrientationTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code inverse} method returns the correct value for the
     * {@link CardPileOrientation#DEFAULT} value.
     */
    @Test
    public void testInverse_Default()
    {
        assertEquals( CardPileOrientation.DEFAULT, CardPileOrientation.DEFAULT.inverse() );
    }

    /**
     * Ensures the {@code inverse} method supports all known enumeration values.
     */
    @Test
    public void testInverse_NoUnsupportedValues()
    {
        for( final CardPileOrientation orientation : CardPileOrientation.values( CardPileOrientation.class ) )
        {
            orientation.inverse();
        }
    }
}
