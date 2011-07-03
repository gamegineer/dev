/*
 * LocalNodeTable.java
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
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.internal.net.Loggers;

/**
 * Adapts an instance of {@link ITable} to {@link INetworkTable}.
 */
@ThreadSafe
final class LocalNodeTable
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
    private final INetworkTableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalNodeTable} class.
     * 
     * @param tableManager
     *        The table manager for the local table network node; must not be
     *        {@code null}.
     * @param table
     *        The local table; must not be {@code null}.
     */
    LocalNodeTable(
        /* @NonNull */
        final INetworkTableManager tableManager,
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

    /**
     * Initializes the listeners for the local table.
     */
    private void initializeListeners()
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

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setCardOrientation(int, int, org.gamegineer.table.core.CardOrientation)
     */
    @Override
    public void setCardOrientation(
        final int cardPileIndex,
        final int cardIndex,
        final CardOrientation cardOrientation )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardOrientation, "cardOrientation" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            ignoreEvents_ = true;

            final ICardPile cardPile = table_.getCardPile( cardPileIndex );
            if( cardPile != null )
            {
                final ICard card = cardPile.getCard( cardIndex );
                if( card != null )
                {
                    card.setOrientation( cardOrientation );
                }
            }

            ignoreEvents_ = false;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setTableMemento(java.lang.Object)
     */
    @Override
    public void setTableMemento(
        final Object tableMemento )
    {
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            ignoreEvents_ = true;

            try
            {
                table_.setMemento( tableMemento );
            }
            catch( final MementoException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.LocalNodeTable_setTableMemento_failed, e );
            }
            finally
            {
                ignoreEvents_ = false;
            }
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
        public void cardLocationChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
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

            final ICard card = event.getCard();
            final CardOrientation cardOrientation = card.getOrientation();
            final int cardIndex = card.getCardPile().getCardIndex( card );
            final int cardPileIndex = card.getCardPile().getTable().getCardPileIndex( card.getCardPile() );
            tableManager_.setCardOrientation( LocalNodeTable.this, cardPileIndex, cardIndex, cardOrientation );
        }

        /*
         * @see org.gamegineer.table.core.ICardListener#cardSurfaceDesignsChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        public void cardSurfaceDesignsChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
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
            }
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        public void cardPileBaseDesignChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileBoundsChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        public void cardPileBoundsChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardRemoved(
            final CardPileContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final ICard card = event.getCard();
                final ICardListener cardListener = cardListeners_.remove( card );
                card.removeCardListener( cardListener );
            }
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
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileRemoved(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final ICardPile cardPile = event.getCardPile();
                for( final ICard card : cardPile.getCards() )
                {
                    final ICardListener cardListener = cardListeners_.remove( card );
                    card.removeCardListener( cardListener );
                }

                final ICardPileListener cardPileListener = cardPileListeners_.remove( cardPile );
                cardPile.removeCardPileListener( cardPileListener );
            }
        }
    }
}
