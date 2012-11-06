/*
 * CardPilePopupMenu.java
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
 * Created on Jan 29, 2010 at 11:20:46 PM.
 */

package org.gamegineer.table.internal.ui.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.ContainerModel;
import org.gamegineer.table.internal.ui.prototype.ComponentPrototypesExtensionPoint;

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

    /** The model associated with the menu. */
    private final ContainerModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPilePopupMenu} class.
     * 
     * @param model
     *        The model associated with the menu; must not be {@code null}.
     */
    CardPilePopupMenu(
        /* @NonNull */
        final ContainerModel model )
    {
        assert model != null;

        model_ = model;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the add component menu.
     * 
     * @return The add component menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createAddComponentMenu()
    {
        final JMenu menu = new JMenu( NlsMessages.CardPilePopupMenu_addComponent_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( NlsMessages.CardPilePopupMenu_addComponent_mnemonic ).getKeyCode() );
        ComponentPrototypesExtensionPoint.buildMenu( menu, Actions.getAddComponentAction(), model_ );
        return menu;
    }

    /**
     * Creates the layout menu.
     * 
     * @return The layout menu; never {@code null}.
     */
    /* @NonNull */
    private static JMenu createLayoutMenu()
    {
        final JMenu menu = new JMenu( NlsMessages.CardPilePopupMenu_layout_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( NlsMessages.CardPilePopupMenu_layout_mnemonic ).getKeyCode() );
        final ButtonGroup layoutButtonGroup = new ButtonGroup();
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetStackedCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianUpCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianDownCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianLeftCardPileLayoutAction() ) ) );
        layoutButtonGroup.add( menu.add( new JRadioButtonMenuItem( Actions.getSetAccordianRightCardPileLayoutAction() ) ) );
        return menu;
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        add( createAddComponentMenu() );
        addSeparator();
        add( Actions.getRemoveCardAction() );
        add( Actions.getRemoveAllCardsAction() );
        add( Actions.getFlipCardAction() );
        addSeparator();
        add( createLayoutMenu() );
        addSeparator();
        add( Actions.getRemoveCardPileAction() );

        addPopupMenuListener( MenuUtils.getDefaultPopupMenuListener() );
    }
}
