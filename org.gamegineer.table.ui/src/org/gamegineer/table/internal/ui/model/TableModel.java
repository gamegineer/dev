/*
 * TableModel.java
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
 * Created on Dec 26, 2009 at 10:32:11 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The table model.
 */
@ThreadSafe
public final class TableModel
    implements ICardPileModelListener, ITableListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of card pile models. */
    @GuardedBy( "lock_" )
    private final Map<ICardPile, CardPileModel> cardPileModels_;

    /** The focused card pile or {@code null} if no card pile has the focus. */
    @GuardedBy( "lock_" )
    private ICardPile focusedCardPile_;

    /** The collection of table model listeners. */
    private final CopyOnWriteArrayList<ITableModelListener> listeners_;

    /** The instance lock. */
    private final Object lock_;

    /** The offset of the table origin relative to the view origin. */
    @GuardedBy( "lock_" )
    private final Dimension originOffset_;

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
        cardPileModels_ = new IdentityHashMap<ICardPile, CardPileModel>();
        focusedCardPile_ = null;
        listeners_ = new CopyOnWriteArrayList<ITableModelListener>();
        originOffset_ = new Dimension( 0, 0 );
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
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addTableModelListener(
        /* @NonNull */
        final ITableModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.TableModel_addTableModelListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardPileAdded(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final ICardPile cardPile = event.getCardPile();
            final CardPileModel cardPileModel = new CardPileModel( cardPile );
            cardPileModels_.put( cardPile, cardPileModel );
            cardPileModel.addCardPileModelListener( this );
        }

        fireTableModelStateChanged();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileFocusGained(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileFocusGained(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        fireTableModelStateChanged();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileFocusLost(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileFocusLost(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        fireTableModelStateChanged();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileModelStateChanged(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileModelStateChanged(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        fireTableModelStateChanged();
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardPileRemoved(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final ICardPile cardPile = event.getCardPile();
        final boolean clearFocusedCardPile;
        synchronized( lock_ )
        {
            final CardPileModel cardPileModel = cardPileModels_.remove( cardPile );
            if( cardPileModel != null )
            {
                cardPileModel.removeCardPileModelListener( this );
            }

            clearFocusedCardPile = (focusedCardPile_ == cardPile);
        }

        if( clearFocusedCardPile )
        {
            setFocus( (ICardPile)null );
        }

        fireTableModelStateChanged();
    }

    /**
     * Fires a card pile focus changed event.
     */
    private void fireCardPileFocusChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.cardPileFocusChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.TableModel_cardPileFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table model state changed event.
     */
    private void fireTableModelStateChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableModelStateChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.TableModel_tableModelStateChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table origin offset changed event.
     */
    private void fireTableOriginOffsetChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableOriginOffsetChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.TableModel_tableOriginOffsetChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the card pile model associated with the specified card pile.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @return The card pile model associated with the specified card pile;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPile} does not exist in the table associated with
     *         this model.
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    /* @NonNull */
    public CardPileModel getCardPileModel(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        final CardPileModel cardPileModel;
        synchronized( lock_ )
        {
            cardPileModel = cardPileModels_.get( cardPile );
        }

        assertArgumentLegal( cardPileModel != null, "cardPile", Messages.TableModel_getCardPileModel_cardPile_absent ); //$NON-NLS-1$
        return cardPileModel;
    }

    /**
     * Gets the focused card pile.
     * 
     * @return The focused card pile or {@code null} if no card pile has the
     *         focus.
     */
    /* @Nullable */
    public ICardPile getFocusedCardPile()
    {
        synchronized( lock_ )
        {
            return focusedCardPile_;
        }
    }

    /**
     * Gets the offset of the table origin relative to the view origin in table
     * coordinates.
     * 
     * @return The offset of the table origin relative to the view origin in
     *         table coordinates; never {@code null}.
     */
    /* @NonNull */
    public Dimension getOriginOffset()
    {
        synchronized( lock_ )
        {
            return new Dimension( originOffset_ );
        }
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
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.TableModel_removeTableModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Sets the focus to the specified card pile.
     * 
     * @param cardPile
     *        The card pile to receive the focus or {@code null} if no card pile
     *        should have the focus.
     */
    public void setFocus(
        /* @Nullable */
        final ICardPile cardPile )
    {
        final boolean cardPileFocusChanged;
        final CardPileModel oldFocusedCardPileModel;
        final CardPileModel newFocusedCardPileModel;

        synchronized( lock_ )
        {
            if( cardPile != focusedCardPile_ )
            {
                cardPileFocusChanged = true;
                oldFocusedCardPileModel = (focusedCardPile_ != null) ? cardPileModels_.get( focusedCardPile_ ) : null;
                newFocusedCardPileModel = (cardPile != null) ? cardPileModels_.get( cardPile ) : null;
                focusedCardPile_ = cardPile;
            }
            else
            {
                cardPileFocusChanged = false;
                oldFocusedCardPileModel = null;
                newFocusedCardPileModel = null;
            }
        }

        if( cardPileFocusChanged )
        {
            if( oldFocusedCardPileModel != null )
            {
                oldFocusedCardPileModel.setFocused( false );
            }
            if( newFocusedCardPileModel != null )
            {
                newFocusedCardPileModel.setFocused( true );
            }

            fireCardPileFocusChanged();
            fireTableModelStateChanged();
        }
    }

    /**
     * Sets the offset of the table origin relative to the view origin in table
     * coordinates.
     * 
     * @param originOffset
     *        The offset of the table origin relative to the view origin in
     *        table coordinates; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code originOffset} is {@code null}.
     */
    public void setOriginOffset(
        /* @NonNull */
        final Dimension originOffset )
    {
        assertArgumentNotNull( originOffset, "originOffset" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            originOffset_.setSize( originOffset );
        }

        fireTableOriginOffsetChanged();
        fireTableModelStateChanged();
    }
}
