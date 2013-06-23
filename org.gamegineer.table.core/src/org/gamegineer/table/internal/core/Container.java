/*
 * Container.java
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.IterableUtils;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.NoSuchContainerLayoutException;

/**
 * Implementation of {@link org.gamegineer.table.core.IContainer}.
 */
@ThreadSafe
final class Container
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

    /**
     * The name of the memento attribute that stores the container layout
     * identifier.
     */
    private static final String LAYOUT_ID_MEMENTO_ATTRIBUTE_NAME = "container.layoutId"; //$NON-NLS-1$

    /**
     * The collection of components in this container ordered from bottom to
     * top.
     */
    @GuardedBy( "getLock()" )
    private final List<Component> components_;

    /** The collection of container listeners. */
    @GuardedBy( "getLock()" )
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
     * @param strategy
     *        The container strategy; must not be {@code null}.
     */
    Container(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final IContainerStrategy strategy )
    {
        super( tableEnvironment, strategy );

        components_ = new ArrayList<Component>();
        containerListeners_ = new CopyOnWriteArrayList<IContainerListener>();
        layout_ = strategy.getDefaultLayout();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainer#addComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public void addComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        addComponents( Collections.singletonList( component ), null );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addComponent(org.gamegineer.table.core.IComponent, int)
     */
    @Override
    public void addComponent(
        final IComponent component,
        final int index )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        addComponents( Collections.singletonList( component ), new Integer( index ) );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addComponents(java.util.List)
     */
    @Override
    public void addComponents(
        final List<IComponent> components )
    {
        assertArgumentNotNull( components, "components" ); //$NON-NLS-1$

        addComponents( components, null );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addComponents(java.util.List, int)
     */
    @Override
    public void addComponents(
        final List<IComponent> components,
        final int index )
    {
        assertArgumentNotNull( components, "components" ); //$NON-NLS-1$

        addComponents( components, new Integer( index ) );
    }

    /**
     * Adds the specified collection of components to this container at the
     * specified index.
     * 
     * @param components
     *        The collection of components to be added to this container; must
     *        not be {@code null}. The components are added to the this
     *        container at the specified index in the order they appear in the
     *        collection.
     * @param boxedIndex
     *        The index at which the components will be added or {@code null} if
     *        the components should be added to the top of this container.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code components} contains a {@code null} element; if any
     *         component is already contained in a container; or if any
     *         component was created by a table environment other than the table
     *         environment that created this container.
     * @throws java.lang.IndexOutOfBoundsException
     *         If {@code boxedIndex} is not {@code null} and out of range (
     *         {@code index < 0 || index > getComponentCount()}).
     */
    private void addComponents(
        /* @NonNull */
        final List<IComponent> components,
        /* @Nullable */
        final Integer boxedIndex )
    {
        assert components != null;

        getLock().lock();
        try
        {
            final List<Component> addedComponents = new ArrayList<Component>();
            final Rectangle oldBounds = getBounds();
            int index = (boxedIndex != null) ? boxedIndex.intValue() : components_.size();
            final int firstComponentIndex = index;

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
                components_.add( index, typedComponent );
                addedComponents.add( typedComponent );
                ++index;
            }

            layout_.layout( this );

            final Rectangle newBounds = getBounds();
            final boolean containerBoundsChanged = !newBounds.equals( oldBounds );

            if( containerBoundsChanged || !addedComponents.isEmpty() )
            {
                incrementTableRevisionNumber();
            }

            int componentIndex = firstComponentIndex;
            for( final Component component : addedComponents )
            {
                fireComponentAdded( component, componentIndex++ );
            }

            if( containerBoundsChanged )
            {
                fireComponentBoundsChanged();
            }
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addContainerListener(org.gamegineer.table.core.IContainerListener)
     */
    @Override
    public void addContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            assertArgumentLegal( containerListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Container_addContainerListener_listener_registered ); //$NON-NLS-1$
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Creates a container content changed event for this container.
     * 
     * @param component
     *        The component associated with the event; must not be {@code null}.
     * @param componentIndex
     *        The index of the component associated with the event; must not be
     *        negative.
     * 
     * @return A new container content changed event; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private ContainerContentChangedEvent createContainerContentChangedEvent(
        /* @NonNull */
        final Component component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert getLock().isHeldByCurrentThread();

        return new ContainerContentChangedEvent( this, getPath(), component, componentIndex );
    }

    /**
     * Creates a container event for this container.
     * 
     * @return A new container event; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private ContainerEvent createContainerEvent()
    {
        assert getLock().isHeldByCurrentThread();

        return new ContainerEvent( this, getPath() );
    }

    /**
     * Fires a component added event.
     * 
     * @param component
     *        The added component; must not be {@code null}.
     * @param componentIndex
     *        The index of the added component; must not be negative.
     */
    @GuardedBy( "getLock()" )
    private void fireComponentAdded(
        /* @NonNull */
        final Component component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert getLock().isHeldByCurrentThread();

        final ContainerContentChangedEvent event = createContainerContentChangedEvent( component, componentIndex );
        final Iterator<IContainerListener> iterator = containerListeners_.iterator();
        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                while( iterator.hasNext() )
                {
                    try
                    {
                        iterator.next().componentAdded( event );
                    }
                    catch( final RuntimeException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Container_componentAdded_unexpectedException, e );
                    }
                }
            }
        } );
    }

    /**
     * Fires a component removed event.
     * 
     * @param component
     *        The removed component; must not be {@code null}.
     * @param componentIndex
     *        The index of the removed component; must not be negative.
     */
    @GuardedBy( "getLock()" )
    private void fireComponentRemoved(
        /* @NonNull */
        final Component component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert getLock().isHeldByCurrentThread();

        final ContainerContentChangedEvent event = createContainerContentChangedEvent( component, componentIndex );
        final Iterator<IContainerListener> iterator = containerListeners_.iterator();
        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                while( iterator.hasNext() )
                {
                    try
                    {
                        iterator.next().componentRemoved( event );
                    }
                    catch( final RuntimeException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Container_componentRemoved_unexpectedException, e );
                    }
                }
            }
        } );
    }

    /**
     * Fires a container layout changed event.
     */
    @GuardedBy( "getLock()" )
    private void fireContainerLayoutChanged()
    {
        assert getLock().isHeldByCurrentThread();

        final ContainerEvent event = createContainerEvent();
        final Iterator<IContainerListener> iterator = containerListeners_.iterator();
        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                while( iterator.hasNext() )
                {
                    try
                    {
                        iterator.next().containerLayoutChanged( event );
                    }
                    catch( final RuntimeException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Container_containerLayoutChanged_unexpectedException, e );
                    }
                }
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getBounds()
     */
    @Override
    public Rectangle getBounds()
    {
        getLock().lock();
        try
        {
            Rectangle bounds = super.getBounds();

            for( final IComponent component : components_ )
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
    public ComponentPath getChildPath(
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
    public Component getComponent(
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
     * Gets the component in this container at the specified path.
     * 
     * @param paths
     *        The collection of constituent component paths of the overall
     *        component path; must not be {@code null} and must not be empty.
     * 
     * @return The component in this container at the specified path; or
     *         {@code null} if no component exists at the specified path.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    Component getComponent(
        /* @NonNull */
        final List<ComponentPath> paths )
    {
        assert paths != null;
        assert !paths.isEmpty();
        assert getLock().isHeldByCurrentThread();

        final ComponentPath path = paths.get( 0 );
        if( path.getIndex() < components_.size() )
        {
            final Component component = components_.get( path.getIndex() );
            if( paths.size() == 1 )
            {
                return component;
            }
            else if( component instanceof Container )
            {
                return ((Container)component).getComponent( paths.subList( 1, paths.size() ) );
            }
        }

        return null;
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponentCount()
     */
    @Override
    public int getComponentCount()
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
     * Gets the collection of mementos representing the components in this
     * container.
     * 
     * @return The collection of mementos representing the components in this
     *         container; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private List<Object> getComponentMementos()
    {
        assert getLock().isHeldByCurrentThread();

        final List<Object> componentMementos = new ArrayList<Object>( components_.size() );
        for( final Component component : components_ )
        {
            componentMementos.add( component.createMemento() );
        }

        return componentMementos;
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponents()
     */
    @Override
    public List<IComponent> getComponents()
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

    /*
     * @see org.gamegineer.table.core.IContainer#getLayout()
     */
    @Override
    public IContainerLayout getLayout()
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

    /**
     * Gets the identifier of the layout of components in this container.
     * 
     * @return The container layout identifier; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private ContainerLayoutId getLayoutId()
    {
        assert getLock().isHeldByCurrentThread();

        return layout_.getId();
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getStrategy()
     */
    @Override
    public IContainerStrategy getStrategy()
    {
        return (IContainerStrategy)super.getStrategy();
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#hitTest(java.awt.Point, java.util.List)
     */
    @Override
    boolean hitTest(
        final Point location,
        final List<IComponent> components )
    {
        assert location != null;
        assert components != null;
        assert getLock().isHeldByCurrentThread();

        if( super.hitTest( location, components ) )
        {
            for( final Component component : components_ )
            {
                component.hitTest( location, components );
            }

            return true;
        }

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#readMemento(java.lang.Object)
     */
    @Override
    @SuppressWarnings( "unchecked" )
    void readMemento(
        final Object memento )
        throws MementoException
    {
        assert memento != null;
        assert getLock().isHeldByCurrentThread();

        super.readMemento( memento );

        setLayoutId( MementoUtils.getAttribute( memento, LAYOUT_ID_MEMENTO_ATTRIBUTE_NAME, ContainerLayoutId.class ) );
        setComponentMementos( MementoUtils.getAttribute( memento, COMPONENTS_MEMENTO_ATTRIBUTE_NAME, List.class ) );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeAllComponents()
     */
    @Override
    public List<IComponent> removeAllComponents()
    {
        return removeComponents( new ComponentRangeStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public void removeComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final ComponentRangeStrategy componentRangeStrategy = new ComponentRangeStrategy()
        {
            private int index_ = -1;

            @GuardedBy( "getLock()" )
            @SuppressWarnings( "synthetic-access" )
            private void calculateComponentIndex()
            {
                assert getLock().isHeldByCurrentThread();

                if( index_ == -1 )
                {
                    assertArgumentLegal( component.getContainer() == Container.this, "component", NonNlsMessages.Container_removeComponent_component_notOwned ); //$NON-NLS-1$
                    index_ = components_.indexOf( component );
                    assert index_ != -1;
                }
            }

            @Override
            int getLowerIndex()
            {
                assert getLock().isHeldByCurrentThread();

                calculateComponentIndex();
                return index_;
            }

            @Override
            int getUpperIndex()
            {
                assert getLock().isHeldByCurrentThread();

                calculateComponentIndex();
                return index_ + 1;
            }
        };
        final List<IComponent> components = removeComponents( componentRangeStrategy );
        assert components.size() == 1;
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponent(int)
     */
    @Override
    public IComponent removeComponent(
        final int index )
    {
        final ComponentRangeStrategy componentRangeStrategy = new ComponentRangeStrategy()
        {
            @Override
            int getLowerIndex()
            {
                assert getLock().isHeldByCurrentThread();

                return index;
            }

            @Override
            int getUpperIndex()
            {
                assert getLock().isHeldByCurrentThread();

                return index + 1;
            }
        };
        final List<IComponent> components = removeComponents( componentRangeStrategy );
        assert components.size() == 1;
        return components.get( 0 );
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

            layout_.layout( this );

            final Rectangle newBounds = getBounds();
            final boolean containerBoundsChanged = !newBounds.equals( oldBounds );

            if( containerBoundsChanged || !removedComponents.isEmpty() )
            {
                incrementTableRevisionNumber();
            }

            // ensure events are fired in order from highest index to lowest index
            int componentIndex = componentRangeStrategy.getUpperIndex();
            for( final Component component : IterableUtils.reverse( removedComponents ) )
            {
                fireComponentRemoved( component, --componentIndex );
            }

            if( containerBoundsChanged )
            {
                fireComponentBoundsChanged();
            }
        }
        finally
        {
            getLock().unlock();
        }

        return new ArrayList<IComponent>( removedComponents );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeContainerListener(org.gamegineer.table.core.IContainerListener)
     */
    @Override
    public void removeContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            assertArgumentLegal( containerListeners_.remove( listener ), "listener", NonNlsMessages.Container_removeContainerListener_listener_notRegistered ); //$NON-NLS-1$
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Sets the collection of mementos representing the components in this
     * container.
     * 
     * @param componentMementos
     *        The collection of mementos representing the components in this
     *        container; must not be {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If any component memento is malformed.
     */
    @GuardedBy( "getLock()" )
    private void setComponentMementos(
        /* @NonNull */
        final List<Object> componentMementos )
        throws MementoException
    {
        assert componentMementos != null;
        assert getLock().isHeldByCurrentThread();

        removeAllComponents();

        for( final Object componentMemento : componentMementos )
        {
            addComponent( ComponentFactory.createComponent( getTableEnvironment(), componentMemento ) );
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#setLayout(org.gamegineer.table.core.IContainerLayout)
     */
    @Override
    public void setLayout(
        final IContainerLayout layout )
    {
        assertArgumentNotNull( layout, "layout" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            layout_ = layout;
            final boolean containerBoundsChanged;

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

            incrementTableRevisionNumber();
            fireContainerLayoutChanged();
            if( containerBoundsChanged )
            {
                fireComponentBoundsChanged();
            }
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Sets the identifier of the layout of components in this container.
     * 
     * @param layoutId
     *        The container layout identifier; must not be {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the container layout identifier is not registered.
     */
    @GuardedBy( "getLock()" )
    private void setLayoutId(
        /* @NonNull */
        final ContainerLayoutId layoutId )
        throws MementoException
    {
        assert layoutId != null;
        assert getLock().isHeldByCurrentThread();

        try
        {
            setLayout( ContainerLayoutRegistry.getContainerLayout( layoutId ) );
        }
        catch( final NoSuchContainerLayoutException e )
        {
            throw new MementoException( e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#translate(java.awt.Dimension)
     */
    @Override
    void translate(
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
    void writeMemento(
        final Map<String, Object> memento )
    {
        assert memento != null;
        assert getLock().isHeldByCurrentThread();

        super.writeMemento( memento );

        memento.put( COMPONENTS_MEMENTO_ATTRIBUTE_NAME, getComponentMementos() );
        memento.put( LAYOUT_ID_MEMENTO_ATTRIBUTE_NAME, getLayoutId() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A strategy to calculate a contiguous range of components in a container.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private class ComponentRangeStrategy
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The upper index of the component range, exclusive. */
        private final int upperIndex_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentRangeStrategy}
         * class.
         */
        ComponentRangeStrategy()
        {
            upperIndex_ = components_.size();
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
        @GuardedBy( "getLock()" )
        int getLowerIndex()
        {
            assert getLock().isHeldByCurrentThread();

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
        @GuardedBy( "getLock()" )
        int getUpperIndex()
        {
            assert getLock().isHeldByCurrentThread();

            return upperIndex_;
        }
    }
}
