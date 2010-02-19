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
import java.awt.MouseInfo;
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
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.CardPileFactory;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.internal.ui.Services;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;

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

    /** The collection of card pile views. */
    private final Map<ICardPile, CardPileView> cardPileViews_;

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
        cardPileViews_ = new IdentityHashMap<ICardPile, CardPileView>();
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
     * Adds a new card to the focused card pile.
     * 
     * @param faceDesignId
     *        The identifier of the design on the card face; must not be {@code
     *        null}.
     */
    private void addCard(
        /* @NonNull */
        final CardSurfaceDesignId faceDesignId )
    {
        assert faceDesignId != null;

        final ICardPile cardPile = model_.getFocusedCardPile();
        if( cardPile != null )
        {
            final ICardSurfaceDesign backDesign = Services.getDefault().getCardSurfaceDesignRegistry().getCardSurfaceDesign( CardSurfaceDesignId.fromString( "org.gamegineer.cardSurfaces.back.thatch" ) ); //$NON-NLS-1$ );
            final ICardSurfaceDesign faceDesign = Services.getDefault().getCardSurfaceDesignRegistry().getCardSurfaceDesign( faceDesignId );
            final ICard card = CardFactory.createCard( backDesign, faceDesign );
            cardPile.addCard( card );
        }
    }

    /**
     * Adds a new card pile to the table.
     */
    private void addCardPile()
    {
        final ICardPileBaseDesign cardPileBaseDesign = Services.getDefault().getCardPileDesignRegistry().getCardPileBaseDesign( CardPileBaseDesignId.fromString( "org.gamegineer.cardPileBases.default" ) ); //$NON-NLS-1$
        final ICardPile cardPile = CardPileFactory.createCardPile( cardPileBaseDesign );
        model_.getTable().addCardPile( cardPile );
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
                addCard( CardSurfaceDesignId.fromString( e.getActionCommand() ) );
            }
        };
        actionMediator_.bind( Actions.getAddAceOfClubsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddAceOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddAceOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddAceOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bind( Actions.getAddCardPileAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                addCardPile();
            }
        } );
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
                flipTopCard();
            }
        } );
        actionMediator_.bind( Actions.getRemoveCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeTopCard();
            }
        } );
        actionMediator_.bind( Actions.getRemoveCardPileAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeFocusedCardPile();
            }
        } );

        final IPredicate<Action> hasCardPredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final ICardPile cardPile = model_.getFocusedCardPile();
                if( cardPile != null )
                {
                    return !cardPile.getCards().isEmpty();
                }

                return false;
            }
        };
        final IPredicate<Action> hasFocusedCardPilePredicate = new IPredicate<Action>()
        {

            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return model_.getFocusedCardPile() != null;
            }
        };
        actionMediator_.bind( Actions.getAddAceOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddAceOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddAceOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddAceOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddEightOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddEightOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddEightOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddEightOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFiveOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFiveOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFiveOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFiveOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFourOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFourOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFourOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddFourOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddJackOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddJackOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddJackOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddJackOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddJokerCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddKingOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddKingOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddKingOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddKingOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddNineOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddNineOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddNineOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddNineOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddQueenOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddQueenOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddQueenOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddQueenOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSevenOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSevenOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSevenOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSevenOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSixOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSixOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSixOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddSixOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTenOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTenOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTenOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTenOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddThreeOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddThreeOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddThreeOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddThreeOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTwoOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTwoOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTwoOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getAddTwoOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bind( Actions.getFlipCardAction(), hasCardPredicate );
        actionMediator_.bind( Actions.getRemoveCardAction(), hasCardPredicate );
        actionMediator_.bind( Actions.getRemoveCardPileAction(), hasFocusedCardPilePredicate );
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardPileAdded(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardPileAdded( event.getCardPile() );
            }
        } );
    }

    /**
     * Invoked when a new card pile is added to the table.
     * 
     * @param cardPile
     *        The added card pile; must not be {@code null}.
     */
    private void cardPileAdded(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        final ICardPileBaseDesignUI cardPileBaseDesignUI = Services.getDefault().getCardPileDesignUIRegistry().getCardPileBaseDesignUI( cardPile.getBaseDesign().getId() );
        final CardPileView view = new CardPileView( model_.getCardPileModel( cardPile ), cardPileBaseDesignUI );
        cardPileViews_.put( cardPile, view );
        view.initialize( this );
        repaint( view.getBounds() );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#cardPileFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    public void cardPileFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardPileFocusChanged();
            }
        } );
    }

    /**
     * Invoked when the card pile focus has changed.
     */
    private void cardPileFocusChanged()
    {
        updateActions();
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    public void cardPileRemoved(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                cardPileRemoved( event.getCardPile() );
            }
        } );
    }

    /**
     * Invoked when a card pile is removed from the table.
     * 
     * @param cardPile
     *        The removed card pile; must not be {@code null}.
     */
    private void cardPileRemoved(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        final CardPileView view = cardPileViews_.remove( cardPile );
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
                    setMouseInputHandler( PopupMenuMouseInputHandler.class, null );
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
        mouseInputHandlers.put( DraggingCardPileMouseInputHandler.class, new DraggingCardPileMouseInputHandler() );
        mouseInputHandlers.put( PopupMenuMouseInputHandler.class, new PopupMenuMouseInputHandler() );
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
     * Flips the card at the top of the focused card pile.
     */
    private void flipTopCard()
    {
        final ICardPile cardPile = model_.getFocusedCardPile();
        if( cardPile != null )
        {
            final List<ICard> cards = cardPile.getCards();
            if( !cards.isEmpty() )
            {
                cards.get( cards.size() - 1 ).flip();
            }
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

        for( final ICardPile cardPile : model_.getTable().getCardPiles() )
        {
            final CardPileView view = cardPileViews_.get( cardPile );
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
     * Removes the focused card pile from the table.
     */
    private void removeFocusedCardPile()
    {
        final ICardPile cardPile = model_.getFocusedCardPile();
        if( cardPile != null )
        {
            model_.getTable().removeCardPile( cardPile );
        }
    }

    /**
     * Removes the card at the top of the focused card pile.
     */
    private void removeTopCard()
    {
        final ICardPile cardPile = model_.getFocusedCardPile();
        if( cardPile != null )
        {
            cardPile.removeCard();
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
     *        handler; may be {@code null} if no mouse event triggered the
     *        activation.
     */
    private void setMouseInputHandler(
        /* @NonNUll */
        final Class<? extends AbstractMouseInputHandler> handlerClass,
        /* @Nullable */
        final MouseEvent e )
    {
        assert handlerClass != null;

        mouseInputHandler_.deactivate();
        mouseInputHandler_ = mouseInputHandlers_.get( handlerClass );
        assert mouseInputHandler_ != null;
        mouseInputHandler_.activate( e );
    }

    /**
     * Updates the actions bound to this component.
     */
    void updateActions()
    {
        actionMediator_.updateAll();
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
         *        The mouse event that triggered activation of this handler; may
         *        be {@code null} if no mouse event triggered the activation.
         */
        void activate(
            /* @NonNull */
            final MouseEvent e )
        {
            // default implementation does nothing
        }

        /**
         * Deactivates this handler.
         */
        void deactivate()
        {
            // default implementation does nothing
        }

        /**
         * Gets the mouse location in table coordinates associated with the
         * specified mouse event or the current mouse location if no mouse event
         * is available.
         * 
         * @param e
         *        The mouse event; may be {@code null} if no mouse event is
         *        available.
         * 
         * @return The mouse location in table coordinates; never {@code null}.
         */
        /* @NonNull */
        protected final Point getMouseLocation(
            /* @Nullable */
            final MouseEvent e )
        {
            if( e != null )
            {
                return e.getPoint();
            }

            final Point location = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen( location, TableView.this );
            return location;
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
            model_.setFocus( model_.getTable().getCardPile( e.getPoint() ) );
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
                setMouseInputHandler( PopupMenuMouseInputHandler.class, e );
            }
            else if( SwingUtilities.isLeftMouseButton( e ) )
            {
                if( model_.getTable().getCardPile( e.getPoint() ) != null )
                {
                    setMouseInputHandler( DraggingCardPileMouseInputHandler.class, e );
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
                setMouseInputHandler( PopupMenuMouseInputHandler.class, e );
            }
        }
    }

    /**
     * The mouse input handler that is active when a card pile is being dragged.
     */
    private final class DraggingCardPileMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card pile being dragged. */
        private ICardPile draggedCardPile_;

        /**
         * The offset between the mouse pointer and the dragged card pile
         * location.
         */
        private final Dimension draggedCardPileLocationOffset_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * DraggingCardPileMouseInputHandler} class.
         */
        DraggingCardPileMouseInputHandler()
        {
            draggedCardPileLocationOffset_ = new Dimension( 0, 0 );
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
            final Point mouseLocation = getMouseLocation( e );
            draggedCardPile_ = model_.getTable().getCardPile( mouseLocation );
            if( draggedCardPile_ != null )
            {
                final Point cardPileLocation = draggedCardPile_.getLocation();
                draggedCardPileLocationOffset_.setSize( cardPileLocation.x - mouseLocation.x, cardPileLocation.y - mouseLocation.y );
            }
            else
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            draggedCardPile_ = null;
            draggedCardPileLocationOffset_.setSize( 0, 0 );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent e )
        {
            final Point location = e.getPoint();
            location.translate( draggedCardPileLocationOffset_.width, draggedCardPileLocationOffset_.height );
            draggedCardPile_.setLocation( location );
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
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }
        }
    }

    /**
     * The mouse input handler that is active when a popup menu is visible.
     */
    private final class PopupMenuMouseInputHandler
        extends AbstractMouseInputHandler
        implements PopupMenuListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PopupMenuMouseInputHandler}
         * class.
         */
        PopupMenuMouseInputHandler()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        void activate(
            final MouseEvent e )
        {
            final Point location = getMouseLocation( e );
            final JPopupMenu menu = getPopupMenu( location );
            menu.addPopupMenuListener( this );
            menu.show( TableView.this, location.x, location.y );
        }

        /**
         * Gets the popup menu associated with the specified location.
         * 
         * @param location
         *        The location; must not be {@code null}.
         * 
         * @return The popup menu associated with the specified location; never
         *         {@code null}.
         */
        /* @NonNull */
        @SuppressWarnings( "synthetic-access" )
        private JPopupMenu getPopupMenu(
            /* @NonNull */
            final Point location )
        {
            assert location != null;

            if( model_.getFocusedCardPile() != null )
            {
                return new CardPilePopupMenu();
            }

            return new TablePopupMenu();
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuCanceled(javax.swing.event.PopupMenuEvent)
         */
        @SuppressWarnings( "synthetic-access" )
        public void popupMenuCanceled(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent e )
        {
            setMouseInputHandler( DefaultMouseInputHandler.class, null );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent)
         */
        @SuppressWarnings( "synthetic-access" )
        public void popupMenuWillBecomeInvisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent e )
        {
            setMouseInputHandler( DefaultMouseInputHandler.class, null );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent)
         */
        public void popupMenuWillBecomeVisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent e )
        {
            // do nothing
        }
    }
}
