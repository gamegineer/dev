/*
 * AboutDialog.java
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
 * Created on Mar 30, 2013 at 8:39:59 PM.
 */

package org.gamegineer.table.internal.ui.impl.dialogs.about;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Window;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.View;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.AbstractDialog;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.table.internal.ui.impl.Branding;

/**
 * A dialog used to display information about the application.
 */
@NotThreadSafe
public final class AboutDialog
    extends AbstractDialog
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The identifier for the license button. */
    private static final String LICENSE_BUTTON_ID = "license"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AboutDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    public AboutDialog(
        /* @Nullable */
        final Window parentShell )
    {
        super( parentShell );

        setTitle( NlsMessages.AboutDialog_title() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#buttonPressed(java.lang.String)
     */
    @Override
    protected void buttonPressed(
        final String id )
    {
        if( id.equals( LICENSE_BUTTON_ID ) )
        {
            licensePressed();
        }
        else
        {
            super.buttonPressed( id );
        }
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#contentRealized()
     */
    @Override
    protected void contentRealized()
    {
        super.contentRealized();

        getButton( DialogConstants.OK_BUTTON_ID ).requestFocusInWindow();
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#createButtonsForButtonBar(java.awt.Container)
     */
    @Override
    protected void createButtonsForButtonBar(
        final Container parent )
    {
        final JButton licenseButton = createButton( parent, LICENSE_BUTTON_ID, NlsMessages.AboutDialog_licenseButton_text, false );
        licenseButton.setMnemonic( KeyStroke.getKeyStroke( NlsMessages.AboutDialog_licenseButton_mnemonic ).getKeyCode() );

        createButton( parent, DialogConstants.OK_BUTTON_ID, DialogConstants.OK_BUTTON_LABEL, true );
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#createDialogArea(java.awt.Container)
     */
    @Override
    protected Component createDialogArea(
        final Container parent )
    {
        final Container container = (Container)super.createDialogArea( parent );
        final SpringLayout containerLayout = new SpringLayout();
        container.setLayout( containerLayout );

        final JLabel applicationIconLabel = new JLabel();
        applicationIconLabel.setIcon( getApplicationIcon() );
        container.add( applicationIconLabel );
        containerLayout.putConstraint( SpringLayout.NORTH, applicationIconLabel, 0, SpringLayout.NORTH, container );
        containerLayout.putConstraint( SpringLayout.WEST, applicationIconLabel, 0, SpringLayout.WEST, container );

        final JLabel messageLabel = new JLabel( NlsMessages.AboutDialog_messageLabel_text() );
        messageLabel.setVerticalAlignment( SwingConstants.TOP );
        container.add( messageLabel );
        containerLayout.putConstraint( SpringLayout.NORTH, messageLabel, 0, SpringLayout.NORTH, applicationIconLabel );
        containerLayout.putConstraint( SpringLayout.WEST, messageLabel, convertWidthInDlusToPixels( 10 ), SpringLayout.EAST, applicationIconLabel );
        final int preferredMessageLabelWidth = convertWidthInCharsToPixels( 70 );
        containerLayout.getConstraints( messageLabel ).setWidth( Spring.constant( 0, preferredMessageLabelWidth, Integer.MAX_VALUE ) );
        containerLayout.getConstraints( messageLabel ).setHeight( Spring.constant( 0, getPreferredHtmlHeight( messageLabel.getText(), preferredMessageLabelWidth ), Integer.MAX_VALUE ) );

        containerLayout.putConstraint( SpringLayout.EAST, container, 0, SpringLayout.EAST, messageLabel );
        containerLayout.putConstraint( SpringLayout.SOUTH, container, 0, SpringLayout.SOUTH, messageLabel );

        return container;
    }

    /**
     * Gets the application icon.
     * 
     * @return The application icon or {@code null} if no icon is available.
     */
    /* @Nullable */
    private static Icon getApplicationIcon()
    {
        final int preferredSize = 32;
        Image preferredImage = null;
        for( final Image image : Branding.getWindowImages() )
        {
            preferredImage = image;
            if( (image.getWidth( null ) == preferredSize) && (image.getHeight( null ) == preferredSize) )
            {
                break;
            }
        }

        return (preferredImage != null) ? new ImageIcon( preferredImage ) : null;
    }

    /**
     * Gets the preferred height of the specified HTML text for the specified
     * preferred width.
     * 
     * @param htmlText
     *        The HTML text; must not be {@code null}.
     * @param preferredWidth
     *        The preferred width of the HTML text in pixels; must be positive.
     * 
     * @return The preferred height in pixels.
     */
    private static int getPreferredHtmlHeight(
        /* @NonNull */
        final String htmlText,
        final int preferredWidth )
    {
        assert htmlText != null;
        assert preferredWidth > 0;

        final JLabel label = new JLabel( htmlText );
        final View view = (View)label.getClientProperty( javax.swing.plaf.basic.BasicHTML.propertyKey );
        view.setSize( preferredWidth, 0.0F );
        return (int)Math.ceil( view.getPreferredSpan( View.Y_AXIS ) );
    }

    /**
     * Invoked when the License button is pressed.
     */
    private void licensePressed()
    {
        final LicenseDialog dialog = new LicenseDialog( getShell() );
        dialog.open();
    }
}
