/*
 * TurnStageStrategyTest.java
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
 * Created on Sep 12, 2008 at 10:07:25 PM.
 */

package org.gamegineer.game.internal.core.system.stages;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.internal.core.GameAttributes;
import org.gamegineer.game.internal.core.Player;
import org.gamegineer.game.internal.core.Players;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.stages.TurnStageStrategy}
 * class.
 */
public final class TurnStageStrategyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** An engine context for use in the test. */
    private IEngineContext engineContext_;

    /** The turn stage strategy under test in the fixture. */
    private TurnStageStrategy strategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TurnStageStrategyTest} class.
     */
    public TurnStageStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a player list suitable for use as the value of the {@code
     * PLAYER_LIST} attribute.
     * 
     * @return A player list; never {@code null}.
     */
    /* @NonNull */
    private static List<Player> createPlayerList()
    {
        return createPlayerList( 2 );
    }

    /**
     * Creates a player list with the specified number of players suitable for
     * use as the value of the {@code PLAYER_LIST} attribute.
     * 
     * @param playerCount
     *        The count of players in the list; must be greater than zero.
     * 
     * @return A player list; never {@code null}.
     */
    /* @NonNull */
    private static List<Player> createPlayerList(
        final int playerCount )
    {
        assert playerCount > 0;

        final List<Player> players = new ArrayList<Player>( playerCount );
        for( int index = 0; index < playerCount; ++index )
        {
            players.add( Players.createUniquePlayer() );
        }

        return players;
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        engineContext_ = new FakeEngineContext();
        strategy_ = new TurnStageStrategy();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        strategy_ = null;
        engineContext_ = null;
    }

    /**
     * Ensures the {@code activate} method adds the current player index
     * attribute to the engine state with the correct default value on its first
     * call.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_CurrentPlayerIndexAdded()
        throws Exception
    {
        GameAttributes.PLAYER_LIST.add( engineContext_.getState(), createPlayerList() );

        strategy_.activate( createDummy( IStage.class ), engineContext_ );

        assertEquals( 0, GameAttributes.CURRENT_PLAYER_INDEX.getValue( engineContext_.getState() ).intValue() );
    }

    /**
     * Ensures the {@code activate} method increments the current player index
     * attribute on subsequent calls.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_CurrentPlayerIndexIncremented()
        throws Exception
    {
        final int PLAYER_COUNT = 2;
        GameAttributes.PLAYER_LIST.add( engineContext_.getState(), createPlayerList( PLAYER_COUNT ) );
        final IStage stage = createDummy( IStage.class );
        strategy_.activate( stage, engineContext_ );
        strategy_.deactivate( stage, engineContext_ );

        strategy_.activate( stage, engineContext_ );

        assertEquals( 1, GameAttributes.CURRENT_PLAYER_INDEX.getValue( engineContext_.getState() ).intValue() );
    }

    /**
     * Ensures the {@code activate} method sets the round complete indicator
     * after the last player has finished their turn.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testActivate_RoundCompleteIndicatorSetAfterLastPlayer()
        throws Exception
    {
        final int PLAYER_COUNT = 2;
        GameAttributes.PLAYER_LIST.add( engineContext_.getState(), createPlayerList( PLAYER_COUNT ) );
        GameAttributes.ROUND_COMPLETE.add( engineContext_.getState(), false );
        final IStage stage = createDummy( IStage.class );
        for( int turn = 0; turn < PLAYER_COUNT; ++turn )
        {
            strategy_.activate( stage, engineContext_ );
            strategy_.deactivate( stage, engineContext_ );
        }

        strategy_.activate( stage, engineContext_ );

        assertTrue( GameAttributes.ROUND_COMPLETE.getValue( engineContext_.getState() ) );
    }

    /**
     * Ensures the {@code activate} method adds the turn complete indicator
     * attribute to the engine state with the correct default value.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testActivate_TurnCompleteIndicatorAdded()
        throws Exception
    {
        strategy_.activate( createDummy( IStage.class ), engineContext_ );

        assertFalse( GameAttributes.TURN_COMPLETE.getValue( engineContext_.getState() ) );
    }

    /**
     * Ensures the {@code deactivate} method does not remove the current player
     * index attribute from the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_CurrentPlayerIndexNotRemoved()
        throws Exception
    {
        final IStage stage = createDummy( IStage.class );
        strategy_.activate( stage, engineContext_ );

        strategy_.deactivate( stage, engineContext_ );

        assertTrue( GameAttributes.CURRENT_PLAYER_INDEX.isPresent( engineContext_.getState() ) );
    }

    /**
     * Ensures the {@code deactivate} method removes the turn complete indicator
     * attribute from the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_TurnCompleteIndicatorRemoved()
        throws Exception
    {
        final IStage stage = createDummy( IStage.class );
        strategy_.activate( stage, engineContext_ );

        strategy_.deactivate( stage, engineContext_ );

        assertFalse( GameAttributes.TURN_COMPLETE.isPresent( engineContext_.getState() ) );
    }

    /**
     * Ensures the {@code isComplete} method returns {@code true} when the turn
     * is complete.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testIsComplete_Turn_Complete()
        throws Exception
    {
        final IStage stage = createDummy( IStage.class );
        strategy_.activate( stage, engineContext_ );
        GameAttributes.TURN_COMPLETE.setValue( engineContext_.getState(), true );

        final boolean isComplete = strategy_.isComplete( stage, engineContext_ );

        assertTrue( isComplete );
    }

    /**
     * Ensures the {@code isComplete} method returns {@code false} when the turn
     * is incomplete.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsComplete_Turn_Incomplete()
        throws Exception
    {
        final IStage stage = createDummy( IStage.class );
        strategy_.activate( stage, engineContext_ );

        final boolean isComplete = strategy_.isComplete( stage, engineContext_ );

        assertFalse( isComplete );
    }
}
