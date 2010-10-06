/*
 * MenuBarView.java
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
 * Created on Oct 8, 2009 at 11:53:25 PM.
 */

package org.gamegineer.table.internal.ui.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.internal.ui.util.swing.JMenuItemGroup;

/**
 * The menu bar view.
 */
@NotThreadSafe
final class MenuBarView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The menu bar. */
    private final JMenuBar menuBar_;

    /** The model associated with this view. */
    private final MainModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MenuBarView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     */
    MenuBarView(
        /* @NonNull */
        final MainModel model )
    {
        assert model != null;

        model_ = model;
        menuBar_ = createMenuBar();
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
        final JMenu menu = new JMenu( Messages.MenuBarView_addCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addCard_mnemonic ).getKeyCode() );
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
        final JMenu menu = new JMenu( Messages.MenuBarView_addClubsCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addClubsCard_mnemonic ).getKeyCode() );
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
     * Creates the add deck menu.
     * 
     * @return The add deck menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddDeckMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_addDeck_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addDeck_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddStandard52CardDeckAction() );
        menu.add( Actions.getAddStandard54CardDeckAction() );
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
        final JMenu menu = new JMenu( Messages.MenuBarView_addDiamondsCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addDiamondsCard_mnemonic ).getKeyCode() );
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
        final JMenu menu = new JMenu( Messages.MenuBarView_addHeartsCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addHeartsCard_mnemonic ).getKeyCode() );
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
        final JMenu menu = new JMenu( Messages.MenuBarView_addSpadesCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addSpadesCard_mnemonic ).getKeyCode() );
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
        final JMenu menu = new JMenu( Messages.MenuBarView_addSpecialCard_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_addSpecialCard_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddJokerCardAction() );
        return menu;
    }

    /**
     * Creates the file menu.
     * 
     * @return The file menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createFileMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_file_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_file_mnemonic ).getKeyCode() );
        menu.add( Actions.getOpenNewTableAction() );
        menu.add( Actions.getOpenTableAction() );
        menu.addSeparator();
        menu.add( Actions.getSaveTableAction() );
        menu.add( Actions.getSaveTableAsAction() );
        menu.add( new JMenuItemGroup( menu, Actions.getOpenTableAction(), new FileHistoryMenuItemGroupContentProvider( model_ ) ) );
        menu.addSeparator();
        menu.add( Actions.getExitAction() );
        return menu;
    }

    /**
     * Creates the help menu.
     * 
     * @return The help menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createHelpMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_help_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_help_mnemonic ).getKeyCode() );
        menu.add( Actions.getOpenAboutDialogAction() );
        return menu;
    }

    /**
     * Creates the layout menu.
     * 
     * @return The layout menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createLayoutMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_layout_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_layout_mnemonic ).getKeyCode() );
        final ButtonGroup layoutButtonGroup = new ButtonGroup();
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetStackedCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianUpCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianDownCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianLeftCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianRightCardPileLayoutAction() ) ) );
        return menu;
    }

    /**
     * Creates the menu bar.
     * 
     * @return The menu bar; never {@code null}.
     */
    /* @NonNull */
    private JMenuBar createMenuBar()
    {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add( createFileMenu() );
        menuBar.add( createViewMenu() );
        menuBar.add( createTableMenu() );
        menuBar.add( createHelpMenu() );
        return menuBar;
    }

    /**
     * Creates the table menu.
     * 
     * @return The table menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createTableMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_table_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_table_mnemonic ).getKeyCode() );
        menu.add( Actions.getAddCardPileAction() );
        menu.add( Actions.getRemoveCardPileAction() );
        menu.add( Actions.getRemoveAllCardPilesAction() );
        menu.addSeparator();
        menu.add( createLayoutMenu() );
        menu.addSeparator();
        menu.add( createAddCardMenu() );
        menu.add( createAddDeckMenu() );
        menu.add( Actions.getRemoveCardAction() );
        menu.add( Actions.getRemoveAllCardsAction() );
        menu.add( Actions.getFlipCardAction() );
        menu.addSeparator();
        menu.add( Actions.getImportTableAction() );
        return menu;
    }

    /**
     * Creates the view menu.
     * 
     * @return The view menu; never {@code null}.
     */
    private JMenu createViewMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_view_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( Messages.MenuBarView_view_mnemonic ).getKeyCode() );
        menu.add( Actions.getResetTableOriginAction() );
        return menu;
    }

    /**
     * Gets the menu bar.
     * 
     * @return The menu bar; never {@code null}.
     */
    /* @NonNull */
    JMenuBar getMenuBar()
    {
        return menuBar_;
    }
}
