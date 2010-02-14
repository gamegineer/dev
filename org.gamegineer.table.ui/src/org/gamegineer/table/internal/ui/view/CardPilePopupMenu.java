/*
 * CardPilePopupMenu.java
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
 * Created on Jan 29, 2010 at 11:20:46 PM.
 */

package org.gamegineer.table.internal.ui.view;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import net.jcip.annotations.NotThreadSafe;

/**
 * The popup menu associated with a card pile.
 */
@NotThreadSafe
final class CardPilePopupMenu
    extends JPopupMenu
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8397677136081557526L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPilePopupMenu} class.
     */
    CardPilePopupMenu()
    {
        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the add card menu.
     * 
     * @return The add card menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddCardMenu()
    {
        final JMenu menu = new JMenu( Messages.CardPilePopupMenu_addCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.CardPilePopupMenu_addCard_mnemonic ).getKeyCode() );
        menu.add( createAddClubsCardMenu() );
        menu.add( createAddDiamondsCardMenu() );
        menu.add( createAddHeartsCardMenu() );
        menu.add( createAddSpadesCardMenu() );
        menu.add( createAddSpecialCardMenu() );
        return menu;
    }

    /**
     * Creates the add Clubs card menu.
     * 
     * @return The add Clubs card menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddClubsCardMenu()
    {
        final JMenu menu = new JMenu( Messages.CardPilePopupMenu_addClubsCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.CardPilePopupMenu_addClubsCard_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddAceOfClubsCardAction() );
        menu.add( Actions.getAddTwoOfClubsCardAction() );
        menu.add( Actions.getAddThreeOfClubsCardAction() );
        menu.add( Actions.getAddFourOfClubsCardAction() );
        menu.add( Actions.getAddFiveOfClubsCardAction() );
        menu.add( Actions.getAddSixOfClubsCardAction() );
        menu.add( Actions.getAddSevenOfClubsCardAction() );
        menu.add( Actions.getAddEightOfClubsCardAction() );
        menu.add( Actions.getAddNineOfClubsCardAction() );
        menu.add( Actions.getAddTenOfClubsCardAction() );
        menu.add( Actions.getAddJackOfClubsCardAction() );
        menu.add( Actions.getAddQueenOfClubsCardAction() );
        menu.add( Actions.getAddKingOfClubsCardAction() );
        return menu;
    }

    /**
     * Creates the add Diamonds card menu.
     * 
     * @return The add Diamonds card menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddDiamondsCardMenu()
    {
        final JMenu menu = new JMenu( Messages.CardPilePopupMenu_addDiamondsCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.CardPilePopupMenu_addDiamondsCard_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddAceOfDiamondsCardAction() );
        menu.add( Actions.getAddTwoOfDiamondsCardAction() );
        menu.add( Actions.getAddThreeOfDiamondsCardAction() );
        menu.add( Actions.getAddFourOfDiamondsCardAction() );
        menu.add( Actions.getAddFiveOfDiamondsCardAction() );
        menu.add( Actions.getAddSixOfDiamondsCardAction() );
        menu.add( Actions.getAddSevenOfDiamondsCardAction() );
        menu.add( Actions.getAddEightOfDiamondsCardAction() );
        menu.add( Actions.getAddNineOfDiamondsCardAction() );
        menu.add( Actions.getAddTenOfDiamondsCardAction() );
        menu.add( Actions.getAddJackOfDiamondsCardAction() );
        menu.add( Actions.getAddQueenOfDiamondsCardAction() );
        menu.add( Actions.getAddKingOfDiamondsCardAction() );
        return menu;
    }

    /**
     * Creates the add Hearts card menu.
     * 
     * @return The add Hearts card menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddHeartsCardMenu()
    {
        final JMenu menu = new JMenu( Messages.CardPilePopupMenu_addHeartsCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.CardPilePopupMenu_addHeartsCard_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddAceOfHeartsCardAction() );
        menu.add( Actions.getAddTwoOfHeartsCardAction() );
        menu.add( Actions.getAddThreeOfHeartsCardAction() );
        menu.add( Actions.getAddFourOfHeartsCardAction() );
        menu.add( Actions.getAddFiveOfHeartsCardAction() );
        menu.add( Actions.getAddSixOfHeartsCardAction() );
        menu.add( Actions.getAddSevenOfHeartsCardAction() );
        menu.add( Actions.getAddEightOfHeartsCardAction() );
        menu.add( Actions.getAddNineOfHeartsCardAction() );
        menu.add( Actions.getAddTenOfHeartsCardAction() );
        menu.add( Actions.getAddJackOfHeartsCardAction() );
        menu.add( Actions.getAddQueenOfHeartsCardAction() );
        menu.add( Actions.getAddKingOfHeartsCardAction() );
        return menu;
    }

    /**
     * Creates the add Spades card menu.
     * 
     * @return The add Spades card menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddSpadesCardMenu()
    {
        final JMenu menu = new JMenu( Messages.CardPilePopupMenu_addSpadesCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.CardPilePopupMenu_addSpadesCard_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddAceOfSpadesCardAction() );
        menu.add( Actions.getAddTwoOfSpadesCardAction() );
        menu.add( Actions.getAddThreeOfSpadesCardAction() );
        menu.add( Actions.getAddFourOfSpadesCardAction() );
        menu.add( Actions.getAddFiveOfSpadesCardAction() );
        menu.add( Actions.getAddSixOfSpadesCardAction() );
        menu.add( Actions.getAddSevenOfSpadesCardAction() );
        menu.add( Actions.getAddEightOfSpadesCardAction() );
        menu.add( Actions.getAddNineOfSpadesCardAction() );
        menu.add( Actions.getAddTenOfSpadesCardAction() );
        menu.add( Actions.getAddJackOfSpadesCardAction() );
        menu.add( Actions.getAddQueenOfSpadesCardAction() );
        menu.add( Actions.getAddKingOfSpadesCardAction() );
        return menu;
    }

    /**
     * Creates the add Special card menu.
     * 
     * @return The add Special card menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddSpecialCardMenu()
    {
        final JMenu menu = new JMenu( Messages.CardPilePopupMenu_addSpecialCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.CardPilePopupMenu_addSpecialCard_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddJokerCardAction() );
        return menu;
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        add( createAddCardMenu() );
        add( Actions.getRemoveCardAction() );
        add( Actions.getFlipCardAction() );
        addSeparator();
        add( Actions.getRemoveCardPileAction() );
    }
}
