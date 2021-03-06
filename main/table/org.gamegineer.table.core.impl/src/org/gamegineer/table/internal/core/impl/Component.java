/*
 * Component.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Jul 4, 2012 at 8:09:29 PM.
 */

package org.gamegineer.table.internal.core.impl;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.ITableEnvironmentLock;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;

/**
 * Implementation of {@link IComponent}.
 */
@ThreadSafe
class Component
    implements IComponent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the memento attribute that stores the component location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "component.location"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the component orientation.
     */
    private static final String ORIENTATION_MEMENTO_ATTRIBUTE_NAME = "component.orientation"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the component origin. */
    private static final String ORIGIN_MEMENTO_ATTRIBUTE_NAME = "component.origin"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the component surface
     * design identifiers.
     */
    private static final String SURFACE_DESIGN_IDS_MEMENTO_ATTRIBUTE_NAME = "component.surfaceDesignIds"; //$NON-NLS-1$

    /** The collection of component listeners. */
    @GuardedBy( "getLock()" )
    private final CopyOnWriteArrayList<IComponentListener> componentListeners_;

    /** The component location in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point location_;

    /** The component orientation. */
    @GuardedBy( "getLock()" )
    private ComponentOrientation orientation_;

    /** The component origin in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point origin_;

    /** The component parent or {@code null} if this component has no parent. */
    @GuardedBy( "getLock()" )
    private @Nullable IComponentParent parent_;

    /** The component strategy. */
    private final IComponentStrategy strategy_;

    /**
     * The collection of component surface designs. The key is the component
     * orientation. The value is the component surface design.
     */
    @GuardedBy( "getLock()" )
    private final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns_;

    /** The table environment associated with the component. */
    private final TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Component} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the component.
     * @param strategy
     *        The component strategy.
     */
    Component(
        final TableEnvironment tableEnvironment,
        final IComponentStrategy strategy )
    {
        componentListeners_ = new CopyOnWriteArrayList<>();
        location_ = strategy.getDefaultLocation();
        orientation_ = strategy.getDefaultOrientation();
        origin_ = strategy.getDefaultOrigin();
        parent_ = null;
        strategy_ = strategy;
        surfaceDesigns_ = strategy.getDefaultSurfaceDesigns();
        tableEnvironment_ = tableEnvironment;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponent#addComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public final void addComponentListener(
        final IComponentListener listener )
    {
        getLock().lock();
        try
        {
            assertArgumentLegal( componentListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Component_addComponentListener_listener_registered ); //$NON-NLS-1$
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Creates a component event for this component.
     * 
     * @return A new component event.
     */
    @GuardedBy( "getLock()" )
    private ComponentEvent createComponentEvent()
    {
        assert getLock().isHeldByCurrentThread();

        return new ComponentEvent( this, getPath() );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
     */
    @Override
    public final Object createMemento()
    {
        final Map<String, Object> memento = ComponentFactory.createMemento( this );

        getLock().lock();
        try
        {
            writeMemento( memento );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Fires a component bounds changed event.
     */
    @GuardedBy( "getLock()" )
    final void fireComponentBoundsChanged()
    {
        assert getLock().isHeldByCurrentThread();

        final ComponentEvent event = createComponentEvent();
        final Iterator<IComponentListener> iterator = componentListeners_.iterator();
        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                while( iterator.hasNext() )
                {
                    try
                    {
                        iterator.next().componentBoundsChanged( event );
                    }
                    catch( final RuntimeException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Component_componentBoundsChanged_unexpectedException, e );
                    }
                }
            }
        } );
    }

    /**
     * Fires a component orientation changed event.
     */
    @GuardedBy( "getLock()" )
    private void fireComponentOrientationChanged()
    {
        assert getLock().isHeldByCurrentThread();

        final ComponentEvent event = createComponentEvent();
        final Iterator<IComponentListener> iterator = componentListeners_.iterator();
        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                while( iterator.hasNext() )
                {
                    try
                    {
                        iterator.next().componentOrientationChanged( event );
                    }
                    catch( final RuntimeException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Component_componentOrientationChanged_unexpectedException, e );
                    }
                }
            }
        } );
    }

    /**
     * Fires a component surface design changed event.
     */
    @GuardedBy( "getLock()" )
    private void fireComponentSurfaceDesignChanged()
    {
        assert getLock().isHeldByCurrentThread();

        final ComponentEvent event = createComponentEvent();
        final Iterator<IComponentListener> iterator = componentListeners_.iterator();
        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                while( iterator.hasNext() )
                {
                    try
                    {
                        iterator.next().componentSurfaceDesignChanged( event );
                    }
                    catch( final RuntimeException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Component_componentSurfaceDesignChanged_unexpectedException, e );
                    }
                }
            }
        } );
    }

    /**
     * Fires the specified event notification.
     * 
     * @param eventNotification
     *        The event notification.
     */
    @GuardedBy( "getLock()" )
    final void fireEventNotification(
        final Runnable eventNotification )
    {
        assert getLock().isHeldByCurrentThread();

        tableEnvironment_.fireEventNotification( eventNotification );
    }

    /**
     * This implementation defines the component bounds using the component
     * location and the size of the surface design in the current orientation.
     * Subclasses may override and are not required to call the superclass
     * implementation.
     * 
     * @see org.gamegineer.table.core.IComponent#getBounds()
     */
    @Override
    public Rectangle getBounds()
    {
        getLock().lock();
        try
        {
            final ComponentSurfaceDesign surfaceDesign = surfaceDesigns_.get( orientation_ );
            assert surfaceDesign != null;
            return new Rectangle( location_, surfaceDesign.getSize() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getContainer()
     */
    @Override
    public final @Nullable Container getContainer()
    {
        getLock().lock();
        try
        {
            return (parent_ instanceof Container) ? (Container)parent_ : null;
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
    public final Point getLocation()
    {
        return getBounds().getLocation();
    }

    /**
     * Gets the table environment lock.
     * 
     * @return The table environment lock.
     */
    final ITableEnvironmentLock getLock()
    {
        return tableEnvironment_.getLock();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrientation()
     */
    @Override
    public final ComponentOrientation getOrientation()
    {
        getLock().lock();
        try
        {
            return orientation_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrigin()
     */
    @Override
    public final Point getOrigin()
    {
        getLock().lock();
        try
        {
            return new Point( origin_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getPath()
     */
    @Override
    public final @Nullable ComponentPath getPath()
    {
        getLock().lock();
        try
        {
            return (parent_ != null) ? parent_.getChildPath( this ) : null;
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
    public final Dimension getSize()
    {
        return getBounds().getSize();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getStrategy()
     */
    @Override
    public IComponentStrategy getStrategy()
    {
        return strategy_;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSupportedOrientations()
     */
    @Override
    public final Collection<ComponentOrientation> getSupportedOrientations()
    {
        return strategy_.getSupportedOrientations();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSurfaceDesign(org.gamegineer.table.core.ComponentOrientation)
     */
    @Override
    public final ComponentSurfaceDesign getSurfaceDesign(
        final ComponentOrientation orientation )
    {
        assertArgumentLegal( isSupportedOrientation( orientation ), "orientation", NonNlsMessages.Component_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            final ComponentSurfaceDesign surfaceDesign = surfaceDesigns_.get( orientation );
            assert surfaceDesign != null;
            return surfaceDesign;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSurfaceDesigns()
     */
    @Override
    public final Map<ComponentOrientation, ComponentSurfaceDesign> getSurfaceDesigns()
    {
        getLock().lock();
        try
        {
            return new IdentityHashMap<>( surfaceDesigns_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the collection of surface design identifiers for this component.
     * 
     * @return The collection of surface design identifier for this component.
     */
    @GuardedBy( "getLock()" )
    private Map<ComponentOrientation, ComponentSurfaceDesignId> getSurfaceDesignIds()
    {
        assert getLock().isHeldByCurrentThread();

        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds = new IdentityHashMap<>( surfaceDesigns_.size() );
        for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesign> entry : surfaceDesigns_.entrySet() )
        {
            surfaceDesignIds.put( entry.getKey(), entry.getValue().getId() );
        }

        return surfaceDesignIds;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getTable()
     */
    @Override
    public final @Nullable Table getTable()
    {
        getLock().lock();
        try
        {
            return (parent_ != null) ? parent_.getTable() : null;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getTableEnvironment()
     */
    @Override
    public final TableEnvironment getTableEnvironment()
    {
        return tableEnvironment_;
    }

    /**
     * Hit tests this component against the specified location.
     * 
     * <p>
     * This implementation adds this component to the specified collection if it
     * occupies the specified location. Subclasses may override and must call
     * the superclass implementation.
     * </p>
     * 
     * @param location
     *        The location in table coordinates.
     * @param components
     *        The collection that receives the components that occupy the
     *        specified location.
     * 
     * @return {@code true} if this component occupies the specified location;
     *         otherwise {@code false}.
     */
    @GuardedBy( "getLock()" )
    boolean hitTest(
        final Point location,
        final List<IComponent> components )
    {
        assert getLock().isHeldByCurrentThread();

        if( getBounds().contains( location ) )
        {
            components.add( this );
            return true;
        }

        return false;
    }

    /**
     * Increments the revision number of the table associated with this
     * component.
     * 
     * <p>
     * This method does nothing if this component is not associated with a
     * table.
     * </p>
     */
    @GuardedBy( "getLock()" )
    final void incrementTableRevisionNumber()
    {
        assert getLock().isHeldByCurrentThread();

        final Table table = getTable();
        if( table != null )
        {
            table.incrementRevisionNumber();
        }
    }

    /**
     * Indicates the specified component orientation is supported.
     * 
     * @param orientation
     *        The component orientation.
     * 
     * @return {@code true} if the specified component orientation is supported;
     *         otherwise {@code false}.
     */
    private boolean isSupportedOrientation(
        final ComponentOrientation orientation )
    {
        return getSupportedOrientations().contains( orientation );
    }

    /**
     * Reads the state of this object from the specified memento.
     * 
     * <p>
     * This implementation reads the component attributes from the specified
     * memento. Subclasses may override and must call the superclass
     * implementation.
     * </p>
     * 
     * @param memento
     *        The memento.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento does not represent a valid state for the
     *         component.
     */
    @GuardedBy( "getLock()" )
    void readMemento(
        final Object memento )
        throws MementoException
    {
        assert getLock().isHeldByCurrentThread();

        setLocation( MementoUtils.<@NonNull Point>getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class ) );
        setOrientation( MementoUtils.<@NonNull ComponentOrientation>getAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME, ComponentOrientation.class ) );
        setOrigin( MementoUtils.<@NonNull Point>getAttribute( memento, ORIGIN_MEMENTO_ATTRIBUTE_NAME, Point.class ) );
        @SuppressWarnings( {
            "rawtypes", "unchecked"
        } )
        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds = MementoUtils.<@NonNull Map>getAttribute( memento, SURFACE_DESIGN_IDS_MEMENTO_ATTRIBUTE_NAME, Map.class );
        setSurfaceDesignIds( surfaceDesignIds );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#removeComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public final void removeComponentListener(
        final IComponentListener listener )
    {
        getLock().lock();
        try
        {
            assertArgumentLegal( componentListeners_.remove( listener ), "listener", NonNlsMessages.Component_removeComponentListener_listener_notRegistered ); //$NON-NLS-1$
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setLocation(java.awt.Point)
     */
    @Override
    public final void setLocation(
        final Point location )
    {
        getLock().lock();
        try
        {
            translate( new Dimension( location.x - location_.x, location.y - location_.y ) );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
     */
    @Override
    public final void setMemento(
        final Object memento )
        throws MementoException
    {
        getLock().lock();
        try
        {
            readMemento( memento );
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
    public final void setOrientation(
        final ComponentOrientation orientation )
    {
        assertArgumentLegal( isSupportedOrientation( orientation ), "orientation", NonNlsMessages.Component_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            orientation_ = orientation;
            incrementTableRevisionNumber();
            fireComponentOrientationChanged();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setOrigin(java.awt.Point)
     */
    @Override
    public final void setOrigin(
        final Point origin )
    {
        getLock().lock();
        try
        {
            translate( new Dimension( origin.x - origin_.x, origin.y - origin_.y ) );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Sets the component parent.
     * 
     * @param parent
     *        The component parent or {@code null} if this component has no
     *        parent.
     */
    @GuardedBy( "getLock()" )
    final void setParent(
        final @Nullable IComponentParent parent )
    {
        assert getLock().isHeldByCurrentThread();

        parent_ = parent;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setSurfaceDesign(org.gamegineer.table.core.ComponentOrientation, org.gamegineer.table.core.ComponentSurfaceDesign)
     */
    @Override
    public final void setSurfaceDesign(
        final ComponentOrientation orientation,
        final ComponentSurfaceDesign surfaceDesign )
    {
        assertArgumentLegal( isSupportedOrientation( orientation ), "orientation", NonNlsMessages.Component_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            surfaceDesigns_.put( orientation, surfaceDesign );
            incrementTableRevisionNumber();
            fireComponentSurfaceDesignChanged();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Sets the collection of surface design identifiers for this component.
     * 
     * @param surfaceDesignIds
     *        The collection of surface design identifiers. The collection may
     *        contain a subset of the supported component surface design
     *        identifiers.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If any component surface design identifier is not registered.
     */
    @GuardedBy( "getLock()" )
    private void setSurfaceDesignIds(
        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds )
        throws MementoException
    {
        try
        {
            final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<>( surfaceDesignIds.size() );
            for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesignId> entry : surfaceDesignIds.entrySet() )
            {
                surfaceDesigns.put( entry.getKey(), ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( entry.getValue() ) );
            }

            setSurfaceDesigns( surfaceDesigns );
        }
        catch( final NoSuchComponentSurfaceDesignException e )
        {
            throw new MementoException( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setSurfaceDesigns(java.util.Map)
     */
    @Override
    public final void setSurfaceDesigns(
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns )
    {
        for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesign> entry : surfaceDesigns.entrySet() )
        {
            final ComponentOrientation orientation = entry.getKey();
            final ComponentSurfaceDesign surfaceDesign = entry.getValue();
            setSurfaceDesign( orientation, surfaceDesign );
        }
    }

    /**
     * Translates this component by the specified offset.
     * 
     * <p>
     * This implementation translates the component location and origin and
     * notifies listeners that the component bounds have changed. Subclasses may
     * override and must call the superclass implementation.
     * </p>
     * 
     * @param offset
     *        The translation offset.
     */
    @GuardedBy( "getLock()" )
    void translate(
        final Dimension offset )
    {
        assert getLock().isHeldByCurrentThread();

        location_.translate( offset.width, offset.height );
        origin_.translate( offset.width, offset.height );
        incrementTableRevisionNumber();
        fireComponentBoundsChanged();
    }

    /**
     * Writes the state of this object to the specified memento.
     * 
     * <p>
     * This implementation writes the component attributes to the specified
     * memento. Subclasses may override and must call the superclass
     * implementation.
     * </p>
     * 
     * @param memento
     *        The memento.
     */
    @GuardedBy( "getLock()" )
    void writeMemento(
        final Map<String, Object> memento )
    {
        assert getLock().isHeldByCurrentThread();

        memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
        memento.put( ORIENTATION_MEMENTO_ATTRIBUTE_NAME, orientation_ );
        memento.put( ORIGIN_MEMENTO_ATTRIBUTE_NAME, new Point( origin_ ) );
        memento.put( SURFACE_DESIGN_IDS_MEMENTO_ATTRIBUTE_NAME, getSurfaceDesignIds() );
    }
}
