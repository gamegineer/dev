/*
 * Card.java
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
 * Created on Oct 11, 2009 at 9:53:36 PM.
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
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.ITable;

/**
 * Implementation of {@link org.gamegineer.table.core.ICard}.
 */
@ThreadSafe
final class Card
    extends Component
    implements ICard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the memento attribute that stores the design on the back of
     * the card.
     */
    private static final String BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME = "backDesign"; //$NON-NLS-1$

    /** The default card surface design. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Card.DEFAULT_SURFACE_DESIGN" ), 0, 0 ); //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the design on the face of
     * the card.
     */
    private static final String FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "faceDesign"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "location"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card orientation. */
    private static final String ORIENTATION_MEMENTO_ATTRIBUTE_NAME = "orientation"; //$NON-NLS-1$

    /** The collection of supported card orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardOrientation.values( CardOrientation.class ) ) );

    /** The design on the back of the card. */
    @GuardedBy( "getLock()" )
    private ComponentSurfaceDesign backDesign_;

    /** The design on the face of the card. */
    @GuardedBy( "getLock()" )
    private ComponentSurfaceDesign faceDesign_;

    /** The collection of component listeners. */
    private final CopyOnWriteArrayList<IComponentListener> listeners_;

    /** The card location in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point location_;

    /** The card orientation. */
    @GuardedBy( "getLock()" )
    private CardOrientation orientation_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Card} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the card; must not be
     *        {@code null}.
     */
    Card(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );

        backDesign_ = DEFAULT_SURFACE_DESIGN;
        faceDesign_ = DEFAULT_SURFACE_DESIGN;
        listeners_ = new CopyOnWriteArrayList<IComponentListener>();
        location_ = new Point( 0, 0 );
        orientation_ = CardOrientation.FACE;
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
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.Card_addComponentListener_listener_registered ); //$NON-NLS-1$
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
            memento.put( BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, backDesign_ );
            memento.put( FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, faceDesign_ );
            memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
            memento.put( ORIENTATION_MEMENTO_ATTRIBUTE_NAME, orientation_ );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Card_componentBoundsChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Card_componentOrientationChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Card_componentSurfaceDesignChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Creates a new instance of the {@code Card} class from the specified
     * memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new card; must not be
     *        {@code null}.
     * @param memento
     *        The memento representing the initial card state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Card} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Card fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final Card card = new Card( tableEnvironment );

        final ComponentSurfaceDesign backDesign = MementoUtils.getAttribute( memento, BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        card.setSurfaceDesign( CardOrientation.BACK, backDesign );

        final ComponentSurfaceDesign faceDesign = MementoUtils.getAttribute( memento, FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        card.setSurfaceDesign( CardOrientation.FACE, faceDesign );

        final Point location = MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        card.setLocation( location );

        final CardOrientation orientation = MementoUtils.getAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME, CardOrientation.class );
        card.setOrientation( orientation );

        return card;
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
            final Container container = getContainer();
            if( container == null )
            {
                return null;
            }

            final ComponentPath parentPath = container.getPath();
            if( parentPath == null )
            {
                return null;
            }

            return new ComponentPath( parentPath, container.getComponentIndex( this ) );
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
            if( orientation_ == CardOrientation.BACK )
            {
                return backDesign_.getSize();
            }
            else if( orientation_ == CardOrientation.FACE )
            {
                return faceDesign_.getSize();
            }
            else
            {
                throw new AssertionError( "unknown card orientation" ); //$NON-NLS-1$
            }
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
        assertArgumentLegal( orientation instanceof CardOrientation, "orientation", NonNlsMessages.Card_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == CardOrientation.BACK )
            {
                return backDesign_;
            }
            else if( orientation == CardOrientation.FACE )
            {
                return faceDesign_;
            }

            throw new AssertionError( "unknown card orientation" ); //$NON-NLS-1$
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
        final Container container = getContainer();
        return (container != null) ? container.getTable() : null;
    }

    /**
     * Indicates the specified memento is a {@code Card} memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return {@code true} if the specified memento is a {@code Card} memento;
     *         otherwise {@code false}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    static boolean isMemento(
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert memento != null;

        return MementoUtils.hasAttribute( memento, BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#removeComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void removeComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.Card_removeComponentListener_listener_notRegistered ); //$NON-NLS-1$
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

        getTableEnvironment().addEventNotification( new Runnable()
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
            final Card card = fromMemento( getTableEnvironment(), memento );

            setSurfaceDesign( CardOrientation.BACK, card.getSurfaceDesign( CardOrientation.BACK ) );
            setSurfaceDesign( CardOrientation.FACE, card.getSurfaceDesign( CardOrientation.FACE ) );
            setLocation( card.getLocation() );
            setOrientation( card.getOrientation() );
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
        assertArgumentLegal( orientation instanceof CardOrientation, "orientation", NonNlsMessages.Card_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            orientation_ = (CardOrientation)orientation;
        }
        finally
        {
            getLock().unlock();
        }

        getTableEnvironment().addEventNotification( new Runnable()
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
        assertArgumentLegal( orientation instanceof CardOrientation, "orientation", NonNlsMessages.Card_orientation_illegal ); //$NON-NLS-1$
        assertArgumentNotNull( surfaceDesign, "surfaceDesign" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == CardOrientation.BACK )
            {
                backDesign_ = surfaceDesign;
            }
            else if( orientation == CardOrientation.FACE )
            {
                faceDesign_ = surfaceDesign;
            }
            else
            {
                throw new AssertionError( "unknown card orientation" ); //$NON-NLS-1$
            }
        }
        finally
        {
            getLock().unlock();
        }

        // TODO: replace with addEventNotification method in base class
        getTableEnvironment().addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentSurfaceDesignChanged();
            }
        } );
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Card[" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            sb.append( "backDesign_=" ); //$NON-NLS-1$
            sb.append( backDesign_ );
            sb.append( ", faceDesign_=" ); //$NON-NLS-1$
            sb.append( faceDesign_ );
            sb.append( ", location_=" ); //$NON-NLS-1$
            sb.append( location_ );
            sb.append( ", orientation_=" ); //$NON-NLS-1$
            sb.append( orientation_ );
        }
        finally
        {
            getLock().unlock();
        }

        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }
}
