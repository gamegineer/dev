/*
 * PlayerTest.java
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
 * Created on Jul 21, 2008 at 10:55:31 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.game.core.system.IRole;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.core.Player}
 * class.
 */
public final class PlayerTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerTest} class.
     */
    public PlayerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * role.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Role_Null()
    {
        new Player( null, "user-id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * user identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_UserId_Null()
    {
        new Player( createDummy( IRole.class ), null );
    }
}
