/*
 * StateAsStateTest.java
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
 * Created on Feb 21, 2008 at 9:42:45 PM.
 */

package org.gamegineer.engine.internal.core;

import org.gamegineer.engine.core.AbstractStateTestCase;
import org.gamegineer.engine.core.IState;

/**
 * A fixture for testing the {@link org.gamegineer.engine.internal.core.State}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IState} interface.
 */
public final class StateAsStateTest
    extends AbstractStateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateAsStateTest} class.
     */
    public StateAsStateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractStateTestCase#createState()
     */
    @Override
    protected IState createState()
    {
        return new TransactionalState( new State() );
    }
}
