/*
 * TableView.java
 * Copyright 2008-2011 Gamegineer.org
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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.common.ui.wizard.IWizard;
import org.gamegineer.common.ui.wizard.WizardDialog;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.CardPileLayout;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardPileBaseDesignRegistry;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.ICardSurfaceDesignRegistry;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.internal.ui.wizards.hosttablenetwork.HostTableNetworkWizard;
import org.gamegineer.table.internal.ui.wizards.jointablenetwork.JoinTableNetworkWizard;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.ICardPileBaseDesignUIRegistry;

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

    /** The background paint. */
    private final Paint backgroundPaint_;

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
        backgroundPaint_ = createBackgroundPaint();
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
            final ICardSurfaceDesignRegistry cardSurfaceDesignRegistry = Activator.getDefault().getCardSurfaceDesignRegistry();
            assert cardSurfaceDesignRegistry != null;
            final ICardSurfaceDesign backDesign = cardSurfaceDesignRegistry.getCardSurfaceDesign( CardSurfaceDesignId.fromString( "org.gamegineer.cardSurfaces.back.red" ) ); //$NON-NLS-1$ );
            final ICardSurfaceDesign faceDesign = cardSurfaceDesignRegistry.getCardSurfaceDesign( faceDesignId );
            final ICard card = model_.getTable().createCard();
            card.setSurfaceDesigns( backDesign, faceDesign );
            cardPile.addCard( card );
        }
    }

    /**
     * Adds a new card pile to the table.
     */
    private void addCardPile()
    {
        final ICardPileBaseDesignRegistry cardPileBaseDesignRegistry = Activator.getDefault().getCardPileBaseDesignRegistry();
        assert cardPileBaseDesignRegistry != null;
        final ICardPileBaseDesign cardPileBaseDesign = cardPileBaseDesignRegistry.getCardPileBaseDesign( CardPileBaseDesignId.fromString( "org.gamegineer.cardPileBases.default" ) ); //$NON-NLS-1$
        final ICardPile cardPile = model_.getTable().createCardPile();
        cardPile.setBaseDesign( cardPileBaseDesign );

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
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                addCard( CardSurfaceDesignId.fromString( event.getActionCommand() ) );
            }
        };
        final ActionListener setCardPileLayoutActionListener = new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                setCardPileLayout( CardPileLayout.valueOf( event.getActionCommand() ) );
            }
        };
        actionMediator_.bindActionListener( Actions.getAddAceOfClubsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddAceOfDiamondsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddAceOfHeartsCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddAceOfSpadesCardAction(), addCardActionListener );
        actionMediator_.bindActionListener( Actions.getAddCardPileAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
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
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                addStandard52CardDeck();
            }
        } );
        actionMediator_.bindActionListener( Actions.getAddStandard54CardDeckAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
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
        actionMediator_.bindActionListener( Actions.getDisconnectTableNetworkAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                disconnectTableNetwork();
            }
        } );
        actionMediator_.bindActionListener( Actions.getFlipCardAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                flipTopCard();
            }
        } );
        actionMediator_.bindActionListener( Actions.getHostTableNetworkAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                hostTableNetwork();
            }
        } );
        actionMediator_.bindActionListener( Actions.getJoinTableNetworkAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                joinTableNetwork();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveAllCardPilesAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeAllCardPiles();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveAllCardsAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeAllCards();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveCardAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeTopCard();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveCardPileAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeFocusedCardPile();
            }
        } );
        actionMediator_.bindActionListener( Actions.getResetTableOriginAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                resetTableOrigin();
            }
        } );
        actionMediator_.bindActionListener( Actions.getSetAccordianDownCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianLeftCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianRightCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianUpCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetStackedCardPileLayoutAction(), setCardPileLayoutActionListener );

        final IPredicate<Action> hasEditableCardPilePredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                if( model_.getTable().getCardPileCount() == 0 )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> hasEditableCardPredicate = new IPredicate<Action>()
        {
            @Override
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

                if( cardPile.getCardCount() == 0 )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> hasEditableFocusedCardPilePredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                if( model_.getFocusedCardPile() == null )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> isTableEditablePredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return model_.isEditable();
            }
        };
        final IPredicate<Action> isNetworkConnectedPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return model_.getTableNetwork().isConnected();
            }
        };
        final IPredicate<Action> isNetworkDisconnectedPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return !model_.getTableNetwork().isConnected();
            }
        };
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddAceOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddCardPileAction(), isTableEditablePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddEightOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFiveOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddFourOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJackOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddJokerCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddKingOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddNineOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddQueenOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSevenOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddSixOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddStandard52CardDeckAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddStandard54CardDeckAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTenOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddThreeOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfClubsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfDiamondsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfHeartsCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getAddTwoOfSpadesCardAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getDisconnectTableNetworkAction(), isNetworkConnectedPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getFlipCardAction(), hasEditableCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getHostTableNetworkAction(), isNetworkDisconnectedPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getJoinTableNetworkAction(), isNetworkDisconnectedPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveAllCardPilesAction(), hasEditableCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveAllCardsAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveCardAction(), hasEditableCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveCardPileAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianDownCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianLeftCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianRightCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianUpCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetStackedCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );

        final IPredicate<Action> isCardPileLayoutSelectedPredicate = new IPredicate<Action>()
        {
            @Override
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

    /**
     * Creates the background paint.
     * 
     * @return The background paint; never {@code null}.
     */
    /* @NonNull */
    private static Paint createBackgroundPaint()
    {
        try
        {
            final URL url = Activator.getDefault().getBundleContext().getBundle().getEntry( "/icons/backgrounds/green-felt.png" ); //$NON-NLS-1$
            assert url != null;
            final BufferedImage image = ImageIO.read( url );
            return new TexturePaint( image, new Rectangle( 0, 0, image.getWidth(), image.getHeight() ) );
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableView_createBackgroundPaint_readImageError, e );
            return new Color( 0, 128, 0 );
        }
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
            @Override
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
        repaintTable( view.getDirtyBounds() );
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
            @Override
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
            repaintTable( view.getDirtyBounds() );
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

        final ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry = Activator.getDefault().getCardPileBaseDesignUIRegistry();
        assert cardPileBaseDesignUIRegistry != null;
        final ICardPileBaseDesignUI cardPileBaseDesignUI = cardPileBaseDesignUIRegistry.getCardPileBaseDesignUI( cardPile.getBaseDesign().getId() );
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
                final KeyEvent event )
            {
                if( event.getKeyCode() == KeyEvent.VK_CONTEXT_MENU )
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
        mouseInputHandlers.put( DraggingCardsMouseInputHandler.class, new DraggingCardsMouseInputHandler() );
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
                final MouseEvent event )
            {
                mouseInputHandler_.mouseDragged( event );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseMoved(
                final MouseEvent event )
            {
                mouseInputHandler_.mouseMoved( event );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mousePressed(
                final MouseEvent event )
            {
                mouseInputHandler_.mousePressed( event );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseReleased(
                final MouseEvent event )
            {
                mouseInputHandler_.mouseReleased( event );
            }
        };
    }

    /**
     * Disconnects the table network.
     */
    private void disconnectTableNetwork()
    {
        model_.getTableNetwork().disconnect();
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
     * Hosts a new table network.
     */
    private void hostTableNetwork()
    {
        final IWizard wizard = new HostTableNetworkWizard( model_ );
        final WizardDialog dialog = new WizardDialog( JOptionPane.getFrameForComponent( this ), wizard );
        dialog.open();
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setLayout( null );
        setOpaque( true );
    }

    /*
     * @see java.awt.Component#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }

    /**
     * Joins an existing table network.
     */
    private void joinTableNetwork()
    {
        final IWizard wizard = new JoinTableNetworkWizard( model_ );
        final WizardDialog dialog = new WizardDialog( JOptionPane.getFrameForComponent( this ), wizard );
        dialog.open();
    }

    /*
     * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
     */
    @Override
    protected void paintChildren(
        final Graphics g )
    {
        super.paintChildren( g );

        final Graphics2D g2d = (Graphics2D)g;

        final Dimension originOffset = model_.getOriginOffset();
        g.translate( originOffset.width, originOffset.height );

        final Rectangle clipBounds = g.getClipBounds();

        g2d.setPaint( backgroundPaint_ );
        g2d.fillRect( -originOffset.width, -originOffset.height, getWidth(), getHeight() );

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
     * Removes all card piles from the table.
     */
    private void removeAllCardPiles()
    {
        model_.getTable().removeCardPiles();
    }

    /**
     * Removes all cards from the focused card pile.
     */
    private void removeAllCards()
    {
        final ICardPile cardPile = model_.getFocusedCardPile();
        if( cardPile != null )
        {
            cardPile.removeCards();
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
     * @param event
     *        The mouse event that triggered activation of the mouse input
     *        handler; may be {@code null} if no mouse event triggered the
     *        activation.
     */
    private void setMouseInputHandler(
        /* @NonNUll */
        final Class<? extends AbstractMouseInputHandler> handlerClass,
        /* @Nullable */
        final MouseEvent event )
    {
        assert handlerClass != null;

        mouseInputHandler_.deactivate();
        mouseInputHandler_ = mouseInputHandlers_.get( handlerClass );
        assert mouseInputHandler_ != null;
        mouseInputHandler_.activate( event );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelDirtyFlagChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelDirtyFlagChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelFileChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelFileChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelOriginOffsetChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelOriginOffsetChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableModelOriginOffsetChanged();
            }
        } );
    }

    /**
     * Invoked when the table model origin offset has changed.
     */
    private void tableModelOriginOffsetChanged()
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
         * @param event
         *        The mouse event that triggered activation of this handler; may
         *        be {@code null} if no mouse event triggered the activation.
         */
        void activate(
            /* @NonNull */
            @SuppressWarnings( "unused" )
            final MouseEvent event )
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
         * @param event
         *        The mouse event; may be {@code null} if no mouse event is
         *        available.
         * 
         * @return The mouse location in table coordinates; never {@code null}.
         */
        /* @NonNull */
        @SuppressWarnings( "synthetic-access" )
        protected final Point getMouseLocation(
            /* @Nullable */
            final MouseEvent event )
        {
            if( event != null )
            {
                final Point location = event.getPoint();
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
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        void activate(
            final MouseEvent event )
        {
            updateCursor( event );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseMoved(
            final MouseEvent event )
        {
            updateCursor( event );
        }

        /*
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mousePressed(
            final MouseEvent event )
        {
            final ICardPile cardPile = model_.getTable().getCardPile( getMouseLocation( event ) );
            model_.setFocus( cardPile );

            if( event.isPopupTrigger() )
            {
                setMouseInputHandler( PopupMenuMouseInputHandler.class, event );
            }
            else if( SwingUtilities.isLeftMouseButton( event ) )
            {
                if( cardPile != null )
                {
                    if( model_.isEditable() )
                    {
                        if( (event.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK )
                        {
                            setMouseInputHandler( DraggingCardPileMouseInputHandler.class, event );
                        }
                        else
                        {
                            setMouseInputHandler( DraggingCardsMouseInputHandler.class, event );
                        }
                    }
                }
                else
                {
                    setMouseInputHandler( DraggingTableMouseInputHandler.class, event );
                }
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( event.isPopupTrigger() )
            {
                setMouseInputHandler( PopupMenuMouseInputHandler.class, event );
            }
        }

        /**
         * Updates the mouse cursor.
         * 
         * @param event
         *        The mouse event; may be {@code null} if no mouse event is
         *        available.
         */
        @SuppressWarnings( "synthetic-access" )
        private void updateCursor(
            /* @Nullable */
            final MouseEvent event )
        {
            final ICardPile cardPile = model_.getTable().getCardPile( getMouseLocation( event ) );
            final Cursor newCursor;
            if( cardPile != null )
            {
                newCursor = model_.isEditable() ? Cursors.getDefaultCursor() : Cursors.getInvalidCursor();
            }
            else
            {
                newCursor = Cursors.getHandCursor();
            }

            if( newCursor != getCursor() )
            {
                setCursor( newCursor );
            }
        }
    }

    /**
     * The mouse input handler that is active when a collection of cards are
     * being dragged.
     */
    private final class DraggingCardsMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card pile that temporarily holds the cards being dragged. */
        private ICardPile mobileCardPile_;

        /**
         * The offset between the mouse pointer and the mobile card pile base
         * location.
         */
        private final Dimension mobileCardPileBaseLocationOffset_;

        /** The card pile that is the source of the cards being dragged. */
        private ICardPile sourceCardPile_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * DraggingCardsMouseInputHandler} class.
         */
        DraggingCardsMouseInputHandler()
        {
            mobileCardPileBaseLocationOffset_ = new Dimension( 0, 0 );
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
            final MouseEvent event )
        {
            final Point mouseLocation = getMouseLocation( event );
            sourceCardPile_ = model_.getTable().getCardPile( mouseLocation );
            if( sourceCardPile_ != null )
            {
                final List<ICard> draggedCards = sourceCardPile_.removeCards( mouseLocation );
                if( !draggedCards.isEmpty() )
                {
                    final Point draggedCardsBaseLocation = draggedCards.get( 0 ).getLocation();
                    mobileCardPileBaseLocationOffset_.setSize( draggedCardsBaseLocation.x - mouseLocation.x, draggedCardsBaseLocation.y - mouseLocation.y );
                    mobileCardPile_ = model_.getTable().createCardPile();
                    mobileCardPile_.setBaseDesign( sourceCardPile_.getBaseDesign() );
                    mobileCardPile_.setBaseLocation( draggedCardsBaseLocation );
                    mobileCardPile_.setLayout( sourceCardPile_.getLayout() );
                    model_.getTable().addCardPile( mobileCardPile_ );
                    mobileCardPile_.addCards( draggedCards );
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
            mobileCardPileBaseLocationOffset_.setSize( 0, 0 );
            sourceCardPile_ = null;
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            location.translate( mobileCardPileBaseLocationOffset_.width, mobileCardPileBaseLocationOffset_.height );
            mobileCardPile_.setBaseLocation( location );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                model_.getTable().removeCardPile( mobileCardPile_ );

                final Point mouseLocation = getMouseLocation( event );
                final ICardPile cardPile = model_.getTable().getCardPile( mouseLocation );
                final ICardPile targetCardPile = (cardPile != null) ? cardPile : sourceCardPile_;
                targetCardPile.addCards( mobileCardPile_.removeCards() );
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
            final MouseEvent event )
        {
            final Point mouseLocation = getMouseLocation( event );
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
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            location.translate( draggedCardPileLocationOffset_.width, draggedCardPileLocationOffset_.height );
            draggedCardPile_.setLocation( location );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isLeftMouseButton( event ) )
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
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            convertPointFromTable( location );
            originalLocation_.setLocation( location );
            originalOriginOffset_.setSize( model_.getOriginOffset() );

            setCursor( Cursors.getGrabCursor() );
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
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
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
            final MouseEvent event )
        {
            if( SwingUtilities.isLeftMouseButton( event ) )
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
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
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
            final PopupMenuEvent event )
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
            final PopupMenuEvent event )
        {
            setMouseInputHandler( DefaultMouseInputHandler.class, null );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent)
         */
        @Override
        public void popupMenuWillBecomeVisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent event )
        {
            // do nothing
        }
    }
}
