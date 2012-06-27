/*
 * LocalNetworkTable.java
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
 * Created on Jun 27, 2011 at 8:14:12 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.core.TableEvent;
import org.gamegineer.table.internal.net.Loggers;

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

    /** Indicates events fired by the local table should be ignored. */
    private boolean ignoreEvents_;

    /** The node layer. */
    private final INodeLayer nodeLayer_;

    /** The local table. */
    private final ITable table_;

    /** The local table listener. */
    private final ITableListener tableListener_;

    /** The table manager for the local table network node. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalNetworkTable} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param tableManager
     *        The table manager for the local table network node; must not be
     *        {@code null}.
     * @param table
     *        The local table; must not be {@code null}.
     */
    LocalNetworkTable(
        /* @NonNull */
        final INodeLayer nodeLayer,
        /* @NonNull */
        final ITableManager tableManager,
        /* @NonNull */
        final ITable table )
    {
        assert nodeLayer != null;
        assert nodeLayer.isNodeLayerThread();
        assert tableManager != null;
        assert table != null;

        componentListener_ = new ComponentListenerProxy( new ComponentListener() );
        containerListener_ = new ContainerListenerProxy( new ContainerListener() );
        ignoreEvents_ = false;
        nodeLayer_ = nodeLayer;
        table_ = table;
        tableListener_ = new TableListenerProxy( new TableListener() );
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
     *        The component; must not be {@code null}.
     */
    private void addComponentListeners(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        component.addComponentListener( componentListener_ );

        if( component instanceof IContainer )
        {
            ((IContainer)component).addContainerListener( containerListener_ );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#dispose()
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
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    private Lock getTableEnvironmentLock()
    {
        return table_.getTableEnvironment().getLock();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementComponentState(org.gamegineer.table.core.ComponentPath, org.gamegineer.table.internal.net.node.ComponentIncrement)
     */
    @Override
    public void incrementComponentState(
        final ComponentPath componentPath,
        final ComponentIncrement componentIncrement )
    {
        assertArgumentNotNull( componentPath, "componentPath" ); //$NON-NLS-1$
        assertArgumentNotNull( componentIncrement, "componentIncrement" ); //$NON-NLS-1$
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.incrementComponentState( table_, componentPath, componentIncrement );
        ignoreEvents_ = oldIgnoreEvents;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementTableState(org.gamegineer.table.internal.net.node.TableIncrement)
     */
    @Override
    public void incrementTableState(
        final TableIncrement tableIncrement )
    {
        assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.incrementTableState( table_, tableIncrement );
        ignoreEvents_ = oldIgnoreEvents;
    }

    /**
     * Initializes the listeners for the local table.
     */
    private void initializeListeners()
    {
        getTableEnvironmentLock().lock();
        try
        {
            table_.addTableListener( tableListener_ );

            for( final ICardPile cardPile : table_.getCardPiles() )
            {
                addComponentListeners( cardPile );

                for( final IComponent component : cardPile.getComponents() )
                {
                    addComponentListeners( component );
                }
            }
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
     *        The component; must not be {@code null}.
     */
    private void removeComponentListeners(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        component.removeComponentListener( componentListener_ );

        if( component instanceof IContainer )
        {
            ((IContainer)component).removeContainerListener( containerListener_ );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setTableState(java.lang.Object)
     */
    @Override
    public void setTableState(
        final Object tableMemento )
    {
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.setTableState( table_, tableMemento );
        ignoreEvents_ = oldIgnoreEvents;
    }

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     */
    private void syncExec(
        /* @NonNull */
        final Runnable task )
    {
        assert task != null;

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
            table_.removeTableListener( tableListener_ );

            for( final ICardPile cardPile : table_.getCardPiles() )
            {
                removeComponentListeners( cardPile );

                for( final IComponent component : cardPile.getComponents() )
                {
                    removeComponentListeners( component );
                }
            }
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
        @SuppressWarnings( "synthetic-access" )
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            final ComponentPath componentPath;
            final ComponentIncrement componentIncrement = new ComponentIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                // In the current implementation, handling this event for non-root components
                // is unnecessary because a non-root component can only be moved by moving a
                // root component that contains the non-root component. Thus, when the
                // component bounds changed event is fired for the root component, it will
                // automatically set the non-root component location when the root component
                // location is changed.
                //
                // If we send the component bounds changed events for all components over the
                // network, the non-root component movement on remote tables will appear
                // "jumpy" because multiple IComponent.setLocation() calls will be made that
                // may be a few pixels off.

                final IComponent component = event.getComponent();
                if( component.getContainer() == null )
                {
                    componentPath = component.getPath();
                    if( componentPath != null )
                    {
                        componentIncrement.setLocation( component.getLocation() );
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
        @SuppressWarnings( "synthetic-access" )
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            final ComponentPath componentPath;
            final ComponentIncrement componentIncrement = new ComponentIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IComponent component = event.getComponent();
                componentPath = component.getPath();
                if( componentPath != null )
                {
                    componentIncrement.setOrientation( component.getOrientation() );
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
        @SuppressWarnings( "synthetic-access" )
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            final ComponentPath componentPath;
            final ComponentIncrement componentIncrement = new ComponentIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IComponent component = event.getComponent();
                componentPath = component.getPath();
                if( componentPath != null )
                {
                    final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new HashMap<ComponentOrientation, ComponentSurfaceDesign>();
                    for( final ComponentOrientation orientation : component.getSupportedOrientations() )
                    {
                        surfaceDesigns.put( orientation, component.getSurfaceDesign( orientation ) );
                    }
                    componentIncrement.setSurfaceDesigns( surfaceDesigns );
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
    }

    /**
     * A proxy for instances of {@link IComponentListener} that ensures all
     * methods are called on the associated node layer thread.
     */
    @Immutable
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
         *        The actual component listener; must not be {@code null}.
         */
        ComponentListenerProxy(
            /* @NonNull */
            final IComponentListener actualComponentListener )
        {
            assert actualComponentListener != null;

            actualComponentListener_ = actualComponentListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
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
        @SuppressWarnings( "synthetic-access" )
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
        @SuppressWarnings( "synthetic-access" )
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
        @SuppressWarnings( "synthetic-access" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            final IComponent component = event.getComponent();
            addComponentListeners( component );

            if( ignoreEvents_ )
            {
                return;
            }

            final ComponentPath containerPath;
            final ContainerIncrement containerIncrement = new ContainerIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IContainer container = event.getContainer();
                containerPath = container.getPath();
                if( containerPath != null )
                {
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
        @SuppressWarnings( {
            "boxing", "synthetic-access"
        } )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            final IComponent component = event.getComponent();
            removeComponentListeners( component );

            if( ignoreEvents_ )
            {
                return;
            }

            final ComponentPath containerPath;
            final ContainerIncrement containerIncrement = new ContainerIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IContainer container = event.getContainer();
                containerPath = container.getPath();
                if( containerPath != null )
                {
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
        @SuppressWarnings( "synthetic-access" )
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            final ComponentPath containerPath;
            final ContainerIncrement containerIncrement = new ContainerIncrement();
            getTableEnvironmentLock().lock();
            try
            {
                final IContainer container = event.getContainer();
                containerPath = container.getPath();
                if( containerPath != null )
                {
                    containerIncrement.setLayout( container.getLayout() );
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
    }

    /**
     * A proxy for instances of {@link IContainerListener} that ensures all
     * methods are called on the associated node layer thread.
     */
    @Immutable
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
         *        The actual container listener; must not be {@code null}.
         */
        ContainerListenerProxy(
            /* @NonNull */
            final IContainerListener actualContainerListener )
        {
            assert actualContainerListener != null;

            actualContainerListener_ = actualContainerListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
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
        @SuppressWarnings( "synthetic-access" )
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
        @SuppressWarnings( "synthetic-access" )
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

    /**
     * A table listener for the local table adapter.
     */
    @Immutable
    private final class TableListener
        implements ITableListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableListener} class.
         */
        TableListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileAdded(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            getTableEnvironmentLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                addComponentListeners( cardPile );

                for( final IComponent component : cardPile.getComponents() )
                {
                    addComponentListeners( component );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( ignoreEvents_ )
            {
                return;
            }

            final TableIncrement tableIncrement = new TableIncrement();
            tableIncrement.setAddedCardPileMementos( Collections.singletonList( event.getCardPile().createMemento() ) );

            tableManager_.incrementTableState( LocalNetworkTable.this, tableIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( {
            "boxing", "synthetic-access"
        } )
        public void cardPileRemoved(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            getTableEnvironmentLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                for( final IComponent component : cardPile.getComponents() )
                {
                    removeComponentListeners( component );
                }

                removeComponentListeners( cardPile );
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }

            if( ignoreEvents_ )
            {
                return;
            }

            final TableIncrement tableIncrement = new TableIncrement();
            tableIncrement.setRemovedCardPileIndexes( Collections.singletonList( event.getCardPileIndex() ) );

            tableManager_.incrementTableState( LocalNetworkTable.this, tableIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#rootComponentChanged(org.gamegineer.table.core.TableEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void rootComponentChanged(
            final TableEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            // TODO: add support for root component
        }
    }

    /**
     * A proxy for instances of {@link ITableListener} that ensures all methods
     * are called on the associated node layer thread.
     */
    @Immutable
    private final class TableListenerProxy
        implements ITableListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual table listener. */
        private final ITableListener actualTableListener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableListenerProxy} class.
         * 
         * @param actualTableListener
         *        The actual table listener; must not be {@code null}.
         */
        TableListenerProxy(
            /* @NonNull */
            final ITableListener actualTableListener )
        {
            assert actualTableListener != null;

            actualTableListener_ = actualTableListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileAdded(
            final TableContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualTableListener_.cardPileAdded( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileRemoved(
            final TableContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualTableListener_.cardPileRemoved( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#rootComponentChanged(org.gamegineer.table.core.TableEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void rootComponentChanged(
            final TableEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualTableListener_.rootComponentChanged( event );
                }
            } );
        }
    }
}
