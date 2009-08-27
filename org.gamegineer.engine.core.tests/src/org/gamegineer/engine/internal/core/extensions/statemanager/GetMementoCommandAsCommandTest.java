/*
 * GetMementoCommandAsCommandTest.java
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
 * Created on Jul 2, 2008 at 10:57:25 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.AbstractCommandTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.statemanager.GetMementoCommand}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.ICommand} interface.
 */
public final class GetMementoCommandAsCommandTest
    extends AbstractCommandTestCase<GetMementoCommand, IMemento>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetMementoCommandAsCommandTest}
     * class.
     */
    public GetMementoCommandAsCommandTest()
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
    protected GetMementoCommand createCommand()
    {
        return new GetMementoCommand();
    }
}
