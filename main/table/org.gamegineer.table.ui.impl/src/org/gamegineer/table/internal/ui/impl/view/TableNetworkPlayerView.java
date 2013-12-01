/*
 * TableNetworkPlayerView.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Collection;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.layout.PixelConverter;
import org.gamegineer.table.internal.ui.impl.Activator;
import org.gamegineer.table.internal.ui.impl.BundleImages;
import org.gamegineer.table.internal.ui.impl.model.TableModel;
import org.gamegineer.table.internal.ui.impl.util.Comparators;
import org.gamegineer.table.internal.ui.impl.util.swing.SortedListModel;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkListener;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkEvent;

/**
 * A view of the table network players.
 */
@NotThreadSafe
final class TableNetworkPlayerView
    extends JPanel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5093618726394484148L;

    /** The model associated with this view. */
    private final TableModel model_;

    /** The player list model. */
    private final DefaultListModel<IPlayer> playerListModel_;

    /** The table network listener for this view. */
    private ITableNetworkListener tableNetworkListener_;


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
        playerListModel_ = new DefaultListModel<>();
        tableNetworkListener_ = null;

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

        tableNetworkListener_ = new TableNetworkListener();
        model_.getTableNetwork().addTableNetworkListener( tableNetworkListener_ );
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        final PixelConverter pixelConverter = new PixelConverter( this );
        final int borderWidth = pixelConverter.convertWidthInDlusToPixels( 3 );
        final int borderHeight = pixelConverter.convertHeightInDlusToPixels( 3 );
        setBorder( BorderFactory.createEmptyBorder( borderHeight, borderWidth, borderHeight, borderWidth ) );
        setLayout( new BorderLayout() );
        setOpaque( true );

        final JLabel playersLabel = new JLabel( NlsMessages.TableNetworkPlayerView_playersLabel_text );
        playersLabel.setBorder( BorderFactory.createEmptyBorder( 0, 0, pixelConverter.convertWidthInDlusToPixels( 3 ), 0 ) );
        add( playersLabel, BorderLayout.NORTH );

        final JList<IPlayer> playerList = new JList<>();
        playerList.setModel( new SortedListModel<>( playerListModel_, Comparators.PLAYER_BY_NAME ) );
        playerList.setCellRenderer( new PlayerListCellRenderer() );
        final JScrollPane scrollPane = new JScrollPane( playerList );
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
        final Collection<IPlayer> players )
    {
        assert players != null;

        playerListModel_.clear();
        for( final IPlayer player : players )
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
        model_.getTableNetwork().removeTableNetworkListener( tableNetworkListener_ );
        tableNetworkListener_ = null;

        super.removeNotify();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The cell renderer for the player list.
     */
    @Immutable
    private static final class PlayerListCellRenderer
        extends DefaultListCellRenderer
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 1956026725118986173L;

        /** The icon to display for a player with the editor requester role. */
        private final Icon editorRequesterRoleIcon_;

        /** The icon to display for a player with the editor role. */
        private final Icon editorRoleIcon_;

        /** The icon to display for a player with no special roles. */
        private final Icon noRolesIcon_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PlayerListCellRenderer}
         * class.
         */
        PlayerListCellRenderer()
        {
            final BundleImages bundleImages = Activator.getDefault().getBundleImages();
            editorRoleIcon_ = bundleImages.getIcon( BundleImages.ROLE_EDITOR );
            editorRequesterRoleIcon_ = bundleImages.getIcon( BundleImages.ROLE_EDITOR_REQUESTER );
            noRolesIcon_ = bundleImages.getIcon( BundleImages.ROLE_NONE );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
        @Override
        public Component getListCellRendererComponent(
            final JList<?> list,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus )
        {
            final IPlayer player = (IPlayer)value;
            final JLabel label = (JLabel)super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            label.setText( NlsMessages.TableNetworkPlayerView_playersList_text( player ) );
            label.setIcon( getPlayerIcon( player ) );
            return label;
        }

        /**
         * Gets the icon for the specified player.
         * 
         * @param player
         *        The player; must not be {@code null}.
         * 
         * @return The icon for the specified player; never {@code null}.
         */
        /* @NonNull */
        private Icon getPlayerIcon(
            /* @NonNull */
            final IPlayer player )
        {
            assert player != null;

            if( player.hasRole( PlayerRole.EDITOR ) )
            {
                return editorRoleIcon_;
            }
            else if( player.hasRole( PlayerRole.EDITOR_REQUESTER ) )
            {
                return editorRequesterRoleIcon_;
            }

            return noRolesIcon_;
        }
    }

    /**
     * A table network listener for the table network player view.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
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
                public void run()
                {
                    refreshPlayerList( event.getTableNetwork().getPlayers() );
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
                public void run()
                {
                    refreshPlayerList( Collections.<IPlayer>emptyList() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkPlayersUpdated(org.gamegineer.table.net.TableNetworkEvent)
         */
        @Override
        public void tableNetworkPlayersUpdated(
            final TableNetworkEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    refreshPlayerList( event.getTableNetwork().getPlayers() );
                }
            } );
        }
    }
}
