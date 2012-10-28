/*
 * NlsMessages.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jul 22, 2011 at 11:14:51 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.internal.ui.Branding;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class NlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- AboutDialog ------------------------------------------------------

    /** The About dialog message. */
    public static String AboutDialog_message;

    /** The About dialog title. */
    public static String AboutDialog_title;

    // --- AddAceOfClubsCardAction ------------------------------------------

    /** The add Ace of Clubs card action mnemonic. */
    public static String AddAceOfClubsCardAction_mnemonic;

    /** The add Ace of Clubs card action text. */
    public static String AddAceOfClubsCardAction_text;

    // --- AddAceOfDiamondsCardAction ---------------------------------------

    /** The add Ace of Diamonds card action mnemonic. */
    public static String AddAceOfDiamondsCardAction_mnemonic;

    /** The add Ace of Diamonds card action text. */
    public static String AddAceOfDiamondsCardAction_text;

    // --- AddAceOfHeartsCardAction -----------------------------------------

    /** The add Ace of Hearts card action mnemonic. */
    public static String AddAceOfHeartsCardAction_mnemonic;

    /** The add Ace of Hearts card action text. */
    public static String AddAceOfHeartsCardAction_text;

    // --- AddAceOfSpadesCardAction -----------------------------------------

    /** The add Ace of Spades card action mnemonic. */
    public static String AddAceOfSpadesCardAction_mnemonic;

    /** The add Ace of Spades card action text. */
    public static String AddAceOfSpadesCardAction_text;

    // --- AddCardPileAction ------------------------------------------------

    /** The add card pile action accelerator. */
    public static String AddCardPileAction_accelerator;

    /** The add card pile action mnemonic. */
    public static String AddCardPileAction_mnemonic;

    /** The add card pile action text. */
    public static String AddCardPileAction_text;

    // --- AddEightOfClubsCardAction ----------------------------------------

    /** The add Eight of Clubs card action mnemonic. */
    public static String AddEightOfClubsCardAction_mnemonic;

    /** The add Eight of Clubs card action text. */
    public static String AddEightOfClubsCardAction_text;

    // --- AddEightOfDiamondsCardAction -------------------------------------

    /** The add Eight of Diamonds card action mnemonic. */
    public static String AddEightOfDiamondsCardAction_mnemonic;

    /** The add Eight of Diamonds card action text. */
    public static String AddEightOfDiamondsCardAction_text;

    // --- AddEightOfHeartsCardAction ---------------------------------------

    /** The add Eight of Hearts card action mnemonic. */
    public static String AddEightOfHeartsCardAction_mnemonic;

    /** The add Eight of Hearts card action text. */
    public static String AddEightOfHeartsCardAction_text;

    // --- AddEightOfSpadesCardAction ---------------------------------------

    /** The add Eight of Spades card action mnemonic. */
    public static String AddEightOfSpadesCardAction_mnemonic;

    /** The add Eight of Spades card action text. */
    public static String AddEightOfSpadesCardAction_text;

    // --- AddFiveOfClubsCardAction -----------------------------------------

    /** The add Five of Clubs card action mnemonic. */
    public static String AddFiveOfClubsCardAction_mnemonic;

    /** The add Five of Clubs card action text. */
    public static String AddFiveOfClubsCardAction_text;

    // --- AddFiveOfDiamondsCardAction --------------------------------------

    /** The add Five of Diamonds card action mnemonic. */
    public static String AddFiveOfDiamondsCardAction_mnemonic;

    /** The add Five of Diamonds card action text. */
    public static String AddFiveOfDiamondsCardAction_text;

    // --- AddFiveOfHeartsCardAction ----------------------------------------

    /** The add Five of Hearts card action mnemonic. */
    public static String AddFiveOfHeartsCardAction_mnemonic;

    /** The add Five of Hearts card action text. */
    public static String AddFiveOfHeartsCardAction_text;

    // --- AddFiveOfSpadesCardAction ----------------------------------------

    /** The add Five of Spades card action mnemonic. */
    public static String AddFiveOfSpadesCardAction_mnemonic;

    /** The add Five of Spades card action text. */
    public static String AddFiveOfSpadesCardAction_text;

    // --- AddFourOfClubsCardAction -----------------------------------------

    /** The add Four of Clubs card action mnemonic. */
    public static String AddFourOfClubsCardAction_mnemonic;

    /** The add Four of Clubs card action text. */
    public static String AddFourOfClubsCardAction_text;

    // --- AddFourOfDiamondsCardAction --------------------------------------

    /** The add Four of Diamonds card action mnemonic. */
    public static String AddFourOfDiamondsCardAction_mnemonic;

    /** The add Four of Diamonds card action text. */
    public static String AddFourOfDiamondsCardAction_text;

    // --- AddFourOfHeartsCardAction ----------------------------------------

    /** The add Four of Hearts card action mnemonic. */
    public static String AddFourOfHeartsCardAction_mnemonic;

    /** The add Four of Hearts card action text. */
    public static String AddFourOfHeartsCardAction_text;

    // --- AddFourOfSpadesCardAction ----------------------------------------

    /** The add Four of Spades card action mnemonic. */
    public static String AddFourOfSpadesCardAction_mnemonic;

    /** The add Four of Spades card action text. */
    public static String AddFourOfSpadesCardAction_text;

    // --- AddJackOfClubsCardAction -----------------------------------------

    /** The add Jack of Clubs card action mnemonic. */
    public static String AddJackOfClubsCardAction_mnemonic;

    /** The add Jack of Clubs card action text. */
    public static String AddJackOfClubsCardAction_text;

    // --- AddJackOfDiamondsCardAction --------------------------------------

    /** The add Jack of Diamonds card action mnemonic. */
    public static String AddJackOfDiamondsCardAction_mnemonic;

    /** The add Jack of Diamonds card action text. */
    public static String AddJackOfDiamondsCardAction_text;

    // --- AddJackOfHeartsCardAction ----------------------------------------

    /** The add Jack of Hearts card action mnemonic. */
    public static String AddJackOfHeartsCardAction_mnemonic;

    /** The add Jack of Hearts card action text. */
    public static String AddJackOfHeartsCardAction_text;

    // --- AddJackOfSpadesCardAction ----------------------------------------

    /** The add Jack of Spades card action mnemonic. */
    public static String AddJackOfSpadesCardAction_mnemonic;

    /** The add Jack of Spades card action text. */
    public static String AddJackOfSpadesCardAction_text;

    // --- AddJokerCardAction -----------------------------------------------

    /** The add Joker card action mnemonic. */
    public static String AddJokerCardAction_mnemonic;

    /** The add Joker card action text. */
    public static String AddJokerCardAction_text;

    // --- AddKingOfClubsCardAction -----------------------------------------

    /** The add King of Clubs card action mnemonic. */
    public static String AddKingOfClubsCardAction_mnemonic;

    /** The add King of Clubs card action text. */
    public static String AddKingOfClubsCardAction_text;

    // --- AddKingOfDiamondsCardAction --------------------------------------

    /** The add King of Diamonds card action mnemonic. */
    public static String AddKingOfDiamondsCardAction_mnemonic;

    /** The add King of Diamonds card action text. */
    public static String AddKingOfDiamondsCardAction_text;

    // --- AddKingOfHeartsCardAction ----------------------------------------

    /** The add King of Hearts card action mnemonic. */
    public static String AddKingOfHeartsCardAction_mnemonic;

    /** The add King of Hearts card action text. */
    public static String AddKingOfHeartsCardAction_text;

    // --- AddKingOfSpadesCardAction ----------------------------------------

    /** The add King of Spades card action mnemonic. */
    public static String AddKingOfSpadesCardAction_mnemonic;

    /** The add King of Spades card action text. */
    public static String AddKingOfSpadesCardAction_text;

    // --- AddNineOfClubsCardAction -----------------------------------------

    /** The add Nine of Clubs card action mnemonic. */
    public static String AddNineOfClubsCardAction_mnemonic;

    /** The add Nine of Clubs card action text. */
    public static String AddNineOfClubsCardAction_text;

    // --- AddNineOfDiamondsCardAction --------------------------------------

    /** The add Nine of Diamonds card action mnemonic. */
    public static String AddNineOfDiamondsCardAction_mnemonic;

    /** The add Nine of Diamonds card action text. */
    public static String AddNineOfDiamondsCardAction_text;

    // --- AddNineOfHeartsCardAction ----------------------------------------

    /** The add Nine of Hearts card action mnemonic. */
    public static String AddNineOfHeartsCardAction_mnemonic;

    /** The add Nine of Hearts card action text. */
    public static String AddNineOfHeartsCardAction_text;

    // --- AddNineOfSpadesCardAction ----------------------------------------

    /** The add Nine of Spades card action mnemonic. */
    public static String AddNineOfSpadesCardAction_mnemonic;

    /** The add Nine of Spades card action text. */
    public static String AddNineOfSpadesCardAction_text;

    // --- AddQueenOfClubsCardAction ----------------------------------------

    /** The add Queen of Clubs card action mnemonic. */
    public static String AddQueenOfClubsCardAction_mnemonic;

    /** The add Queen of Clubs card action text. */
    public static String AddQueenOfClubsCardAction_text;

    // --- AddQueenOfDiamondsCardAction -------------------------------------

    /** The add Queen of Diamonds card action mnemonic. */
    public static String AddQueenOfDiamondsCardAction_mnemonic;

    /** The add Queen of Diamonds card action text. */
    public static String AddQueenOfDiamondsCardAction_text;

    // --- AddQueenOfHeartsCardAction ---------------------------------------

    /** The add Queen of Hearts card action mnemonic. */
    public static String AddQueenOfHeartsCardAction_mnemonic;

    /** The add Queen of Hearts card action text. */
    public static String AddQueenOfHeartsCardAction_text;

    // --- AddQueenOfSpadesCardAction ---------------------------------------

    /** The add Queen of Spades card action mnemonic. */
    public static String AddQueenOfSpadesCardAction_mnemonic;

    /** The add Queen of Spades card action text. */
    public static String AddQueenOfSpadesCardAction_text;

    // --- AddSevenOfClubsCardAction ----------------------------------------

    /** The add Seven of Clubs card action mnemonic. */
    public static String AddSevenOfClubsCardAction_mnemonic;

    /** The add Seven of Clubs card action text. */
    public static String AddSevenOfClubsCardAction_text;

    // --- AddSevenOfDiamondsCardAction -------------------------------------

    /** The add Seven of Diamonds card action mnemonic. */
    public static String AddSevenOfDiamondsCardAction_mnemonic;

    /** The add Seven of Diamonds card action text. */
    public static String AddSevenOfDiamondsCardAction_text;

    // --- AddSevenOfHeartsCardAction ---------------------------------------

    /** The add Seven of Hearts card action mnemonic. */
    public static String AddSevenOfHeartsCardAction_mnemonic;

    /** The add Seven of Hearts card action text. */
    public static String AddSevenOfHeartsCardAction_text;

    // --- AddSevenOfSpadesCardAction ---------------------------------------

    /** The add Seven of Spades card action mnemonic. */
    public static String AddSevenOfSpadesCardAction_mnemonic;

    /** The add Seven of Spades card action text. */
    public static String AddSevenOfSpadesCardAction_text;

    // --- AddSixOfClubsCardAction ------------------------------------------

    /** The add Six of Clubs card action mnemonic. */
    public static String AddSixOfClubsCardAction_mnemonic;

    /** The add Six of Clubs card action text. */
    public static String AddSixOfClubsCardAction_text;

    // --- AddSixOfDiamondsCardAction ---------------------------------------

    /** The add Six of Diamonds card action mnemonic. */
    public static String AddSixOfDiamondsCardAction_mnemonic;

    /** The add Six of Diamonds card action text. */
    public static String AddSixOfDiamondsCardAction_text;

    // --- AddSixOfHeartsCardAction -----------------------------------------

    /** The add Six of Hearts card action mnemonic. */
    public static String AddSixOfHeartsCardAction_mnemonic;

    /** The add Six of Hearts card action text. */
    public static String AddSixOfHeartsCardAction_text;

    // --- AddSixOfSpadesCardAction -----------------------------------------

    /** The add Six of Spades card action mnemonic. */
    public static String AddSixOfSpadesCardAction_mnemonic;

    /** The add Six of Spades card action text. */
    public static String AddSixOfSpadesCardAction_text;

    // --- AddStandard52CardDeckAction --------------------------------------

    /** The add standard 52-card deck action mnemonic. */
    public static String AddStandard52CardDeckAction_mnemonic;

    /** The add standard 52-card deck action text. */
    public static String AddStandard52CardDeckAction_text;

    // --- AddStandard54CardDeckAction --------------------------------------

    /** The add standard 54-card deck action mnemonic. */
    public static String AddStandard54CardDeckAction_mnemonic;

    /** The add standard 54-card deck action text. */
    public static String AddStandard54CardDeckAction_text;

    // --- AddTenOfClubsCardAction ------------------------------------------

    /** The add Ten of Clubs card action mnemonic. */
    public static String AddTenOfClubsCardAction_mnemonic;

    /** The add Ten of Clubs card action text. */
    public static String AddTenOfClubsCardAction_text;

    // --- AddTenOfDiamondsCardAction ---------------------------------------

    /** The add Ten of Diamonds card action mnemonic. */
    public static String AddTenOfDiamondsCardAction_mnemonic;

    /** The add Ten of Diamonds card action text. */
    public static String AddTenOfDiamondsCardAction_text;

    // --- AddTenOfHeartsCardAction -----------------------------------------

    /** The add Ten of Hearts card action mnemonic. */
    public static String AddTenOfHeartsCardAction_mnemonic;

    /** The add Ten of Hearts card action text. */
    public static String AddTenOfHeartsCardAction_text;

    // --- AddTenOfSpadesCardAction -----------------------------------------

    /** The add Ten of Spades card action mnemonic. */
    public static String AddTenOfSpadesCardAction_mnemonic;

    /** The add Ten of Spades card action text. */
    public static String AddTenOfSpadesCardAction_text;

    // --- AddThreeOfClubsCardAction ----------------------------------------

    /** The add Three of Clubs card action mnemonic. */
    public static String AddThreeOfClubsCardAction_mnemonic;

    /** The add Three of Clubs card action text. */
    public static String AddThreeOfClubsCardAction_text;

    // --- AddThreeOfDiamondsCardAction -------------------------------------

    /** The add Three of Diamonds card action mnemonic. */
    public static String AddThreeOfDiamondsCardAction_mnemonic;

    /** The add Three of Diamonds card action text. */
    public static String AddThreeOfDiamondsCardAction_text;

    // --- AddThreeOfHeartsCardAction ---------------------------------------

    /** The add Three of Hearts card action mnemonic. */
    public static String AddThreeOfHeartsCardAction_mnemonic;

    /** The add Three of Hearts card action text. */
    public static String AddThreeOfHeartsCardAction_text;

    // --- AddThreeOfSpadesCardAction ---------------------------------------

    /** The add Three of Spades card action mnemonic. */
    public static String AddThreeOfSpadesCardAction_mnemonic;

    /** The add Three of Spades card action text. */
    public static String AddThreeOfSpadesCardAction_text;

    // --- AddTwoOfClubsCardAction ------------------------------------------

    /** The add Two of Clubs card action mnemonic. */
    public static String AddTwoOfClubsCardAction_mnemonic;

    /** The add Two of Clubs card action text. */
    public static String AddTwoOfClubsCardAction_text;

    // --- AddTwoOfDiamondsCardAction ---------------------------------------

    /** The add Two of Diamonds card action mnemonic. */
    public static String AddTwoOfDiamondsCardAction_mnemonic;

    /** The add Two of Diamonds card action text. */
    public static String AddTwoOfDiamondsCardAction_text;

    // --- AddTwoOfHeartsCardAction -----------------------------------------

    /** The add Two of Hearts card action mnemonic. */
    public static String AddTwoOfHeartsCardAction_mnemonic;

    /** The add Two of Hearts card action text. */
    public static String AddTwoOfHeartsCardAction_text;

    // --- AddTwoOfSpadesCardAction -----------------------------------------

    /** The add Two of Spades card action mnemonic. */
    public static String AddTwoOfSpadesCardAction_mnemonic;

    /** The add Two of Spades card action text. */
    public static String AddTwoOfSpadesCardAction_text;

    // --- CancelTableNetworkControlRequestAction ---------------------------

    /** The cancel table network control request action mnemonic. */
    public static String CancelTableNetworkControlRequestAction_mnemonic;

    /** The cancel table network control request action text. */
    public static String CancelTableNetworkControlRequestAction_text;

    // --- CardPilePopupMenu ------------------------------------------------

    /** The Add Card menu mnemonic. */
    public static String CardPilePopupMenu_addCard_mnemonic;

    /** The Add Card menu text. */
    public static String CardPilePopupMenu_addCard_text;

    /** The Add Clubs Card menu mnemonic. */
    public static String CardPilePopupMenu_addClubsCard_mnemonic;

    /** The Add Clubs Card menu text. */
    public static String CardPilePopupMenu_addClubsCard_text;

    /** The Add Component menu mnemonic. */
    public static String CardPilePopupMenu_addComponent_mnemonic;

    /** The Add Component menu text. */
    public static String CardPilePopupMenu_addComponent_text;

    /** The Add Deck menu mnemonic. */
    public static String CardPilePopupMenu_addDeck_mnemonic;

    /** The Add Deck menu text. */
    public static String CardPilePopupMenu_addDeck_text;

    /** The Add Diamonds Card menu mnemonic. */
    public static String CardPilePopupMenu_addDiamondsCard_mnemonic;

    /** The Add Diamonds Card menu text. */
    public static String CardPilePopupMenu_addDiamondsCard_text;

    /** The Add Hearts Card menu mnemonic. */
    public static String CardPilePopupMenu_addHeartsCard_mnemonic;

    /** The Add Hearts Card menu text. */
    public static String CardPilePopupMenu_addHeartsCard_text;

    /** The Add Spades Card menu mnemonic. */
    public static String CardPilePopupMenu_addSpadesCard_mnemonic;

    /** The Add Spades Card menu text. */
    public static String CardPilePopupMenu_addSpadesCard_text;

    /** The Add Special Card menu mnemonic. */
    public static String CardPilePopupMenu_addSpecialCard_mnemonic;

    /** The Add Special Card menu text. */
    public static String CardPilePopupMenu_addSpecialCard_text;

    /** The Layout menu mnemonic. */
    public static String CardPilePopupMenu_layout_mnemonic;

    /** The Layout menu text. */
    public static String CardPilePopupMenu_layout_text;

    // --- DisconnectTableNetworkAction -------------------------------------

    /** The disconnect table network action mnemonic. */
    public static String DisconnectTableNetworkAction_mnemonic;

    /** The disconnect table network action text. */
    public static String DisconnectTableNetworkAction_text;

    // --- DisplayHelpAction ------------------------------------------------

    /** The display help action mnemonic. */
    public static String DisplayHelpAction_mnemonic;

    /** The display help action text. */
    public static String DisplayHelpAction_text;

    // --- ExitAction -------------------------------------------------------

    /** The exit action mnemonic. */
    public static String ExitAction_mnemonic;

    /** The exit action text. */
    public static String ExitAction_text;

    // --- FileChoosers -----------------------------------------------------

    /** The table file filter. */
    public static String FileChoosers_fileFilter_table;

    // --- FlipCardAction ---------------------------------------------------

    /** The flip card action accelerator. */
    public static String FlipCardAction_accelerator;

    /** The flip card action mnemonic. */
    public static String FlipCardAction_mnemonic;

    /** The flip card action text. */
    public static String FlipCardAction_text;

    // --- GiveTableNetworkControlAction ------------------------------------

    /** The give table network control action mnemonic. */
    public static String GiveTableNetworkControlAction_mnemonic;

    /** The give table network control action text. */
    public static String GiveTableNetworkControlAction_text;

    // --- HostTableNetworkAction -------------------------------------------

    /** The host table network action mnemonic. */
    public static String HostTableNetworkAction_mnemonic;

    /** The host table network action text. */
    public static String HostTableNetworkAction_text;

    // --- JoinTableNetworkAction -------------------------------------------

    /** The join table network action mnemonic. */
    public static String JoinTableNetworkAction_mnemonic;

    /** The join table network action text. */
    public static String JoinTableNetworkAction_text;

    // --- MainFrame --------------------------------------------------------

    /** The Confirm Save Dirty Table dialog message. */
    public static String MainFrame_confirmSaveDirtyTable_message;

    /** An error occurred while opening the table. */
    public static String MainFrame_openTable_error;

    /** An error occurred while saving the table. */
    public static String MainFrame_saveTable_error;

    /** The frame title. */
    public static String MainFrame_title;

    /** The name of an untitled table. */
    public static String MainFrame_untitledTable;

    // --- MainView ---------------------------------------------------------

    /** The table network was disconnected due to a generic error. */
    public static String MainView_tableNetworkDisconnected_error_generic;

    /** The table network was disconnected due to a server shutdown. */
    public static String MainView_tableNetworkDisconnected_error_serverShutdown;

    /** The table network was disconnected due to a transport error. */
    public static String MainView_tableNetworkDisconnected_error_transportError;

    /**
     * The table network was disconnected due to an unexpected peer termination.
     */
    public static String MainView_tableNetworkDisconnected_error_unexpectedPeerTermination;

    // --- MenuBarView ------------------------------------------------------

    /** The Add Card menu mnemonic. */
    public static String MenuBarView_addCard_mnemonic;

    /** The Add Card menu text. */
    public static String MenuBarView_addCard_text;

    /** The Add Clubs Card menu mnemonic. */
    public static String MenuBarView_addClubsCard_mnemonic;

    /** The Add Clubs Card menu text. */
    public static String MenuBarView_addClubsCard_text;

    /** The Add Component menu mnemonic. */
    public static String MenuBarView_addComponent_mnemonic;

    /** The Add Component menu text. */
    public static String MenuBarView_addComponent_text;

    /** The Add Deck menu mnemonic. */
    public static String MenuBarView_addDeck_mnemonic;

    /** The Add Deck menu text. */
    public static String MenuBarView_addDeck_text;

    /** The Add Diamonds Card menu mnemonic. */
    public static String MenuBarView_addDiamondsCard_mnemonic;

    /** The Add Diamonds Card menu text. */
    public static String MenuBarView_addDiamondsCard_text;

    /** The Add Hearts Card menu mnemonic. */
    public static String MenuBarView_addHeartsCard_mnemonic;

    /** The Add Hearts Card menu text. */
    public static String MenuBarView_addHeartsCard_text;

    /** The Add Spades Card menu mnemonic. */
    public static String MenuBarView_addSpadesCard_mnemonic;

    /** The Add Spades Card menu text. */
    public static String MenuBarView_addSpadesCard_text;

    /** The Add Special Card menu mnemonic. */
    public static String MenuBarView_addSpecialCard_mnemonic;

    /** The Add Special Card menu text. */
    public static String MenuBarView_addSpecialCard_text;

    /** The File menu mnemonic. */
    public static String MenuBarView_file_mnemonic;

    /** The File menu text. */
    public static String MenuBarView_file_text;

    /** The Help menu mnemonic. */
    public static String MenuBarView_help_mnemonic;

    /** The Help menu text. */
    public static String MenuBarView_help_text;

    /** The Layout menu mnemonic. */
    public static String MenuBarView_layout_mnemonic;

    /** The Layout menu text. */
    public static String MenuBarView_layout_text;

    /** The Network menu mnemonic. */
    public static String MenuBarView_network_mnemonic;

    /** The Network menu text. */
    public static String MenuBarView_network_text;

    /** The Table menu mnemonic. */
    public static String MenuBarView_table_mnemonic;

    /** The Table menu text. */
    public static String MenuBarView_table_text;

    /** The View menu mnemonic. */
    public static String MenuBarView_view_mnemonic;

    /** The View menu text. */
    public static String MenuBarView_view_text;

    // --- OpenAboutDialogAction --------------------------------------------

    /** The open about dialog action mnemonic. */
    public static String OpenAboutDialogAction_mnemonic;

    /** The open about dialog action text. */
    public static String OpenAboutDialogAction_text;

    // --- OpenNewTableAction -----------------------------------------------

    /** The open new table action accelerator. */
    public static String OpenNewTableAction_accelerator;

    /** The open new table action mnemonic. */
    public static String OpenNewTableAction_mnemonic;

    /** The open new table action text. */
    public static String OpenNewTableAction_text;

    // --- OpenTableAction -----------------------------------------------

    /** The open table action accelerator. */
    public static String OpenTableAction_accelerator;

    /** The open table action mnemonic. */
    public static String OpenTableAction_mnemonic;

    /** The open table action text. */
    public static String OpenTableAction_text;

    // --- RemoveAllCardPilesAction -----------------------------------------

    /** The remove all card piles action accelerator. */
    public static String RemoveAllCardPilesAction_accelerator;

    /** The remove all card piles action mnemonic. */
    public static String RemoveAllCardPilesAction_mnemonic;

    /** The remove all card piles action text. */
    public static String RemoveAllCardPilesAction_text;

    // --- RemoveAllCardsAction ---------------------------------------------

    /** The remove all cards action accelerator. */
    public static String RemoveAllCardsAction_accelerator;

    /** The remove all cards action mnemonic. */
    public static String RemoveAllCardsAction_mnemonic;

    /** The remove all cards action text. */
    public static String RemoveAllCardsAction_text;

    // --- RemoveCardAction -------------------------------------------------

    /** The remove card action accelerator. */
    public static String RemoveCardAction_accelerator;

    /** The remove card action mnemonic. */
    public static String RemoveCardAction_mnemonic;

    /** The remove card action text. */
    public static String RemoveCardAction_text;

    // --- RemoveCardPileAction ---------------------------------------------

    /** The remove card pile action accelerator. */
    public static String RemoveCardPileAction_accelerator;

    /** The remove card pile action mnemonic. */
    public static String RemoveCardPileAction_mnemonic;

    /** The remove card pile action text. */
    public static String RemoveCardPileAction_text;

    // --- RequestTableNetworkControlAction ---------------------------------

    /** The request table network control action mnemonic. */
    public static String RequestTableNetworkControlAction_mnemonic;

    /** The request table network control action text. */
    public static String RequestTableNetworkControlAction_text;

    // --- ResetTableOriginAction -------------------------------------------

    /** The reset table origin action mnemonic. */
    public static String ResetTableOriginAction_mnemonic;

    /** The reset table origin action text. */
    public static String ResetTableOriginAction_text;

    // --- SaveTableAction --------------------------------------------------

    /** The save table action accelerator. */
    public static String SaveTableAction_accelerator;

    /** The save table action mnemonic. */
    public static String SaveTableAction_mnemonic;

    /** The save table action text. */
    public static String SaveTableAction_text;

    // --- SaveTableAsAction ------------------------------------------------

    /** The save table as action mnemonic. */
    public static String SaveTableAsAction_mnemonic;

    /** The save table as action text. */
    public static String SaveTableAsAction_text;

    // --- SetAccordianDownCardPileLayoutAction -----------------------------

    /** The set accordian down card pile layout action mnemonic. */
    public static String SetAccordianDownCardPileLayoutAction_mnemonic;

    /** The set accordian down card pile layout action text. */
    public static String SetAccordianDownCardPileLayoutAction_text;

    // --- SetAccordianLeftCardPileLayoutAction -----------------------------

    /** The set accordian left card pile layout action mnemonic. */
    public static String SetAccordianLeftCardPileLayoutAction_mnemonic;

    /** The set accordian left card pile layout action text. */
    public static String SetAccordianLeftCardPileLayoutAction_text;

    // --- SetAccordianRightCardPileLayoutAction ----------------------------

    /** The set accordian right card pile layout action mnemonic. */
    public static String SetAccordianRightCardPileLayoutAction_mnemonic;

    /** The set accordian right card pile layout action text. */
    public static String SetAccordianRightCardPileLayoutAction_text;

    // --- SetAccordianUpCardPileLayoutAction -------------------------------

    /** The set accordian up card pile layout action mnemonic. */
    public static String SetAccordianUpCardPileLayoutAction_mnemonic;

    /** The set accordian up card pile layout action text. */
    public static String SetAccordianUpCardPileLayoutAction_text;

    // --- SetStackedCardPileLayoutAction -----------------------------------

    /** The set stacked card pile layout action mnemonic. */
    public static String SetStackedCardPileLayoutAction_mnemonic;

    /** The set stacked card pile layout action text. */
    public static String SetStackedCardPileLayoutAction_text;

    // --- TableNetworkPlayerView -------------------------------------------

    /** The text for the players label. */
    public static String TableNetworkPlayerView_playersLabel_text;

    /** The text for the editor role in the players list. */
    public static String TableNetworkPlayerView_playersList_role_editor;

    /** The text for the host role in the players list. */
    public static String TableNetworkPlayerView_playersList_role_host;

    /** The text for the local role in the players list. */
    public static String TableNetworkPlayerView_playersList_role_local;

    // --- TablePopupMenu ---------------------------------------------------

    /** The Add Component menu mnemonic. */
    public static String TablePopupMenu_addComponent_mnemonic;

    /** The Add Component menu text. */
    public static String TablePopupMenu_addComponent_text;

    // --- ViewUtils --------------------------------------------------------

    /** The name of the default component surface design user interface. */
    public static String ViewUtils_defaultComponentSurfaceDesignUI_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NlsMessages.class.getName(), NlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NlsMessages} class.
     */
    private NlsMessages()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- AboutDialog ------------------------------------------------------

    /**
     * Gets the formatted message for the About dialog message.
     * 
     * @return The formatted message for the About dialog message; never
     *         {@code null}.
     */
    /* @NonNull */
    static String AboutDialog_message()
    {
        return bind( AboutDialog_message, Branding.getName(), Branding.getVersion() );
    }

    /**
     * Gets the formatted message for the About dialog title.
     * 
     * @return The formatted message for the About dialog title; never
     *         {@code null}.
     */
    /* @NonNull */
    static String AboutDialog_title()
    {
        return bind( AboutDialog_title, Branding.getName() );
    }

    // --- MainFrame --------------------------------------------------------

    /**
     * Gets the formatted message for the Confirm Save Dirty Table dialog
     * message.
     * 
     * @param tableName
     *        The name of the active table; must not be {@code null}.
     * 
     * @return The formatted message for the Confirm Save Dirty Table dialog
     *         message; never {@code null}.
     */
    /* @NonNull */
    static String MainFrame_confirmSaveDirtyTable_message(
        /* @NonNull */
        final String tableName )
    {
        return bind( MainFrame_confirmSaveDirtyTable_message, tableName );
    }

    /**
     * Gets the formatted message for the frame title.
     * 
     * @param tableName
     *        The name of the active table; must not be {@code null}.
     * 
     * @return The formatted message for the frame title; never {@code null}.
     */
    /* @NonNull */
    static String MainFrame_title(
        /* @NonNull */
        final String tableName )
    {
        return bind( MainFrame_title, tableName, Branding.getName() );
    }

    // --- MainView ---------------------------------------------------------

    /**
     * Gets the formatted message for the table network disconnected message.
     * 
     * @param error
     *        The error that caused the table network disconnect; must not be
     *        {@code null}.
     * 
     * @return The formatted message for the table network disconnected message;
     *         never {@code null}.
     */
    /* @NonNull */
    static String MainView_tableNetworkDisconnected_error(
        /* @NonNull */
        final TableNetworkError error )
    {
        switch( error )
        {
            case SERVER_SHUTDOWN:
                return MainView_tableNetworkDisconnected_error_serverShutdown;

            case TRANSPORT_ERROR:
                return MainView_tableNetworkDisconnected_error_transportError;

            case UNEXPECTED_PEER_TERMINATION:
                return MainView_tableNetworkDisconnected_error_unexpectedPeerTermination;

            default:
                return bind( MainView_tableNetworkDisconnected_error_generic, error );
        }
    }

    // --- OpenAboutDialogAction --------------------------------------------

    /**
     * Gets the formatted text for the open about dialog action.
     * 
     * @return The formatted text for the open about dialog action; never
     *         {@code null}.
     */
    /* @NonNull */
    static String OpenAboutDialogAction_text()
    {
        return bind( OpenAboutDialogAction_text, Branding.getName() );
    }

    // --- TableNetworkPlayerView -------------------------------------------

    /**
     * Gets the formatted text for the specified player role in the players
     * list.
     * 
     * @param playerRole
     *        The player role; must not be {@code null}.
     * 
     * @return The formatted text for the specified role in the players list;
     *         never {@code null}.
     */
    /* @NonNull */
    static String TableNetworkPlayerView_playersList_role(
        /* @NonNull */
        final PlayerRole playerRole )
    {
        switch( playerRole )
        {
            case EDITOR:
                return TableNetworkPlayerView_playersList_role_editor;

            case HOST:
                return TableNetworkPlayerView_playersList_role_host;

            case LOCAL:
                return TableNetworkPlayerView_playersList_role_local;

            default:
                throw new AssertionError( "unsupported player role" ); //$NON-NLS-1$
        }
    }

    /**
     * Gets the formatted text for an element in the players list.
     * 
     * @param player
     *        The player associated with the list element; must not be
     *        {@code null}.
     * 
     * @return The formatted text for an element in the players list; never
     *         {@code null}.
     */
    /* @NonNull */
    static String TableNetworkPlayerView_playersList_text(
        /* @NonNull */
        final IPlayer player )
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( player.getName() );

        final List<PlayerRole> annotatedRoles = new ArrayList<PlayerRole>( 2 );
        if( player.hasRole( PlayerRole.EDITOR ) )
        {
            annotatedRoles.add( PlayerRole.EDITOR );
        }
        if( player.hasRole( PlayerRole.HOST ) )
        {
            annotatedRoles.add( PlayerRole.HOST );
        }
        if( player.hasRole( PlayerRole.LOCAL ) )
        {
            annotatedRoles.add( PlayerRole.LOCAL );
        }

        if( !annotatedRoles.isEmpty() )
        {
            sb.append( " (" ); //$NON-NLS-1$

            for( int index = 0, size = annotatedRoles.size(); index < size; ++index )
            {
                sb.append( TableNetworkPlayerView_playersList_role( annotatedRoles.get( index ) ) );
                if( index < (size - 1) )
                {
                    sb.append( ", " ); //$NON-NLS-1$
                }
            }

            sb.append( ")" ); //$NON-NLS-1$
        }

        return sb.toString();
    }
}
