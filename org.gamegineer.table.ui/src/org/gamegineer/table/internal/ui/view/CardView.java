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
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;

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

    /** The card associated with this view. */
    private final ICard card_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardView} class.
     * 
     * @param card
     *        The card associated with this view; must not be {@code null}.
     */
    CardView(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        card_ = card;

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
     * @see org.gamegineer.table.core.ICardListener#cardFlipped(org.gamegineer.table.core.CardEvent)
     */
    public void cardFlipped(
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
     * @see org.gamegineer.table.core.ICardListener#cardLocationChanged(org.gamegineer.table.core.CardEvent)
     */
    public void cardLocationChanged(
        final CardEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // TODO: Set component location equal to card location.
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setOpaque( true );
        setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
        setSize( 71, 96 );

        updateComponent();
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
        setBackground( (card_.getOrientation() == CardOrientation.FACE_UP) ? Color.RED : Color.BLUE );
    }
}
