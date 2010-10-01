/*
 * TablePopupMenu.java
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
 * Created on Jan 29, 2010 at 11:28:09 PM.
 */

package org.gamegineer.table.internal.ui.view;

import javax.swing.JPopupMenu;
import net.jcip.annotations.NotThreadSafe;

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


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TablePopupMenu} class.
     */
    TablePopupMenu()
    {
        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        add( Actions.getAddCardPileAction() );
        add( Actions.getRemoveAllCardPilesAction() );
    }
}
