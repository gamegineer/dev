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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.table.core.CardChangeEvent;
import org.gamegineer.table.core.CardDesign;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.internal.ui.action.ActionMediator;

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

    /** The mouse input listener for this view. */
    private final MouseInputListener mouseInputListener_;

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
        mouseInputListener_ = new MouseInputHandler();
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
        addMouseListener( mouseInputListener_ );
        addMouseMotionListener( mouseInputListener_ );
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
        actionMediator_.bind( Actions.getFlipCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                flipCard();
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

        actionMediator_.bind( Actions.getFlipCardAction(), new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return !table_.getCards().isEmpty();
            }
        } );
        actionMediator_.bind( Actions.getRemoveCardAction(), new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
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
     * Flips the most recently added card on the table.
     */
    private void flipCard()
    {
        final ICard[] cards = table_.getCards().toArray( new ICard[ 0 ] );
        if( cards.length > 0 )
        {
            cards[ cards.length - 1 ].flip();
        }
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
        removeMouseMotionListener( mouseInputListener_ );
        removeMouseListener( mouseInputListener_ );
        table_.removeTableListener( this );
        actionMediator_.unbindAll();

        super.removeNotify();
    }


    // ======================================================================
    // Nested Classes
    // ======================================================================

    /**
     * The mouse input handler for the table view.
     */
    private final class MouseInputHandler
        extends MouseInputAdapter
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card being dragged or {@code null} if no card is being dragged. */
        private ICard draggedCard_;

        /** The offset between the mouse pointer and the dragged card location. */
        private final Dimension draggedCardLocationOffset_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MouseInputListener} class.
         */
        MouseInputHandler()
        {
            draggedCard_ = null;
            draggedCardLocationOffset_ = new Dimension( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent e )
        {
            if( draggedCard_ != null )
            {
                final Point location = e.getPoint();
                location.translate( draggedCardLocationOffset_.width, draggedCardLocationOffset_.height );
                draggedCard_.setLocation( location );
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mousePressed(
            final MouseEvent e )
        {
            assert draggedCard_ == null;

            final Point mouseLocation = e.getPoint();
            for( final ICard card : cardViews_.keySet() )
            {
                if( card.getBounds().contains( mouseLocation ) )
                {
                    final Point cardLocation = card.getLocation();
                    draggedCard_ = card;
                    draggedCardLocationOffset_.setSize( cardLocation.x - mouseLocation.x, cardLocation.y - mouseLocation.y );
                    break;
                }
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(
            @SuppressWarnings( "unused" )
            final MouseEvent e )
        {
            draggedCard_ = null;
            draggedCardLocationOffset_.setSize( 0, 0 );
        }
    }
}
