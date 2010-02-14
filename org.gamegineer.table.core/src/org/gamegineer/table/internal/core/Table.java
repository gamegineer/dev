/*
 * Table.java
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
 * Created on Oct 6, 2009 at 11:09:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;

/**
 * Implementation of {@link org.gamegineer.table.core.ITable}.
 */
@ThreadSafe
public final class Table
    implements ITable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of card piles on this table. */
    @GuardedBy( "lock_ " )
    private final List<ICardPile> cardPiles_;

    /** The collection of table listeners. */
    private final CopyOnWriteArrayList<ITableListener> listeners_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Table} class.
     */
    public Table()
    {
        lock_ = new Object();
        cardPiles_ = new ArrayList<ICardPile>();
        listeners_ = new CopyOnWriteArrayList<ITableListener>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITable#addCardPile(org.gamegineer.table.core.ICardPile)
     */
    public void addCardPile(
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( !cardPiles_.contains( cardPile ) )
            {
                cardPiles_.add( cardPile );
            }
        }

        fireCardPileAdded( cardPile );
    }

    /*
     * @see org.gamegineer.table.core.ITable#addTableListener(org.gamegineer.table.core.ITableListener)
     */
    public void addTableListener(
        final ITableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.Table_addTableListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a card pile added event.
     * 
     * @param cardPile
     *        The added card pile; must not be {@code null}.
     */
    private void fireCardPileAdded(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        final TableContentChangedEvent event = InternalTableContentChangedEvent.createTableContentChangedEvent( this, cardPile );
        for( final ITableListener listener : listeners_ )
        {
            try
            {
                listener.cardPileAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.Table_cardPileAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile removed event.
     * 
     * @param cardPile
     *        The removed card pile; must not be {@code null}.
     */
    private void fireCardPileRemoved(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        final TableContentChangedEvent event = InternalTableContentChangedEvent.createTableContentChangedEvent( this, cardPile );
        for( final ITableListener listener : listeners_ )
        {
            try
            {
                listener.cardPileRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.Table_cardPileRemoved_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCardPile(java.awt.Point)
     */
    public ICardPile getCardPile(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            for( final ListIterator<ICardPile> iterator = cardPiles_.listIterator( cardPiles_.size() ); iterator.hasPrevious(); )
            {
                final ICardPile cardPile = iterator.previous();
                if( cardPile.getBounds().contains( location ) )
                {
                    return cardPile;
                }
            }
        }

        return null;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCardPiles()
     */
    public List<ICardPile> getCardPiles()
    {
        synchronized( lock_ )
        {
            return new ArrayList<ICardPile>( cardPiles_ );
        }
    }

    /*
     * @see org.gamegineer.table.core.ITable#removeCardPile(org.gamegineer.table.core.ICardPile)
     */
    public void removeCardPile(
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            cardPiles_.remove( cardPile );
        }

        fireCardPileRemoved( cardPile );
    }

    /*
     * @see org.gamegineer.table.core.ITable#removeTableListener(org.gamegineer.table.core.ITableListener)
     */
    public void removeTableListener(
        final ITableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.Table_removeTableListener_listener_notRegistered ); //$NON-NLS-1$
    }
}
