/*
 * OptionDialogs.java
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
 * Created on Oct 5, 2010 at 9:53:38 PM.
 */

package org.gamegineer.table.internal.ui.impl.util;

import java.awt.Component;
import javax.swing.JOptionPane;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.ui.impl.Branding;

/**
 * A collection of useful methods for working with option dialogs in the
 * application.
 */
@ThreadSafe
public final class OptionDialogs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code OptionDialogs} class.
     */
    private OptionDialogs()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Shows a confirmation dialog with the options Yes, No, and Cancel.
     * 
     * @param parentComponent
     *        The parent component of the dialog; may be {@code null}.
     * @param message
     *        The dialog message; must not be {@code null}.
     * 
     * @return The option selected by the user.
     */
    public static int showConfirmDialog(
        @Nullable
        final Component parentComponent,
        final String message )
    {
        return JOptionPane.showConfirmDialog( parentComponent, message, Branding.getName(), JOptionPane.YES_NO_CANCEL_OPTION );
    }

    /**
     * Shows an error message dialog.
     * 
     * @param parentComponent
     *        The parent component of the dialog; may be {@code null}.
     * @param message
     *        The dialog message; must not be {@code null}.
     */
    public static void showErrorMessageDialog(
        @Nullable
        final Component parentComponent,
        final String message )
    {
        JOptionPane.showMessageDialog( parentComponent, message, Branding.getName(), JOptionPane.ERROR_MESSAGE );
    }
}
