/*
 * MainView.java
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
 * Created on Oct 8, 2009 at 11:00:49 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.IMainModelListener;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent;
import org.gamegineer.table.internal.ui.model.MainModelEvent;
import org.gamegineer.table.internal.ui.model.TableModel;

/**
 * The top-level view.
 */
@NotThreadSafe
final class MainView
    extends JPanel
    implements IMainModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8895515474498086806L;

    /** The model associated with this view. */
    private final MainModel model_;

    /** The table view. */
    private TableView tableView_;


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

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see javax.swing.JComponent#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        model_.addMainModelListener( this );
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setLayout( new BorderLayout() );
        setOpaque( true );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#mainModelStateChanged(org.gamegineer.table.internal.ui.model.MainModelEvent)
     */
    @Override
    public void mainModelStateChanged(
        final MainModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see javax.swing.JComponent#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        model_.removeMainModelListener( this );

        super.removeNotify();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#tableClosed(org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent)
     */
    @Override
    public void tableClosed(
        final MainModelContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableClosed( event.getTableModel() );
            }
        } );
    }

    /**
     * Invoked when a table has been closed.
     * 
     * @param tableModel
     *        The table model that was closed; must not be {@code null}.
     */
    private void tableClosed(
        /* @NonNull */
        final TableModel tableModel )
    {
        assert tableModel != null;
        assert tableView_ != null;

        remove( tableView_ );
        tableView_ = null;
        validate();
        requestFocusInWindow();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#tableOpened(org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent)
     */
    @Override
    public void tableOpened(
        final MainModelContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableOpened( event.getTableModel() );
            }
        } );
    }

    /**
     * Invoked when a new table has been opened.
     * 
     * @param tableModel
     *        The table model that was opened; must not be {@code null}.
     */
    private void tableOpened(
        /* @NonNull */
        final TableModel tableModel )
    {
        assert tableModel != null;
        assert tableView_ == null;

        tableView_ = new TableView( tableModel );
        add( tableView_, BorderLayout.CENTER );
        validate();
        tableView_.requestFocusInWindow();
    }
}
