/*
 * Tabletop.java
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
 * Created on Jun 28, 2012 at 8:07:30 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.core.TabletopLayouts;
import org.gamegineer.table.core.TabletopOrientation;

/**
 * A tabletop.
 */
@ThreadSafe
final class Tabletop
    extends Container
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the memento attribute that stores the collection of
     * components on the tabletop.
     */
    private static final String COMPONENTS_MEMENTO_ATTRIBUTE_NAME = "components"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the tabletop layout. */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "layout"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the tabletop location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "location"; //$NON-NLS-1$

    /** The collection of supported component orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( TabletopOrientation.values( TabletopOrientation.class ) ) );

    /**
     * The name of the memento attribute that stores the component surface
     * design.
     */
    private static final String SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "surfaceDesign"; //$NON-NLS-1$

    /** The collection of component listeners. */
    private final CopyOnWriteArrayList<IComponentListener> componentListeners_;

    /**
     * The collection of components on this tabletop ordered from bottom to top.
     */
    @GuardedBy( "getLock()" )
    private final List<Component> components_;

    /** The collection of container listeners. */
    private final CopyOnWriteArrayList<IContainerListener> containerListeners_;

    /** The tabletop layout. */
    @GuardedBy( "getLock()" )
    private IContainerLayout layout_;

    /** The tabletop location in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point location_;

    /** The tabletop surface design. */
    @GuardedBy( "getLock()" )
    private ComponentSurfaceDesign surfaceDesign_;

    /**
     * The table that contains this tabletop or {@code null} if this component
     * is not contained in a table.
     */
    @GuardedBy( "getLock()" )
    private Table table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Tabletop} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the tabletop; must not be
     *        {@code null}.
     */
    Tabletop(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );

        componentListeners_ = new CopyOnWriteArrayList<IComponentListener>();
        components_ = new ArrayList<Component>();
        containerListeners_ = new CopyOnWriteArrayList<IContainerListener>();
        layout_ = TabletopLayouts.ABSOLUTE;
        location_ = new Point( Short.MIN_VALUE / 2, Short.MIN_VALUE / 2 );
        surfaceDesign_ = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Tabletop.DEFAULT_SURFACE_DESIGN" ), Short.MAX_VALUE, Short.MAX_VALUE ); //$NON-NLS-1$
        table_ = null;
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

        addComponents( Collections.singletonList( component ) );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#addComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void addComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( componentListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Tabletop_addComponentListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addComponents(java.util.List)
     */
    @Override
    public void addComponents(
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
                    throw new IllegalArgumentException( NonNlsMessages.Tabletop_addComponents_components_containsNullElement );
                }
                assertArgumentLegal( typedComponent.getContainer() == null, "components", NonNlsMessages.Tabletop_addComponents_components_containsOwnedComponent ); //$NON-NLS-1$
                assertArgumentLegal( typedComponent.getTableEnvironment() == getTableEnvironment(), "components", NonNlsMessages.Tabletop_addComponents_components_containsComponentCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

                typedComponent.setContainer( this );
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
    public void addContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( containerListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Tabletop_addContainerListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
     */
    @Override
    public Object createMemento()
    {
        final Map<String, Object> memento = new HashMap<String, Object>();

        getLock().lock();
        try
        {
            memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, layout_ );
            memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
            memento.put( SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, surfaceDesign_ );

            final List<Object> componentMementos = new ArrayList<Object>( components_.size() );
            for( final IComponent component : components_ )
            {
                componentMementos.add( component.createMemento() );
            }
            memento.put( COMPONENTS_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( componentMementos ) );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Tabletop_componentAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component bounds changed event.
     */
    private void fireComponentBoundsChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : componentListeners_ )
        {
            try
            {
                listener.componentBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Tabletop_componentBoundsChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component orientation changed event.
     */
    private void fireComponentOrientationChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : componentListeners_ )
        {
            try
            {
                listener.componentOrientationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Tabletop_componentOrientationChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Tabletop_componentRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component surface design changed event.
     */
    private void fireComponentSurfaceDesignChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : componentListeners_ )
        {
            try
            {
                listener.componentSurfaceDesignChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Tabletop_componentSurfaceDesignChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Tabletop_containerLayoutChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Creates a new instance of the {@code Tabletop} class from the specified
     * memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new tabletop; must not
     *        be {@code null}.
     * @param memento
     *        The memento representing the initial tabletop state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Tabletop} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Tabletop fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final Tabletop tabletop = new Tabletop( tableEnvironment );

        final IContainerLayout layout = MementoUtils.getAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME, IContainerLayout.class );
        tabletop.setLayout( layout );

        final Point location = MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        tabletop.setLocation( location );

        final ComponentSurfaceDesign surfaceDesign = MementoUtils.getAttribute( memento, SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        tabletop.setSurfaceDesign( TabletopOrientation.DEFAULT, surfaceDesign );

        @SuppressWarnings( "unchecked" )
        final List<Object> componentMementos = MementoUtils.getAttribute( memento, COMPONENTS_MEMENTO_ATTRIBUTE_NAME, List.class );
        for( final Object componentMemento : componentMementos )
        {
            tabletop.addComponent( Component.fromMemento( tableEnvironment, componentMemento ) );
        }

        return tabletop;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getBounds()
     */
    @Override
    public Rectangle getBounds()
    {
        getLock().lock();
        try
        {
            Rectangle bounds = new Rectangle( location_, surfaceDesign_.getSize() );
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
     * @see org.gamegineer.table.internal.core.Container#getComponent(int)
     */
    @Override
    public Component getComponent(
        final int index )
    {
        getLock().lock();
        try
        {
            assertArgumentLegal( (index >= 0) && (index < components_.size()), "index", NonNlsMessages.Tabletop_getComponentFromIndex_index_outOfRange ); //$NON-NLS-1$

            return components_.get( index );
        }
        finally
        {
            getLock().unlock();
        }
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

    /*
     * @see org.gamegineer.table.internal.core.Container#getComponentIndex(org.gamegineer.table.internal.core.Component)
     */
    @Override
    int getComponentIndex(
        final Component component )
    {
        assert component != null;
        assert getLock().isHeldByCurrentThread();

        final int index = components_.indexOf( component );
        assert index != -1;
        return index;
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#getComponentIndex(java.awt.Point)
     */
    @Override
    int getComponentIndex(
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

    /*
     * @see org.gamegineer.table.core.IComponent#getLocation()
     */
    @Override
    public Point getLocation()
    {
        getLock().lock();
        try
        {
            return new Point( location_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrientation()
     */
    @Override
    public ComponentOrientation getOrientation()
    {
        return TabletopOrientation.DEFAULT;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrigin()
     */
    @Override
    public Point getOrigin()
    {
        return getLocation();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getPath()
     */
    @Override
    public ComponentPath getPath()
    {
        getLock().lock();
        try
        {
            if( table_ == null )
            {
                return null;
            }

            return new ComponentPath( null, 0 );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSize()
     */
    @Override
    public Dimension getSize()
    {
        return getBounds().getSize();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSurfaceDesign(org.gamegineer.table.core.ComponentOrientation)
     */
    @Override
    public ComponentSurfaceDesign getSurfaceDesign(
        final ComponentOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( orientation instanceof TabletopOrientation, "orientation", NonNlsMessages.Tabletop_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == TabletopOrientation.DEFAULT )
            {
                return surfaceDesign_;
            }

            throw new AssertionError( "unknown tabletop orientation" ); //$NON-NLS-1$
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getTableInternal()
     */
    @Override
    Table getTableInternal()
    {
        assert getLock().isHeldByCurrentThread();

        return table_;
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#hasComponents()
     */
    @Override
    boolean hasComponents()
    {
        assert getLock().isHeldByCurrentThread();

        return !components_.isEmpty();
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponent()
     */
    @Override
    public IComponent removeComponent()
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
     * @see org.gamegineer.table.core.IContainer#removeComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public void removeComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final int componentIndex;

        getLock().lock();
        try
        {
            assertArgumentLegal( component.getContainer() == this, "component", NonNlsMessages.Tabletop_removeComponent_component_notOwned ); //$NON-NLS-1$

            componentIndex = components_.indexOf( component );
            assert componentIndex != -1;
            final Component typedComponent = components_.remove( componentIndex );
            assert typedComponent != null;
            typedComponent.setContainer( null );
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
     * @see org.gamegineer.table.core.IComponent#removeComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void removeComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( componentListeners_.remove( listener ), "listener", NonNlsMessages.Tabletop_removeComponentListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponents()
     */
    @Override
    public List<IComponent> removeComponents()
    {
        return removeComponents( new ComponentRangeStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponents(java.awt.Point)
     */
    @Override
    public List<IComponent> removeComponents(
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
                component.setContainer( null );
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
    public void removeContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( containerListeners_.remove( listener ), "listener", NonNlsMessages.Tabletop_removeContainerListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#setLayout(org.gamegineer.table.core.IContainerLayout)
     */
    @Override
    public void setLayout(
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
     * @see org.gamegineer.table.core.IComponent#setLocation(java.awt.Point)
     */
    @Override
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        translateLocation( new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldLocation = getLocation();
                return new Dimension( location.x - oldLocation.x, location.y - oldLocation.y );
            }
        } );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
     */
    @Override
    public void setMemento(
        final Object memento )
        throws MementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            final Tabletop tabletop = fromMemento( getTableEnvironment(), memento );

            setLayout( tabletop.getLayout() );
            setLocation( tabletop.getLocation() );
            setSurfaceDesign( TabletopOrientation.DEFAULT, tabletop.getSurfaceDesign( TabletopOrientation.DEFAULT ) );

            removeComponents();
            addComponents( tabletop.removeComponents() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setOrientation(org.gamegineer.table.core.ComponentOrientation)
     */
    @Override
    public void setOrientation(
        final ComponentOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( orientation instanceof TabletopOrientation, "orientation", NonNlsMessages.Tabletop_orientation_illegal ); //$NON-NLS-1$

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentOrientationChanged();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setOrigin(java.awt.Point)
     */
    @Override
    public void setOrigin(
        final Point origin )
    {
        setLocation( origin );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setSurfaceDesign(org.gamegineer.table.core.ComponentOrientation, org.gamegineer.table.core.ComponentSurfaceDesign)
     */
    @Override
    public void setSurfaceDesign(
        final ComponentOrientation orientation,
        final ComponentSurfaceDesign surfaceDesign )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( orientation instanceof TabletopOrientation, "orientation", NonNlsMessages.Tabletop_orientation_illegal ); //$NON-NLS-1$
        assertArgumentNotNull( surfaceDesign, "surfaceDesign" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == TabletopOrientation.DEFAULT )
            {
                surfaceDesign_ = surfaceDesign;
            }
            else
            {
                throw new AssertionError( "unknown tabletop orientation" ); //$NON-NLS-1$
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
                fireComponentSurfaceDesignChanged();
            }
        } );
    }

    /**
     * Sets the table that contains this tabletop.
     * 
     * @param table
     *        The table that contains this tabletop or {@code null} if this
     *        tabletop is not contained in a table.
     */
    @GuardedBy( "getLock()" )
    void setTable(
        /* @Nullable */
        final Table table )
    {
        assert getLock().isHeldByCurrentThread();

        table_ = table;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Tabletop[" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            sb.append( "surfaceDesign_=" ); //$NON-NLS-1$
            sb.append( surfaceDesign_ );
            sb.append( ", location_=" ); //$NON-NLS-1$
            sb.append( location_ );
            sb.append( ", components_.size()=" ); //$NON-NLS-1$
            sb.append( components_.size() );
            sb.append( ", layout_=" ); //$NON-NLS-1$
            sb.append( layout_ );
        }
        finally
        {
            getLock().unlock();
        }

        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }

    /**
     * Translates the tabletop location by the specified amount.
     * 
     * @param translationOffsetStrategy
     *        The strategy used to calculate the amount to translate the
     *        location; must not be {@code null}. The strategy will be invoked
     *        while the table environment lock is held.
     */
    private void translateLocation(
        /* @NonNull */
        final TranslationOffsetStrategy translationOffsetStrategy )
    {
        assert translationOffsetStrategy != null;

        getLock().lock();
        try
        {
            final Dimension offset = translationOffsetStrategy.getOffset();
            location_.translate( offset.width, offset.height );
            for( final Component component : components_ )
            {
                final Point componentLocation = component.getLocation();
                componentLocation.translate( offset.width, offset.height );
                component.setLocation( componentLocation );
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
                fireComponentBoundsChanged();
            }
        } );
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

    /**
     * A strategy to calculate a translation offset.
     */
    @Immutable
    private class TranslationOffsetStrategy
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TranslationOffsetStrategy}
         * class.
         */
        TranslationOffsetStrategy()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the translation offset.
         * 
         * <p>
         * The default implementation returns a zero offset.
         * </p>
         * 
         * @return The translation offset; never {@code null}.
         */
        /* @NonNull */
        Dimension getOffset()
        {
            return new Dimension( 0, 0 );
        }
    }
}
