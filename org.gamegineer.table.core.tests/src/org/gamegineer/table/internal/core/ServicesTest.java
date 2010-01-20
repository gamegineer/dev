/*
 * ServicesTest.java
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
 * Created on Sep 15, 2009 at 11:31:52 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Services}
 * class.
 */
public final class ServicesTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServicesTest} class.
     */
    public ServicesTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getCardDesignRegistry} method does not return {@code
     * null}, which validates the card design registry service was registered
     * with OSGi correctly.
     */
    @Test
    public void testGetCardDesignRegistry_ReturnValue_NonNull()
    {
        assertNotNull( Services.getDefault().getCardDesignRegistry() );
    }

    /**
     * Ensures the {@code getCardPileDesignRegistry} method does not return
     * {@code null}, which validates the card pile design registry service was
     * registered with OSGi correctly.
     */
    @Test
    public void testGetCardPileDesignRegistry_ReturnValue_NonNull()
    {
        assertNotNull( Services.getDefault().getCardPileDesignRegistry() );
    }

    /**
     * Ensures the {@code open} method throws an exception when passed a {@code
     * null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_Context_Null()
    {
        Services.getDefault().open( null );
    }
}
