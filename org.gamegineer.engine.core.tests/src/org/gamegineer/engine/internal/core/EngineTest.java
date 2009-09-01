/*
 * EngineTest.java
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
 * Created on Apr 26, 2008 at 10:37:59 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.security.Principal;
import org.gamegineer.common.core.util.ref.Reference;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.MockCommands;
import org.gamegineer.engine.core.MockInvertibleCommand;
import org.gamegineer.engine.core.MockWriteCommand;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandEventMediatorFacade;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;
import org.gamegineer.engine.core.extensions.commandeventmediator.MockCommandListener;
import org.gamegineer.engine.core.extensions.commandhistory.CommandHistoryFacade;
import org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager;
import org.gamegineer.engine.core.extensions.securitymanager.ThreadPrincipals;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.MockStateListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateEventMediatorFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.engine.internal.core.Engine}
 * class.
 */
public final class EngineTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine under test in the fixture. */
    private Engine m_engine;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineTest} class.
     */
    public EngineTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified command listener to the engine under test.
     * 
     * @param listener
     *        The command listener; must not be {@code null}.
     */
    private void addCommandListener(
        /* @NonNull */
        final ICommandListener listener )
    {
        CommandEventMediatorFacade.addCommandListener( m_engine, listener );
    }

    /**
     * Adds the specified state listener to the engine under test.
     * 
     * @param listener
     *        The state listener; must not be {@code null}.
     */
    private void addStateListener(
        /* @NonNull */
        final IStateListener listener )
    {
        StateEventMediatorFacade.addStateListener( m_engine, listener );
    }

    /**
     * Removes the specified command listener from the engine under test.
     * 
     * @param listener
     *        The command listener; must not be {@code null}.
     */
    private void removeCommandListener(
        /* @NonNull */
        final ICommandListener listener )
    {
        CommandEventMediatorFacade.removeCommandListener( m_engine, listener );
    }

    /**
     * Removes the specified state listener from the engine under test.
     * 
     * @param listener
     *        The state listener; must not be {@code null}.
     */
    private void removeStateListener(
        /* @NonNull */
        final IStateListener listener )
    {
        StateEventMediatorFacade.removeStateListener( m_engine, listener );
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
        m_engine = Engine.createEngine();
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
        m_engine = null;
    }

    /**
     * Ensures the {@code createEngine(ICommand)} method throws an exception if
     * passed a {@code null} command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testCreateEngineFromCommand_Command_Null()
        throws Exception
    {
        Engine.createEngine( null );
    }

    /**
     * Ensures the {@code executeCommand} method throws an exception if it's
     * invoked on an engine worker thread.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testExecuteCommand_Caller_InvokedOnEngineWorkerThread()
        throws Exception
    {
        final MockCommandListener listener = new MockCommandListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void commandExecuting(
                final CommandExecutingEvent event )
                throws EngineException
            {
                super.commandExecuting( event );

                m_engine.executeCommand( new MockCommand<Void>() );
            }
        };
        addCommandListener( listener );

        m_engine.executeCommand( new MockCommand<Void>() );
    }

    /**
     * Ensures the {@code executeCommand} method throws the exact exception
     * thrown from a failed command without wrapping it in another exception.
     */
    @Test
    public void testExecuteCommand_Command_Failed_ThrowsExpectedException()
    {
        final String EXPECTED_EXCEPTION_MESSAGE = "the message"; //$NON-NLS-1$
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
                throws EngineException
            {
                assert context != null;

                throw new EngineException( EXPECTED_EXCEPTION_MESSAGE );
            }
        };
        try
        {
            m_engine.executeCommand( command );
            fail( "Expected an exception to be thrown." ); //$NON-NLS-1$
        }
        catch( final EngineException e )
        {
            assertEquals( EXPECTED_EXCEPTION_MESSAGE, e.getMessage() );
        }
    }

    /**
     * Ensures the {@code executeCommand} method does not add a phantom command
     * to the command history and does not fire any engine events associated
     * with the phantom command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Command_PhantomCommand()
        throws Exception
    {
        @PhantomCommand
        class TestCommand
            extends MockInvertibleCommand<Void>
        {
            @Override
            public Void execute(
                final IEngineContext context )
            {
                // NB: Phantom commands do not automatically start a transaction, so we have
                // to take care of this engine implementation detail in order to successfully
                // execute a phantom command which modifies the engine state.
                final State state = ((EngineContext)context).getEngine().getState();
                state.beginTransaction();
                state.addAttribute( new AttributeName( Scope.APPLICATION, "name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
                state.commitTransaction();
                return null;
            }
        }

        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        final int originalCommandHistorySize = CommandHistoryFacade.getCommandHistory( m_engine ).size();
        commandListener.clearEvents();
        stateListener.clearEvents();
        m_engine.executeCommand( new TestCommand() );

        assertEquals( 0, commandListener.getCommandExecutingEventCount() );
        assertEquals( 0, commandListener.getCommandExecutedEventCount() );
        assertEquals( 0, stateListener.getStateChangingEventCount() );
        assertEquals( 0, stateListener.getStateChangedEventCount() );
        assertEquals( originalCommandHistorySize, CommandHistoryFacade.getCommandHistory( m_engine ).size() ); // executes a command behind the scenes
    }

    /**
     * Ensures the {@code executeCommand} method stores the user principal
     * correctly in the command context so that it is accessible to the security
     * manager extension.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_CommandContext_SetsUserPrincipal()
        throws Exception
    {
        // XXX: This test is temporary until the engine SPI model is more
        // mature, at which time the engine will simply visit each registered
        // extension allowing them to modify the command context as needed.
        // At that time, an equivalent test will have to be added to the
        // SecurityManagerExtension class.

        final Principal expectedPrincipal = ThreadPrincipals.getUserPrincipal();
        final ICommand<Principal> command = new MockCommand<Principal>()
        {
            @Override
            public Principal execute(
                final IEngineContext context )
            {
                return context.getExtension( ISecurityManager.class ).getUserPrincipal( context );
            }
        };

        final Principal actualPrincipal = m_engine.executeCommand( command );

        assertEquals( expectedPrincipal, actualPrincipal );
    }

    /**
     * Ensures the {@code executeCommand} method does not allow a listener to
     * indirectly modify the engine state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Listeners_CannotChangeState()
        throws Exception
    {
        final AttributeName ATTR_NAME_1 = new AttributeName( Scope.APPLICATION, "name1" ); //$NON-NLS-1$
        final String ATTR_VALUE_1 = "value1"; //$NON-NLS-1$
        final AttributeName ATTR_NAME_2 = new AttributeName( Scope.APPLICATION, "name2" ); //$NON-NLS-1$
        final String ATTR_VALUE_2 = "value2"; //$NON-NLS-1$
        final Engine engine = m_engine;
        final Reference<Exception> commandExecutedExceptionRef = new Reference<Exception>();
        final Reference<Exception> commandExecutingExceptionRef = new Reference<Exception>();
        final Reference<Exception> stateChangedExceptionRef = new Reference<Exception>();
        final Reference<Exception> stateChangingExceptionRef = new Reference<Exception>();
        final MockCommandListener commandListener = new MockCommandListener()
        {
            @Override
            public void commandExecuted(
                final CommandExecutedEvent event )
            {
                super.commandExecuted( event );

                try
                {
                    engine.getState().addAttribute( ATTR_NAME_2, ATTR_VALUE_2 );
                }
                catch( final IllegalStateException exception )
                {
                    commandExecutedExceptionRef.set( exception );
                }
            }

            @Override
            public void commandExecuting(
                final CommandExecutingEvent event )
                throws EngineException
            {
                super.commandExecuting( event );

                try
                {
                    engine.getState().addAttribute( ATTR_NAME_2, ATTR_VALUE_2 );
                }
                catch( final IllegalStateException exception )
                {
                    commandExecutingExceptionRef.set( exception );
                }
            }
        };
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener()
        {
            @Override
            public void stateChanged(
                final StateChangeEvent event )
            {
                super.stateChanged( event );

                try
                {
                    engine.getState().addAttribute( ATTR_NAME_2, ATTR_VALUE_2 );
                }
                catch( final IllegalStateException exception )
                {
                    stateChangedExceptionRef.set( exception );
                }
            }

            @Override
            public void stateChanging(
                final StateChangeEvent event )
                throws EngineException
            {
                super.stateChanging( event );

                try
                {
                    engine.getState().addAttribute( ATTR_NAME_2, ATTR_VALUE_2 );
                }
                catch( final IllegalStateException exception )
                {
                    stateChangingExceptionRef.set( exception );
                }
            }
        };
        addStateListener( stateListener );

        commandExecutingExceptionRef.set( null );
        commandExecutedExceptionRef.set( null );
        stateChangingExceptionRef.set( null );
        stateChangedExceptionRef.set( null );
        m_engine.executeCommand( MockCommands.createAddAttributeCommand( ATTR_NAME_1, ATTR_VALUE_1 ) );

        assertNotNull( commandExecutingExceptionRef.get() );
        assertNotNull( commandExecutedExceptionRef.get() );
        assertNotNull( stateChangingExceptionRef.get() );
        assertNotNull( stateChangedExceptionRef.get() );
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values during a failed command execution.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecuteCommand_Listeners_Command_Failed()
        throws Exception
    {
        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
                throws EngineException
            {
                assert context != null;

                throw new EngineException();
            }
        };
        try
        {
            m_engine.executeCommand( command );
            fail( "Expected an exception to be thrown." ); //$NON-NLS-1$
        }
        catch( final EngineException e )
        {
            assertNotNull( commandListener.getCommandExecutingEvent() );
            assertNotNull( commandListener.getCommandExecutedEvent() );
            assertTrue( commandListener.getCommandExecutedEvent().hasException() );

            assertNull( stateListener.getStateChangingEvent() );
            assertNull( stateListener.getStateChangedEvent() );

            throw e;
        }
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values during a successful command execution.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Listeners_Command_Successful()
        throws Exception
    {
        final AttributeName ATTR_NAME_1 = new AttributeName( Scope.APPLICATION, "name1" ); //$NON-NLS-1$
        final AttributeName ATTR_NAME_2 = new AttributeName( Scope.APPLICATION, "name2" ); //$NON-NLS-1$
        final String ATTR_VALUE_OLD_2 = "value2"; //$NON-NLS-1$
        final String ATTR_VALUE_NEW_2 = "newValue2"; //$NON-NLS-1$

        final ICommand<Void> initializeCommand = new MockWriteCommand<Void>()
        {
            @Override
            public Void execute(
                final IEngineContext context )
            {
                assert context != null;

                context.getState().addAttribute( ATTR_NAME_1, "value1" ); //$NON-NLS-1$
                context.getState().addAttribute( ATTR_NAME_2, ATTR_VALUE_OLD_2 );
                return null;
            }
        };
        m_engine.executeCommand( initializeCommand );

        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        final String EXPECTED_VALUE = "returnValue"; //$NON-NLS-1$
        final ICommand<String> modifyCommand = new MockWriteCommand<String>()
        {
            @Override
            public String execute(
                final IEngineContext context )
            {
                assert context != null;

                context.getState().setAttribute( ATTR_NAME_2, ATTR_VALUE_NEW_2 );
                return EXPECTED_VALUE;
            }
        };
        m_engine.executeCommand( modifyCommand );

        assertNotNull( commandListener.getCommandExecutingEvent() );
        assertNotNull( commandListener.getCommandExecutedEvent() );
        assertFalse( commandListener.getCommandExecutedEvent().hasException() );
        assertEquals( EXPECTED_VALUE, commandListener.getCommandExecutedEvent().getResult() );

        assertNotNull( stateListener.getStateChangingEvent() );
        assertEquals( 1, stateListener.getStateChangingEvent().getAttributeChanges().size() );
        assertEquals( ATTR_VALUE_OLD_2, stateListener.getStateChangingEvent().getAttributeChange( ATTR_NAME_2 ).getOldValue() );
        assertEquals( ATTR_VALUE_NEW_2, stateListener.getStateChangingEvent().getAttributeChange( ATTR_NAME_2 ).getNewValue() );
        assertNotNull( stateListener.getStateChangedEvent() );
        assertEquals( 1, stateListener.getStateChangedEvent().getAttributeChanges().size() );
        assertEquals( ATTR_VALUE_OLD_2, stateListener.getStateChangedEvent().getAttributeChange( ATTR_NAME_2 ).getOldValue() );
        assertEquals( ATTR_VALUE_NEW_2, stateListener.getStateChangedEvent().getAttributeChange( ATTR_NAME_2 ).getNewValue() );
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values when the {@code commandExecuted} method of a
     * command listener throws an exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Listeners_CommandListener_CommandExecuted_ThrowsException()
        throws Exception
    {
        final MockCommandListener commandListener1 = new MockCommandListener();
        addCommandListener( commandListener1 );
        final MockCommandListener commandListener2 = new MockCommandListener()
        {
            @Override
            public void commandExecuted(
                final CommandExecutedEvent event )
            {
                super.commandExecuted( event );

                throw new RuntimeException();
            }
        };
        addCommandListener( commandListener2 );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertNotNull( commandListener1.getCommandExecutingEvent() );
        assertNotNull( commandListener1.getCommandExecutedEvent() );
        assertFalse( commandListener1.getCommandExecutedEvent().hasException() );

        assertNotNull( commandListener2.getCommandExecutingEvent() );
        assertNotNull( commandListener2.getCommandExecutedEvent() );
        assertFalse( commandListener2.getCommandExecutedEvent().hasException() );

        assertNotNull( stateListener.getStateChangingEvent() );
        assertNotNull( stateListener.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values when the {@code commandExecuting} method of a
     * command listener throws an {@code EngineException}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecuteCommand_Listeners_CommandListener_CommandExecuting_ThrowsEngineExeption()
        throws Exception
    {
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );
        final MockCommandListener commandListener = new MockCommandListener()
        {
            @Override
            public void commandExecuting(
                final CommandExecutingEvent event )
                throws EngineException
            {
                super.commandExecuting( event );

                throw new EngineException();
            }
        };
        addCommandListener( commandListener ); // must be last command before test command

        commandListener.clearEvents();
        stateListener.clearEvents();
        try
        {
            m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        catch( final EngineException e )
        {
            assertNotNull( commandListener.getCommandExecutingEvent() );
            assertNotNull( commandListener.getCommandExecutedEvent() );
            assertTrue( commandListener.getCommandExecutedEvent().hasException() );

            assertNull( stateListener.getStateChangingEvent() );
            assertNull( stateListener.getStateChangedEvent() );

            throw e;
        }
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values when the {@code commandExecuting} method of a
     * command listener throws a non-{@code EngineException}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Listeners_CommandListener_CommandExecuting_ThrowsNonEngineException()
        throws Exception
    {
        final MockCommandListener commandListener1 = new MockCommandListener();
        addCommandListener( commandListener1 );
        final MockCommandListener commandListener2 = new MockCommandListener()
        {
            @Override
            public void commandExecuting(
                final CommandExecutingEvent event )
                throws EngineException
            {
                super.commandExecuting( event );

                throw new RuntimeException();
            }
        };
        addCommandListener( commandListener2 );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertNotNull( commandListener1.getCommandExecutingEvent() );
        assertNotNull( commandListener1.getCommandExecutedEvent() );
        assertFalse( commandListener1.getCommandExecutedEvent().hasException() );

        assertNotNull( commandListener2.getCommandExecutingEvent() );
        assertNotNull( commandListener2.getCommandExecutedEvent() );
        assertFalse( commandListener2.getCommandExecutedEvent().hasException() );

        assertNotNull( stateListener.getStateChangingEvent() );
        assertNotNull( stateListener.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values when the {@code stateChanged} method of a state
     * listener throws an exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Listeners_StateListener_StateChanged_ThrowsException()
        throws Exception
    {
        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener1 = new MockStateListener();
        addStateListener( stateListener1 );
        final MockStateListener stateListener2 = new MockStateListener()
        {
            @Override
            public void stateChanged(
                final StateChangeEvent event )
            {
                super.stateChanged( event );

                throw new RuntimeException();
            }
        };
        addStateListener( stateListener2 );

        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertNotNull( commandListener.getCommandExecutingEvent() );
        assertNotNull( commandListener.getCommandExecutedEvent() );
        assertFalse( commandListener.getCommandExecutedEvent().hasException() );

        assertNotNull( stateListener1.getStateChangingEvent() );
        assertNotNull( stateListener1.getStateChangedEvent() );

        assertNotNull( stateListener2.getStateChangingEvent() );
        assertNotNull( stateListener2.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values when the {@code stateChanging} method of a state
     * listener throws an {@code EngineException}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecuteCommand_Listeners_StateListener_StateChanging_ThrowsEngineException()
        throws Exception
    {
        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener()
        {
            @Override
            public void stateChanging(
                final StateChangeEvent event )
                throws EngineException
            {
                super.stateChanging( event );

                throw new EngineException();
            }
        };
        addStateListener( stateListener ); // must be last command before test command

        commandListener.clearEvents();
        stateListener.clearEvents();
        try
        {
            m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        catch( final EngineException e )
        {
            assertNotNull( commandListener.getCommandExecutingEvent() );
            assertNotNull( commandListener.getCommandExecutedEvent() );
            assertTrue( commandListener.getCommandExecutedEvent().hasException() );

            assertNotNull( stateListener.getStateChangingEvent() );
            assertNull( stateListener.getStateChangedEvent() );

            throw e;
        }
    }

    /**
     * Ensures the {@code executeCommand} method correctly invokes all listeners
     * with the expected values when the {@code stateChanging} method of a state
     * listener throws a non-{@code EngineException}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_Listeners_StateListener_StateChanging_ThrowsNonEngineException()
        throws Exception
    {
        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener1 = new MockStateListener();
        addStateListener( stateListener1 );
        final MockStateListener stateListener2 = new MockStateListener()
        {
            @Override
            public void stateChanging(
                final StateChangeEvent event )
                throws EngineException
            {
                super.stateChanging( event );

                throw new RuntimeException();
            }
        };
        addStateListener( stateListener2 );

        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertNotNull( commandListener.getCommandExecutingEvent() );
        assertNotNull( commandListener.getCommandExecutedEvent() );
        assertFalse( commandListener.getCommandExecutedEvent().hasException() );

        assertNotNull( stateListener1.getStateChangingEvent() );
        assertNotNull( stateListener1.getStateChangedEvent() );

        assertNotNull( stateListener2.getStateChangingEvent() );
        assertNotNull( stateListener2.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code executeCommand} method does not invoke state listeners
     * if the command does not change the state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecuteCommand_StateListeners_NotNotifiedIfNoStateChange()
        throws Exception
    {
        final MockStateListener listener = new MockStateListener();
        addStateListener( listener );

        m_engine.executeCommand( new MockCommand<Void>() );

        assertNull( listener.getStateChangingEvent() );
        assertNull( listener.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code getExtensionRegistry} method does not return {@code
     * null}.
     */
    @Test
    public void testGetExtensionRegistry_ReturnValue_NonNull()
    {
        assertNotNull( m_engine.getExtensionRegistry() );
    }

    /**
     * Ensures the {@code getState} method does not return {@code null}.
     */
    @Test
    public void testGetState_ReturnValue_NonNull()
    {
        assertNotNull( m_engine.getState() );
    }

    /**
     * Ensures the {@code redo} method does not notify any listeners of the
     * redone command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRedo_Listeners_NotNotified()
        throws Exception
    {
        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        CommandHistoryFacade.undo( m_engine );
        commandListener.clearEvents();
        stateListener.clearEvents();
        CommandHistoryFacade.redo( m_engine );

        assertNull( commandListener.getCommandExecutingEvent() );
        assertNull( commandListener.getCommandExecutedEvent() );
        assertNull( stateListener.getStateChangingEvent() );
        assertNull( stateListener.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code removeCommandListener} method removes a listener that
     * has been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveCommandListener_Listener_Registered()
        throws Exception
    {
        final MockCommandListener listener = new MockCommandListener();
        addCommandListener( listener );
        m_engine.executeCommand( new MockCommand<Void>() );

        assertNotNull( listener.getCommandExecutingEvent() );
        assertNotNull( listener.getCommandExecutedEvent() );

        removeCommandListener( listener );
        listener.clearEvents();
        m_engine.executeCommand( new MockCommand<Void>() );

        assertNull( listener.getCommandExecutingEvent() );
        assertNull( listener.getCommandExecutedEvent() );
    }

    /**
     * Ensures the {@code removeStateListener} method removes a listener that
     * has been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveStateListener_Listener_Registered()
        throws Exception
    {
        final MockStateListener listener = new MockStateListener();
        addStateListener( listener );
        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name1" ), "value1" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertNotNull( listener.getStateChangingEvent() );
        assertNotNull( listener.getStateChangedEvent() );

        removeStateListener( listener );
        listener.clearEvents();
        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name2" ), "value2" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertNull( listener.getStateChangingEvent() );
        assertNull( listener.getStateChangedEvent() );
    }

    /**
     * Ensures the {@code undo} method does not notify any listeners of the
     * undone command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUndo_Listeners_NotNotified()
        throws Exception
    {
        final MockCommandListener commandListener = new MockCommandListener();
        addCommandListener( commandListener );
        final MockStateListener stateListener = new MockStateListener();
        addStateListener( stateListener );

        m_engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        commandListener.clearEvents();
        stateListener.clearEvents();
        CommandHistoryFacade.undo( m_engine );

        assertNull( commandListener.getCommandExecutingEvent() );
        assertNull( commandListener.getCommandExecutedEvent() );
        assertNull( stateListener.getStateChangingEvent() );
        assertNull( stateListener.getStateChangedEvent() );
    }
}
