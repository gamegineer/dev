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
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.IMainModelListener;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent;
import org.gamegineer.table.internal.ui.model.MainModelEvent;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.util.OptionDialogs;
import org.gamegineer.table.net.ITableNetworkListener;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkEvent;

/**
 * The top-level view.
 */
@NotThreadSafe
final class MainView
    extends JPanel
    implements IMainModelListener, ITableNetworkListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8895515474498086806L;

    /** The model associated with this view. */
    private final MainModel model_;

    /** The split pane used to layout the component views. */
    private JSplitPane splitPane_;

    /** The default size of the split pane divider. */
    private int splitPaneDividerSize_;

    /** The table network player view. */
    private TableNetworkPlayerView tableNetworkPlayerView_;

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
        splitPane_ = null;
        splitPaneDividerSize_ = 0;
        tableNetworkPlayerView_ = null;
        tableView_ = null;

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

        splitPane_ = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
        splitPane_.setBorder( BorderFactory.createEmptyBorder() );
        splitPaneDividerSize_ = splitPane_.getDividerSize();
        splitPane_.setDividerSize( 0 );
        splitPane_.setResizeWeight( 1.0 );
        add( splitPane_, BorderLayout.CENTER );
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

    /**
     * Sets the table network player view visibility.
     * 
     * @param isVisible
     *        {@code true} to show the table network player view; {@code false}
     *        to hide the table network player view.
     */
    private void setPlayerViewVisible(
        final boolean isVisible )
    {
        tableNetworkPlayerView_.setVisible( isVisible );
        splitPane_.setDividerSize( isVisible ? splitPaneDividerSize_ : 0 );
        splitPane_.setDividerLocation( isVisible ? 0.8 : 0.0 );
        validate();
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

        tableModel.getTableNetwork().removeTableNetworkListener( this );

        splitPane_.setLeftComponent( null );
        tableView_ = null;
        splitPane_.setRightComponent( null );
        tableNetworkPlayerView_ = null;

        validate();
        requestFocusInWindow();
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkListener#tableNetworkConnected(org.gamegineer.table.net.TableNetworkEvent)
     */
    @Override
    public void tableNetworkConnected(
        final TableNetworkEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                setPlayerViewVisible( true );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkListener#tableNetworkDisconnected(org.gamegineer.table.net.TableNetworkDisconnectedEvent)
     */
    @Override
    public void tableNetworkDisconnected(
        final TableNetworkDisconnectedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                setPlayerViewVisible( false );

                final TableNetworkError error = event.getError();
                if( error != null )
                {
                    OptionDialogs.showErrorMessageDialog( MainView.this, Messages.MainView_tableNetworkDisconnected_error( error ) );
                }
            }
        } );
    }

    /*
     * @see org.gamegineer.table.net.ITableNetworkListener#tableNetworkPlayersUpdated(org.gamegineer.table.net.TableNetworkEvent)
     */
    @Override
    public void tableNetworkPlayersUpdated(
        final TableNetworkEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
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
        splitPane_.setLeftComponent( tableView_ );
        tableNetworkPlayerView_ = new TableNetworkPlayerView( tableModel );
        tableNetworkPlayerView_.setVisible( false );
        splitPane_.setRightComponent( tableNetworkPlayerView_ );

        tableModel.getTableNetwork().addTableNetworkListener( this );

        validate();
        tableView_.requestFocusInWindow();
    }
}
