/*
 * CardTest.java
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
 * Created on Oct 11, 2009 at 10:00:09 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.CardDesign;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Card}
 * class.
 */
public final class CardTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardTest} class.
     */
    public CardTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * back design.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_BackDesign_Null()
    {
        new Card( null, CardDesign.EMPTY );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * face design.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_FaceDesign_Null()
    {
        new Card( CardDesign.EMPTY, null );
    }
}
