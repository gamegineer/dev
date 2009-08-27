/*
 * FakeStateAsStateTest.java
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
 * Created on Jun 25, 2008 at 12:16:15 AM.
 */

package org.gamegineer.engine.core;

/**
 * A fixture for testing the {@link org.gamegineer.engine.core.FakeState} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IState} interface.
 */
public final class FakeStateAsStateTest
    extends AbstractStateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeStateAsStateTest} class.
     */
    public FakeStateAsStateTest()
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
        return new FakeState();
    }
}
