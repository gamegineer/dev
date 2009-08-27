/*
 * StageTest.java
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
 * Created on Nov 15, 2008 at 10:01:52 PM.
 */

package org.gamegineer.game.internal.core.system;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.core.system.FakeStageStrategy;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.Stage} class.
 */
public final class StageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default stage cardinality. */
    private static final int DEFAULT_CARDINALITY = 0;

    /** The default stage identifier. */
    private static final String DEFAULT_IDENTIFIER = "id"; //$NON-NLS-1$

    /** The default stage list. */
    private static final List<IStage> DEFAULT_STAGES = Collections.emptyList();

    /** The default stage strategy. */
    private static final IStageStrategy DEFAULT_STRATEGY = new FakeStageStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageTest} class.
     */
    public StageTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createStage} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateStage_Id_Null()
    {
        Stage.createStage( null, DEFAULT_STRATEGY, DEFAULT_CARDINALITY, DEFAULT_STAGES );
    }

    /**
     * Ensures the {@code createStage} method throws an exception when one or
     * more of its arguments results in an illegal stage.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal stage attributes is located in the
     * {@code GameSystemUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateStage_Stage_Illegal()
    {
        final IStage illegalStage = GameSystems.createIllegalStage();

        Stage.createStage( illegalStage.getId(), illegalStage.getStrategy(), illegalStage.getCardinality(), illegalStage.getStages() );
    }

    /**
     * Ensures the {@code createStage} method makes a copy of the stage list.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testCreateStage_Stages_Copy()
    {
        final List<IStage> stages = new ArrayList<IStage>();
        final Stage stage = Stage.createStage( DEFAULT_IDENTIFIER, DEFAULT_STRATEGY, DEFAULT_CARDINALITY, stages );
        final int originalStagesSize = stages.size();

        stages.add( null );

        assertEquals( originalStagesSize, stage.getStages().size() );
    }

    /**
     * Ensures the {@code createStage} method throws an exception when passed a
     * {@code null} stage list.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateStage_Stages_Null()
    {
        Stage.createStage( DEFAULT_IDENTIFIER, DEFAULT_STRATEGY, DEFAULT_CARDINALITY, null );
    }

    /**
     * Ensures the {@code createStage} method throws an exception when passed a
     * {@code null} strategy.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateStage_Strategy_Null()
    {
        Stage.createStage( DEFAULT_IDENTIFIER, null, DEFAULT_CARDINALITY, DEFAULT_STAGES );
    }
}
