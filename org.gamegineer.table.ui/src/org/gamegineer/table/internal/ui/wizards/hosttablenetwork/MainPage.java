/*
 * MainPage.java
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
 * Created on Oct 7, 2010 at 11:22:38 PM.
 */

package org.gamegineer.table.internal.ui.wizards.hosttablenetwork;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.gamegineer.common.ui.databinding.swing.ComponentProperties;
import org.gamegineer.common.ui.databinding.wizard.WizardPageDataBindingAdapter;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.wizard.AbstractWizardPage;
import org.gamegineer.table.internal.ui.databinding.conversion.Converters;
import org.gamegineer.table.internal.ui.util.swing.JComponents;
import org.gamegineer.table.internal.ui.util.swing.SpringUtilities;

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

    /** The confirmed password field widget. */
    private JPasswordField confirmedPasswordField_;

    /** The page data binding adapter. */
    private WizardPageDataBindingAdapter dataBindingAdapter_;

    /** The page data binding context. */
    private DataBindingContext dataBindingContext_;

    /** The password field widget. */
    private JPasswordField passwordField_;

    /** The player name text field widget. */
    private JTextField playerNameTextField_;

    /** The port text field widget. */
    private JTextField portTextField_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainPage} class.
     */
    MainPage()
    {
        super( MainPage.class.getName() );

        confirmedPasswordField_ = null;
        dataBindingAdapter_ = null;
        dataBindingContext_ = null;
        passwordField_ = null;
        playerNameTextField_ = null;
        portTextField_ = null;

        setTitle( Messages.MainPage_title );
        setDescription( Messages.MainPage_description );
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

        final JLabel playerNameLabel = new JLabel( Messages.MainPage_playerNameLabel_text );
        playerNameLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_playerNameLabel_mnemonic ).getKeyCode() );
        container.add( playerNameLabel );
        playerNameTextField_ = new JTextField();
        JComponents.freezeHeight( playerNameTextField_ );
        container.add( playerNameTextField_ );
        playerNameLabel.setLabelFor( playerNameTextField_ );

        final JLabel portLabel = new JLabel( Messages.MainPage_portLabel_text );
        portLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_portLabel_mnemonic ).getKeyCode() );
        container.add( portLabel );
        portTextField_ = new JTextField();
        JComponents.freezeHeight( portTextField_ );
        container.add( portTextField_ );
        portLabel.setLabelFor( portTextField_ );

        final JLabel passwordLabel = new JLabel( Messages.MainPage_passwordLabel_text );
        passwordLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_passwordLabel_mnemonic ).getKeyCode() );
        container.add( passwordLabel );
        passwordField_ = new JPasswordField();
        JComponents.freezeHeight( passwordField_ );
        container.add( passwordField_ );
        passwordLabel.setLabelFor( passwordField_ );

        final JLabel confirmPasswordLabel = new JLabel( Messages.MainPage_confirmPasswordLabel_text );
        confirmPasswordLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_confirmPasswordLabel_mnemonic ).getKeyCode() );
        container.add( confirmPasswordLabel );
        confirmedPasswordField_ = new JPasswordField();
        JComponents.freezeHeight( confirmedPasswordField_ );
        container.add( confirmedPasswordField_ );
        confirmPasswordLabel.setLabelFor( confirmedPasswordField_ );

        final int horizontalSpacing = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        final int verticalSpacing = convertHeightInDlusToPixels( DialogConstants.VERTICAL_SPACING );
        SpringUtilities.buildCompactGrid( container, 4, 2, 0, 0, horizontalSpacing, verticalSpacing );

        createDataBindings();

        return container;
    }

    /**
     * Creates the data bindings for this page.
     */
    private void createDataBindings()
    {
        final Model model = ((HostTableNetworkWizard)getWizard()).getModel();
        dataBindingContext_ = new DataBindingContext();

        final IObservableValue playerNameTargetValue = ComponentProperties.text().observe( playerNameTextField_ );
        final IObservableValue playerNameModelValue = PojoProperties.value( "playerName" ).observe( model ); //$NON-NLS-1$
        final UpdateValueStrategy playerNameTargetToModelStrategy = new UpdateValueStrategy();
        playerNameTargetToModelStrategy.setBeforeSetValidator( model.getPlayerNameValidator() );
        dataBindingContext_.bindValue( playerNameTargetValue, playerNameModelValue, playerNameTargetToModelStrategy, null );

        final IObservableValue portTargetValue = ComponentProperties.text().observe( portTextField_ );
        final UpdateValueStrategy portModelToTargetStrategy = new UpdateValueStrategy();
        portModelToTargetStrategy.setConverter( Converters.getPrimitiveIntegerToStringConverter() );
        final IObservableValue portModelValue = PojoProperties.value( "port" ).observe( model ); //$NON-NLS-1$
        final UpdateValueStrategy portTargetToModelStrategy = new UpdateValueStrategy();
        portTargetToModelStrategy.setConverter( Converters.withExceptionMessage( Converters.getStringToPrimitiveIntegerConverter(), Messages.MainPage_port_illegal ) );
        portTargetToModelStrategy.setBeforeSetValidator( model.getPortValidator() );
        dataBindingContext_.bindValue( portTargetValue, portModelValue, portTargetToModelStrategy, portModelToTargetStrategy );

        final IObservableValue passwordTargetValue = ComponentProperties.password().observe( passwordField_ );
        final IObservableValue passwordModelValue = PojoProperties.value( "password" ).observe( model ); //$NON-NLS-1$
        dataBindingContext_.bindValue( passwordTargetValue, passwordModelValue );

        final IObservableValue confirmedPasswordTargetValue = ComponentProperties.password().observe( confirmedPasswordField_ );
        final IObservableValue confirmedPasswordModelValue = PojoProperties.value( "confirmedPassword" ).observe( model ); //$NON-NLS-1$
        dataBindingContext_.bindValue( confirmedPasswordTargetValue, confirmedPasswordModelValue );

        dataBindingContext_.addValidationStatusProvider( model.getPasswordValidationStatusProvider( passwordTargetValue, confirmedPasswordTargetValue ) );

        dataBindingAdapter_ = new WizardPageDataBindingAdapter( this, dataBindingContext_ );
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
}
