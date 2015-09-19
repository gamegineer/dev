/*
 * MainPage.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Oct 8, 2010 at 9:52:07 PM.
 */

package org.gamegineer.table.internal.ui.impl.wizards.jointablenetwork;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.databinding.swing.ComponentProperties;
import org.gamegineer.common.ui.databinding.wizard.WizardPageDataBindingAdapter;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.wizard.AbstractWizardPage;
import org.gamegineer.table.internal.ui.impl.databinding.conversion.Converters;
import org.gamegineer.table.internal.ui.impl.util.swing.JComponents;
import org.gamegineer.table.internal.ui.impl.util.swing.SpringUtils;

/**
 * The main page in the join table network wizard.
 */
@NotThreadSafe
final class MainPage
    extends AbstractWizardPage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The page controls. */
    private @Nullable Controls controls_;

    /** The page data binding adapter. */
    private @Nullable WizardPageDataBindingAdapter dataBindingAdapter_;

    /** The page data binding context. */
    private @Nullable DataBindingContext dataBindingContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainPage} class.
     */
    MainPage()
    {
        super( MainPage.class.getName() );

        controls_ = null;
        dataBindingAdapter_ = null;
        dataBindingContext_ = null;

        setTitle( NlsMessages.MainPage_title );
        setDescription( NlsMessages.MainPage_description );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialogPage#createContent(java.awt.Container)
     */
    @Override
    protected Component createContent(
        final Container parent )
    {
        final Container container = (Container)super.createContent( parent );
        final SpringLayout layout = new SpringLayout();
        container.setLayout( layout );

        final JLabel playerNameLabel = new JLabel( NlsMessages.MainPage_playerNameLabel_text );
        playerNameLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.MainPage_playerNameLabel_mnemonic ).getKeyCode() );
        container.add( playerNameLabel );
        final JTextField playerNameTextField = new JTextField();
        JComponents.freezeHeight( playerNameTextField );
        container.add( playerNameTextField );
        playerNameLabel.setLabelFor( playerNameTextField );

        final JLabel hostNameLabel = new JLabel( NlsMessages.MainPage_hostNameLabel_text );
        hostNameLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.MainPage_hostNameLabel_mnemonic ).getKeyCode() );
        container.add( hostNameLabel );
        final JTextField hostNameTextField = new JTextField();
        JComponents.freezeHeight( hostNameTextField );
        container.add( hostNameTextField );
        hostNameLabel.setLabelFor( hostNameTextField );

        final JLabel portLabel = new JLabel( NlsMessages.MainPage_portLabel_text );
        portLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.MainPage_portLabel_mnemonic ).getKeyCode() );
        container.add( portLabel );
        final JTextField portTextField = new JTextField();
        JComponents.freezeHeight( portTextField );
        container.add( portTextField );
        portLabel.setLabelFor( portTextField );

        final JLabel passwordLabel = new JLabel( NlsMessages.MainPage_passwordLabel_text );
        passwordLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.MainPage_passwordLabel_mnemonic ).getKeyCode() );
        container.add( passwordLabel );
        final JPasswordField passwordField = new JPasswordField();
        JComponents.freezeHeight( passwordField );
        container.add( passwordField );
        passwordLabel.setLabelFor( passwordField );

        final int horizontalSpacing = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        final int verticalSpacing = convertHeightInDlusToPixels( DialogConstants.VERTICAL_SPACING );
        SpringUtils.buildCompactGrid( container, 4, 2, 0, 0, horizontalSpacing, verticalSpacing );

        controls_ = new Controls( hostNameTextField, passwordField, playerNameTextField, portTextField );

        createDataBindings();

        return container;
    }

    /**
     * Creates the data bindings for this page.
     */
    private void createDataBindings()
    {
        final JoinTableNetworkWizard wizard = (JoinTableNetworkWizard)getWizard();
        assert wizard != null;
        final Model model = wizard.getModel();
        final Controls controls = getControls();
        final DataBindingContext dataBindingContext = dataBindingContext_ = new DataBindingContext();

        final IObservableValue playerNameTargetValue = ComponentProperties.text().observe( controls.playerNameTextField );
        final IObservableValue playerNameModelValue = PojoProperties.value( "playerName" ).observe( model ); //$NON-NLS-1$
        final UpdateValueStrategy playerNameTargetToModelStrategy = new UpdateValueStrategy();
        playerNameTargetToModelStrategy.setBeforeSetValidator( model.getPlayerNameValidator() );
        dataBindingContext.bindValue( playerNameTargetValue, playerNameModelValue, playerNameTargetToModelStrategy, null );

        final IObservableValue hostNameTargetValue = ComponentProperties.text().observe( controls.hostNameTextField );
        final IObservableValue hostNameModelValue = PojoProperties.value( "hostName" ).observe( model ); //$NON-NLS-1$
        final UpdateValueStrategy hostNameTargetToModelStrategy = new UpdateValueStrategy();
        hostNameTargetToModelStrategy.setBeforeSetValidator( model.getHostNameValidator() );
        dataBindingContext.bindValue( hostNameTargetValue, hostNameModelValue, hostNameTargetToModelStrategy, null );

        final IObservableValue portTargetValue = ComponentProperties.text().observe( controls.portTextField );
        final UpdateValueStrategy portModelToTargetStrategy = new UpdateValueStrategy();
        portModelToTargetStrategy.setConverter( Converters.getPrimitiveIntegerToStringConverter() );
        final IObservableValue portModelValue = PojoProperties.value( "port" ).observe( model ); //$NON-NLS-1$
        final UpdateValueStrategy portTargetToModelStrategy = new UpdateValueStrategy();
        portTargetToModelStrategy.setConverter( Converters.withExceptionMessage( Converters.getStringToPrimitiveIntegerConverter(), NlsMessages.MainPage_port_illegal ) );
        portTargetToModelStrategy.setBeforeSetValidator( model.getPortValidator() );
        dataBindingContext.bindValue( portTargetValue, portModelValue, portTargetToModelStrategy, portModelToTargetStrategy );

        final IObservableValue passwordTargetValue = ComponentProperties.password().observe( controls.passwordField );
        final IObservableValue passwordModelValue = PojoProperties.value( "password" ).observe( model ); //$NON-NLS-1$
        dataBindingContext.bindValue( passwordTargetValue, passwordModelValue );

        dataBindingAdapter_ = new WizardPageDataBindingAdapter( this, dataBindingContext );
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialogPage#dispose()
     */
    @Override
    public void dispose()
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

        super.dispose();
    }

    /**
     * Gets the page controls.
     * 
     * @return The page controls; never {@code null}.
     */
    private Controls getControls()
    {
        assert controls_ != null;
        return controls_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The page controls.
     */
    @Immutable
    private static final class Controls
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The host name text field widget. */
        final JTextField hostNameTextField;

        /** The password field widget. */
        final JPasswordField passwordField;

        /** The player name text field widget. */
        final JTextField playerNameTextField;

        /** The port text field widget. */
        final JTextField portTextField;

        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Controls} class.
         * 
         * @param hostNameTextField
         *        The host name text field widget; must not be {@code null}.
         * @param passwordField
         *        The password field widget; must not be {@code null}.
         * @param playerNameTextField
         *        The player name text field widget; must not be {@code null}.
         * @param portTextField
         *        The port text field widget; must not be {@code null}.
         */
        Controls(
            final JTextField hostNameTextField,
            final JPasswordField passwordField,
            final JTextField playerNameTextField,
            final JTextField portTextField )
        {
            this.hostNameTextField = hostNameTextField;
            this.passwordField = passwordField;
            this.playerNameTextField = playerNameTextField;
            this.portTextField = portTextField;
        }
    }
}
