/*
 * MainView.java
 * Copyright 2008-2011 Gamegineer.org
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
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.DialogUtils;
import org.gamegineer.table.internal.ui.model.IMainModelListener;
import org.gamegineer.table.internal.ui.model.MainModel;
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
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8895515474498086806L;

    /** The main model listener for this view. */
    private IMainModelListener mainModelListener_;

    /** The model associated with this view. */
    private final MainModel model_;

    /** The split pane used to layout the component views. */
    private JSplitPane splitPane_;

    /** The default size of the split pane divider. */
    private int splitPaneDividerSize_;

    /** The table network listener for this view. */
    private ITableNetworkListener tableNetworkListener_;

    /** The table network player view. */
    private final TableNetworkPlayerView tableNetworkPlayerView_;

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

        mainModelListener_ = null;
        model_ = model;
        splitPane_ = null;
        splitPaneDividerSize_ = 0;
        tableNetworkListener_ = null;
        tableNetworkPlayerView_ = new TableNetworkPlayerView( model.getTableModel() );
        tableView_ = new TableView( model.getTableModel() );

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

        mainModelListener_ = new MainModelListener();
        model_.addMainModelListener( mainModelListener_ );
        tableNetworkListener_ = new TableNetworkListener();
        model_.getTableModel().getTableNetwork().addTableNetworkListener( tableNetworkListener_ );
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
        splitPane_.setLeftComponent( tableView_ );
        splitPane_.setRightComponent( tableNetworkPlayerView_ );
        add( splitPane_, BorderLayout.CENTER );

        tableNetworkPlayerView_.setVisible( false );
    }

    /*
     * @see javax.swing.JComponent#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        model_.getTableModel().getTableNetwork().removeTableNetworkListener( tableNetworkListener_ );
        tableNetworkListener_ = null;
        model_.removeMainModelListener( mainModelListener_ );
        mainModelListener_ = null;

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
        splitPane_.setDividerLocation( isVisible ? (getWidth() - Math.max( DialogUtils.convertWidthInCharsToPixels( getFontMetrics( getFont() ), 40 ), tableNetworkPlayerView_.getPreferredSize().width )) : 0 );
        validate();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A main model listener for the main view.
     */
    @Immutable
    private final class MainModelListener
        extends org.gamegineer.table.internal.ui.model.MainModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MainModelListener} class.
         */
        MainModelListener()
        {
            super();
        }
    }

    /**
     * A table network listener for the main view.
     */
    @Immutable
    private final class TableNetworkListener
        extends org.gamegineer.table.net.TableNetworkListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableNetworkListener} class.
         */
        TableNetworkListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkConnected(org.gamegineer.table.net.TableNetworkEvent)
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
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkDisconnected(org.gamegineer.table.net.TableNetworkDisconnectedEvent)
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
                        OptionDialogs.showErrorMessageDialog( MainView.this, NlsMessages.MainView_tableNetworkDisconnected_error( error ) );
                    }
                }
            } );
        }
    }
}
