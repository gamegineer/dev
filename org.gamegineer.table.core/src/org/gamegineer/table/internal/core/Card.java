/*
 * Card.java
 * Copyright 2008-2010 Gamegineer.org
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;
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

    /** The design on the back of the card. */
    private final ICardSurfaceDesign backDesign_;

    /** The design on the face of the card. */
    private final ICardSurfaceDesign faceDesign_;

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


    // ======================================================================
    // Constructors
    // ======================================================================

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
        assertArgumentNotNull( backDesign, "backDesign" ); //$NON-NLS-1$
        assertArgumentNotNull( faceDesign, "faceDesign" ); //$NON-NLS-1$
        assertArgumentLegal( faceDesign.getSize().equals( backDesign.getSize() ), "faceDesign", Messages.Card_ctor_faceDesign_sizeNotEqual ); //$NON-NLS-1$

        lock_ = new Object();
        backDesign_ = backDesign;
        faceDesign_ = faceDesign;
        listeners_ = new CopyOnWriteArrayList<ICardListener>();
        location_ = new Point( 0, 0 );
        orientation_ = CardOrientation.FACE_UP;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICard#addCardListener(org.gamegineer.table.core.ICardListener)
     */
    public void addCardListener(
        final ICardListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.Card_addCardListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a card location changed event.
     */
    private void fireCardLocationChanged()
    {
        final CardEvent event = InternalCardEvent.createCardEvent( this );
        for( final ICardListener listener : listeners_ )
        {
            try
            {
                listener.cardLocationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.Card_cardLocationChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card orientation changed event.
     */
    private void fireCardOrientationChanged()
    {
        final CardEvent event = InternalCardEvent.createCardEvent( this );
        for( final ICardListener listener : listeners_ )
        {
            try
            {
                listener.cardOrientationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.Card_cardOrientationChanged_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#flip()
     */
    public void flip()
    {
        synchronized( lock_ )
        {
            orientation_ = orientation_.inverse();
        }

        fireCardOrientationChanged();
    }

    /*
     * @see org.gamegineer.table.core.ICard#getBackDesign()
     */
    public ICardSurfaceDesign getBackDesign()
    {
        return backDesign_;
    }

    /*
     * @see org.gamegineer.table.core.ICard#getBounds()
     */
    public Rectangle getBounds()
    {
        synchronized( lock_ )
        {
            return new Rectangle( location_, backDesign_.getSize() );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getFaceDesign()
     */
    public ICardSurfaceDesign getFaceDesign()
    {
        return faceDesign_;
    }

    /*
     * @see org.gamegineer.table.core.ICard#getLocation()
     */
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
    public Dimension getSize()
    {
        return backDesign_.getSize();
    }

    /*
     * @see org.gamegineer.table.core.ICard#removeCardListener(org.gamegineer.table.core.ICardListener)
     */
    public void removeCardListener(
        final ICardListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.Card_removeCardListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICard#setLocation(java.awt.Point)
     */
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            location_.setLocation( location );
        }

        fireCardLocationChanged();
    }

    /*
     * @see org.gamegineer.table.core.ICard#setOrientation(org.gamegineer.table.core.CardOrientation)
     */
    public void setOrientation(
        final CardOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            orientation_ = orientation;
        }

        fireCardOrientationChanged();
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
