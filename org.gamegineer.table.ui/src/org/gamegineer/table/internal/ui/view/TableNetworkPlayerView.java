/*
 * TableNetworkPlayerView.java
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
 * Created on May 21, 2011 at 8:34:34 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.DialogUtils;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.net.ITableNetworkListener;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkEvent;

/**
 * A view of the table network players.
 */
@NotThreadSafe
final class TableNetworkPlayerView
    extends JPanel
    implements ITableNetworkListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5093618726394484148L;

    /** The model associated with this view. */
    private final TableModel model_;

    /** The player list model. */
    private final DefaultListModel playerListModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkPlayerView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     */
    TableNetworkPlayerView(
        /* @NonNull */
        final TableModel model )
    {
        assert model != null;

        model_ = model;
        playerListModel_ = new DefaultListModel();

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

        model_.getTableNetwork().addTableNetworkListener( this );
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        final FontMetrics fontMetrics = getFontMetrics( getFont() );
        final int borderWidth = DialogUtils.convertWidthInDlusToPixels( fontMetrics, 3 );
        final int borderHeight = DialogUtils.convertHeightInDlusToPixels( fontMetrics, 3 );
        setBorder( BorderFactory.createEmptyBorder( borderHeight, borderWidth, borderHeight, borderWidth ) );
        setLayout( new BorderLayout() );
        setOpaque( true );

        final JLabel label = new JLabel( NlsMessages.TableNetworkPlayerView_playersLabel_text );
        label.setBorder( BorderFactory.createEmptyBorder( 0, 0, DialogUtils.convertWidthInDlusToPixels( fontMetrics, 3 ), 0 ) );
        add( label, BorderLayout.NORTH );

        final JList list = new JList( playerListModel_ );
        final JScrollPane scrollPane = new JScrollPane( list );
        add( scrollPane, BorderLayout.CENTER );
    }

    /**
     * Refreshes the player list.
     * 
     * @param players
     *        The collection of players connected to the table network; must not
     *        be {@code null}.
     */
    private void refreshPlayerList(
        /* @NonNull */
        final Collection<String> players )
    {
        assert players != null;

        final List<String> sortedPlayers = new ArrayList<String>( players );
        Collections.sort( sortedPlayers );

        playerListModel_.clear();
        for( final String player : sortedPlayers )
        {
            playerListModel_.addElement( player );
        }
    }

    /*
     * @see javax.swing.JComponent#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        model_.getTableNetwork().removeTableNetworkListener( this );

        super.removeNotify();
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
                refreshPlayerList( event.getTableNetwork().getPlayers() );
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
                refreshPlayerList( Collections.<String>emptyList() );
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

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                refreshPlayerList( event.getTableNetwork().getPlayers() );
            }
        } );
    }
}
