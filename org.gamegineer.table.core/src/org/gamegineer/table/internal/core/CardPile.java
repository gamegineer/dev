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
import org.gamegineer.table.core.CardPileLayout;
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

    /** The offset of each accordian level in table coordinates. */
    private static final Dimension ACCORDIAN_LEVEL_OFFSET = new Dimension( 16, 18 );

    /** The number of cards per stack level. */
    private static final int CARDS_PER_STACK_LEVEL = 10;

    /** The offset of each stack level in table coordinates. */
    private static final Dimension STACK_LEVEL_OFFSET = new Dimension( 2, 1 );

    /** The design of the card pile base. */
    private final ICardPileBaseDesign baseDesign_;

    /** The collection of cards in this card pile ordered from bottom to top. */
    @GuardedBy( "lock_ " )
    private final List<ICard> cards_;

    /** The card pile layout. */
    @GuardedBy( "lock_" )
    private CardPileLayout layout_;

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
        layout_ = CardPileLayout.STACKED;
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
                final Rectangle oldBounds = getBounds();

                // Calculate new card location and add it to collection
                final Point cardLocation = getBaseLocation();
                final Dimension cardOffset = getCardOffsetAt( cards_.size() );
                cardLocation.translate( cardOffset.width, cardOffset.height );
                card.setLocation( cardLocation );
                cards_.add( card );
                cardAdded = true;

                // Recalculate card pile location
                final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
                final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
                location_.setLocation( topCardBounds.union( bottomCardBounds ).getLocation() );

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

    /**
     * Gets the location of the card pile base in table coordinates.
     * 
     * <p>
     * This method must be called while {@code lock_} is held.
     * </p>
     * 
     * @return The location of the card pile base in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    private Point getBaseLocation()
    {
        assert Thread.holdsLock( lock_ );

        return cards_.isEmpty() ? new Point( location_ ) : cards_.get( 0 ).getLocation();
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBounds()
     */
    public Rectangle getBounds()
    {
        synchronized( lock_ )
        {
            if( cards_.isEmpty() )
            {
                return new Rectangle( location_, baseDesign_.getSize() );
            }

            final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
            final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
            return topCardBounds.union( bottomCardBounds );
        }
    }

    /**
     * Gets the offset from the card pile base origin in table coordinates of
     * the card at the specified index.
     * 
     * <p>
     * This method must be called while {@code lock_} is held.
     * </p>
     * 
     * @param index
     *        The card index; must be non-negative.
     * 
     * @return The offset from the card pile base origin in table coordinates of
     *         the card at the specified index; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If an unknown layout is active.
     */
    /* @NonNull */
    private Dimension getCardOffsetAt(
        final int index )
    {
        assert Thread.holdsLock( lock_ );
        assert index >= 0;

        switch( layout_ )
        {
            case STACKED:
                final int stackLevel = index / CARDS_PER_STACK_LEVEL;
                return new Dimension( STACK_LEVEL_OFFSET.width * stackLevel, STACK_LEVEL_OFFSET.height * stackLevel );

            case ACCORDIAN_UP:
                return new Dimension( 0, -ACCORDIAN_LEVEL_OFFSET.height * index );

            case ACCORDIAN_DOWN:
                return new Dimension( 0, ACCORDIAN_LEVEL_OFFSET.height * index );

            case ACCORDIAN_LEFT:
                return new Dimension( -ACCORDIAN_LEVEL_OFFSET.width * index, 0 );

            case ACCORDIAN_RIGHT:
                return new Dimension( ACCORDIAN_LEVEL_OFFSET.width * index, 0 );
        }

        throw new IllegalStateException( Messages.CardPile_getCardOffsetAt_unknownLayout );
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
     * @see org.gamegineer.table.core.ICardPile#getLayout()
     */
    public CardPileLayout getLayout()
    {
        synchronized( lock_ )
        {
            return layout_;
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

                // Remove card from collection
                card = cards_.remove( cards_.size() - 1 );

                // Recalculate card pile location
                if( cards_.isEmpty() )
                {
                    location_.setLocation( card.getLocation() );
                }
                else
                {
                    final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
                    final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
                    location_.setLocation( topCardBounds.union( bottomCardBounds ).getLocation() );
                }

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
     * @see org.gamegineer.table.core.ICardPile#setLayout(org.gamegineer.table.core.CardPileLayout)
     */
    public void setLayout(
        final CardPileLayout layout )
    {
        assertArgumentNotNull( layout, "layout" ); //$NON-NLS-1$

        final boolean cardPileBoundsChanged;
        synchronized( lock_ )
        {
            layout_ = layout;

            if( cards_.isEmpty() )
            {
                cardPileBoundsChanged = false;
            }
            else
            {
                final Rectangle oldBounds = getBounds();

                // Recalculate card locations
                final Point baseLocation = getBaseLocation();
                final Point cardLocation = new Point();
                for( int index = 0, size = cards_.size(); index < size; ++index )
                {
                    cardLocation.setLocation( baseLocation );
                    final Dimension cardOffset = getCardOffsetAt( index );
                    cardLocation.translate( cardOffset.width, cardOffset.height );
                    cards_.get( index ).setLocation( cardLocation );
                }

                // Recalculate card pile location
                final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
                final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
                location_.setLocation( topCardBounds.union( bottomCardBounds ).getLocation() );

                final Rectangle newBounds = getBounds();
                cardPileBoundsChanged = !newBounds.equals( oldBounds );
            }
        }

        if( cardPileBoundsChanged )
        {
            fireCardPileBoundsChanged();
        }
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
            final Dimension offset = new Dimension( location.x - location_.x, location.y - location_.y );
            location_.setLocation( location );
            for( final ICard card : cards_ )
            {
                final Point cardLocation = card.getLocation();
                cardLocation.translate( offset.width, offset.height );
                card.setLocation( cardLocation );
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
        synchronized( lock_ )
        {
            return String.format( "CardPile[baseDesign_='%1$s', cards_.size='%2$d', location_='%3$s', layout_='%4$s'", baseDesign_, cards_.size(), location_, layout_ ); //$NON-NLS-1$
        }
    }
}
