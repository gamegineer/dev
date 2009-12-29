/*
 * TableModel.java
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
 * Created on Dec 26, 2009 at 10:32:11 PM.
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
import org.gamegineer.table.core.CardChangeEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The table model.
 */
@ThreadSafe
public final class TableModel
    implements ITableListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of card models. */
    @GuardedBy( "lock_" )
    private final Map<ICard, CardModel> cardModels_;

    /** The focused card or {@code null} if no card has the focus. */
    @GuardedBy( "lock_" )
    private ICard focusedCard_;

    /** The table model listener. */
    @GuardedBy( "lock_" )
    private ITableModelListener listener_;

    /** The instance lock. */
    private final Object lock_;

    /** The table associated with this model. */
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModel} class.
     * 
     * @param table
     *        The table associated with this model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    public TableModel(
        /* @NonNull */
        final ITable table )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$

        lock_ = new Object();
        cardModels_ = new IdentityHashMap<ICard, CardModel>();
        focusedCard_ = null;
        listener_ = null;
        table_ = table;

        table_.addTableListener( this );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified table model listener to this table model.
     * 
     * @param listener
     *        The table model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered table model listener.
     * @throws java.lang.IllegalStateException
     *         If a table model listener is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addTableModelListener(
        /* @NonNull */
        final ITableModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ != listener, "listener", Messages.TableModel_addTableModelListener_listener_registered ); //$NON-NLS-1$
            assertStateLegal( listener_ == null, Messages.TableModel_addTableModelListener_tooManyListeners );

            listener_ = listener;
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardAdded(org.gamegineer.table.core.CardChangeEvent)
     */
    @Override
    public void cardAdded(
        final CardChangeEvent event )
    {
        synchronized( lock_ )
        {
            cardModels_.put( event.getCard(), new CardModel( event.getCard() ) );
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardRemoved(org.gamegineer.table.core.CardChangeEvent)
     */
    @Override
    public void cardRemoved(
        final CardChangeEvent event )
    {
        synchronized( lock_ )
        {
            cardModels_.remove( event.getCard() );
        }
    }

    /**
     * Fires a card focus changed event.
     */
    private void fireCardFocusChanged()
    {
        final ITableModelListener listener = getTableModelListener();
        if( listener != null )
        {
            try
            {
                listener.cardFocusChanged( new TableModelEvent( this ) );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.TableModel_cardFocusChanged_unexpectedException, e );
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
     *         If {@code card} does not exist in the table associated with this
     *         model.
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

        assertArgumentLegal( cardModel != null, "card", Messages.TableModel_getCardModel_card_absent ); //$NON-NLS-1$
        return cardModel;
    }

    /**
     * Gets the table associated with this model.
     * 
     * @return The table associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ITable getTable()
    {
        return table_;
    }

    /**
     * Gets the table model listener.
     * 
     * @return The table model listener; may be {@code null}.
     */
    /* @Nullable */
    private ITableModelListener getTableModelListener()
    {
        synchronized( lock_ )
        {
            return listener_;
        }
    }

    /**
     * Removes the specified table model listener from this table model.
     * 
     * @param listener
     *        The table model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered table model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeTableModelListener(
        /* @NonNull */
        final ITableModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ == listener, "listener", Messages.TableModel_removeTableModelListener_listener_notRegistered ); //$NON-NLS-1$

            listener_ = null;
        }
    }

    /**
     * Sets the focus to the specified card.
     * 
     * @param card
     *        The card to receive the focus or {@code null} if no card should
     *        have the focus.
     */
    public void setFocus(
        /* @Nullable */
        final ICard card )
    {
        final CardModel oldFocusedCardModel;
        final CardModel newFocusedCardModel;

        synchronized( lock_ )
        {
            if( card != focusedCard_ )
            {
                oldFocusedCardModel = (focusedCard_ != null) ? cardModels_.get( focusedCard_ ) : null;
                newFocusedCardModel = (card != null) ? cardModels_.get( card ) : null;
                focusedCard_ = card;
            }
            else
            {
                oldFocusedCardModel = null;
                newFocusedCardModel = null;
            }
        }

        if( oldFocusedCardModel != newFocusedCardModel )
        {
            if( oldFocusedCardModel != null )
            {
                oldFocusedCardModel.setFocused( false );
            }
            if( newFocusedCardModel != null )
            {
                newFocusedCardModel.setFocused( true );
            }

            fireCardFocusChanged();
        }
    }
}
