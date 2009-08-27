/*
 * GameAttributes.java
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
 * Created on Jul 23, 2008 at 9:33:27 PM.
 */

package org.gamegineer.game.internal.core;

import java.util.Collections;
import java.util.List;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.util.attribute.Attribute;

// TODO: It's possible that this class will need to be moved to an external
// package for use by other game command developers whose specialized game
// command implementations may need to access these attributes directly from
// an engine context.  On the other hand, they will probably have commands
// available to them (e.g. GetGameNameCommand) that they can execute in-place
// to get the same information.

/**
 * A collection of engine attributes defined by all games.
 */
public final class GameAttributes
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The current player index attribute. */
    public static final Attribute<Integer> CURRENT_PLAYER_INDEX = new Attribute<Integer>( Scope.APPLICATION, "org.gamegineer.game.currentPlayerIndex" ); //$NON-NLS-1$

    /** The current round identifier attribute. */
    public static final Attribute<Integer> CURRENT_ROUND_ID = new Attribute<Integer>( Scope.APPLICATION, "org.gamegineer.game.currentRoundId" ) //$NON-NLS-1$
    {
        @Override
        @SuppressWarnings( "boxing" )
        protected boolean isLegalValue(
            final Integer value )
        {
            return value > 0;
        }
    };

    /** The game complete attribute. */
    public static final Attribute<Boolean> GAME_COMPLETE = new Attribute<Boolean>( Scope.APPLICATION, "org.gamegineer.game.gameComplete" ); //$NON-NLS-1$

    /** The game identifier attribute. */
    public static final Attribute<String> GAME_ID = new Attribute<String>( Scope.APPLICATION, "org.gamegineer.game.gameId" ); //$NON-NLS-1$

    /** The game name attribute. */
    public static final Attribute<String> GAME_NAME = new Attribute<String>( Scope.APPLICATION, "org.gamegineer.game.gameName" ); //$NON-NLS-1$

    /** The game system identifier attribute. */
    public static final Attribute<String> GAME_SYSTEM_ID = new Attribute<String>( Scope.APPLICATION, "org.gamegineer.game.gameSystemId" ); //$NON-NLS-1$

    /** The player list attribute. */
    public static final Attribute<List<Player>> PLAYER_LIST = new Attribute<List<Player>>( Scope.APPLICATION, "org.gamegineer.game.playerList" ) //$NON-NLS-1$
    {
        @Override
        protected List<Player> decorateValue(
            final List<Player> value )
        {
            return Collections.unmodifiableList( value );
        }
    };

    /** The root stage attribute. */
    public static final Attribute<Stage> ROOT_STAGE = new Attribute<Stage>( Scope.APPLICATION, "org.gamegineer.game.rootStage" ); //$NON-NLS-1$

    /** The round complete attribute. */
    public static final Attribute<Boolean> ROUND_COMPLETE = new Attribute<Boolean>( Scope.APPLICATION, "org.gamegineer.game.roundComplete" ); //$NON-NLS-1$

    /** The turn complete attribute. */
    public static final Attribute<Boolean> TURN_COMPLETE = new Attribute<Boolean>( Scope.APPLICATION, "org.gamegineer.game.turnComplete" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameAttributes} class.
     */
    private GameAttributes()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the stage state attribute for the specified stage.
     * 
     * @param stageId
     *        The stage identifier; must not be {@code null}.
     * 
     * @return The stage state attribute for the specified stage; never
     *         {@code null}.
     */
    /* @NonNull */
    static Attribute<StageState> stageState(
        /* @NonNull */
        final String stageId )
    {
        assert stageId != null;

        return new Attribute<StageState>( Scope.APPLICATION, String.format( "org.gamegineer.game.stage-%1$s.state", stageId ) ); //$NON-NLS-1$
    }
}
