/*
 * ActionMediator.java
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
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.event.ActionListener;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.Action;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;

/**
 * A mediator between a client and its associated actions.
 * 
 * <p>
 * Clients are expected to use this class to manage their action attachments,
 * such as action listeners, so they can be properly unbound when the client
 * goes out of scope.
 * </p>
 */
@NotThreadSafe
public final class ActionMediator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of bound action listeners. */
    private final Map<BasicAction, ActionListener> actionListeners_;

    /** The collection of bound should enable action predicates. */
    private final Map<BasicAction, IPredicate<Action>> shouldEnablePredicates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionMediator} class.
     */
    public ActionMediator()
    {
        actionListeners_ = new IdentityHashMap<BasicAction, ActionListener>();
        shouldEnablePredicates_ = new IdentityHashMap<BasicAction, IPredicate<Action>>();
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
     * @throws java.lang.IllegalStateException
     *         If an action listener is already bound to {@code action}.
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
        assertStateLegal( !actionListeners_.containsKey( action ), Messages.ActionMediator_bindActionListener_alreadyBound );

        actionListeners_.put( action, listener );

        action.addActionListener( listener );
    }

    /**
     * Binds the specified should enable action predicate to the specified
     * action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * @param predicate
     *        The should enable action predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If a should enable action predicate is already bound to the
     *         action.
     * @throws java.lang.NullPointerException
     *         If {@code action} or {@code predicate} is {@code null}.
     */
    public void bind(
        /* @NonNull */
        final BasicAction action,
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertStateLegal( !shouldEnablePredicates_.containsKey( action ), Messages.ActionMediator_bindShouldEnablePredicate_alreadyBound );

        shouldEnablePredicates_.put( action, predicate );

        action.addShouldEnablePredicate( predicate );
        action.update();
    }

    /**
     * Indicates the specified action has at least one bound attachment.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @return {@code true} if the specified action has at least one bound
     *         attachment; otherwise {@code false}.
     */
    private boolean hasAttachments(
        /* @NonNull */
        final BasicAction action )
    {
        assert action != null;

        return actionListeners_.containsKey( action ) || hasPredicates( action );
    }

    /**
     * Indicates the specified action has at least one bound predicate.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @return {@code true} if the specified action has at least one bound
     *         predicate; otherwise {@code false}.
     */
    private boolean hasPredicates(
        /* @NonNull */
        final BasicAction action )
    {
        assert action != null;

        return shouldEnablePredicates_.containsKey( action );
    }

    /**
     * Unbinds all attachments from the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code action} has no bound attachments.
     * @throws java.lang.NullPointerException
     *         If {@code action} is {@code null}.
     */
    public void unbind(
        /* @NonNull */
        final BasicAction action )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$
        assertArgumentLegal( hasAttachments( action ), "action", Messages.ActionMediator_unbind_noAttachments ); //$NON-NLS-1$

        final IPredicate<Action> predicate = shouldEnablePredicates_.remove( action );
        if( predicate != null )
        {
            action.removeShouldEnablePredicate( predicate );
            action.update();
        }

        final ActionListener listener = actionListeners_.remove( action );
        if( listener != null )
        {
            action.removeActionListener( listener );
        }
    }

    /**
     * Unbinds all attachments from all actions.
     */
    public void unbindAll()
    {
        for( final Map.Entry<BasicAction, IPredicate<Action>> entry : shouldEnablePredicates_.entrySet() )
        {
            entry.getKey().removeShouldEnablePredicate( entry.getValue() );
            entry.getKey().update();
        }
        shouldEnablePredicates_.clear();

        for( final Map.Entry<BasicAction, ActionListener> entry : actionListeners_.entrySet() )
        {
            entry.getKey().removeActionListener( entry.getValue() );
        }
        actionListeners_.clear();
    }

    /**
     * Updates the state of the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code action} has no bound predicates.
     * @throws java.lang.NullPointerException
     *         If {@code action} is {@code null}.
     */
    public void update(
        /* @NonNull */
        final BasicAction action )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$
        assertArgumentLegal( hasPredicates( action ), "action", Messages.ActionMediator_update_noPredicates ); //$NON-NLS-1$

        action.update();
    }

    /**
     * Updates the state of all actions.
     */
    public void updateAll()
    {
        for( final BasicAction action : shouldEnablePredicates_.keySet() )
        {
            action.update();
        }
    }
}
