/*
 * CardPileView.java
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
 * Created on Jan 26, 2010 at 11:47:48 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.internal.ui.Services;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.internal.ui.model.CardPileModelEvent;
import org.gamegineer.table.internal.ui.model.ICardPileModelListener;
import org.gamegineer.table.ui.ICardDesignUI;
import org.gamegineer.table.ui.ICardPileDesignUI;

/**
 * A view of a card pile.
 */
@NotThreadSafe
final class CardPileView
    implements ICardPileListener, ICardPileModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The current bounds of this view in table coordinates. */
    private Rectangle bounds_;

    /** The card pile design user interface. */
    private final ICardPileDesignUI cardPileDesignUI_;

    /** The collection of card views. */
    private final Map<ICard, CardView> cardViews_;

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
     * @param cardPileDesignUI
     *        The card pile design user interface; must not be {@code null}.
     */
    CardPileView(
        /* @NonNull */
        final CardPileModel model,
        /* @NonNull */
        final ICardPileDesignUI cardPileDesignUI )
    {
        assert model != null;
        assert cardPileDesignUI != null;

        bounds_ = null;
        cardViews_ = new IdentityHashMap<ICard, CardView>();
        model_ = model;
        cardPileDesignUI_ = cardPileDesignUI;
        tableView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardAdded(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    public void cardAdded(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardAdded( event.getCard() );
            }
        } );
    }

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

        final ICardDesignUI backDesignUI = Services.getDefault().getCardDesignUIRegistry().getCardDesignUI( card.getBackDesign().getId() );
        final ICardDesignUI faceDesignUI = Services.getDefault().getCardDesignUIRegistry().getCardDesignUI( card.getFaceDesign().getId() );
        final CardView view = new CardView( model_.getCardModel( card ), backDesignUI, faceDesignUI );
        cardViews_.put( card, view );
        view.initialize( tableView_ ); // TODO: Change to accept CardPileView?
        tableView_.repaint( view.getBounds() );
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardPileBoundsChanged(org.gamegineer.table.core.CardPileEvent)
     */
    public void cardPileBoundsChanged(
        final CardPileEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardPileBoundsChanged();
            }
        } );
    }

    /**
     * Invoked after the card pile bounds have changed.
     */
    private void cardPileBoundsChanged()
    {
        final Rectangle newBounds = getBounds();
        tableView_.repaint( newBounds.union( bounds_ ) );
        bounds_ = newBounds;
    }

    /**
     * Invoked after the card pile has gained or lost the logical focus.
     */
    private void cardPileFocusChanged()
    {
        tableView_.repaint( getBounds() );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileFocusGained(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileFocusGained(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardPileFocusChanged();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileFocusLost(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileFocusLost(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardPileFocusChanged();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    public void cardRemoved(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardRemoved( event.getCard() );
            }
        } );
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

        final CardView view = cardViews_.remove( card );
        if( view != null )
        {
            tableView_.repaint( view.getBounds() );
            view.uninitialize();
        }
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
        bounds.grow( 2, 2 );
        return bounds;
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
        assert tableView_ == null;

        tableView_ = tableView;
        bounds_ = getBounds();
        model_.addCardPileModelListener( this );
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
        assert tableView_ != null;

        final Rectangle cardPileBounds = model_.getCardPile().getBounds();
        cardPileDesignUI_.getIcon().paintIcon( tableView_, g, cardPileBounds.x, cardPileBounds.y );

        if( model_.isFocused() )
        {
            final Rectangle viewBounds = getBounds();
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
        assert tableView_ != null;

        model_.getCardPile().removeCardPileListener( this );
        model_.removeCardPileModelListener( this );
        tableView_ = null;
    }
}
