/*
 * CardPile.java
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
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.CardPileLayout;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.core.ITable;

/**
 * Implementation of {@link org.gamegineer.table.core.ICardPile}.
 */
@ThreadSafe
final class CardPile
    implements ICardPile
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

    /** The default card pile base design. */
    private static final CardPileBaseDesign DEFAULT_BASE_DESIGN = new CardPileBaseDesign( CardPileBaseDesignId.fromString( "org.gamegineer.table.internal.core.CardPile.DEFAULT_BASE_DESIGN" ), 0, 0 ); //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the card pile layout.
     */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "layout"; //$NON-NLS-1$

    /** The offset of each stack level in table coordinates. */
    private static final Dimension STACK_LEVEL_OFFSET = new Dimension( 2, 1 );

    /** The design of the card pile base. */
    @GuardedBy( "getLock()" )
    private ICardPileBaseDesign baseDesign_;

    /** The location of the card pile base in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point baseLocation_;

    /** The collection of cards in this card pile ordered from bottom to top. */
    @GuardedBy( "getLock()" )
    private final List<Card> cards_;

    /** The card pile layout. */
    @GuardedBy( "getLock()" )
    private CardPileLayout layout_;

    /** The collection of card pile listeners. */
    private final CopyOnWriteArrayList<ICardPileListener> listeners_;

    /**
     * The table that contains this card pile or {@code null} if this card pile
     * is not contained in a table.
     */
    @GuardedBy( "getLock()" )
    private Table table_;

    /** The table context associated with the card pile. */
    private final TableContext tableContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPile} class.
     * 
     * @param tableContext
     *        The table context associated with the card pile; must not be
     *        {@code null}.
     */
    CardPile(
        /* @NonNull */
        final TableContext tableContext )
    {
        assert tableContext != null;

        baseDesign_ = DEFAULT_BASE_DESIGN;
        baseLocation_ = new Point( 0, 0 );
        cards_ = new ArrayList<Card>();
        layout_ = CardPileLayout.STACKED;
        listeners_ = new CopyOnWriteArrayList<ICardPileListener>();
        table_ = null;
        tableContext_ = tableContext;
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
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.CardPile_addCardPileListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#addCards(java.util.List)
     */
    @Override
    public void addCards(
        final List<ICard> cards )
    {
        assertArgumentNotNull( cards, "cards" ); //$NON-NLS-1$

        final List<Card> addedCards = new ArrayList<Card>();
        final int firstCardIndex;
        final boolean cardPileBoundsChanged;

        getLock().lock();
        try
        {
            final Rectangle oldBounds = getBounds();
            firstCardIndex = cards_.size();

            for( final ICard card : cards )
            {
                final Card typedCard = (Card)card;
                if( typedCard == null )
                {
                    throw new IllegalArgumentException( NonNlsMessages.CardPile_addCards_cards_containsNullElement );
                }
                assertArgumentLegal( typedCard.getCardPile() == null, "cards", NonNlsMessages.CardPile_addCards_cards_containsOwnedCard ); //$NON-NLS-1$
                assertArgumentLegal( typedCard.getTableContext() == tableContext_, "cards", NonNlsMessages.CardPile_addCards_cards_containsCardCreatedByDifferentTable ); //$NON-NLS-1$

                final Point cardLocation = new Point( baseLocation_ );
                final Dimension cardOffset = getCardOffsetAt( cards_.size() );
                cardLocation.translate( cardOffset.width, cardOffset.height );
                typedCard.setCardPile( this );
                typedCard.setLocation( cardLocation );
                cards_.add( typedCard );
                addedCards.add( typedCard );
            }

            final Rectangle newBounds = getBounds();
            cardPileBoundsChanged = !newBounds.equals( oldBounds );
        }
        finally
        {
            getLock().unlock();
        }

        if( !addedCards.isEmpty() || cardPileBoundsChanged )
        {
            tableContext_.addEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    int cardIndex = firstCardIndex;
                    for( final ICard card : addedCards )
                    {
                        fireCardAdded( card, cardIndex++ );
                    }

                    if( cardPileBoundsChanged )
                    {
                        fireComponentBoundsChanged();
                    }
                }
            } );
        }
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
            memento.put( BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, baseDesign_ );
            memento.put( BASE_LOCATION_MEMENTO_ATTRIBUTE_NAME, new Point( baseLocation_ ) );
            memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, layout_ );

            final List<Object> cardMementos = new ArrayList<Object>( cards_.size() );
            for( final ICard card : cards_ )
            {
                cardMementos.add( card.createMemento() );
            }
            memento.put( CARDS_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( cardMementos ) );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Fires a card added event.
     * 
     * @param card
     *        The added card; must not be {@code null}.
     * @param cardIndex
     *        The index of the added card; must not be negative.
     */
    private void fireCardAdded(
        /* @NonNull */
        final ICard card,
        final int cardIndex )
    {
        assert card != null;
        assert cardIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final CardPileContentChangedEvent event = new CardPileContentChangedEvent( this, card, cardIndex );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_cardAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile base design changed event.
     */
    private void fireCardPileBaseDesignChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final CardPileEvent event = new CardPileEvent( this );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardPileBaseDesignChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_cardPileBaseDesignChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile layout changed event.
     */
    private void fireCardPileLayoutChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final CardPileEvent event = new CardPileEvent( this );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardPileLayoutChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_cardPileLayoutChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card removed event.
     * 
     * @param card
     *        The removed card; must not be {@code null}.
     * @param cardIndex
     *        The index of the removed card; must not be negative.
     */
    private void fireCardRemoved(
        /* @NonNull */
        final ICard card,
        final int cardIndex )
    {
        assert card != null;
        assert cardIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final CardPileContentChangedEvent event = new CardPileContentChangedEvent( this, card, cardIndex );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.cardRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_cardRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component bounds changed event.
     */
    private void fireComponentBoundsChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final ICardPileListener listener : listeners_ )
        {
            try
            {
                listener.componentBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_componentBoundsChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Creates a new instance of the {@code CardPile} class from the specified
     * memento.
     * 
     * @param tableContext
     *        The table context associated with the new card pile; must not be
     *        {@code null}.
     * @param memento
     *        The memento representing the initial card pile state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code CardPile} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static CardPile fromMemento(
        /* @NonNull */
        final TableContext tableContext,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableContext != null;
        assert memento != null;

        final CardPile cardPile = new CardPile( tableContext );

        final ICardPileBaseDesign baseDesign = MementoUtils.getAttribute( memento, BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ICardPileBaseDesign.class );
        cardPile.setBaseDesign( baseDesign );

        final Point location = MementoUtils.getAttribute( memento, BASE_LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        cardPile.setLocation( location );

        final CardPileLayout layout = MementoUtils.getAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME, CardPileLayout.class );
        cardPile.setLayout( layout );

        @SuppressWarnings( "unchecked" )
        final List<Object> cardMementos = MementoUtils.getAttribute( memento, CARDS_MEMENTO_ATTRIBUTE_NAME, List.class );
        for( final Object cardMemento : cardMementos )
        {
            cardPile.addCard( Card.fromMemento( tableContext, cardMemento ) );
        }

        return cardPile;
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBaseDesign()
     */
    @Override
    public ICardPileBaseDesign getBaseDesign()
    {
        getLock().lock();
        try
        {
            return baseDesign_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getBaseLocation()
     */
    @Override
    public Point getBaseLocation()
    {
        getLock().lock();
        try
        {
            return new Point( baseLocation_ );
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
            if( cards_.isEmpty() )
            {
                return new Rectangle( baseLocation_, baseDesign_.getSize() );
            }

            final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
            final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
            return topCardBounds.union( bottomCardBounds );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getCard(int)
     */
    @Override
    public ICard getCard(
        final int index )
    {
        getLock().lock();
        try
        {
            assertArgumentLegal( (index >= 0) && (index < cards_.size()), "index", NonNlsMessages.CardPile_getCardFromIndex_index_outOfRange ); //$NON-NLS-1$

            return cards_.get( index );
        }
        finally
        {
            getLock().unlock();
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

        getLock().lock();
        try
        {
            final int index = getCardIndex( location );
            return (index != -1) ? cards_.get( index ) : null;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getCardCount()
     */
    @Override
    public int getCardCount()
    {
        getLock().lock();
        try
        {
            return cards_.size();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getCardIndex(org.gamegineer.table.core.ICard)
     */
    @Override
    public int getCardIndex(
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        final int index;
        getLock().lock();
        try
        {
            index = cards_.indexOf( card );
        }
        finally
        {
            getLock().unlock();
        }

        assertArgumentLegal( index != -1, "card", NonNlsMessages.CardPile_getCardIndex_card_notOwned ); //$NON-NLS-1$
        return index;
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
    @GuardedBy( "getLock()" )
    private int getCardIndex(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert getLock().isHeldByCurrentThread();

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
     * @param index
     *        The card index; must be non-negative.
     * 
     * @return The offset from the card pile base location in table coordinates
     *         of the card at the specified index; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If an unknown layout is active.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private Dimension getCardOffsetAt(
        final int index )
    {
        assert index >= 0;
        assert getLock().isHeldByCurrentThread();

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

        throw new IllegalStateException( NonNlsMessages.CardPile_getCardOffsetAt_unknownLayout );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getCards()
     */
    @Override
    public List<ICard> getCards()
    {
        getLock().lock();
        try
        {
            return new ArrayList<ICard>( cards_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getLayout()
     */
    @Override
    public CardPileLayout getLayout()
    {
        getLock().lock();
        try
        {
            return layout_;
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
        return getBounds().getLocation();
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
     * @see org.gamegineer.table.core.IComponent#getSize()
     */
    @Override
    public Dimension getSize()
    {
        return getBounds().getSize();
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#getTable()
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

    /**
     * Gets the table context associated with the card pile.
     * 
     * @return The table context associated with the card pile; never
     *         {@code null}.
     */
    /* @NonNull */
    TableContext getTableContext()
    {
        return tableContext_;
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
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.CardPile_removeCardPileListener_listener_notRegistered ); //$NON-NLS-1$
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
     *        pile instance lock is held.
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

        final List<Card> removedCards = new ArrayList<Card>();
        final int upperCardIndex = cardRangeStrategy.getUpperIndex() - 1;
        final boolean cardPileBoundsChanged;

        getLock().lock();
        try
        {
            final Rectangle oldBounds = getBounds();

            removedCards.addAll( cards_.subList( cardRangeStrategy.getLowerIndex(), cardRangeStrategy.getUpperIndex() ) );
            cards_.removeAll( removedCards );
            for( final Card card : removedCards )
            {
                card.setCardPile( null );
            }

            final Rectangle newBounds = getBounds();
            cardPileBoundsChanged = !newBounds.equals( oldBounds );
        }
        finally
        {
            getLock().unlock();
        }

        if( !removedCards.isEmpty() || cardPileBoundsChanged )
        {
            tableContext_.addEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    // ensure events are fired in order from highest index to lowest index
                    final List<ICard> reversedRemovedCards = new ArrayList<ICard>( removedCards );
                    Collections.reverse( reversedRemovedCards );
                    int cardIndex = upperCardIndex;
                    for( final ICard card : reversedRemovedCards )
                    {
                        fireCardRemoved( card, cardIndex-- );
                    }

                    if( cardPileBoundsChanged )
                    {
                        fireComponentBoundsChanged();
                    }
                }
            } );
        }

        return new ArrayList<ICard>( removedCards );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#setBaseDesign(org.gamegineer.table.core.ICardPileBaseDesign)
     */
    @Override
    public void setBaseDesign(
        final ICardPileBaseDesign baseDesign )
    {
        assertArgumentNotNull( baseDesign, "baseDesign" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            baseDesign_ = baseDesign;
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
                fireCardPileBaseDesignChanged();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.ICardPile#setBaseLocation(java.awt.Point)
     */
    @Override
    public void setBaseLocation(
        final Point baseLocation )
    {
        assertArgumentNotNull( baseLocation, "baseLocation" ); //$NON-NLS-1$

        translateBaseLocation( new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldBaseLocation = getBaseLocation();
                return new Dimension( baseLocation.x - oldBaseLocation.x, baseLocation.y - oldBaseLocation.y );
            }
        } );
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

        getLock().lock();
        try
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
                fireCardPileLayoutChanged();

                if( cardPileBoundsChanged )
                {
                    fireComponentBoundsChanged();
                }
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setLocation(java.awt.Point)
     */
    @Override
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        translateBaseLocation( new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldLocation = getLocation();
                return new Dimension( location.x - oldLocation.x, location.y - oldLocation.y );
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
            final CardPile cardPile = fromMemento( tableContext_, memento );

            setBaseDesign( cardPile.getBaseDesign() );
            setBaseLocation( cardPile.getBaseLocation() );
            setLayout( cardPile.getLayout() );

            removeCards();
            addCards( cardPile.removeCards() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Sets the table that contains this card pile.
     * 
     * @param table
     *        The table that contains this card pile or {@code null} if this
     *        card pile is not contained in a table.
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

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "CardPile[" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            sb.append( "baseDesign_=" ); //$NON-NLS-1$
            sb.append( baseDesign_ );
            sb.append( ", baseLocation_=" ); //$NON-NLS-1$
            sb.append( baseLocation_ );
            sb.append( ", cards_.size()=" ); //$NON-NLS-1$
            sb.append( cards_.size() );
            sb.append( ", layout_=" ); //$NON-NLS-1$
            sb.append( layout_ );
        }
        finally
        {
            getLock().unlock();
        }

        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }

    /**
     * Translates the card pile base location by the specified amount.
     * 
     * @param translationOffsetStrategy
     *        The strategy used to calculate the amount to translate the base
     *        location; must not be {@code null}. The strategy will be invoked
     *        while the card pile instance lock is held.
     */
    private void translateBaseLocation(
        /* @NonNull */
        final TranslationOffsetStrategy translationOffsetStrategy )
    {
        assert translationOffsetStrategy != null;

        getLock().lock();
        try
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
