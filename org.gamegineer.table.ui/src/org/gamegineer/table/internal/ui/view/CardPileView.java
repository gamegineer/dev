/*
 * CardPileView.java
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
 * Created on Jan 26, 2010 at 11:47:48 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.internal.ui.model.CardPileModelEvent;
import org.gamegineer.table.internal.ui.model.ICardPileModelListener;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;
import org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry;

/**
 * A view of a card pile.
 */
@NotThreadSafe
final class CardPileView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The horizontal padding between the focus border and the card pile in
     * table coordinates.
     */
    private static final int HORIZONTAL_PADDING = 2;

    /**
     * The vertical padding between the focus border and the card pile in table
     * coordinates.
     */
    private static final int VERTICAL_PADDING = 2;

    /** The card pile base design user interface. */
    private final ICardPileBaseDesignUI baseDesignUI_;

    /** The card pile listener for this view. */
    private ICardPileListener cardPileListener_;

    /** The card pile model listener for this view. */
    private ICardPileModelListener cardPileModelListener_;

    /** The collection of card views. */
    private final Map<ICard, CardView> cardViews_;

    /** The dirty bounds of this view in table coordinates. */
    private final Rectangle dirtyBounds_;

    /** The model associated with this view. */
    private final CardPileModel model_;

    /** The table view that owns this view. */
    private TableView tableView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     * @param baseDesignUI
     *        The card pile base design user interface; must not be {@code null}
     *        .
     */
    CardPileView(
        /* @NonNull */
        final CardPileModel model,
        /* @NonNull */
        final ICardPileBaseDesignUI baseDesignUI )
    {
        assert model != null;
        assert baseDesignUI != null;

        baseDesignUI_ = baseDesignUI;
        cardPileListener_ = null;
        cardViews_ = new IdentityHashMap<ICard, CardView>();
        dirtyBounds_ = new Rectangle();
        model_ = model;
        tableView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when a new card is added to the card pile.
     * 
     * @param card
     *        The added card; must not be {@code null}.
     */
    private void cardAdded(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        if( isInitialized() )
        {
            if( !cardViews_.containsKey( card ) )
            {
                final CardView view = createCardView( card );
                tableView_.repaintTable( view.getBounds() );
            }
        }
    }

    /**
     * Invoked after the card pile base design has changed.
     */
    private void cardPileBaseDesignChanged()
    {
        if( isInitialized() )
        {
            tableView_.repaintTable( getDirtyBounds() );
        }
    }

    /**
     * Invoked after the card pile bounds have changed.
     */
    private void cardPileBoundsChanged()
    {
        if( isInitialized() )
        {
            final Rectangle viewBounds = getBounds();
            dirtyBounds_.add( viewBounds );
            tableView_.repaintTable( getDirtyBounds() );
            dirtyBounds_.setBounds( viewBounds );
        }
    }

    /**
     * Invoked after the card pile model has gained or lost the logical focus.
     */
    private void cardPileModelFocusChanged()
    {
        if( isInitialized() )
        {
            tableView_.repaintTable( getDirtyBounds() );
        }
    }

    /**
     * Invoked when a card is removed from the card pile.
     * 
     * @param card
     *        The removed card; must not be {@code null}.
     */
    private void cardRemoved(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        if( isInitialized() )
        {
            final CardView view = cardViews_.remove( card );
            if( view != null )
            {
                tableView_.repaintTable( view.getBounds() );
                view.uninitialize();
            }
        }
    }

    /**
     * Creates a card view for the specified card.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @return The card view; never {@code null}.
     */
    /* @NonNull */
    private CardView createCardView(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;
        assert isInitialized();

        final ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry = Activator.getDefault().getCardSurfaceDesignUIRegistry();
        assert cardSurfaceDesignUIRegistry != null;
        final ICardSurfaceDesignUI backDesignUI = cardSurfaceDesignUIRegistry.getCardSurfaceDesignUI( card.getBackDesign().getId() );
        final ICardSurfaceDesignUI faceDesignUI = cardSurfaceDesignUIRegistry.getCardSurfaceDesignUI( card.getFaceDesign().getId() );
        final CardView view = new CardView( model_.getCardModel( card ), backDesignUI, faceDesignUI );
        cardViews_.put( card, view );
        view.initialize( tableView_ ); // TODO: Change to accept CardPileView?
        return view;
    }

    /**
     * Gets the bounds of this view in table coordinates.
     * 
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    Rectangle getBounds()
    {
        final Rectangle bounds = model_.getCardPile().getBounds();
        bounds.grow( HORIZONTAL_PADDING, VERTICAL_PADDING );
        return bounds;
    }

    /**
     * Gets the dirty bounds of this view in table coordinates.
     * 
     * <p>
     * The dirty bounds represents the largest region this view has occupied
     * since the last paint operation. It is at least as large as the bounds
     * returned from {@link #getBounds()}.
     * </p>
     * 
     * @return The dirty bounds of this view in table coordinates; never {@code
     *         null}.
     */
    /* @NonNull */
    Rectangle getDirtyBounds()
    {
        return new Rectangle( dirtyBounds_ );
    }

    /**
     * Initializes this view.
     * 
     * <p>
     * This method must only be called when the view is uninitialized.
     * </p>
     * 
     * @param tableView
     *        The table view that owns this view; must not be {@code null}.
     */
    void initialize(
        /* @NonNull */
        final TableView tableView )
    {
        assert tableView != null;
        assert !isInitialized();

        tableView_ = tableView;
        dirtyBounds_.setBounds( getBounds() );
        cardPileModelListener_ = new CardPileModelListener();
        model_.addCardPileModelListener( cardPileModelListener_ );
        cardPileListener_ = new CardPileListener();
        model_.getCardPile().addCardPileListener( cardPileListener_ );

        for( final ICard card : model_.getCardPile().getCards() )
        {
            createCardView( card );
        }
    }

    /**
     * Indicates this view has been initialized.
     * 
     * @return {@code true} if this view has been initialized; otherwise {@code
     *         false}.
     */
    private boolean isInitialized()
    {
        return tableView_ != null;
    }

    /**
     * Paints this view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     */
    void paint(
        /* @NonNull */
        final Graphics g )
    {
        assert g != null;
        assert isInitialized();

        final Rectangle viewBounds = getBounds();

        final List<ICard> cards = model_.getCardPile().getCards();
        if( cards.isEmpty() )
        {
            baseDesignUI_.getIcon().paintIcon( tableView_, g, viewBounds.x + HORIZONTAL_PADDING, viewBounds.y + VERTICAL_PADDING );
        }
        else
        {
            for( final ICard card : cards )
            {
                final CardView cardView = cardViews_.get( card );
                if( cardView != null )
                {
                    cardView.paint( g );
                }
            }
        }

        if( model_.isFocused() )
        {
            final Color oldColor = g.getColor();
            g.setColor( Color.GREEN );
            g.drawRect( viewBounds.x, viewBounds.y, viewBounds.width - 1, viewBounds.height - 1 );
            g.setColor( oldColor );
        }
    }

    /**
     * Uninitializes this view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     */
    void uninitialize()
    {
        assert isInitialized();

        for( final CardView view : cardViews_.values() )
        {
            view.uninitialize();
        }
        cardViews_.clear();

        model_.getCardPile().removeCardPileListener( cardPileListener_ );
        cardPileListener_ = null;
        model_.removeCardPileModelListener( cardPileModelListener_ );
        cardPileModelListener_ = null;
        tableView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card pile listener for the card pile view.
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
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.CardPileListener#cardAdded(org.gamegineer.table.core.CardPileContentChangedEvent)
         */
        @Override
        public void cardAdded(
            final CardPileContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.cardAdded( event.getCard() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.CardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        public void cardPileBaseDesignChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.cardPileBaseDesignChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.CardPileListener#cardPileBoundsChanged(org.gamegineer.table.core.CardPileEvent)
         */
        @Override
        public void cardPileBoundsChanged(
            final CardPileEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.cardPileBoundsChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.CardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
         */
        @Override
        public void cardRemoved(
            final CardPileContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.cardRemoved( event.getCard() );
                }
            } );
        }
    }

    /**
     * A card pile model listener for the card pile view.
     */
    @Immutable
    private final class CardPileModelListener
        extends org.gamegineer.table.internal.ui.model.CardPileModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardPileModelListener}
         * class.
         */
        CardPileModelListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.CardPileModelListener#cardPileModelFocusChanged(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
         */
        @Override
        public void cardPileModelFocusChanged(
            final CardPileModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.cardPileModelFocusChanged();
                }
            } );
        }
    }
}
