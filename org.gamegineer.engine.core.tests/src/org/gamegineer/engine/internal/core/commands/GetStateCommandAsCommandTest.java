/*
 * GetStateCommandAsCommandTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Apr 30, 2008 at 11:34:20 PM.
 */

package org.gamegineer.engine.internal.core.commands;

import java.util.Map;
import org.gamegineer.engine.core.AbstractCommandTestCase;
import org.gamegineer.engine.core.AttributeName;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.commands.GetStateCommand} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.ICommand} interface.
 */
public final class GetStateCommandAsCommandTest
    extends AbstractCommandTestCase<GetStateCommand, Map<AttributeName, Object>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetStateCommandAsCommandTest}
     * class.
     */
    public GetStateCommandAsCommandTest()
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
    protected GetStateCommand createCommand()
    {
        return new GetStateCommand();
    }
}
