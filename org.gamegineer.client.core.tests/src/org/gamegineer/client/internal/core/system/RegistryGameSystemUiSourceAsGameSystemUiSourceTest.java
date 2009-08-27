/*
 * RegistryGameSystemUiSourceAsGameSystemUiSourceTest.java
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
 * Created on Mar 7, 2009 at 9:07:35 PM.
 */

package org.gamegineer.client.internal.core.system;

import org.gamegineer.client.core.system.AbstractGameSystemUiSourceTestCase;
import org.gamegineer.client.core.system.IGameSystemUiSource;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.system.RegistryGameSystemUiSource}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.core.system.IGameSystemUiSource} interface.
 */
public final class RegistryGameSystemUiSourceAsGameSystemUiSourceTest
    extends AbstractGameSystemUiSourceTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code RegistryGameSystemUiSourceAsGameSystemUiSourceTest} class.
     */
    public RegistryGameSystemUiSourceAsGameSystemUiSourceTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.system.AbstractGameSystemUiSourceTestCase#createGameSystemUiSource()
     */
    @Override
    protected IGameSystemUiSource createGameSystemUiSource()
    {
        return new RegistryGameSystemUiSource();
    }
}
