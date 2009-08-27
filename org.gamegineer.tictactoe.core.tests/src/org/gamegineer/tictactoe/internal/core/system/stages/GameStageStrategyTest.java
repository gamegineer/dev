/*
 * GameStageStrategyTest.java
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
 * Created on Aug 16, 2009 at 8:55:21 PM.
 */

package org.gamegineer.tictactoe.internal.core.system.stages;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.tictactoe.core.SquareId;
import org.gamegineer.tictactoe.internal.core.TicTacToeGameAttributes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.system.stages.GameStageStrategy}
 * class.
 */
public final class GameStageStrategyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** An engine context for use in the test. */
    private IEngineContext m_engineContext;

    /** The game stage strategy under test in the fixture. */
    private GameStageStrategy m_strategy;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameStageStrategyTest} class.
     */
    public GameStageStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        m_strategy = new GameStageStrategy();
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
     * Ensures the {@code activate} method adds the game complete indicator
     * attribute to the engine state with the correct default value.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testActivate_GameCompleteIndicatorAdded()
        throws Exception
    {
        m_strategy.activate( createDummy( IStage.class ), m_engineContext );

        assertFalse( TicTacToeGameAttributes.GAME_COMPLETE.getValue( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code activate} method adds the square state attributes to
     * the engine state with the correct default value.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_SquareStatesAdded()
        throws Exception
    {
        m_strategy.activate( createDummy( IStage.class ), m_engineContext );

        for( final SquareId squareId : SquareId.values() )
        {
            assertNotNull( TicTacToeGameAttributes.squareState( squareId ).getValue( m_engineContext.getState() ) );
        }
    }

    /**
     * Ensures the {@code deactivate} method removes the game complete indicator
     * attribute from the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_GameCompleteIndicatorRemoved()
        throws Exception
    {
        final IStage stage = createDummy( IStage.class );
        m_strategy.activate( stage, m_engineContext );

        m_strategy.deactivate( stage, m_engineContext );

        assertFalse( TicTacToeGameAttributes.GAME_COMPLETE.isPresent( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code deactivate} method does not remove the square state
     * attributes from the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_SquareStatesNotRemoved()
        throws Exception
    {
        final IStage stage = createDummy( IStage.class );
        m_strategy.activate( stage, m_engineContext );

        m_strategy.deactivate( stage, m_engineContext );

        for( final SquareId squareId : SquareId.values() )
        {
            assertNotNull( TicTacToeGameAttributes.squareState( squareId ).getValue( m_engineContext.getState() ) );
        }
    }

    /**
     * Ensures the {@code isComplete} method returns {@code true} when the game
     * is complete.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testIsComplete_Game_Complete()
    {
        TicTacToeGameAttributes.GAME_COMPLETE.add( m_engineContext.getState(), true );

        final boolean isComplete = m_strategy.isComplete( createDummy( IStage.class ), m_engineContext );

        assertTrue( isComplete );
    }

    /**
     * Ensures the {@code isComplete} method returns {@code false} when the game
     * is incomplete.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testIsComplete_Game_Incomplete()
    {
        TicTacToeGameAttributes.GAME_COMPLETE.add( m_engineContext.getState(), false );

        final boolean isComplete = m_strategy.isComplete( createDummy( IStage.class ), m_engineContext );

        assertFalse( isComplete );
    }
}
