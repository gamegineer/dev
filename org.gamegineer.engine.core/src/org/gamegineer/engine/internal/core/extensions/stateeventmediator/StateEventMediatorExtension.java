/*
 * StateEventMediatorExtension.java
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
 * Created on May 31, 2008 at 9:37:44 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateEventMediator;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;
import org.gamegineer.engine.core.util.attribute.Attribute;
import org.gamegineer.engine.internal.core.Loggers;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

// XXX: The public static methods in this class are temporary until the engine
// SPI model is more mature, at which time the engine will simply visit each
// registered extension allowing them to perform some action before and after
// a command is executed.

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateEventMediator}
 * extension.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class StateEventMediatorExtension
    extends AbstractExtension
    implements IStateEventMediator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the active state listener collection extension context
     * attribute.
     */
    private static final String ATTR_ACTIVE_STATE_LISTENERS = "org.gamegineer.engine.internal.core.extensions.stateeventmediator.activeStateListeners"; //$NON-NLS-1$

    /** The state listener collection attribute. */
    private static final Attribute<List<IStateListener>> STATE_LISTENERS_ATTRIBUTE = new Attribute<List<IStateListener>>( Scope.ENGINE_CONTROL, "org.gamegineer.engine.internal.core.extensions.stateeventmediator.stateListeners" ) //$NON-NLS-1$
    {
        @Override
        protected List<IStateListener> decorateValue(
            final List<IStateListener> value )
        {
            return Collections.unmodifiableList( value );
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventMediatorExtension}
     * class.
     */
    public StateEventMediatorExtension()
    {
        super( IStateEventMediator.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateEventMediator#addStateListener(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener)
     */
    public void addStateListener(
        final IEngineContext context,
        final IStateListener listener )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertExtensionStarted();

        final List<IStateListener> stateListeners = STATE_LISTENERS_ATTRIBUTE.getValue( context.getState() );
        assertArgumentLegal( !stateListeners.contains( listener ), "listener", Messages.StateEventMediatorExtension_listener_registered ); //$NON-NLS-1$

        final List<IStateListener> newStateListeners = new ArrayList<IStateListener>( stateListeners );
        newStateListeners.add( listener );
        STATE_LISTENERS_ATTRIBUTE.setValue( context.getState(), newStateListeners );
    }

    /**
     * Fires the specified state changed event to the specified state listeners.
     * 
     * @param listeners
     *        The state listeners; must not be {@code null}.
     * @param event
     *        The event; must not be {@code null}.
     */
    private static void fireStateChanged(
        /* @NonNull */
        final List<IStateListener> listeners,
        /* @NonNull */
        final StateChangeEvent event )
    {
        assert listeners != null;
        assert event != null;

        for( final IStateListener listener : listeners )
        {
            try
            {
                listener.stateChanged( event );
            }
            catch( final Exception e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.StateEventMediatorExtension_stateChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a state changed event for the specified attribute changes to all
     * state listeners.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param attributeChanges
     *        The collection of state attribute changes; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code attributeChanges} is {@code null}.
     */
    public static void fireStateChanged(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final Map<AttributeName, IAttributeChange> attributeChanges )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( attributeChanges, "attributeChanges" ); //$NON-NLS-1$

        fireStateChanged( getActiveStateListeners( context ), InternalStateChangeEvent.createStateChangeEvent( context, attributeChanges ) );
    }

    /**
     * Fires the specified state changing event to the specified state
     * listeners.
     * 
     * @param listeners
     *        The state listeners; must not be {@code null}.
     * @param event
     *        The event; must not be {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineException
     *         If the state should not be changed.
     */
    private static void fireStateChanging(
        /* @NonNull */
        final List<IStateListener> listeners,
        /* @NonNull */
        final StateChangeEvent event )
        throws EngineException
    {
        assert listeners != null;
        assert event != null;

        for( final IStateListener listener : listeners )
        {
            try
            {
                listener.stateChanging( event );
            }
            catch( final EngineException e )
            {
                throw e;
            }
            catch( final Exception e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.StateEventMediatorExtension_stateChanging_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a state changing event for the specified attribute changes to all
     * state listeners.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param attributeChanges
     *        The collection of state attribute changes; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code attributeChanges} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If the state should not be changed.
     */
    public static void fireStateChanging(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final Map<AttributeName, IAttributeChange> attributeChanges )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( attributeChanges, "attributeChanges" ); //$NON-NLS-1$

        fireStateChanging( getActiveStateListeners( context ), InternalStateChangeEvent.createStateChangeEvent( context, attributeChanges ) );
    }

    /**
     * Gets the active state listener collection.
     * 
     * <p>
     * The active state listener collection represents the state listener
     * collection immediately before the current command was executed. The
     * purpose of this attribute is to ensure events fired during command
     * execution are always sent to the same set of state listeners even if the
     * current command modifies the state listener collection.
     * </p>
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The active state listener collection; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} does not contain the active stat listener
     *         collection.
     */
    /* @NonNull */
    @SuppressWarnings( "unchecked" )
    private static List<IStateListener> getActiveStateListeners(
        /* @NonNull */
        final IEngineContext context )
    {
        assert context != null;

        final List<IStateListener> activeStateListeners = (List<IStateListener>)getExtensionContext( context ).getAttribute( ATTR_ACTIVE_STATE_LISTENERS );
        assertArgumentLegal( activeStateListeners != null, "context", Messages.StateEventMediatorExtension_activeStateListeners_unavailable ); //$NON-NLS-1$

        return activeStateListeners;
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

        getExtensionContext( context ).addAttribute( ATTR_ACTIVE_STATE_LISTENERS, STATE_LISTENERS_ATTRIBUTE.getValue( context.getState() ) );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateEventMediator#removeStateListener(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener)
     */
    public void removeStateListener(
        final IEngineContext context,
        final IStateListener listener )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertExtensionStarted();

        final List<IStateListener> stateListeners = STATE_LISTENERS_ATTRIBUTE.getValue( context.getState() );
        final int index = stateListeners.indexOf( listener );
        assertArgumentLegal( index != -1, "listener", Messages.StateEventMediatorExtension_listener_unregistered ); //$NON-NLS-1$

        final List<IStateListener> newStateListeners = new ArrayList<IStateListener>( stateListeners );
        newStateListeners.remove( index );
        STATE_LISTENERS_ATTRIBUTE.setValue( context.getState(), newStateListeners );
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.AbstractExtension#start(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void start(
        final IEngineContext context )
        throws EngineException
    {
        super.start( context );

        STATE_LISTENERS_ATTRIBUTE.add( context.getState(), Collections.<IStateListener>emptyList() );
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.AbstractExtension#stop(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void stop(
        final IEngineContext context )
        throws EngineException
    {
        super.stop( context );

        STATE_LISTENERS_ATTRIBUTE.remove( context.getState() );
    }
}
