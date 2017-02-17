/*
 * CardOrientationTest.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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

package org.gamegineer.cards.core;

import static org.junit.Assert.assertEquals;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

/**
 * A fixture for testing the {@link CardOrientation} enumeration.
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
     * Ensures the {@link CardOrientation#inverse} method returns the correct
     * value for the {@link CardOrientation#BACK} value.
     */
    @Test
    public void testInverse_Back()
    {
        assertEquals( CardOrientation.FACE, CardOrientation.BACK.inverse() );
    }

    /**
     * Ensures the {@link CardOrientation#inverse} method returns the correct
     * value for the {@link CardOrientation#FACE} value.
     */
    @Test
    public void testInverse_Face()
    {
        assertEquals( CardOrientation.BACK, CardOrientation.FACE.inverse() );
    }

    /**
     * Ensures the {@link CardOrientation#inverse} method supports all known
     * enumeration values.
     */
    @Test
    public void testInverse_NoUnsupportedValues()
    {
        for( final CardOrientation orientation : CardOrientation.<@NonNull CardOrientation>values( CardOrientation.class ) )
        {
            orientation.inverse();
        }
    }
}
