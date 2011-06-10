/*
 * CardPile.java
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
 * Created on Jan 14, 2010 at 11:11:09 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.gamegineer.common.core.util.memento.MalformedMementoException;
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
    implements ICardPile, IMementoOriginator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The offset of each accordian level in table coordinates. */
    private static final Dimension ACCORDIAN_LEVEL_OFFSET = new Dimension( 16, 18 );

    /**
     * The name of the memento attribute that stores the design of the card pile
     * base.
     */
    private static final String BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "baseDesign"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the location of the card
     * pile base.
     */
    private static final String BASE_LOCATION_MEMENTO_ATTRIBUTE_NAME = "baseLocation"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the collection of cards in
     * the card pile.
     */
    private static final String CARDS_MEMENTO_ATTRIBUTE_NAME = "cards"; //$NON-NLS-1$

    /** The number of cards per stack level. */
    private static final int CARDS_PER_STACK_LEVEL = 10;

    /**
     * The name of the memento attribute that stores the card pile layout.
     */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "layout"; //$NON-NLS-1$

    /** The offset of each stack level in table coordinates. */
    private static final Dimension STACK_LEVEL_OFFSET = new Dimension( 2, 1 );

    /** The design of the card pile base. */
    @GuardedBy( "lock_" )
    private ICardPileBaseDesign baseDesign_;

    /** The location of the card pile base in table coordinates. */
    @GuardedBy( "lock_" )
    private final Point baseLocation_;

    /** The collection of cards in this card pile ordered from bottom to top. */
    @GuardedBy( "lock_ " )
    private final List<ICard> cards_;

    /** The card pile layout. */
    @GuardedBy( "lock_" )
    private CardPileLayout layout_;

    /** The collection of card pile listeners. */
    private final CopyOnWriteArrayList<ICardPileListener> listeners_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPile} class.
     */
    private CardPile()
    {
        baseDesign_ = null;
        baseLocation_ = new Point( 0, 0 );
        cards_ = new ArrayList<ICard>();
        layout_ = CardPileLayout.STACKED;
        listeners_ = new CopyOnWriteArrayList<ICardPileListener>();
        lock_ = new Object();
    }

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
        this();

        setBaseDesign( baseDesign );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPile#addCard(org.gamegineer.table.core.ICard)
     */
    @Override
    public void addCard(
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        addCards( Collections.singletonList( card ) );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#addCardPileListener(org.gamegineer.table.core.ICardPileListener)
     */
    @Override
    public void addCardPileListener(
        final ICardPileListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.CardPile_addCardPileListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#addCards(java.util.List)
     */
    @Override
    public void addCards(
        final List<ICard> cards )
    {
        assertArgumentNotNull( cards, "cards" ); //$NON-NLS-1$
        assertArgumentLegal( !cards.contains( null ), "cards", Messages.CardPile_addCards_cards_containsNullElement ); //$NON-NLS-1$

        final List<ICard> addedCards = new ArrayList<ICard>();
        final boolean cardPileBoundsChanged;
        synchronized( lock_ )
        {
            final Rectangle oldBounds = getBounds();

            for( final ICard card : cards )
            {
                if( !cards_.contains( card ) )
                {
                    final Point cardLocation = new Point( baseLocation_ );
                    final Dimension cardOffset = getCardOffsetAt( cards_.size() );
                    cardLocation.translate( cardOffset.width, cardOffset.height );
                    card.setLocation( cardLocation );
                    cards_.add( card );
                    addedCards.add( card );
                }
            }

            final Rectangle newBounds = getBounds();
            cardPileBoundsChanged = !newBounds.equals( oldBounds );
        }

        for( final ICard card : addedCards )
        {
            fireCardAdded( card );
        }

        if( cardPileBoundsChanged )
        {
            fireCardPileBoundsChanged();
        }
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
            memento.put( BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, baseDesign_ );
            memento.put( BASE_LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( baseLocation_ ) );
            memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, layout_ );

            final List<Object> cardMementos = new ArrayList<Object>( cards_.size() );
            for( final ICard card : cards_ )
            {
                if( card instanceof IMementoOriginator )
                {
                    cardMementos.add( ((IMementoOriginator)card).createMemento() );
                }
                else
                {
                    // TODO: should createMemento throw a checked exception instead?
                    Loggers.getDefaultLogger().warning( Messages.CardPile_createMemento_cardNotMementoOriginator( card.getClass() ) );
                }
            }
            memento.put( CARDS_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( cardMementos ) );
        }

        return Collections.unmodifiableMap( memento );
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

        final CardPileContentChangedEvent event = new CardPileContentChangedEvent( this, card );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardPile_cardAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile bounds changed event.
     */
    private void fireCardPileBoundsChanged()
    {
        final CardPileEvent event = new CardPileEvent( this );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardPileBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardPile_cardPileBoundsChanged_unexpectedException, e );
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

        final CardPileContentChangedEvent event = new CardPileContentChangedEvent( this, card );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardPile_cardRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Creates a new instance of the {@code CardPile} class from the specified
     * memento.
     * 
     * @param memento
     *        The memento representing the initial card pile state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code CardPile} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MalformedMementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static CardPile fromMemento(
        /* @NonNull */
        final Object memento )
        throws MalformedMementoException
    {
        assert memento != null;

        final CardPile cardPile = new CardPile();
        cardPile.setMemento( memento );
        return cardPile;
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBaseDesign()
     */
    @Override
    public ICardPileBaseDesign getBaseDesign()
    {
        synchronized( lock_ )
        {
            return baseDesign_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBaseLocation()
     */
    @Override
    public Point getBaseLocation()
    {
        synchronized( lock_ )
        {
            return new Point( baseLocation_ );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBounds()
     */
    @Override
    public Rectangle getBounds()
    {
        synchronized( lock_ )
        {
            if( cards_.isEmpty() )
            {
                return new Rectangle( baseLocation_, baseDesign_.getSize() );
            }

            final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
            final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
            return topCardBounds.union( bottomCardBounds );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getCard(java.awt.Point)
     */
    @Override
    public ICard getCard(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final int index = getCardIndex( location );
            return (index != -1) ? cards_.get( index ) : null;
        }
    }

    /**
     * Gets the index of the card in this card pile at the specified location.
     * 
     * <p>
     * This method follows the same rules defined by {@link #getCard(Point)}.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The index of the card in this card pile at the specified location
     *         or -1 if no card in this card pile is at that location.
     */
    @GuardedBy( "lock_" )
    private int getCardIndex(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert Thread.holdsLock( lock_ );

        final int upperIndex = cards_.size() - 1;
        final int lowerIndex = Math.max( 0, (layout_ == CardPileLayout.STACKED) ? upperIndex : 0 );
        for( int index = upperIndex; index >= lowerIndex; --index )
        {
            if( cards_.get( index ).getBounds().contains( location ) )
            {
                return index;
            }
        }

        return -1;
    }

    /**
     * Gets the offset from the card pile base location in table coordinates of
     * the card at the specified index.
     * 
     * <p>
     * This method must be called while {@code lock_} is held.
     * </p>
     * 
     * @param index
     *        The card index; must be non-negative.
     * 
     * @return The offset from the card pile base location in table coordinates
     *         of the card at the specified index; never {@code null}.
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
    @Override
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
    @Override
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
    @Override
    public Point getLocation()
    {
        return getBounds().getLocation();
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getSize()
     */
    @Override
    public Dimension getSize()
    {
        return getBounds().getSize();
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCard()
     */
    @Override
    public ICard removeCard()
    {
        final CardRangeStrategy cardRangeStrategy = new CardRangeStrategy()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            int getLowerIndex()
            {
                return cards_.isEmpty() ? 0 : cards_.size() - 1;
            }
        };
        final List<ICard> cards = removeCards( cardRangeStrategy );
        assert cards.size() <= 1;
        return cards.isEmpty() ? null : cards.get( 0 );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCardPileListener(org.gamegineer.table.core.ICardPileListener)
     */
    @Override
    public void removeCardPileListener(
        final ICardPileListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.CardPile_removeCardPileListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCards()
     */
    @Override
    public List<ICard> removeCards()
    {
        return removeCards( new CardRangeStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#removeCards(java.awt.Point)
     */
    @Override
    public List<ICard> removeCards(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final CardRangeStrategy cardRangeStrategy = new CardRangeStrategy()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            int getLowerIndex()
            {
                final int cardIndex = getCardIndex( location );
                return (cardIndex != -1) ? cardIndex : cards_.size();
            }
        };
        return removeCards( cardRangeStrategy );
    }

    /**
     * Removes all cards from this card pile in the specified range.
     * 
     * @param cardRangeStrategy
     *        The strategy used to determine the range of cards to remove; must
     *        not be {@code null}. The strategy will be invoked while the card
     *        pile is synchronized.
     * 
     * @return The collection of cards removed from this card pile; never
     *         {@code null}. The cards are returned in order from the card
     *         nearest the bottom of the card pile to the card nearest the top
     *         of the card pile.
     */
    /* @NonNull */
    private List<ICard> removeCards(
        /* @NonNull */
        final CardRangeStrategy cardRangeStrategy )
    {
        assert cardRangeStrategy != null;

        final List<ICard> removedCards = new ArrayList<ICard>();
        final boolean cardPileBoundsChanged;
        synchronized( lock_ )
        {
            final Rectangle oldBounds = getBounds();

            removedCards.addAll( cards_.subList( cardRangeStrategy.getLowerIndex(), cardRangeStrategy.getUpperIndex() ) );
            cards_.removeAll( removedCards );

            final Rectangle newBounds = getBounds();
            cardPileBoundsChanged = !newBounds.equals( oldBounds );
        }

        for( final ICard card : removedCards )
        {
            fireCardRemoved( card );
        }

        if( cardPileBoundsChanged )
        {
            fireCardPileBoundsChanged();
        }

        return removedCards;
    }

    /**
     * Sets the design of the card pile base.
     * 
     * @param baseDesign
     *        The design of the card pile base; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code baseDesign} is {@code null}.
     */
    private void setBaseDesign(
        /* @NonNull */
        final ICardPileBaseDesign baseDesign )
    {
        assertArgumentNotNull( baseDesign, "baseDesign" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            baseDesign_ = baseDesign;
        }

        // TODO: fire event
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#setBaseLocation(java.awt.Point)
     */
    @Override
    public void setBaseLocation(
        final Point baseLocation )
    {
        assertArgumentNotNull( baseLocation, "baseLocation" ); //$NON-NLS-1$

        final TranslationOffsetStrategy translationOffsetStrategy = new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldBaseLocation = getBaseLocation();
                return new Dimension( baseLocation.x - oldBaseLocation.x, baseLocation.y - oldBaseLocation.y );
            }
        };
        translateBaseLocation( translationOffsetStrategy );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#setLayout(org.gamegineer.table.core.CardPileLayout)
     */
    @Override
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

                final Point cardLocation = new Point();
                for( int index = 0, size = cards_.size(); index < size; ++index )
                {
                    cardLocation.setLocation( baseLocation_ );
                    final Dimension cardOffset = getCardOffsetAt( index );
                    cardLocation.translate( cardOffset.width, cardOffset.height );
                    cards_.get( index ).setLocation( cardLocation );
                }

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
    @Override
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final TranslationOffsetStrategy translationOffsetStrategy = new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldLocation = getLocation();
                return new Dimension( location.x - oldLocation.x, location.y - oldLocation.y );
            }
        };
        translateBaseLocation( translationOffsetStrategy );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
     */
    @Override
    public void setMemento(
        final Object memento )
        throws MalformedMementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        // TODO: make this method atomic

        final ICardPileBaseDesign baseDesign = MementoUtils.getRequiredAttribute( memento, BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ICardPileBaseDesign.class );
        setBaseDesign( baseDesign );

        final Point location = MementoUtils.getOptionalAttribute( memento, BASE_LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        if( location != null )
        {
            setLocation( location );
        }

        final CardPileLayout layout = MementoUtils.getOptionalAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME, CardPileLayout.class );
        if( layout != null )
        {
            setLayout( layout );
        }

        removeCards();
        @SuppressWarnings( "unchecked" )
        final List<Object> cardMementos = MementoUtils.getOptionalAttribute( memento, CARDS_MEMENTO_ATTRIBUTE_NAME, List.class );
        if( cardMementos != null )
        {
            for( final Object cardMemento : cardMementos )
            {
                addCard( Card.fromMemento( cardMemento ) );
            }
        }
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
            return String.format( "CardPile[baseDesign_='%1$s', baseLocation_='%2$s', layout_='%3$s', cards_.size='%4$d'", baseDesign_, baseLocation_, layout_, cards_.size() ); //$NON-NLS-1$
        }
    }

    /**
     * Translates the card pile base location by the specified amount.
     * 
     * @param translationOffsetStrategy
     *        The strategy used to calculate the amount to translate the base
     *        location; must not be {@code null}. The strategy will be invoked
     *        while the card pile is synchronized.
     */
    private void translateBaseLocation(
        /* @NonNull */
        final TranslationOffsetStrategy translationOffsetStrategy )
    {
        assert translationOffsetStrategy != null;

        synchronized( lock_ )
        {
            final Dimension offset = translationOffsetStrategy.getOffset();
            baseLocation_.translate( offset.width, offset.height );
            for( final ICard card : cards_ )
            {
                final Point cardLocation = card.getLocation();
                cardLocation.translate( offset.width, offset.height );
                card.setLocation( cardLocation );
            }
        }

        fireCardPileBoundsChanged();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A strategy to calculate a contiguous range of cards in a card pile.
     */
    @Immutable
    private class CardRangeStrategy
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardRangeStrategy} class.
         */
        CardRangeStrategy()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the lower index of the card range, inclusive.
         * 
         * <p>
         * The default implementation returns 0.
         * </p>
         * 
         * @return The lower index of the card range, inclusive.
         */
        int getLowerIndex()
        {
            return 0;
        }

        /**
         * Gets the upper index of the card range, exclusive.
         * 
         * <p>
         * The default implementation returns the size of the card collection.
         * </p>
         * 
         * @return The upper index of the card range, exclusive.
         */
        @SuppressWarnings( "synthetic-access" )
        int getUpperIndex()
        {
            return cards_.size();
        }
    }

    /**
     * A strategy to calculate a translation offset.
     */
    @Immutable
    private class TranslationOffsetStrategy
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TranslationOffsetStrategy}
         * class.
         */
        TranslationOffsetStrategy()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the translation offset.
         * 
         * <p>
         * The default implementation returns a zero offset.
         * </p>
         * 
         * @return The translation offset; never {@code null}.
         */
        /* @NonNull */
        Dimension getOffset()
        {
            return new Dimension( 0, 0 );
        }
    }
}
