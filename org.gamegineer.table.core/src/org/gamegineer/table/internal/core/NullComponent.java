/*
 * NullComponent.java
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
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
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;

/**
 * Null implementation of {@link org.gamegineer.table.core.IComponent}.
 */
@ThreadSafe
public final class NullComponent
    implements IComponent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default component surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.NullComponent.DEFAULT_SURFACE_DESIGN" ), 0, 0 ); //$NON-NLS-1$

    /** The name of the memento attribute that stores the component location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "location"; //$NON-NLS-1$

    /** The collection of supported component orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( Orientation.values( Orientation.class ) ) );

    /**
     * The name of the memento attribute that stores the component surface
     * design.
     */
    private static final String SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "surfaceDesign"; //$NON-NLS-1$

    /** The collection of component listeners. */
    private final CopyOnWriteArrayList<IComponentListener> listeners_;

    /** The component location in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point location_;

    /** The component surface design. */
    @GuardedBy( "getLock()" )
    private ComponentSurfaceDesign surfaceDesign_;

    /**
     * The table that contains this component or {@code null} if this component
     * is not contained in a table.
     */
    @GuardedBy( "getLock()" )
    private Table table_;

    /** The table environment associated with the component. */
    private final TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullComponent} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the component; must not be
     *        {@code null}.
     */
    NullComponent(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        assert tableEnvironment != null;

        listeners_ = new CopyOnWriteArrayList<IComponentListener>();
        location_ = new Point( 0, 0 );
        surfaceDesign_ = DEFAULT_SURFACE_DESIGN;
        table_ = null;
        tableEnvironment_ = tableEnvironment;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponent#addComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void addComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.NullComponent_addComponentListener_listener_registered ); //$NON-NLS-1$
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
            memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
            memento.put( SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, surfaceDesign_ );
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
    private void fireComponentBoundsChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : listeners_ )
        {
            try
            {
                listener.componentBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NullComponent_componentBoundsChanged_unexpectedException, e );
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
        for( final IComponentListener listener : listeners_ )
        {
            try
            {
                listener.componentOrientationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NullComponent_componentOrientationChanged_unexpectedException, e );
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
        for( final IComponentListener listener : listeners_ )
        {
            try
            {
                listener.componentSurfaceDesignChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NullComponent_componentSurfaceDesignChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Creates a new instance of the {@code NullComponent} class from the
     * specified memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component; must not
     *        be {@code null}.
     * @param memento
     *        The memento representing the initial component state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code NullComponent} class; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static NullComponent fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final NullComponent component = new NullComponent( tableEnvironment );

        final Point location = MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        component.setLocation( location );

        final ComponentSurfaceDesign surfaceDesign = MementoUtils.getAttribute( memento, SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        component.setSurfaceDesign( Orientation.DEFAULT, surfaceDesign );

        return component;
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
            return new Rectangle( location_, getSize() );
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
    public IContainer getContainer()
    {
        return null;
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

    /**
     * Gets the table environment lock.
     * 
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    private ReentrantLock getLock()
    {
        return tableEnvironment_.getLock();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrientation()
     */
    @Override
    public ComponentOrientation getOrientation()
    {
        return Orientation.DEFAULT;
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
        getLock().lock();
        try
        {
            return surfaceDesign_.getSize();
        }
        finally
        {
            getLock().unlock();
        }
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
        assertArgumentLegal( orientation instanceof Orientation, "orientation", NonNlsMessages.NullComponent_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == Orientation.DEFAULT )
            {
                return surfaceDesign_;
            }

            throw new AssertionError( "unknown null component orientation" ); //$NON-NLS-1$
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
    public ITable getTable()
    {
        getLock().lock();
        try
        {
            return table_;
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
    public ITableEnvironment getTableEnvironment()
    {
        return tableEnvironment_;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#removeComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void removeComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.NullComponent_removeComponentListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setLocation(java.awt.Point)
     */
    @Override
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            location_.setLocation( location );
        }
        finally
        {
            getLock().unlock();
        }

        tableEnvironment_.addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentBoundsChanged();
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
            final NullComponent component = fromMemento( tableEnvironment_, memento );

            setLocation( component.getLocation() );
            setSurfaceDesign( Orientation.DEFAULT, component.getSurfaceDesign( Orientation.DEFAULT ) );
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
        assertArgumentLegal( orientation instanceof Orientation, "orientation", NonNlsMessages.NullComponent_orientation_illegal ); //$NON-NLS-1$

        tableEnvironment_.addEventNotification( new Runnable()
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
        assertArgumentLegal( orientation instanceof Orientation, "orientation", NonNlsMessages.NullComponent_orientation_illegal ); //$NON-NLS-1$
        assertArgumentNotNull( surfaceDesign, "surfaceDesign" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == Orientation.DEFAULT )
            {
                surfaceDesign_ = surfaceDesign;
            }
            else
            {
                throw new AssertionError( "unknown null component orientation" ); //$NON-NLS-1$
            }
        }
        finally
        {
            getLock().unlock();
        }

        tableEnvironment_.addEventNotification( new Runnable()
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
     * Sets the table that contains this component.
     * 
     * @param table
     *        The table that contains this component or {@code null} if this
     *        component is not contained in a table.
     */
    void setTable(
        /* @Nullable */
        final Table table )
    {
        getLock().lock();
        try
        {
            table_ = table;
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
     * Enumerates the possible orientations of a {@code NullComponent}.
     */
    @Immutable
    private static final class Orientation
        extends ComponentOrientation
    {
        // ======================================================================
        // Fields
        // ======================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = -6305741270318957331L;

        /** The default orientation. */
        static final Orientation DEFAULT = new Orientation( "default", 0 ); //$NON-NLS-1$


        // ======================================================================
        // Constructors
        // ======================================================================

        /**
         * Initializes a new instance of the {@code Orientation} class.
         * 
         * @param name
         *        The name of the enum constant; must not be {@code null}.
         * @param ordinal
         *        The ordinal of the enum constant.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code ordinal} is negative.
         * @throws java.lang.NullPointerException
         *         If {@code name} is {@code null}.
         */
        private Orientation(
            /* @NonNull */
            final String name,
            final int ordinal )
        {
            super( name, ordinal );
        }


        // ======================================================================
        // Methods
        // ======================================================================

        /*
         * @see org.gamegineer.table.core.ComponentOrientation#inverse()
         */
        @Override
        public ComponentOrientation inverse()
        {
            if( this == DEFAULT )
            {
                return DEFAULT;
            }

            throw new AssertionError( String.format( "unknown null component orientation (%s)", name() ) ); //$NON-NLS-1$
        }
    }
}
