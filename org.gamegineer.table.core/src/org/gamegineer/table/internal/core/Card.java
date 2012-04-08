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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainer;

/**
 * Implementation of {@link org.gamegineer.table.core.ICard}.
 */
@ThreadSafe
final class Card
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
    private static final CardSurfaceDesign DEFAULT_SURFACE_DESIGN = new CardSurfaceDesign( CardSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Card.DEFAULT_SURFACE_DESIGN" ), 0, 0 ); //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the design on the face of
     * the card.
     */
    private static final String FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "faceDesign"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "location"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card orientation. */
    private static final String ORIENTATION_MEMENTO_ATTRIBUTE_NAME = "orientation"; //$NON-NLS-1$

    /** The design on the back of the card. */
    @GuardedBy( "getLock()" )
    private ICardSurfaceDesign backDesign_;

    /**
     * The card pile that contains this card or {@code null} if this card is not
     * contained in a card pile.
     */
    @GuardedBy( "getLock()" )
    private CardPile cardPile_;

    /** The design on the face of the card. */
    @GuardedBy( "getLock()" )
    private ICardSurfaceDesign faceDesign_;

    /** The collection of component listeners. */
    private final CopyOnWriteArrayList<IComponentListener> listeners_;

    /** The card location in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point location_;

    /** The card orientation. */
    @GuardedBy( "getLock()" )
    private CardOrientation orientation_;

    /** The table context associated with the card. */
    private final TableContext tableContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Card} class.
     * 
     * @param tableContext
     *        The table context associated with the card; must not be
     *        {@code null}.
     */
    Card(
        /* @NonNull */
        final TableContext tableContext )
    {
        assert tableContext != null;

        backDesign_ = DEFAULT_SURFACE_DESIGN;
        cardPile_ = null;
        faceDesign_ = DEFAULT_SURFACE_DESIGN;
        listeners_ = new CopyOnWriteArrayList<IComponentListener>();
        location_ = new Point( 0, 0 );
        orientation_ = CardOrientation.FACE_UP;
        tableContext_ = tableContext;
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

    /*
     * @see org.gamegineer.table.core.ICard#flip()
     */
    @Override
    public void flip()
    {
        getLock().lock();
        try
        {
            setOrientation( orientation_.inverse() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Creates a new instance of the {@code Card} class from the specified
     * memento.
     * 
     * @param tableContext
     *        The table context associated with the new card; must not be
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
        final TableContext tableContext,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableContext != null;
        assert memento != null;

        final Card card = new Card( tableContext );

        final ICardSurfaceDesign backDesign = MementoUtils.getAttribute( memento, BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, ICardSurfaceDesign.class );
        final ICardSurfaceDesign faceDesign = MementoUtils.getAttribute( memento, FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ICardSurfaceDesign.class );
        try
        {
            card.setSurfaceDesigns( backDesign, faceDesign );
        }
        catch( final IllegalArgumentException e )
        {
            throw new MementoException( e );
        }

        final Point location = MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        card.setLocation( location );

        final CardOrientation orientation = MementoUtils.getAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME, CardOrientation.class );
        card.setOrientation( orientation );

        return card;
    }

    /*
     * @see org.gamegineer.table.core.ICard#getBackDesign()
     */
    @Override
    public ICardSurfaceDesign getBackDesign()
    {
        getLock().lock();
        try
        {
            return backDesign_;
        }
        finally
        {
            getLock().unlock();
        }
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
            return new Rectangle( location_, backDesign_.getSize() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getCardPile()
     */
    @Override
    public ICardPile getCardPile()
    {
        getLock().lock();
        try
        {
            return cardPile_;
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
        return getCardPile();
    }

    /*
     * @see org.gamegineer.table.core.ICard#getFaceDesign()
     */
    @Override
    public ICardSurfaceDesign getFaceDesign()
    {
        getLock().lock();
        try
        {
            return faceDesign_;
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

    /**
     * Gets the table lock.
     * 
     * @return The table lock; never {@code null}.
     */
    /* @NonNull */
    private ReentrantLock getLock()
    {
        return tableContext_.getLock();
    }

    /*
     * @see org.gamegineer.table.core.ICard#getOrientation()
     */
    @Override
    public CardOrientation getOrientation()
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
     * @see org.gamegineer.table.core.IComponent#getSize()
     */
    @Override
    public Dimension getSize()
    {
        getLock().lock();
        try
        {
            return backDesign_.getSize();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the table context associated with the card.
     * 
     * @return The table context associated with the card; never {@code null}.
     */
    /* @NonNull */
    TableContext getTableContext()
    {
        return tableContext_;
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

    /**
     * Sets the card pile that contains this card.
     * 
     * @param cardPile
     *        The card pile that contains this card or {@code null} if this card
     *        is not contained in a card pile.
     */
    void setCardPile(
        /* @Nullable */
        final CardPile cardPile )
    {
        getLock().lock();
        try
        {
            cardPile_ = cardPile;
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

        tableContext_.addEventNotification( new Runnable()
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
            final Card card = fromMemento( tableContext_, memento );

            setSurfaceDesigns( card.getBackDesign(), card.getFaceDesign() );
            setLocation( card.getLocation() );
            setOrientation( card.getOrientation() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#setOrientation(org.gamegineer.table.core.CardOrientation)
     */
    @Override
    public void setOrientation(
        final CardOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            orientation_ = orientation;
        }
        finally
        {
            getLock().unlock();
        }

        tableContext_.addEventNotification( new Runnable()
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
     * @see org.gamegineer.table.core.ICard#setSurfaceDesigns(org.gamegineer.table.core.ICardSurfaceDesign, org.gamegineer.table.core.ICardSurfaceDesign)
     */
    @Override
    public void setSurfaceDesigns(
        final ICardSurfaceDesign backDesign,
        final ICardSurfaceDesign faceDesign )
    {
        assertArgumentNotNull( backDesign, "backDesign" ); //$NON-NLS-1$
        assertArgumentNotNull( faceDesign, "faceDesign" ); //$NON-NLS-1$
        assertArgumentLegal( faceDesign.getSize().equals( backDesign.getSize() ), "faceDesign", NonNlsMessages.Card_setSurfaceDesigns_faceDesign_sizeNotEqual ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            backDesign_ = backDesign;
            faceDesign_ = faceDesign;
        }
        finally
        {
            getLock().unlock();
        }

        tableContext_.addEventNotification( new Runnable()
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
