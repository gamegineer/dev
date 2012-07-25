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
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.ITable;

/**
 * Implementation of {@link org.gamegineer.table.core.IComponent}.
 */
@ThreadSafe
abstract class Component
    implements IComponent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of component listeners. */
    private final CopyOnWriteArrayList<IComponentListener> componentListeners_;

    /**
     * The container that contains this component or {@code null} if this
     * component is not contained in a container.
     */
    @GuardedBy( "getLock()" )
    private Container container_;

    /** The component orientation. */
    @GuardedBy( "getLock()" )
    private ComponentOrientation orientation_;

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
     */
    Component(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        assert tableEnvironment != null;

        componentListeners_ = new CopyOnWriteArrayList<IComponentListener>();
        container_ = null;
        orientation_ = getDefaultOrientation();
        surfaceDesigns_ = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>( getDefaultSurfaceDesigns() );
        tableEnvironment_ = tableEnvironment;

        assert surfaceDesigns_.keySet().equals( getDefaultSurfaceDesigns().keySet() );
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
     * Creates a new component from the specified memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component; must not
     *        be {@code null}.
     * @param memento
     *        The memento representing the initial component state; must not be
     *        {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Component fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        if( Card.isMemento( memento ) )
        {
            return Card.fromMemento( tableEnvironment, memento );
        }
        else if( CardPile.isMemento( memento ) )
        {
            return CardPile.fromMemento( tableEnvironment, memento );
        }

        throw new MementoException( NonNlsMessages.Component_fromMemento_unknown );
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
            return container_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the default component orientation.
     * 
     * @return The default component orientation; never {@code null}.
     */
    /* @NonNull */
    abstract ComponentOrientation getDefaultOrientation();

    /**
     * Gets the default collection of component surface designs.
     * 
     * @return The default collection of component surface designs; never
     *         {@code null}. The keys in the returned collection must be
     *         identical to the keys in the collection returned from
     *         {@link #getSupportedOrientations}.
     */
    /* @NonNull */
    abstract Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns();

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
     * @see org.gamegineer.table.core.IComponent#getPath()
     */
    @Override
    public ComponentPath getPath()
    {
        getLock().lock();
        try
        {
            if( container_ == null )
            {
                return null;
            }

            final ComponentPath parentPath = container_.getPath();
            if( parentPath == null )
            {
                return null;
            }

            return new ComponentPath( parentPath, container_.getComponentIndex( this ) );
        }
        finally
        {
            getLock().unlock();
        }
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
     * @see org.gamegineer.table.core.IComponent#getTable()
     */
    @Override
    public final ITable getTable()
    {
        getLock().lock();
        try
        {
            return getTableInternal();
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
     * Gets the table associated with this component.
     * 
     * @return The table associated with this component or {@code null} if this
     *         component is not associated with a table.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    Table getTableInternal()
    {
        assert getLock().isHeldByCurrentThread();

        return (container_ != null) ? container_.getTableInternal() : null;
    }

    /**
     * This implementation always returns {@code false}. Subclasses may override
     * and are not required to call the superclass implementation.
     * 
     * @see org.gamegineer.table.core.IComponent#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return false;
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

    /**
     * Sets the container that contains this component.
     * 
     * @param container
     *        The container that contains this component or {@code null} if this
     *        component is not contained in a container.
     */
    @GuardedBy( "getLock()" )
    final void setContainer(
        /* @Nullable */
        final Container container )
    {
        assert getLock().isHeldByCurrentThread();

        container_ = container;
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
}
