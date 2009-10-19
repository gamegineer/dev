/*
 * ActionListenerManager.java
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
 * Created on Oct 18, 2009 at 9:41:35 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;

/**
 * A manager of action listeners.
 * 
 * <p>
 * This class acts as a mediator between actions and their bound action
 * listeners. Clients are expected to use this class to manage their action
 * listeners so they can be properly unbound when the client goes out of scope.
 * </p>
 */
@NotThreadSafe
public final class ActionListenerManager
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of action listener collections. */
    private final Map<BasicAction, Collection<ActionListener>> listenerCollections_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionListenerManager} class.
     */
    public ActionListenerManager()
    {
        listenerCollections_ = new IdentityHashMap<BasicAction, Collection<ActionListener>>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified action listener to the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * @param listener
     *        The action listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already bound to {@code action}.
     * @throws java.lang.NullPointerException
     *         If {@code action} or {@code listener} is {@code null}.
     */
    public void bind(
        /* @NonNull */
        final BasicAction action,
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        final Collection<ActionListener> listeners = getActionListeners( action );
        assertArgumentLegal( !listeners.contains( listener ), "listener" ); //$NON-NLS-1$
        listeners.add( listener );

        action.addActionListener( listener );
    }

    /**
     * Gets the collection of bound action listeners for the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @return The collection of bound action listeners for the specified
     *         action; never {@code null}.
     */
    /* @NonNull */
    private Collection<ActionListener> getActionListeners(
        /* @NonNull */
        final BasicAction action )
    {
        assert action != null;

        Collection<ActionListener> listeners = listenerCollections_.get( action );
        if( listeners == null )
        {
            listeners = new ArrayList<ActionListener>();
            listenerCollections_.put( action, listeners );
        }

        return listeners;
    }

    /**
     * Removes all action listeners for the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     */
    private void removeActionListeners(
        /* @NonNull */
        final BasicAction action )
    {
        assert action != null;

        for( final ActionListener listener : getActionListeners( action ) )
        {
            action.removeActionListener( listener );
        }
    }

    /**
     * Unbinds the specified action listener from the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * @param listener
     *        The action listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not bound to {@code action}.
     * @throws java.lang.NullPointerException
     *         If {@code action} or {@code listener} is {@code null}.
     */
    public void unbind(
        /* @NonNull */
        final BasicAction action,
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        final Collection<ActionListener> listeners = getActionListeners( action );
        assertArgumentLegal( listeners.remove( listener ), "listener" ); //$NON-NLS-1$

        action.removeActionListener( listener );
    }

    /**
     * Unbinds all action listeners from all actions.
     */
    public void unbindAll()
    {
        for( final BasicAction action : listenerCollections_.keySet() )
        {
            removeActionListeners( action );
        }

        listenerCollections_.clear();
    }

    /**
     * Unbinds all action listeners from the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code action} is {@code null}.
     */
    public void unbindAll(
        /* @NonNull */
        final BasicAction action )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$

        removeActionListeners( action );

        listenerCollections_.remove( action );
    }
}
