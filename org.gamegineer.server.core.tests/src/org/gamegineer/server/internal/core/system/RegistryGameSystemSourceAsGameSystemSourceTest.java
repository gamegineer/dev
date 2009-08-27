/*
 * RegistryGameSystemSourceAsGameSystemSourceTest.java
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
 * Created on Feb 22, 2009 at 9:54:40 PM.
 */

package org.gamegineer.server.internal.core.system;

import org.gamegineer.server.core.system.AbstractGameSystemSourceTestCase;
import org.gamegineer.server.core.system.IGameSystemSource;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.system.RegistryGameSystemSource}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.server.core.system.IGameSystemSource} interface.
 */
public final class RegistryGameSystemSourceAsGameSystemSourceTest
    extends AbstractGameSystemSourceTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code RegistryGameSystemSourceAsGameSystemSourceTest} class.
     */
    public RegistryGameSystemSourceAsGameSystemSourceTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.server.core.config.AbstractGameSystemSourceTestCase#createGameSystemSource()
     */
    @Override
    protected IGameSystemSource createGameSystemSource()
    {
        return new RegistryGameSystemSource();
    }
}
