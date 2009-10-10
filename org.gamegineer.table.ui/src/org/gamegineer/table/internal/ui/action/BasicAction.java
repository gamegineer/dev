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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.event.EventListenerList;
import net.jcip.annotations.NotThreadSafe;

/**
 * An extension to the Swing action framework that allows delegation of action
 * handling to any number of components.
 */
@NotThreadSafe
public class BasicAction
    extends AbstractAction
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 4533526026382678240L;

    /** The collection of action listeners. */
    private final EventListenerList listeners_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BasicAction} class.
     */
    public BasicAction()
    {
        listeners_ = new EventListenerList();
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
        for( final ActionListener listener : listeners_.getListeners( ActionListener.class ) )
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
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addActionListener(
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        listeners_.add( ActionListener.class, listener );
    }

    /**
     * Removes the specified listener from this action.
     * 
     * @param listener
     *        The listener; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeActionListener(
        /* @NonNull */
        final ActionListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        listeners_.remove( ActionListener.class, listener );
    }
}
