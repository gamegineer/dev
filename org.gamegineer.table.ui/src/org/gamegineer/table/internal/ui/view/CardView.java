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
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.internal.ui.model.CardModel;
import org.gamegineer.table.internal.ui.model.ICardModelListener;
import org.gamegineer.table.ui.IComponentSurfaceDesignUI;

/**
 * A view of a card.
 */
@NotThreadSafe
final class CardView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component surface design user interface for the card back. */
    private final IComponentSurfaceDesignUI backDesignUI_;

    /** The card model listener for this view. */
    private ICardModelListener cardModelListener_;

    /** The card pile view that owns this view. */
    private CardPileView cardPileView_;

    /** The component listener for this view. */
    private IComponentListener componentListener_;

    /** The component surface design user interface for the card face. */
    private final IComponentSurfaceDesignUI faceDesignUI_;

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
     *        The component surface design user interface for the card back;
     *        must not be {@code null}.
     * @param faceDesignUI
     *        The component surface design user interface for the card face;
     *        must not be {@code null}.
     */
    CardView(
        /* @NonNull */
        final CardModel model,
        /* @NonNull */
        final IComponentSurfaceDesignUI backDesignUI,
        /* @NonNull */
        final IComponentSurfaceDesignUI faceDesignUI )
    {
        assert model != null;
        assert backDesignUI != null;
        assert faceDesignUI != null;

        backDesignUI_ = backDesignUI;
        cardModelListener_ = null;
        cardPileView_ = null;
        componentListener_ = null;
        faceDesignUI_ = faceDesignUI;
        model_ = model;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the component orientation has changed.
     */
    private void componentOrientationChanged()
    {
        if( isInitialized() )
        {
            cardPileView_.repaintCardPile( getBounds() );
        }
    }

    /**
     * Invoked after a component surface design has changed.
     */
    private void componentSurfaceDesignChanged()
    {
        if( isInitialized() )
        {
            cardPileView_.repaintCardPile( getBounds() );
        }
    }

    /**
     * Gets the active component surface design user interface.
     * 
     * @return The active component surface design user interface; never
     *         {@code null}.
     */
    /* @NonNull */
    private IComponentSurfaceDesignUI getActiveComponentSurfaceDesignUI()
    {
        final ComponentOrientation orientation = model_.getCard().getOrientation();
        if( orientation == CardOrientation.BACK )
        {
            return backDesignUI_;
        }
        else if( orientation == CardOrientation.FACE )
        {
            return faceDesignUI_;
        }

        throw new AssertionError( "unknown card orientation" ); //$NON-NLS-1$
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
        componentListener_ = new ComponentListener();
        model_.getCard().addComponentListener( componentListener_ );

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
        getActiveComponentSurfaceDesignUI().getIcon().paintIcon( component, g, viewBounds.x, viewBounds.y );
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

        model_.getCard().removeComponentListener( componentListener_ );
        componentListener_ = null;
        model_.removeCardModelListener( cardModelListener_ );
        cardModelListener_ = null;
        cardPileView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

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
        }
    }

    /**
     * A component listener for the card view.
     */
    @Immutable
    private final class ComponentListener
        extends org.gamegineer.table.core.ComponentListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentListener} class.
         */
        ComponentListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardView.this.componentOrientationChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardView.this.componentSurfaceDesignChanged();
                }
            } );
        }
    }
}
