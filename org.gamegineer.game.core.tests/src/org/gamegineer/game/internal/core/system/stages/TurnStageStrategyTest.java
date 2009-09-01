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
    private IEngineContext m_engineContext;

    /** The turn stage strategy under test in the fixture. */
    private TurnStageStrategy m_strategy;


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

        final List<Player> playerList = new ArrayList<Player>( playerCount );
        for( int index = 0; index < playerCount; ++index )
        {
            playerList.add( Players.createUniquePlayer() );
        }

        return playerList;
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
        m_engineContext = new FakeEngineContext();
        m_strategy = new TurnStageStrategy();
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
        m_strategy = null;
        m_engineContext = null;
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
        GameAttributes.PLAYER_LIST.add( m_engineContext.getState(), createPlayerList() );

        m_strategy.activate( createDummy( IStage.class ), m_engineContext );

        assertEquals( 0, GameAttributes.CURRENT_PLAYER_INDEX.getValue( m_engineContext.getState() ).intValue() );
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
        GameAttributes.PLAYER_LIST.add( m_engineContext.getState(), createPlayerList( PLAYER_COUNT ) );
        final IStage stage = createDummy( IStage.class );
        m_strategy.activate( stage, m_engineContext );
        m_strategy.deactivate( stage, m_engineContext );

        m_strategy.activate( stage, m_engineContext );

        assertEquals( 1, GameAttributes.CURRENT_PLAYER_INDEX.getValue( m_engineContext.getState() ).intValue() );
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
        GameAttributes.PLAYER_LIST.add( m_engineContext.getState(), createPlayerList( PLAYER_COUNT ) );
        GameAttributes.ROUND_COMPLETE.add( m_engineContext.getState(), false );
        final IStage stage = createDummy( IStage.class );
        for( int turn = 0; turn < PLAYER_COUNT; ++turn )
        {
            m_strategy.activate( stage, m_engineContext );
            m_strategy.deactivate( stage, m_engineContext );
        }

        m_strategy.activate( stage, m_engineContext );

        assertTrue( GameAttributes.ROUND_COMPLETE.getValue( m_engineContext.getState() ) );
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
        m_strategy.activate( createDummy( IStage.class ), m_engineContext );

        assertFalse( GameAttributes.TURN_COMPLETE.getValue( m_engineContext.getState() ) );
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
        m_strategy.activate( stage, m_engineContext );

        m_strategy.deactivate( stage, m_engineContext );

        assertTrue( GameAttributes.CURRENT_PLAYER_INDEX.isPresent( m_engineContext.getState() ) );
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
        m_strategy.activate( stage, m_engineContext );

        m_strategy.deactivate( stage, m_engineContext );

        assertFalse( GameAttributes.TURN_COMPLETE.isPresent( m_engineContext.getState() ) );
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
        m_strategy.activate( stage, m_engineContext );
        GameAttributes.TURN_COMPLETE.setValue( m_engineContext.getState(), true );

        final boolean isComplete = m_strategy.isComplete( stage, m_engineContext );

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
        m_strategy.activate( stage, m_engineContext );

        final boolean isComplete = m_strategy.isComplete( stage, m_engineContext );

        assertFalse( isComplete );
    }
}
