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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.IMemento;
import org.gamegineer.common.core.util.memento.MalformedMementoException;
import org.gamegineer.common.core.util.memento.MementoBuilder;
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
    @Override
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

    /*
     * @see org.gamegineer.table.core.ICard#flip()
     */
    @Override
    public void flip()
    {
        synchronized( lock_ )
        {
            orientation_ = orientation_.inverse();
        }

        fireCardOrientationChanged();
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
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.common.core.util.memento.MalformedMementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    public static Card fromMemento(
        /* @NonNull */
        final IMemento memento )
        throws MalformedMementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        final ICardSurfaceDesign backDesign = MementoUtils.getRequiredAttribute( memento, BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, ICardSurfaceDesign.class );
        final ICardSurfaceDesign faceDesign = MementoUtils.getRequiredAttribute( memento, FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ICardSurfaceDesign.class );
        final Card card;
        try
        {
            card = new Card( backDesign, faceDesign );
        }
        catch( final IllegalArgumentException e )
        {
            throw new MalformedMementoException( FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, e );
        }

        final Point location = MementoUtils.getOptionalAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        if( location != null )
        {
            card.setLocation( location );
        }

        final CardOrientation orientation = MementoUtils.getOptionalAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME, CardOrientation.class );
        if( orientation != null )
        {
            card.setOrientation( orientation );
        }

        return card;
    }

    /*
     * @see org.gamegineer.table.core.ICard#getBackDesign()
     */
    @Override
    public ICardSurfaceDesign getBackDesign()
    {
        return backDesign_;
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
     * @see org.gamegineer.table.core.ICard#getFaceDesign()
     */
    @Override
    public ICardSurfaceDesign getFaceDesign()
    {
        return faceDesign_;
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
     * @see org.gamegineer.table.core.ICard#getMemento()
     */
    @Override
    public IMemento getMemento()
    {
        final MementoBuilder mementoBuilder = new MementoBuilder();

        synchronized( lock_ )
        {
            mementoBuilder.addAttribute( BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, backDesign_ );
            mementoBuilder.addAttribute( FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, faceDesign_ );
            mementoBuilder.addAttribute( LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( location_ ) );
            mementoBuilder.addAttribute( ORIENTATION_MEMENTO_ATTRIBUTE_NAME, orientation_ );
        }

        return mementoBuilder.toMemento();
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
        return backDesign_.getSize();
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
     * @see org.gamegineer.table.core.ICard#setLocation(java.awt.Point)
     */
    @Override
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
    @Override
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
