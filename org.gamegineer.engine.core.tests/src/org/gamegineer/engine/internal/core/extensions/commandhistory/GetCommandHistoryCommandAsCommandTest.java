/*
 * GetCommandHistoryCommandAsCommandTest.java
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
 * Created on May 2, 2008 at 10:46:06 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import java.util.List;
import org.gamegineer.engine.core.AbstractCommandTestCase;
import org.gamegineer.engine.core.IInvertibleCommand;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.GetCommandHistoryCommand}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.ICommand} interface.
 */
public final class GetCommandHistoryCommandAsCommandTest
    extends AbstractCommandTestCase<GetCommandHistoryCommand, List<IInvertibleCommand<?>>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GetCommandHistoryCommandAsCommandTest} class.
     */
    public GetCommandHistoryCommandAsCommandTest()
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
    protected GetCommandHistoryCommand createCommand()
    {
        return new GetCommandHistoryCommand();
    }
}
