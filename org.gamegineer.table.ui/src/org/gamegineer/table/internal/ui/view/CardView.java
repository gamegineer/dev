/*
 * CardView.java
 * Copyright 2008-2009 Gamegineer.org
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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.ui.ICardDesignUI;

/**
 * A view of a card.
 */
@NotThreadSafe
final class CardView
    implements ICardListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card design user interface for the card back. */
    private final ICardDesignUI backDesignUI_;

    /** The current bounds of this view in table coordinates. */
    private Rectangle bounds_;

    /** The card associated with this view. */
    private final ICard card_;

    /** The card design user interface for the card face. */
    private final ICardDesignUI faceDesignUI_;

    /** Indicates the card associated with this view has the focus. */
    private boolean isFocused_;

    /** The table view that owns this view. */
    private TableView tableView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardView} class.
     * 
     * @param card
     *        The card associated with this view; must not be {@code null}.
     * @param backDesignUI
     *        The card design user interface for the card back; must not be
     *        {@code null}.
     * @param faceDesignUI
     *        The card design user interface for the card face; must not be
     *        {@code null}.
     */
    CardView(
        /* @NonNull */
        final ICard card,
        /* @NonNull */
        final ICardDesignUI backDesignUI,
        /* @NonNull */
        final ICardDesignUI faceDesignUI )
    {
        assert card != null;
        assert backDesignUI != null;
        assert faceDesignUI != null;

        bounds_ = null;
        card_ = card;
        backDesignUI_ = backDesignUI;
        faceDesignUI_ = faceDesignUI;
        isFocused_ = false;
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
        // TODO: Instead of storing card bounds, we should modify the
        // cardLocationChanged event to include the old and new location
        // information.

        final Rectangle newBounds = getBounds();
        tableView_.repaint( newBounds.union( bounds_ ) );
        bounds_ = newBounds;
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
        tableView_.repaint( getBounds() );
    }

    /**
     * Gets the active card design user interface.
     * 
     * @return The active card design user interface; never {@code null}.
     */
    /* @NonNull */
    private ICardDesignUI getActiveCardDesignUI()
    {
        return (card_.getOrientation() == CardOrientation.BACK_UP) ? backDesignUI_ : faceDesignUI_;
    }

    /**
     * Gets the bounds of this view in table coordinates.
     * 
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    Rectangle getBounds()
    {
        final Rectangle bounds = card_.getBounds();
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
        card_.addCardListener( this );
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

        final Rectangle cardBounds = card_.getBounds();
        getActiveCardDesignUI().getIcon().paintIcon( tableView_, g, cardBounds.x, cardBounds.y );

        if( isFocused_ )
        {
            final Rectangle viewBounds = getBounds();
            final Color oldColor = g.getColor();
            g.setColor( Color.GREEN );
            g.drawRect( viewBounds.x, viewBounds.y, viewBounds.width - 1, viewBounds.height - 1 );
            g.setColor( oldColor );
        }
    }

    /**
     * Sets a flag that indicates the card associated with this view has the
     * focus.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param isFocused
     *        {@code true} if the card associated with this view has the focus;
     *        otherwise {@code false}.
     */
    void setFocused(
        final boolean isFocused )
    {
        assert tableView_ != null;

        isFocused_ = isFocused;
        tableView_.repaint( getBounds() );
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

        card_.removeCardListener( this );
        tableView_ = null;
    }
}
