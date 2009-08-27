/*
 * GameSystemUiSourceFactoryTest.java
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
 * Created on Mar 7, 2009 at 9:06:01 PM.
 */

package org.gamegineer.client.core.system;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.core.system.GameSystemUiSourceFactory} class.
 */
public final class GameSystemUiSourceFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiSourceFactoryTest}
     * class.
     */
    public GameSystemUiSourceFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createRegistryGameSystemUiSource} method does not
     * return {@code null}.
     */
    @Test
    public void testCreateRegistryGameSystemUiSource_ReturnValue_NonNull()
    {
        assertNotNull( GameSystemUiSourceFactory.createRegistryGameSystemUiSource() );
    }
}
