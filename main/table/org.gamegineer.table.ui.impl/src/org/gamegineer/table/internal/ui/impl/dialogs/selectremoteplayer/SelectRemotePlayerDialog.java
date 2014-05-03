/*
 * SelectRemotePlayerDialog.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.dialogs.selectremoteplayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.databinding.dialog.BannerDialogDataBindingAdapter;
import org.gamegineer.common.ui.databinding.swing.ComponentProperties;
import org.gamegineer.common.ui.dialog.AbstractBannerDialog;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.layout.PixelConverter;
import org.gamegineer.table.internal.ui.impl.Activator;
import org.gamegineer.table.internal.ui.impl.BundleImages;
import org.gamegineer.table.internal.ui.impl.model.TableModel;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;

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

    /** The dialog controls. */
    @Nullable
    private Controls controls_;

    /** The dialog data binding adapter. */
    @Nullable
    private BannerDialogDataBindingAdapter dataBindingAdapter_;

    /** The dialog data binding context. */
    @Nullable
    private DataBindingContext dataBindingContext_;

    /** The dialog model. */
    private final Model model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SelectRemotePlayerDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     * @param tableModel
     *        The table model associated with the dialog; must not be
     *        {@code null}.
     */
    public SelectRemotePlayerDialog(
        @Nullable
        final Window parentShell,
        final TableModel tableModel )
    {
        super( parentShell );

        controls_ = null;
        dataBindingAdapter_ = null;
        dataBindingContext_ = null;
        model_ = new Model( tableModel );

        setTitle( NlsMessages.SelectRemotePlayerDialog_title );
        setBannerTitle( NlsMessages.SelectRemotePlayerDialog_bannerTitle );
        setDescription( NlsMessages.SelectRemotePlayerDialog_description );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#close()
     */
    @Override
    public boolean close()
    {
        if( dataBindingAdapter_ != null )
        {
            dataBindingAdapter_.dispose();
            dataBindingAdapter_ = null;
        }

        if( dataBindingContext_ != null )
        {
            dataBindingContext_.dispose();
            dataBindingContext_ = null;
        }

        return super.close();
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#contentCreated()
     */
    @Override
    protected void contentCreated()
    {
        super.contentCreated();

        // NB: may provide a data binding strategy for JList.setModel()
        getControls().playerList.setModel( model_.getRemotePlayers() );

        createDataBindings();
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractBannerDialog#createContentArea(java.awt.Container)
     */
    @Override
    protected Component createContentArea(
        final Container parent )
    {
        final Container container = (Container)super.createContentArea( parent );
        final PixelConverter pixelConverter = new PixelConverter( container );

        final JLabel remotePlayersLabel = new JLabel( NlsMessages.SelectRemotePlayerDialog_remotePlayersLabel_text );
        remotePlayersLabel.setBorder( BorderFactory.createEmptyBorder( 0, 0, pixelConverter.convertWidthInDlusToPixels( 3 ), 0 ) );
        remotePlayersLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.SelectRemotePlayerDialog_remotePlayersLabel_mnemonic ).getKeyCode() );
        container.add( remotePlayersLabel, BorderLayout.NORTH );
        final JList<IPlayer> playerList = new JList<>();
        playerList.setCellRenderer( new PlayerListCellRenderer() );
        final JScrollPane scrollPane = new JScrollPane( playerList );
        container.add( scrollPane, BorderLayout.CENTER );
        remotePlayersLabel.setLabelFor( playerList );

        controls_ = new Controls( playerList );

        return container;
    }

    /**
     * Creates the data bindings for this dialog.
     */
    private void createDataBindings()
    {
        final DataBindingContext dataBindingContext = dataBindingContext_ = new DataBindingContext();
        final Controls controls = getControls();

        final IObservableValue remotePlayerTargetValue = ComponentProperties.singleSelectionValue().observe( controls.playerList );
        final IObservableValue remotePlayerModelValue = PojoProperties.value( "remotePlayer" ).observe( model_ ); //$NON-NLS-1$
        final UpdateValueStrategy remotePlayerTargetToModelStrategy = new UpdateValueStrategy();
        remotePlayerTargetToModelStrategy.setBeforeSetValidator( model_.getRemotePlayerValidator() );
        dataBindingContext.bindValue( remotePlayerTargetValue, remotePlayerModelValue, remotePlayerTargetToModelStrategy, null );

        dataBindingAdapter_ = new BannerDialogDataBindingAdapter( this, dataBindingContext )
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            protected void handleValidationStatusChanged()
            {
                super.handleValidationStatusChanged();

                final JButton okButton = getButton( DialogConstants.OK_BUTTON_ID );
                assert okButton != null;
                okButton.setEnabled( !isCurrentValidationStatusFatal() );
            }
        };
    }

    /**
     * Gets the dialog controls.
     * 
     * @return The dialog controls; never {@code null}.
     */
    private Controls getControls()
    {
        assert controls_ != null;
        return controls_;
    }

    /**
     * Gets the player that was selected.
     * 
     * @return The player that was selected or {@code null} if no remote player
     *         was selected.
     */
    @Nullable
    public IPlayer getSelectedPlayer()
    {
        return model_.getRemotePlayer();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The dialog controls.
     */
    @Immutable
    private static final class Controls
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The player list component. */
        final JList<IPlayer> playerList;

        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Controls} class.
         * 
         * @param playerList
         *        The player list component; must not be {@code null}.
         */
        Controls(
            final JList<IPlayer> playerList )
        {
            this.playerList = playerList;
        }
    }

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
            final Icon editorRequesterRoleIcon = bundleImages.getIcon( BundleImages.ROLE_EDITOR_REQUESTER );
            assert editorRequesterRoleIcon != null;
            editorRequesterRoleIcon_ = editorRequesterRoleIcon;
            final Icon noRolesIcon = bundleImages.getIcon( BundleImages.ROLE_NONE );
            assert noRolesIcon != null;
            noRolesIcon_ = noRolesIcon;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
        @Override
        public Component getListCellRendererComponent(
            @Nullable
            final JList<?> list,
            @Nullable
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus )
        {
            assert value != null;

            final IPlayer player = (IPlayer)value;
            final JLabel label = (JLabel)super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            label.setText( player.getName() );
            label.setIcon( player.hasRole( PlayerRole.EDITOR_REQUESTER ) ? editorRequesterRoleIcon_ : noRolesIcon_ );
            return label;
        }
    }
}
