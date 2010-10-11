/*
 * MainPage.java
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
 * Created on Oct 7, 2010 at 11:22:38 PM.
 */

package org.gamegineer.table.internal.ui.wizards.hostnetworktable;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.wizard.AbstractWizardPage;
import org.gamegineer.table.internal.ui.util.swing.JComponents;
import org.gamegineer.table.internal.ui.util.swing.SpringUtilities;

/**
 * The main page in the host network table wizard.
 */
@NotThreadSafe
final class MainPage
    extends AbstractWizardPage
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainPage} class.
     */
    MainPage()
    {
        super( MainPage.class.getName() );

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
        final JTextField playerNameTextField = new JTextField();
        JComponents.freezeHeight( playerNameTextField );
        container.add( playerNameTextField );
        playerNameLabel.setLabelFor( playerNameTextField );

        final JLabel portLabel = new JLabel( Messages.MainPage_portLabel_text );
        portLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_portLabel_mnemonic ).getKeyCode() );
        container.add( portLabel );
        final JTextField portTextField = new JTextField();
        JComponents.freezeHeight( portTextField );
        container.add( portTextField );
        portLabel.setLabelFor( portTextField );

        final JLabel passwordLabel = new JLabel( Messages.MainPage_passwordLabel_text );
        passwordLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_passwordLabel_mnemonic ).getKeyCode() );
        container.add( passwordLabel );
        final JPasswordField passwordField = new JPasswordField();
        JComponents.freezeHeight( passwordField );
        container.add( passwordField );
        passwordLabel.setLabelFor( passwordField );

        final JLabel confirmPasswordLabel = new JLabel( Messages.MainPage_confirmPasswordLabel_text );
        confirmPasswordLabel.setDisplayedMnemonic( KeyStroke.getKeyStroke( Messages.MainPage_confirmPasswordLabel_mnemonic ).getKeyCode() );
        container.add( confirmPasswordLabel );
        final JPasswordField confirmPasswordField = new JPasswordField();
        JComponents.freezeHeight( confirmPasswordField );
        container.add( confirmPasswordField );
        confirmPasswordLabel.setLabelFor( confirmPasswordField );

        final int horizontalSpacing = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        final int verticalSpacing = convertHeightInDlusToPixels( DialogConstants.VERTICAL_SPACING );
        SpringUtilities.buildCompactGrid( container, 4, 2, 0, 0, horizontalSpacing, verticalSpacing );

        return container;
    }
}
