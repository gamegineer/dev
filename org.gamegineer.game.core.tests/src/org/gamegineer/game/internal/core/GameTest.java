/*
 * GameTest.java
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
 * Created on Jul 17, 2008 at 11:41:29 PM.
 */

package org.gamegineer.game.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.MockCommands;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.GameConfigurationBuilder;
import org.gamegineer.game.core.system.GameSystemBuilder;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.MockStageStrategy;
import org.gamegineer.game.core.system.StageBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.core.Game}
 * class.
 */
public final class GameTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game under test in the fixture. */
    private Game m_game;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameTest} class.
     */
    public GameTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a stage strategy that always indicates the stage is not complete.
     * 
     * @return A stage strategy that always indicates the stage is not complete;
     *         never {@code null}.
     */
    /* @NonNull */
    private static MockStageStrategy createIncompleteStageStrategy()
    {
        return new MockStageStrategy()
        {
            @Override
            public boolean isComplete(
                final IStage stage,
                final IEngineContext context )
            {
                super.isComplete( stage, context );

                return false;
            }
        };
    }

    /**
     * Creates a game with one stage using the specified stage strategy.
     * 
     * <p>
     * This method will block until the one stage has become the active stage.
     * </p>
     * 
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * 
     * @return The game; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Game createOneStageGame(
        /* @NonNull */
        final MockStageStrategy strategy )
        throws Exception
    {
        assert strategy != null;

        final GameSystemBuilder gameSystemBuilder = new GameSystemBuilder();
        gameSystemBuilder.setId( "game-system-id" ); //$NON-NLS-1$
        gameSystemBuilder.addRole( GameSystems.createUniqueRole() );
        final StageBuilder stageBuilder = GameSystems.createUniqueStageBuilder();
        stageBuilder.setStrategy( strategy );
        gameSystemBuilder.addStage( stageBuilder.toStage() );
        final IGameSystem gameSystem = gameSystemBuilder.toGameSystem();

        final GameConfigurationBuilder gameConfigBuilder = new GameConfigurationBuilder();
        gameConfigBuilder.setId( "game-id" ).setName( "game-name" ).setSystem( gameSystem ); //$NON-NLS-1$ //$NON-NLS-2$
        gameConfigBuilder.addPlayers( Configurations.createPlayerConfigurationList( gameSystem ) );
        final Game game = Game.createGame( gameConfigBuilder.toGameConfiguration() );

        // XXX: This smells bad, but it can't be helped due to the asynchronous
        // nature of the game/engine.  We will attempt to refactor it later
        // using patterns applicable for asynchronous testing.  We may have to
        // resort to using a test-specific Stage class (i.e. make Stage
        // abstract).
        waitForStageStrategyActivation( game, strategy );
        return game;
    }

    /**
     * Creates a command that will modify the game state.
     * 
     * @return A command that will modify the game state; never {@code null}.
     */
    /* @NonNull */
    private static ICommand<?> createWriteCommand()
    {
        return MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), new Object() ); //$NON-NLS-1$
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
        m_game = null;
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
        if( m_game != null )
        {
            m_game.shutdown();
            m_game = null;
        }
    }

    /**
     * Ensures the {@code commandExecuted} method delegates all calls to the
     * active stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testCommandExecuted_DelegateToActiveStage()
        throws Exception
    {
        final MockStageStrategy strategy = createIncompleteStageStrategy();
        m_game = createOneStageGame( strategy );

        final int originalCommandExecutedCallCount = strategy.getCommandExecutedCallCount();
        m_game.executeCommand( new MockCommand<Void>() );

        assertEquals( originalCommandExecutedCallCount + 1, strategy.getCommandExecutedCallCount() );
    }

    /**
     * Ensures the {@code commandExecuting} method delegates all calls to the
     * active stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testCommandExecuting_DelegateToActiveStage()
        throws Exception
    {
        final MockStageStrategy strategy = createIncompleteStageStrategy();
        m_game = createOneStageGame( strategy );

        final int originalCommandExecutingCallCount = strategy.getCommandExecutingCallCount();
        m_game.executeCommand( new MockCommand<Void>() );

        assertEquals( originalCommandExecutingCallCount + 1, strategy.getCommandExecutingCallCount() );
    }

    /**
     * Ensures the {@code createGame} method throws an exception when passed a
     * {@code null} game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testCreateGame_GameConfig_Null()
        throws Exception
    {
        Game.createGame( null );
    }

    /**
     * Ensures the {@code stateChanged} method delegates all calls to the active
     * stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testStateChanged_DelegateToActiveStage()
        throws Exception
    {
        final MockStageStrategy strategy = createIncompleteStageStrategy();
        m_game = createOneStageGame( strategy );

        final int originalStateChangedCallCount = strategy.getStateChangedCallCount();
        m_game.executeCommand( createWriteCommand() );

        assertEquals( originalStateChangedCallCount + 1, strategy.getStateChangedCallCount() );
    }

    /**
     * Ensures the {@code stateChanging} method delegates all calls to the
     * active stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testStateChanging_DelegateToActiveStage()
        throws Exception
    {
        final MockStageStrategy strategy = createIncompleteStageStrategy();
        m_game = createOneStageGame( strategy );

        final int originalStateChangingCallCount = strategy.getStateChangingCallCount();
        m_game.executeCommand( createWriteCommand() );

        assertEquals( originalStateChangingCallCount + 1, strategy.getStateChangingCallCount() );
    }

    /**
     * Waits a limited amount of time for the specified stage strategy to be
     * activated.
     * 
     * @param game
     *        The game; must not be {@code null}.
     * @param stageStrategy
     *        The stage strategy; must not be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    private static void waitForStageStrategyActivation(
        /* @NonNull */
        final Game game,
        /* @NonNull */
        final MockStageStrategy stageStrategy )
        throws Exception
    {
        assert game != null;
        assert stageStrategy != null;

        final ICommand<Boolean> isStageStrategyActiveCommand = new AbstractCommand<Boolean>()
        {
            public Boolean execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                return stageStrategy.getActivateCallCount() > 0;
            }
        };

        boolean isStageStrategyActive = false;
        final long WAIT_TIME = 1000;
        final long startTime = System.currentTimeMillis();
        while( (System.currentTimeMillis() - startTime) < WAIT_TIME )
        {
            if( game.executeCommand( isStageStrategyActiveCommand ) )
            {
                isStageStrategyActive = true;
                break;
            }
        }

        assertTrue( isStageStrategyActive );
    }
}
