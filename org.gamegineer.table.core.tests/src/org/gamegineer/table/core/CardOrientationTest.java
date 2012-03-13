/*
 * CardOrientationTest.java
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
 * Created on Oct 11, 2009 at 11:13:44 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.CardOrientation}
 * enumeration.
 */
public final class CardOrientationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardOrientationTest} class.
     */
    public CardOrientationTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code inverse} method returns the correct value for the
     * {@code BACK_UP} value.
     */
    @Test
    public void testInverse_BackUp()
    {
        assertEquals( CardOrientation.FACE_UP, CardOrientation.BACK_UP.inverse() );
    }

    /**
     * Ensures the {@code inverse} method returns the correct value for the
     * {@code FACE_UP} value.
     */
    @Test
    public void testInverse_FaceUp()
    {
        assertEquals( CardOrientation.BACK_UP, CardOrientation.FACE_UP.inverse() );
    }

    /**
     * Ensures the {@code inverse} method supports all known enumeration values.
     */
    @Test
    public void testInverse_NoUnsupportedValues()
    {
        for( final CardOrientation orientation : CardOrientation.values() )
        {
            orientation.inverse();
        }
    }
}
