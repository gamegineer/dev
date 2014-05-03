/*
 * ActionMediator.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.action;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
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

    /** The collection of bound should select action predicates. */
    private final Map<BasicAction, IPredicate<Action>> shouldSelectPredicates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionMediator} class.
     */
    public ActionMediator()
    {
        actionListeners_ = new IdentityHashMap<>();
        shouldEnablePredicates_ = new IdentityHashMap<>();
        shouldSelectPredicates_ = new IdentityHashMap<>();
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
     */
    public void bindActionListener(
        final BasicAction action,
        final ActionListener listener )
    {
        assertStateLegal( !actionListeners_.containsKey( action ), NonNlsMessages.ActionMediator_bindActionListener_alreadyBound );

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
     */
    public void bindShouldEnablePredicate(
        final BasicAction action,
        final IPredicate<Action> predicate )
    {
        assertStateLegal( !shouldEnablePredicates_.containsKey( action ), NonNlsMessages.ActionMediator_bindShouldEnablePredicate_alreadyBound );

        shouldEnablePredicates_.put( action, predicate );

        action.addShouldEnablePredicate( predicate );
        action.update();
    }

    /**
     * Binds the specified should select action predicate to the specified
     * action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * @param predicate
     *        The should select action predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If a should select action predicate is already bound to the
     *         action.
     */
    public void bindShouldSelectPredicate(
        final BasicAction action,
        final IPredicate<Action> predicate )
    {
        assertStateLegal( !shouldSelectPredicates_.containsKey( action ), NonNlsMessages.ActionMediator_bindShouldSelectPredicate_alreadyBound );

        shouldSelectPredicates_.put( action, predicate );

        action.addShouldSelectPredicate( predicate );
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
        final BasicAction action )
    {
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
        final BasicAction action )
    {
        return shouldEnablePredicates_.containsKey( action ) || shouldSelectPredicates_.containsKey( action );
    }

    /**
     * Unbinds all attachments from the specified action.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code action} has no bound attachments.
     */
    public void unbind(
        final BasicAction action )
    {
        assertArgumentLegal( hasAttachments( action ), "action", NonNlsMessages.ActionMediator_unbind_noAttachments ); //$NON-NLS-1$

        final IPredicate<Action> shouldEnablePredicate = shouldEnablePredicates_.remove( action );
        if( shouldEnablePredicate != null )
        {
            action.removeShouldEnablePredicate( shouldEnablePredicate );
            action.update();
        }

        final IPredicate<Action> shouldSelectPredicate = shouldSelectPredicates_.remove( action );
        if( shouldSelectPredicate != null )
        {
            action.removeShouldSelectPredicate( shouldSelectPredicate );
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
            final IPredicate<Action> shouldEnablePredicate = entry.getValue();
            assert shouldEnablePredicate != null;
            entry.getKey().removeShouldEnablePredicate( shouldEnablePredicate );
            entry.getKey().update();
        }
        shouldEnablePredicates_.clear();

        for( final Map.Entry<BasicAction, IPredicate<Action>> entry : shouldSelectPredicates_.entrySet() )
        {
            final IPredicate<Action> shouldSelectPredicate = entry.getValue();
            assert shouldSelectPredicate != null;
            entry.getKey().removeShouldSelectPredicate( shouldSelectPredicate );
            entry.getKey().update();
        }
        shouldSelectPredicates_.clear();

        for( final Map.Entry<BasicAction, ActionListener> entry : actionListeners_.entrySet() )
        {
            final ActionListener actionListener = entry.getValue();
            assert actionListener != null;
            entry.getKey().removeActionListener( actionListener );
        }
        actionListeners_.clear();
    }
}
