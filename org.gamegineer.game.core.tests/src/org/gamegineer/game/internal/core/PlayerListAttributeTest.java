/*
 * PlayerListAttributeTest.java
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
 * Created on Sep 6, 2008 at 11:39:40 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import java.util.ArrayList;
import org.gamegineer.engine.core.FakeState;
import org.gamegineer.engine.core.IState;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.GameAttributes#PLAYER_LIST} class.
 */
public final class PlayerListAttributeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerListAttributeTest} class.
     */
    public PlayerListAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code add} method adds the attribute value to the engine
     * state as an immutable collection.
     */
    @Test
    public void testAdd_Value_Immutable()
    {
        final IState state = new FakeState();

        GameAttributes.PLAYER_LIST.add( state, new ArrayList<Player>() );

        assertImmutableCollection( GameAttributes.PLAYER_LIST.getValue( state ) );
    }

    /**
     * Ensures the {@code setValue} method adds the attribute value to the
     * engine state as an immutable collection.
     */
    @Test
    public void testSetValue_Value_Immutable()
    {
        final IState state = new FakeState();
        GameAttributes.PLAYER_LIST.add( state, new ArrayList<Player>() );

        GameAttributes.PLAYER_LIST.setValue( state, new ArrayList<Player>() );

        assertImmutableCollection( GameAttributes.PLAYER_LIST.getValue( state ) );
    }
}
