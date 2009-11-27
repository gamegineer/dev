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
import java.awt.Graphics;
import javax.swing.JPanel;
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
    extends JPanel
    implements ICardListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3436102069399598192L;

    /** The card design user interface for the card back. */
    private final ICardDesignUI backDesignUI_;

    /** The card associated with this view. */
    private final ICard card_;

    /** The card design user interface for the card face. */
    private final ICardDesignUI faceDesignUI_;


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

        card_ = card;
        backDesignUI_ = backDesignUI;
        faceDesignUI_ = faceDesignUI;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see javax.swing.JComponent#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        card_.addCardListener( this );
    }

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
                updateComponent();
            }
        } );
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
            public void run()
            {
                repaint();
            }
        } );
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
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setOpaque( false );
        setSize( card_.getSize() );

        updateComponent();
    }

    /*
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(
        final Graphics g )
    {
        getActiveCardDesignUI().getIcon().paintIcon( this, g, 0, 0 );
    }

    /*
     * @see javax.swing.JComponent#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        card_.removeCardListener( this );

        super.removeNotify();
    }

    /**
     * Updates this component.
     */
    private void updateComponent()
    {
        setLocation( card_.getLocation() );
    }
}
