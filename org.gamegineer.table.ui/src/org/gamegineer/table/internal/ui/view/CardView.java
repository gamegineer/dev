/*
 * CardView.java
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
 * Created on Oct 15, 2009 at 10:41:19 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.internal.ui.model.CardModel;
import org.gamegineer.table.internal.ui.model.ICardModelListener;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;

/**
 * A view of a card.
 */
@NotThreadSafe
final class CardView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card surface design user interface for the card back. */
    private final ICardSurfaceDesignUI backDesignUI_;

    /** The card listener for this view. */
    private ICardListener cardListener_;

    /** The card model listener for this view. */
    private ICardModelListener cardModelListener_;

    /** The card pile view that owns this view. */
    private CardPileView cardPileView_;

    /** The card surface design user interface for the card face. */
    private final ICardSurfaceDesignUI faceDesignUI_;

    /** The model associated with this view. */
    private final CardModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     * @param backDesignUI
     *        The card surface design user interface for the card back; must not
     *        be {@code null}.
     * @param faceDesignUI
     *        The card surface design user interface for the card face; must not
     *        be {@code null}.
     */
    CardView(
        /* @NonNull */
        final CardModel model,
        /* @NonNull */
        final ICardSurfaceDesignUI backDesignUI,
        /* @NonNull */
        final ICardSurfaceDesignUI faceDesignUI )
    {
        assert model != null;
        assert backDesignUI != null;
        assert faceDesignUI != null;

        backDesignUI_ = backDesignUI;
        cardListener_ = null;
        cardModelListener_ = null;
        cardPileView_ = null;
        faceDesignUI_ = faceDesignUI;
        model_ = model;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the card orientation has changed.
     */
    private void cardOrientationChanged()
    {
        if( isInitialized() )
        {
            cardPileView_.repaintCardPile( getBounds() );
        }
    }

    /**
     * Invoked after the card surface designs have changed.
     */
    private void cardSurfaceDesignsChanged()
    {
        if( isInitialized() )
        {
            cardPileView_.repaintCardPile( getBounds() );
        }
    }

    /**
     * Gets the active card surface design user interface.
     * 
     * @return The active card surface design user interface; never {@code null}
     *         .
     */
    /* @NonNull */
    private ICardSurfaceDesignUI getActiveCardSurfaceDesignUI()
    {
        return (model_.getCard().getOrientation() == CardOrientation.BACK_UP) ? backDesignUI_ : faceDesignUI_;
    }

    /**
     * Gets the bounds of this view in table coordinates.
     * 
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    Rectangle getBounds()
    {
        return model_.getCard().getBounds();
    }

    /**
     * Initializes this view.
     * 
     * <p>
     * This method must only be called when the view is uninitialized.
     * </p>
     * 
     * @param cardPileView
     *        The card pile view that owns this view; must not be {@code null}.
     */
    void initialize(
        /* @NonNull */
        final CardPileView cardPileView )
    {
        assert cardPileView != null;
        assert !isInitialized();

        cardPileView_ = cardPileView;
        cardModelListener_ = new CardModelListener();
        model_.addCardModelListener( cardModelListener_ );
        cardListener_ = new CardListener();
        model_.getCard().addCardListener( cardListener_ );

        cardPileView_.repaintCardPile( getBounds() );
    }

    /**
     * Indicates this view has been initialized.
     * 
     * @return {@code true} if this view has been initialized; otherwise
     *         {@code false}.
     */
    private boolean isInitialized()
    {
        return cardPileView_ != null;
    }

    /**
     * Paints this view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param component
     *        The component in which to paint; must not be {@code null}.
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     */
    void paint(
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final Graphics g )
    {
        assert component != null;
        assert g != null;
        assert isInitialized();

        final Rectangle viewBounds = getBounds();
        getActiveCardSurfaceDesignUI().getIcon().paintIcon( component, g, viewBounds.x, viewBounds.y );
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

        cardPileView_.repaintCardPile( getBounds() );

        model_.getCard().removeCardListener( cardListener_ );
        cardListener_ = null;
        model_.removeCardModelListener( cardModelListener_ );
        cardModelListener_ = null;
        cardPileView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card listener for the card view.
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
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.CardListener#cardOrientationChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        public void cardOrientationChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardView.this.cardOrientationChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.CardListener#cardSurfaceDesignsChanged(org.gamegineer.table.core.CardEvent)
         */
        @Override
        public void cardSurfaceDesignsChanged(
            final CardEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardView.this.cardSurfaceDesignsChanged();
                }
            } );
        }
    }

    /**
     * A card model listener for the card view.
     */
    @Immutable
    private final class CardModelListener
        extends org.gamegineer.table.internal.ui.model.CardModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardModelListener} class.
         */
        CardModelListener()
        {
            super();
        }
    }
}
