/*
 * GetGameNameCommandAsCommandTest.java
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
 * Created on Nov 29, 2008 at 9:28:36 PM.
 */

package org.gamegineer.game.internal.core.commands;

import org.gamegineer.engine.core.AbstractCommandTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.commands.GetGameNameCommand} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.ICommand} interface.
 */
public final class GetGameNameCommandAsCommandTest
    extends AbstractCommandTestCase<GetGameNameCommand, String>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetGameNameCommandAsCommandTest}
     * class.
     */
    public GetGameNameCommandAsCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractCommandTestCase#createCommand()
     */
    @Override
    protected GetGameNameCommand createCommand()
    {
        return new GetGameNameCommand();
    }
}
