/*
 * CommandEventMediatorExtension.java
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
 * Created on May 30, 2008 at 9:10:51 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutedEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandExecutingEvent;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;
import org.gamegineer.engine.core.util.attribute.Attribute;
import org.gamegineer.engine.internal.core.Loggers;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

// XXX: The public static methods in this class are temporary until the engine
// SPI model is more mature, at which time the engine will simply visit each
// registered extension allowing them to perform some action before and after
// a command is executed.

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator}
 * extension.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class CommandEventMediatorExtension
    extends AbstractExtension
    implements ICommandEventMediator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the active command listener collection extension context
     * attribute.
     */
    private static final String ATTR_ACTIVE_COMMAND_LISTENERS = "org.gamegineer.engine.internal.core.extensions.commandeventmediator.activeCommandListeners"; //$NON-NLS-1$

    /** The command listener collection attribute. */
    private static final Attribute<List<ICommandListener>> COMMAND_LISTENERS_ATTRIBUTE = new Attribute<List<ICommandListener>>( Scope.ENGINE_CONTROL, "org.gamegineer.engine.internal.core.extensions.commandeventmediator.commandListeners" ) //$NON-NLS-1$
    {
        @Override
        protected List<ICommandListener> decorateValue(
            final List<ICommandListener> value )
        {
            return Collections.unmodifiableList( value );
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandEventMediatorExtension}
     * class.
     */
    public CommandEventMediatorExtension()
    {
        super( ICommandEventMediator.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator#addCommandListener(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener)
     */
    public void addCommandListener(
        final IEngineContext context,
        final ICommandListener listener )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertExtensionStarted();

        final List<ICommandListener> commandListeners = COMMAND_LISTENERS_ATTRIBUTE.getValue( context.getState() );
        assertArgumentLegal( !commandListeners.contains( listener ), "listener", Messages.CommandEventMediatorExtension_listener_registered ); //$NON-NLS-1$

        final List<ICommandListener> newCommandListeners = new ArrayList<ICommandListener>( commandListeners );
        newCommandListeners.add( listener );
        COMMAND_LISTENERS_ATTRIBUTE.setValue( context.getState(), newCommandListeners );
    }

    /**
     * Fires the specified command executed event to the specified command
     * listeners.
     * 
     * @param listeners
     *        The command listeners; must not be {@code null}.
     * @param event
     *        The event; must not be {@code null}.
     */
    private static void fireCommandExecuted(
        /* @NonNull */
        final List<ICommandListener> listeners,
        /* @NonNull */
        final CommandExecutedEvent event )
    {
        assert listeners != null;
        assert event != null;

        for( final ICommandListener listener : listeners )
        {
            try
            {
                listener.commandExecuted( event );
            }
            catch( final Exception e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CommandEventMediatorExtension_commandExecuted_unexpectedException, e );
            }
        }
    }

    /**
     * Fires the specified command executing event to the specified command
     * listeners.
     * 
     * @param listeners
     *        The command listeners; must not be {@code null}.
     * @param event
     *        The event; must not be {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If the command should not be executed.
     */
    private static void fireCommandExecuting(
        /* @NonNull */
        final List<ICommandListener> listeners,
        /* @NonNull */
        final CommandExecutingEvent event )
        throws EngineException
    {
        assert listeners != null;
        assert event != null;

        for( final ICommandListener listener : listeners )
        {
            try
            {
                listener.commandExecuting( event );
            }
            catch( final EngineException e )
            {
                throw e;
            }
            catch( final Exception e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CommandEventMediatorExtension_commandExecuting_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a command executing event for the specified command to all command
     * listeners.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code command} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If the command should not be executed.
     */
    public static void fireCommandExecuting(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        fireCommandExecuting( getActiveCommandListeners( context ), InternalCommandExecutingEvent.createCommandExecutingEvent( context, command ) );
    }

    /**
     * Fires a failed command executed event for the specified command to all
     * command listeners.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param exception
     *        The exception thrown during command execution; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context}, {@code command}, or {@code execution} is
     *         {@code null}.
     */
    public static void fireFailedCommandExecuted(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @NonNull */
        final Exception exception )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$
        assertArgumentNotNull( exception, "exception" ); //$NON-NLS-1$

        fireCommandExecuted( getActiveCommandListeners( context ), InternalCommandExecutedEvent.createFailedCommandExecutedEvent( context, command, exception ) );
    }

    /**
     * Fires a successful command executed event for the specified command to
     * all command listeners.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param command
     *        The command associated with the event; must not be {@code null}.
     * @param result
     *        The result of the command execution; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code command} is {@code null}.
     */
    public static void fireSuccessfulCommandExecuted(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final ICommand<?> command,
        /* @Nullable */
        final Object result )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        fireCommandExecuted( getActiveCommandListeners( context ), InternalCommandExecutedEvent.createSuccessfulCommandExecutedEvent( context, command, result ) );
    }

    /**
     * Gets the active command listener collection.
     * 
     * <p>
     * The active command listener collection represents the command listener
     * collection immediately before the current command was executed. The
     * purpose of this attribute is to ensure events fired during command
     * execution are always sent to the same set of command listeners even if
     * the current command modifies the command listener collection.
     * </p>
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The active command listener collection; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} does not contain the active command listener
     *         collection.
     */
    /* @NonNull */
    @SuppressWarnings( "unchecked" )
    private static List<ICommandListener> getActiveCommandListeners(
        /* @NonNull */
        final IEngineContext context )
    {
        assert context != null;

        final List<ICommandListener> activeCommandListeners = (List<ICommandListener>)getExtensionContext( context ).getAttribute( ATTR_ACTIVE_COMMAND_LISTENERS );
        assertArgumentLegal( activeCommandListeners != null, "context", Messages.CommandEventMediatorExtension_activeCommandListeners_unavailable ); //$NON-NLS-1$

        return activeCommandListeners;
    }

    /**
     * Invoked immediately before the current command is executed.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public static void preExecuteCommand(
        /* @NonNull */
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        getExtensionContext( context ).addAttribute( ATTR_ACTIVE_COMMAND_LISTENERS, COMMAND_LISTENERS_ATTRIBUTE.getValue( context.getState() ) );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator#removeCommandListener(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener)
     */
    public void removeCommandListener(
        final IEngineContext context,
        final ICommandListener listener )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertExtensionStarted();

        final List<ICommandListener> commandListeners = COMMAND_LISTENERS_ATTRIBUTE.getValue( context.getState() );
        final int index = commandListeners.indexOf( listener );
        assertArgumentLegal( index != -1, "listener", Messages.CommandEventMediatorExtension_listener_unregistered ); //$NON-NLS-1$

        final List<ICommandListener> newCommandListeners = new ArrayList<ICommandListener>( commandListeners );
        newCommandListeners.remove( index );
        COMMAND_LISTENERS_ATTRIBUTE.setValue( context.getState(), newCommandListeners );
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#start(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void start(
        final IEngineContext context )
        throws EngineException
    {
        super.start( context );

        COMMAND_LISTENERS_ATTRIBUTE.add( context.getState(), Collections.<ICommandListener>emptyList() );
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#stop(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void stop(
        final IEngineContext context )
        throws EngineException
    {
        super.stop( context );

        COMMAND_LISTENERS_ATTRIBUTE.remove( context.getState() );
    }
}
