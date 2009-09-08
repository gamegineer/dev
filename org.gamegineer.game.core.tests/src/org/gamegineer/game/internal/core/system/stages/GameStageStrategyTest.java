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
 * Created on Sep 2, 2008 at 10:10:06 PM.
 */

package org.gamegineer.game.internal.core.system.stages;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.internal.core.GameAttributes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.stages.GameStageStrategy}
 * class.
 */
public final class GameStageStrategyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** An engine context for use in the test. */
    private IEngineContext engineContext_;

    /** The game stage strategy under test in the fixture. */
    private GameStageStrategy strategy_;


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
        engineContext_ = new FakeEngineContext();
        strategy_ = new GameStageStrategy();
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
        strategy_.activate( createDummy( IStage.class ), engineContext_ );

        assertFalse( GameAttributes.GAME_COMPLETE.getValue( engineContext_.getState() ) );
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
        strategy_.activate( stage, engineContext_ );

        strategy_.deactivate( stage, engineContext_ );

        assertFalse( GameAttributes.GAME_COMPLETE.isPresent( engineContext_.getState() ) );
    }

    /**
     * Ensures the {@code isComplete} method returns {@code true} when the game
     * is complete.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testIsComplete_Game_Complete()
    {
        GameAttributes.GAME_COMPLETE.add( engineContext_.getState(), true );

        final boolean isComplete = strategy_.isComplete( createDummy( IStage.class ), engineContext_ );

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
        GameAttributes.GAME_COMPLETE.add( engineContext_.getState(), false );

        final boolean isComplete = strategy_.isComplete( createDummy( IStage.class ), engineContext_ );

        assertFalse( isComplete );
    }
}
