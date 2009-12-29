/*
 * CardModel.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Dec 25, 2009 at 9:30:44 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The card model.
 */
@ThreadSafe
public final class CardModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card associated with this model. */
    private final ICard card_;

    /** Indicates the associated card has the focus. */
    @GuardedBy( "lock_" )
    private boolean isFocused_;

    /** The card model listener. */
    @GuardedBy( "lock_" )
    private ICardModelListener listener_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardModel} class.
     * 
     * @param card
     *        The card associated with this model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    public CardModel(
        /* @NonNull */
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        lock_ = new Object();
        card_ = card;
        isFocused_ = false;
        listener_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card model listener to this card model.
     * 
     * @param listener
     *        The card model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered card model listener.
     * @throws java.lang.IllegalStateException
     *         If a card model listener is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardModelListener(
        /* @NonNull */
        final ICardModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ != listener, "listener", Messages.CardModel_addCardModelListener_listener_registered ); //$NON-NLS-1$
            assertStateLegal( listener_ == null, Messages.CardModel_addCardModelListener_tooManyListeners );

            listener_ = listener;
        }
    }

    /**
     * Fires a card focus changed event.
     */
    private void fireCardFocusChanged()
    {
        final ICardModelListener listener = getCardModelListener();
        if( listener != null )
        {
            try
            {
                listener.cardFocusChanged( new CardModelEvent( this ) );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardModel_cardFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the card associated with this model.
     * 
     * @return The card associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ICard getCard()
    {
        return card_;
    }

    /**
     * Gets the card model listener.
     * 
     * @return The card model listener; may be {@code null}.
     */
    /* @Nullable */
    private ICardModelListener getCardModelListener()
    {
        synchronized( lock_ )
        {
            return listener_;
        }
    }

    /**
     * Indicates the associated card has the focus.
     * 
     * @return {@code true} if the associated card has the focus; otherwise
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
     * Removes the specified card model listener from this card model.
     * 
     * @param listener
     *        The card model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered card model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeCardModelListener(
        /* @NonNull */
        final ICardModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ == listener, "listener", Messages.CardModel_removeCardModelListener_listener_notRegistered ); //$NON-NLS-1$

            listener_ = null;
        }
    }

    /**
     * Sets or removes the focus from the associated card.
     * 
     * @param isFocused
     *        {@code true} if the associated card has the focus; otherwise
     *        {@code false}.
     */
    public void setFocused(
        final boolean isFocused )
    {
        synchronized( lock_ )
        {
            isFocused_ = isFocused;
        }

        fireCardFocusChanged();
    }
}
