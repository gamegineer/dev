/*
 * CardPileModel.java
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
 * Created on Jan 26, 2010 at 10:41:29 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The card pile model.
 */
@ThreadSafe
public final class CardPileModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card model listener for this model. */
    private final ICardModelListener cardModelListener_;

    /** The collection of card models. */
    @GuardedBy( "lock_" )
    private final Map<ICard, CardModel> cardModels_;

    /** The card pile associated with this model. */
    private final ICardPile cardPile_;

    /** Indicates the associated card pile has the focus. */
    @GuardedBy( "lock_" )
    private boolean isFocused_;

    /** The collection of card pile model listeners. */
    private final CopyOnWriteArrayList<ICardPileModelListener> listeners_;

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

        cardModelListener_ = new CardModelListener();
        cardModels_ = new IdentityHashMap<ICard, CardModel>();
        cardPile_ = cardPile;
        isFocused_ = false;
        listeners_ = new CopyOnWriteArrayList<ICardPileModelListener>();
        lock_ = new Object();

        cardPile_.addCardPileListener( new CardPileListener() );

        for( final IComponent component : cardPile.getComponents() )
        {
            createCardModel( (ICard)component ); // FIXME: remove cast
        }
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
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardPileModelListener(
        /* @NonNull */
        final ICardPileModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.CardPileModel_addCardPileModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Creates a card model for the specified card.
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @return The card model; never {@code null}.
     */
    /* @NonNull */
    private CardModel createCardModel(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        final CardModel cardModel = new CardModel( card );
        cardModels_.put( card, cardModel );
        cardModel.addCardModelListener( cardModelListener_ );
        return cardModel;
    }

    /**
     * Fires a card pile changed event.
     */
    private void fireCardPileChanged()
    {
        final CardPileModelEvent event = new CardPileModelEvent( this );
        for( final ICardPileModelListener listener : listeners_ )
        {
            try
            {
                listener.cardPileChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPileModel_cardPileChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile model focus changed event.
     */
    private void fireCardPileModelFocusChanged()
    {
        final CardPileModelEvent event = new CardPileModelEvent( this );
        for( final ICardPileModelListener listener : listeners_ )
        {
            try
            {
                listener.cardPileModelFocusChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPileModel_cardPileModelFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the card model associated with the specified card.
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @return The card model associated with the specified card; never
     *         {@code null}.
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

        assertArgumentLegal( cardModel != null, "card", NonNlsMessages.CardPileModel_getCardModel_card_absent ); //$NON-NLS-1$
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
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.CardPileModel_removeCardPileModelListener_listener_notRegistered ); //$NON-NLS-1$
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

        fireCardPileModelFocusChanged();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card model listener for the card pile model.
     */
    @Immutable
    private final class CardModelListener
        extends org.gamegineer.table.internal.ui.model.CardModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardModelListener} class.
         */
        CardModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.ICardModelListener#cardChanged(org.gamegineer.table.internal.ui.model.CardModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardChanged(
            final CardModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }
    }

    /**
     * A card pile listener for the card pile model.
     */
    @Immutable
    private final class CardPileListener
        extends org.gamegineer.table.core.CardPileListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardPileListener} class.
         */
        CardPileListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.CardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileBaseDesignChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.CardPileListener#cardPileLayoutChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileLayoutChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.CardPileListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                createCardModel( (ICard)event.getComponent() ); // FIXME: remove cast
            }

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.CardPileListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final CardModel cardModel = cardModels_.remove( event.getComponent() );
                if( cardModel != null )
                {
                    cardModel.removeCardModelListener( cardModelListener_ );
                }
            }

            fireCardPileChanged();
        }
    }
}
