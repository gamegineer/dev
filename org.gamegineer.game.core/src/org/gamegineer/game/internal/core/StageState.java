/*
 * StageState.java
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
 * Created on Aug 1, 2008 at 11:25:53 PM.
 */

package org.gamegineer.game.internal.core;

import net.jcip.annotations.Immutable;

/**
 * Represents the mutable state of a game stage while it is active.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class StageState
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The index of the active child stage. */
    private final int m_activeChildStageIndex;

    /** The index of the previous child stage. */
    private final int m_previousChildStageIndex;

    /** The stage associated with this state. */
    private final Stage m_stage;

    /** The stage version. */
    private final StageVersion m_version;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageState} class with a clean
     * state.
     * 
     * @param stage
     *        The stage associated with this state; must not be {@code null}.
     */
    StageState(
        /* @NonNull */
        final Stage stage )
    {
        this( stage, new StageVersion(), -1, -1 );
    }

    /**
     * Initializes a new instance of the {@code StageState} class with the
     * specified state.
     * 
     * @param stage
     *        The stage associated with this state; must not be {@code null}.
     * @param version
     *        The stage version; must not be {@code null}.
     * @param activeChildStageIndex
     *        The index of the active child stage or -1 if no child stage is
     *        active; must be greater than or equal to -1 and less than the
     *        child stage count.
     * @param previousChildStageIndex
     *        The index of the previous child stage or -1 if no child stage has
     *        yet been active; must be greater than or equal to -1 and less than
     *        the child stage count.
     */
    private StageState(
        /* @NonNull */
        final Stage stage,
        /* @NonNull */
        final StageVersion version,
        final int activeChildStageIndex,
        final int previousChildStageIndex )
    {
        assert stage != null;
        assert version != null;
        assert (activeChildStageIndex >= -1) && (activeChildStageIndex < stage.getStages().size());
        assert (previousChildStageIndex >= -1) && (previousChildStageIndex < stage.getStages().size());

        m_stage = stage;
        m_version = version;
        m_activeChildStageIndex = activeChildStageIndex;
        m_previousChildStageIndex = previousChildStageIndex;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates the child stage at the specified index.
     * 
     * <p>
     * This method must not be called if a child stage is already active.
     * </p>
     * 
     * @param activeChildStageIndex
     *        The index of the active child stage; must be greater than or equal
     *        to 0 and less than the child stage count.
     * 
     * @return The modified stage state; never {@code null}.
     */
    /* @NonNull */
    StageState activateChildStage(
        final int activeChildStageIndex )
    {
        assert activeChildStageIndex != -1;
        assert m_activeChildStageIndex == -1;

        return new StageState( m_stage, m_version.increment(), activeChildStageIndex, m_previousChildStageIndex );
    }

    /**
     * Deactivates the active child stage.
     * 
     * <p>
     * This method must not be called if a child stage is not active.
     * </p>
     * 
     * @return The modified stage state; never {@code null}.
     */
    /* @NonNull */
    StageState deactivateChildStage()
    {
        assert m_activeChildStageIndex != -1;

        return new StageState( m_stage, m_version.increment(), -1, m_activeChildStageIndex );
    }

    /**
     * Gets the index of the active child stage.
     * 
     * @return The index of the active child stage or -1 if no child stage is
     *         active.
     */
    int getActiveChildStageIndex()
    {
        return m_activeChildStageIndex;
    }

    /**
     * Gets the index of the previous child stage.
     * 
     * @return The index of the previous child stage or -1 if no child stage has
     *         yet been active.
     */
    int getPreviousChildStageIndex()
    {
        return m_previousChildStageIndex;
    }

    /**
     * Gets the stage associated with this state.
     * 
     * @return The stage associated with this state; never {@code null}.
     */
    /* @NonNull */
    Stage getStage()
    {
        return m_stage;
    }

    /**
     * Gets the stage version.
     * 
     * @return The stage version.
     */
    /* @NonNull */
    StageVersion getVersion()
    {
        return m_version;
    }
}