/*
 * LocalNetworkTable.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jun 27, 2011 at 8:14:12 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.concurrent.locks.LockUtils;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.impl.Loggers;

/**
 * Adapts a local table to {@link INetworkTable}.
 */
@NotThreadSafe
final class LocalNetworkTable
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local component listener. */
    private final IComponentListener componentListener_;

    /** The local container listener. */
    private final IContainerListener containerListener_;

    /** The node layer. */
    private final INodeLayer nodeLayer_;

    /** The local table. */
    private final ITable table_;

    /** The table manager for the local table network node. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalNetworkTable} class.
     * 
     * @param nodeLayer
     *        The node layer.
     * @param tableManager
     *        The table manager for the local table network node.
     * @param table
     *        The local table.
     */
    LocalNetworkTable(
        final INodeLayer nodeLayer,
        final ITableManager tableManager,
        final ITable table )
    {
        assert nodeLayer.isNodeLayerThread();

        componentListener_ = new ComponentListenerProxy( new ComponentListener() );
        containerListener_ = new ContainerListenerProxy( new ContainerListener() );
        nodeLayer_ = nodeLayer;
        table_ = table;
        tableManager_ = tableManager;

        initializeListeners();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the listeners for the specified component.
     * 
     * @param component
     *        The component.
     */
    @GuardedBy( "getTableEnvironmentLock()" )
    private void addComponentListeners(
        final IComponent component )
    {
        assert LockUtils.isHeldByCurrentThread( getTableEnvironmentLock() );

        component.addComponentListener( componentListener_ );

        if( component instanceof IContainer )
        {
            final IContainer container = (IContainer)component;

            container.addContainerListener( containerListener_ );

            for( final IComponent childComponent : container.getComponents() )
            {
                addComponentListeners( childComponent );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INetworkTable#dispose()
     */
    @Override
    public void dispose()
    {
        assert nodeLayer_.isNodeLayerThread();

        uninitializeListeners();
    }

    /**
     * Gets the table environment lock.
     * 
     * @return The table environment lock.
     */
    private Lock getTableEnvironmentLock()
    {
        return table_.getTableEnvironment().getLock();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INetworkTable#incrementComponentState(org.gamegineer.table.core.ComponentPath, org.gamegineer.table.internal.net.impl.node.ComponentIncrement)
     */
    @Override
    public void incrementComponentState(
        final ComponentPath componentPath,
        final ComponentIncrement componentIncrement )
    {
        assert nodeLayer_.isNodeLayerThread();

        NetworkTableUtils.incrementComponentState( table_, componentPath, componentIncrement );
    }

    /**
     * Initializes the listeners for the local table.
     */
    private void initializeListeners()
    {
        getTableEnvironmentLock().lock();
        try
        {
            addComponentListeners( table_.getTabletop() );
        }
        finally
        {
            getTableEnvironmentLock().unlock();
        }
    }

    /**
     * Removes the listeners for the specified component.
     * 
     * @param component
     *        The component.
     */
    @GuardedBy( "getTableEnvironmentLock()" )
    private void removeComponentListeners(
        final IComponent component )
    {
        assert LockUtils.isHeldByCurrentThread( getTableEnvironmentLock() );

        component.removeComponentListener( componentListener_ );

        if( component instanceof IContainer )
        {
            final IContainer container = (IContainer)component;

            container.removeContainerListener( containerListener_ );

            for( final IComponent childComponent : container.getComponents() )
            {
                removeComponentListeners( childComponent );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INetworkTable#setTableState(java.lang.Object)
     */
    @Override
    public void setTableState(
        final Object tableMemento )
    {
        assert nodeLayer_.isNodeLayerThread();

        NetworkTableUtils.setTableState( table_, tableMemento );
    }

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param task
     *        The task to execute.
     */
    private void syncExec(
        final Runnable task )
    {
        try
        {
            nodeLayer_.syncExec( task );
        }
        catch( final ExecutionException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.LocalNetworkTable_syncExec_error, e );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.LocalNetworkTable_syncExec_interrupted, e );
        }
    }

    /**
     * Uninitializes the listeners for the local table.
     */
    private void uninitializeListeners()
    {
        getTableEnvironmentLock().lock();
        try
        {
            removeComponentListeners( table_.getTabletop() );
        }
        finally
        {
            getTableEnvironmentLock().unlock();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component listener for the local table adapter.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class ComponentListener
        implements IComponentListener
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
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvent( event ) )
            {
                return;
            }

            final ComponentPath componentPath;
            final ComponentIncrement componentIncrement = new ComponentIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                // TODO: Review the following implementation -- it probably will not apply
                // once the table contains more than simply cards and card piles.

                // In the current implementation, handling this event for non-containers
                // is unnecessary because a non-container can only be moved by moving any
                // of its ancestor containers.  Thus, when the component bounds changed
                // event is fired for the container, it will automatically set the
                // non-container location when the container location is changed.
                //
                // If we send the component bounds changed events for all components over
                // the network, the non-container movement on remote tables will appear
                // "jumpy" because multiple IComponent.setLocation() calls will be made
                // for the same component that may be a few pixels off.

                if( event.getComponent() instanceof IContainer )
                {
                    componentPath = event.getComponentPath();
                    if( componentPath != null )
                    {
                        componentIncrement.setLocation( event.getComponent().getLocation() );
                    }
                }
                else
                {
                    componentPath = null;
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( componentPath != null )
            {
                tableManager_.incrementComponentState( LocalNetworkTable.this, componentPath, componentIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvent( event ) )
            {
                return;
            }

            final ComponentPath componentPath;
            final ComponentIncrement componentIncrement = new ComponentIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                componentPath = event.getComponentPath();
                if( componentPath != null )
                {
                    componentIncrement.setOrientation( event.getComponent().getOrientation() );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( componentPath != null )
            {
                tableManager_.incrementComponentState( LocalNetworkTable.this, componentPath, componentIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvent( event ) )
            {
                return;
            }

            final ComponentPath componentPath;
            final ComponentIncrement componentIncrement = new ComponentIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                componentPath = event.getComponentPath();
                if( componentPath != null )
                {
                    final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = event.getComponent().getSurfaceDesigns();
                    final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds = new IdentityHashMap<>( surfaceDesigns.size() );
                    for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesign> entry : surfaceDesigns.entrySet() )
                    {
                        surfaceDesignIds.put( entry.getKey(), entry.getValue().getId() );
                    }

                    componentIncrement.setSurfaceDesignIds( surfaceDesignIds );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( componentPath != null )
            {
                tableManager_.incrementComponentState( LocalNetworkTable.this, componentPath, componentIncrement );
            }
        }

        /**
         * Indicates the specified component event should be ignored because it
         * was originated by the node layer.
         * 
         * @param event
         *        The component event.
         * 
         * @return {@code true} if the specified component event should be
         *         ignored; otherwise {@code false}.
         */
        private boolean ignoreEvent(
            final ComponentEvent event )
        {
            return nodeLayer_.isNodeLayerThread( event.getThread() );
        }
    }

    /**
     * A proxy for instances of {@link IComponentListener} that ensures all
     * methods are called on the associated node layer thread.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private class ComponentListenerProxy
        implements IComponentListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual component listener. */
        private IComponentListener actualComponentListener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentListenerProxy}
         * class.
         * 
         * @param actualComponentListener
         *        The actual component listener.
         */
        ComponentListenerProxy(
            final IComponentListener actualComponentListener )
        {
            actualComponentListener_ = actualComponentListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualComponentListener_.componentBoundsChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualComponentListener_.componentOrientationChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualComponentListener_.componentSurfaceDesignChanged( event );
                }
            } );
        }
    }

    /**
     * A container listener for the local table adapter.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class ContainerListener
        implements IContainerListener
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
         * @see org.gamegineer.table.core.IContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "boxing" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assert nodeLayer_.isNodeLayerThread();

            final ComponentPath containerPath;
            final ContainerIncrement containerIncrement = new ContainerIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IComponent component = event.getComponent();
                addComponentListeners( component );

                if( ignoreEvent( event ) )
                {
                    return;
                }

                containerPath = event.getContainerPath();
                if( containerPath != null )
                {
                    containerIncrement.setAddedComponentIndex( event.getComponentIndex() );
                    containerIncrement.setAddedComponentMementos( Collections.singletonList( component.createMemento() ) );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( containerPath != null )
            {
                tableManager_.incrementComponentState( LocalNetworkTable.this, containerPath, containerIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "boxing" )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assert nodeLayer_.isNodeLayerThread();

            final ComponentPath containerPath;
            final ContainerIncrement containerIncrement = new ContainerIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IComponent component = event.getComponent();
                removeComponentListeners( component );

                if( ignoreEvent( event ) )
                {
                    return;
                }

                containerPath = event.getContainerPath();
                if( containerPath != null )
                {
                    containerIncrement.setRemovedComponentIndex( event.getComponentIndex() );
                    containerIncrement.setRemovedComponentCount( 1 );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( containerPath != null )
            {
                tableManager_.incrementComponentState( LocalNetworkTable.this, containerPath, containerIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#containerLayoutChanged(org.gamegineer.table.core.ContainerEvent)
         */
        @Override
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvent( event ) )
            {
                return;
            }

            final ComponentPath containerPath;
            final ContainerIncrement containerIncrement = new ContainerIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                containerPath = event.getContainerPath();
                if( containerPath != null )
                {
                    containerIncrement.setLayoutId( event.getContainer().getLayout().getId() );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( containerPath != null )
            {
                tableManager_.incrementComponentState( LocalNetworkTable.this, containerPath, containerIncrement );
            }
        }

        /**
         * Indicates the specified container event should be ignored because it
         * was originated by the node layer.
         * 
         * @param event
         *        The container event.
         * 
         * @return {@code true} if the specified container event should be
         *         ignored; otherwise {@code false}.
         */
        private boolean ignoreEvent(
            final ContainerEvent event )
        {
            return nodeLayer_.isNodeLayerThread( event.getThread() );
        }
    }

    /**
     * A proxy for instances of {@link IContainerListener} that ensures all
     * methods are called on the associated node layer thread.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class ContainerListenerProxy
        implements IContainerListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual container listener. */
        private IContainerListener actualContainerListener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ContainerListenerProxy}
         * class.
         * 
         * @param actualContainerListener
         *        The actual container listener.
         */
        ContainerListenerProxy(
            final IContainerListener actualContainerListener )
        {
            actualContainerListener_ = actualContainerListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualContainerListener_.componentAdded( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualContainerListener_.componentRemoved( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#containerLayoutChanged(org.gamegineer.table.core.ContainerEvent)
         */
        @Override
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualContainerListener_.containerLayoutChanged( event );
                }
            } );
        }
    }
}
