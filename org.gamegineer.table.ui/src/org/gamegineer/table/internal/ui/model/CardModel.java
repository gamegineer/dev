/*
 * CardModel.java
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
 * Created on Dec 25, 2009 at 9:30:44 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardEvent;
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

    /** The collection of card model listeners. */
    private final CopyOnWriteArrayList<ICardModelListener> listeners_;


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

        card_ = card;
        listeners_ = new CopyOnWriteArrayList<ICardModelListener>();

        card_.addCardListener( new CardListener() );
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
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardModelListener(
        /* @NonNull */
        final ICardModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.CardModel_addCardModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a card changed event.
     */
    private void fireCardChanged()
    {
        final CardModelEvent event = new CardModelEvent( this );
        for( final ICardModelListener listener : listeners_ )
        {
            try
            {
                listener.cardChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardModel_cardChanged_unexpectedException, e );
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
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.CardModel_removeCardModelListener_listener_notRegistered ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card listener for the card model.
     */
    @Immutable
    private final class CardListener
        extends org.gamegineer.table.core.CardListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardListener} class.
         */
        CardListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.CardListener#cardLocationChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardLocationChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardChanged();
        }

        /*
         * @see org.gamegineer.table.core.CardListener#cardOrientationChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardOrientationChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardChanged();
        }

        /*
         * @see org.gamegineer.table.core.CardListener#cardSurfaceDesignsChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardSurfaceDesignsChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardChanged();
        }
    }
}
