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
 * Created on Jul 22, 2008 at 9:54:39 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.concurrent.Future;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;
import org.gamegineer.game.core.system.MockStageStrategy;
import org.gamegineer.game.core.system.StageBuilder;
import org.gamegineer.game.internal.core.commands.ActivateStageCommand;
import org.gamegineer.game.internal.core.commands.DeactivateStageCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.core.Stage}
 * class.
 */
public final class StageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A mock engine context for use in the test. */
    private MockEngineContext m_engineContext;


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
     * Creates a stage strategy that always indicates the stage is complete.
     * 
     * @return A stage strategy that always indicates the stage is complete;
     *         never {@code null}.
     */
    /* @NonNull */
    private static MockStageStrategy createCompleteStageStrategy()
    {
        return new MockStageStrategy();
    }

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
     * Creates a new stage using the specified stage strategy.
     * 
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    private static Stage createStage(
        /* @NonNull */
        final IStageStrategy strategy )
    {
        assert strategy != null;

        final StageBuilder builder = GameSystems.createUniqueStageBuilder();
        builder.setStrategy( strategy );
        return new Stage( builder.toStage() );
    }

    /**
     * Creates a new stage using the specified stage strategy and child stages
     * using the specified child strategy.
     * 
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * @param childStrategy
     *        The child stage strategy; must not be {@code null}.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    private static Stage createStage(
        /* @NonNull */
        final IStageStrategy strategy,
        /* @NonNull */
        final IStageStrategy childStrategy )
    {
        assert strategy != null;
        assert childStrategy != null;

        final StageBuilder builder = GameSystems.createUniqueStageBuilder();
        builder.setStrategy( strategy );
        final int CHILD_STAGE_COUNT = 3;
        for( int index = 0; index < CHILD_STAGE_COUNT; ++index )
        {
            final StageBuilder childBuilder = GameSystems.createUniqueStageBuilder();
            childBuilder.setStrategy( childStrategy );
            builder.addStage( childBuilder.toStage() );
        }
        return new Stage( builder.toStage() );
    }

    /**
     * Creates a new stage using the specified stage strategy, child stages
     * using the specified child strategy, and grandchild stages using the
     * specified grandchild strategy.
     * 
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * @param childStrategy
     *        The child stage strategy; must not be {@code null}.
     * @param grandchildStrategy
     *        The grandchild stage strategy; must not be {@code null}.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    private static Stage createStage(
        /* @NonNull */
        final IStageStrategy strategy,
        /* @NonNull */
        final IStageStrategy childStrategy,
        /* @NonNull */
        final IStageStrategy grandchildStrategy )
    {
        assert strategy != null;
        assert childStrategy != null;
        assert grandchildStrategy != null;

        final StageBuilder builder = GameSystems.createUniqueStageBuilder();
        builder.setStrategy( strategy );
        final int CHILD_STAGE_COUNT = 3;
        for( int childIndex = 0; childIndex < CHILD_STAGE_COUNT; ++childIndex )
        {
            final StageBuilder childBuilder = GameSystems.createUniqueStageBuilder();
            childBuilder.setStrategy( childStrategy );
            for( int grandchildIndex = 0; grandchildIndex < CHILD_STAGE_COUNT; ++grandchildIndex )
            {
                final StageBuilder grandchildBuilder = GameSystems.createUniqueStageBuilder();
                grandchildBuilder.setStrategy( grandchildStrategy );
                childBuilder.addStage( grandchildBuilder.toStage() );
            }
            builder.addStage( childBuilder.toStage() );
        }
        return new Stage( builder.toStage() );
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
        m_engineContext = new MockEngineContext();
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
        m_engineContext = null;
    }

    /**
     * Ensures the {@code activate} method of an active executing stage
     * activates the next appropriate child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_Active_Executing_ChildActivated()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy(), createCompleteStageStrategy() );
        stage.activate( m_engineContext ); // activate stage

        stage.activate( m_engineContext ); // activate stage.childStages[0]

        assertTrue( GameAttributes.stageState( stage.getStages().get( 0 ).getId() ).isPresent( m_engineContext.getState() ) );
        assertEquals( 0, GameAttributes.stageState( stage.getId() ).getValue( m_engineContext.getState() ).getActiveChildStageIndex() );
    }

    /**
     * Ensures the {@code activate} method of an active executing stage throws
     * an exception if it has no child stages.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testActivate_Active_Executing_NoChildren()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        stage.activate( m_engineContext );
    }

    /**
     * Ensures the {@code activate} method of an active non-executing stage
     * whose active child stage is the executing stage activates the next
     * appropriate grandchild stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_Active_NotExecuting_ActiveChildIsExecuting_GrandchildActivated()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy(), createCompleteStageStrategy(), createCompleteStageStrategy() );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        stage.activate( m_engineContext ); // activate stage.childStages[0].childStages[0]

        assertTrue( GameAttributes.stageState( stage.getStages().get( 0 ).getStages().get( 0 ).getId() ).isPresent( m_engineContext.getState() ) );
        assertEquals( 0, GameAttributes.stageState( stage.getId() ).getValue( m_engineContext.getState() ).getActiveChildStageIndex() );
    }

    /**
     * Ensures the {@code activate} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testActivate_Context_Null()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( null );
    }

    /**
     * Ensures the {@code activate} method of an inactive stage adds the stage
     * state to the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_Inactive_StageStateAdded()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.activate( m_engineContext );

        assertTrue( GameAttributes.stageState( stage.getId() ).isPresent( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code activate} method of an inactive stage invokes the
     * {@code IStageStrategy.activate} method.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testActivate_Inactive_StrategyInvoked()
        throws Exception
    {
        final MockStageStrategy strategy = createCompleteStageStrategy();
        final Stage stage = createStage( strategy );

        stage.activate( m_engineContext );

        assertEquals( 1, strategy.getActivateCallCount() );
    }

    /**
     * Ensures the {@code commandExecuted} method of a complete active executing
     * stage submits a {@code DeactivateStageCommand} command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuted_Active_Executing_Complete_DeactivateStageCommandSubmitted()
        throws Exception
    {
        final String expectedSubmittedCommandType = new DeactivateStageCommand( "dummyId", new StageVersion() ).getType(); //$NON-NLS-1$
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        stage.commandExecuted( new MockCommandExecutedEvent( m_engineContext ) );

        assertNotNull( m_engineContext.getSubmittedCommand() );
        assertEquals( expectedSubmittedCommandType, m_engineContext.getSubmittedCommand().getType() );
    }

    /**
     * Ensures the {@code commandExecuted} method of an incomplete active
     * executing stage submits a {@code ActivateStageCommand} command if it has
     * child stages.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuted_Active_Executing_Incomplete_HasChildren_ActivateStageCommandSubmitted()
        throws Exception
    {
        final String expectedSubmittedCommandType = new ActivateStageCommand( "dummyId", new StageVersion() ).getType(); //$NON-NLS-1$
        final Stage stage = createStage( createIncompleteStageStrategy(), createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        stage.commandExecuted( new MockCommandExecutedEvent( m_engineContext ) );

        assertNotNull( m_engineContext.getSubmittedCommand() );
        assertEquals( expectedSubmittedCommandType, m_engineContext.getSubmittedCommand().getType() );
    }

    /**
     * Ensures the {@code commandExecuted} method of an incomplete active
     * executing stage does not submit any commands if it has no child stages.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuted_Active_Executing_Incomplete_NoChildren_NoCommandSubmitted()
        throws Exception
    {
        final Stage stage = createStage( createIncompleteStageStrategy() );
        stage.activate( m_engineContext );

        stage.commandExecuted( new MockCommandExecutedEvent( m_engineContext ) );

        assertNull( m_engineContext.getSubmittedCommand() );
    }

    /**
     * Ensures the {@code commandExecuted} method of an active executing stage
     * invokes the {@code IStageStrategy.commandExecuted} method.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuted_Active_Executing_StrategyInvoked()
        throws Exception
    {
        final MockStageStrategy strategy = createCompleteStageStrategy();
        final Stage stage = createStage( strategy );
        stage.activate( m_engineContext );

        stage.commandExecuted( new MockCommandExecutedEvent( m_engineContext ) );

        assertEquals( 1, strategy.getCommandExecutedCallCount() );
    }

    /**
     * Ensures the {@code commandExecuted} method of an active non-executing
     * stage passes control to the active child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuted_Active_NotExecuting_DelegateToActiveChildStage()
        throws Exception
    {
        final MockStageStrategy childStrategy = createCompleteStageStrategy();
        final Stage stage = createStage( createCompleteStageStrategy(), childStrategy );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        stage.commandExecuted( new MockCommandExecutedEvent( m_engineContext ) );

        assertEquals( 1, childStrategy.getCommandExecutedCallCount() );
    }

    /**
     * Ensures the {@code commandExecuted} method does not throw an exception
     * when it submits a command to an engine that has been shut down.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuted_CommandSubmittedWithEngineShutdown()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        m_engineContext.shutdown();
        stage.commandExecuted( new MockCommandExecutedEvent( m_engineContext ) );

        assertNotNull( m_engineContext.getSubmittedCommand() );
    }

    /**
     * Ensures the {@code commandExecuting} method of an executing stage invokes
     * the {@code IStageStrategy.commandExecuting} method.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuting_Active_Executing_StrategyInvoked()
        throws Exception
    {
        final MockStageStrategy strategy = createCompleteStageStrategy();
        final Stage stage = createStage( strategy );
        stage.activate( m_engineContext );

        stage.commandExecuting( new MockCommandExecutingEvent( m_engineContext ) );

        assertEquals( 1, strategy.getCommandExecutingCallCount() );
    }

    /**
     * Ensures the {@code commandExecuting} method of an active non-executing
     * stage passes control to the active child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCommandExecuting_Active_NotExecuting_DelegateToActiveChildStage()
        throws Exception
    {
        final MockStageStrategy childStrategy = createCompleteStageStrategy();
        final Stage stage = createStage( createCompleteStageStrategy(), childStrategy );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        stage.commandExecuting( new MockCommandExecutingEvent( m_engineContext ) );

        assertEquals( 1, childStrategy.getCommandExecutingCallCount() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * stage.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Stage_Null()
    {
        new Stage( null );
    }

    /**
     * Ensures the {@code deactivate} method of an active executing stage
     * removes the stage state from the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_Active_Executing_StageStateRemoved()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        final boolean deactivated = stage.deactivate( m_engineContext );

        assertTrue( deactivated );
        assertFalse( GameAttributes.stageState( stage.getId() ).isPresent( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code deactivate} method of an active executing stage
     * invokes the {@code IStageStrategy.deactivate} method.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_Active_Executing_StrategyInvoked()
        throws Exception
    {
        final MockStageStrategy strategy = createCompleteStageStrategy();
        final Stage stage = createStage( strategy );
        stage.activate( m_engineContext );

        final boolean deactivated = stage.deactivate( m_engineContext );

        assertTrue( deactivated );
        assertEquals( 1, strategy.getDeactivateCallCount() );
    }

    /**
     * Ensures the {@code deactivate} method of an active non-executing stage
     * whose active child stage is the executing stage deactivates the active
     * child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_Active_NotExecuting_ActiveChildIsExecuting_ActiveChildDeactivated()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy(), createCompleteStageStrategy() );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        final boolean deactivated = stage.deactivate( m_engineContext );

        assertFalse( deactivated );
        assertFalse( GameAttributes.stageState( stage.getStages().get( 0 ).getId() ).isPresent( m_engineContext.getState() ) );
        assertEquals( -1, GameAttributes.stageState( stage.getId() ).getValue( m_engineContext.getState() ).getActiveChildStageIndex() );
    }

    /**
     * Ensures the {@code deactivate} method of an active non-executing stage
     * whose active child stage is not the executing stage does not deactivate
     * the active child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDeactivate_Active_NotExecuting_ActiveChildIsNotExecuting_ActiveChildNotDeactivated()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy(), createCompleteStageStrategy(), createCompleteStageStrategy() );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]
        stage.activate( m_engineContext ); // activate stage.childStages[0].childStages[0]

        final boolean deactivated = stage.deactivate( m_engineContext );

        assertFalse( deactivated );
        assertTrue( GameAttributes.stageState( stage.getStages().get( 0 ).getId() ).isPresent( m_engineContext.getState() ) );
        assertTrue( -1 != GameAttributes.stageState( stage.getId() ).getValue( m_engineContext.getState() ).getActiveChildStageIndex() );
    }

    /**
     * Ensures the {@code deactivate} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testDeactivate_Context_Null()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.deactivate( null );
    }

    /**
     * Ensures the {@code deactivate} method of an inactive stage throws an
     * exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testDeactivate_Inactive()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.deactivate( m_engineContext );
    }

    /**
     * Ensures the {@code getActiveStage} method returns the correct value when
     * the specified stage is active.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetActiveStage_Active()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        assertSame( stage, Stage.getActiveStage( m_engineContext.getState(), stage.getId() ) );
    }

    /**
     * Ensures the {@code getActiveStage} method returns {@code null} when the
     * specified stage is inactive.
     */
    @Test
    public void testGetActiveStage_Inactive()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        assertNull( Stage.getActiveStage( m_engineContext.getState(), stage.getId() ) );
    }

    /**
     * Ensures the {@code getActiveStage} method throws an exception when passed
     * a {@code null} stage identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetActiveState_StageId_Null()
    {
        Stage.getActiveStage( createDummy( IState.class ), null );
    }

    /**
     * Ensures the {@code getActiveStage} method throws an exception when passed
     * a {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testGetActiveState_State_Null()
    {
        Stage.getActiveStage( null, "id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getVersion} method throws an exception when the stage
     * is inactive.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetVersion_Inactive()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.getVersion( m_engineContext.getState() );
    }

    /**
     * Ensures the {@code getVersion} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testGetVersion_State_Null()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.getVersion( null );
    }

    /**
     * Ensures the {@code isActive} method returns {@code true} for an active
     * stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsActive_Active()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        assertTrue( stage.isActive( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code isActive} method returns {@code false} for an inactive
     * stage.
     */
    @Test
    public void testIsActive_Inactive()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        assertFalse( stage.isActive( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code isActive} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testIsActive_State_Null()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.isActive( null );
    }

    /**
     * Ensures the {@code isExecuting} method returns {@code true} for an active
     * stage that is currently executing.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsExecuting_Active_Executing()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy() );
        stage.activate( m_engineContext );

        assertTrue( stage.isExecuting( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code isExecuting} method returns {@code false} for an
     * active stage that is not currently executing.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsExecuting_Active_NotExecuting()
        throws Exception
    {
        final Stage stage = createStage( createCompleteStageStrategy(), createCompleteStageStrategy() );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        assertFalse( stage.isExecuting( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code isExecuting} method returns {@code false} for an
     * inactive stage.
     */
    @Test
    public void testIsExecuting_Inactive()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        assertFalse( stage.isExecuting( m_engineContext.getState() ) );
    }

    /**
     * Ensures the {@code isExecuting} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testIsExecuting_State_Null()
    {
        final Stage stage = createStage( createCompleteStageStrategy() );

        stage.isExecuting( null );
    }

    /**
     * Ensures the {@code stateChanged} method of an active executing stage
     * invokes the {@code IStageStrategy.stateChanged} method.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testStateChanged_Active_Executing_StrategyInvoked()
        throws Exception
    {
        final MockStageStrategy strategy = createCompleteStageStrategy();
        final Stage stage = createStage( strategy );
        stage.activate( m_engineContext );

        stage.stateChanged( new MockStateChangeEvent( m_engineContext ) );

        assertEquals( 1, strategy.getStateChangedCallCount() );
    }

    /**
     * Ensures the {@code stateChanged} method of an active non-executing stage
     * passes control to the active child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testStateChanged_Active_NotExecuting_DelegateToActiveChildStage()
        throws Exception
    {
        final MockStageStrategy childStrategy = createCompleteStageStrategy();
        final Stage stage = createStage( createCompleteStageStrategy(), childStrategy );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        stage.stateChanged( new MockStateChangeEvent( m_engineContext ) );

        assertEquals( 1, childStrategy.getStateChangedCallCount() );
    }

    /**
     * Ensures the {@code stateChanging} method of an active executing stage
     * invokes the {@code IStageStrategy.stateChanging} method.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testStateChanging_Active_Executing_StrategyInvoked()
        throws Exception
    {
        final MockStageStrategy strategy = createCompleteStageStrategy();
        final Stage stage = createStage( strategy );
        stage.activate( m_engineContext );

        stage.stateChanging( new MockStateChangeEvent( m_engineContext ) );

        assertEquals( 1, strategy.getStateChangingCallCount() );
    }

    /**
     * Ensures the {@code stateChanging} method of an active non-executing stage
     * passes control to the active child stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testStateChanging_Active_NotExecuting_DelegateToActiveChildStage()
        throws Exception
    {
        final MockStageStrategy childStrategy = createCompleteStageStrategy();
        final Stage stage = createStage( createCompleteStageStrategy(), childStrategy );
        stage.activate( m_engineContext ); // activate stage
        stage.activate( m_engineContext ); // activate stage.childStages[0]

        stage.stateChanging( new MockStateChangeEvent( m_engineContext ) );

        assertEquals( 1, childStrategy.getStateChangingCallCount() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A mock command executed event for testing game stages.
     */
    private static final class MockCommandExecutedEvent
        extends CommandExecutedEvent
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 1L;

        /** The engine context. */
        private final IEngineContext m_context;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockCommandExecutedEvent}
         * class.
         * 
         * @param source
         *        The context representing the engine that fired the event; must
         *        not be {@code null}.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code source} is {@code null}.
         */
        MockCommandExecutedEvent(
            /* @NonNull */
            @SuppressWarnings( "hiding" )
            final IEngineContext source )
        {
            super( source );

            m_context = source;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getCommand()
         */
        public ICommand<?> getCommand()
        {
            return createDummy( ICommand.class );
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getEngineContext()
         */
        public IEngineContext getEngineContext()
        {
            return m_context;
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#getException()
         */
        public Exception getException()
        {
            throw new AssertionError();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#getResult()
         */
        public Object getResult()
        {
            throw new AssertionError();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent#hasException()
         */
        public boolean hasException()
        {
            throw new AssertionError();
        }
    }

    /**
     * A mock command executing event for testing game stages.
     */
    private static final class MockCommandExecutingEvent
        extends CommandExecutingEvent
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 1L;

        /** The engine context. */
        private final IEngineContext m_context;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockCommandExecutingEvent}
         * class.
         * 
         * @param source
         *        The context representing the engine that fired the event; must
         *        not be {@code null}.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code source} is {@code null}.
         */
        MockCommandExecutingEvent(
            /* @NonNull */
            @SuppressWarnings( "hiding" )
            final IEngineContext source )
        {
            super( source );

            m_context = source;
        }

        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getCommand()
         */
        @Override
        public ICommand<?> getCommand()
        {
            return createDummy( ICommand.class );
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent#getEngineContext()
         */
        @Override
        public IEngineContext getEngineContext()
        {
            return m_context;
        }
    }

    /**
     * A mock engine context used for testing game stages.
     */
    private static final class MockEngineContext
        extends FakeEngineContext
        implements ICommandQueue
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Indicates the engine has been shut down. */
        private boolean m_isShutdown;

        /** The most recent command submitted to the command queue extension. */
        private ICommand<?> m_submittedCommand;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockEngineContext} class.
         */
        MockEngineContext()
        {
            m_isShutdown = false;
            m_submittedCommand = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the most recent command submitted to the command queue
         * extension.
         * 
         * @return The most recent command submitted to the command queue
         *         extension; {@code null} if no command was ever submitted.
         */
        /* @Nullable */
        ICommand<?> getSubmittedCommand()
        {
            return m_submittedCommand;
        }

        /*
         * @see org.gamegineer.engine.core.IEngineContext#getExtension(java.lang.Class)
         */
        @Override
        public <T> T getExtension(
            final Class<T> type )
        {
            assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

            if( type == ICommandQueue.class )
            {
                return type.cast( this );
            }

            return null;
        }

        /**
         * Simulates an engine shutdown.
         */
        void shutdown()
        {
            m_isShutdown = true;
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue#submitCommand(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.ICommand)
         */
        @SuppressWarnings( "unchecked" )
        public <T> Future<T> submitCommand(
            final IEngineContext context,
            final ICommand<T> command )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
            assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

            m_submittedCommand = command;

            if( m_isShutdown )
            {
                throw new IllegalStateException();
            }

            return createDummy( Future.class );
        }
    }

    /**
     * A mock state change event for testing game stages.
     */
    private static final class MockStateChangeEvent
        extends StateChangeEvent
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 1L;

        /** The engine context. */
        private final IEngineContext m_context;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockStateChangeEvent} class.
         * 
         * @param source
         *        The context representing the engine that fired the event; must
         *        not be {@code null}.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code source} is {@code null}.
         */
        MockStateChangeEvent(
            /* @NonNull */
            @SuppressWarnings( "hiding" )
            final IEngineContext source )
        {
            super( source );

            m_context = source;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#containsAttributeChange(org.gamegineer.engine.core.AttributeName)
         */
        public boolean containsAttributeChange(
            @SuppressWarnings( "unused" )
            final AttributeName name )
        {
            throw new AssertionError();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#getAttributeChange(org.gamegineer.engine.core.AttributeName)
         */
        public IAttributeChange getAttributeChange(
            @SuppressWarnings( "unused" )
            final AttributeName name )
        {
            throw new AssertionError();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#getAttributeChanges()
         */
        public Collection<IAttributeChange> getAttributeChanges()
        {
            throw new AssertionError();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateEvent#getEngineContext()
         */
        public IEngineContext getEngineContext()
        {
            return m_context;
        }
    }
}
