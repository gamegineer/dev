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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.util.TableUtils;

/**
 * The container model.
 */
@ThreadSafe
public final class ContainerModel
    extends ComponentModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model listener for this model. */
    private final IComponentModelListener componentModelListener_;

    /** The collection of component models. */
    @GuardedBy( "getLock()" )
    private final Map<IComponent, ComponentModel> componentModels_;

    /** The container model listener for this model. */
    private final IContainerModelListener containerModelListener_;

    /** The collection of container model listeners. */
    private final CopyOnWriteArrayList<IContainerModelListener> listeners_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerModel} class.
     * 
     * @param container
     *        The container associated with this model; must not be {@code null}
     *        .
     */
    ContainerModel(
        /* @NonNull */
        final IContainer container )
    {
        super( container );

        componentModelListener_ = new ComponentModelListener();
        componentModels_ = new IdentityHashMap<IComponent, ComponentModel>();
        containerModelListener_ = new ContainerModelListener();
        listeners_ = new CopyOnWriteArrayList<IContainerModelListener>();

        final List<IComponent> components = TableUtils.addContainerListenerAndGetComponents( container, new ContainerListener() );
        for( final IComponent component : components )
        {
            synchronized( getLock() )
            {
                createComponentModel( component );
            }
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
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private ComponentModel createComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;
        assert Thread.holdsLock( getLock() );

        final ComponentModel componentModel = ComponentModelFactory.createComponentModel( component );
        componentModels_.put( component, componentModel );

        componentModel.addComponentModelListener( componentModelListener_ );
        if( componentModel instanceof ContainerModel )
        {
            ((ContainerModel)componentModel).addContainerModelListener( containerModelListener_ );
        }

        return componentModel;
    }

    /**
     * Deletes the component model associated with the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    private void deleteComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;
        assert Thread.holdsLock( getLock() );

        final ComponentModel componentModel = componentModels_.remove( component );
        if( componentModel != null )
        {
            componentModel.setFocused( false );

            componentModel.removeComponentModelListener( componentModelListener_ );
            if( componentModel instanceof ContainerModel )
            {
                ((ContainerModel)componentModel).removeContainerModelListener( containerModelListener_ );
            }
        }
    }

    /**
     * Fires a container changed event.
     */
    @SuppressWarnings( "unused" )
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
        synchronized( getLock() )
        {
            componentModel = componentModels_.get( component );
        }

        assertArgumentLegal( componentModel != null, "component", NonNlsMessages.ContainerModel_getComponentModel_component_absent ); //$NON-NLS-1$
        return componentModel;
    }

    /**
     * Gets the component model in this container model at the specified path.
     * 
     * @param paths
     *        The collection of constituent component paths of the overall
     *        component path; must not be {@code null} and must not be empty.
     * 
     * @return The component model in this container model at the specified
     *         path; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If no component model exists at the specified path.
     */
    /* @NonNull */
    ComponentModel getComponentModel(
        /* @NonNull */
        final List<ComponentPath> paths )
    {
        assert paths != null;
        assert !paths.isEmpty();

        final ComponentPath path = paths.get( 0 );
        final ComponentModel componentModel = getComponentModel( getContainer().getComponent( path.getIndex() ) );
        if( paths.size() == 1 )
        {
            return componentModel;
        }

        assertArgumentLegal( componentModel instanceof ContainerModel, "paths", NonNlsMessages.ContainerModel_getComponentModel_path_notExists ); //$NON-NLS-1$
        return ((ContainerModel)componentModel).getComponentModel( paths.subList( 1, paths.size() ) );
    }

    /**
     * Gets the container associated with this model.
     * 
     * @return The container associated with this model; never {@code null}.
     */
    /* @NonNull */
    public IContainer getContainer()
    {
        return (IContainer)getComponent();
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


    // ======================================================================
    // Nested Types
    // ======================================================================

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
        public void componentChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireComponentChanged();
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelFocusChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentModelFocusChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireComponentModelFocusChanged();
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

            synchronized( getLock() )
            {
                createComponentModel( event.getComponent() );
            }

            fireComponentChanged();
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

            synchronized( getLock() )
            {
                deleteComponentModel( event.getComponent() );
            }

            fireComponentChanged();
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#containerLayoutChanged(org.gamegineer.table.core.ContainerEvent)
         */
        @Override
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireComponentChanged();
        }
    }

    /**
     * A container model listener for the container model.
     */
    @Immutable
    private final class ContainerModelListener
        extends org.gamegineer.table.internal.ui.model.ContainerModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ContainerModelListener}
         * class.
         */
        ContainerModelListener()
        {
        }
    }
}
