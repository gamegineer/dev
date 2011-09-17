/*
 * SelectRemotePlayerDialog.java
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
 * Created on Sep 16, 2011 at 8:24:51 PM.
 */

package org.gamegineer.table.internal.ui.dialogs.selectremoteplayer;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.AbstractBannerDialog;
import org.gamegineer.common.ui.dialog.DialogUtils;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetwork;

/**
 * A dialog used to select a remote table network player.
 */
@NotThreadSafe
public final class SelectRemotePlayerDialog
    extends AbstractBannerDialog
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player list component. */
    private JList playerList_;

    /** The player list model. */
    private final DefaultListModel playerListModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SelectRemotePlayerDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     * @param tableModel
     *        The table model associated with the dialog; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableModel} is {@code null}.
     */
    public SelectRemotePlayerDialog(
        /* @Nullable */
        final Window parentShell,
        /* @NonNull */
        final TableModel tableModel )
    {
        super( parentShell );

        assertArgumentNotNull( tableModel, "tableModel" ); //$NON-NLS-1$

        playerList_ = null;
        playerListModel_ = createRemotePlayerList( tableModel );

        setTitle( NlsMessages.SelectRemotePlayerDialog_title );
        setBannerTitle( NlsMessages.SelectRemotePlayerDialog_bannerTitle );
        setDescription( NlsMessages.SelectRemotePlayerDialog_description );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractBannerDialog#createContentArea(java.awt.Container)
     */
    @Override
    protected Component createContentArea(
        final Container parent )
    {
        final Container container = (Container)super.createContentArea( parent );
        final FontMetrics fontMetrics = container.getFontMetrics( container.getFont() );

        final JLabel remotePlayersLabel = new JLabel( NlsMessages.SelectRemotePlayerDialog_remotePlayersLabel_text );
        remotePlayersLabel.setBorder( BorderFactory.createEmptyBorder( 0, 0, DialogUtils.convertWidthInDlusToPixels( fontMetrics, 3 ), 0 ) );
        remotePlayersLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.SelectRemotePlayerDialog_remotePlayersLabel_mnemonic ).getKeyCode() );
        container.add( remotePlayersLabel, BorderLayout.NORTH );
        final JList playerList = new JList( playerListModel_ );
        playerList.setCellRenderer( new PlayerListCellRenderer() );
        final JScrollPane scrollPane = new JScrollPane( playerList );
        container.add( scrollPane, BorderLayout.CENTER );
        remotePlayersLabel.setLabelFor( playerList );

        // TODO: enable/disable OK button if no item is selected (via data binding)

        return container;
    }

    /**
     * Creates the remote player list.
     * 
     * @param tableModel
     *        The table model; must not be {@code null}.
     * 
     * @return The remote player list; never {@code null}.
     */
    /* @NonNull */
    private static DefaultListModel createRemotePlayerList(
        /* @NonNull */
        final TableModel tableModel )
    {
        assert tableModel != null;

        final ITableNetwork tableNetwork = tableModel.getTableNetwork();
        final IPlayer localPlayer = tableNetwork.getLocalPlayer();
        final DefaultListModel playerListModel = new DefaultListModel();
        for( final IPlayer player : tableNetwork.getPlayers() )
        {
            if( player != localPlayer )
            {
                playerListModel.addElement( player );
            }
        }

        return playerListModel;
    }

    /**
     * Gets the player that was selected.
     * 
     * @return The player that was selected or {@code null} if no remote player
     *         was selected.
     */
    /* @Nullable */
    public IPlayer getSelectedPlayer()
    {
        // TODO: replace with data binding on model
        return (playerList_ != null) ? (IPlayer)playerList_.getSelectedValue() : null;
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


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PlayerListCellRenderer}
         * class.
         */
        PlayerListCellRenderer()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
        @Override
        public Component getListCellRendererComponent(
            final JList list,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus )
        {
            final IPlayer player = (IPlayer)value;
            final JLabel label = (JLabel)super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            label.setText( player.getName() );
            return label;
        }
    }
}
