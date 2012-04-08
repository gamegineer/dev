/*
 * LocalNetworkTable.java
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
 * Created on Jun 27, 2011 at 8:14:12 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.internal.net.Loggers;

/**
 * Adapts a local table to {@link INetworkTable}.
 */
@NotThreadSafe
final class LocalNetworkTable
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local card component listener. */
    private final IComponentListener cardComponentListener_;

    /** The local card pile listener. */
    private final ICardPileListener cardPileListener_;

    /** Indicates events fired by the local table should be ignored. */
    private boolean ignoreEvents_;

    /** The node layer. */
    private final INodeLayer nodeLayer_;

    /** The local table. */
    private final ITable table_;

    /** The local table listener. */
    private final ITableListener tableListener_;

    /** The table manager for the local table network node. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalNetworkTable} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param tableManager
     *        The table manager for the local table network node; must not be
     *        {@code null}.
     * @param table
     *        The local table; must not be {@code null}.
     */
    LocalNetworkTable(
        /* @NonNull */
        final INodeLayer nodeLayer,
        /* @NonNull */
        final ITableManager tableManager,
        /* @NonNull */
        final ITable table )
    {
        assert nodeLayer != null;
        assert nodeLayer.isNodeLayerThread();
        assert tableManager != null;
        assert table != null;

        cardComponentListener_ = new ComponentListenerProxy( new CardComponentListener() );
        cardPileListener_ = new CardPileListenerProxy( new CardPileListener() );
        ignoreEvents_ = false;
        nodeLayer_ = nodeLayer;
        table_ = table;
        tableListener_ = new TableListenerProxy( new TableListener() );
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
        assert nodeLayer_.isNodeLayerThread();

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
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.incrementCardPileState( table_, cardPileIndex, cardPileIncrement );
        ignoreEvents_ = oldIgnoreEvents;
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
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.incrementCardState( table_, cardPileIndex, cardIndex, cardIncrement );
        ignoreEvents_ = oldIgnoreEvents;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementTableState(org.gamegineer.table.internal.net.node.TableIncrement)
     */
    @Override
    public void incrementTableState(
        final TableIncrement tableIncrement )
    {
        assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.incrementTableState( table_, tableIncrement );
        ignoreEvents_ = oldIgnoreEvents;
    }

    /**
     * Initializes the listeners for the local table.
     */
    private void initializeListeners()
    {
        getTableLock().lock();
        try
        {
            table_.addTableListener( tableListener_ );

            for( final ICardPile cardPile : table_.getCardPiles() )
            {
                cardPile.addCardPileListener( cardPileListener_ );

                for( final IComponent component : cardPile.getComponents() )
                {
                    ((ICard)component).addComponentListener( cardComponentListener_ ); // FIXME: remove cast
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
        assert nodeLayer_.isNodeLayerThread();

        final boolean oldIgnoreEvents = ignoreEvents_;
        ignoreEvents_ = true;
        NetworkTableUtils.setTableState( table_, tableMemento );
        ignoreEvents_ = oldIgnoreEvents;
    }

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     */
    private void syncExec(
        /* @NonNull */
        final Runnable task )
    {
        assert task != null;

        try
        {
            nodeLayer_.syncExec( task );
        }
        catch( final ExecutionException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.LocalNetworkTable_syncExec_error, e );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.LocalNetworkTable_syncExec_interrupted, e );
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
            table_.removeTableListener( tableListener_ );

            for( final ICardPile cardPile : table_.getCardPiles() )
            {
                cardPile.removeCardPileListener( cardPileListener_ );

                for( final IComponent component : cardPile.getComponents() )
                {
                    ((ICard)component).removeComponentListener( cardComponentListener_ ); // FIXME: remove cast
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
     * A card component listener for the local table adapter.
     */
    @Immutable
    private final class CardComponentListener
        implements IComponentListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardComponentListener}
         * class.
         */
        CardComponentListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            // do nothing

            // In the current implementation, handling this event is unnecessary because a
            // card can only be moved by moving a card pile that contains the card. Thus,
            // when the corresponding card pile bounds changed event is fired, it will
            // automatically set the new card location when the card pile location is changed.
            //
            // If we send both the card pile bounds changed event and the card location
            // changed event over the network, the card movement on remote tables will appear
            // "jumpy" because two ICard.setLocation() calls will be made that may be a few
            // pixels off.
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1, cardIndex = -1;
            final CardIncrement cardIncrement = new CardIncrement();
            getTableLock().lock();
            try
            {
                final ICard card = (ICard)event.getComponent(); // FIXME: remove cast
                final ICardPile cardPile = card.getCardPile();
                if( cardPile != null )
                {
                    final ITable table = cardPile.getTable();
                    if( table != null )
                    {
                        cardIndex = cardPile.getComponentIndex( card );
                        cardPileIndex = table.getCardPileIndex( cardPile );
                        cardIncrement.setOrientation( card.getOrientation() );
                    }
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( (cardPileIndex != -1) && (cardIndex != -1) )
            {
                tableManager_.incrementCardState( LocalNetworkTable.this, cardPileIndex, cardIndex, cardIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1, cardIndex = -1;
            final CardIncrement cardIncrement = new CardIncrement();
            getTableLock().lock();
            try
            {
                final ICard card = (ICard)event.getComponent(); // FIXME: remove cast
                final ICardPile cardPile = card.getCardPile();
                if( cardPile != null )
                {
                    final ITable table = cardPile.getTable();
                    if( table != null )
                    {
                        cardIndex = cardPile.getComponentIndex( card );
                        cardPileIndex = table.getCardPileIndex( cardPile );
                        cardIncrement.setSurfaceDesigns( card.getBackDesign(), card.getFaceDesign() );
                    }
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( (cardPileIndex != -1) && (cardIndex != -1) )
            {
                tableManager_.incrementCardState( LocalNetworkTable.this, cardPileIndex, cardIndex, cardIncrement );
            }
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
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileBaseDesignChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                final ITable table = cardPile.getTable();
                if( table != null )
                {
                    cardPileIndex = table.getCardPileIndex( cardPile );
                    cardPileIncrement.setBaseDesign( cardPile.getBaseDesign() );
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( cardPileIndex != -1 )
            {
                tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
            }
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
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                final ITable table = cardPile.getTable();
                if( table != null )
                {
                    cardPileIndex = table.getCardPileIndex( cardPile );
                    cardPileIncrement.setLayout( cardPile.getLayout() );
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( cardPileIndex != -1 )
            {
                tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            final ICard card = (ICard)event.getComponent(); // FIXME: remove cast
            card.addComponentListener( cardComponentListener_ );

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = (ICardPile)event.getContainer(); // FIXME: remove cast
                final ITable table = cardPile.getTable();
                if( table != null )
                {
                    cardPileIndex = table.getCardPileIndex( cardPile );
                    cardPileIncrement.setAddedCardMementos( Collections.singletonList( card.createMemento() ) );
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( cardPileIndex != -1 )
            {
                tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = (ICardPile)event.getComponent(); // FIXME: remove cast
                final ITable table = cardPile.getTable();
                if( table != null )
                {
                    cardPileIndex = table.getCardPileIndex( cardPile );
                    cardPileIncrement.setBaseLocation( cardPile.getBaseLocation() );
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( cardPileIndex != -1 )
            {
                tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            // TODO
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( {
            "boxing", "synthetic-access"
        } )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
            assert nodeLayer_.isNodeLayerThread();

            final ICard card = (ICard)event.getComponent(); // FIXME: remove cast
            card.removeComponentListener( cardComponentListener_ );

            if( ignoreEvents_ )
            {
                return;
            }

            int cardPileIndex = -1;
            final CardPileIncrement cardPileIncrement = new CardPileIncrement();
            getTableLock().lock();
            try
            {
                final ICardPile cardPile = (ICardPile)event.getContainer(); // FIXME: remove cast
                final ITable table = cardPile.getTable();
                if( table != null )
                {
                    cardPileIndex = table.getCardPileIndex( cardPile );
                    cardPileIncrement.setRemovedCardCount( 1 );
                }
            }
            finally
            {
                getTableLock().unlock();
            }

            if( cardPileIndex != -1 )
            {
                tableManager_.incrementCardPileState( LocalNetworkTable.this, cardPileIndex, cardPileIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            // TODO
        }
    }

    /**
     * A proxy for instances of {@link ICardPileListener} that ensures all
     * methods are called on the associated node layer thread.
     */
    @Immutable
    private final class CardPileListenerProxy
        implements ICardPileListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual card pile listener. */
        private final ICardPileListener actualCardPileListener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardPileListenerProxy}
         * class.
         * 
         * @param actualCardPileListener
         *        The actual card pile listener; must not be {@code null}.
         */
        CardPileListenerProxy(
            /* @NonNull */
            final ICardPileListener actualCardPileListener )
        {
            assert actualCardPileListener != null;

            actualCardPileListener_ = actualCardPileListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileBaseDesignChanged(
            final CardPileEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.cardPileBaseDesignChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ICardPileListener#cardPileLayoutChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileLayoutChanged(
            final CardPileEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.cardPileLayoutChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.componentAdded( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.componentBoundsChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.componentOrientationChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.componentRemoved( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualCardPileListener_.componentSurfaceDesignChanged( event );
                }
            } );
        }
    }

    /**
     * A proxy for instances of {@link IComponentListener} that ensures all
     * methods are called on the associated node layer thread.
     */
    @Immutable
    private final class ComponentListenerProxy
        implements IComponentListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual component listener. */
        private IComponentListener actualComponentListener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentListenerProxy}
         * class.
         * 
         * @param actualComponentListener
         *        The actual component listener; must not be {@code null}.
         */
        ComponentListenerProxy(
            /* @NonNull */
            final IComponentListener actualComponentListener )
        {
            assert actualComponentListener != null;

            actualComponentListener_ = actualComponentListener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentBoundsChanged(
            @SuppressWarnings( "unused" )
            final ComponentEvent event )
        {
            // do nothing

            // In the current implementation, handling this event is unnecessary because a
            // card can only be moved by moving a card pile that contains the card. Thus,
            // when the corresponding card pile bounds changed event is fired, it will
            // automatically set the new card location when the card pile location is changed.
            //
            // If we send both the card pile bounds changed event and the card location
            // changed event over the network, the card movement on remote tables will appear
            // "jumpy" because two ICard.setLocation() calls will be made that may be a few
            // pixels off.
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualComponentListener_.componentOrientationChanged( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualComponentListener_.componentSurfaceDesignChanged( event );
                }
            } );
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
            assert nodeLayer_.isNodeLayerThread();

            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                cardPile.addCardPileListener( cardPileListener_ );

                for( final IComponent component : cardPile.getComponents() )
                {
                    ((ICard)component).addComponentListener( cardComponentListener_ ); // FIXME: remove cast
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
            assert nodeLayer_.isNodeLayerThread();

            getTableLock().lock();
            try
            {
                final ICardPile cardPile = event.getCardPile();
                for( final IComponent component : cardPile.getComponents() )
                {
                    ((ICard)component).removeComponentListener( cardComponentListener_ ); // FIXME: remove cast
                }

                cardPile.removeCardPileListener( cardPileListener_ );
            }
            finally
            {
                getTableLock().unlock();
            }

            if( ignoreEvents_ )
            {
                return;
            }

            final TableIncrement tableIncrement = new TableIncrement();
            tableIncrement.setRemovedCardPileIndexes( Collections.singletonList( event.getCardPileIndex() ) );

            tableManager_.incrementTableState( LocalNetworkTable.this, tableIncrement );
        }
    }

    /**
     * A proxy for instances of {@link ITableListener} that ensures all methods
     * are called on the associated node layer thread.
     */
    @Immutable
    private final class TableListenerProxy
        implements ITableListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual table listener. */
        private final ITableListener actualTableListener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableListenerProxy} class.
         * 
         * @param actualTableListener
         *        The actual table listener; must not be {@code null}.
         */
        TableListenerProxy(
            /* @NonNull */
            final ITableListener actualTableListener )
        {
            assert actualTableListener != null;

            actualTableListener_ = actualTableListener;
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
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualTableListener_.cardPileAdded( event );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileRemoved(
            final TableContentChangedEvent event )
        {
            syncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    actualTableListener_.cardPileRemoved( event );
                }
            } );
        }
    }
}
