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
import java.awt.event.InputEvent;
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
import org.gamegineer.table.core.CardPileLayout;
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
            final ICardSurfaceDesign backDesign = Services.getDefault().getCardSurfaceDesignRegistry().getCardSurfaceDesign( CardSurfaceDesignId.fromString( "org.gamegineer.cardSurfaces.back.red" ) ); //$NON-NLS-1$ );
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

        final Point location = getMouseLocation();
        convertPointFromTable( location );
        final Dimension tableSize = getSize();
        final Dimension cardPileSize = cardPile.getSize();
        if( location.x < 0 )
        {
            location.x = 0;
        }
        else if( location.x + cardPileSize.width > tableSize.width )
        {
            location.x = tableSize.width - cardPileSize.width;
        }
        if( location.y < 0 )
        {
            location.y = 0;
        }
        else if( location.y + cardPileSize.height > tableSize.height )
        {
            location.y = tableSize.height - cardPileSize.height;
        }
        convertPointToTable( location );
        cardPile.setLocation( location );

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

        for( final ICardPile cardPile : model_.getTable().getCardPiles() )
        {
            createCardPileView( cardPile );
        }
    }

    /**
     * Adds a standard 52-card deck to the focused card pile.
     */
    private void addStandard52CardDeck()
    {
        final String[] faceDesignIds = new String[] {
            "org.gamegineer.cardSurfaces.clubs.ace", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.two", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.three", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.four", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.five", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.six", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.seven", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.eight", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.nine", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.ten", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.jack", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.queen", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.clubs.king", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.ace", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.two", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.three", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.four", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.five", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.six", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.seven", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.eight", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.nine", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.ten", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.jack", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.queen", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.diamonds.king", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.ace", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.two", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.three", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.four", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.five", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.six", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.seven", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.eight", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.nine", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.ten", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.jack", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.queen", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.hearts.king", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.ace", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.two", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.three", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.four", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.five", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.six", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.seven", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.eight", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.nine", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.ten", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.jack", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.queen", //$NON-NLS-1$
            "org.gamegineer.cardSurfaces.spades.king", //$NON-NLS-1$
        };

        for( final String faceDesignId : faceDesignIds )
        {
            addCard( CardSurfaceDesignId.fromString( faceDesignId ) );
        }
    }

    /**
     * Adds a standard 54-card deck to the focused card pile.
     */
    private void addStandard54CardDeck()
    {
        addStandard52CardDeck();

        final CardSurfaceDesignId jokerFaceDesignId = CardSurfaceDesignId.fromString( "org.gamegineer.cardSurfaces.special.joker" ); //$NON-NLS-1$
        addCard( jokerFaceDesignId );
        addCard( jokerFaceDesignId );
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
        final ActionListener setCardPileLayoutActionListener = new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent e )
            {
                setCardPileLayout( CardPileLayout.valueOf( e.getActionCommand() ) );
            }
        };
        actionMediator_.bindActionListener( Actions.getAddAceOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddAceOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddAceOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddAceOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddCardPileAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                addCardPile();
            }
        } );
        actionMediator_.bindActionListener( Actions.getAddEightOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddEightOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddEightOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddEightOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFiveOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFiveOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFiveOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFiveOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFourOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFourOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFourOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddFourOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddJackOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddJackOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddJackOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddJackOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddJokerCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddKingOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddKingOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddKingOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddKingOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddNineOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddNineOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddNineOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddNineOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddQueenOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddQueenOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddQueenOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddQueenOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSevenOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSevenOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSevenOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSevenOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSixOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSixOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSixOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddSixOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddStandard52CardDeckAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                addStandard52CardDeck();
            }
        } );
        actionMediator_.bindActionListener( Actions.getAddStandard54CardDeckAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                addStandard54CardDeck();
            }
        } );
        actionMediator_.bindActionListener( Actions.getAddTenOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTenOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTenOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTenOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddThreeOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddThreeOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddThreeOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddThreeOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTwoOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTwoOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTwoOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddTwoOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getFlipCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                flipTopCard();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeTopCard();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveCardPileAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeFocusedCardPile();
            }
        } );
        actionMediator_.bindActionListener( Actions.getResetTableOriginAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                resetTableOrigin();
            }
        } );
        actionMediator_.bindActionListener( Actions.getSetAccordianDownCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianLeftCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianRightCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianUpCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetStackedCardPileLayoutAction(), setCardPileLayoutActionListener );

        final IPredicate<Action> hasCardPredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final ICardPile cardPile = model_.getFocusedCardPile();
                if( cardPile == null )
                {
                    return false;
                }

                return !cardPile.getCards().isEmpty();
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
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJokerCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddStandard52CardDeckAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddStandard54CardDeckAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfClubsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfDiamondsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfHeartsCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfSpadesCardAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getFlipCardAction(), hasCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveCardAction(), hasCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveCardPileAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianDownCardPileLayoutAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianLeftCardPileLayoutAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianRightCardPileLayoutAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianUpCardPileLayoutAction(), hasFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetStackedCardPileLayoutAction(), hasFocusedCardPilePredicate );

        final IPredicate<Action> isCardPileLayoutSelectedPredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                final Action obj )
            {
                final ICardPile cardPile = model_.getFocusedCardPile();
                if( cardPile == null )
                {
                    return false;
                }

                return cardPile.getLayout() == CardPileLayout.valueOf( (String)obj.getValue( Action.ACTION_COMMAND_KEY ) );
            }

        };
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianDownCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianLeftCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianRightCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianUpCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetStackedCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    @Override
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

        final CardPileView view = createCardPileView( cardPile );
        repaintTable( view.getBounds() );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#cardPileFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void cardPileFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    @Override
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
            repaintTable( view.getBounds() );
            view.uninitialize();
        }
    }

    /**
     * Converts the specified point from the table coordinate system to the view
     * coordinate system.
     * 
     * @param point
     *        The point; must not be {@code null}.
     */
    private void convertPointFromTable(
        /* @NonNull */
        final Point point )
    {
        assert point != null;

        final Dimension originOffset = model_.getOriginOffset();
        point.translate( originOffset.width, originOffset.height );
    }

    /**
     * Converts the specified point from the view coordinate system to the table
     * coordinate system.
     * 
     * @param point
     *        The point; must not be {@code null}.
     */
    private void convertPointToTable(
        /* @NonNull */
        final Point point )
    {
        assert point != null;

        final Dimension originOffset = model_.getOriginOffset();
        point.translate( -originOffset.width, -originOffset.height );
    }

    /**
     * Converts the specified rectangle from the table coordinate system to the
     * view coordinate system.
     * 
     * @param rect
     *        The rectangle; must not be {@code null}.
     */
    private void convertRectangleFromTable(
        /* @NonNull */
        final Rectangle rect )
    {
        assert rect != null;

        final Dimension originOffset = model_.getOriginOffset();
        rect.translate( originOffset.width, originOffset.height );
    }

    /**
     * Creates a card pile view for the specified card pile.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @return The card pile view; never {@code null}.
     */
    /* @NonNull */
    private CardPileView createCardPileView(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        final ICardPileBaseDesignUI cardPileBaseDesignUI = Services.getDefault().getCardPileDesignUIRegistry().getCardPileBaseDesignUI( cardPile.getBaseDesign().getId() );
        final CardPileView view = new CardPileView( model_.getCardPileModel( cardPile ), cardPileBaseDesignUI );
        cardPileViews_.put( cardPile, view );
        view.initialize( this );
        return view;
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
        mouseInputHandlers.put( DraggingCardMouseInputHandler.class, new DraggingCardMouseInputHandler() );
        mouseInputHandlers.put( DraggingCardPileMouseInputHandler.class, new DraggingCardPileMouseInputHandler() );
        mouseInputHandlers.put( DraggingTableMouseInputHandler.class, new DraggingTableMouseInputHandler() );
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
     * Gets the mouse location in table coordinates.
     * 
     * @return The mouse location in table coordinates; never {@code null}.
     */
    /* @NonNull */
    private Point getMouseLocation()
    {
        final Point location = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen( location, this );
        convertPointToTable( location );
        return location;
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

        final Dimension originOffset = model_.getOriginOffset();
        g.translate( originOffset.width, originOffset.height );

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
        for( final CardPileView view : cardPileViews_.values() )
        {
            view.uninitialize();
        }
        cardPileViews_.clear();

        removeMouseMotionListener( mouseInputListener_ );
        removeMouseListener( mouseInputListener_ );
        removeKeyListener( keyListener_ );
        model_.getTable().removeTableListener( this );
        model_.removeTableModelListener( this );
        actionMediator_.unbindAll();

        super.removeNotify();
    }

    /**
     * Repaints the specified region of the table.
     * 
     * @param region
     *        The region of the table to repaint in table coordinates.
     */
    void repaintTable(
        /* @NonNull */
        final Rectangle region )
    {
        assert region != null;

        convertRectangleFromTable( region );
        repaint( region );
    }

    /**
     * Resets the table origin to the view origin.
     */
    private void resetTableOrigin()
    {
        model_.setOriginOffset( new Dimension( 0, 0 ) );
    }

    /**
     * Sets the layout of the focused card pile to the specified value.
     * 
     * @param cardPileLayout
     *        The card pile layout; must not be {@code null}.
     */
    private void setCardPileLayout(
        /* @NonNull */
        final CardPileLayout cardPileLayout )
    {
        assert cardPileLayout != null;

        final ICardPile cardPile = model_.getFocusedCardPile();
        if( cardPile != null )
        {
            cardPile.setLayout( cardPileLayout );
        }
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

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelStateChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelStateChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableOriginOffsetChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableOriginOffsetChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableOriginOffsetChanged();
            }
        } );
    }

    /**
     * Invoked when the table origin offset has changed.
     */
    private void tableOriginOffsetChanged()
    {
        repaint();
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
        @SuppressWarnings( "synthetic-access" )
        protected final Point getMouseLocation(
            /* @Nullable */
            final MouseEvent e )
        {
            if( e != null )
            {
                final Point location = e.getPoint();
                convertPointToTable( location );
                return location;
            }

            return TableView.this.getMouseLocation();
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
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mousePressed(
            final MouseEvent e )
        {
            final ICardPile cardPile = model_.getTable().getCardPile( getMouseLocation( e ) );
            model_.setFocus( cardPile );

            if( e.isPopupTrigger() )
            {
                setMouseInputHandler( PopupMenuMouseInputHandler.class, e );
            }
            else if( SwingUtilities.isLeftMouseButton( e ) )
            {
                if( cardPile != null )
                {
                    if( (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK )
                    {
                        setMouseInputHandler( DraggingCardPileMouseInputHandler.class, e );
                    }
                    else
                    {
                        setMouseInputHandler( DraggingCardMouseInputHandler.class, e );
                    }
                }
                else
                {
                    setMouseInputHandler( DraggingTableMouseInputHandler.class, e );
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
     * The mouse input handler that is active when a card is being dragged.
     */
    private final class DraggingCardMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card pile that temporarily holds the card being dragged. */
        private ICardPile mobileCardPile_;

        /**
         * The offset between the mouse pointer and the mobile card pile
         * location.
         */
        private final Dimension mobileCardPileLocationOffset_;

        /** The card pile that is the source of the card being dragged. */
        private ICardPile sourceCardPile_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * DraggingCardMouseInputHandler} class.
         */
        DraggingCardMouseInputHandler()
        {
            mobileCardPileLocationOffset_ = new Dimension( 0, 0 );
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
            sourceCardPile_ = model_.getTable().getCardPile( mouseLocation );
            if( sourceCardPile_ != null )
            {
                final ICard draggedCard = sourceCardPile_.removeCard();
                if( draggedCard != null )
                {
                    final Point draggedCardLocation = draggedCard.getLocation();
                    mobileCardPileLocationOffset_.setSize( draggedCardLocation.x - mouseLocation.x, draggedCardLocation.y - mouseLocation.y );
                    mobileCardPile_ = CardPileFactory.createCardPile( sourceCardPile_.getBaseDesign() );
                    mobileCardPile_.setLocation( draggedCardLocation );
                    model_.getTable().addCardPile( mobileCardPile_ );
                    mobileCardPile_.addCard( draggedCard );
                    model_.setFocus( mobileCardPile_ );
                }
                else
                {
                    setMouseInputHandler( DefaultMouseInputHandler.class, null );
                }
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
            mobileCardPile_ = null;
            mobileCardPileLocationOffset_.setSize( 0, 0 );
            sourceCardPile_ = null;
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent e )
        {
            final Point location = getMouseLocation( e );
            location.translate( mobileCardPileLocationOffset_.width, mobileCardPileLocationOffset_.height );
            mobileCardPile_.setLocation( location );
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
                model_.getTable().removeCardPile( mobileCardPile_ );

                final Point mouseLocation = getMouseLocation( e );
                final ICardPile cardPile = model_.getTable().getCardPile( mouseLocation );
                final ICardPile targetCardPile = (cardPile != null) ? cardPile : sourceCardPile_;
                targetCardPile.addCard( mobileCardPile_.removeCard() );
                model_.setFocus( targetCardPile );

                setMouseInputHandler( DefaultMouseInputHandler.class, null );
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
            final Point location = getMouseLocation( e );
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
     * The mouse input handler that is active when the table is being dragged.
     */
    private final class DraggingTableMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The mouse location when this handler was activated in view
         * coordinates.
         */
        private final Point originalLocation_;

        /** The original origin offset. */
        private final Dimension originalOriginOffset_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * DraggingTableMouseInputHandler} class.
         */
        DraggingTableMouseInputHandler()
        {
            originalLocation_ = new Point( 0, 0 );
            originalOriginOffset_ = new Dimension( 0, 0 );
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
            final Point location = getMouseLocation( e );
            convertPointFromTable( location );
            originalLocation_.setLocation( location );
            originalOriginOffset_.setSize( model_.getOriginOffset() );
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            originalLocation_.setLocation( 0, 0 );
            originalOriginOffset_.setSize( 0, 0 );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseDragged(
            final MouseEvent e )
        {
            final Point location = getMouseLocation( e );
            convertPointFromTable( location );
            final Dimension originOffset = new Dimension( originalOriginOffset_.width + (location.x - originalLocation_.x), originalOriginOffset_.height + (location.y - originalLocation_.y) );
            model_.setOriginOffset( originOffset );
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
        @SuppressWarnings( "synthetic-access" )
        void activate(
            final MouseEvent e )
        {
            final Point location = getMouseLocation( e );
            final JPopupMenu menu = getPopupMenu( location );
            menu.addPopupMenuListener( this );
            convertPointFromTable( location );
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
        @Override
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
        @Override
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
        @Override
        public void popupMenuWillBecomeVisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent e )
        {
            // do nothing
        }
    }
}
