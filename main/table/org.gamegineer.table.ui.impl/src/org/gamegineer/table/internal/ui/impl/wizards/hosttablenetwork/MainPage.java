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
 * Created on Oct 7, 2010 at 11:22:38 PM.
 */

package org.gamegineer.table.internal.ui.impl.wizards.hosttablenetwork;

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
 * The main page in the host table network wizard.
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

        final JLabel confirmPasswordLabel = new JLabel( NlsMessages.MainPage_confirmPasswordLabel_text );
        confirmPasswordLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( NlsMessages.MainPage_confirmPasswordLabel_mnemonic ).getKeyCode() );
        container.add( confirmPasswordLabel );
        final JPasswordField confirmedPasswordField = new JPasswordField();
        JComponents.freezeHeight( confirmedPasswordField );
        container.add( confirmedPasswordField );
        confirmPasswordLabel.setLabelFor( confirmedPasswordField );

        final int horizontalSpacing = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        final int verticalSpacing = convertHeightInDlusToPixels( DialogConstants.VERTICAL_SPACING );
        SpringUtils.buildCompactGrid( container, 4, 2, 0, 0, horizontalSpacing, verticalSpacing );

        controls_ = new Controls( confirmedPasswordField, passwordField, playerNameTextField, portTextField );

        createDataBindings();

        return container;
    }

    /**
     * Creates the data bindings for this page.
     */
    private void createDataBindings()
    {
        final HostTableNetworkWizard wizard = (HostTableNetworkWizard)getWizard();
        assert wizard != null;
        final Model model = wizard.getModel();
        final Controls controls = getControls();
        final DataBindingContext dataBindingContext = dataBindingContext_ = new DataBindingContext();

        final IObservableValue playerNameTargetValue = ComponentProperties.text().observe( controls.playerNameTextField );
        final IObservableValue playerNameModelValue = PojoProperties.value( "playerName" ).observe( model ); //$NON-NLS-1$
        final UpdateValueStrategy playerNameTargetToModelStrategy = new UpdateValueStrategy();
        playerNameTargetToModelStrategy.setBeforeSetValidator( model.getPlayerNameValidator() );
        dataBindingContext.bindValue( playerNameTargetValue, playerNameModelValue, playerNameTargetToModelStrategy, null );

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

        final IObservableValue confirmedPasswordTargetValue = ComponentProperties.password().observe( controls.confirmedPasswordField );
        final IObservableValue confirmedPasswordModelValue = PojoProperties.value( "confirmedPassword" ).observe( model ); //$NON-NLS-1$
        dataBindingContext.bindValue( confirmedPasswordTargetValue, confirmedPasswordModelValue );

        dataBindingContext.addValidationStatusProvider( model.getPasswordValidationStatusProvider( passwordTargetValue, confirmedPasswordTargetValue ) );

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
     * @return The page controls.
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

        /** The confirmed password field widget. */
        final JPasswordField confirmedPasswordField;

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
         * @param confirmedPasswordField
         *        The confirmed password field widget.
         * @param passwordField
         *        The password field widget.
         * @param playerNameTextField
         *        The player name text field widget.
         * @param portTextField
         *        The port text field widget.
         */
        Controls(
            final JPasswordField confirmedPasswordField,
            final JPasswordField passwordField,
            final JTextField playerNameTextField,
            final JTextField portTextField )
        {
            this.confirmedPasswordField = confirmedPasswordField;
            this.passwordField = passwordField;
            this.playerNameTextField = playerNameTextField;
            this.portTextField = portTextField;
        }
    }
}
