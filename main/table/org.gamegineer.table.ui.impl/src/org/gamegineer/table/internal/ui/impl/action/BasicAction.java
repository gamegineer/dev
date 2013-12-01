/*
 * BasicAction.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.action;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
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

    /** The key used for storing the action identifier. */
    public static final String ID_KEY = "IdKey"; //$NON-NLS-1$

    /** Serializable class version number. */
    private static final long serialVersionUID = 4533526026382678240L;

    /** The collection of action listeners. */
    private final CopyOnWriteArrayList<ActionListener> actionListeners_;

    /** The collection of should enable predicates. */
    private final CopyOnWriteArrayList<IPredicate<Action>> shouldEnablePredicates_;

    /** The collection of should select predicates. */
    private final CopyOnWriteArrayList<IPredicate<Action>> shouldSelectPredicates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BasicAction} class.
     * 
     * @param id
     *        The action identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public BasicAction(
        /* @NonNull */
        final Object id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        actionListeners_ = new CopyOnWriteArrayList<>();
        shouldEnablePredicates_ = new CopyOnWriteArrayList<>();
        shouldSelectPredicates_ = new CopyOnWriteArrayList<>();

        putValue( ID_KEY, id );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(
        final ActionEvent event )
    {
        for( final ActionListener listener : actionListeners_ )
        {
            listener.actionPerformed( event );
        }
    }

    /**
     * Adds the specified listener to this action.
     * 
     * @param listener
     *        The listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered action listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public final void addActionListener(
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( actionListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.BasicAction_addActionListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Adds the specified should enable predicate to this action.
     * 
     * @param predicate
     *        The predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code predicate} is already a registered should enable
     *         predicate.
     * @throws java.lang.NullPointerException
     *         If {@code predicate} is {@code null}.
     */
    public final void addShouldEnablePredicate(
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertArgumentLegal( shouldEnablePredicates_.addIfAbsent( predicate ), "predicate", NonNlsMessages.BasicAction_addShouldEnablePredicate_predicate_registered ); //$NON-NLS-1$
    }

    /**
     * Adds the specified should select predicate to this action.
     * 
     * @param predicate
     *        The predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code predicate} is already a registered should select
     *         predicate.
     * @throws java.lang.NullPointerException
     *         If {@code predicate} is {@code null}.
     */
    public final void addShouldSelectPredicate(
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertArgumentLegal( shouldSelectPredicates_.addIfAbsent( predicate ), "predicate", NonNlsMessages.BasicAction_addShouldSelectPredicate_predicate_registered ); //$NON-NLS-1$
    }

    /**
     * Gets the action accelerator.
     * 
     * @return The action accelerator or {@code null} if no accelerator is
     *         defined.
     */
    /* @Nullable */
    public final KeyStroke getAccelerator()
    {
        return (KeyStroke)getValue( ACCELERATOR_KEY );
    }

    /**
     * Gets the action identifier.
     * 
     * @return The action identifier; never {@code null}.
     */
    /* @NonNull */
    public final Object getId()
    {
        return getValue( ID_KEY );
    }

    /**
     * Removes the specified listener from this action.
     * 
     * @param listener
     *        The listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered action listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public final void removeActionListener(
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( actionListeners_.remove( listener ), "listener", NonNlsMessages.BasicAction_removeActionListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Removes the specified should enable predicate from this action.
     * 
     * @param predicate
     *        The predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code predicate} is not a registered should enable predicate.
     * @throws java.lang.NullPointerException
     *         If {@code predicate} is {@code null}.
     */
    public final void removeShouldEnablePredicate(
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertArgumentLegal( shouldEnablePredicates_.remove( predicate ), "predicate", NonNlsMessages.BasicAction_removeShouldEnablePredicate_predicate_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Removes the specified should select predicate from this action.
     * 
     * @param predicate
     *        The predicate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code predicate} is not a registered should select predicate.
     * @throws java.lang.NullPointerException
     *         If {@code predicate} is {@code null}.
     */
    public final void removeShouldSelectPredicate(
        /* @NonNull */
        final IPredicate<Action> predicate )
    {
        assertArgumentNotNull( predicate, "predicate" ); //$NON-NLS-1$
        assertArgumentLegal( shouldSelectPredicates_.remove( predicate ), "predicate", NonNlsMessages.BasicAction_removeShouldSelectPredicate_predicate_notRegistered ); //$NON-NLS-1$
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
     * Indicates this action should be selected.
     * 
     * @return {@code true} if this action should be selected; {@code false} if
     *         this action should not be selected.
     */
    private boolean shouldSelect()
    {
        for( final IPredicate<Action> predicate : shouldSelectPredicates_ )
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
    @SuppressWarnings( "boxing" )
    public final void update()
    {
        setEnabled( shouldEnable() );
        putValue( SELECTED_KEY, shouldSelect() );
    }
}
