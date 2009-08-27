/*
 * NullCommandletAsCommandletTest.java
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
 * Created on Oct 14, 2008 at 10:55:21 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.core;

import org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.core.NullCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public final class NullCommandletAsCommandletTest
    extends AbstractCommandletTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullCommandletAsCommandletTest}
     * class.
     */
    public NullCommandletAsCommandletTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase#createCommandlet()
     */
    @Override
    protected ICommandlet createCommandlet()
    {
        return new NullCommandlet();
    }
}
