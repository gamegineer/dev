/*
 * Component.java
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
 * Created on Jul 4, 2012 at 8:09:29 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistryFacade;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;

/**
 * Implementation of {@link org.gamegineer.table.core.IComponent}.
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

    /** The name of the memento attribute that stores the component orientation. */
    private static final String ORIENTATION_MEMENTO_ATTRIBUTE_NAME = "component.orientation"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the component origin. */
    private static final String ORIGIN_MEMENTO_ATTRIBUTE_NAME = "component.origin"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the component surface
     * design identifiers.
     */
    private static final String SURFACE_DESIGN_IDS_MEMENTO_ATTRIBUTE_NAME = "component.surfaceDesignIds"; //$NON-NLS-1$

    /** The collection of component listeners. */
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
    private IComponentParent parent_;

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
     *        The table environment associated with the component; must not be
     *        {@code null}.
     * @param strategy
     *        The component strategy; must not be {@code null}.
     */
    Component(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final IComponentStrategy strategy )
    {
        assert tableEnvironment != null;
        assert strategy != null;

        componentListeners_ = new CopyOnWriteArrayList<IComponentListener>();
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
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( componentListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Component_addComponentListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Adds an event notification to the table environment associated with the
     * component.
     * 
     * @param notification
     *        The event notification; must not be {@code null}.
     */
    final void addEventNotification(
        /* @NonNull */
        final Runnable notification )
    {
        tableEnvironment_.addEventNotification( notification );
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
    final void fireComponentBoundsChanged()
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Component_componentBoundsChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Component_componentOrientationChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Component_componentSurfaceDesignChanged_unexpectedException, e );
            }
        }
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
            return new Rectangle( location_, surfaceDesigns_.get( orientation_ ).getSize() );
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
    public final Container getContainer()
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
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    final ReentrantLock getLock()
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
    public final ComponentPath getPath()
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

    /**
     * Gets the component strategy.
     * 
     * @return The component strategy; never {@code null}.
     */
    /* @NonNull */
    IComponentStrategy getStrategy()
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
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( isSupportedOrientation( orientation ), "orientation", NonNlsMessages.Component_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            return surfaceDesigns_.get( orientation );
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
            return new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>( surfaceDesigns_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the collection of surface design identifiers for this component.
     * 
     * @return The collection of surface design identifier for this component;
     *         never {@code null}.
     */
    @GuardedBy( "getLock()" )
    private Map<ComponentOrientation, ComponentSurfaceDesignId> getSurfaceDesignIds()
    {
        assert getLock().isHeldByCurrentThread();

        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesignId>( surfaceDesigns_.size() );
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
    public final Table getTable()
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

    /*
     * @see org.gamegineer.table.core.IComponent#isFocusable()
     */
    @Override
    public final boolean isFocusable()
    {
        return strategy_.isFocusable();
    }

    /**
     * Indicates the specified component orientation is supported.
     * 
     * @param orientation
     *        The component orientation; must not be {@code null}.
     * 
     * @return {@code true} if the specified component orientation is supported;
     *         otherwise {@code false}.
     */
    private boolean isSupportedOrientation(
        /* @NonNull */
        final ComponentOrientation orientation )
    {
        assert orientation != null;

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
     *        The memento; must not be {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento does not represent a valid state for the
     *         component.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "unchecked" )
    void readMemento(
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert memento != null;
        assert getLock().isHeldByCurrentThread();

        setLocation( MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class ) );
        setOrientation( MementoUtils.getAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME, ComponentOrientation.class ) );
        setOrigin( MementoUtils.getAttribute( memento, ORIGIN_MEMENTO_ATTRIBUTE_NAME, Point.class ) );
        setSurfaceDesignIds( MementoUtils.getAttribute( memento, SURFACE_DESIGN_IDS_MEMENTO_ATTRIBUTE_NAME, Map.class ) );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#removeComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public final void removeComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( componentListeners_.remove( listener ), "listener", NonNlsMessages.Component_removeComponentListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setLocation(java.awt.Point)
     */
    @Override
    public final void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

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
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

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
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( isSupportedOrientation( orientation ), "orientation", NonNlsMessages.Component_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            orientation_ = orientation;
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
                fireComponentOrientationChanged();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setOrigin(java.awt.Point)
     */
    @Override
    public final void setOrigin(
        final Point origin )
    {
        assertArgumentNotNull( origin, "origin" ); //$NON-NLS-1$

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
        /* @Nullable */
        final IComponentParent parent )
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
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( isSupportedOrientation( orientation ), "orientation", NonNlsMessages.Component_orientation_illegal ); //$NON-NLS-1$
        assertArgumentNotNull( surfaceDesign, "surfaceDesign" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            surfaceDesigns_.put( orientation, surfaceDesign );
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
     * Sets the collection of surface design identifiers for this component.
     * 
     * @param surfaceDesignIds
     *        The collection of surface design identifiers; must not be
     *        {@code null}. The collection may contain a subset of the supported
     *        component surface design identifiers.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If any component surface design identifier is not registered.
     */
    @GuardedBy( "getLock()" )
    private void setSurfaceDesignIds(
        /* @NonNull */
        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds )
        throws MementoException
    {
        assert surfaceDesignIds != null;

        try
        {
            final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>( surfaceDesignIds.size() );
            for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesignId> entry : surfaceDesignIds.entrySet() )
            {
                surfaceDesigns.put( entry.getKey(), ComponentSurfaceDesignRegistryFacade.getComponentSurfaceDesign( entry.getValue() ) );
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
    public void setSurfaceDesigns(
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns )
    {
        assertArgumentNotNull( surfaceDesigns, "surfaceDesigns" ); //$NON-NLS-1$

        for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesign> entry : surfaceDesigns.entrySet() )
        {
            assertArgumentLegal( entry.getValue() != null, "surfaceDesigns", NonNlsMessages.Component_setSurfaceDesigns_surfaceDesigns_containsNullSurfaceDesign ); //$NON-NLS-1$
            setSurfaceDesign( entry.getKey(), entry.getValue() );
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
     *        The translation offset; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    void translate(
        /* @NonNull */
        final Dimension offset )
    {
        assert offset != null;
        assert getLock().isHeldByCurrentThread();

        location_.translate( offset.width, offset.height );
        origin_.translate( offset.width, offset.height );

        addEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                fireComponentBoundsChanged();
            }
        } );
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
     *        The memento; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    void writeMemento(
        /* @NonNull */
        final Map<String, Object> memento )
    {
        assert memento != null;
        assert getLock().isHeldByCurrentThread();

        memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
        memento.put( ORIENTATION_MEMENTO_ATTRIBUTE_NAME, orientation_ );
        memento.put( ORIGIN_MEMENTO_ATTRIBUTE_NAME, new Point( origin_ ) );
        memento.put( SURFACE_DESIGN_IDS_MEMENTO_ATTRIBUTE_NAME, getSurfaceDesignIds() );
    }
}
