/*
 * StageBuilderTest.java
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
 * Created on Nov 15, 2008 at 9:14:59 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.game.core.system.Assert.assertStageEquals;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.system.StageBuilder} class.
 */
public final class StageBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage builder under test in the fixture. */
    private StageBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageBuilderTest} class.
     */
    public StageBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal stage list for the specified stage builder.
     * 
     * <p>
     * It is assumed the specified stage builder is complete except for the
     * stage list attribute.
     * </p>
     * 
     * @param builder
     *        The stage builder; must not be {@code null}.
     * 
     * @return A legal stage list for the specified stage builder; never
     *         {@code null}.
     */
    /* @NonNull */
    private static List<IStage> createLegalStageList(
        /* @NonNull */
        final StageBuilder builder )
    {
        assert builder != null;

        return GameSystems.createUniqueStageList( 0 );
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
        m_builder = new StageBuilder();
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
        m_builder = null;
    }

    /**
     * Ensures the {@code addStage} method adds a stage to the resulting stage.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddStage_AddsStage()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.STAGES );
        final List<IStage> expectedStages = createLegalStageList( builder );

        for( final IStage stage : expectedStages )
        {
            builder.addStage( stage );
        }

        final IStage stage = builder.toStage();
        final List<IStage> actualStages = stage.getStages();
        assertEquals( expectedStages.size(), actualStages.size() );
        for( int index = 0; index < expectedStages.size(); ++index )
        {
            assertStageEquals( expectedStages.get( index ), actualStages.get( index ) );
        }
    }

    /**
     * Ensures the {@code addStage} method throws an exception when passed a
     * {@code null} stage.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStage_Stage_Null()
    {
        m_builder.addStage( null );
    }

    /**
     * Ensures the {@code addStage} method returns the same builder instance.
     */
    @Test
    public void testAddStage_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addStage( createDummy( IStage.class ) ) );
    }

    /**
     * Ensures the {@code addStages} method adds a collection of stages to the
     * resulting stage.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddStages_AddsStages()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.STAGES );
        final List<IStage> expectedStages = createLegalStageList( builder );

        builder.addStages( expectedStages );

        final IStage stage = builder.toStage();
        final List<IStage> actualStages = stage.getStages();
        assertEquals( expectedStages.size(), actualStages.size() );
        for( int index = 0; index < expectedStages.size(); ++index )
        {
            assertStageEquals( expectedStages.get( index ), actualStages.get( index ) );
        }
    }

    /**
     * Ensures the {@code addStages} method throws an exception when passed a
     * {@code null} stage list.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStages_Stages_Null()
    {
        m_builder.addStages( null );
    }

    /**
     * Ensures the {@code addStages} method returns the same builder instance.
     */
    @Test
    public void testAddStages_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addStages( Collections.<IStage>emptyList() ) );
    }

    /**
     * Ensures the {@code setCardinality} method returns the same builder
     * instance.
     */
    @Test
    public void testSetCardinality_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setCardinality( 0 ) );
    }

    /**
     * Ensures the {@code setCardinality} method sets the cardinality on the
     * resulting stage.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testSetCardinality_SetsCardinality()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.CARDINALITY );
        final int expectedCardinality = 1;

        builder.setCardinality( expectedCardinality );

        final IStage stage = builder.toStage();
        assertEquals( expectedCardinality, stage.getCardinality() );
    }

    /**
     * Ensures the {@code setId} method returns the same builder instance.
     */
    @Test
    public void testSetId_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setId( "id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setId} method sets the identifier on the resulting
     * stage.
     */
    @Test
    public void testSetId_SetsId()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.ID );
        final String expectedId = "id"; //$NON-NLS-1$

        builder.setId( expectedId );

        final IStage stage = builder.toStage();
        assertEquals( expectedId, stage.getId() );
    }

    /**
     * Ensures the {@code setId} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testSetId_Identifier_Null()
    {
        m_builder.setId( null );
    }

    /**
     * Ensures the {@code setStrategy} method returns the same builder instance.
     */
    @Test
    public void testSetStrategy_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setStrategy( createDummy( IStageStrategy.class ) ) );
    }

    /**
     * Ensures the {@code setStrategy} method sets the strategy on the resulting
     * stage.
     */
    @Test
    public void testSetStrategy_SetsStrategy()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.STRATEGY );
        final IStageStrategy expectedStrategy = createDummy( IStageStrategy.class );

        builder.setStrategy( expectedStrategy );

        final IStage stage = builder.toStage();
        assertSame( expectedStrategy, stage.getStrategy() );
    }

    /**
     * Ensures the {@code setStrategy} method throws an exception when passed a
     * {@code null} strategy.
     */
    @Test( expected = NullPointerException.class )
    public void testSetStrategy_Strategy_Null()
    {
        m_builder.setStrategy( null );
    }

    /**
     * Ensures the {@code toStage} method throws an exception when the
     * cardinality is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToStage_Cardinality_NotSet()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.CARDINALITY );

        builder.toStage();
    }

    /**
     * Ensures the {@code toStage} method throws an exception when the stage
     * identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToStage_Identifier_NotSet()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.ID );

        builder.toStage();
    }

    /**
     * Ensures the {@code toStage} method throws an exception when the state of
     * the builder results in an illegal stage.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal
     * attribute will cause an exception to be thrown. The primary collection of
     * tests for all possible permutations of illegal stage attributes is
     * located in the {@code GameSystemUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalStateException.class )
    public void testToStage_Stage_Illegal()
    {
        // TODO: See comment in GameConfigurationBuilderTest.testToGameConfiguration_GameConfig_Illegal().

        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.STAGES );
        builder.addStage( GameSystems.createStage( builder.getId() ) );

        builder.toStage();
    }

    /**
     * Ensures the {@code toStage} method throws an exception when the strategy
     * is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToStage_Strategy_NotSet()
    {
        final StageBuilder builder = GameSystems.createIncompleteStageBuilder( GameSystems.StageAttribute.STRATEGY );

        builder.toStage();
    }
}
