/*
 * Card.java
 * Copyright 2008-2011 Gamegineer.org
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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardSurfaceDesign;

/**
 * Implementation of {@link org.gamegineer.table.core.ICard}.
 */
@ThreadSafe
public final class Card
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
    @GuardedBy( "lock_" )
    private ICardSurfaceDesign backDesign_;

    /**
     * The card pile that contains this card or {@code null} if this card is not
     * contained in a card pile.
     */
    @GuardedBy( "lock_" )
    private ICardPile cardPile_;

    /** The design on the face of the card. */
    @GuardedBy( "lock_" )
    private ICardSurfaceDesign faceDesign_;

    /** The collection of card listeners. */
    private final CopyOnWriteArrayList<ICardListener> listeners_;

    /** The card location in table coordinates. */
    @GuardedBy( "lock_" )
    private final Point location_;

    /** The instance lock. */
    private final Object lock_;

    /** The card orientation. */
    @GuardedBy( "lock_" )
    private CardOrientation orientation_;

    /**
     * The collection of pending event notifications to be executed the next
     * time the instance lock is released.
     */
    private final Queue<Runnable> pendingEventNotifications_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Card} class.
     */
    private Card()
    {
        backDesign_ = null;
        cardPile_ = null;
        faceDesign_ = null;
        listeners_ = new CopyOnWriteArrayList<ICardListener>();
        location_ = new Point( 0, 0 );
        lock_ = new Object();
        orientation_ = CardOrientation.FACE_UP;
        pendingEventNotifications_ = new ConcurrentLinkedQueue<Runnable>();
    }

    /**
     * Initializes a new instance of the {@code Card} class.
     * 
     * @param backDesign
     *        The design on the back of the card; must not be {@code null}.
     * @param faceDesign
     *        The design on the face of the card; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code backDesign} and {@code faceDesign} do not have the same
     *         size.
     * @throws java.lang.NullPointerException
     *         If {@code backDesign} or {@code faceDesign} is {@code null}.
     */
    public Card(
        /* @NonNull */
        final ICardSurfaceDesign backDesign,
        /* @NonNull */
        final ICardSurfaceDesign faceDesign )
    {
        this();

        setSurfaceDesigns( backDesign, faceDesign );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICard#addCardListener(org.gamegineer.table.core.ICardListener)
     */
    @Override
    public void addCardListener(
        final ICardListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.Card_addCardListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
     */
    @Override
    public Object createMemento()
    {
        final Map<String, Object> memento = new HashMap<String, Object>();

        synchronized( lock_ )
        {
            memento.put( BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, backDesign_ );
            memento.put( FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, faceDesign_ );
            memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
            memento.put( ORIENTATION_MEMENTO_ATTRIBUTE_NAME, orientation_ );
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Fires a card location changed event.
     */
    private void fireCardLocationChanged()
    {
        assert !Thread.holdsLock( lock_ );

        final CardEvent event = new CardEvent( this );
        for( final ICardListener listener : listeners_ )
        {
            try
            {
                listener.cardLocationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Card_cardLocationChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card orientation changed event.
     */
    private void fireCardOrientationChanged()
    {
        assert !Thread.holdsLock( lock_ );

        final CardEvent event = new CardEvent( this );
        for( final ICardListener listener : listeners_ )
        {
            try
            {
                listener.cardOrientationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Card_cardOrientationChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card surface designs changed event.
     */
    private void fireCardSurfaceDesignsChanged()
    {
        assert !Thread.holdsLock( lock_ );

        final CardEvent event = new CardEvent( this );
        for( final ICardListener listener : listeners_ )
        {
            try
            {
                listener.cardSurfaceDesignsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Card_cardSurfaceDesignsChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires all pending event notifications.
     */
    private void firePendingEventNotifications()
    {
        assert !Thread.holdsLock( lock_ );

        Runnable notification = null;
        while( (notification = pendingEventNotifications_.poll()) != null )
        {
            notification.run();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#flip()
     */
    @Override
    public void flip()
    {
        synchronized( lock_ )
        {
            setOrientationInternal( orientation_.inverse() );
        }

        firePendingEventNotifications();
    }

    /**
     * Creates a new instance of the {@code Card} class from the specified
     * memento.
     * 
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
        final Object memento )
        throws MementoException
    {
        assert memento != null;

        final Card card = new Card();

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
        synchronized( lock_ )
        {
            return backDesign_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getBounds()
     */
    @Override
    public Rectangle getBounds()
    {
        synchronized( lock_ )
        {
            return new Rectangle( location_, backDesign_.getSize() );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getCardPile()
     */
    @Override
    public ICardPile getCardPile()
    {
        synchronized( lock_ )
        {
            return cardPile_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getFaceDesign()
     */
    @Override
    public ICardSurfaceDesign getFaceDesign()
    {
        synchronized( lock_ )
        {
            return faceDesign_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getLocation()
     */
    @Override
    public Point getLocation()
    {
        synchronized( lock_ )
        {
            return new Point( location_ );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getOrientation()
     */
    @Override
    public CardOrientation getOrientation()
    {
        synchronized( lock_ )
        {
            return orientation_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getSize()
     */
    @Override
    public Dimension getSize()
    {
        synchronized( lock_ )
        {
            return backDesign_.getSize();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#removeCardListener(org.gamegineer.table.core.ICardListener)
     */
    @Override
    public void removeCardListener(
        final ICardListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.Card_removeCardListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICard#setCardPile(org.gamegineer.table.core.ICardPile)
     */
    @Override
    public void setCardPile(
        final ICardPile cardPile )
    {
        synchronized( lock_ )
        {
            cardPile_ = cardPile;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#setLocation(java.awt.Point)
     */
    @Override
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            setLocationInternal( location );
        }

        firePendingEventNotifications();
    }

    /**
     * Sets the location of this card in table coordinates.
     * 
     * @param location
     *        The location of this card in table coordinates; must not be
     *        {@code null}.
     */
    @GuardedBy( "lock_" )
    private void setLocationInternal(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert Thread.holdsLock( lock_ );

        location_.setLocation( location );

        pendingEventNotifications_.offer( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireCardLocationChanged();
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

        final Card card = fromMemento( memento );

        synchronized( lock_ )
        {
            setSurfaceDesignsInternal( card.getBackDesign(), card.getFaceDesign() );
            setLocationInternal( card.getLocation() );
            setOrientationInternal( card.getOrientation() );
        }

        firePendingEventNotifications();
    }

    /*
     * @see org.gamegineer.table.core.ICard#setOrientation(org.gamegineer.table.core.CardOrientation)
     */
    @Override
    public void setOrientation(
        final CardOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            setOrientationInternal( orientation );
        }

        firePendingEventNotifications();
    }

    /**
     * Sets the orientation of this card.
     * 
     * @param orientation
     *        The orientation of this card; must not be {@code null}.
     */
    @GuardedBy( "lock_" )
    private void setOrientationInternal(
        /* @NonNull */
        final CardOrientation orientation )
    {
        assert orientation != null;
        assert Thread.holdsLock( lock_ );

        orientation_ = orientation;

        pendingEventNotifications_.offer( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireCardOrientationChanged();
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

        synchronized( lock_ )
        {
            setSurfaceDesignsInternal( backDesign, faceDesign );
        }

        firePendingEventNotifications();
    }

    /**
     * Sets the surface designs of this card.
     * 
     * @param backDesign
     *        The design on the back of the card; must not be {@code null}.
     * @param faceDesign
     *        The design on the face of the card; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code backDesign} and {@code faceDesign} do not have the same
     *         size.
     */
    @GuardedBy( "lock_" )
    private void setSurfaceDesignsInternal(
        /* @NonNull */
        final ICardSurfaceDesign backDesign,
        /* @NonNull */
        final ICardSurfaceDesign faceDesign )
    {
        assert backDesign != null;
        assert faceDesign != null;
        assertArgumentLegal( faceDesign.getSize().equals( backDesign.getSize() ), "faceDesign", Messages.Card_setSurfaceDesignsInternal_faceDesign_sizeNotEqual ); //$NON-NLS-1$
        assert Thread.holdsLock( lock_ );

        backDesign_ = backDesign;
        faceDesign_ = faceDesign;

        pendingEventNotifications_.offer( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireCardSurfaceDesignsChanged();
            }
        } );
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        synchronized( lock_ )
        {
            return String.format( "Card[backDesign_='%1$s', faceDesign_='%2$s', location_='%3$s', orientation_='%4$s'", backDesign_, faceDesign_, location_, orientation_ ); //$NON-NLS-1$
        }
    }
}
