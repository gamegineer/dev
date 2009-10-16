/*
 * TableView.java
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
 * Created on Oct 6, 2009 at 11:16:52 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardDesign;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;

/**
 * A view of the table.
 */
@NotThreadSafe
final class TableView
    extends JPanel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3574703230407179091L;

    /** The table associated with this view. */
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableView} class.
     * 
     * @param table
     *        The table associated with this view; must not be {@code null}.
     */
    TableView(
        /* @NonNull */
        final ITable table )
    {
        assert table != null;

        table_ = table;

        initializeComponent();

        registerActionListeners();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new card to the table.
     */
    private void addCard()
    {
        final ICard card = CardFactory.createCard( CardDesign.EMPTY, CardDesign.EMPTY );
        table_.addCard( card );

        // TODO: Eventually move this to the appropriate table listener method.
        final CardView view = new CardView( card );
        final int offset = table_.getCards().size() - 1;
        view.setLocation( offset * view.getWidth(), offset * view.getHeight() );
        add( view );
        repaint( view.getBounds() );
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setLayout( null );
        setOpaque( true );
        setBackground( new Color( 0, 128, 0 ) );
    }

    /**
     * Registers the action listeners for this component.
     */
    private void registerActionListeners()
    {
        Actions.getAddCardAction().addActionListener( new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                addCard();
            }
        } );
    }
}
