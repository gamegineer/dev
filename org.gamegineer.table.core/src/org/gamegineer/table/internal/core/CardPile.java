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
import org.gamegineer.table.core.ICardPileBaseDesign;
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

    /** The number of cards per stack level. */
    private static final int CARDS_PER_STACK_LEVEL = 10;

    /** The offset of each stack level in table coordinates. */
    private static final Point STACK_LEVEL_OFFSET = new Point( 2, 1 );

    /** The design of the card pile base. */
    private final ICardPileBaseDesign baseDesign_;

    /** The collection of cards in this card pile ordered from bottom to top. */
    @GuardedBy( "lock_ " )
    private final List<ICard> cards_;

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
     * @param baseDesign
     *        The design of the card pile base; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code baseDesign} is {@code null}.
     */
    public CardPile(
        /* @NonNull */
        final ICardPileBaseDesign baseDesign )
    {
        assertArgumentNotNull( baseDesign, "baseDesign" ); //$NON-NLS-1$

        lock_ = new Object();
        baseDesign_ = baseDesign;
        cards_ = new ArrayList<ICard>();
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

        final boolean cardAdded;
        final boolean cardPileBoundsChanged;
        synchronized( lock_ )
        {
            if( cards_.contains( card ) )
            {
                cardAdded = false;
                cardPileBoundsChanged = false;
            }
            else
            {
                cardAdded = true;
                final Rectangle oldBounds = getBounds();
                final Point cardLocation = new Point( location_ );
                final Dimension cardOffset = getCardOffset( cards_.size() );
                cardLocation.translate( cardOffset.width, cardOffset.height );
                card.setLocation( cardLocation );
                cards_.add( card );
                final Rectangle newBounds = getBounds();
                cardPileBoundsChanged = !newBounds.equals( oldBounds );
            }
        }

        if( cardAdded )
        {
            fireCardAdded( card );
        }

        if( cardPileBoundsChanged )
        {
            fireCardPileBoundsChanged();
        }
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
     * @see org.gamegineer.table.core.ICardPile#getBaseDesign()
     */
    public ICardPileBaseDesign getBaseDesign()
    {
        return baseDesign_;
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBounds()
     */
    public Rectangle getBounds()
    {
        synchronized( lock_ )
        {
            final Dimension size;
            if( cards_.isEmpty() )
            {
                size = baseDesign_.getSize();
            }
            else
            {
                final ICard topCard = cards_.get( cards_.size() - 1 );
                final Point topCardLocation = topCard.getLocation();
                size = topCard.getSize();
                size.width += (topCardLocation.x - location_.x);
                size.height += (topCardLocation.y - location_.y);
            }

            return new Rectangle( location_, size );
        }
    }

    /**
     * Gets the offset from the card pile base origin in table coordinates of
     * the card at the specified index.
     * 
     * @param index
     *        The card index; must be non-negative.
     * 
     * @return The offset from the card pile base origin in table coordinates of
     *         the card at the specified index; never {@code null}.
     */
    /* @NonNull */
    private static Dimension getCardOffset(
        final int index )
    {
        assert index >= 0;

        final int stackLevel = index / CARDS_PER_STACK_LEVEL;
        return new Dimension( STACK_LEVEL_OFFSET.x * stackLevel, STACK_LEVEL_OFFSET.y * stackLevel );
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
        return baseDesign_.getSize();
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCard()
     */
    public ICard removeCard()
    {
        final ICard card;
        final boolean cardPileBoundsChanged;
        synchronized( lock_ )
        {
            if( cards_.isEmpty() )
            {
                card = null;
                cardPileBoundsChanged = false;
            }
            else
            {
                final Rectangle oldBounds = getBounds();
                card = cards_.remove( cards_.size() - 1 );
                final Rectangle newBounds = getBounds();
                cardPileBoundsChanged = !newBounds.equals( oldBounds );
            }
        }

        if( card != null )
        {
            fireCardRemoved( card );
        }

        if( cardPileBoundsChanged )
        {
            fireCardPileBoundsChanged();
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

            for( int index = 0, size = cards_.size(); index < size; ++index )
            {
                final Point cardLocation = new Point( location );
                final Dimension cardOffset = getCardOffset( index );
                cardLocation.translate( cardOffset.width, cardOffset.height );
                cards_.get( index ).setLocation( cardLocation );
            }
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
        return String.format( "CardPile[baseDesign_='%1$s', cards_.size='%2$d', location_='%3$s'", baseDesign_, cards_.size(), getLocation() ); //$NON-NLS-1$
    }
}
