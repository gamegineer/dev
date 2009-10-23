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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardChangeEvent;
import org.gamegineer.table.core.CardDesign;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.action.IActionEnabledPredicate;

/**
 * A view of the table.
 */
@NotThreadSafe
final class TableView
    extends JPanel
    implements ITableListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3574703230407179091L;

    /** The action mediator. */
    private final ActionMediator actionMediator_;

    /** The collection of card views. */
    private final Map<ICard, CardView> cardViews_;

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

        actionMediator_ = new ActionMediator();
        cardViews_ = new IdentityHashMap<ICard, CardView>();
        table_ = table;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new card to the table.
     */
    private void addCard()
    {
        table_.addCard( CardFactory.createCard( CardDesign.EMPTY, CardDesign.EMPTY ) );
    }

    /*
     * @see javax.swing.JComponent#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        bindActions();
        table_.addTableListener( this );
    }

    /**
     * Binds the action attachments for this component.
     */
    private void bindActions()
    {
        actionMediator_.bind( Actions.getAddCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                addCard();
            }
        } );
        actionMediator_.bind( Actions.getRemoveCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeCard();
            }
        } );

        actionMediator_.bind( Actions.getRemoveCardAction(), new IActionEnabledPredicate()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean isActionEnabled(
                final Action action )
            {
                assertArgumentNotNull( action, "action" ); //$NON-NLS-1$

                return !table_.getCards().isEmpty();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardAdded(org.gamegineer.table.core.CardChangeEvent)
     */
    public void cardAdded(
        final CardChangeEvent event )
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
     * Invoked when a new card is added to the table.
     * 
     * @param card
     *        The added card; must not be {@code null}.
     */
    private void cardAdded(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        final CardView view = new CardView( card );
        cardViews_.put( card, view );
        final int offset = table_.getCards().size() - 1;
        view.setLocation( offset * view.getWidth(), offset * view.getHeight() );
        add( view );
        repaint( view.getBounds() );

        actionMediator_.updateAll();
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardRemoved(org.gamegineer.table.core.CardChangeEvent)
     */
    public void cardRemoved(
        final CardChangeEvent event )
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
     * Invoked when a card is removed from the table.
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
            remove( view );
            repaint( view.getBounds() );
        }

        actionMediator_.updateAll();
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
     * Removes the most recently added card from the table.
     */
    private void removeCard()
    {
        final ICard[] cards = table_.getCards().toArray( new ICard[ 0 ] );
        if( cards.length > 0 )
        {
            table_.removeCard( cards[ cards.length - 1 ] );
        }
    }

    /*
     * @see javax.swing.JComponent#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        table_.removeTableListener( this );
        actionMediator_.unbindAll();

        super.removeNotify();
    }
}
