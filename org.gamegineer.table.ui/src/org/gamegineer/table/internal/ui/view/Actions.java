/*
 * Actions.java
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

    /**
     * The identifier of the action used to cancel a table network control
     * request.
     */
    private static final String CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID = "cancelTableNetworkControlRequestAction"; //$NON-NLS-1$

    /** The identifier of the action used to disconnect a table network. */
    private static final String DISCONNECT_TABLE_NETWORK_ACTION_ID = "disconnectTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to exit the application. */
    private static final String EXIT_ACTION_ID = "exitAction"; //$NON-NLS-1$

    /** The identifier of the action used to flip a card in a card pile. */
    private static final String FLIP_CARD_ACTION_ID = "flipCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to give table network control. */
    private static final String GIVE_TABLE_NETWORK_CONTROL_ID = "giveTableNetworkControlAction"; //$NON-NLS-1$

    /** The identifier of the action used to host a table network. */
    private static final String HOST_TABLE_NETWORK_ACTION_ID = "hostTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to join a table network. */
    private static final String JOIN_TABLE_NETWORK_ACTION_ID = "joinTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to open the about dialog. */
    private static final String OPEN_ABOUT_DIALOG_ACTION_ID = "openAboutDialogAction"; //$NON-NLS-1$

    /** The identifier of the action used to open a new table. */
    private static final String OPEN_NEW_TABLE_ACTION_ID = "openNewTableAction"; //$NON-NLS-1$

    /** The identifier of the action used to open an existing table. */
    private static final String OPEN_TABLE_ACTION_ID = "openTableAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to remove all card piles from the
     * table.
     */
    private static final String REMOVE_ALL_CARD_PILES_ACTION_ID = "removeAllCardPilesAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove all cards from a card pile. */
    private static final String REMOVE_ALL_CARDS_ACTION_ID = "removeAllCardsAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove a card from a card pile. */
    private static final String REMOVE_CARD_ACTION_ID = "removeCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove a card pile from the table. */
    private static final String REMOVE_CARD_PILE_ACTION_ID = "removeCardPileAction"; //$NON-NLS-1$

    /** The identifier of the action used to request table network control. */
    private static final String REQUEST_TABLE_NETWORK_CONTROL_ID = "requestTableNetworkControlAction"; //$NON-NLS-1$

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
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddAceOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddAceOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_ACE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddAceOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddAceOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_ACE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddAceOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddAceOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_ACE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddAceOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddAceOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_CARD_PILE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.AddCardPileAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddCardPileAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddCardPileAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddEightOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddEightOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddEightOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddEightOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddEightOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddEightOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_EIGHT_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddEightOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddEightOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFiveOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFiveOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFiveOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFiveOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFiveOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFiveOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_FIVE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFiveOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFiveOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFourOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFourOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFourOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFourOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFourOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFourOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_FOUR_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddFourOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddFourOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddJackOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddJackOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddJackOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddJackOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddJackOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddJackOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_JACK_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddJackOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddJackOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_JOKER_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.special.joker" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddJokerCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddJokerCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddKingOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddKingOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddKingOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddKingOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddKingOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddKingOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_KING_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddKingOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddKingOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddNineOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddNineOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddNineOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddNineOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddNineOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddNineOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_NINE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddNineOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddNineOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddQueenOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddQueenOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddQueenOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddQueenOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddQueenOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddQueenOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_QUEEN_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddQueenOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddQueenOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSevenOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSevenOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSevenOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSevenOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSevenOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSevenOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_SEVEN_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSevenOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSevenOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSixOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSixOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSixOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSixOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSixOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSixOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_SIX_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddSixOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddSixOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_STANDARD_52_CARD_DECK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddStandard52CardDeckAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddStandard52CardDeckAction_text );
            }
        } );
        actions.put( ADD_STANDARD_54_CARD_DECK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddStandard54CardDeckAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddStandard54CardDeckAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTenOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTenOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTenOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTenOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTenOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTenOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_TEN_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTenOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTenOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddThreeOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddThreeOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddThreeOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddThreeOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddThreeOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddThreeOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_THREE_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddThreeOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddThreeOfSpadesCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_CLUBS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.clubs.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTwoOfClubsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTwoOfClubsCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_DIAMONDS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.diamonds.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTwoOfDiamondsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTwoOfDiamondsCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_HEARTS_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.hearts.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTwoOfHeartsCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTwoOfHeartsCardAction_text );
            }
        } );
        actions.put( ADD_TWO_OF_SPADES_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cardSurfaces.spades.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.AddTwoOfSpadesCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.AddTwoOfSpadesCardAction_text );
            }
        } );
        actions.put( CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.CancelTableNetworkControlRequestAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.CancelTableNetworkControlRequestAction_text );
            }
        } );
        actions.put( DISCONNECT_TABLE_NETWORK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.DisconnectTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.DisconnectTableNetworkAction_text );
            }
        } );
        actions.put( EXIT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.ExitAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.ExitAction_text );
            }
        } );
        actions.put( FLIP_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.FlipCardAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.FlipCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.FlipCardAction_text );
            }
        } );
        actions.put( GIVE_TABLE_NETWORK_CONTROL_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.GiveTableNetworkControlAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.GiveTableNetworkControlAction_text );
            }
        } );
        actions.put( HOST_TABLE_NETWORK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.HostTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.HostTableNetworkAction_text );
            }
        } );
        actions.put( JOIN_TABLE_NETWORK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.JoinTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.JoinTableNetworkAction_text );
            }
        } );
        actions.put( OPEN_ABOUT_DIALOG_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenAboutDialogAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenAboutDialogAction_text );
            }
        } );
        actions.put( OPEN_NEW_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenNewTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenNewTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenNewTableAction_text );
            }
        } );
        actions.put( OPEN_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                // NB: Using an empty string for the command key to force the user to be
                // prompted for a file name.  Cannot use null because AbstractButton
                // substitutes the button text for the command key when the command key
                // is null.
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenTableAction_accelerator ) );
                putValue( ACTION_COMMAND_KEY, "" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenTableAction_text );
            }
        } );
        actions.put( REMOVE_ALL_CARD_PILES_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardPilesAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardPilesAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveAllCardPilesAction_text );
            }
        } );
        actions.put( REMOVE_ALL_CARDS_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardsAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardsAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveAllCardsAction_text );
            }
        } );
        actions.put( REMOVE_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveCardAction_text );
            }
        } );
        actions.put( REMOVE_CARD_PILE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardPileAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardPileAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveCardPileAction_text );
            }
        } );
        actions.put( REQUEST_TABLE_NETWORK_CONTROL_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RequestTableNetworkControlAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RequestTableNetworkControlAction_text );
            }
        } );
        actions.put( RESET_TABLE_ORIGIN_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.ResetTableOriginAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.ResetTableOriginAction_text );
            }
        } );
        actions.put( SAVE_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SaveTableAction_text );
            }
        } );
        actions.put( SAVE_TABLE_AS_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAsAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SaveTableAsAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_DOWN.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianDownCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianDownCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_LEFT.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianLeftCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianLeftCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_RIGHT.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianRightCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianRightCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.ACCORDIAN_UP.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianUpCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianUpCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, CardPileLayout.STACKED.name() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetStackedCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetStackedCardPileLayoutAction_text );
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
     * Gets the cancel table network control request action.
     * 
     * @return The cancel table network control request action; never {@code
     *         null}.
     */
    /* @NonNull */
    static BasicAction getCancelTableNetworkControlRequestAction()
    {
        return actions_.get( CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID );
    }

    /**
     * Gets the disconnect table network action.
     * 
     * @return The disconnect table network action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getDisconnectTableNetworkAction()
    {
        return actions_.get( DISCONNECT_TABLE_NETWORK_ACTION_ID );
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
     * Gets the give table network control action.
     * 
     * @return The give table network control action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getGiveTableNetworkControlAction()
    {
        return actions_.get( GIVE_TABLE_NETWORK_CONTROL_ID );
    }

    /**
     * Gets the host table network action.
     * 
     * @return The host table network action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getHostTableNetworkAction()
    {
        return actions_.get( HOST_TABLE_NETWORK_ACTION_ID );
    }

    /**
     * Gets the join table network action.
     * 
     * @return The join table network action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getJoinTableNetworkAction()
    {
        return actions_.get( JOIN_TABLE_NETWORK_ACTION_ID );
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
     * Gets the open table action.
     * 
     * @return The open table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenTableAction()
    {
        return actions_.get( OPEN_TABLE_ACTION_ID );
    }

    /**
     * Gets the remove all card piles action.
     * 
     * @return The remove all card piles action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveAllCardPilesAction()
    {
        return actions_.get( REMOVE_ALL_CARD_PILES_ACTION_ID );
    }

    /**
     * Gets the remove all cards action.
     * 
     * @return The remove all cards action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveAllCardsAction()
    {
        return actions_.get( REMOVE_ALL_CARDS_ACTION_ID );
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
     * Gets the request table network control action.
     * 
     * @return The request table network control action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRequestTableNetworkControlAction()
    {
        return actions_.get( REQUEST_TABLE_NETWORK_CONTROL_ID );
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
