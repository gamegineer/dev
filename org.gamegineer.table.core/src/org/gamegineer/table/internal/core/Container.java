/*
 * Container.java
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
 * Created on Jul 4, 2012 at 8:47:42 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerListener;

/**
 * Implementation of {@link org.gamegineer.table.core.IContainer}.
 */
@ThreadSafe
abstract class Container
    extends Component
    implements IComponentParent, IContainer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the memento attribute that stores the collection of
     * components in the container.
     */
    private static final String COMPONENTS_MEMENTO_ATTRIBUTE_NAME = "container.components"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the container layout. */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "container.layout"; //$NON-NLS-1$

    /**
     * The collection of components in this container ordered from bottom to
     * top.
     */
    @GuardedBy( "getLock()" )
    private final List<Component> components_;

    /** The collection of container listeners. */
    private final CopyOnWriteArrayList<IContainerListener> containerListeners_;

    /** The container layout. */
    @GuardedBy( "getLock()" )
    private IContainerLayout layout_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Container} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the container; must not be
     *        {@code null}.
     */
    Container(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );

        components_ = new ArrayList<Component>();
        containerListeners_ = new CopyOnWriteArrayList<IContainerListener>();
        layout_ = getDefaultLayout();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainer#addComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public final void addComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        addComponents( Collections.singletonList( component ) );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addComponents(java.util.List)
     */
    @Override
    public final void addComponents(
        final List<IComponent> components )
    {
        assertArgumentNotNull( components, "components" ); //$NON-NLS-1$

        final List<Component> addedComponents = new ArrayList<Component>();
        final int firstComponentIndex;
        final boolean containerBoundsChanged;

        getLock().lock();
        try
        {
            final Rectangle oldBounds = getBounds();
            firstComponentIndex = components_.size();

            for( final IComponent component : components )
            {
                final Component typedComponent = (Component)component;
                if( typedComponent == null )
                {
                    throw new IllegalArgumentException( NonNlsMessages.Container_addComponents_components_containsNullElement );
                }
                assertArgumentLegal( typedComponent.getContainer() == null, "components", NonNlsMessages.Container_addComponents_components_containsOwnedComponent ); //$NON-NLS-1$
                assertArgumentLegal( typedComponent.getTableEnvironment() == getTableEnvironment(), "components", NonNlsMessages.Container_addComponents_components_containsComponentCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

                typedComponent.setParent( this );
                components_.add( typedComponent );
                addedComponents.add( typedComponent );
            }

            layout_.layout( this );

            final Rectangle newBounds = getBounds();
            containerBoundsChanged = !newBounds.equals( oldBounds );
        }
        finally
        {
            getLock().unlock();
        }

        if( !addedComponents.isEmpty() || containerBoundsChanged )
        {
            addEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    int componentIndex = firstComponentIndex;
                    for( final IComponent component : addedComponents )
                    {
                        fireComponentAdded( component, componentIndex++ );
                    }

                    if( containerBoundsChanged )
                    {
                        fireComponentBoundsChanged();
                    }
                }
            } );
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addContainerListener(org.gamegineer.table.core.IContainerListener)
     */
    @Override
    public final void addContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( containerListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Container_addContainerListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a component added event.
     * 
     * @param component
     *        The added component; must not be {@code null}.
     * @param componentIndex
     *        The index of the added component; must not be negative.
     */
    private void fireComponentAdded(
        /* @NonNull */
        final IComponent component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final ContainerContentChangedEvent event = new ContainerContentChangedEvent( this, component, componentIndex );
        for( final IContainerListener listener : containerListeners_ )
        {
            try
            {
                listener.componentAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Container_componentAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component removed event.
     * 
     * @param component
     *        The removed component; must not be {@code null}.
     * @param componentIndex
     *        The index of the removed component; must not be negative.
     */
    private void fireComponentRemoved(
        /* @NonNull */
        final IComponent component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final ContainerContentChangedEvent event = new ContainerContentChangedEvent( this, component, componentIndex );
        for( final IContainerListener listener : containerListeners_ )
        {
            try
            {
                listener.componentRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Container_componentRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a container layout changed event.
     */
    private void fireContainerLayoutChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ContainerEvent event = new ContainerEvent( this );
        for( final IContainerListener listener : containerListeners_ )
        {
            try
            {
                listener.containerLayoutChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Container_containerLayoutChanged_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getBounds()
     */
    @Override
    public final Rectangle getBounds()
    {
        getLock().lock();
        try
        {
            Rectangle bounds = super.getBounds();

            // TODO: allow layout to optimize calculating child component bounding region
            for( final IComponent component : getComponents() )
            {
                bounds = bounds.union( component.getBounds() );
            }

            return bounds;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.IComponentParent#getChildPath(org.gamegineer.table.internal.core.Component)
     */
    @GuardedBy( "getLock()" )
    @Override
    public final ComponentPath getChildPath(
        final Component component )
    {
        assert component != null;
        assert getLock().isHeldByCurrentThread();

        final ComponentPath path = getPath();
        if( path == null )
        {
            return null;
        }

        final int index = components_.indexOf( component );
        assert index != -1;

        return new ComponentPath( path, index );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponent(int)
     */
    @Override
    public final Component getComponent(
        final int index )
    {
        getLock().lock();
        try
        {
            assertArgumentLegal( (index >= 0) && (index < components_.size()), "index", NonNlsMessages.Container_getComponentFromIndex_index_outOfRange ); //$NON-NLS-1$
            return components_.get( index );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the component within this container's bounds (including the
     * container itself) at the specified location.
     * 
     * <p>
     * If two or more components occupy the specified location, the top-most
     * component will be returned.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The component within this container's bounds at the specified
     *         location or {@code null} if no component is at that location.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    final IComponent getComponent(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert getLock().isHeldByCurrentThread();

        if( getBounds().contains( location ) )
        {
            if( getComponentCount() == 0 )
            {
                return this;
            }

            final int index = getComponentIndex( location );
            if( index != -1 )
            {
                final Component component = getComponent( index );
                return (component instanceof Container) ? ((Container)component).getComponent( location ) : component;
            }
        }

        return null;
    }

    /**
     * Gets the component in this container at the specified path.
     * 
     * @param paths
     *        The collection of constituent component paths of the overall
     *        component path; must not be {@code null} and must not be empty.
     * 
     * @return The component in this container at the specified path; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If no component exists at the specified path.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    final Component getComponent(
        /* @NonNull */
        final List<ComponentPath> paths )
    {
        assert paths != null;
        assert !paths.isEmpty();
        assert getLock().isHeldByCurrentThread();

        final ComponentPath path = paths.get( 0 );
        final Component component = getComponent( path.getIndex() );
        if( paths.size() == 1 )
        {
            return component;
        }

        assertArgumentLegal( component instanceof Container, "paths", NonNlsMessages.Container_getComponentFromPath_path_notExists ); //$NON-NLS-1$
        return ((Container)component).getComponent( paths.subList( 1, paths.size() ) );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponentCount()
     */
    @Override
    public final int getComponentCount()
    {
        getLock().lock();
        try
        {
            return components_.size();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the index of the component in this container at the specified
     * location.
     * 
     * <p>
     * This method follows the same rules defined by
     * {@link #getComponent(Point)}.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The index of the component in this container at the specified
     *         location or -1 if no component in this container is at that
     *         location.
     */
    @GuardedBy( "getLock()" )
    private int getComponentIndex(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert getLock().isHeldByCurrentThread();

        return layout_.getComponentIndex( this, location );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponents()
     */
    @Override
    public final List<IComponent> getComponents()
    {
        getLock().lock();
        try
        {
            return new ArrayList<IComponent>( components_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the default container layout.
     * 
     * @return The default container layout; never {@code null}.
     */
    /* @NonNull */
    abstract IContainerLayout getDefaultLayout();

    /*
     * @see org.gamegineer.table.core.IContainer#getLayout()
     */
    @Override
    public final IContainerLayout getLayout()
    {
        getLock().lock();
        try
        {
            return layout_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#readMemento(java.lang.Object)
     */
    @Override
    final void readMemento(
        final Object memento )
        throws MementoException
    {
        assert memento != null;
        assert getLock().isHeldByCurrentThread();

        super.readMemento( memento );

        setLayout( MementoUtils.getAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME, IContainerLayout.class ) );

        @SuppressWarnings( "unchecked" )
        final List<Object> componentMementos = MementoUtils.getAttribute( memento, COMPONENTS_MEMENTO_ATTRIBUTE_NAME, List.class );
        removeAllComponents();
        for( final Object componentMemento : componentMementos )
        {
            addComponent( ComponentFactory.createComponent( getTableEnvironment(), componentMemento ) );
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeAllComponents()
     */
    @Override
    public final List<IComponent> removeAllComponents()
    {
        return removeComponents( new ComponentRangeStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public final void removeComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final int componentIndex;

        getLock().lock();
        try
        {
            assertArgumentLegal( component.getContainer() == this, "component", NonNlsMessages.Container_removeComponent_component_notOwned ); //$NON-NLS-1$

            componentIndex = components_.indexOf( component );
            assert componentIndex != -1;
            final Component typedComponent = components_.remove( componentIndex );
            assert typedComponent != null;
            typedComponent.setParent( null );
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentRemoved( component, componentIndex );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponents(java.awt.Point)
     */
    @Override
    public final List<IComponent> removeComponents(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final ComponentRangeStrategy componentRangeStrategy = new ComponentRangeStrategy()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            int getLowerIndex()
            {
                final int componentIndex = getComponentIndex( location );
                return (componentIndex != -1) ? componentIndex : components_.size();
            }
        };
        return removeComponents( componentRangeStrategy );
    }

    /**
     * Removes all components from this container in the specified range.
     * 
     * @param componentRangeStrategy
     *        The strategy used to determine the range of components to remove;
     *        must not be {@code null}. The strategy will be invoked while the
     *        table lock is held.
     * 
     * @return The collection of components removed from this container; never
     *         {@code null}. The components are returned in order from the
     *         component nearest the bottom of the container to the component
     *         nearest the top of the container.
     */
    /* @NonNull */
    private List<IComponent> removeComponents(
        /* @NonNull */
        final ComponentRangeStrategy componentRangeStrategy )
    {
        assert componentRangeStrategy != null;

        final List<Component> removedComponents = new ArrayList<Component>();
        final int upperComponentIndex = componentRangeStrategy.getUpperIndex() - 1;
        final boolean containerBoundsChanged;

        getLock().lock();
        try
        {
            final Rectangle oldBounds = getBounds();

            removedComponents.addAll( components_.subList( componentRangeStrategy.getLowerIndex(), componentRangeStrategy.getUpperIndex() ) );
            components_.removeAll( removedComponents );
            for( final Component component : removedComponents )
            {
                component.setParent( null );
            }

            final Rectangle newBounds = getBounds();
            containerBoundsChanged = !newBounds.equals( oldBounds );
        }
        finally
        {
            getLock().unlock();
        }

        if( !removedComponents.isEmpty() || containerBoundsChanged )
        {
            addEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    // ensure events are fired in order from highest index to lowest index
                    final List<IComponent> reversedRemovedComponents = new ArrayList<IComponent>( removedComponents );
                    Collections.reverse( reversedRemovedComponents );
                    int componentIndex = upperComponentIndex;
                    for( final IComponent component : reversedRemovedComponents )
                    {
                        fireComponentRemoved( component, componentIndex-- );
                    }

                    if( containerBoundsChanged )
                    {
                        fireComponentBoundsChanged();
                    }
                }
            } );
        }

        return new ArrayList<IComponent>( removedComponents );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeContainerListener(org.gamegineer.table.core.IContainerListener)
     */
    @Override
    public final void removeContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( containerListeners_.remove( listener ), "listener", NonNlsMessages.Container_removeContainerListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeTopComponent()
     */
    @Override
    public final IComponent removeTopComponent()
    {
        final ComponentRangeStrategy componentRangeStrategy = new ComponentRangeStrategy()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            int getLowerIndex()
            {
                return components_.isEmpty() ? 0 : components_.size() - 1;
            }
        };
        final List<IComponent> components = removeComponents( componentRangeStrategy );
        assert components.size() <= 1;
        return components.isEmpty() ? null : components.get( 0 );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#setLayout(org.gamegineer.table.core.IContainerLayout)
     */
    @Override
    public final void setLayout(
        final IContainerLayout layout )
    {
        assertArgumentNotNull( layout, "layout" ); //$NON-NLS-1$

        final boolean containerBoundsChanged;

        getLock().lock();
        try
        {
            layout_ = layout;

            if( components_.isEmpty() )
            {
                containerBoundsChanged = false;
            }
            else
            {
                final Rectangle oldBounds = getBounds();

                layout_.layout( this );

                final Rectangle newBounds = getBounds();
                containerBoundsChanged = !newBounds.equals( oldBounds );
            }
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireContainerLayoutChanged();

                if( containerBoundsChanged )
                {
                    fireComponentBoundsChanged();
                }
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#translate(java.awt.Dimension)
     */
    @Override
    final void translate(
        final Dimension offset )
    {
        assert offset != null;
        assert getLock().isHeldByCurrentThread();

        super.translate( offset );

        for( final Component component : components_ )
        {
            component.translate( offset );
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#writeMemento(java.util.Map)
     */
    @Override
    final void writeMemento(
        final Map<String, Object> memento )
    {
        assert memento != null;
        assert getLock().isHeldByCurrentThread();

        super.writeMemento( memento );

        final List<Object> componentMementos = new ArrayList<Object>();
        for( final Component component : components_ )
        {
            componentMementos.add( component.createMemento() );
        }
        memento.put( COMPONENTS_MEMENTO_ATTRIBUTE_NAME, componentMementos );

        memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, layout_ );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A strategy to calculate a contiguous range of components in a container.
     */
    @Immutable
    private class ComponentRangeStrategy
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentRangeStrategy}
         * class.
         */
        ComponentRangeStrategy()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the lower index of the component range, inclusive.
         * 
         * <p>
         * The default implementation returns 0.
         * </p>
         * 
         * @return The lower index of the component range, inclusive.
         */
        int getLowerIndex()
        {
            return 0;
        }

        /**
         * Gets the upper index of the component range, exclusive.
         * 
         * <p>
         * The default implementation returns the size of the component
         * collection.
         * </p>
         * 
         * @return The upper index of the component range, exclusive.
         */
        @SuppressWarnings( "synthetic-access" )
        int getUpperIndex()
        {
            return components_.size();
        }
    }
}
