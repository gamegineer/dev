/*
 * MenuBarView.java
 * Copyright 2008-2009 Gamegineer.org
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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.MainModel;

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
    @SuppressWarnings( "unused" )
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
     * Creates the file menu.
     * 
     * @return The file menu; never {@code null}.
     */
    /* @NonNull */
    private JMenu createFileMenu()
    {
        final JMenu menu = new JMenu( Messages.MenuBarView_file_text );
        menu.setMnemonic( Messages.toMnemonic( Messages.MenuBarView_file_mnemonic ) );
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
        menu.setMnemonic( Messages.toMnemonic( Messages.MenuBarView_help_mnemonic ) );
        menu.add( Actions.getOpenAboutDialogAction() );
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
        menu.setMnemonic( Messages.toMnemonic( Messages.MenuBarView_table_mnemonic ) );
        menu.add( Actions.getAddCardAction() );
        menu.add( Actions.getRemoveCardAction() );
        menu.addSeparator();
        menu.add( Actions.getFlipCardAction() );
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
