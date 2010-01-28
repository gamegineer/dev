/*
 * CardPileModel.java
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
 * Created on Jan 26, 2010 at 10:41:29 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The card pile model.
 */
@ThreadSafe
public final class CardPileModel
    implements ICardPileListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of card models. */
    @GuardedBy( "lock_" )
    private final Map<ICard, CardModel> cardModels_;

    /** The card pile associated with this model. */
    private final ICardPile cardPile_;

    /** Indicates the associated card pile has the focus. */
    @GuardedBy( "lock_" )
    private boolean isFocused_;

    /** The card pile model listener. */
    @GuardedBy( "lock_" )
    private ICardPileModelListener listener_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileModel} class.
     * 
     * @param cardPile
     *        The card pile associated with this model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    public CardPileModel(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        lock_ = new Object();
        cardModels_ = new IdentityHashMap<ICard, CardModel>();
        cardPile_ = cardPile;
        isFocused_ = false;
        listener_ = null;

        cardPile_.addCardPileListener( this );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card pile model listener to this card pile model.
     * 
     * @param listener
     *        The card pile model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered card pile model
     *         listener.
     * @throws java.lang.IllegalStateException
     *         If a card pile model listener is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardPileModelListener(
        /* @NonNull */
        final ICardPileModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ != listener, "listener", Messages.CardPileModel_addCardPileModelListener_listener_registered ); //$NON-NLS-1$
            assertStateLegal( listener_ == null, Messages.CardPileModel_addCardPileModelListener_tooManyListeners );

            listener_ = listener;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardAdded(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    public void cardAdded(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            cardModels_.put( event.getCard(), new CardModel( event.getCard() ) );
        }
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardPileBoundsChanged(org.gamegineer.table.core.CardPileEvent)
     */
    public void cardPileBoundsChanged(
        final CardPileEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    public void cardRemoved(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            cardModels_.remove( event.getCard() );
        }
    }

    /**
     * Fires a card pile focus gained event.
     */
    private void fireCardPileFocusGained()
    {
        final ICardPileModelListener listener = getCardPileModelListener();
        if( listener != null )
        {
            try
            {
                listener.cardPileFocusGained( new CardPileModelEvent( this ) );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPileModel_cardPileFocusGained_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile focus lost event.
     */
    private void fireCardPileFocusLost()
    {
        final ICardPileModelListener listener = getCardPileModelListener();
        if( listener != null )
        {
            try
            {
                listener.cardPileFocusLost( new CardPileModelEvent( this ) );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPileModel_cardPileFocusLost_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the card model associated with the specified card.
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @return The card model associated with the specified card; never {@code
     *         null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code card} does not exist in the card pile associated with
     *         this model.
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    /* @NonNull */
    public CardModel getCardModel(
        /* @NonNull */
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        final CardModel cardModel;
        synchronized( lock_ )
        {
            cardModel = cardModels_.get( card );
        }

        assertArgumentLegal( cardModel != null, "card", Messages.CardPileModel_getCardModel_card_absent ); //$NON-NLS-1$
        return cardModel;
    }

    /**
     * Gets the card pile associated with this model.
     * 
     * @return The card pile associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ICardPile getCardPile()
    {
        return cardPile_;
    }

    /**
     * Gets the card pile model listener.
     * 
     * @return The card pile model listener; may be {@code null}.
     */
    /* @Nullable */
    private ICardPileModelListener getCardPileModelListener()
    {
        synchronized( lock_ )
        {
            return listener_;
        }
    }

    /**
     * Indicates the associated card pile has the focus.
     * 
     * @return {@code true} if the associated card pile has the focus; otherwise
     *         {@code false}.
     */
    public boolean isFocused()
    {
        synchronized( lock_ )
        {
            return isFocused_;
        }
    }

    /**
     * Removes the specified card pile model listener from this card pile model.
     * 
     * @param listener
     *        The card pile model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered card pile model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeCardPileModelListener(
        /* @NonNull */
        final ICardPileModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ == listener, "listener", Messages.CardPileModel_removeCardPileModelListener_listener_notRegistered ); //$NON-NLS-1$

            listener_ = null;
        }
    }

    /**
     * Sets or removes the focus from the associated card pile.
     * 
     * @param isFocused
     *        {@code true} if the associated card pile has the focus; otherwise
     *        {@code false}.
     */
    public void setFocused(
        final boolean isFocused )
    {
        synchronized( lock_ )
        {
            isFocused_ = isFocused;
        }

        if( isFocused )
        {
            fireCardPileFocusGained();
        }
        else
        {
            fireCardPileFocusLost();
        }
    }
}
