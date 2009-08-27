/*
 * GetGameIdCommandAsCommandTest.java
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
 * Created on Jan 6, 2009 at 11:58:41 PM.
 */

package org.gamegineer.game.internal.core.commands;

import org.gamegineer.engine.core.AbstractCommandTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.commands.GetGameIdCommand} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.ICommand} interface.
 */
public final class GetGameIdCommandAsCommandTest
    extends AbstractCommandTestCase<GetGameIdCommand, String>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetGameIdCommandAsCommandTest}
     * class.
     */
    public GetGameIdCommandAsCommandTest()
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
    protected GetGameIdCommand createCommand()
    {
        return new GetGameIdCommand();
    }
}
