/*
 * TablePopupMenu.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jan 29, 2010 at 11:28:09 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.impl.model.TableModel;
import org.gamegineer.table.internal.ui.impl.prototype.ComponentPrototypesExtensionPoint;

/**
 * The popup menu associated with a table.
 */
@NotThreadSafe
final class TablePopupMenu
    extends JPopupMenu
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1769395419445648694L;

    /** The model associated with the menu. */
    private final TableModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TablePopupMenu} class.
     * 
     * @param model
     *        The model associated with the menu.
     */
    TablePopupMenu(
        final TableModel model )
    {
        model_ = model;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the add component menu.
     * 
     * @return The add component menu.
     */
    private JMenu createAddComponentMenu()
    {
        final JMenu menu = new JMenu( NlsMessages.TablePopupMenu_addComponent_text );
        menu.setMnemonic( KeyStroke.getKeyStroke( NlsMessages.TablePopupMenu_addComponent_mnemonic ).getKeyCode() );
        ComponentPrototypesExtensionPoint.buildMenu( menu, Actions.getAddComponentAction(), model_ );
        return menu;
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        add( createAddComponentMenu() );
        addSeparator();
        add( Actions.getRemoveAllComponentsAction() );

        addPopupMenuListener( MenuUtils.getDefaultPopupMenuListener() );
    }
}
