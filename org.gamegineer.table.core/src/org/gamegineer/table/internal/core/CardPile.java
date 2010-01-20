/*
 * CardPile.java
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
 * Created on Jan 14, 2010 at 11:11:09 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileDesign;
import org.gamegineer.table.core.ICardPileListener;

/**
 * Implementation of {@link org.gamegineer.table.core.ICardPile}.
 */
@ThreadSafe
public final class CardPile
    implements ICardPile
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of cards in this card pile ordered from bottom to top. */
    @GuardedBy( "lock_ " )
    private final List<ICard> cards_;

    /** The design of the card pile base. */
    private final ICardPileDesign design_;

    /** The collection of card pile listeners. */
    private final CopyOnWriteArrayList<ICardPileListener> listeners_;

    /** The card pile location in table coordinates. */
    @GuardedBy( "lock_" )
    private final Point location_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPile} class.
     * 
     * @param design
     *        The design of the card pile base; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code design} is {@code null}.
     */
    public CardPile(
        /* @NonNull */
        final ICardPileDesign design )
    {
        assertArgumentNotNull( design, "design" ); //$NON-NLS-1$

        lock_ = new Object();
        cards_ = new ArrayList<ICard>();
        design_ = design;
        listeners_ = new CopyOnWriteArrayList<ICardPileListener>();
        location_ = new Point( 0, 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPile#addCard(org.gamegineer.table.core.ICard)
     */
    public void addCard(
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( !cards_.contains( card ) )
            {
                cards_.add( card );
            }
        }

        fireCardAdded( card );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#addCardPileListener(org.gamegineer.table.core.ICardPileListener)
     */
    public void addCardPileListener(
        final ICardPileListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.CardPile_addCardPileListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a card added event.
     * 
     * @param card
     *        The added card; must not be {@code null}.
     */
    private void fireCardAdded(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        final CardPileContentChangedEvent event = InternalCardPileContentChangedEvent.createCardPileContentChangedEvent( this, card );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPile_cardAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile bounds changed event.
     */
    private void fireCardPileBoundsChanged()
    {
        final CardPileEvent event = InternalCardPileEvent.createCardPileEvent( this );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardPileBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPile_cardPileBoundsChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card removed event.
     * 
     * @param card
     *        The removed card; must not be {@code null}.
     */
    private void fireCardRemoved(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        final CardPileContentChangedEvent event = InternalCardPileContentChangedEvent.createCardPileContentChangedEvent( this, card );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPile_cardRemoved_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBounds()
     */
    public Rectangle getBounds()
    {
        synchronized( lock_ )
        {
            return new Rectangle( location_, design_.getSize() );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getCards()
     */
    public List<ICard> getCards()
    {
        synchronized( lock_ )
        {
            return new ArrayList<ICard>( cards_ );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getDesign()
     */
    public ICardPileDesign getDesign()
    {
        return design_;
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getLocation()
     */
    public Point getLocation()
    {
        synchronized( lock_ )
        {
            return new Point( location_ );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getSize()
     */
    public Dimension getSize()
    {
        return design_.getSize();
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCard()
     */
    public ICard removeCard()
    {
        final ICard card;
        synchronized( lock_ )
        {
            if( cards_.isEmpty() )
            {
                card = null;
            }
            else
            {
                card = cards_.remove( cards_.size() - 1 );
            }
        }

        if( card != null )
        {
            fireCardRemoved( card );
        }

        return card;
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCardPileListener(org.gamegineer.table.core.ICardPileListener)
     */
    public void removeCardPileListener(
        final ICardPileListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.CardPile_removeCardPileListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#setLocation(java.awt.Point)
     */
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            location_.setLocation( location );
        }

        fireCardPileBoundsChanged();
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings( "boxing" )
    public String toString()
    {
        return String.format( "CardPile[cards_.size='%1$d', design_='%2$s', location_='%3$s'", cards_.size(), design_, getLocation() ); //$NON-NLS-1$
    }
}
