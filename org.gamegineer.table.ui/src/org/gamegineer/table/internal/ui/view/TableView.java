/*
 * TableView.java
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
 * Created on Oct 6, 2009 at 11:16:52 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.awt.Color;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.MainModel;

/**
 * The table view.
 */
@NotThreadSafe
final class TableView
    extends JPanel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3574703230407179091L;

    /** The model associated with this view. */
    @SuppressWarnings( "unused" )
    private final MainModel model_;


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
        final MainModel model )
    {
        assert model != null;

        model_ = model;

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
        setBackground( new Color( 0, 128, 0 ) );
    }
}
