/*
 * LicenseDialog.java
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
 * Created on Apr 4, 2013 at 10:40:19 PM.
 */

package org.gamegineer.table.internal.ui.impl.dialogs.about;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.AbstractDialog;
import org.gamegineer.common.ui.dialog.DialogConstants;

/**
 * A dialog used to display the application license.
 */
@NotThreadSafe
final class LicenseDialog
    extends AbstractDialog
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LicenseDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    public LicenseDialog(
        /* @Nullable */
        final Window parentShell )
    {
        super( parentShell );

        setTitle( NlsMessages.LicenseDialog_title );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#createButtonsForButtonBar(java.awt.Container)
     */
    @Override
    protected void createButtonsForButtonBar(
        final Container parent )
    {
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

        final JTextArea licenseTextArea = new JTextArea( 20, 50 );
        licenseTextArea.setEditable( false );
        licenseTextArea.setLineWrap( true );
        licenseTextArea.setWrapStyleWord( true );
        licenseTextArea.setText( NlsMessages.LicenseDialog_licenseTextArea_text );
        licenseTextArea.setCaretPosition( 0 );
        final JScrollPane licenseScrollPane = new JScrollPane( licenseTextArea );
        container.add( licenseScrollPane, BorderLayout.CENTER );

        return container;
    }
}
