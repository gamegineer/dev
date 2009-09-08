/*
 * AbstractEngineTestCase.java
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
 * Created on Apr 6, 2008 at 9:52:47 PM.
 */

package org.gamegineer.engine.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.IEngine} interface.
 * 
 * @param <T>
 *        The type of the engine.
 */
public abstract class AbstractEngineTestCase<T extends IEngine>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine under test in the fixture. */
    private T engine_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractEngineTestCase} class.
     */
    protected AbstractEngineTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the engine to be tested with a clean state.
     * 
     * @return The engine to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createEngine()
        throws Exception;

    /**
     * Creates the engine to be tested whose state is initialized using the
     * specified command.
     * 
     * @param command
     *        The command used to build the initial engine state; must not be
     *        {@code null}.
     * 
     * @return The engine to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code command} is {@code null}.
     */
    /* @NonNull */
    protected abstract T createEngine(
        /* @NonNull */
        ICommand<?> command )
        throws Exception;

    /**
     * Gets the command history for the specified engine.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * 
     * @return The command history for the specified engine; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code engine} is {@code null}.
     */
    /* @NonNull */
    protected abstract List<IInvertibleCommand<?>> getCommandHistory(
        /* @NonNull */
        T engine );

    /**
     * Gets the engine under test in the fixture.
     * 
     * @return The engine under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getEngine()
    {
        assertNotNull( engine_ );
        return engine_;
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
        engine_ = createEngine();
        assertNotNull( engine_ );
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
        engine_.shutdown();
        engine_ = null;
    }

    /**
     * Ensures the the engine does not add any initialization command specified
     * during creation to its command history (and thus it can't be undone) even
     * if that command modifies the application state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateEngineFromCommand_CommandNotAddedToHistory()
        throws Exception
    {
        final ICommand<Void> command = new MockWriteCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
            {
                final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
                context.getState().addAttribute( name, new Object() );
                return null;
            }
        };
        final T engine = createEngine( command );

        assertTrue( getCommandHistory( engine ).isEmpty() );
    }

    /**
     * Ensures the engine adds commands which change the application state to
     * the command history.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEngine_ApplicationStateChange_CommandAddedToHistory()
        throws Exception
    {
        final IInvertibleCommand<Void> command = new MockInvertibleCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
            {
                final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
                context.getState().addAttribute( name, new Object() );
                return null;
            }
        };
        engine_.executeCommand( command );

        final List<IInvertibleCommand<?>> commands = getCommandHistory( engine_ );
        assertTrue( commands.contains( command ) );
    }

    /**
     * Ensures the engine allows commands to interact with its state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEngine_CommandInteractionWithState()
        throws Exception
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        final Object value = "value"; //$NON-NLS-1$
        final ICommand<Void> writeCommand = new MockWriteCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
            {
                context.getState().addAttribute( name, value );
                return null;
            }
        };
        engine_.executeCommand( writeCommand );

        final ICommand<Object> readCommand = new MockCommand<Object>()
        {
            @Override
            public Object execute(
                final IEngineContext context )
            {
                return context.getState().getAttribute( name );
            }
        };
        assertEquals( value, engine_.executeCommand( readCommand ) );
    }

    /**
     * Ensures the engine does not add commands which change the engine control
     * state to the command history.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEngine_EngineControlStateChange_CommandNotAddedToHistory()
        throws Exception
    {
        final ICommand<Void> command = new MockWriteCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
            {
                final AttributeName name = new AttributeName( Scope.ENGINE_CONTROL, "name" ); //$NON-NLS-1$
                context.getState().addAttribute( name, new Object() );
                return null;
            }
        };
        engine_.executeCommand( command );

        final List<IInvertibleCommand<?>> commands = getCommandHistory( engine_ );
        assertFalse( commands.contains( command ) );
    }

    /**
     * Ensures the engine does not add commands which do not change the engine
     * state to the command history even if they are invertible.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEngine_EngineStateUnchanged_CommandNotAddedToHistory()
        throws Exception
    {
        final ICommand<Void> command = new MockInvertibleCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                return null;
            }
        };
        engine_.executeCommand( command );

        final List<IInvertibleCommand<?>> commands = getCommandHistory( engine_ );
        assertFalse( commands.contains( command ) );
    }

    /**
     * Ensures the {@code executeCommand} method propagates a checked exception
     * thrown by the command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecuteCommand_Command_CheckedException()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
                throws EngineException
            {
                throw new EngineException();
            }
        };
        engine_.executeCommand( command );
    }

    /**
     * Ensures the {@code executeCommand} method throws an exception when passed
     * a {@code null} command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testExecuteCommand_Command_Null()
        throws Exception
    {
        engine_.executeCommand( null );
    }

    /**
     * Ensures the {@code executeCommand} method propagates an unchecked
     * exception thrown by the command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnsupportedOperationException.class )
    public void testExecuteCommand_Command_UncheckedException()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                throw new UnsupportedOperationException();
            }
        };
        engine_.executeCommand( command );
    }

    /**
     * Ensures the {@code executeCommand} returns the expected command result.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_ReturnValue_CommandResult()
        throws Exception
    {
        final String result = "this is the result"; //$NON-NLS-1$
        final ICommand<String> command = new MockCommand<String>()
        {
            @Override
            public String execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                return result;
            }
        };
        assertEquals( result, engine_.executeCommand( command ) );
    }

    /**
     * Ensures the {@code executeCommand} method throws an exception when the
     * engine has been shut down.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testExecuteCommand_Shutdown()
        throws Exception
    {
        engine_.shutdown();

        engine_.executeCommand( new MockCommand<Void>() );
    }

    /**
     * Ensures the {@code isShutdown} method indicates a running engine is not
     * shut down.
     */
    @Test
    public void testIsShutdown_Running()
    {
        assertFalse( engine_.isShutdown() );
    }

    /**
     * Ensures the {@code isShutdown} method indicates a shut down engine is
     * shut down.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsShutdown_Shutdown()
        throws Exception
    {
        engine_.shutdown();

        assertTrue( engine_.isShutdown() );
    }

    /**
     * Ensures the {@code shutdown} method does not throw an exception if
     * invoked multiple times.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testShutdown_MultipleCalls()
        throws Exception
    {
        engine_.shutdown();

        engine_.shutdown();

        assertTrue( engine_.isShutdown() );
    }

    /**
     * Ensures the {@code submitCommand} method propagates a checked exception
     * thrown by the command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testSubmitCommand_Command_CheckedException()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
                throws EngineException
            {
                throw new EngineException();
            }
        };
        try
        {
            engine_.submitCommand( command ).get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof EngineException )
            {
                throw (EngineException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /**
     * Ensures the {@code submitCommand} method throws an exception when passed
     * a {@code null} command.
     */
    @Test( expected = NullPointerException.class )
    public void testSubmitCommand_Command_Null()
    {
        engine_.submitCommand( null );
    }

    /**
     * Ensures the {@code submitCommand} method propagates an unchecked
     * exception thrown by the command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnsupportedOperationException.class )
    public void testSubmitCommand_Command_UncheckedException()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                throw new UnsupportedOperationException();
            }
        };
        try
        {
            engine_.submitCommand( command ).get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof EngineException )
            {
                throw (EngineException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /**
     * Ensures the {@code submitCommand} returns the expected command result.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSubmitCommand_ReturnValue_CommandResult()
        throws Exception
    {
        final String result = "this is the result"; //$NON-NLS-1$
        final ICommand<String> command = new MockCommand<String>()
        {
            @Override
            public String execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                return result;
            }
        };
        assertEquals( result, engine_.submitCommand( command ).get() );
    }

    /**
     * Ensures the {@code submitCommand} method throws an exception when the
     * engine has been shut down.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testSubmitCommand_Shutdown()
        throws Exception
    {
        engine_.shutdown();

        engine_.submitCommand( new MockCommand<Void>() );
    }
}
