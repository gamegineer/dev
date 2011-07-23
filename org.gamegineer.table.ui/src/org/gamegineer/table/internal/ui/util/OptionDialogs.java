/*
 * OptionDialogs.java
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
 * Created on Oct 5, 2010 at 9:53:38 PM.
 */

package org.gamegineer.table.internal.ui.util;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Component;
import javax.swing.JOptionPane;
import net.jcip.annotations.ThreadSafe;

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
        super();
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    public static int showConfirmDialog(
        /* @Nullable */
        final Component parentComponent,
        /* @NonNull */
        final String message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

        return JOptionPane.showConfirmDialog( parentComponent, message, CommonNlsMessages.Common_application_name, JOptionPane.YES_NO_CANCEL_OPTION );
    }

    /**
     * Shows an error message dialog.
     * 
     * @param parentComponent
     *        The parent component of the dialog; may be {@code null}.
     * @param message
     *        The dialog message; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    public static void showErrorMessageDialog(
        /* @Nullable */
        final Component parentComponent,
        /* @NonNull */
        final String message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

        JOptionPane.showMessageDialog( parentComponent, message, CommonNlsMessages.Common_application_name, JOptionPane.ERROR_MESSAGE );
    }
}
