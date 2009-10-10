/*
 * MainView.java
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
 * Created on Oct 8, 2009 at 11:00:49 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.MainModel;

/**
 * The top-level view.
 */
@NotThreadSafe
final class MainView
    extends JPanel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8895515474498086806L;

    /** The model associated with this view. */
    @SuppressWarnings( "unused" )
    private final MainModel model_;

    /** The table view. */
    private final TableView tableView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     */
    MainView(
        /* @NonNull */
        final MainModel model )
    {
        assert model != null;

        model_ = model;
        tableView_ = new TableView( model );

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
        setLayout( new BorderLayout() );
        add( tableView_, BorderLayout.CENTER );
    }
}
