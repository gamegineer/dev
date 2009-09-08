/*
 * StageStateTest.java
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
 * Created on Aug 1, 2008 at 11:26:07 PM.
 */

package org.gamegineer.game.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.StageState} class.
 */
public final class StageStateTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage associated with the stage state under test. */
    private Stage stage_;

    /** The stage state under test in the fixture. */
    private StageState state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageStateTest} class.
     */
    public StageStateTest()
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
        stage_ = Stages.createUniqueStageWithChildren();
        state_ = new StageState( stage_ );
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
        state_ = null;
        stage_ = null;
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of maximum negative value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MaxNegativeValue()
    {
        state_.activateChildStage( -1 );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of maximum positive value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MaxPositiveValue()
    {
        state_.activateChildStage( Integer.MAX_VALUE );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of minimum negative value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MinNegativeValue()
    {
        state_.activateChildStage( Integer.MIN_VALUE );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of minimum positive value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MinPositiveValue()
    {
        state_.activateChildStage( stage_.getStages().size() );
    }

    /**
     * Ensures the {@code activateChildStage} method does not throw an exception
     * when passed a legal index of minimum value.
     */
    @Test
    public void testActivateChildStage_Index_Legal_MaxValue()
    {
        state_.activateChildStage( stage_.getStages().size() - 1 );
    }

    /**
     * Ensures the {@code activateChildStage} method does not throw an exception
     * when passed a legal index of minimum value.
     */
    @Test
    public void testActivateChildStage_Index_Legal_MinValue()
    {
        state_.activateChildStage( 0 );
    }

    /**
     * Ensures the {@code activateChildStage} method returns a stage state with
     * a new version.
     */
    @Test
    public void testActivateChildStage_NewVersion()
    {
        final StageState newState = state_.activateChildStage( 0 );

        assertFalse( state_.getVersion().equals( newState.getVersion() ) );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * there is already an active child stage.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_State_Illegal()
    {
        state_.activateChildStage( 0 ).activateChildStage( 1 );
    }

    /**
     * Ensures the {@code activateChildStage} method returns a new stage state
     * that reflects the new active child stage index.
     */
    @Test
    public void testActivateChildStage_State_Legal()
    {
        final StageState newState = state_.activateChildStage( 0 );

        assertEquals( 0, newState.getActiveChildStageIndex() );
        assertEquals( -1, newState.getPreviousChildStageIndex() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * stage.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Stage_Null()
    {
        new StageState( null );
    }

    /**
     * Ensures the {@code deactivateChildStage} method returns a stage state
     * with a new version.
     */
    @Test
    public void testDeactivateChildStage_NewVersion()
    {
        final StageState initialState = state_.activateChildStage( 0 );

        final StageState newState = initialState.deactivateChildStage();

        assertFalse( initialState.getVersion().equals( newState.getVersion() ) );
    }

    /**
     * Ensures the {@code deactivateChildStage} method throws an exception when
     * there is no active child stage.
     */
    @Test( expected = AssertionError.class )
    public void testDeactivateChildStage_State_Illegal()
    {
        state_.deactivateChildStage();
    }

    /**
     * Ensures the {@code deactivateChildStage} method returns a new stage state
     * that reflects the new active and previous child stage indexes.
     */
    @Test
    public void testDeactivateChildStage_State_Legal()
    {
        final StageState newState = state_.activateChildStage( 0 ).deactivateChildStage();

        assertEquals( -1, newState.getActiveChildStageIndex() );
        assertEquals( 0, newState.getPreviousChildStageIndex() );
    }
}
