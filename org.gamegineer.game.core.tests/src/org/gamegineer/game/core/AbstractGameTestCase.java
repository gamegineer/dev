/*
 * AbstractGameTestCase.java
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
 * Created on Jul 17, 2008 at 11:41:50 PM.
 */

package org.gamegineer.game.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.EnumSet;
import java.util.List;
import org.gamegineer.engine.core.AbstractEngineTestCase;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.commandhistory.CommandHistoryFacade;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.GameConfigurationBuilder;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.system.AbstractStageStrategy;
import org.gamegineer.game.core.system.GameSystemBuilder;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.StageBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.IGame} interface.
 * 
 * @param <T>
 *        The type of the game.
 */
public abstract class AbstractGameTestCase<T extends IGame>
    extends AbstractEngineTestCase<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game configuration for the game under test. */
    private IGameConfiguration m_gameConfig;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractGameTestCase} class.
     */
    protected AbstractGameTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#createEngine()
     */
    @Override
    protected final T createEngine()
        throws Exception
    {
        assertNotNull( m_gameConfig );
        return createGame( m_gameConfig );
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#createEngine(org.gamegineer.engine.core.ICommand)
     */
    @Override
    protected final T createEngine(
        @SuppressWarnings( "unused" )
        final ICommand<?> command )
        throws Exception
    {
        // NB: see comment in testCreateEngineFromCommand_CommandNotAddedToHistory
        fail( "not supported" ); //$NON-NLS-1$
        return null;
    }

    /**
     * Creates the game to be tested.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}.
     * 
     * @return The game to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code gameConfig} is {@code null}.
     */
    /* @NonNull */
    protected abstract T createGame(
        /* @NonNull */
        IGameConfiguration gameConfig )
        throws Exception;

    /**
     * Creates a game configuration that represents a game that will never
     * complete.
     * 
     * @return A game configuration that represents a game that will never
     *         complete; never {@code null}.
     */
    /* @NonNull */
    private static IGameConfiguration createPerpetualGameConfiguration()
    {
        final StageBuilder stageBuilder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.STRATEGY );
        stageBuilder.setStrategy( new AbstractStageStrategy()
        {
            public boolean isComplete(
                @SuppressWarnings( "unused" )
                final IStage stage,
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                return false;
            }
        } );

        final GameSystemBuilder gameSystemBuilder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.STAGES );
        gameSystemBuilder.addStage( stageBuilder.toStage() );
        final IGameSystem gameSystem = gameSystemBuilder.toGameSystem();

        final GameConfigurationBuilder gameConfigBuilder = Configurations.createIncompleteGameConfigurationBuilder( EnumSet.<Configurations.GameConfigurationAttribute>of( Configurations.GameConfigurationAttribute.SYSTEM, Configurations.GameConfigurationAttribute.PLAYERS ) );
        gameConfigBuilder.setSystem( gameSystem );
        gameConfigBuilder.addPlayers( Configurations.createPlayerConfigurationList( gameSystem ) );
        return gameConfigBuilder.toGameConfiguration();
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#getCommandHistory(org.gamegineer.engine.core.IEngine)
     */
    @Override
    protected final List<IInvertibleCommand<?>> getCommandHistory(
        final T engine )
    {
        return CommandHistoryFacade.getCommandHistory( engine );
    }

    /**
     * Gets the game under test in the fixture.
     * 
     * @return The game under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getGame()
    {
        return getEngine();
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        m_gameConfig = Configurations.createUniqueGameConfiguration();

        super.setUp();
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        m_gameConfig = null;
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#testCreateEngineFromCommand_CommandNotAddedToHistory()
     */
    @Override
    public void testCreateEngineFromCommand_CommandNotAddedToHistory()
        throws Exception
    {
        // TODO: For now we've disabled this test because there is no way to
        // provide an initialization command to a game.  This functionality
        // doesn't seem to be necessary now, but we may change this in the
        // future, in which case we will re-enable this test for games.
    }

    /**
     * Ensures the {@code getId} method returns the expected identifier.
     */
    @Test
    public void testGetId()
    {
        assertEquals( m_gameConfig.getId(), getGame().getId() );
    }

    /**
     * Ensures the {@code getName} method returns the expected name.
     */
    @Test
    public void testGetName()
    {
        assertEquals( m_gameConfig.getName(), getGame().getName() );
    }

    /**
     * Ensures the {@code getSystemId} method returns the expected identifier.
     */
    @Test
    public void testGetSystemId()
    {
        assertEquals( m_gameConfig.getSystem().getId(), getGame().getSystemId() );
    }

    /**
     * Ensures the {@code isComplete} method returns {@code true} when the game
     * is complete.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testIsComplete_Complete()
        throws Exception
    {
        boolean isComplete = false;
        while( !isComplete )
        {
            Thread.sleep( 1 );
            isComplete = getGame().isComplete();
        }

        assertTrue( isComplete );
    }

    /**
     * Ensures the {@code isComplete} method returns {@code false} when the game
     * is incomplete.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsComplete_Incomplete()
        throws Exception
    {
        final IGame game = createGame( createPerpetualGameConfiguration() );

        try
        {
            final boolean isComplete = game.isComplete();

            assertFalse( isComplete );
        }
        finally
        {
            game.shutdown();
        }
    }
}
