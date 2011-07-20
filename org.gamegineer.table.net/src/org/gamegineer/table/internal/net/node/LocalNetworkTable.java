/*
 * LocalNetworkTable.java
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
 * Created on Jun 27, 2011 at 8:14:12 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;

/**
 * Adapts a local table to {@link INetworkTable}.
 */
@ThreadSafe
final class LocalNetworkTable
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of local card listeners. The key is the local card. The
     * value is the card listener.
     */
    @GuardedBy( "lock_" )
    private final Map<ICard, ICardListener> cardListeners_;

    /**
     * The collection of local card pile listeners. The key is the local card
     * pile. The value is the card pile listener.
     */
    @GuardedBy( "lock_" )
    private final Map<ICardPile, ICardPileListener> cardPileListeners_;

    /** Indicates events fired by the local table should be ignored. */
    @GuardedBy( "lock_" )
    private boolean ignoreEvents_;

    /** The instance lock. */
    private final Object lock_;

    /** The local table. */
    private final ITable table_;

    /** The local table listener. */
    @GuardedBy( "lock_" )
    private ITableListener tableListener_;

    /** The table manager for the local table network node. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalNetworkTable} class.
     * 
     * @param tableManager
     *        The table manager for the local table network node; must not be
     *        {@code null}.
     * @param table
     *        The local table; must not be {@code null}.
     */
    LocalNetworkTable(
        /* @NonNull */
        final ITableManager tableManager,
        /* @NonNull */
        final ITable table )
    {
        assert tableManager != null;
        assert table != null;

        cardListeners_ = new IdentityHashMap<ICard, ICardListener>();
        cardPileListeners_ = new IdentityHashMap<ICardPile, ICardPileListener>();
        ignoreEvents_ = false;
        lock_ = new Object();
        table_ = table;
        tableListener_ = null;
        tableManager_ = tableManager;

        initializeListeners();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#dispose()
     */
    @Override
    public void dispose()
    {
        uninitializeListeners();
    }

    /**
     * Gets the table lock.
     * 
     * @return The table lock; never {@code null}.
     */
    /* @NonNull */
    private Lock getTableLock()
    {
        return table_.getLock();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementCardPileState(int, org.gamegineer.table.internal.net.node.CardPileIncrement)
     */
    @Override
    public void incrementCardPileState(
        final int cardPileIndex,
        final CardPileIncrement cardPileIncrement )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardPileIncrement, "cardPileIncrement" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final boolean oldIgnoreEvents = ignoreEvents_;
            ignoreEvents_ = true;
            NetworkTableUtils.incrementCardPileState( table_, cardPileIndex, cardPileIncrement );
            ignoreEvents_ = oldIgnoreEvents;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementCardState(int, int, org.gamegineer.table.internal.net.node.CardIncrement)
     */
    @Override
    public void incrementCardState(
        final int cardPileIndex,
        final int cardIndex,
        final CardIncrement cardIncrement )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardIncrement, "cardIncrement" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final boolean oldIgnoreEvents = ignoreEvents_;
            ignoreEvents_ = true;
            NetworkTableUtils.incrementCardState( table_, cardPileIndex, cardIndex, cardIncrement );
            ignoreEvents_ = oldIgnoreEvents;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementTableState(org.gamegineer.table.internal.net.node.TableIncrement)
     */
    @Override
    public void incrementTableState(
        final TableIncrement tableIncrement )
    {
        assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final boolean oldIgnoreEvents = ignoreEvents_;
            ignoreEvents_ = true;
            NetworkTableUtils.incrementTableState( table_, tableIncrement );
            ignoreEvents_ = oldIgnoreEvents;
        }
    }

    /**
     * Initializes the listeners for the local table.
     */
    private void initializeListeners()
    {
        getTableLock().lock();
        try
        {
            tableListener_ = new TableListener();
            table_.addTableListener( tableListener_ );

            for( final ICardPile cardPile : table_.getCardPiles() )
            {
                final ICardPileListener cardPileListener = new CardPileListener();
                cardPile.addCardPileListener( cardPileListener );
                cardPileListeners_.put( cardPile, cardPileListener );

                for( final ICard card : cardPile.getCards() )
                {
                    final ICardListener cardListener = new CardListener();
                    card.addCardListener( cardListener );
                    cardListeners_.put( card, cardListener );
                }
            }
        }
        finally
        {
            getTableLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setTableState(java.lang.Object)
     */
    @Override
    public void setTableState(
        final Object tableMemento )
    {
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final boolean oldIgnoreEvents = ignoreEvents_;
            ignoreEvents_ = true;
            NetworkTableUtils.setTableState( table_, tableMemento );
            ignoreEvents_ = oldIgnoreEvents;
        }
    }

    /**
     * Uninitializes the listeners for the local table.
     */
    private void uninitializeListeners()
    {
        getTableLock().lock();
        try
        {
            if( tableListener_ != null )
            {
                table_.removeTableListener( tableListener_ );
                tableListener_ = null;
            }

            for( final ICardPile cardPile : table_.getCardPiles() )
            {
                final ICardPileListener cardPileListener = cardPileListeners_.remove( cardPile );
                if( cardPileListener != null )
                {
                    cardPile.removeCardPileListener( cardPileListener );
                }

                for( final ICard card : cardPile.getCards() )
                {
                    final ICardListener cardListener = cardListeners_.remove( card );
                    if( cardListener != null )
                    {
                        card.removeCardListener( cardListener );
                    }
                }
            }
        }
        finally
        {
            getTableLock().unlock();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card listener for the local table adapter.
     */
    @Immutable
    private final class CardListener
        implements ICardListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardListener} class.
         */
        CardListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ICardListener#cardLocationChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardLocationChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex, cardIndex;
            final CardIncrement cardIncrement = new CardIncrement();
            getTableLock().lock();
            try
            {
                final ICard card = event.getCard();
                cardIndex = card.getCardPile().getCardIndex( card );
                cardPileIndex = card.getCardPile().getTable().getCardPileIndex( card.getCardPile() );
                cardIncrement.setLocation( card.getLocation() );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardState( LocalNetworkTable.this, cardPileIndex, cardIndex, cardIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ICardListener#cardOrientationChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardOrientationChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex, cardIndex;
            final CardIncrement cardIncrement = new CardIncrement();
            getTableLock().lock();
            try
            {
                final ICard card = event.getCard();
                cardIndex = card.getCardPile().getCardIndex( card );
                cardPileIndex = card.getCardPile().getTable().getCardPileIndex( card.getCardPile() );
                cardIncrement.setOrientation( card.getOrientation() );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardState( LocalNetworkTable.this, cardPileIndex, cardIndex, cardIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ICardListener#cardSurfaceDesignsChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardSurfaceDesignsChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex, cardIndex;
            final CardIncrement cardIncrement = new CardIncrement();
            getTableLock().lock();
            try
            {
                final ICard card = event.getCard();
                cardIndex = card.getCardPile().getCardIndex( card );
                cardPileIndex = card.getCardPile().getTable().getCardPileIndex( card.getCardPile() );
                cardIncrement.setSurfaceDesigns( card.getBackDesign(), card.getFaceDesign() );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardState( LocalNetworkTable.this, cardPileIndex, cardIndex, cardIncrement );
        }
    }

    /**
     * A card pile listener for the local table adapter.
     */
    @Immutable
    private final class CardPileListener
        implements ICardPileListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardPileListener} class.
         */
        CardPileListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardAdded(org.gamegineer.table.core.CardPileContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardAdded(
            final CardPileContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final ICard card = event.getCard();
                final ICardListener cardListener = new CardListener();
                card.addCardListener( cardListener );
                cardListeners_.put( card, cardListener );

                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                cardPileIndex = cardPile.getTable().getCardPileIndex( cardPile );
                cardPileIncrement.setAddedCardMementos( Collections.singletonList( event.getCard().createMemento() ) );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileBaseDesignChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                cardPileIndex = cardPile.getTable().getCardPileIndex( cardPile );
                cardPileIncrement.setBaseDesign( cardPile.getBaseDesign() );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileBoundsChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileBoundsChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                cardPileIndex = cardPile.getTable().getCardPileIndex( cardPile );
                cardPileIncrement.setBaseLocation( cardPile.getBaseLocation() );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileLayoutChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileLayoutChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                cardPileIndex = cardPile.getTable().getCardPileIndex( cardPile );
                cardPileIncrement.setLayout( cardPile.getLayout() );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
         */
        @Override
        @SuppressWarnings( {
            "boxing", "synthetic-access"
        } )
        public void cardRemoved(
            final CardPileContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final ICard card = event.getCard();
                final ICardListener cardListener = cardListeners_.remove( card );
                if( cardListener != null )
                {
                    card.removeCardListener( cardListener );
                }

                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final int cardPileIndex;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                cardPileIndex = cardPile.getTable().getCardPileIndex( cardPile );
                cardPileIncrement.setRemovedCardCount( 1 );
            }
            finally
            {
                getTableLock().unlock();
            }

            tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
        }
    }

    /**
     * A table listener for the local table adapter.
     */
    @Immutable
    private final class TableListener
        implements ITableListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableListener} class.
         */
        TableListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileAdded(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                getTableLock().lock();
                try
                {
                    final ICardPile cardPile = event.getCardPile();
                    final ICardPileListener cardPileListener = new CardPileListener();
                    cardPile.addCardPileListener( cardPileListener );
                    cardPileListeners_.put( cardPile, cardPileListener );

                    for( final ICard card : cardPile.getCards() )
                    {
                        final ICardListener cardListener = new CardListener();
                        card.addCardListener( cardListener );
                        cardListeners_.put( card, cardListener );
                    }
                }
                finally
                {
                    getTableLock().unlock();
                }

                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final TableIncrement tableIncrement = new TableIncrement();
            tableIncrement.setAddedCardPileMementos( Collections.singletonList( event.getCardPile().createMemento() ) );

            tableManager_.incrementTableState( LocalNetworkTable.this, tableIncrement );
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( {
            "boxing", "synthetic-access"
        } )
        public void cardPileRemoved(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                getTableLock().lock();
                try
                {
                    final ICardPile cardPile = event.getCardPile();
                    for( final ICard card : cardPile.getCards() )
                    {
                        final ICardListener cardListener = cardListeners_.remove( card );
                        if( cardListener != null )
                        {
                            card.removeCardListener( cardListener );
                        }
                    }

                    final ICardPileListener cardPileListener = cardPileListeners_.remove( cardPile );
                    if( cardPileListener != null )
                    {
                        cardPile.removeCardPileListener( cardPileListener );
                    }
                }
                finally
                {
                    getTableLock().unlock();
                }

                if( ignoreEvents_ )
                {
                    return;
                }
            }

            final TableIncrement tableIncrement = new TableIncrement();
            tableIncrement.setRemovedCardPileIndexes( Collections.singletonList( event.getCardPileIndex() ) );

            tableManager_.incrementTableState( LocalNetworkTable.this, tableIncrement );
        }
    }
}
