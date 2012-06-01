/*
 * ContainerModel.java
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
 * Created on Jan 26, 2010 at 10:41:29 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The container model.
 */
@ThreadSafe
public final class ContainerModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model listener for this model. */
    private final IComponentModelListener componentModelListener_;

    /** The collection of component models. */
    @GuardedBy( "lock_" )
    private final Map<IComponent, ComponentModel> componentModels_;

    /** The container associated with this model. */
    private final IContainer container_;

    /** Indicates the associated container has the focus. */
    @GuardedBy( "lock_" )
    private boolean isFocused_;

    /** The collection of container model listeners. */
    private final CopyOnWriteArrayList<IContainerModelListener> listeners_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerModel} class.
     * 
     * @param container
     *        The container associated with this model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    public ContainerModel(
        /* @NonNull */
        final IContainer container )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$

        componentModelListener_ = new ComponentModelListener();
        componentModels_ = new IdentityHashMap<IComponent, ComponentModel>();
        container_ = container;
        isFocused_ = false;
        listeners_ = new CopyOnWriteArrayList<IContainerModelListener>();
        lock_ = new Object();

        container_.addComponentListener( new ComponentListener() );
        container_.addContainerListener( new ContainerListener() );

        for( final IComponent component : container.getComponents() )
        {
            createComponentModel( component );
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified container model listener to this container model.
     * 
     * @param listener
     *        The container model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered container model
     *         listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addContainerModelListener(
        /* @NonNull */
        final IContainerModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.ContainerModel_addContainerModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Creates a component model for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return The component model; never {@code null}.
     */
    /* @NonNull */
    private ComponentModel createComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        final ComponentModel componentModel = new ComponentModel( component );
        componentModels_.put( component, componentModel );
        componentModel.addComponentModelListener( componentModelListener_ );
        return componentModel;
    }

    /**
     * Fires a container changed event.
     */
    private void fireContainerChanged()
    {
        final ContainerModelEvent event = new ContainerModelEvent( this );
        for( final IContainerModelListener listener : listeners_ )
        {
            try
            {
                listener.containerChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ContainerModel_containerChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a container model focus changed event.
     */
    private void fireContainerModelFocusChanged()
    {
        final ContainerModelEvent event = new ContainerModelEvent( this );
        for( final IContainerModelListener listener : listeners_ )
        {
            try
            {
                listener.containerModelFocusChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ContainerModel_containerModelFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the component model associated with the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return The component model associated with the specified component;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} does not exist in the container associated
     *         with this model.
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    /* @NonNull */
    public ComponentModel getComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final ComponentModel componentModel;
        synchronized( lock_ )
        {
            componentModel = componentModels_.get( component );
        }

        assertArgumentLegal( componentModel != null, "component", NonNlsMessages.ContainerModel_getComponentModel_component_absent ); //$NON-NLS-1$
        return componentModel;
    }

    /**
     * Gets the container associated with this model.
     * 
     * @return The container associated with this model; never {@code null}.
     */
    /* @NonNull */
    public IContainer getContainer()
    {
        return container_;
    }

    /**
     * Indicates the associated container has the focus.
     * 
     * @return {@code true} if the associated container has the focus; otherwise
     *         {@code false}.
     */
    public boolean isFocused()
    {
        synchronized( lock_ )
        {
            return isFocused_;
        }
    }

    /**
     * Removes the specified container model listener from this container model.
     * 
     * @param listener
     *        The container model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered container model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeContainerModelListener(
        /* @NonNull */
        final IContainerModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.ContainerModel_removeContainerModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Sets or removes the focus from the associated container.
     * 
     * @param isFocused
     *        {@code true} if the associated container has the focus; otherwise
     *        {@code false}.
     */
    public void setFocused(
        final boolean isFocused )
    {
        synchronized( lock_ )
        {
            isFocused_ = isFocused;
        }

        fireContainerModelFocusChanged();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component listener for the container model.
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

            fireContainerChanged();
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

            fireContainerChanged();
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

            fireContainerChanged();
        }
    }

    /**
     * A component model listener for the container model.
     */
    @Immutable
    private final class ComponentModelListener
        extends org.gamegineer.table.internal.ui.model.ComponentModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentModelListener}
         * class.
         */
        ComponentModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireContainerChanged();
        }
    }

    /**
     * A container listener for the container model.
     */
    @Immutable
    private final class ContainerListener
        extends org.gamegineer.table.core.ContainerListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ContainerListener} class.
         */
        ContainerListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                createComponentModel( event.getComponent() );
            }

            fireContainerChanged();
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final ComponentModel componentModel = componentModels_.remove( event.getComponent() );
                if( componentModel != null )
                {
                    componentModel.removeComponentModelListener( componentModelListener_ );
                }
            }

            fireContainerChanged();
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#containerLayoutChanged(org.gamegineer.table.core.ContainerEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireContainerChanged();
        }
    }
}
