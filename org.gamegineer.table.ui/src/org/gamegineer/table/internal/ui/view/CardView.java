/*
 * CardView.java
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
 * Created on Oct 15, 2009 at 10:41:19 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.internal.ui.model.CardModel;
import org.gamegineer.table.internal.ui.model.CardModelEvent;
import org.gamegineer.table.internal.ui.model.ICardModelListener;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;

/**
 * A view of a card.
 */
@NotThreadSafe
final class CardView
    implements ICardListener, ICardModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card surface design user interface for the card back. */
    private final ICardSurfaceDesignUI backDesignUI_;

    /** The current bounds of this view in table coordinates. */
    private Rectangle bounds_;

    /** The card surface design user interface for the card face. */
    private final ICardSurfaceDesignUI faceDesignUI_;

    /** The model associated with this view. */
    private final CardModel model_;

    /** The table view that owns this view. */
    private TableView tableView_;


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

        bounds_ = null;
        model_ = model;
        backDesignUI_ = backDesignUI;
        faceDesignUI_ = faceDesignUI;
        tableView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardListener#cardLocationChanged(org.gamegineer.table.core.CardEvent)
     */
    public void cardLocationChanged(
        final CardEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardLocationChanged();
            }
        } );
    }

    /**
     * Invoked after the card location has changed.
     */
    private void cardLocationChanged()
    {
        if( isInitialized() )
        {
            final Rectangle newBounds = getBounds();
            tableView_.repaintTable( newBounds.union( bounds_ ) );
            bounds_ = newBounds;
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardModelListener#cardModelStateChanged(org.gamegineer.table.internal.ui.model.CardModelEvent)
     */
    public void cardModelStateChanged(
        final CardModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.core.ICardListener#cardOrientationChanged(org.gamegineer.table.core.CardEvent)
     */
    public void cardOrientationChanged(
        final CardEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardOrientationChanged();
            }
        } );
    }

    /**
     * Invoked after the card orientation has changed.
     */
    private void cardOrientationChanged()
    {
        if( isInitialized() )
        {
            tableView_.repaintTable( getBounds() );
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
        final Rectangle bounds = model_.getCard().getBounds();
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
        assert !isInitialized();

        tableView_ = tableView;
        bounds_ = getBounds();
        model_.addCardModelListener( this );
        model_.getCard().addCardListener( this );
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

        final Rectangle cardBounds = model_.getCard().getBounds();
        getActiveCardSurfaceDesignUI().getIcon().paintIcon( tableView_, g, cardBounds.x, cardBounds.y );
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

        model_.getCard().removeCardListener( this );
        model_.removeCardModelListener( this );
        tableView_ = null;
    }
}
