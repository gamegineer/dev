/*
 * GetGameCommandletAsCommandletHelpTest.java
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
 * Created on Jan 15, 2009 at 10:59:29 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import org.gamegineer.client.ui.console.commandlet.AbstractCommandletHelpTestCase;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.server.GetGameCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandletHelp}
 * interface.
 */
public final class GetGameCommandletAsCommandletHelpTest
    extends AbstractCommandletHelpTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GetGameCommandletAsCommandletHelpTest} class.
     */
    public GetGameCommandletAsCommandletHelpTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.AbstractCommandletHelpTestCase#createCommandletHelp()
     */
    @Override
    protected ICommandletHelp createCommandletHelp()
    {
        return new GetGameCommandlet();
    }
}
