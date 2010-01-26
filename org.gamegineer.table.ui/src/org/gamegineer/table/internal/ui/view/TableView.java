/*
 * TableView.java
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
 * Created on Oct 6, 2009 at 11:16:52 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardDesign;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.internal.ui.Services;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.ui.ICardDesignUI;

/**
 * A view of the table.
 */
@NotThreadSafe
final class TableView
    extends JPanel
    implements ITableListener, ITableModelListener
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

    /** The key listener for this view. */
    private final KeyListener keyListener_;

    /** The model associated with this view. */
    private final TableModel model_;

    /** The current mouse input handler. */
    private AbstractMouseInputHandler mouseInputHandler_;

    /** The collection of mouse input handler singletons. */
    private final Map<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler> mouseInputHandlers_;

    /** The mouse input listener for this view. */
    private final MouseInputListener mouseInputListener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     */
    TableView(
        /* @NonNull */
        final TableModel model )
    {
        assert model != null;

        actionMediator_ = new ActionMediator();
        cardViews_ = new IdentityHashMap<ICard, CardView>();
        keyListener_ = createKeyListener();
        model_ = model;
        mouseInputHandlers_ = createMouseInputHandlers();
        mouseInputHandler_ = mouseInputHandlers_.get( DefaultMouseInputHandler.class );
        mouseInputListener_ = createMouseInputListener();

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new card to the table.
     * 
     * @param faceDesignId
     *        The identifier of the design on the card face; must not be {@code
     *        null}.
     */
    private void addCard(
        /* @NonNull */
        final CardDesignId faceDesignId )
    {
        assert faceDesignId != null;

        final ICardDesign backDesign = Services.getDefault().getCardDesignRegistry().getCardDesign( CardDesignId.fromString( "org.gamegineer.cards.back.thatch" ) ); //$NON-NLS-1$ );
        final ICardDesign faceDesign = Services.getDefault().getCardDesignRegistry().getCardDesign( faceDesignId );
        final ICard card = CardFactory.createCard( backDesign, faceDesign );
        model_.getTable().addCard( card );
    }

    /*
     * @see javax.swing.JComponent#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        bindActions();
        model_.addTableModelListener( this );
        model_.getTable().addTableListener( this );
        addKeyListener( keyListener_ );
        addMouseListener( mouseInputListener_ );
        addMouseMotionListener( mouseInputListener_ );
    }

    /**
     * Binds the action attachments for this component.
     */
    private void bindActions()
    {
        final ActionListener addCardActionListener = new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent e )
            {
                addCard( CardDesignId.fromString( e.getActionCommand() ) );
            }
        };
        actionMediator_.bind( Actions.getAddAceOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddAceOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddAceOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddAceOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddEightOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddEightOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddEightOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddEightOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFiveOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFiveOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFiveOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFiveOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFourOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFourOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFourOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddFourOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddJackOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddJackOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddJackOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddJackOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddJokerCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddKingOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddKingOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddKingOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddKingOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddNineOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddNineOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddNineOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddNineOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddQueenOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddQueenOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddQueenOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddQueenOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSevenOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSevenOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSevenOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSevenOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSixOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSixOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSixOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddSixOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTenOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTenOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTenOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTenOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddThreeOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddThreeOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddThreeOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddThreeOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTwoOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTwoOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTwoOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddTwoOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getFlipCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                flipFocusedCard();
            }
        } );
        actionMediator_.bind( Actions.getRemoveCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeFocusedCard();
            }
        } );

        final IPredicate<Action> hasFocusedCardPredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return model_.getFocusedCard() != null;
            }
        };
        actionMediator_.bind( Actions.getFlipCardAction(), hasFocusedCardPredicate );
        actionMediator_.bind( Actions.getRemoveCardAction(), hasFocusedCardPredicate );
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardAdded(
        final TableContentChangedEvent event )
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

        final ICardDesignUI backDesignUI = Services.getDefault().getCardDesignUIRegistry().getCardDesignUI( card.getBackDesign().getId() );
        final ICardDesignUI faceDesignUI = Services.getDefault().getCardDesignUIRegistry().getCardDesignUI( card.getFaceDesign().getId() );
        final CardView view = new CardView( model_.getCardModel( card ), backDesignUI, faceDesignUI );
        cardViews_.put( card, view );
        view.initialize( this );
        repaint( view.getBounds() );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#cardFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    public void cardFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardFocusChanged();
            }
        } );
    }

    /**
     * Invoked when the card focus has changed.
     */
    private void cardFocusChanged()
    {
        actionMediator_.updateAll();
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardPileAdded(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // TODO: IMPLEMENT
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardPileRemoved(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // TODO: IMPLEMENT
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardRemoved(
        final TableContentChangedEvent event )
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
            repaint( view.getBounds() );
            view.uninitialize();
        }
    }

    /**
     * Creates the key listener for the view.
     * 
     * @return The key listener for the view; never {@code null}.
     */
    /* @NonNull */
    private KeyListener createKeyListener()
    {
        return new KeyAdapter()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void keyReleased(
                final KeyEvent e )
            {
                if( e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU )
                {
                    showPopupMenu( new Point( 0, 0 ) );
                }
            }
        };
    }

    /**
     * Creates the collection of mouse input handler singletons for the view.
     * 
     * @return The collection of mouse input handler singletons for the view;
     *         never {@code null}.
     */
    /* @NonNull */
    private Map<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler> createMouseInputHandlers()
    {
        final Map<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler> mouseInputHandlers = new HashMap<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler>();
        mouseInputHandlers.put( DefaultMouseInputHandler.class, new DefaultMouseInputHandler() );
        mouseInputHandlers.put( DraggingMouseInputHandler.class, new DraggingMouseInputHandler() );
        return mouseInputHandlers;
    }

    /**
     * Create the mouse input listener for the view.
     * 
     * @return The mouse input listener for the view; never {@code null}.
     */
    /* @NonNull */
    private MouseInputListener createMouseInputListener()
    {
        return new MouseInputAdapter()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseDragged(
                final MouseEvent e )
            {
                mouseInputHandler_.mouseDragged( e );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseMoved(
                final MouseEvent e )
            {
                mouseInputHandler_.mouseMoved( e );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mousePressed(
                final MouseEvent e )
            {
                mouseInputHandler_.mousePressed( e );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseReleased(
                final MouseEvent e )
            {
                mouseInputHandler_.mouseReleased( e );
            }
        };
    }

    /**
     * Flips the focused card on the table.
     */
    private void flipFocusedCard()
    {
        final ICard card = model_.getFocusedCard();
        if( card != null )
        {
            card.flip();
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

    /*
     * @see java.awt.Component#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }

    /*
     * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
     */
    @Override
    protected void paintChildren(
        final Graphics g )
    {
        super.paintChildren( g );

        final Rectangle clipBounds = g.getClipBounds();

        for( final ICard card : model_.getTable().getCards() )
        {
            final CardView view = cardViews_.get( card );
            if( view != null )
            {
                if( clipBounds.intersects( view.getBounds() ) )
                {
                    view.paint( g );
                }
            }
        }
    }

    /**
     * Removes the focused card from the table.
     */
    private void removeFocusedCard()
    {
        final ICard card = model_.getFocusedCard();
        if( card != null )
        {
            model_.getTable().removeCard( card );
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
        removeKeyListener( keyListener_ );
        model_.getTable().removeTableListener( this );
        model_.removeTableModelListener( this );
        actionMediator_.unbindAll();

        super.removeNotify();
    }

    /**
     * Sets the current mouse input handler
     * 
     * @param handlerClass
     *        The class of the mouse input handler to activate; must not be
     *        {@code null}.
     * @param e
     *        The mouse event that triggered activation of the mouse input
     *        handler; must not be {@code null}.
     */
    private void setMouseInputHandler(
        /* @NonNUll */
        final Class<? extends AbstractMouseInputHandler> handlerClass,
        /* @NonNull */
        final MouseEvent e )
    {
        assert handlerClass != null;
        assert e != null;

        mouseInputHandler_.deactivate();
        mouseInputHandler_ = mouseInputHandlers_.get( handlerClass );
        assert mouseInputHandler_ != null;
        mouseInputHandler_.activate( e );
    }

    /**
     * Shows a context-sensitive popup menu at the specified location.
     * 
     * @param location
     *        The popup menu location; must not be {@code null}.
     */
    private void showPopupMenu(
        /* @NonNull */
        final Point location )
    {
        assert location != null;

        final JPopupMenu menu;
        if( model_.getFocusedCard() != null )
        {
            menu = new CardPopupMenu();
        }
        else
        {
            menu = null;
        }

        if( menu != null )
        {
            menu.show( this, location.x, location.y );
        }
    }


    // ======================================================================
    // Nested Classes
    // ======================================================================

    /**
     * Superclass for all mouse input handlers.
     * 
     * <p>
     * Instances of this class represent the State participant of the State
     * pattern.
     * </p>
     */
    private abstract class AbstractMouseInputHandler
        extends MouseInputAdapter
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMouseInputHandler}
         * class.
         */
        protected AbstractMouseInputHandler()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Activates this handler.
         * 
         * @param e
         *        The mouse event that triggered activation of this handler;
         *        must not be {@code null}.
         */
        void activate(
            /* @NonNull */
            final MouseEvent e )
        {
            assert e != null;

            // default implementation does nothing
        }

        /**
         * Deactivates this handler.
         */
        void deactivate()
        {
            // default implementation does nothing
        }
    }

    /**
     * The default mouse input handler.
     */
    private final class DefaultMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DefaultMouseInputHandler}
         * class.
         */
        DefaultMouseInputHandler()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseMoved(
            final MouseEvent e )
        {
            model_.setFocus( model_.getTable().getCard( e.getPoint() ) );
        }

        /*
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mousePressed(
            final MouseEvent e )
        {
            if( e.isPopupTrigger() )
            {
                showPopupMenu( e.getPoint() );
            }
            else if( SwingUtilities.isLeftMouseButton( e ) )
            {
                if( model_.getTable().getCard( e.getPoint() ) != null )
                {
                    setMouseInputHandler( DraggingMouseInputHandler.class, e );
                }
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent e )
        {
            if( e.isPopupTrigger() )
            {
                showPopupMenu( e.getPoint() );
            }
        }
    }

    /**
     * The mouse input handler that is active when a card is being dragged.
     */
    private final class DraggingMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card being dragged. */
        private ICard draggedCard_;

        /** The offset between the mouse pointer and the dragged card location. */
        private final Dimension draggedCardLocationOffset_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DraggingMouseInputHandler}
         * class.
         */
        DraggingMouseInputHandler()
        {
            draggedCardLocationOffset_ = new Dimension( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        void activate(
            final MouseEvent e )
        {
            assert e != null;

            final Point mouseLocation = e.getPoint();
            draggedCard_ = model_.getTable().getCard( mouseLocation );
            if( draggedCard_ != null )
            {
                final Point cardLocation = draggedCard_.getLocation();
                draggedCardLocationOffset_.setSize( cardLocation.x - mouseLocation.x, cardLocation.y - mouseLocation.y );
            }
            else
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, e );
            }
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            draggedCard_ = null;
            draggedCardLocationOffset_.setSize( 0, 0 );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent e )
        {
            final Point location = e.getPoint();
            location.translate( draggedCardLocationOffset_.width, draggedCardLocationOffset_.height );
            draggedCard_.setLocation( location );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent e )
        {
            if( SwingUtilities.isLeftMouseButton( e ) )
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, e );
            }
        }
    }
}
