/*
 * BasicAction.java
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
 * Created on Oct 9, 2009 at 10:34:12 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.IPredicate;

/**
 * An extension to the Swing action framework that allows delegation of action
 * behavior and state to any number of observers.
 */
@ThreadSafe
public class BasicAction
    extends AbstractAction
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 4533526026382678240L;

    /** The collection of action listeners. */
    private final CopyOnWriteArrayList<ActionListener> actionListeners_;

    /** The collection of should enable predicates. */
    private final CopyOnWriteArrayList<IPredicate<Action>> shouldEnablePredicates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BasicAction} class.
     */
    public BasicAction()
    {
        actionListeners_ = new CopyOnWriteArrayList<ActionListener>();
        shouldEnablePredicates_ = new CopyOnWriteArrayList<IPredicate<Action>>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public final void actionPerformed(
        final ActionEvent e )
    {
        for( final ActionListener listener : actionListeners_ )
        {
            listener.actionPerformed( e );
        }
    }

    /**
     * Adds the specified listener to this action.
     * 
     * @param listener
     *        The listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addActionListener(
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( actionListeners_.addIfAbsent( listener ), "listener" ); //$NON-NLS-1$
    }

    /**
     * Adds the specified should enable predicate to this action.
     * 
     * @param predicate
     *        The predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code predicate} is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code predicate} is {@code null}.
     */
    public void addShouldEnablePredicate(
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertArgumentLegal( shouldEnablePredicates_.addIfAbsent( predicate ), "predicate" ); //$NON-NLS-1$
    }

    /**
     * Removes the specified listener from this action.
     * 
     * @param listener
     *        The listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not registered.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeActionListener(
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( actionListeners_.remove( listener ), "listener" ); //$NON-NLS-1$
    }

    /**
     * Removes the specified should enable predicate from this action.
     * 
     * @param predicate
     *        The predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code predicate} is not registered.
     * @throws java.lang.NullPointerException
     *         If {@code predicate} is {@code null}.
     */
    public void removeShouldEnablePredicate(
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertArgumentLegal( shouldEnablePredicates_.remove( predicate ), "predicate" ); //$NON-NLS-1$
    }

    /**
     * Indicates this action should be enabled.
     * 
     * @return {@code true} if this action should be enabled; {@code false} if
     *         this action should be disabled.
     */
    private boolean shouldEnable()
    {
        for( final IPredicate<Action> predicate : shouldEnablePredicates_ )
        {
            if( !predicate.evaluate( this ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Updates the state of this action.
     */
    public void update()
    {
        setEnabled( shouldEnable() );
    }
}
