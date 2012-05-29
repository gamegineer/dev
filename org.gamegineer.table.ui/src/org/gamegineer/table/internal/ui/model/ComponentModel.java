/*
 * ComponentModel.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Dec 25, 2009 at 9:30:44 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The component model.
 */
@ThreadSafe
public final class ComponentModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component associated with this model. */
    private final IComponent component_;

    /** The collection of component model listeners. */
    private final CopyOnWriteArrayList<IComponentModelListener> listeners_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModel} class.
     * 
     * @param component
     *        The component associated with this model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    public ComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        component_ = component;
        listeners_ = new CopyOnWriteArrayList<IComponentModelListener>();

        component_.addComponentListener( new ComponentListener() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified component model listener to this component model.
     * 
     * @param listener
     *        The component model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered component model
     *         listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addComponentModelListener(
        /* @NonNull */
        final IComponentModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.ComponentModel_addComponentModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a component changed event.
     */
    private void fireComponentChanged()
    {
        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the component associated with this model.
     * 
     * @return The component associated with this model; never {@code null}.
     */
    /* @NonNull */
    public IComponent getComponent()
    {
        return component_;
    }

    /**
     * Removes the specified component model listener from this component model.
     * 
     * @param listener
     *        The component model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered component model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeComponentModelListener(
        /* @NonNull */
        final IComponentModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.ComponentModel_removeComponentModelListener_listener_notRegistered ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component listener for the component model.
     */
    @Immutable
    private final class ComponentListener
        extends org.gamegineer.table.core.ComponentListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentListener} class.
         */
        ComponentListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireComponentChanged();
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireComponentChanged();
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireComponentChanged();
        }
    }
}
