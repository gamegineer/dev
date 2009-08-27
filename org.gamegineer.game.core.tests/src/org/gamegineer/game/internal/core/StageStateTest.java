/*
 * StageStateTest.java
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
    private Stage m_stage;

    /** The stage state under test in the fixture. */
    private StageState m_state;


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
        m_stage = Stages.createUniqueStageWithChildren();
        m_state = new StageState( m_stage );
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
        m_state = null;
        m_stage = null;
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of maximum negative value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MaxNegativeValue()
    {
        m_state.activateChildStage( -1 );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of maximum positive value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MaxPositiveValue()
    {
        m_state.activateChildStage( Integer.MAX_VALUE );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of minimum negative value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MinNegativeValue()
    {
        m_state.activateChildStage( Integer.MIN_VALUE );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * passed an illegal index of minimum positive value.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_Index_Illegal_MinPositiveValue()
    {
        m_state.activateChildStage( m_stage.getStages().size() );
    }

    /**
     * Ensures the {@code activateChildStage} method does not throw an exception
     * when passed a legal index of minimum value.
     */
    @Test
    public void testActivateChildStage_Index_Legal_MaxValue()
    {
        m_state.activateChildStage( m_stage.getStages().size() - 1 );
    }

    /**
     * Ensures the {@code activateChildStage} method does not throw an exception
     * when passed a legal index of minimum value.
     */
    @Test
    public void testActivateChildStage_Index_Legal_MinValue()
    {
        m_state.activateChildStage( 0 );
    }

    /**
     * Ensures the {@code activateChildStage} method returns a stage state with
     * a new version.
     */
    @Test
    public void testActivateChildStage_NewVersion()
    {
        final StageState newState = m_state.activateChildStage( 0 );

        assertFalse( m_state.getVersion().equals( newState.getVersion() ) );
    }

    /**
     * Ensures the {@code activateChildStage} method throws an exception when
     * there is already an active child stage.
     */
    @Test( expected = AssertionError.class )
    public void testActivateChildStage_State_Illegal()
    {
        m_state.activateChildStage( 0 ).activateChildStage( 1 );
    }

    /**
     * Ensures the {@code activateChildStage} method returns a new stage state
     * that reflects the new active child stage index.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testActivateChildStage_State_Legal()
    {
        final StageState newState = m_state.activateChildStage( 0 );

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
        final StageState initialState = m_state.activateChildStage( 0 );

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
        m_state.deactivateChildStage();
    }

    /**
     * Ensures the {@code deactivateChildStage} method returns a new stage state
     * that reflects the new active and previous child stage indexes.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testDeactivateChildStage_State_Legal()
    {
        final StageState newState = m_state.activateChildStage( 0 ).deactivateChildStage();

        assertEquals( -1, newState.getActiveChildStageIndex() );
        assertEquals( 0, newState.getPreviousChildStageIndex() );
    }
}
