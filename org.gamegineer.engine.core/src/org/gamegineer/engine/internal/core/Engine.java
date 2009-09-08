/*
 * Engine.java
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
 * Created on Apr 6, 2008 at 9:43:01 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MementoBuilder;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.CommandBehavior;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.contexts.command.CommandContextBuilder;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.statemanager.IStateManager;
import org.gamegineer.engine.core.util.attribute.Attribute;
import org.gamegineer.engine.internal.core.commands.NullCommand;
import org.gamegineer.engine.internal.core.extensions.commandeventmediator.CommandEventMediatorExtension;
import org.gamegineer.engine.internal.core.extensions.commandhistory.CommandHistoryExtension;
import org.gamegineer.engine.internal.core.extensions.commandqueue.CommandQueueExtension;
import org.gamegineer.engine.internal.core.extensions.extensionregistry.ExtensionRegistryExtension;
import org.gamegineer.engine.internal.core.extensions.securitymanager.SecurityManagerExtension;
import org.gamegineer.engine.internal.core.extensions.stateeventmediator.StateEventMediatorExtension;
import org.gamegineer.engine.internal.core.extensions.statemanager.StateManagerExtension;

/**
 * Implementation of {@link org.gamegineer.engine.core.IEngine}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
final class Engine
    implements IEngine, ICommandHistory, ICommandQueue, IStateManager
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The command history attribute.
     * 
     * <p>
     * This is not a true attribute because the command history is stored
     * outside of the engine state. It is defined here to reserve its name.
     * </p>
     */
    private static final Attribute<Object> COMMAND_HISTORY_ATTRIBUTE = new Attribute<Object>( Scope.ENGINE_CONTROL, "org.gamegineer.engine.internal.core.commandHistory" ); //$NON-NLS-1$

    /** The engine command history. */
    private final CommandHistory commandHistory_;

    /** The command executor service. */
    private final ExecutorService executorService_;

    /** The state of the engine. */
    private final State state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Engine} class.
     */
    private Engine()
    {
        commandHistory_ = new CommandHistory();
        executorService_ = Executors.newSingleThreadExecutor( new WorkerThreadFactory() );
        state_ = new State();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#canRedo(org.gamegineer.engine.core.IEngineContext)
     */
    public boolean canRedo(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return commandHistory_.canRedo();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#canUndo(org.gamegineer.engine.core.IEngineContext)
     */
    public boolean canUndo(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return commandHistory_.canUndo();
    }

    /**
     * Indicates the specified attribute change map contains at least one
     * application attribute change.
     * 
     * @param attributeChangeMap
     *        The attribute change map; must not be {@code null}.
     * 
     * @return {@code true} if the attribute change map contains at least one
     *         application attribute change; otherwise {@code false}.
     */
    private static boolean containsApplicationAttributeChange(
        /* @NonNull */
        final Map<AttributeName, IAttributeChange> attributeChangeMap )
    {
        assert attributeChangeMap != null;

        for( final AttributeName name : attributeChangeMap.keySet() )
        {
            if( name.getScope() == Scope.APPLICATION )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a command context based on the current thread state.
     * 
     * @return A command context; never {@code null}.
     */
    /* @NonNull */
    private ICommandContext createCommandContext()
    {
        final CommandContextBuilder builder = new CommandContextBuilder();

        try
        {
            SecurityManagerExtension.preSubmitCommand( builder );
        }
        catch( final Exception e )
        {
            Loggers.DEFAULT.log( Level.SEVERE, Messages.Engine_createCommandContext_unexpectedException, e );
        }

        return builder.toCommandContext();
    }

    /**
     * Creates a new instance of the {@code Engine} class with a clean state.
     * 
     * @return A new instance of the {@code Engine} class; never {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while creating the engine.
     */
    /* @NonNull */
    static Engine createEngine()
        throws EngineException
    {
        return createEngine( new NullCommand() );
    }

    /**
     * Creates a new instance of the {@code Engine} class and executes the
     * specified command to initialize the engine state.
     * 
     * @param command
     *        The command used to initialize the engine state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Engine} class; never {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while creating the engine.
     */
    /* @NonNull */
    static Engine createEngine(
        /* @NonNull */
        final ICommand<?> command )
        throws EngineException
    {
        assert command != null;

        final Engine engine = new Engine();

        @PhantomCommand
        class InitializationCommand
            extends AbstractCommand<Void>
        {
            @SuppressWarnings( "synthetic-access" )
            public Void execute(
                final IEngineContext context )
                throws EngineException
            {
                try
                {
                    engine.state_.beginTransaction();
                    engine.initializeExtensions( context );
                    command.execute( context );
                    engine.state_.commitTransaction();

                    return null;
                }
                finally
                {
                    if( engine.state_.isTransactionActive() )
                    {
                        engine.state_.rollbackTransaction();
                    }
                }
            }
        }

        engine.executeCommand( new InitializationCommand() );

        return engine;
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#executeCommand(org.gamegineer.engine.core.ICommand)
     */
    public <T> T executeCommand(
        final ICommand<T> command )
        throws EngineException
    {
        assert !WorkerThread.isWorkerThread();

        try
        {
            return submitCommand( command ).get();
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
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            throw new EngineException( Messages.Engine_executeCommand_executionInterrupted, e );
        }
    }

    /**
     * Executes the specified command synchronously and updates the command
     * history if applicable.
     * 
     * @param <T>
     *        The result type of the command.
     * @param command
     *        The command to be executed; must not be {@code null}.
     * @param commandContext
     *        The command context; must not be {@code null}.
     * @param fireEvents
     *        {@code true} if the appropriate engine events should be fired
     *        during the execution of the command; otherwise {@code false}.
     * @param updateCommandHistory
     *        {@code true} if the command should be added to the command
     *        history; otherwise {@code false}.
     * 
     * @return The result of the command execution; may be {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the command.
     */
    /* @Nullable */
    private <T> T executeCommand(
        /* @NonNull */
        final ICommand<T> command,
        /* @NonNull */
        final ICommandContext commandContext,
        final boolean fireEvents,
        final boolean updateCommandHistory )
        throws EngineException
    {
        assert command != null;
        assert commandContext != null;

        final IEngineContext engineContext = new EngineContext( this, commandContext );

        try
        {
            CommandEventMediatorExtension.preExecuteCommand( engineContext );
            StateEventMediatorExtension.preExecuteCommand( engineContext );

            if( fireEvents )
            {
                CommandEventMediatorExtension.fireCommandExecuting( engineContext, command );
            }

            final T result;
            final Map<AttributeName, IAttributeChange> attributeChangeMap;
            final boolean addCommandToCommandHistory;
            try
            {
                if( isTransactionRequired( command ) )
                {
                    state_.beginTransaction();
                }

                result = command.execute( engineContext );

                if( state_.isTransactionActive() )
                {
                    state_.prepareToCommitTransaction();

                    // TODO: We should get the set of scopes which have changed at the same time
                    // we get the attribute changes to avoid having to iterate over the changes
                    // searching for the application scope.  Try to do this without introducing
                    // an out parameter to State.getAttributeChanges.  If that's not possible,
                    // probably have to add an overload that accepts the out parameter to not
                    // require clients to request that information.
                    attributeChangeMap = state_.getAttributeChanges();
                    addCommandToCommandHistory = containsApplicationAttributeChange( attributeChangeMap );
                    if( fireEvents && !attributeChangeMap.isEmpty() )
                    {
                        StateEventMediatorExtension.fireStateChanging( engineContext, attributeChangeMap );
                    }

                    state_.commitTransaction();
                }
                else
                {
                    attributeChangeMap = Collections.emptyMap();
                    addCommandToCommandHistory = false;
                }
            }
            finally
            {
                if( state_.isTransactionActive() )
                {
                    state_.rollbackTransaction();
                }
            }

            if( updateCommandHistory && addCommandToCommandHistory )
            {
                commandHistory_.add( (IInvertibleCommand<?>)command, commandContext );
            }

            if( fireEvents )
            {
                CommandEventMediatorExtension.fireSuccessfulCommandExecuted( engineContext, command, result );
                if( !attributeChangeMap.isEmpty() )
                {
                    StateEventMediatorExtension.fireStateChanged( engineContext, attributeChangeMap );
                }
            }

            if( Debug.DEFAULT )
            {
                Debug.trace( String.format( "Executed command: '%1$s'.", command ) ); //$NON-NLS-1$
            }

            return result;
        }
        catch( final EngineException e )
        {
            if( fireEvents )
            {
                CommandEventMediatorExtension.fireFailedCommandExecuted( engineContext, command, e );
            }

            throw e;
        }
    }

    /**
     * Executes the specified phantom command synchronously.
     * 
     * <p>
     * Note the following differences between the execution of a phantom command
     * and a normal command:
     * </p>
     * 
     * <ul>
     * <li>The phantom command is not added to the command history.</li>
     * <li>Standard engine events are not fired.</li>
     * <li>A transaction is not activated during the execution of the phantom
     * command.</li>
     * </ul>
     * 
     * @param <T>
     *        The result type of the command.
     * @param command
     *        The phantom command to be executed; must not be {@code null}.
     * @param commandContext
     *        The command context; must not be {@code null}.
     * 
     * @return The result of the phantom command execution; may be {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs during the execution of the phantom command.
     */
    /* @Nullable */
    private <T> T executePhantomCommand(
        /* @NonNull */
        final ICommand<T> command,
        /* @NonNull */
        final ICommandContext commandContext )
        throws EngineException
    {
        assert command != null;
        assert command.getClass().isAnnotationPresent( PhantomCommand.class );
        assert commandContext != null;

        final T result = command.execute( new EngineContext( this, commandContext ) );

        if( Debug.DEFAULT )
        {
            Debug.trace( String.format( "Executed phantom command: '%1$s'.", command ) ); //$NON-NLS-1$
        }

        return result;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#getCommands(org.gamegineer.engine.core.IEngineContext)
     */
    public List<IInvertibleCommand<?>> getCommands(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final List<IInvertibleCommand<?>> commands = new ArrayList<IInvertibleCommand<?>>();
        for( final CommandHistory.Entry commandHistoryEntry : commandHistory_.getEntries() )
        {
            commands.add( commandHistoryEntry.getCommand() );
        }
        return commands;
    }

    /**
     * Gets the engine extension registry.
     * 
     * @return The engine extension registry; never {@code null}.
     */
    /* @NonNull */
    IExtensionRegistry getExtensionRegistry()
    {
        return ExtensionRegistryExtension.getExtensionRegistry( state_ );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.statemanager.IStateManager#getMemento(org.gamegineer.engine.core.IEngineContext)
     */
    public IMemento getMemento(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final MementoBuilder builder = new MementoBuilder();

        // Add persistent engine control attributes
        builder.addAttribute( COMMAND_HISTORY_ATTRIBUTE.getName().toString(), commandHistory_.getEntries() );

        // All application attributes are persistent
        for( final AttributeName name : state_.getAttributeNames( Scope.APPLICATION ) )
        {
            builder.addAttribute( name.toString(), state_.getAttribute( name ) );
        }

        return builder.toMemento();
    }

    /**
     * Gets the engine state.
     * 
     * @return The engine state; never {@code null}.
     */
    /* @NonNull */
    State getState()
    {
        return state_;
    }

    /**
     * Initializes the built-in engine extensions.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while initializing any of the extensions.
     */
    private void initializeExtensions(
        /* @NonNull */
        final IEngineContext context )
        throws EngineException
    {
        assert context != null;

        // Bootstrap the extension registry extension 
        final ExtensionRegistryExtension extensionRegistry = new ExtensionRegistryExtension();
        extensionRegistry.start( context );

        // Command history (NB: the actual attribute exists outside the state, so we add a
        // bogus value to ensure the attribute name is reserved)
        COMMAND_HISTORY_ATTRIBUTE.add( state_, new Object() );
        extensionRegistry.registerExtension( context, new CommandHistoryExtension( this ) );

        // Command queue
        extensionRegistry.registerExtension( context, new CommandQueueExtension( this ) );

        // State manager
        extensionRegistry.registerExtension( context, new StateManagerExtension( this ) );

        // Security manager
        extensionRegistry.registerExtension( context, new SecurityManagerExtension() );

        // Command event mediator
        extensionRegistry.registerExtension( context, new CommandEventMediatorExtension() );

        // State event mediator
        extensionRegistry.registerExtension( context, new StateEventMediatorExtension() );
    }

    /**
     * Indicates the specified command acquires the engine write lock but is not
     * invertible.
     * 
     * @param command
     *        The command; must not be {@code null}.
     * 
     * @return {@code true} if the specified command acquires the engine write
     *         lock but is not invertible; otherwise {@code false}.
     */
    private static boolean isNonInvertibleWriteCommand(
        /* @NonNull */
        final ICommand<?> command )
    {
        assert command != null;

        final CommandBehavior behavior = command.getClass().getAnnotation( CommandBehavior.class );
        if( behavior == null )
        {
            return false;
        }

        return behavior.writeLockRequired() && !(command instanceof IInvertibleCommand<?>);
    }

    /**
     * Indicates the specified command is a phantom command.
     * 
     * @param command
     *        The command; must not be {@code null}.
     * 
     * @return {@code true} if the specified command is a phantom command;
     *         otherwise {@code false}.
     */
    private static boolean isPhantomCommand(
        /* @NonNull */
        final ICommand<?> command )
    {
        assert command != null;

        return command.getClass().isAnnotationPresent( PhantomCommand.class );
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#isShutdown()
     */
    public boolean isShutdown()
    {
        return executorService_.isShutdown();
    }

    /**
     * Indicates the specified command requires a transaction.
     * 
     * @param command
     *        The command; must not be {@code null}.
     * 
     * @return {@code true} if the specified command requires a transaction;
     *         otherwise {@code false}.
     */
    private static boolean isTransactionRequired(
        /* @NonNull */
        final ICommand<?> command )
    {
        assert command != null;

        final CommandBehavior behavior = command.getClass().getAnnotation( CommandBehavior.class );
        if( behavior == null )
        {
            return false;
        }

        return behavior.writeLockRequired();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#redo(org.gamegineer.engine.core.IEngineContext)
     */
    public void redo(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertStateLegal( commandHistory_.canRedo(), Messages.Engine_redo_notAvailable );

        final CommandHistory.Entry redoEntry = commandHistory_.getRedoEntry();
        executeCommand( redoEntry.getCommand(), redoEntry.getCommandContext(), false, false );
        commandHistory_.redo();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.statemanager.IStateManager#setMemento(org.gamegineer.engine.core.IEngineContext, org.gamegineer.common.persistence.memento.IMemento)
     */
    public void setMemento(
        final IEngineContext context,
        final IMemento memento )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        List<CommandHistory.Entry> commandHistory = null;

        try
        {
            state_.beginTransaction();

            state_.removeAllAttributes( state_.getAttributeNames( Scope.APPLICATION ) );

            for( final String mementoAttrName : memento.getAttributeNames() )
            {
                final AttributeName stateAttrName;
                try
                {
                    stateAttrName = AttributeName.fromString( mementoAttrName );
                }
                catch( final IllegalArgumentException e )
                {
                    throw new EngineException( Messages.Engine_setMemento_attributeName_illegalFormat( mementoAttrName ) );
                }

                switch( stateAttrName.getScope() )
                {
                    case APPLICATION:
                        state_.addAttribute( stateAttrName, memento.getAttribute( mementoAttrName ) );
                        break;

                    case ENGINE_CONTROL:
                        if( COMMAND_HISTORY_ATTRIBUTE.getName().equals( stateAttrName ) )
                        {
                            commandHistory = memento.getAttribute( mementoAttrName );
                        }
                        else
                        {
                            throw new EngineException( Messages.Engine_setMemento_attribute_transient( stateAttrName ) );
                        }
                        break;

                    default:
                        throw new EngineException( Messages.Engine_setMemento_attribute_transient( stateAttrName ) );
                }
            }

            if( commandHistory == null )
            {
                throw new EngineException( Messages.Engine_setMemento_commandHistory_absent );
            }

            state_.commitTransaction();
        }
        finally
        {
            if( state_.isTransactionActive() )
            {
                state_.rollbackTransaction();
            }
        }

        commandHistory_.reset( commandHistory );
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#shutdown()
     */
    public void shutdown()
        throws InterruptedException
    {
        executorService_.shutdown();
        executorService_.awaitTermination( Long.MAX_VALUE, TimeUnit.NANOSECONDS );
    }

    /*
     * @see org.gamegineer.engine.core.IEngine#submitCommand(org.gamegineer.engine.core.ICommand)
     */
    public <T> Future<T> submitCommand(
        final ICommand<T> command )
    {
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        return submitCommand( command, createCommandContext() );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue#submitCommand(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.ICommand)
     */
    public <T> Future<T> submitCommand(
        final IEngineContext context,
        final ICommand<T> command )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        final ICommandContext commandContext = context.getContext( ICommandContext.class );
        assertArgumentLegal( commandContext != null, "context", Messages.Engine_submitCommand_illegalContext ); //$NON-NLS-1$

        return submitCommand( command, commandContext );
    }

    /**
     * Executes the specified command asynchronously.
     * 
     * @param <T>
     *        The result type of the command.
     * @param command
     *        The command to be executed; must not be {@code null}.
     * @param commandContext
     *        The command context; must not be {@code null}.
     * 
     * @return A handle to the asynchronous operation; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the engine has been shut down.
     */
    /* @NonNull */
    private <T> Future<T> submitCommand(
        /* @NonNull */
        final ICommand<T> command,
        /* @NonNull */
        final ICommandContext commandContext )
    {
        assert command != null;
        assert commandContext != null;

        final Callable<T> callable = new Callable<T>()
        {
            @SuppressWarnings( "synthetic-access" )
            public T call()
                throws Exception
            {
                if( isPhantomCommand( command ) )
                {
                    return executePhantomCommand( command, commandContext );
                }
                else if( isNonInvertibleWriteCommand( command ) )
                {
                    return executeCommand( new InvertibleCommandAdapter<T>( command ), commandContext, true, true );
                }

                return executeCommand( command, commandContext, true, true );
            }
        };

        try
        {
            return new SafeFuture<T>( executorService_.submit( callable ) );
        }
        catch( final RejectedExecutionException e )
        {
            throw new IllegalStateException( Messages.Engine_submitCommand_shutdown, e );
        }
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#undo(org.gamegineer.engine.core.IEngineContext)
     */
    public void undo(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertStateLegal( commandHistory_.canUndo(), Messages.Engine_undo_notAvailable );

        final CommandHistory.Entry undoEntry = commandHistory_.getUndoEntry();
        executeCommand( undoEntry.getCommand().getInverseCommand(), undoEntry.getCommandContext(), false, false );
        commandHistory_.undo();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link java.util.concurrent.Future} that ensures
     * clients do not wait upon the result of the associated asynchronous task
     * from an engine worker thread.
     * 
     * @param <V>
     *        The type of the task result.
     */
    private static final class SafeFuture<V>
        implements Future<V>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The asynchronous task to which all operations are delegated. */
        private final Future<V> future_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code SafeFuture} class.
         * 
         * @param future
         *        The asynchronous task to which all operations are delegated;
         *        must not be {@code null}.
         */
        SafeFuture(
            /* @NonNull */
            final Future<V> future )
        {
            assert future != null;

            future_ = future;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Asserts that the current thread is not an engine worker thread.
         */
        private static void assertNotWorkerThread()
        {
            if( WorkerThread.isWorkerThread() )
            {
                throw new AssertionError( Messages.Engine_SafeFuture_waitFromWorkerThread );
            }
        }

        /*
         * @see java.util.concurrent.Future#cancel(boolean)
         */
        public boolean cancel(
            final boolean mayInterruptIfRunning )
        {
            return future_.cancel( mayInterruptIfRunning );
        }

        /*
         * @see java.util.concurrent.Future#get()
         */
        public V get()
            throws InterruptedException, ExecutionException
        {
            assertNotWorkerThread();

            return future_.get();
        }

        /*
         * @see java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)
         */
        public V get(
            final long timeout,
            final TimeUnit unit )
            throws InterruptedException, ExecutionException, TimeoutException
        {
            assertNotWorkerThread();

            return future_.get( timeout, unit );
        }

        /*
         * @see java.util.concurrent.Future#isCancelled()
         */
        public boolean isCancelled()
        {
            return future_.isCancelled();
        }

        /*
         * @see java.util.concurrent.Future#isDone()
         */
        public boolean isDone()
        {
            return future_.isDone();
        }
    }

    /**
     * A worker thread for executing engine commands.
     */
    private static final class WorkerThread
        extends Thread
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Indicates a thread is an engine worker thread. */
        private static final ThreadLocal<Boolean> isWorkerThread_ = new ThreadLocal<Boolean>()
        {
            @Override
            protected Boolean initialValue()
            {
                return Boolean.FALSE;
            }
        };


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code WorkerThread} class.
         * 
         * @param r
         *        The object to be executed in the thread; may be {@code null}.
         * @param name
         *        The thread name; may be {@code null}.
         */
        WorkerThread(
            /* @Nullable */
            final Runnable r,
            /* @Nullable */
            final String name )
        {
            super( r, name );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Indicates the current thread is an engine worker thread.
         * 
         * @return {@code true} if the current thread is an engine worker
         *         thread; otherwise {@code false}.
         */
        static boolean isWorkerThread()
        {
            return isWorkerThread_.get().booleanValue();
        }

        /*
         * @see java.lang.Thread#run()
         */
        @Override
        public void run()
        {
            isWorkerThread_.set( Boolean.TRUE );

            super.run();
        }
    }

    /**
     * A factory for creating instances of {@link Engine.WorkerThread}.
     */
    private static final class WorkerThreadFactory
        implements ThreadFactory
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The unique identifier for the next thread factory. */
        private static final AtomicInteger poolNumber_ = new AtomicInteger();

        /** The name prefix for all threads created by this factory. */
        private final String namePrefix_;

        /** The unique identifier for the next thread created by this factory. */
        private final AtomicInteger threadNumber_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code WorkerThreadFactory} class.
         */
        WorkerThreadFactory()
        {
            namePrefix_ = "GamegineerEngine-Pool-" + poolNumber_.incrementAndGet() + "-Thread-"; //$NON-NLS-1$ //$NON-NLS-2$
            threadNumber_ = new AtomicInteger();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
         */
        public Thread newThread(
            final Runnable r )
        {
            return new WorkerThread( r, namePrefix_ + threadNumber_.incrementAndGet() );
        }
    }
}
