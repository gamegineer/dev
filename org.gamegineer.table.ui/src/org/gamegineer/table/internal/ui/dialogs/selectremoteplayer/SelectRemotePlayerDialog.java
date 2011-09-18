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
import org.gamegineer.common.ui.databinding.dialog.BannerDialogDataBindingAdapter;
import org.gamegineer.common.ui.databinding.swing.ComponentProperties;
import org.gamegineer.common.ui.dialog.AbstractBannerDialog;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.dialog.DialogUtils;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.net.IPlayer;

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

    /** The dialog data binding adapter. */
    private BannerDialogDataBindingAdapter dataBindingAdapter_;

    /** The dialog data binding context. */
    private DataBindingContext dataBindingContext_;

    /** The dialog model. */
    private final Model model_;

    /** The player list component. */
    private JList playerList_;


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

        dataBindingAdapter_ = null;
        dataBindingContext_ = null;
        model_ = new Model( tableModel );
        playerList_ = null;

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
        playerList_.setModel( model_.getRemotePlayers() );

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
        final FontMetrics fontMetrics = container.getFontMetrics( container.getFont() );

        final JLabel remotePlayersLabel = new JLabel( NlsMessages.SelectRemotePlayerDialog_remotePlayersLabel_text );
        remotePlayersLabel.setBorder( BorderFactory.createEmptyBorder( 0, 0, DialogUtils.convertWidthInDlusToPixels( fontMetrics, 3 ), 0 ) );
        remotePlayersLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.SelectRemotePlayerDialog_remotePlayersLabel_mnemonic ).getKeyCode() );
        container.add( remotePlayersLabel, BorderLayout.NORTH );
        playerList_ = new JList();
        playerList_.setCellRenderer( new PlayerListCellRenderer() );
        final JScrollPane scrollPane = new JScrollPane( playerList_ );
        container.add( scrollPane, BorderLayout.CENTER );
        remotePlayersLabel.setLabelFor( playerList_ );

        return container;
    }

    /**
     * Creates the data bindings for this dialog.
     */
    private void createDataBindings()
    {
        dataBindingContext_ = new DataBindingContext();

        final IObservableValue remotePlayerTargetValue = ComponentProperties.singleSelectionValue().observe( playerList_ );
        final IObservableValue remotePlayerModelValue = PojoProperties.value( "remotePlayer" ).observe( model_ ); //$NON-NLS-1$
        final UpdateValueStrategy remotePlayerTargetToModelStrategy = new UpdateValueStrategy();
        remotePlayerTargetToModelStrategy.setBeforeSetValidator( model_.getRemotePlayerValidator() );
        dataBindingContext_.bindValue( remotePlayerTargetValue, remotePlayerModelValue, remotePlayerTargetToModelStrategy, null );

        dataBindingAdapter_ = new BannerDialogDataBindingAdapter( this, dataBindingContext_ )
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            protected void handleValidationStatusChanged()
            {
                super.handleValidationStatusChanged();

                getButton( DialogConstants.OK_BUTTON_ID ).setEnabled( !isCurrentValidationStatusFatal() );
            }
        };
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
        return model_.getRemotePlayer();
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
