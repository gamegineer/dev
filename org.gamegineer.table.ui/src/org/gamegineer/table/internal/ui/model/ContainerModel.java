/*
 * ContainerModel.java
 * Copyright 2008-2013 Gamegineer.org
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
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
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
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.util.TableUtils;

/**
 * The container model.
 */
@ThreadSafe
public final class ContainerModel
    extends ComponentModel
    implements IComponentModelParent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model listener for this model. */
    private final IComponentModelListener componentModelListener_;

    /**
     * The collection of component models ordered from the component at the
     * bottom of the container to the component at the top of the container.
     */
    @GuardedBy( "getLock()" )
    private final List<ComponentModel> componentModels_;

    /** The container listener for this model. */
    private final IContainerListener containerListener_;

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
     * @param tableEnvironmentModel
     *        The table environment model associated with this model; must not
     *        be {@code null}.
     * @param container
     *        The container associated with this model; must not be {@code null}
     *        .
     */
    ContainerModel(
        /* @NonNull */
        final TableEnvironmentModel tableEnvironmentModel,
        /* @NonNull */
        final IContainer container )
    {
        super( tableEnvironmentModel, container );

        componentModelListener_ = new ComponentModelListener();
        componentModels_ = new ArrayList<ComponentModel>();
        containerListener_ = new ContainerListener();
        containerModelListener_ = new ContainerModelListener();
        listeners_ = new CopyOnWriteArrayList<IContainerModelListener>();
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
     * @param componentIndex
     *        The component index; must not be negative.
     * 
     * @return The component model that was created; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private ComponentModel createComponentModel(
        /* @NonNull */
        final IComponent component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert getLock().isHeldByCurrentThread();

        final ComponentModel componentModel = getTableEnvironmentModel().createComponentModel( component );
        componentModel.initialize( this );
        componentModels_.add( componentIndex, componentModel );

        componentModel.addComponentModelListener( componentModelListener_ );
        if( componentModel instanceof ContainerModel )
        {
            ((ContainerModel)componentModel).addContainerModelListener( containerModelListener_ );
        }

        return componentModel;
    }

    /**
     * Deletes the component model associated with the component at the
     * specified index.
     * 
     * @param componentIndex
     *        The component index; must not be negative.
     */
    @GuardedBy( "getLock()" )
    private void deleteComponentModel(
        final int componentIndex )
    {
        assert componentIndex >= 0;
        assert getLock().isHeldByCurrentThread();

        final ComponentModel componentModel = componentModels_.remove( componentIndex );
        componentModel.uninitialize();

        componentModel.removeComponentModelListener( componentModelListener_ );
        if( componentModel instanceof ContainerModel )
        {
            ((ContainerModel)componentModel).removeContainerModelListener( containerModelListener_ );
        }
    }

    /**
     * Fires a component model added event.
     * 
     * @param componentModel
     *        The added component model; must not be {@code null}.
     * @param componentModelIndex
     *        The index of the added component; must not be negative.
     */
    private void fireComponentModelAdded(
        /* @NonNull */
        final ComponentModel componentModel,
        final int componentModelIndex )
    {
        assert componentModel != null;
        assert componentModelIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final ContainerModelContentChangedEvent event = new ContainerModelContentChangedEvent( this, componentModel, componentModelIndex );
        for( final IContainerModelListener listener : listeners_ )
        {
            try
            {
                listener.componentModelAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ContainerModel_componentModelAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component model removed event.
     * 
     * @param componentModel
     *        The removed component model; must not be {@code null}.
     * @param componentModelIndex
     *        The index of the removed component; must not be negative.
     */
    private void fireComponentModelRemoved(
        /* @NonNull */
        final ComponentModel componentModel,
        final int componentModelIndex )
    {
        assert componentModel != null;
        assert componentModelIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final ContainerModelContentChangedEvent event = new ContainerModelContentChangedEvent( this, componentModel, componentModelIndex );
        for( final IContainerModelListener listener : listeners_ )
        {
            try
            {
                listener.componentModelRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ContainerModel_componentModelRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a container layout changed event.
     */
    private void fireContainerLayoutChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ContainerModelEvent event = new ContainerModelEvent( this );
        for( final IContainerModelListener listener : listeners_ )
        {
            try
            {
                listener.containerLayoutChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ContainerModel_containerLayoutChanged_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IComponentModelParent#getChildPath(org.gamegineer.table.internal.ui.model.ComponentModel)
     */
    @Override
    public ComponentPath getChildPath(
        final ComponentModel componentModel )
    {
        assert componentModel != null;
        assert getLock().isHeldByCurrentThread();

        final ComponentPath path = getPath();
        if( path == null )
        {
            return null;
        }

        final int index = componentModels_.indexOf( componentModel );
        assert index != -1;

        return new ComponentPath( path, index );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ComponentModel#getComponent()
     */
    @Override
    public IContainer getComponent()
    {
        return (IContainer)super.getComponent();
    }

    /**
     * Gets the component model in this container model at the specified path.
     * 
     * @param paths
     *        The collection of constituent component paths of the overall
     *        component path; must not be {@code null} and must not be empty.
     * 
     * @return The component model in this container model at the specified path
     *         or {@code null} if no component model exists at the specified
     *         path.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    ComponentModel getComponentModel(
        /* @NonNull */
        final List<ComponentPath> paths )
    {
        assert paths != null;
        assert !paths.isEmpty();
        assert getLock().isHeldByCurrentThread();

        final ComponentPath path = paths.get( 0 );
        final ComponentModel componentModel = getComponentModel( path.getIndex() );
        if( componentModel != null )
        {
            if( paths.size() == 1 )
            {
                return componentModel;
            }
            else if( componentModel instanceof ContainerModel )
            {
                return ((ContainerModel)componentModel).getComponentModel( paths.subList( 1, paths.size() ) );
            }
        }

        return null;
    }

    /**
     * Gets the component model at the specified index.
     * 
     * @param componentModelIndex
     *        The component model index.
     * 
     * @return The component model at the specified index or {@code null} if the
     *         specified index is not a legal component model index.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    private ComponentModel getComponentModel(
        final int componentModelIndex )
    {
        assert componentModelIndex >= 0;
        assert getLock().isHeldByCurrentThread();

        return (componentModelIndex < componentModels_.size()) ? componentModels_.get( componentModelIndex ) : null;
    }

    /**
     * Gets the collection of component models in this container model.
     * 
     * @return The collection of component models in this container model; never
     *         {@code null}. The component models are returned in order from the
     *         component at the bottom of the container to the component at the
     *         top of the container.
     */
    /* @NonNull */
    public List<ComponentModel> getComponentModels()
    {
        getLock().lock();
        try
        {
            return new ArrayList<ComponentModel>( componentModels_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IComponentModelParent#getTableModel()
     */
    @Override
    public TableModel getTableModel()
    {
        final IComponentModelParent parent = getParent();
        return (parent != null) ? parent.getTableModel() : null;
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ComponentModel#hitTest(java.awt.Point, java.util.List)
     */
    @Override
    boolean hitTest(
        final Point location,
        final List<ComponentModel> componentModels )
    {
        assert location != null;
        assert componentModels != null;
        assert getLock().isHeldByCurrentThread();

        if( super.hitTest( location, componentModels ) )
        {
            for( final ComponentModel componentModel : componentModels_ )
            {
                componentModel.hitTest( location, componentModels );
            }

            return true;
        }

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ComponentModel#initialize(org.gamegineer.table.internal.ui.model.IComponentModelParent)
     */
    @Override
    void initialize(
        final IComponentModelParent parent )
    {
        getLock().lock();
        try
        {
            super.initialize( parent );

            final List<IComponent> components = TableUtils.addContainerListenerAndGetComponents( getComponent(), containerListener_ );
            int componentIndex = 0;
            for( final IComponent component : components )
            {
                createComponentModel( component, componentIndex++ );
            }
        }
        finally
        {
            getLock().unlock();
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

    /*
     * @see org.gamegineer.table.internal.ui.model.ComponentModel#uninitialize()
     */
    @Override
    void uninitialize()
    {
        getLock().lock();
        try
        {
            getComponent().removeContainerListener( containerListener_ );

            super.uninitialize();
        }
        finally
        {
            getLock().unlock();
        }
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

            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelFocusChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentModelFocusChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentModelFocusChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelHoverChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentModelHoverChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentModelHoverChanged();
                }
            } );
        }
    }

    /**
     * A container listener for the container model.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
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
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            final ComponentModel componentModel;

            getLock().lock();
            try
            {
                componentModel = createComponentModel( event.getComponent(), event.getComponentIndex() );
            }
            finally
            {
                getLock().unlock();
            }

            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentModelAdded( componentModel, event.getComponentIndex() );
                    fireComponentChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            final ComponentModel componentModel;

            getLock().lock();
            try
            {
                componentModel = getComponentModel( event.getComponentIndex() );
                if( componentModel != null )
                {
                    final TableModel tableModel = getTableModel();
                    assert tableModel != null;
                    final ComponentModel hoveredComponentModel = tableModel.getHoveredComponentModel();
                    if( (hoveredComponentModel != null) && hoveredComponentModel.isSameOrDescendantOf( componentModel ) )
                    {
                        tableModel.setHover( null );
                    }
                    final ComponentModel focusedComponentModel = tableModel.getFocusedComponentModel();
                    if( (focusedComponentModel != null) && focusedComponentModel.isSameOrDescendantOf( componentModel ) )
                    {
                        tableModel.setFocus( null );
                    }

                    deleteComponentModel( event.getComponentIndex() );
                }
            }
            finally
            {
                getLock().unlock();
            }

            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    if( componentModel != null )
                    {
                        fireComponentModelRemoved( componentModel, event.getComponentIndex() );
                    }

                    fireComponentChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#containerLayoutChanged(org.gamegineer.table.core.ContainerEvent)
         */
        @Override
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireContainerLayoutChanged();
                    fireComponentChanged();
                }
            } );
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
