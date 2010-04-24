/*
 * Actions.java
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
 * Created on Oct 9, 2009 at 10:26:28 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.KeyStroke;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileLayout;
import org.gamegineer.table.internal.ui.action.BasicAction;

/**
 * The collection of actions available to all views.
 */
@ThreadSafe
final class Actions
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The identifier of the action used to add an Ace of Clubs card to a card
     * pile.
     */
    private static final String ADD_ACE_OF_CLUBS_CARD_ACTION_ID = "addAceOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Ace of Diamonds card to a
     * card pile.
     */
    private static final String ADD_ACE_OF_DIAMONDS_CARD_ACTION_ID = "addAceOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Ace of Hearts card to a card
     * pile.
     */
    private static final String ADD_ACE_OF_HEARTS_CARD_ACTION_ID = "addAceOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Ace of Spades card to a card
     * pile.
     */
    private static final String ADD_ACE_OF_SPADES_CARD_ACTION_ID = "addAceOfSpadesCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to add a card pile to the table. */
    private static final String ADD_CARD_PILE_ACTION_ID = "addCardPileAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Eight of Clubs card to a card
     * pile.
     */
    private static final String ADD_EIGHT_OF_CLUBS_CARD_ACTION_ID = "addEightOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Eight of Diamonds card to a
     * card pile.
     */
    private static final String ADD_EIGHT_OF_DIAMONDS_CARD_ACTION_ID = "addEightOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Eight of Hearts card to a
     * card pile.
     */
    private static final String ADD_EIGHT_OF_HEARTS_CARD_ACTION_ID = "addEightOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add an Eight of Spades card to a
     * card pile.
     */
    private static final String ADD_EIGHT_OF_SPADES_CARD_ACTION_ID = "addEightOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Five of Clubs card to a card
     * pile.
     */
    private static final String ADD_FIVE_OF_CLUBS_CARD_ACTION_ID = "addFiveOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Five of Diamonds card to a
     * card pile.
     */
    private static final String ADD_FIVE_OF_DIAMONDS_CARD_ACTION_ID = "addFiveOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Five of Hearts card to a card
     * pile.
     */
    private static final String ADD_FIVE_OF_HEARTS_CARD_ACTION_ID = "addFiveOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Five of Spades card to a card
     * pile.
     */
    private static final String ADD_FIVE_OF_SPADES_CARD_ACTION_ID = "addFiveOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Four of Clubs card to a card
     * pile.
     */
    private static final String ADD_FOUR_OF_CLUBS_CARD_ACTION_ID = "addFourOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Four of Diamonds card to a
     * card pile.
     */
    private static final String ADD_FOUR_OF_DIAMONDS_CARD_ACTION_ID = "addFourOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Four of Hearts card to a card
     * pile.
     */
    private static final String ADD_FOUR_OF_HEARTS_CARD_ACTION_ID = "addFourOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Four of Spades card to a card
     * pile.
     */
    private static final String ADD_FOUR_OF_SPADES_CARD_ACTION_ID = "addFourOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Jack of Clubs card to a card
     * pile.
     */
    private static final String ADD_JACK_OF_CLUBS_CARD_ACTION_ID = "addJackOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Jack of Diamonds card to a
     * card pile.
     */
    private static final String ADD_JACK_OF_DIAMONDS_CARD_ACTION_ID = "addJackOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Jack of Hearts card to a card
     * pile.
     */
    private static final String ADD_JACK_OF_HEARTS_CARD_ACTION_ID = "addJackOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Jack of Spades card to a card
     * pile.
     */
    private static final String ADD_JACK_OF_SPADES_CARD_ACTION_ID = "addJackOfSpadesCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to add a Joker card to a card pile. */
    private static final String ADD_JOKER_CARD_ACTION_ID = "addJokerCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a King of Clubs card to a card
     * pile.
     */
    private static final String ADD_KING_OF_CLUBS_CARD_ACTION_ID = "addKingOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a King of Diamonds card to a
     * card pile.
     */
    private static final String ADD_KING_OF_DIAMONDS_CARD_ACTION_ID = "addKingOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a King of Hearts card to a card
     * pile.
     */
    private static final String ADD_KING_OF_HEARTS_CARD_ACTION_ID = "addKingOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a King of Spades card to a card
     * pile.
     */
    private static final String ADD_KING_OF_SPADES_CARD_ACTION_ID = "addKingOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Nine of Clubs card to a card
     * pile.
     */
    private static final String ADD_NINE_OF_CLUBS_CARD_ACTION_ID = "addNineOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Nine of Diamonds card to a
     * card pile.
     */
    private static final String ADD_NINE_OF_DIAMONDS_CARD_ACTION_ID = "addNineOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Nine of Hearts card to a card
     * pile.
     */
    private static final String ADD_NINE_OF_HEARTS_CARD_ACTION_ID = "addNineOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Nine of Spades card to a card
     * pile.
     */
    private static final String ADD_NINE_OF_SPADES_CARD_ACTION_ID = "addNineOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Queen of Clubs card to a card
     * pile.
     */
    private static final String ADD_QUEEN_OF_CLUBS_CARD_ACTION_ID = "addQueenOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Queen of Diamonds card to a
     * card pile.
     */
    private static final String ADD_QUEEN_OF_DIAMONDS_CARD_ACTION_ID = "addQueenOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Queen of Hearts card to a card
     * pile.
     */
    private static final String ADD_QUEEN_OF_HEARTS_CARD_ACTION_ID = "addQueenOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Queen of Spades card to a card
     * pile.
     */
    private static final String ADD_QUEEN_OF_SPADES_CARD_ACTION_ID = "addQueenOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Seven of Clubs card to a card
     * pile.
     */
    private static final String ADD_SEVEN_OF_CLUBS_CARD_ACTION_ID = "addSevenOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Seven of Diamonds card to a
     * card pile.
     */
    private static final String ADD_SEVEN_OF_DIAMONDS_CARD_ACTION_ID = "addSevenOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Seven of Hearts card to a card
     * pile.
     */
    private static final String ADD_SEVEN_OF_HEARTS_CARD_ACTION_ID = "addSevenOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Seven of Spades card to a card
     * pile.
     */
    private static final String ADD_SEVEN_OF_SPADES_CARD_ACTION_ID = "addSevenOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Six of Clubs card to a card
     * pile.
     */
    private static final String ADD_SIX_OF_CLUBS_CARD_ACTION_ID = "addSixOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Six of Diamonds card to a card
     * pile.
     */
    private static final String ADD_SIX_OF_DIAMONDS_CARD_ACTION_ID = "addSixOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Six of Hearts card to a card
     * pile.
     */
    private static final String ADD_SIX_OF_HEARTS_CARD_ACTION_ID = "addSixOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Six of Spades card to a card
     * pile.
     */
    private static final String ADD_SIX_OF_SPADES_CARD_ACTION_ID = "addSixOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a standard 52-card deck to a
     * card pile.
     */
    private static final String ADD_STANDARD_52_CARD_DECK_ACTION_ID = "addStandard52CardDeckAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a standard 54-card deck to a
     * card pile.
     */
    private static final String ADD_STANDARD_54_CARD_DECK_ACTION_ID = "addStandard54CardDeckAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Ten of Clubs card to a card
     * pile.
     */
    private static final String ADD_TEN_OF_CLUBS_CARD_ACTION_ID = "addTenOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Ten of Diamonds card to a card
     * pile.
     */
    private static final String ADD_TEN_OF_DIAMONDS_CARD_ACTION_ID = "addTenOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Ten of Hearts card to a card
     * pile.
     */
    private static final String ADD_TEN_OF_HEARTS_CARD_ACTION_ID = "addTenOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Ten of Spades card to a card
     * pile.
     */
    private static final String ADD_TEN_OF_SPADES_CARD_ACTION_ID = "addTenOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Three of Clubs card to a card
     * pile.
     */
    private static final String ADD_THREE_OF_CLUBS_CARD_ACTION_ID = "addThreeOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Three of Diamonds card to a
     * card pile.
     */
    private static final String ADD_THREE_OF_DIAMONDS_CARD_ACTION_ID = "addThreeOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Three of Hearts card to a card
     * pile.
     */
    private static final String ADD_THREE_OF_HEARTS_CARD_ACTION_ID = "addThreeOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Three of Spades card to a card
     * pile.
     */
    private static final String ADD_THREE_OF_SPADES_CARD_ACTION_ID = "addThreeOfSpadesCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Two of Clubs card to a card
     * pile.
     */
    private static final String ADD_TWO_OF_CLUBS_CARD_ACTION_ID = "addTwoOfClubsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Two of Diamonds card to a card
     * pile.
     */
    private static final String ADD_TWO_OF_DIAMONDS_CARD_ACTION_ID = "addTwoOfDiamondsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Two of Hearts card to a card
     * pile.
     */
    private static final String ADD_TWO_OF_HEARTS_CARD_ACTION_ID = "addTwoOfHeartsCardAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to add a Two of Spades card to a card
     * pile.
     */
    private static final String ADD_TWO_OF_SPADES_CARD_ACTION_ID = "addTwoOfSpadesCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to exit the application. */
    private static final String EXIT_ACTION_ID = "exitAction"; //$NON-NLS-1$

    /** The identifier of the action used to flip a card in a card pile. */
    private static final String FLIP_CARD_ACTION_ID = "flipCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to open the about dialog. */
    private static final String OPEN_ABOUT_DIALOG_ACTION_ID = "openAboutDialogAction"; //$NON-NLS-1$

    /** The identifier of the action used to open a new table. */
    private static final String OPEN_NEW_TABLE_ACTION_ID = "openNewTableAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove a card from a card pile. */
    private static final String REMOVE_CARD_ACTION_ID = "removeCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove a card pile from the table. */
    private static final String REMOVE_CARD_PILE_ACTION_ID = "removeCardPileAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to reset the table origin to the view
     * origin.
     */
    private static final String RESET_TABLE_ORIGIN_ACTION_ID = "resetTableOriginAction"; //$NON-NLS-1$

    /** The identifier of the action used to save a table. */
    private static final String SAVE_TABLE_ACTION_ID = "saveTableAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to save a table to a different
     * location.
     */
    private static final String SAVE_TABLE_AS_ACTION_ID = "saveTableAsAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian down layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianDownCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian left layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianLeftCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian right layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianRightCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian up layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianUpCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the stacked layout on a card
     * pile.
     */
    private static final String SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID = "setStackedCardPileLayoutAction"; //$NON-NLS-1$

    /** The collection of actions. */
    private static final Map<String, BasicAction> actions_ = createActions();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Actions} class.
     */
    private Actions()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the collection of actions.
     * 
     * @return The collection of actions; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static Map<String, BasicAction> createActions()
    {
        final Map<String, BasicAction> actions = new HashMap<String, BasicAction>();

        actions.put( ADD_ACE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddAceOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddAceOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_ACE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddAceOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddAceOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_ACE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddAceOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddAceOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_ACE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddAceOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddAceOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_CARD_PILE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( Messages.AddCardPileAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddCardPileAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddCardPileAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddEightOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddEightOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddEightOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddEightOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddEightOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddEightOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddEightOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddEightOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFiveOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFiveOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFiveOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFiveOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFiveOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFiveOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFiveOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFiveOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFourOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFourOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFourOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFourOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFourOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFourOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddFourOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddFourOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddJackOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddJackOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddJackOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddJackOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddJackOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddJackOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddJackOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddJackOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_JOKER_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.special.joker" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddJokerCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddJokerCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddKingOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddKingOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddKingOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddKingOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddKingOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddKingOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddKingOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddKingOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddNineOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddNineOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddNineOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddNineOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddNineOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddNineOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddNineOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddNineOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddQueenOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddQueenOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddQueenOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddQueenOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddQueenOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddQueenOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddQueenOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddQueenOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSevenOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSevenOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSevenOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSevenOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSevenOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSevenOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSevenOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSevenOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSixOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSixOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSixOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSixOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSixOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSixOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddSixOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddSixOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_STANDARD_52_CARD_DECK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddStandard52CardDeckAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddStandard52CardDeckAction_text );
            }
        } );
        actions.put( ADD_STANDARD_54_CARD_DECK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddStandard54CardDeckAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddStandard54CardDeckAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTenOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTenOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTenOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTenOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTenOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTenOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTenOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTenOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddThreeOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddThreeOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddThreeOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddThreeOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddThreeOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddThreeOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddThreeOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddThreeOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTwoOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTwoOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTwoOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTwoOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTwoOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTwoOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.AddTwoOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.AddTwoOfSpadesCardAction_text );
            }
        } );
        actions.put( EXIT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.ExitAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.ExitAction_text );
            }
        } );
        actions.put( FLIP_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( Messages.FlipCardAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.FlipCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.FlipCardAction_text );
            }
        } );
        actions.put( OPEN_ABOUT_DIALOG_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.OpenAboutDialogAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.OpenAboutDialogAction_text );
            }
        } );
        actions.put( OPEN_NEW_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( Messages.OpenNewTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.OpenNewTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.OpenNewTableAction_text );
            }
        } );
        actions.put( REMOVE_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( Messages.RemoveCardAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.RemoveCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.RemoveCardAction_text );
            }
        } );
        actions.put( REMOVE_CARD_PILE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( Messages.RemoveCardPileAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.RemoveCardPileAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.RemoveCardPileAction_text );
            }
        } );
        actions.put( RESET_TABLE_ORIGIN_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.ResetTableOriginAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.ResetTableOriginAction_text );
            }
        } );
        actions.put( SAVE_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( Messages.SaveTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SaveTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SaveTableAction_text );
            }
        } );
        actions.put( SAVE_TABLE_AS_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SaveTableAsAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SaveTableAsAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_DOWN.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SetAccordianDownCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SetAccordianDownCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_LEFT.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SetAccordianLeftCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SetAccordianLeftCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_RIGHT.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SetAccordianRightCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SetAccordianRightCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_UP.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SetAccordianUpCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SetAccordianUpCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.STACKED.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( Messages.SetStackedCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, Messages.SetStackedCardPileLayoutAction_text );
            }
        } );

        return Collections.unmodifiableMap( actions );
    }

    /**
     * Gets the add Ace of Clubs card action.
     * 
     * @return The add Ace of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfClubsCardAction()
    {
        return actions_.get( ADD_ACE_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Ace of Diamonds card action.
     * 
     * @return The add Ace of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfDiamondsCardAction()
    {
        return actions_.get( ADD_ACE_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Ace of Hearts card action.
     * 
     * @return The add Ace of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfHeartsCardAction()
    {
        return actions_.get( ADD_ACE_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Ace of Spades card action.
     * 
     * @return The add Ace of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfSpadesCardAction()
    {
        return actions_.get( ADD_ACE_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add card pile action.
     * 
     * @return The add card pile action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddCardPileAction()
    {
        return actions_.get( ADD_CARD_PILE_ACTION_ID );
    }

    /**
     * Gets the add Eight of Clubs card action.
     * 
     * @return The add Eight of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfClubsCardAction()
    {
        return actions_.get( ADD_EIGHT_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Eight of Diamonds card action.
     * 
     * @return The add Eight of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfDiamondsCardAction()
    {
        return actions_.get( ADD_EIGHT_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Eight of Hearts card action.
     * 
     * @return The add Eight of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfHeartsCardAction()
    {
        return actions_.get( ADD_EIGHT_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Eight of Spades card action.
     * 
     * @return The add Eight of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfSpadesCardAction()
    {
        return actions_.get( ADD_EIGHT_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Five of Clubs card action.
     * 
     * @return The add Five of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfClubsCardAction()
    {
        return actions_.get( ADD_FIVE_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Five of Diamonds card action.
     * 
     * @return The add Five of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfDiamondsCardAction()
    {
        return actions_.get( ADD_FIVE_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Five of Hearts card action.
     * 
     * @return The add Five of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfHeartsCardAction()
    {
        return actions_.get( ADD_FIVE_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Five of Spades card action.
     * 
     * @return The add Five of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfSpadesCardAction()
    {
        return actions_.get( ADD_FIVE_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Four of Clubs card action.
     * 
     * @return The add Four of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfClubsCardAction()
    {
        return actions_.get( ADD_FOUR_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Four of Diamonds card action.
     * 
     * @return The add Four of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfDiamondsCardAction()
    {
        return actions_.get( ADD_FOUR_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Four of Hearts card action.
     * 
     * @return The add Four of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfHeartsCardAction()
    {
        return actions_.get( ADD_FOUR_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Four of Spades card action.
     * 
     * @return The add Four of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfSpadesCardAction()
    {
        return actions_.get( ADD_FOUR_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Jack of Clubs card action.
     * 
     * @return The add Jack of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfClubsCardAction()
    {
        return actions_.get( ADD_JACK_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Jack of Diamonds card action.
     * 
     * @return The add Jack of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfDiamondsCardAction()
    {
        return actions_.get( ADD_JACK_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Jack of Hearts card action.
     * 
     * @return The add Jack of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfHeartsCardAction()
    {
        return actions_.get( ADD_JACK_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Jack of Spades card action.
     * 
     * @return The add Jack of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfSpadesCardAction()
    {
        return actions_.get( ADD_JACK_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Joker card action.
     * 
     * @return The add Joker card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJokerCardAction()
    {
        return actions_.get( ADD_JOKER_CARD_ACTION_ID );
    }

    /**
     * Gets the add King of Clubs card action.
     * 
     * @return The add King of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfClubsCardAction()
    {
        return actions_.get( ADD_KING_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add King of Diamonds card action.
     * 
     * @return The add King of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfDiamondsCardAction()
    {
        return actions_.get( ADD_KING_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add King of Hearts card action.
     * 
     * @return The add King of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfHeartsCardAction()
    {
        return actions_.get( ADD_KING_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add King of Spades card action.
     * 
     * @return The add King of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfSpadesCardAction()
    {
        return actions_.get( ADD_KING_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Nine of Clubs card action.
     * 
     * @return The add Nine of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfClubsCardAction()
    {
        return actions_.get( ADD_NINE_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Nine of Diamonds card action.
     * 
     * @return The add Nine of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfDiamondsCardAction()
    {
        return actions_.get( ADD_NINE_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Nine of Hearts card action.
     * 
     * @return The add Nine of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfHeartsCardAction()
    {
        return actions_.get( ADD_NINE_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Nine of Spades card action.
     * 
     * @return The add Nine of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfSpadesCardAction()
    {
        return actions_.get( ADD_NINE_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Queen of Clubs card action.
     * 
     * @return The add Queen of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfClubsCardAction()
    {
        return actions_.get( ADD_QUEEN_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Queen of Diamonds card action.
     * 
     * @return The add Queen of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfDiamondsCardAction()
    {
        return actions_.get( ADD_QUEEN_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Queen of Hearts card action.
     * 
     * @return The add Queen of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfHeartsCardAction()
    {
        return actions_.get( ADD_QUEEN_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Queen of Spades card action.
     * 
     * @return The add Queen of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfSpadesCardAction()
    {
        return actions_.get( ADD_QUEEN_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Seven of Clubs card action.
     * 
     * @return The add Seven of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfClubsCardAction()
    {
        return actions_.get( ADD_SEVEN_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Seven of Diamonds card action.
     * 
     * @return The add Seven of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfDiamondsCardAction()
    {
        return actions_.get( ADD_SEVEN_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Seven of Hearts card action.
     * 
     * @return The add Seven of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfHeartsCardAction()
    {
        return actions_.get( ADD_SEVEN_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Seven of Spades card action.
     * 
     * @return The add Seven of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfSpadesCardAction()
    {
        return actions_.get( ADD_SEVEN_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Six of Clubs card action.
     * 
     * @return The add Six of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfClubsCardAction()
    {
        return actions_.get( ADD_SIX_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Six of Diamonds card action.
     * 
     * @return The add Six of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfDiamondsCardAction()
    {
        return actions_.get( ADD_SIX_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Six of Hearts card action.
     * 
     * @return The add Six of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfHeartsCardAction()
    {
        return actions_.get( ADD_SIX_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Six of Spades card action.
     * 
     * @return The add Six of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfSpadesCardAction()
    {
        return actions_.get( ADD_SIX_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add standard 52-card deck action.
     * 
     * @return The add standard 52-card deck action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddStandard52CardDeckAction()
    {
        return actions_.get( ADD_STANDARD_52_CARD_DECK_ACTION_ID );
    }

    /**
     * Gets the add standard 54-card deck action.
     * 
     * @return The add standard 54-card deck action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddStandard54CardDeckAction()
    {
        return actions_.get( ADD_STANDARD_54_CARD_DECK_ACTION_ID );
    }

    /**
     * Gets the add Ten of Clubs card action.
     * 
     * @return The add Ten of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfClubsCardAction()
    {
        return actions_.get( ADD_TEN_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Ten of Diamonds card action.
     * 
     * @return The add Ten of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfDiamondsCardAction()
    {
        return actions_.get( ADD_TEN_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Ten of Hearts card action.
     * 
     * @return The add Ten of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfHeartsCardAction()
    {
        return actions_.get( ADD_TEN_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Ten of Spades card action.
     * 
     * @return The add Ten of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfSpadesCardAction()
    {
        return actions_.get( ADD_TEN_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Three of Clubs card action.
     * 
     * @return The add Three of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfClubsCardAction()
    {
        return actions_.get( ADD_THREE_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Three of Diamonds card action.
     * 
     * @return The add Three of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfDiamondsCardAction()
    {
        return actions_.get( ADD_THREE_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Three of Hearts card action.
     * 
     * @return The add Three of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfHeartsCardAction()
    {
        return actions_.get( ADD_THREE_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Three of Spades card action.
     * 
     * @return The add Three of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfSpadesCardAction()
    {
        return actions_.get( ADD_THREE_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the add Two of Clubs card action.
     * 
     * @return The add Two of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfClubsCardAction()
    {
        return actions_.get( ADD_TWO_OF_CLUBS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Two of Diamonds card action.
     * 
     * @return The add Two of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfDiamondsCardAction()
    {
        return actions_.get( ADD_TWO_OF_DIAMONDS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Two of Hearts card action.
     * 
     * @return The add Two of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfHeartsCardAction()
    {
        return actions_.get( ADD_TWO_OF_HEARTS_CARD_ACTION_ID );
    }

    /**
     * Gets the add Two of Spades card action.
     * 
     * @return The add Two of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfSpadesCardAction()
    {
        return actions_.get( ADD_TWO_OF_SPADES_CARD_ACTION_ID );
    }

    /**
     * Gets the exit action.
     * 
     * @return The exit action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getExitAction()
    {
        return actions_.get( EXIT_ACTION_ID );
    }

    /**
     * Gets the flip card action.
     * 
     * @return The flip card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getFlipCardAction()
    {
        return actions_.get( FLIP_CARD_ACTION_ID );
    }

    /**
     * Gets the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenAboutDialogAction()
    {
        return actions_.get( OPEN_ABOUT_DIALOG_ACTION_ID );
    }

    /**
     * Gets the open new table action.
     * 
     * @return The open new table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenNewTableAction()
    {
        return actions_.get( OPEN_NEW_TABLE_ACTION_ID );
    }

    /**
     * Gets the remove card action.
     * 
     * @return The remove card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveCardAction()
    {
        return actions_.get( REMOVE_CARD_ACTION_ID );
    }

    /**
     * Gets the remove card pile action.
     * 
     * @return The remove card pile action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveCardPileAction()
    {
        return actions_.get( REMOVE_CARD_PILE_ACTION_ID );
    }

    /**
     * Gets the reset table origin action.
     * 
     * @return The reset table origin action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getResetTableOriginAction()
    {
        return actions_.get( RESET_TABLE_ORIGIN_ACTION_ID );
    }

    /**
     * Gets the save table action.
     * 
     * @return The save table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSaveTableAction()
    {
        return actions_.get( SAVE_TABLE_ACTION_ID );
    }

    /**
     * Gets the save table as action.
     * 
     * @return The save table as action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSaveTableAsAction()
    {
        return actions_.get( SAVE_TABLE_AS_ACTION_ID );
    }

    /**
     * Gets the set accordian down card pile layout action.
     * 
     * @return The set accordian down card pile layout action; never {@code
     *         null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianDownCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian left card pile layout action.
     * 
     * @return The set accordian left card pile layout action; never {@code
     *         null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianLeftCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian right card pile layout action.
     * 
     * @return The set accordian right card pile layout action; never {@code
     *         null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianRightCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian up card pile layout action.
     * 
     * @return The set accordian up card pile layout action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianUpCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set stacked card pile layout action.
     * 
     * @return The set stacked card pile layout action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetStackedCardPileLayoutAction()
    {
        return actions_.get( SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Updates the state of all actions.
     */
    static void updateAll()
    {
        for( final BasicAction action : actions_.values() )
        {
            action.update();
        }
    }
}
