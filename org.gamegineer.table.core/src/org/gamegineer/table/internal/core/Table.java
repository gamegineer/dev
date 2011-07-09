/*
 * Table.java
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
 * Created on Oct 6, 2009 at 11:09:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardSurfaceDesign;
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

    /**
     * The name of the memento attribute that stores the collection of card
     * piles on the table.
     */
    private static final String CARD_PILES_MEMENTO_ATTRIBUTE_NAME = "cardPiles"; //$NON-NLS-1$

    /** The collection of card piles on this table. */
    @GuardedBy( "lock_" )
    private final List<CardPile> cardPiles_;

    /** The collection of table listeners. */
    private final CopyOnWriteArrayList<ITableListener> listeners_;

    /** The instance lock. */
    private final Object lock_;

    /**
     * The collection of pending event notifications to be executed the next
     * time the instance lock is released.
     */
    private final Queue<Runnable> pendingEventNotifications_;

    /** The table context. */
    private final TableContext tableContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Table} class.
     * 
     * @param tableContext
     *        The table context; must not be {@code null}.
     */
    private Table(
        /* @NonNull */
        final TableContext tableContext )
    {
        assert tableContext != null;

        cardPiles_ = new ArrayList<CardPile>();
        listeners_ = new CopyOnWriteArrayList<ITableListener>();
        lock_ = new Object();
        pendingEventNotifications_ = new ConcurrentLinkedQueue<Runnable>();
        tableContext_ = tableContext;
    }

    /**
     * Initializes a new instance of the {@code Table} class.
     */
    public Table()
    {
        this( new TableContext() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITable#addCardPile(org.gamegineer.table.core.ICardPile)
     */
    @Override
    public void addCardPile(
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            addCardPileInternal( cardPile );
        }

        firePendingEventNotifications();
    }

    /**
     * Adds the specified card pile to this table.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPile} is already contained in the table or if
     *         {@code cardPile} was created by a different table.
     */
    @GuardedBy( "lock_" )
    private void addCardPileInternal(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;
        assert Thread.holdsLock( lock_ );

        final CardPile typedCardPile = (CardPile)cardPile;
        assertArgumentLegal( typedCardPile.getTable() == null, "cardPile", Messages.Table_addCardPileInternal_cardPile_owned ); //$NON-NLS-1$
        assertArgumentLegal( typedCardPile.getTableContext() == tableContext_, "cardPile", Messages.Table_addCardPileInternal_cardPile_createdByDifferentTable ); //$NON-NLS-1$

        cardPiles_.add( typedCardPile );
        typedCardPile.setTable( this );

        pendingEventNotifications_.offer( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireCardPileAdded( cardPile );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.ITable#addTableListener(org.gamegineer.table.core.ITableListener)
     */
    @Override
    public void addTableListener(
        final ITableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.Table_addTableListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.ITable#createCard(org.gamegineer.table.core.ICardSurfaceDesign, org.gamegineer.table.core.ICardSurfaceDesign)
     */
    @Override
    public ICard createCard(
        final ICardSurfaceDesign backDesign,
        final ICardSurfaceDesign faceDesign )
    {
        final Card card = new Card( tableContext_ );
        card.setSurfaceDesigns( backDesign, faceDesign );
        return card;
    }

    /*
     * @see org.gamegineer.table.core.ITable#createCardPile(org.gamegineer.table.core.ICardPileBaseDesign)
     */
    @Override
    public ICardPile createCardPile(
        final ICardPileBaseDesign baseDesign )
    {
        final CardPile cardPile = new CardPile( tableContext_ );
        cardPile.setBaseDesign( baseDesign );
        return cardPile;
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
            final List<Object> cardPileMementos = new ArrayList<Object>( cardPiles_.size() );
            for( final ICardPile cardPile : cardPiles_ )
            {
                cardPileMementos.add( cardPile.createMemento() );
            }
            memento.put( CARD_PILES_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( cardPileMementos ) );
        }

        return Collections.unmodifiableMap( memento );
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
        assert !Thread.holdsLock( lock_ );

        final TableContentChangedEvent event = new TableContentChangedEvent( this, cardPile );
        for( final ITableListener listener : listeners_ )
        {
            try
            {
                listener.cardPileAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Table_cardPileAdded_unexpectedException, e );
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
        assert !Thread.holdsLock( lock_ );

        final TableContentChangedEvent event = new TableContentChangedEvent( this, cardPile );
        for( final ITableListener listener : listeners_ )
        {
            try
            {
                listener.cardPileRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Table_cardPileRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Fires all pending event notifications.
     */
    private void firePendingEventNotifications()
    {
        assert !Thread.holdsLock( lock_ );

        Runnable notification = null;
        while( (notification = pendingEventNotifications_.poll()) != null )
        {
            notification.run();
        }
    }

    /**
     * Creates a new instance of the {@code Table} class from the specified
     * memento.
     * 
     * @param tableContext
     *        The table context associated with the new table; must not be
     *        {@code null}.
     * @param memento
     *        The memento representing the initial table state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Table} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Table fromMemento(
        /* @NonNull */
        final TableContext tableContext,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableContext != null;
        assert memento != null;

        final Table table = new Table( tableContext );

        @SuppressWarnings( "unchecked" )
        final List<Object> cardPileMementos = MementoUtils.getAttribute( memento, CARD_PILES_MEMENTO_ATTRIBUTE_NAME, List.class );
        for( final Object cardPileMemento : cardPileMementos )
        {
            table.addCardPile( CardPile.fromMemento( table.tableContext_, cardPileMemento ) );
        }

        return table;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCardPile(int)
     */
    @Override
    public ICardPile getCardPile(
        final int index )
    {
        synchronized( lock_ )
        {
            assertArgumentLegal( (index >= 0) && (index < cardPiles_.size()), "index", Messages.Table_getCardPileFromIndex_index_outOfRange ); //$NON-NLS-1$

            return cardPiles_.get( index );
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
            for( int index = cardPiles_.size() - 1; index >= 0; --index )
            {
                final ICardPile cardPile = cardPiles_.get( index );
                if( cardPile.getBounds().contains( location ) )
                {
                    return cardPile;
                }
            }
        }

        return null;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCardPileCount()
     */
    @Override
    public int getCardPileCount()
    {
        synchronized( lock_ )
        {
            return cardPiles_.size();
        }
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCardPileIndex(org.gamegineer.table.core.ICardPile)
     */
    @Override
    public int getCardPileIndex(
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        final int index;
        synchronized( lock_ )
        {
            index = cardPiles_.indexOf( cardPile );
        }

        assertArgumentLegal( index != -1, "cardPile", Messages.Table_getCardPileIndex_cardPile_notOwned ); //$NON-NLS-1$
        return index;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCardPiles()
     */
    @Override
    public List<ICardPile> getCardPiles()
    {
        synchronized( lock_ )
        {
            return new ArrayList<ICardPile>( cardPiles_ );
        }
    }

    /**
     * Gets the table context.
     * 
     * @return The table context; never {@code null}.
     */
    /* @NonNull */
    TableContext getTableContext()
    {
        return tableContext_;
    }

    /*
     * @see org.gamegineer.table.core.ITable#removeCardPile(org.gamegineer.table.core.ICardPile)
     */
    @Override
    public void removeCardPile(
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            removeCardPileInternal( cardPile );
        }

        firePendingEventNotifications();
    }

    /**
     * Removes the specified card pile from this table.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPile} is not contained in this table.
     */
    @GuardedBy( "lock_" )
    private void removeCardPileInternal(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;
        assertArgumentLegal( cardPile.getTable() == this, "cardPile", Messages.Table_removeCardPileInternal_cardPile_notOwned ); //$NON-NLS-1$
        assert Thread.holdsLock( lock_ );

        cardPiles_.remove( cardPile );

        pendingEventNotifications_.offer( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireCardPileRemoved( cardPile );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.ITable#removeCardPiles()
     */
    @Override
    public List<ICardPile> removeCardPiles()
    {
        final List<ICardPile> removedCardPiles;
        synchronized( lock_ )
        {
            removedCardPiles = removeCardPilesInternal();
        }

        firePendingEventNotifications();

        return removedCardPiles;
    }

    /**
     * Removes all card piles from this table.
     * 
     * @return The collection of card piles removed from this table; never
     *         {@code null}. The card piles are returned in the order they were
     *         added to the table from oldest to newest.
     */
    @GuardedBy( "lock_" )
    /* @NonNull */
    private List<ICardPile> removeCardPilesInternal()
    {
        assert Thread.holdsLock( lock_ );

        final List<CardPile> removedCardPiles = new ArrayList<CardPile>( cardPiles_ );
        cardPiles_.clear();
        for( final CardPile cardPile : removedCardPiles )
        {
            cardPile.setTable( null );
        }

        if( !removedCardPiles.isEmpty() )
        {
            pendingEventNotifications_.offer( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    for( final ICardPile cardPile : removedCardPiles )
                    {
                        fireCardPileRemoved( cardPile );
                    }
                }
            } );
        }

        return new ArrayList<ICardPile>( removedCardPiles );
    }

    /*
     * @see org.gamegineer.table.core.ITable#removeTableListener(org.gamegineer.table.core.ITableListener)
     */
    @Override
    public void removeTableListener(
        final ITableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.Table_removeTableListener_listener_notRegistered ); //$NON-NLS-1$
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

        final Table table = fromMemento( tableContext_, memento );

        synchronized( lock_ )
        {
            removeCardPilesInternal();
            for( final ICardPile cardPile : table.removeCardPiles() )
            {
                addCardPileInternal( cardPile );
            }
        }

        firePendingEventNotifications();
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Table[" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            sb.append( "cardPiles_.size()=" ); //$NON-NLS-1$
            sb.append( cardPiles_.size() );
        }

        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }
}
